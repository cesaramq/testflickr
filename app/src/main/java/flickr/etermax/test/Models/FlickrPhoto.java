package flickr.etermax.test.Models;

import flickr.etermax.test.Utils.Json;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class FlickrPhoto {
    private String id;
    private String owner;
    private String urlSmall;
    private String urlMedium;
    private String urlAvatar;
    private String title;
    private String description;
    private String jsonPhotoStr;
    private static final String URL_AVATAR
            = "http://farm%s.staticflickr.com/%s/buddyicons/%s.jpg";
    private static final String URL_AVATAR_DEFAULT = "https://www.flickr.com/images/buddyicon.jpg";

    public FlickrPhoto(Json jsonPhoto) {
        this.jsonPhotoStr = jsonPhoto.toString();
        id = jsonPhoto.at("id").asString();
        owner = jsonPhoto.at("owner").asString();
        setUrlsPhoto(jsonPhoto);
        setUrlAvatar(jsonPhoto);
        title = jsonPhoto.at("title").asString();
        description = jsonPhoto.at("description").at("_content").asString();
    }

    private void setUrlsPhoto(Json jsonPhoto) {
        if (jsonPhoto.has("url_s") && !jsonPhoto.at("url_s").isNull()) {
            urlSmall = jsonPhoto.at("url_s").asString();
        }
        if (jsonPhoto.has("url_m") && !jsonPhoto.at("url_m").isNull()) {
            urlMedium = jsonPhoto.at("url_m").asString();
        } else {
            urlMedium = urlSmall;
        }
    }

    private void setUrlAvatar(Json jsonPhoto) {
        urlAvatar = URL_AVATAR_DEFAULT;
        Json iconserver = jsonPhoto.at("iconserver");
        if (iconserver.isString()) {
            if (Integer.parseInt(iconserver.asString()) > 0) {
                int iconFarm = jsonPhoto.at("iconfarm").asInteger();
                urlAvatar = String.format(URL_AVATAR, iconFarm, iconserver.asString(), owner);
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getUrlSmall() {
        return urlSmall;
    }

    public String getUrlMedium() {
        return urlMedium;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getJsonPhotoStr() {
        return jsonPhotoStr;
    }
}
