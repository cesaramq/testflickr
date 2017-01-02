package flickr.etermax.test.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.webkit.URLUtil;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.builder.Builders;

import java.util.Map;

/**
 * Created by César Muñoz on 09/03/16.
 */
public class NetUtils {
    private Context context;
    private OnNetUtilsActions onNetUtilsActions;
    private static final String BASE_URL = "https://api.flickr.com/services/rest/";
    private static final String POST = "POST", GET = "GET", PUT = "PUT", DELETE = "DELETE";

    public NetUtils(Context context, OnNetUtilsActions onNetUtilsActions) {
        this.context = context;
        this.onNetUtilsActions = onNetUtilsActions;
    }

    //WITH BODY:

    private Future<Response<String>> wBodyRequest(String url, Json data, Json header,
                                                  final String request) {
        url = proccessUrl(url);
        onNetUtilsActions.onInitRequest(url);
        Utils.log("init " + request + " request to: " + url);
        Builders.Any.B b = Ion.with(context).load(request, url);
        b = getHeaderData(b, header);
        return getData(b, data)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        sendResult(e, result);
                    }
                });
    }

    private Future<Response<String>> postRequest(String url, Json data, Json header) {
        return wBodyRequest(url, data, header, POST);
    }

    public Future<Response<String>> postRequest(String url, Json data) {
        return postRequest(url, data, null);
    }

    private Future<Response<String>> putRequest(String url, Json data, Json header) {
        return wBodyRequest(url, data, header, PUT);
    }

    public Future<Response<String>> putRequest(String url, Json data) {
        return putRequest(url, data, null);
    }

    //WITHOUT BODY:

    private Future<Response<String>> woBodyRequest(String url, Json data, Json headerData,
                                                   final String request) {
        url = proccessUrl(url);
        onNetUtilsActions.onInitRequest(url);
        url = getUrlWithData(url, data);
        Utils.log("init " + request + " request to: " + url);
        Builders.Any.B b = Ion.with(context).load(request, url);
        b = getHeaderData(b, headerData);
        return b.asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        sendResult(e, result);
                    }
                });
    }

    private Future<Response<String>> getRequest(String url, Json data, Json headerData) {
        return woBodyRequest(url, data, headerData, GET);
    }

    public Future<Response<String>> getRequest(String url, Json data) {
        return getRequest(url, data, Json.object());
    }

    public Future<Response<String>> getRequest(String url) {
        return getRequest(url, Json.object(), Json.object());
    }

    private Future<Response<String>> deleteRequest(String url, Json data, Json headerData) {
        return woBodyRequest(url, data, headerData, DELETE);
    }

    public Future<Response<String>> deleteRequest(String url, Json data) {
        return deleteRequest(url, data, Json.object());
    }

    private Builders.Any.B getData(Builders.Any.B builder, Json data) {
        if (data != null && data.isObject()) {
            for (Map.Entry<String, Json> uno : data.asJsonMap().entrySet()) {
                if (uno.getValue().isArray()) {
                    String keyArr = uno.getKey() + "[]";
                    for (Json dos : uno.getValue().asJsonList()) {
                        builder.setBodyParameter(keyArr, dos.asString());
                    }
                } else {
                    builder.setBodyParameter(uno.getKey(), uno.getValue().asString());
                }
            }
        }
        return builder;
    }

    public static Builders.Any.B getHeaderData(Builders.Any.B builder, Json headerData) {
        if (headerData != null && headerData.isObject()) {
            for (Map.Entry<String, Json> uno : headerData.asJsonMap().entrySet()) {
                builder.setHeader(uno.getKey(), uno.getValue().asString());
            }
        }
        return builder;
    }

    private String getUrlWithData(String url, Json data) {
        if (data != null && data.isObject()) {
            boolean first = true;
            for (Map.Entry<String, Json> uno : data.asJsonMap().entrySet()) {
                if (first) {
                    url += "?";
                    first = false;
                } else {
                    url += "&";
                }
                if (uno.getValue().isArray()) {
                    String keyArr = uno.getKey() + "[]";
                    for (Json dos : uno.getValue().asJsonList()) {
                        url += (Uri.encode(keyArr) + "=" + Uri.encode(dos.asString()));
                    }
                } else {
                    url += (Uri.encode(uno.getKey()) + "=" + Uri.encode(uno.getValue().asString()));
                }
            }
        }
        return url;
    }

    public interface OnNetUtilsActions {
        void onInitRequest(String url);

        void onFinishRequest(Exception e, String response, int status);
    }

    public void sendResult(Exception e, Response<String> response) {
        onNetUtilsActions.onFinishRequest(e, (response == null) ? "" : response.getResult(),
                (response == null) ? 0 : response.getHeaders().code());
    }

    public static boolean hayInternet(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();
    }

    public static String proccessUrl(String url) {
        if (!URLUtil.isValidUrl(url)) {
            url = BASE_URL + url;
        }
        return url;
    }

}