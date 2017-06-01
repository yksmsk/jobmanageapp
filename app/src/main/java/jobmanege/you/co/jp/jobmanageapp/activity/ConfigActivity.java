package jobmanege.you.co.jp.jobmanageapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.Switch;

import jobmanege.you.co.jp.jobmanageapp.R;

public class ConfigActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    /** ログ出力用タグ */
    private static final String TAG = "ConfigActivity";

    private GridLayout timeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button registBtn = (Button) findViewById(R.id.register);
        Button cancelBtn = (Button) findViewById(R.id.cancel);

        registBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        Switch noticeSwitch = (Switch) findViewById(R.id.notice_switch);
        noticeSwitch.setOnCheckedChangeListener(this);

        timeLayout = (GridLayout) findViewById(R.id.time_layout);
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
                finish();
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
        if(isChecked){
            timeLayout.setVisibility(View.VISIBLE);
        } else {
            timeLayout.setVisibility(View.INVISIBLE);
        }
    }
}
