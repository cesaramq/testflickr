package flickr.etermax.test.Utils;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import com.rey.material.widget.EditText;

/**
 * Created by César Muñoz on 05/09/16.
 */
public class EdittextSearchOnChange implements TextWatcher {
    private Handler handler = new Handler();
    private OnEdittextSearchActions onEdittextSearchActions;
    private String lastString = "";
    private boolean watcherEnabled = true;
    private static final int TIME_DELAY_MS = 800;

    public EdittextSearchOnChange(OnEdittextSearchActions onEdittextSearchActions,
                                  EditText editText) {
        this.onEdittextSearchActions = onEdittextSearchActions;
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (watcherEnabled) {
            lastString = s.toString();
            handler.removeCallbacks(onTextSearchAction);
            handler.postDelayed(onTextSearchAction, TIME_DELAY_MS);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private Runnable onTextSearchAction = new Runnable() {
        @Override
        public void run() {
            searchText();
        }
    };

    private void searchText() {
        onEdittextSearchActions.onEdittextSearch(lastString);
    }

    public interface OnEdittextSearchActions {
        void onEdittextSearch(String str);
    }

    public void setWatcherEnabled(boolean watcherEnabled) {
        this.watcherEnabled = watcherEnabled;
    }
}
