package jobmanege.you.co.jp.jobmanageapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import jobmanege.you.co.jp.jobmanageapp.R;

public class ListViewFragment extends Fragment {

    /** ログ出力用タグ */
    private static final String TAG = "ListViewFragment";


    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater infrater) {
        super.onCreateOptionsMenu(menu, infrater);
        infrater.inflate(R.menu.main, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
