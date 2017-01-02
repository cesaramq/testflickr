package flickr.etermax.test.Managers;

import android.os.AsyncTask;

import java.util.ArrayList;

import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.Utils.Json;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class AsyncProcessPhoto extends AsyncTask<String, Void, ArrayList<FlickrPhoto>> {
    private OnAsyncProcessActions onAsyncProcessActions;

    public AsyncProcessPhoto(OnAsyncProcessActions onAsyncProcessActions) {
        this.onAsyncProcessActions = onAsyncProcessActions;
    }

    @Override
    protected ArrayList<FlickrPhoto> doInBackground(String... params) {
        ArrayList<FlickrPhoto> flickrPhotos = new ArrayList<>();
        Json json = Json.read(params[0]);
        Json photosArray = json.at("photos").at("photo");
        for (Json uno : photosArray.asJsonList()) {
            flickrPhotos.add(new FlickrPhoto(uno));
        }
        return flickrPhotos;
    }

    @Override
    protected void onPostExecute(ArrayList<FlickrPhoto> flickrPhotos) {
        super.onPostExecute(flickrPhotos);
        onAsyncProcessActions.onProcessCompleted(flickrPhotos);
    }

    @Override
    protected void onCancelled(ArrayList<FlickrPhoto> flickrPhotos) {
        super.onCancelled(flickrPhotos);
        onAsyncProcessActions.onProcessError(new Error("Cancelled"));
    }

    public interface OnAsyncProcessActions {
        void onProcessCompleted(ArrayList<FlickrPhoto> flickrPhotos);

        void onProcessError(Error error);
    }
}
