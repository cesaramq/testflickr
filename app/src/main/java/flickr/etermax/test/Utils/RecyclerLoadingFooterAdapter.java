package flickr.etermax.test.Utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import flickr.etermax.test.UI.LoadingFooter;

/**
 * Created by César Muñoz on 26/08/16.
 */
public abstract class RecyclerLoadingFooterAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = -99;
    private LoadingFooter loadingFooter;

    public RecyclerLoadingFooterAdapter(Context context, final RecyclerView.LayoutManager layoutManager) {
        loadingFooter = new LoadingFooter(context);
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == getItemCount() - 1 ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(loadingFooter.getLoading());
        } else {
            return onActualCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() != TYPE_FOOTER) {
            onActualBindViewHolder((VH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return getActualItemCount() + 1;
    }

    public abstract int getActualItemCount();

    public abstract void onActualBindViewHolder(VH holder, int position);

    public abstract VH onActualCreateViewHolder(ViewGroup parent, int viewType);

    public abstract int getActualItemViewType(int position);

    @Override
    public int getItemViewType(int position) {
        if (isLastItem(position)) {
            return TYPE_FOOTER;
        } else {
            return getActualItemViewType(position);
        }
    }

    private boolean isLastItem(int position) {
        return getActualItemCount() == position;
    }

    public void showLoading() {
        loadingFooter.mostrarLoading();
    }

    public void hideLoading() {
        loadingFooter.ocultar();
    }

    public void showNoMoreData() {
        loadingFooter.mostrarNoHayMasDatos();
    }

    public void showNoData() {
        loadingFooter.mostrarNoHayDatosParaMostrar();
    }

    public void showError(View.OnClickListener click) {
        loadingFooter.mostrarError(click);
    }

    public LoadingFooter getLoadingFooter() {
        return loadingFooter;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
