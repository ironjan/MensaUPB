package de.ironjan.mensaupb.persistence;

import android.provider.*;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

/**
 * TODO javadoc
 */
@DatabaseTable(tableName = SynchronizationReport.TABLE)
public class SynchronizationReport {

    public static final String TABLE = "SynchronizationReports";
    public static final String ID = BaseColumns._ID;
    public static final String START_TIME_STAMP = "startTimeStamp";
    public static final String END_TIME_STAMP = "endTimeStamp";
    public static final String DOWNLOADDED = "downloadded";
    public static final String UPDATED = "updated";
    public static final String CREATED = "created";
    public static final String DELETED = "deleted";
    public static final String SUCCESS = "success";

    @DatabaseField(columnName = ID, generatedId = true)
    private long _id;
    @DatabaseField(columnName = START_TIME_STAMP)
    private long startTimeStamp;
    @DatabaseField(columnName = END_TIME_STAMP)
    private long endTimeStamp;
    @DatabaseField(columnName = DOWNLOADDED)
    private int downloadded;
    @DatabaseField(columnName = UPDATED)
    private int updated;
    @DatabaseField(columnName = CREATED)
    private int created;
    @DatabaseField(columnName = DELETED)
    private int deleted;
    @DatabaseField(columnName = SUCCESS)
    private boolean success;

    public SynchronizationReport() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public int getDownloadded() {
        return downloadded;
    }

    public void setDownloadded(int downloadded) {
        this.downloadded = downloadded;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
