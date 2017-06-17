package jobmanege.you.co.jp.jobmanageapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.gfx.android.orma.Inserter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import jobmanege.you.co.jp.jobmanageapp.R;
import jobmanege.you.co.jp.jobmanageapp.common.AppDatabase;
import jobmanege.you.co.jp.jobmanageapp.common.Constant;
import jobmanege.you.co.jp.jobmanageapp.common.Utils;
import jobmanege.you.co.jp.jobmanageapp.model.OrmaDatabase;
import jobmanege.you.co.jp.jobmanageapp.model.TimeData;
import jobmanege.you.co.jp.jobmanageapp.model.TimeData_Selector;

public class InputDialog extends DialogFragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DialogInterface.OnClickListener {
    /**
     * ログ出力用タグ
     */
    private static final String TAG = "InputDialog";

    private Dialog dialog;
    private TextView startTimeText1;
    private TextView endTimeText1;
    private TextView startTimeText2;
    private TextView endTimeText2;

    private int targetYear;
    private int targetMonth;
    private int targetDay;
    private int targetViewId;

    private OrmaDatabase db;
    private TimeData siteData;
    private TimeData officeData;

    private boolean updateFlg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static InputDialog newInstance(Bundle bundle) {
        InputDialog instance = new InputDialog();
        instance.setArguments(bundle);

        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // targetの取得
        Bundle args = getArguments();
        targetYear = args.getInt(Constant.KEY_BUNDLE_TARGET_YEAR);
        targetMonth = args.getInt(Constant.KEY_BUNDLE_TARGET_MONTH);
        targetDay = args.getInt(Constant.KEY_BUNDLE_TARGET_DAY);

        // TODO DBに問い合わせ Backgraundの方がいいかも ***********************************************
        db = AppDatabase.createOrmaInstance(getContext(), Constant.DB_NAME);
        TimeData_Selector selector = db.selectFromTimeData().yearEq(targetYear).monthEq(targetMonth).dayEq(targetDay);
        List<TimeData> dataList = selector.toList();
        Log.d(TAG, "%% list size " + dataList.size());

        for (TimeData data : dataList) {
            if (Constant.DB_TIMEDATA_WORKTYPE_SITE.equals(data.workType)) {
                siteData = data;
            } else if (Constant.DB_TIMEDATA_WORKTYPE_OFFICE.equals(data.workType)) {
                officeData = data;
            }
        }
        // ****************************************************************************************

        // Viewの設定
        LayoutInflater i = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = i.inflate(R.layout.fragment_input_dialog, null);

        // タブ設定
        TabHost tabs = (TabHost) content.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec tab1 = tabs.newTabSpec(getString(R.string.dialog_tab_site));
        tab1.setContent(R.id.tab1);
        tab1.setIndicator(getString(R.string.dialog_tab_site));
        TabHost.TabSpec tab2 = tabs.newTabSpec(getString(R.string.dialog_tab_office));
        tab2.setContent(R.id.tab2);
        tab2.setIndicator(getString(R.string.dialog_tab_office));
        tabs.addTab(tab1);
        tabs.addTab(tab2);
        // Dialogの設定
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(content);
        builder.setPositiveButton(R.string.btn_register, this);
        builder.setNegativeButton(R.string.btn_cancel, null);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        // Buttonの設定
        // 各種ボタンのいべんとりすな設定
        Button btn_now1 = (Button) content.findViewById(R.id.btn_startnow1);
        Button btn_now2 = (Button) content.findViewById(R.id.btn_startnow2);
        Button btn_now3 = (Button) content.findViewById(R.id.btn_endnow1);
        Button btn_now4 = (Button) content.findViewById(R.id.btn_endnow2);

        btn_now1.setOnClickListener(this);
        btn_now2.setOnClickListener(this);
        btn_now3.setOnClickListener(this);
        btn_now4.setOnClickListener(this);

        // TextViewの設定
        startTimeText1 = (TextView) content.findViewById(R.id.dialog_starttime1);
        endTimeText1 = (TextView) content.findViewById(R.id.dialog_endtime1);
        startTimeText2 = (TextView) content.findViewById(R.id.dialog_starttime2);
        endTimeText2 = (TextView) content.findViewById(R.id.dialog_endtime2);

        startTimeText1.setOnClickListener(this);
        endTimeText1.setOnClickListener(this);
        startTimeText2.setOnClickListener(this);
        endTimeText2.setOnClickListener(this);

        if (dataList.size() != 0) {
            updateFlg = true;
            startTimeText1.setText(siteData.startTime);
            endTimeText1.setText(siteData.endTime);
            startTimeText2.setText(officeData.startTime);
            endTimeText2.setText(officeData.endTime);
        } else {
            updateFlg = false;
            startTimeText1.setText(getText(R.string.config_default_time));
            endTimeText1.setText(getText(R.string.config_default_time));
            startTimeText2.setText(getText(R.string.config_default_time));
            endTimeText2.setText(getText(R.string.config_default_time));
        }

        return dialog;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        // 現在時刻を取得
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        int h = now.get(Calendar.HOUR_OF_DAY);
        int m = now.get(Calendar.MINUTE);
        String formatTime = Utils.getRoundTime(h, m);
        switch (id) {
            case R.id.btn_startnow1:
                startTimeText1.setText(formatTime);
                break;
            case R.id.btn_endnow1:
                endTimeText1.setText(formatTime);
                break;
            case R.id.btn_startnow2:
                startTimeText2.setText(formatTime);
                break;
            case R.id.btn_endnow2:
                endTimeText2.setText(formatTime);
                break;
            case R.id.dialog_starttime1:
            case R.id.dialog_endtime1:
            case R.id.dialog_starttime2:
            case R.id.dialog_endtime2:
                targetViewId = id;
                Utils.showTimePickerDialog(getFragmentManager(), v, this);
                break;
        }
    }

