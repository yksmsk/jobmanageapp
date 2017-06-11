package jobmanege.you.co.jp.jobmanageapp.common;

import android.content.Context;

import com.github.gfx.android.orma.AccessThreadConstraint;

import javax.inject.Singleton;

import jobmanege.you.co.jp.jobmanageapp.BuildConfig;
import jobmanege.you.co.jp.jobmanageapp.model.OrmaDatabase;

/**
 * DBの処理周りクラス
 */

public class AppDatabase {


    @Singleton
    public static OrmaDatabase createOrmaInstance(Context context, String name) {
        return OrmaDatabase.builder(context)
                .name(name)
                .readOnMainThread(AccessThreadConstraint.NONE)
                .writeOnMainThread(BuildConfig.DEBUG ? AccessThreadConstraint.WARNING : AccessThreadConstraint.NONE)
                .build();
    }
}
