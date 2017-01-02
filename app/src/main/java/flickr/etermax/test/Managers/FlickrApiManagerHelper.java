package flickr.etermax.test.Managers;

import java.util.ArrayList;

import flickr.etermax.test.Models.FlickrPhoto;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class FlickrApiManagerHelper {
    public interface GetFlickrPhotosList {
        void onGetFlickrPhotosListSuccess(ArrayList<FlickrPhoto> flickrPhotos);

        void onGetFlickrPhotosListError(Error error);
    }
}
