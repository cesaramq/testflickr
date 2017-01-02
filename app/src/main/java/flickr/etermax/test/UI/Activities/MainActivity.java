package flickr.etermax.test.UI.Activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import flickr.etermax.test.MyActivity;
import flickr.etermax.test.R;

public class MainActivity extends MyActivity {
    private boolean modeGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initVars() {
        initRecycler();
    }

    private void initRecycler() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

    }

    private void setClicks() {

    }
}
