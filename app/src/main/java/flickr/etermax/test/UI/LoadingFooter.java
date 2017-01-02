package flickr.etermax.test.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import flickr.etermax.test.R;

/**
 * Created by César Muñoz on 18/08/16.
 */
public class LoadingFooter {
    private View loading;
    private TextView txtEmptyList, txtNoMoreData;
    private boolean isLoading = true, isVisible = true, isError = false;
    private ViewGroup loadingContainer, emptyListContainer, errorContainer, noMoreDataListContainer;
    private View customEmptyView;
    private Context context;

    public LoadingFooter(Context context) {
        this.context = context;
        loading = LayoutInflater.from(context).inflate(R.layout.loading_footer, null);
        loading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        loadingContainer = (ViewGroup) loading.findViewById(R.id.loading_container);
        emptyListContainer = (ViewGroup) loading.findViewById(R.id.empty_data_list_container);
        errorContainer = (ViewGroup) loading.findViewById(R.id.error_container);
        noMoreDataListContainer = (ViewGroup) loading.findViewById(R.id.no_more_data_list_container);
        txtEmptyList = (TextView) loading.findViewById(R.id.txt_empty_list);
        txtNoMoreData = (TextView) loading.findViewById(R.id.txt_no_more_data);
    }

    public void mostrarLoading() {
        if (!isLoading || !isVisible) {
            mostrar();
            isLoading = true;
            isError = false;
            loadingContainer.setVisibility(View.VISIBLE);
            emptyListContainer.setVisibility(View.GONE);
            errorContainer.setVisibility(View.GONE);
            noMoreDataListContainer.setVisibility(View.GONE);
        }
    }

    public void mostrarNoHayDatosParaMostrar() {
        if (isLoading || isError || !isVisible) {
            mostrar();
            isLoading = false;
            isError = false;
            emptyListContainer.setVisibility(View.VISIBLE);
            loadingContainer.setVisibility(View.GONE);
            errorContainer.setVisibility(View.GONE);
            noMoreDataListContainer.setVisibility(View.GONE);
        }
    }

    public void mostrarError(View.OnClickListener click) {
        if (!isError || !isVisible) {
            mostrar();
            isError = true;
            isLoading = false;
            errorContainer.setVisibility(View.VISIBLE);
            loadingContainer.setVisibility(View.GONE);
            emptyListContainer.setVisibility(View.GONE);
            noMoreDataListContainer.setVisibility(View.GONE);
            errorContainer.setOnClickListener(click);
        }
    }

    public void mostrarNoHayMasDatos() {
        if (isLoading || isError || !isVisible) {
            mostrar();
            isLoading = false;
            isError = false;
            noMoreDataListContainer.setVisibility(View.VISIBLE);
            emptyListContainer.setVisibility(View.GONE);
            loadingContainer.setVisibility(View.GONE);
            errorContainer.setVisibility(View.GONE);
        }
    }

    public void ocultar() {
        if (isVisible) {
            isVisible = false;
            loading.setVisibility(View.GONE);
        }
    }

    private void mostrar() {
        if (!isVisible) {
            isVisible = true;
            loading.setVisibility(View.VISIBLE);
        }
    }

    public View getLoading() {
        return loading;
    }

    public void setEmptyListView(int layoutId) {
        emptyListContainer.removeAllViews();
        customEmptyView = LayoutInflater.from(context).inflate(layoutId, emptyListContainer);
    }

    public TextView getTxtEmptyList() {
        return txtEmptyList;
    }

    public TextView getTxtNoMoreData() {
        return txtNoMoreData;
    }

    public View getCustomEmptyView() {
        return customEmptyView;
    }
}
