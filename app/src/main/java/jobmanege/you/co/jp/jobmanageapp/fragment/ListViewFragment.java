package jobmanege.you.co.jp.jobmanageapp.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jobmanege.you.co.jp.jobmanageapp.R;
import jobmanege.you.co.jp.jobmanageapp.common.AppDatabase;
import jobmanege.you.co.jp.jobmanageapp.common.Constant;
import jobmanege.you.co.jp.jobmanageapp.common.Utils;
import jobmanege.you.co.jp.jobmanageapp.model.OrmaDatabase;
import jobmanege.you.co.jp.jobmanageapp.model.TimeData;
import jobmanege.you.co.jp.jobmanageapp.model.TimeData_Selector;

import static jobmanege.you.co.jp.jobmanageapp.R.id.month;
import static jobmanege.you.co.jp.jobmanageapp.R.id.year;

public class ListViewFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    /**
     * ログ出力用タグ
     */
    private static final String TAG = "ListViewFragment";

    /** 初回起動フラグ */
    private boolean isFirst = true;

    /**
     * monthリスト
     */
    private Integer[] monthItems = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private LinearLayout scrollBody;

    private OrmaDatabase db;

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
        scrollBody = (LinearLayout) v.findViewById(R.id.scroll_body);
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.list_view_body);
        // navigation bar のサイズを取得しfooter_spaceにせっとする
        Point real = getRealSize();
        Point disp = getDispSize();
        int h = (real.y - disp.y);
        View space = v.findViewById(R.id.footer_space);
        ViewGroup.LayoutParams params = space.getLayoutParams();
        params.height = (int)(h * 1.5);
        space.setLayoutParams(params);

        // 月のリスト 初期値：Calendarで選択中の月
        ArrayAdapter monthAdapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, monthItems);
        monthAdapter.setDropDownViewResource(R.layout.spinner_item);
        monthSpinner = (Spinner) v.findViewById(month);
        monthSpinner.setAdapter(monthAdapter);

        // 年のリスト 初期値：Calendarで選択中の年
        // DBに登録されている年 + calendarで選択中の年
        db = AppDatabase.createOrmaInstance(getContext(), Constant.DB_NAME);
        List<TimeData> dataList = db.selectFromTimeData().toList();
        Set<Integer> yearSet = new HashSet<>();
        // TODO かれんだーで選択中の年をsetしたい
        yearSet.add(2017);
        for (TimeData data : dataList) {
            yearSet.add(data.year);
        }

        Integer[] yearItems = new Integer[yearSet.size()];
        yearSet.toArray(yearItems);

        ArrayAdapter yearAdapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, yearItems);
        yearAdapter.setDropDownViewResource(R.layout.spinner_item);
        yearSpinner = (Spinner) v.findViewById(year);
        yearSpinner.setAdapter(yearAdapter);

        monthSpinner.setFocusable(false);
        yearSpinner.setFocusable(false);

        monthSpinner.setOnItemSelectedListener(this);
        yearSpinner.setOnItemSelectedListener(this);

        isFirst = false;

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
        Spinner selectSpinner = (Spinner) parent;
        int item =  (Integer)selectSpinner.getSelectedItem();
        Log.d(TAG, "%%select item is " + item);

        // 初回起動時は何もしない
        if(isFirst){
            return;
        }

        switch (parentId) {
            case year:
                Log.d(TAG, "%% year spinner");
//                updateView(item, 0);
                break;
            case R.id.month:
                Log.d(TAG, "%% month spinner");
                updateView(2017, item-1);
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

    /**
     * Viewの描画更新.
     *
     * @param calYear  Caendarで選択中の年
     * @param calMonth Calendarで選択中の月
     */
    public void updateView(int calYear, int calMonth) {
        Log.d(TAG, "%% calYear:" + calYear + "calMonth:" + calMonth);
        // 月のSpinner要素を更新
        monthSpinner.setSelection(calMonth);

        // DBに問合せ
        TimeData_Selector selector = db.relationOfTimeData().selector().yearEq(calYear).monthEq(calMonth);
        List<TimeData> dataList = selector.toList();

        // SrolleViewに要素を追加する
        Calendar cal = new GregorianCalendar(calYear, calMonth, 1);
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        View child;

        // 追加前に子Viewの全削除
        scrollBody.removeAllViews();
        for (int i = 0; i < days; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            View view = getActivity().getLayoutInflater().inflate(R.layout.list_row_item, scrollBody);

            // 子要素の取得と値の設定
            child = scrollBody.getChildAt(i);
            TextView itemDay = (TextView) child.findViewById(R.id.item_day);
            itemDay.setText(String.format(getString(R.string.scroll_list_item_day), i + 1));
            TextView itemWeek = (TextView) child.findViewById(R.id.item_week);
            itemWeek.setText(String.format(getString(R.string.scroll_list_item_week), Utils.getDayOfTheWeek(cal.get(Calendar.DAY_OF_WEEK))));

        }
        child = null;

        for (TimeData data : dataList) {
            // 0始まりなので-1
            child = scrollBody.getChildAt(data.day - 1);
            TextView start;
            TextView end;
            switch (data.workType) {
                case Constant.DB_TIMEDATA_WORKTYPE_SITE:
                    start = (TextView) child.findViewById(R.id.item_site_start);
                    end = (TextView) child.findViewById(R.id.item_site_end);

                    break;
                case Constant.DB_TIMEDATA_WORKTYPE_OFFICE:
                    start = (TextView) child.findViewById(R.id.item_office_start);
                    end = (TextView) child.findViewById(R.id.item_office_end);

                    break;
                default:
                    start = new TextView(getContext());
                    end = new TextView(getContext());
            }

            setWorkTime(start, end, data);

        }

        View listView = getActivity().findViewById(R.id.list_view_body);
        Point real = getRealSize();
        Log.d(TAG, "%% updateView#real.x " + real.x);
        Log.d(TAG, "%% convert value px2dp " + Utils.px2dp(real.x, getContext()));
        listView.setScaleX(real.x/listView.getX());
        Log.d(TAG, "%% listview translation X is " + listView.getTranslationX());
        listView.setTranslationX(-50f);

    }

    /**
     * 稼働時間をViewに設定する.
     *
     * @param start 始業時間のView
     * @param end   終業時間のView
     * @param data  TimeDataオブジェクト
     */
    private void setWorkTime(TextView start, TextView end, TimeData data) {
        start.setText(data.startTime);
        end.setText(data.endTime);
    }

    /**
     * ハードウェアサイズを取得する
     *
     * @return ハードウェアサイズ
     */
    private Point getRealSize() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point real = new Point(0, 0);

        display.getRealSize(real);

        return real;
    }

    /**
     * ディスプレイサイズをしゅとくする
     * @return ディスプレイサイズ
     */
    private Point getDispSize(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point disp = new Point();

        display.getSize(disp);
        return disp;
    }

    /**
     * 稼働時間の合計を計算する
     */
    private void calcTotalTime(){

    }

}
