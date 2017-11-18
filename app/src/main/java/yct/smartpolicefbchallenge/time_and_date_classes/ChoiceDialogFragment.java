package yct.smartpolicefbchallenge.time_and_date_classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import yct.smartpolicefbchallenge.R;

/**
 * Created by mac on 10/8/17.
 */

public class ChoiceDialogFragment extends AppCompatDialogFragment {

    public static final String EXTRA_CHOICE ="com.android.application.choice";

    private int mChoice = 0;

    public static final int CHOICE_DATE = 1;
    public static final int CHOICE_TIME = 2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.dialog_editDateOrTime)
                .setPositiveButton(R.string.dialog_editDate,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mChoice =CHOICE_DATE;
                                sendResult(Activity.RESULT_OK);
                            }

                        }).setNegativeButton(R.string.dialog_editTime,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mChoice = CHOICE_TIME;
                        sendResult(Activity.RESULT_OK);
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)

            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_CHOICE, mChoice);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);


    }

}

