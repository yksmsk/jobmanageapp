package jobmanege.you.co.jp.jobmanageapp.model;


import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

/**
 * TimeDataテーブルモデルクラス
 */
@Table
public class TimeData {

    public TimeData() {
    }

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String workType;

    @Column(indexed = true)
    public int year;

    @Column(indexed = true)
    public int month;

    @Column(indexed = true)
    public int day;

    @Column
    public String startTime;

    @Column
    public String endTime;

    @Setter
    public TimeData(long id, String workType, int year, int month, int day, String startTime, String endTime) {
        this.id = id;
        this.workType = workType;
        this.year = year;
        this.month = month;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