    /**
     * Called when the user is done setting a new time and the dialog has
     * closed.
     *
     * @param view      the view associated with this listener
     * @param hourOfDay the hour that was set
     * @param minute    the minute that was set
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(TAG, "%% onTimeSet");
        switch (targetViewId) {
            case R.id.dialog_starttime1:
                startTimeText1.setText(String.format(Constant.FORMAT_TIME_VIEW, hourOfDay, minute));
                break;
            case R.id.dialog_endtime1:
                endTimeText1.setText(String.format(Constant.FORMAT_TIME_VIEW, hourOfDay, minute));
                break;
            case R.id.dialog_starttime2:
                startTimeText2.setText(String.format(Constant.FORMAT_TIME_VIEW, hourOfDay, minute));
                break;
            case R.id.dialog_endtime2:
                endTimeText2.setText(String.format(Constant.FORMAT_TIME_VIEW, hourOfDay, minute));
                break;

        }
    }

    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param dialog The dialog that received the click.
     * @param which  The button that was clicked (e.g.
     *               {@link DialogInterface#BUTTON1}) or the position
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        // PositiveButtonが押下されたときの処理

        // DBに登録
        if (siteData == null && officeData == null) {
            siteData = new TimeData();
            officeData = new TimeData();
        }
        if (updateFlg) {
            db.updateTimeData()
                    .idEq(siteData.id)
                    .startTime(siteData.startTime)
                    .endTime(siteData.endTime)
                    .execute();

            db.updateTimeData()
                    .idEq(officeData.id)
                    .startTime(officeData.startTime)
                    .endTime(officeData.endTime)
                    .execute();
        } else {
            siteData.workType = Constant.DB_TIMEDATA_WORKTYPE_SITE;
            siteData.year = targetYear;
            siteData.month = targetMonth;
            siteData.day = targetDay;
            siteData.startTime = startTimeText1.getText().toString();
            siteData.endTime = endTimeText1.getText().toString();

            officeData.workType = Constant.DB_TIMEDATA_WORKTYPE_OFFICE;
            officeData.year = targetYear;
            officeData.month = targetMonth;
            officeData.day = targetDay;
            officeData.startTime = startTimeText2.getText().toString();
            officeData.endTime = endTimeText2.getText().toString();

            Inserter<TimeData> inserter = db.prepareInsertIntoTimeData();
            inserter.executeAllAsObservable(Arrays.asList(new TimeData[]{siteData, officeData}))
                    .subscribeOn(Schedulers.io())
                    .subscribe();

        }

        dismiss();
    }
}
