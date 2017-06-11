package jobmanege.you.co.jp.jobmanageapp.common;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import jobmanege.you.co.jp.jobmanageapp.dialog.TimePickDialog;

/**
 * Utilくらす
 */

public class Utils {

    /**
     * Enum:丸め時間
     */
    public enum RoundMinuts {
        M00(0),
        M15(15),
        M30(30),
        M45(45);
        private int timeNum;

        RoundMinuts(int timeNum) {
            this.timeNum = timeNum;
        }

        public int getTime() {
            return timeNum;
        }
    }

    public enum ThresholdMinuts {
        M8(8), M23(23), M38(38), M53(53);

        private int timeNum;

        ThresholdMinuts(int timeNum) {
            this.timeNum = timeNum;
        }

        public int getTime() {
            return timeNum;
        }
    }

    /**
     * TimePickerDialogを表示する.
     *
     * @param fm fragmentManager
     * @param v  view
     */
    public static void showTimePickerDialog(FragmentManager fm, View v) {
        showTimePickerDialog(fm, v, null);
    }

    /**
     * TimePickerDialogを表示する.(Fragment用)
     *
     * @param fm fragmentManager
     * @param v  view
     */
    public static void showTimePickerDialog(FragmentManager fm, View v, Fragment targetFragment) {

        DialogFragment timePickDialog = new TimePickDialog();
        timePickDialog.setTargetFragment(targetFragment, v.getId());
        Bundle args = new Bundle();
        args.putInt(Constant.KEY_BUNDLE_TARGET_ID, v.getId());
        timePickDialog.setArguments(args);

        timePickDialog.show(fm, "timePicker");

    }

    /**
     * 時、分を受け取って丸めた時間を返却する.
     *
     * @param h 時
     * @param m 分
     */
    public static String getRoundTime(int h, int m) {
        int roundH;
        int roundM;

        if (ThresholdMinuts.M8.getTime() <= m && m < ThresholdMinuts.M23.getTime()) {
            roundH = h;
            roundM = RoundMinuts.M15.getTime();
        } else if (ThresholdMinuts.M23.getTime() <= m && m < ThresholdMinuts.M38.getTime()) {
            roundH = h;
            roundM = RoundMinuts.M30.getTime();
        } else if (ThresholdMinuts.M38.getTime() <= m && m < ThresholdMinuts.M53.getTime()) {
            roundH = h;
            roundM = RoundMinuts.M45.getTime();
        } else {
            roundH = (ThresholdMinuts.M53.getTime() <= m) ? h + 1 : h;
            roundM = RoundMinuts.M00.getTime();
        }

        return String.format(Constant.FORMAT_TIME_VIEW, roundH, roundM);
    }
}
