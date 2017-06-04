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
import android.widget.DatePicker;

import java.util.Calendar;

import jobmanege.you.co.jp.jobmanageapp.R;
import jobmanege.you.co.jp.jobmanageapp.dialog.InputDialog;

public class CalendarFragment extends Fragment implements DatePicker.OnDateChangedListener {
    /** ログ出力用タグ */
    private static final String TAG = "CalendarFragment";

    private InputDialog dialog;
    private DatePicker datePicker;


    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "%% onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "%% onCreateView");
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), this);

        dialog = InputDialog.newInstance(null);

        return v;
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


    /**
     * Called upon a date change.
     *
     * @param v        The view associated with this listener.
     * @param year        The year that was set.
     * @param monthOfYear The month that was set (0-11) for compatibility
     *                    with {@link Calendar}.
     * @param dayOfMonth  The day of the month that was set.
     */
    @Override
    public void onDateChanged(DatePicker v, int year, int monthOfYear, int dayOfMonth) {
        int id = v.getId();
        switch (id) {
            case R.id.datePicker:
                dialog.show(getActivity().getFragmentManager(), "test");
                Log.d(TAG,"%% onDateChanged datePicker");
                Log.d(TAG, String.format("Y:%4s M:%2s D:%2s", year, monthOfYear+1, dayOfMonth));
                break;
        }

    }
}
