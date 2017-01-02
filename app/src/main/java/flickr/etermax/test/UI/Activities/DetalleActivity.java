package flickr.etermax.test.UI.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import flickr.etermax.test.MyActivity;
import flickr.etermax.test.R;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class DetalleActivity extends MyActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
    }
}
