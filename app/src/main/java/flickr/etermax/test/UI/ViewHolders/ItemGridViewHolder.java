package flickr.etermax.test.UI.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.R;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class ItemGridViewHolder extends RecyclerView.ViewHolder {
    private ImageView img;
    private Context context;

    public ItemGridViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        img = (ImageView) itemView.findViewById(R.id.img);
    }

    public static ItemGridViewHolder getInstancia(ViewGroup parent) {
        return new ItemGridViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_grid, parent, false));
    }

    public void setData(FlickrPhoto flickrPhoto) {
        Glide.with(context).load(flickrPhoto.getUrlSmall()).into(img);
    }
}
