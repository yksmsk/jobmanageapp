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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import jobmanege.you.co.jp.jobmanageapp.R;

import static jobmanege.you.co.jp.jobmanageapp.R.id.month;

public class ListViewFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    /**
     * ログ出力用タグ
     */
    private static final String TAG = "ListViewFragment";

    /**
     * monthリスト
     */
    private Integer[] monthItems = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    private Spinner monthSpinner;
    private Spinner yearSpinner;

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
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        // 月のリスト 初期値：Calendarで選択中の月
        ArrayAdapter monthAdapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, monthItems);
        monthAdapter.setDropDownViewResource(R.layout.spinner_item);
        monthSpinner = (Spinner) v.findViewById(month);
        monthSpinner.setAdapter(monthAdapter);

        // 年のリスト 初期値：Calendarで選択中の年
        yearSpinner = (Spinner) v.findViewById(R.id.year);
        // DBに登録されている年 + calendarで選択中の年
//        List<TimeData> items = new Select().from(TimeData.class).execute();



        monthSpinner.setOnItemSelectedListener(this);
        yearSpinner.setOnItemSelectedListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater infrater) {
        super.onCreateOptionsMenu(menu, infrater);
        infrater.inflate(R.menu.main, menu);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "%% onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();
        switch (parentId) {
            case R.id.year:
                Log.d(TAG, "%% year spinner");
                break;
            case R.id.month:
                Log.d(TAG, "%% month spinner");
                break;

        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateView(int calYear, int calMonth) {
        Log.d(TAG, "%% calYear:" + calYear + "calMonth:" + calMonth);
        monthSpinner.setSelection(calMonth);
        // SrolleViewに要素を追加する


    }

}
