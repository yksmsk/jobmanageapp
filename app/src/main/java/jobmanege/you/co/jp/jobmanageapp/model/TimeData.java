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
    public String date;

    @Column
    public String startTime;

    @Column
    public String endTime;

    @Setter
    public TimeData(long id, String workType, String date, String startTime, String endTime) {
        this.id = id;
        this.workType = workType;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeData_Relation relation(OrmaDatabase db){
        return db.relationOfTimeData().orderByIdAsc();
    }

}
