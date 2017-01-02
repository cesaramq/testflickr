package flickr.etermax.test.Managers;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.R;
import flickr.etermax.test.Utils.Json;
import flickr.etermax.test.Utils.NetUtils;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class FlickrApiManager {
    private Context context;
    private String apiKey;
    private static final String EXTRAS = "icon_server,description,url_s,url_m";

    public FlickrApiManager(Context context) {
        this.context = context;
        apiKey = context.getString(R.string.flickr_key);
    }

    public void getPublicPhotosList(String search, int page, int perPage,
                                    final FlickrApiManagerHelper.GetFlickrPhotosList callback) {
        NetUtils netUtils = new NetUtils(context, new NetUtils.OnNetUtilsActions() {
            @Override
            public void onInitRequest(String url) {

            }

            @Override
            public void onFinishRequest(Exception e, String response, int status) {
                processResponse(e, response, status, callback);
            }
        });

        Json data = getJsonDataGET();
        if (TextUtils.isEmpty(search)) {
            data.set("method", "flickr.photos.getRecent");
        } else {
            data.set("method", "flickr.photos.search");
            data.set("text", search);
        }
        data.set("per_page", perPage);
        data.set("page", page);

        netUtils.getRequest("", data);
    }

    private void processResponse(Exception e, String response, int status,
                                 final FlickrApiManagerHelper.GetFlickrPhotosList callback) {
        if (status == 200) {
            new AsyncProcessPhoto(new AsyncProcessPhoto.OnAsyncProcessActions() {
                @Override
                public void onProcessCompleted(ArrayList<FlickrPhoto> flickrPhotos) {
                    callback.onGetFlickrPhotosListSuccess(flickrPhotos);
                }

                @Override
                public void onProcessError(Error error) {
                    callback.onGetFlickrPhotosListError(error);
                }
            }).execute(response);
        } else {
            if (e == null) {
                callback.onGetFlickrPhotosListError(new Error(response));
            } else {
                callback.onGetFlickrPhotosListError(new Error(e));
            }
        }
    }

    private Json getJsonDataGET() {
        Json data = Json.object();
        data.set("api_key", apiKey);
        data.set("format", "json");
        data.set("nojsoncallback", "1");
        data.set("extras", EXTRAS);
        return data;
    }
}
