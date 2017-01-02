package flickr.etermax.test.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import flickr.etermax.test.Models.FlickrPhoto;
import flickr.etermax.test.UI.ViewHolders.ItemGridViewHolder;
import flickr.etermax.test.UI.ViewHolders.ItemListViewHolder;
import flickr.etermax.test.Utils.RecyclerLoadingFooterAdapter;

/**
 * Created by César Muñoz on 02/01/17.
 */
public class PhotosAdapter extends RecyclerLoadingFooterAdapter<RecyclerView.ViewHolder> {
    private ArrayList<FlickrPhoto> data = new ArrayList<>();
    private static final int TIPO_LISTA = 1, TIPO_GRID = 2;
    private boolean grid = true;
    private RecyclerView recyclerView;

    public PhotosAdapter(Context context, RecyclerView.LayoutManager layoutManager,
                         RecyclerView recyclerView) {
        super(context, layoutManager);
        this.recyclerView = recyclerView;
    }

    @Override
    public int getActualItemCount() {
        return data.size();
    }

    @Override
    public void onActualBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TIPO_GRID:
                ((ItemGridViewHolder) holder).setData(data.get(position));
                break;
            case TIPO_LISTA:
                ((ItemListViewHolder) holder).setData(data.get(position));
                break;
            default:
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onActualCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TIPO_GRID:
                return ItemGridViewHolder.getInstancia(parent);
            case TIPO_LISTA:
                return ItemListViewHolder.getInstancia(parent);
        }
        return null;
    }

    public void add(ArrayList<FlickrPhoto> datos) {
        hideLoading();
        if (!datos.isEmpty()) {
            int start = data.size();
            data.addAll(datos);
            notifyItemRangeInserted(start, datos.size());
            if (start == 0) {
                recyclerView.scrollToPosition(0);
            }
        } else {
            if (data.isEmpty()) {
                showNoData();
            } else {
                showNoMoreData();
            }
        }
    }

    public void limpiar() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getActualItemViewType(int position) {
        if (grid) {
            return TIPO_GRID;
        } else {
            return TIPO_LISTA;
        }
    }

    public void setGrid(boolean grid) {
        if (grid != this.grid) {
            this.grid = grid;
            notifyDataSetChanged();
        }
    }
}
