package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities;
import com.bantulogic.core.watermetrereader.Helpers.Converters;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        indices = @Index(
                value = "metre_id",
                unique = true
        ),
        foreignKeys = @ForeignKey(
                entity = MetreAccount.class,
                parentColumns = "metre_id",
                childColumns = "metre_id",
                onDelete = CASCADE,
                onUpdate = CASCADE
        )
)
@TypeConverters(Converters.class)
public class MetreReading {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "reading_id")
    private String mReadingId;
    @ColumnInfo(name = "metre_id")
    private long mMetreId;
    @ColumnInfo(name = "date_of_reading")
    private Date mDateOfReading;
    @ColumnInfo(name = "start_reading")
    private int mStartReading;
    @ColumnInfo(name = "end_reading")
    private int mEndReading;
    @ColumnInfo(name = "consumption")
    private int mConsumption;
    @ColumnInfo(name = "reading_gps_coordinates")
    private String mReadingGpsCoordinates;
    @ColumnInfo(name = "reading_gps_latitude")
    private double mReadingGpsLatitude;
    @ColumnInfo(name = "reading_gps_longitude")
    private double mReadingGpsLongitude;
    @ColumnInfo(name = "reading_recorded_by")
    private String mReadingRecordedBy;
    @ColumnInfo(name = "time_reading_recorded")
    private Date mTimeReadingRecorded;

    public MetreReading(
            @NonNull String readingId,
                    long metreId,
                    Date dateOfReading,
                    int startReading,
                    int endReading,
                    int consumption,
                    String readingGpsCoordinates,
                    double readingGpsLatitude,
                    double readingGpsLongitude,
                    String readingRecordedBy,
                    Date timeReadingRecorded
    ){
        this.mReadingId = readingId;
        this.mMetreId = metreId;
        this.mDateOfReading = dateOfReading;
        this.mStartReading = startReading;
        this.mEndReading = endReading;
        this.mConsumption = consumption;
        this.mReadingGpsCoordinates = readingGpsCoordinates;
        this.mReadingGpsLatitude = readingGpsLatitude;
        this.mReadingGpsLongitude = readingGpsLongitude;
        this.mReadingRecordedBy = readingRecordedBy;
        this.mTimeReadingRecorded = timeReadingRecorded;

    }
//region GETTERS AND SETTERS
    public String getReadingId() {
        return mReadingId;
    }

    public void setReadingId(String readingId) {
        mReadingId = readingId;
    }

    public long getMetreId() {
        return mMetreId;
    }

    public void setMetreId(long metreId) {
        mMetreId = metreId;
    }

    public Date getDateOfReading() {
        return mDateOfReading;
    }

    public void setDateOfReading(Date dateOfReading) {
        mDateOfReading = dateOfReading;
    }

    public int getStartReading() {
        return mStartReading;
    }

    public void setStartReading(int startReading) {
        mStartReading = startReading;
    }

    public int getEndReading() {
        return mEndReading;
    }

    public void setEndReading(int endReading) {
        mEndReading = endReading;
    }

    public int getConsumption() {
        return mConsumption;
    }

    public void setConsumption(int consumption) {
        mConsumption = consumption;
    }

    public String getReadingGpsCoordinates() {
        return mReadingGpsCoordinates;
    }

    public void setReadingGpsCoordinates(String readingGpsCoordinates) {
        mReadingGpsCoordinates = readingGpsCoordinates;
    }

    public double getReadingGpsLatitude() {
        return mReadingGpsLatitude;
    }

    public void setReadingGpsLatitude(double readingGpsLatitude) {
        mReadingGpsLatitude = readingGpsLatitude;
    }

    public double getReadingGpsLongitude() {
        return mReadingGpsLongitude;
    }

    public void setReadingGpsLongitude(double readingGpsLongitude) {
        mReadingGpsLongitude = readingGpsLongitude;
    }

    public String getReadingRecordedBy() {
        return mReadingRecordedBy;
    }

    public void setReadingRecordedBy(String readingRecordedBy) {
        mReadingRecordedBy = readingRecordedBy;
    }

    public Date getTimeReadingRecorded() {
        return mTimeReadingRecorded;
    }

    public void setTimeReadingRecorded(Date timeReadingRecorded) {
        mTimeReadingRecorded = timeReadingRecorded;
    }
    //endregion
}
