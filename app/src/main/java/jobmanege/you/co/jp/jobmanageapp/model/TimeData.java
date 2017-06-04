package jobmanege.you.co.jp.jobmanageapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * TimeDataテーブルモデルクラス
 */
@Table(name = "jobmanageapp")
public class TimeData extends Model {

    public TimeData() {
        super();
    }

    @Column(name = "work_type")
    public String workType;

    @Column(name = "date")
    public String date;

    @Column(name = "start_time")
    public String startTime;

    @Column(name = "end_time")
    public String endTime;
}
