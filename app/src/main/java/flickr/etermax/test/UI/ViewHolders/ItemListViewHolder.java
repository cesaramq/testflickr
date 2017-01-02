package flickr.etermax.test.UI.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.R;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class ItemListViewHolder extends RecyclerView.ViewHolder {
    private ImageView img;
    private CircleImageView imgAvatar;
    private TextView txtNombreUser, txtTitulo, txtFecha;
    private Context context;

    public ItemListViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        img = (ImageView) itemView.findViewById(R.id.img);
        imgAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar);
        txtNombreUser = (TextView) itemView.findViewById(R.id.txt_nombre_user);
        txtTitulo = (TextView) itemView.findViewById(R.id.txt_titulo);
        txtFecha = (TextView) itemView.findViewById(R.id.txt_fecha);
    }

    public static ItemListViewHolder getInstancia(ViewGroup parent) {
        return new ItemListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_list, parent, false));
    }

    public void setData(FlickrPhoto flickrPhoto) {
        Glide.with(context).load(flickrPhoto.getUrlMedium()).into(img);
        Glide.with(context).load(flickrPhoto.getUrlAvatar()).into(imgAvatar);
        txtNombreUser.setText(flickrPhoto.getOwnerName());
        txtTitulo.setText(flickrPhoto.getTitle());
        txtFecha.setText(flickrPhoto.getDateUploaded());
    }
}
