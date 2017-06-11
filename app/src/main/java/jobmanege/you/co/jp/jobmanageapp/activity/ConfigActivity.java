package jobmanege.you.co.jp.jobmanageapp.activity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import jobmanege.you.co.jp.jobmanageapp.R;
import jobmanege.you.co.jp.jobmanageapp.common.Constant;
import jobmanege.you.co.jp.jobmanageapp.common.Utils;

public class ConfigActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TimePickerDialog.OnTimeSetListener {

    /** ログ出力用タグ */
    private static final String TAG = "ConfigActivity";


    private GridLayout timeLayout;
    private Switch noticeSwitch;
    private TextView startTimeView;
    private TextView endTimeView;

    private int targetViewId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button registBtn = (Button) findViewById(R.id.register);
        Button cancelBtn = (Button) findViewById(R.id.cancel);

        startTimeView = (TextView) findViewById(R.id.starttime_text);
        endTimeView = (TextView) findViewById(R.id.endtime_text);

        registBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        startTimeView.setOnClickListener(this);
        endTimeView.setOnClickListener(this);

        noticeSwitch = (Switch) findViewById(R.id.notice_switch);
        noticeSwitch.setOnCheckedChangeListener(this);

        timeLayout = (GridLayout) findViewById(R.id.time_layout);
        // noticeSwitchの状態をSharedPreferencesからしゅとくする
        SharedPreferences dataStore = getSharedPreferences("DataStore", MODE_PRIVATE);
        boolean noticeSwitchVal = dataStore.getBoolean(Constant.KEY_NOTICE_SWITCH, false);
        noticeSwitch.setChecked(noticeSwitchVal);

        int sh = dataStore.getInt(Constant.KEY_START_TIME_H, 0);
        int sm = dataStore.getInt(Constant.KEY_START_TIME_M, 0);
        int eh = dataStore.getInt(Constant.KEY_END_TIME_H, 0);
        int em = dataStore.getInt(Constant.KEY_END_TIME_M, 0);
        startTimeView.setText(String.format(Constant.FORMAT_TIME_VIEW, sh, sm));
        endTimeView.setText(String.format(Constant.FORMAT_TIME_VIEW, eh, em));

        if(noticeSwitchVal) {
            timeLayout.setVisibility(View.VISIBLE);
        } else {
            timeLayout.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

       switch (id){
            case R.id.cancel:
                Log.d(TAG, "cancel");
                finish();
                break;
            case R.id.register:
                Log.d(TAG, "register");
                registerConfig();
                finish();
                break;
           case R.id.starttime_text:
           case R.id.endtime_text:
               targetViewId = id;
               Utils.showTimePickerDialog(getFragmentManager(), v);
               break;
        }
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "%% notice switch is :" + isChecked);
        // switch の状態はここでしか取得できないので切り替えが発生したタイミングで保存する
        SharedPreferences dataStore = getSharedPreferences("DataStore", MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();
        editor.putBoolean(Constant.KEY_NOTICE_SWITCH, isChecked);
        editor.commit();

        if(isChecked){
            timeLayout.setVisibility(View.VISIBLE);
        } else {
            timeLayout.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 設定項目を保存する.
     */
    private void registerConfig(){
        // 設定項目の状態取得
        Log.d(TAG, "%% registerConfig");

        String start = startTimeView.getText().toString();
        String end = endTimeView.getText().toString();

        String[] startTime = start.split(Constant.SEPARATOR_TIME_VIEW);
        String[] endTime = end.split(Constant.SEPARATOR_TIME_VIEW);

        SharedPreferences dataStore = getSharedPreferences("DataStore", MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();

        editor.putInt(Constant.KEY_START_TIME_H, Integer.parseInt(startTime[0]));
        editor.putInt(Constant.KEY_START_TIME_M, Integer.parseInt(startTime[1]));
        editor.putInt(Constant.KEY_END_TIME_H,   Integer.parseInt(endTime[0]));
        editor.putInt(Constant.KEY_END_TIME_M,   Integer.parseInt(endTime[1]));

        editor.commit();

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
        switch(targetViewId){
            case R.id.starttime_text:
                startTimeView.setText(String.format(Constant.FORMAT_TIME_VIEW, hourOfDay, minute));
                break;
            case R.id.endtime_text:
                endTimeView.setText(String.format(Constant.FORMAT_TIME_VIEW, hourOfDay, minute));
                break;

        }
    }
}
