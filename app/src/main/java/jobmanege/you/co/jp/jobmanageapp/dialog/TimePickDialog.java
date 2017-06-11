package jobmanege.you.co.jp.jobmanageapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

import jobmanege.you.co.jp.jobmanageapp.R;
import jobmanege.you.co.jp.jobmanageapp.activity.ConfigActivity;
import jobmanege.you.co.jp.jobmanageapp.common.Constant;

/**
 * TimePickerDialog定義クラス
 */

public class TimePickDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    /** ログ出力用タグ */
    private static final String TAG = "TimePickDialog";

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

    }

    @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        int targetId = getArguments().getInt(Constant.KEY_BUNDLE_TARGET_ID);

        switch(targetId){
            case R.id.starttime_text:
            case R.id.endtime_text:
                Log.d(TAG, "config");
                return new TimePickerDialog(getActivity(), (ConfigActivity)getActivity(), h, m, true);
            case R.id.dialog_starttime1:
            case R.id.dialog_endtime1:
            case R.id.dialog_starttime2:
            case R.id.dialog_endtime2:
                Log.d(TAG, "dialog");
                return new TimePickerDialog(getActivity(), (InputDialog) getTargetFragment(), h, m, true);
        }
        return null;
    }
}
