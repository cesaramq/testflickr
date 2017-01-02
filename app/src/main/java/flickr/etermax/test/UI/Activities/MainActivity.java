package flickr.etermax.test.UI.Activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rey.material.widget.EditText;
import com.rey.material.widget.ImageView;

import java.util.ArrayList;

import flickr.etermax.test.Adapters.PhotosAdapter;
import flickr.etermax.test.Managers.FlickrApiManager;
import flickr.etermax.test.Managers.FlickrApiManagerHelper;
import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.MyActivity;
import flickr.etermax.test.R;
import flickr.etermax.test.Utils.EdittextSearchOnChange;
import flickr.etermax.test.Utils.EndlessScrollRecyclerListener;

public class MainActivity extends MyActivity implements
        EndlessScrollRecyclerListener.OnEndlessScrollRecyclerActions,
        FlickrApiManagerHelper.GetFlickrPhotosList, EdittextSearchOnChange.OnEdittextSearchActions {
    private boolean modeGrid = true;
    private PhotosAdapter adapter;
    private RecyclerView recycler;
    private FlickrApiManager manager;
    private GridLayoutManager gridLayoutManager;
    private ImageView btnSwitch;
    private String search = "";
    private EdittextSearchOnChange edittextSearchOnChange;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessScrollRecyclerListener endlessScrollRecyclerListener;
    private static final int PER_PAGE = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVars();
        setClicks();
        pedirPhotos(false);
    }

    private void initVars() {
        btnSwitch = (ImageView) findViewById(R.id.btn_switch_list);
        manager = new FlickrApiManager(this);
        edittextSearchOnChange = new EdittextSearchOnChange(this,
                ((EditText) findViewById(R.id.edt_busqueda)));
        initRecycler();
    }

    private void pedirPhotos(boolean refreshing) {
        if (!refreshing) {
            adapter.showLoading();
        } else {
            adapter.hideLoading();
        }
        manager.cancelRequest();
        manager.getPublicPhotosList(search, endlessScrollRecyclerListener.getCurrentPage(),
                PER_PAGE, this);
    }

    private void buscarPhotos(String search) {
        if (!this.search.equals(search)) {
            this.search = search;
            resetList();
            pedirPhotos(false);
        }
    }

    private void resetList() {
        adapter.limpiar();
        endlessScrollRecyclerListener.setListenerEnabled(false);
        endlessScrollRecyclerListener.resetEndlessScrolling();
    }

    private void initRecycler() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        endlessScrollRecyclerListener = new EndlessScrollRecyclerListener(this, 1);
        endlessScrollRecyclerListener.setListenerEnabled(false);
        recycler.addOnScrollListener(endlessScrollRecyclerListener);
        gridLayoutManager = new GridLayoutManager(this, 3);
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new PhotosAdapter(this, gridLayoutManager, recycler);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(adapter);
        swipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetList();
                pedirPhotos(true);
            }
        });
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
    public void onLoadMore(int page, int totalItemsCount) {
        pedirPhotos(false);
    }

    @Override
    public void onGetFlickrPhotosListSuccess(ArrayList<FlickrPhoto> flickrPhotos) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.add(flickrPhotos);
        endlessScrollRecyclerListener.setListenerEnabled(true);
    }

    @Override
    public void onGetFlickrPhotosListError(Error error) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPhotos(false);
            }
        });
    }

    @Override
    public void onEdittextSearch(String str) {
        buscarPhotos(str);
    }
}
