package yct.smartpolicefbchallenge;

import android.support.v4.app.Fragment;

/**
 * Created by mac on 10/22/17.
 */

public class ReportDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ReportDetailsFragment();
    }
}

