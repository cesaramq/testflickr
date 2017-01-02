package flickr.etermax.test.UI.Activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rey.material.widget.ImageView;

import java.util.ArrayList;

import flickr.etermax.test.Adapters.PhotosAdapter;
import flickr.etermax.test.Managers.FlickrApiManager;
import flickr.etermax.test.Managers.FlickrApiManagerHelper;
import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.MyActivity;
import flickr.etermax.test.R;
import flickr.etermax.test.Utils.EndlessScrollRecyclerListener;

public class MainActivity extends MyActivity implements
        EndlessScrollRecyclerListener.OnEndlessScrollRecyclerActions,
        FlickrApiManagerHelper.GetFlickrPhotosList {
    private boolean modeGrid = true;
    private PhotosAdapter adapter;
    private RecyclerView recycler;
    private FlickrApiManager manager;
    private GridLayoutManager gridLayoutManager;
    private ImageView btnSwitch;
    private LinearLayoutManager linearLayoutManager;
    private EndlessScrollRecyclerListener endlessScrollRecyclerListener;
    private static final int PER_PAGE = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVars();
        setClicks();
        pedirPhotos();
    }

    private void initVars() {
        btnSwitch = (ImageView) findViewById(R.id.btn_switch_list);
        manager = new FlickrApiManager(this);
        initRecycler();
    }

    private void pedirPhotos() {
        adapter.showLoading();
        manager.getPublicPhotosList("", endlessScrollRecyclerListener.getCurrentPage(),
                PER_PAGE, this);
    }

    private void initRecycler() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        endlessScrollRecyclerListener = new EndlessScrollRecyclerListener(this, 1);
        gridLayoutManager = new GridLayoutManager(this, 3);
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new PhotosAdapter(this, gridLayoutManager, recycler);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(adapter);
    }

    private void swithMode() {
        modeGrid = !modeGrid;
        if (modeGrid) {
            recycler.setLayoutManager(gridLayoutManager);
            btnSwitch.setImageResource(R.drawable.list);
        } else {
            recycler.setLayoutManager(linearLayoutManager);
            btnSwitch.setImageResource(R.drawable.grid);
        }
        adapter.setGrid(modeGrid);
    }

    private void setClicks() {
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swithMode();
            }
        });
    }

    @Override
    public void onLoadMore(int page, int totalItemsCounr) {

    }

    @Override
    public void onGetFlickrPhotosListSuccess(ArrayList<FlickrPhoto> flickrPhotos) {
        adapter.add(flickrPhotos);
    }

    @Override
    public void onGetFlickrPhotosListError(Error error) {
        adapter.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPhotos();
            }
        });
    }
}
