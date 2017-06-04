package jobmanege.you.co.jp.jobmanageapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;

import jobmanege.you.co.jp.jobmanageapp.R;
import jobmanege.you.co.jp.jobmanageapp.adapter.AppFragmentStatePageAdapter;
import jobmanege.you.co.jp.jobmanageapp.fragment.CalendarFragment;
import jobmanege.you.co.jp.jobmanageapp.fragment.ListViewFragment;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    /**
     * ログ出力用タグ
     */
    private static final String TAG = "MainACtivity";

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        AppFragmentStatePageAdapter adapter = new AppFragmentStatePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "%% onOptionsItemSelected");
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_conf:
                Log.d(TAG, "%% conf");
                Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "%% onPageSelected");
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {


        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                Log.d(TAG, "%% onPageScrollStateChanged IDLE:");
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                Log.d(TAG, "%% onPageScrollStateChanged DRAGGING");
                updateDate();
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                Log.d(TAG, "%% onPageScrollStateChanged SETTLING");
                break;
        }

    }

    private void updateDate() {
        AppFragmentStatePageAdapter adapter = (AppFragmentStatePageAdapter) viewPager.getAdapter();
        CalendarFragment calFragment = (CalendarFragment) adapter.findFragmentByPosition(viewPager, 0);
        ListViewFragment listViewFragment = (ListViewFragment) adapter.findFragmentByPosition(viewPager, 1);

        DatePicker datePicker = (DatePicker) calFragment.getActivity().findViewById(R.id.datePicker);

        listViewFragment.viewUpdate(datePicker.getYear(), datePicker.getMonth());
    }
}
