package flickr.etermax.test.Utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by César Muñoz on 02/08/16.
 */
public class EndlessScrollRecyclerListener extends RecyclerView.OnScrollListener {
    private static final int visibleThreshold = 2;
    private int currentPage = 0;
    private int startPage = 0;
    private boolean listenerEnabled = true;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private boolean otherRecycler = false;
    private RecyclerView.OnScrollListener otherRecyclerScrollListener;
    private OnEndlessScrollRecyclerActions onEndlessScrollRecyclerActions;

    public EndlessScrollRecyclerListener(OnEndlessScrollRecyclerActions
                                                 onEndlessScrollRecyclerActions) {
        this(onEndlessScrollRecyclerActions, 0, true);
    }

    public EndlessScrollRecyclerListener(OnEndlessScrollRecyclerActions
                                                 onEndlessScrollRecyclerActions,
                                         int startPageDefaultZero) {
        this(onEndlessScrollRecyclerActions, startPageDefaultZero, true);
    }

    public EndlessScrollRecyclerListener(OnEndlessScrollRecyclerActions
                                                 onEndlessScrollRecyclerActions,
                                         int startPageDefaultZero, boolean enableListener) {
        this.startPage = startPageDefaultZero;
        currentPage = startPageDefaultZero;
        this.listenerEnabled = enableListener;
        this.onEndlessScrollRecyclerActions = onEndlessScrollRecyclerActions;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (otherRecycler) {
            otherRecyclerScrollListener.onScrollStateChanged(recyclerView, newState);
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (otherRecycler) {
            otherRecyclerScrollListener.onScrolled(recyclerView, dx, dy);
        }
        if (listenerEnabled) {
            RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
            int firstVisibleItem;
            int totalItemCount = lm.getItemCount();
            if (lm instanceof StaggeredGridLayoutManager) {
                firstVisibleItem = ((StaggeredGridLayoutManager) lm)
                        .findFirstVisibleItemPositions(null)[0];
            } else {
                firstVisibleItem = ((LinearLayoutManager) lm).findFirstVisibleItemPosition();
            }
            int visibleItemCount = lm.getChildCount();
            int lasItem = firstVisibleItem + visibleItemCount;
            if (loading) {
                if (previousTotalItemCount < totalItemCount) {
                    previousTotalItemCount = totalItemCount;
                    currentPage++;
                    loading = false;
                }
            }
            if (!loading) {
                if (lasItem + visibleThreshold >= totalItemCount) {
                    loading = true;
                    onLoadMore(currentPage, totalItemCount);
                }
            }
        }
    }

    public void resetEndlessScrolling() {
        currentPage = startPage;
        previousTotalItemCount = 0;
    }

    public void onLoadMore(int page, int totalItemsCount) {
        onEndlessScrollRecyclerActions.onLoadMore(page, totalItemsCount);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public interface OnEndlessScrollRecyclerActions {
        void onLoadMore(int page, int totalItemsCounr);
    }

    public void setOtherRecyclerScrollListener(RecyclerView.OnScrollListener
                                                       otherRecyclerScrollListener) {
        this.otherRecyclerScrollListener = otherRecyclerScrollListener;
        otherRecycler = otherRecyclerScrollListener != null;
    }

    public void setListenerEnabled(boolean listenerEnabled) {
        this.listenerEnabled = listenerEnabled;
    }
}
