package flickr.etermax.test.UI.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.MyActivity;
import flickr.etermax.test.R;
import flickr.etermax.test.UI.ViewHolders.ItemListViewHolder;
import flickr.etermax.test.Utils.Json;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class DetalleActivity extends MyActivity {
    public static final String FIELD_FLICKR_PHOTO = "flickrPhotoJsonStr";
    private FlickrPhoto flickrPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        verificarVariablesIntent();
        initVars();
        setClicks();
    }

    private void verificarVariablesIntent() {
        String str = getIntent().getStringExtra(FIELD_FLICKR_PHOTO);
        if (str == null) {
            throw new RuntimeException("Falta la variable: " + FIELD_FLICKR_PHOTO);
        }
        flickrPhoto = new FlickrPhoto(Json.read(str));
    }

    private void initVars() {
        ItemListViewHolder listViewHolder = new ItemListViewHolder(findViewById(R.id.lista_item));
        listViewHolder.setData(flickrPhoto);
        ((TextView) findViewById(R.id.description)).setText(flickrPhoto.getDescription());
    }

    private void setClicks() {
        findViewById(R.id.btn_atras).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
