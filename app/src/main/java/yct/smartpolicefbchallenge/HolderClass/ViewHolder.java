package yct.smartpolicefbchallenge.HolderClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import yct.smartpolicefbchallenge.R;
import yct.smartpolicefbchallenge.object_report.report_model;

/**
 * Created by mac on 10/24/17.
 */

public class ViewHolder extends RecyclerView.ViewHolder {



    public View mView;



    private static final String TAG = ViewHolder.class.getSimpleName();


    public ViewHolder(View itemView) {
        super(itemView);

        mView = itemView;


    }



}
