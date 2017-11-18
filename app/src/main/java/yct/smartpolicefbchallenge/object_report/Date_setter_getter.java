package yct.smartpolicefbchallenge.object_report;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mac on 10/8/17.
 */

public class Date_setter_getter {

    UUID mId;

    Date mDate;

    public Date_setter_getter() {
        this(UUID.randomUUID());

    }    public Date_setter_getter(UUID id) {
        mId = id;
        mDate = new Date();
    }



    public UUID getmId() {
        return mId;
    }



    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getDateString(){
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                DateFormat.SHORT).format(mDate);
    }


}