package jobmanege.you.co.jp.jobmanageapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import jobmanege.you.co.jp.jobmanageapp.R;
import jobmanege.you.co.jp.jobmanageapp.fragment.AppFragmentStatePageAdapter;

public class MainActivity extends FragmentActivity {

    /** ログ出力用タグ */
    private static final String TAG = "MainACtivity";

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new AppFragmentStatePageAdapter(getSupportFragmentManager()));


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "%% onOptionsItemSelected");
        int id = item.getItemId();
        switch(id){
            case R.id.menu_conf:
                Log.d(TAG, "%% conf");
                Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
