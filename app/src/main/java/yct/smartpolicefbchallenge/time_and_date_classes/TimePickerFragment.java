package yct.smartpolicefbchallenge.time_and_date_classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import yct.smartpolicefbchallenge.R;

/**
 * Created by mac on 10/8/17.
 */

public class TimePickerFragment extends AppCompatDialogFragment {
    private Date mDate;

    public static final String EXTRA_TIME ="com.android.application.time";

    public static TimePickerFragment newInstance(Date date){
        Bundle args  = new Bundle();
        args.putSerializable(EXTRA_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate = (Date)getArguments().getSerializable(EXTRA_TIME);

        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        final int hours = cal.get(Calendar.HOUR_OF_DAY);
        final int mins = cal.get(Calendar.MINUTE);



        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_time, null);
        TimePicker tp = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);
        tp.setCurrentHour(hours);
        tp.setCurrentMinute(mins);
        tp.setIs24HourView(false);

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {


                Calendar cal = Calendar.getInstance();
                cal.setTime(mDate);
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                mDate = cal.getTime();
                getArguments().putSerializable(EXTRA_TIME, mDate);
            }


        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                }).create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)

            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mDate);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);


    }
}
