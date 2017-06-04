package jobmanege.you.co.jp.jobmanageapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

import jobmanege.you.co.jp.jobmanageapp.activity.ConfigActivity;

/**
 * Created by you on 2017/06/02.
 */

public class TimePickDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

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

        return new TimePickerDialog(getActivity(), (ConfigActivity)getActivity(), h, m, true);
    }
}
