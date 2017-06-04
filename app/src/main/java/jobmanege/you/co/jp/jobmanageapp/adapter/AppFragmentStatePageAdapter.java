package jobmanege.you.co.jp.jobmanageapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import jobmanege.you.co.jp.jobmanageapp.fragment.CalendarFragment;
import jobmanege.you.co.jp.jobmanageapp.fragment.ListViewFragment;


public class AppFragmentStatePageAdapter extends FragmentStatePagerAdapter {

    public AppFragmentStatePageAdapter(FragmentManager fm){
        super(fm);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position 画面位置
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return ListViewFragment.newInstance();
            default:
            case 0:
                return CalendarFragment.newInstance();

        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "" + position;
    }

    public Fragment findFragmentByPosition(ViewPager viewPager, int position) {
        return (Fragment) instantiateItem(viewPager, position);
    }
}
