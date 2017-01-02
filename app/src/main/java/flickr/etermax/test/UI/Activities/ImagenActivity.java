package flickr.etermax.test.UI.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.MyActivity;
import flickr.etermax.test.R;
import flickr.etermax.test.Utils.Json;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class ImagenActivity extends MyActivity {
    public static final String FIELD_DATA = "data";
    private FlickrPhoto flickrPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);
        verificarData();
        initVars();
        setClicks();
    }

    private void verificarData() {
        String data = getIntent().getStringExtra(FIELD_DATA);
        if (data == null) {
            throw new RuntimeException("FALTA LA VARIABLE: " + FIELD_DATA);
        }
        flickrPhoto = new FlickrPhoto(Json.read(data));
    }

    private void initVars() {
        Glide.with(this).load(flickrPhoto.getUrlLarge()).into((ImageView) findViewById(R.id.img));
        ((TextView) findViewById(R.id.txt_nombre_user)).setText(flickrPhoto.getOwnerName());
        ((TextView) findViewById(R.id.txt_titulo)).setText(flickrPhoto.getTitle());
    }

    private void setClicks() {
        findViewById(R.id.btn_cerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
