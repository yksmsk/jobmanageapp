package jobmanege.you.co.jp.jobmanageapp.common;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Calendar;

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

    public static String getDayOfTheWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY: return "日";
            case Calendar.MONDAY: return "月";
            case Calendar.TUESDAY: return "火";
            case Calendar.WEDNESDAY: return "水";
            case Calendar.THURSDAY: return "木";
            case Calendar.FRIDAY: return "金";
            case Calendar.SATURDAY: return "土";
        }
        throw new IllegalStateException();
    }

    /**
     * pixelからdpへの変換
     * @param px
     * @param context
     * @return float dp
     */
    public static float px2dp(int px, Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / metrics.density;
    }

    /**
     * dpからpixelへの変換
     * @param dp
     * @param context
     * @return float pixel
     */
    public static float dp2px(float dp, Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * metrics.density;
    }
}
