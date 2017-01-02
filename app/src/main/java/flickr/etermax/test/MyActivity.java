package flickr.etermax.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import flickr.etermax.test.UI.LoadingDialog;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class MyActivity extends AppCompatActivity {
    private boolean showingLoading = false;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
    }

    public void showLoading() {
        if (!showingLoading) {
            showingLoading = true;
            loadingDialog.show();
        }
    }

    public void hideLoading() {
        if (showingLoading) {
            showingLoading = false;
            loadingDialog.dismiss();
        }
    }
}
