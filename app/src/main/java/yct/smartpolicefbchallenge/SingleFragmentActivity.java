package yct.smartpolicefbchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by mac on 10/13/17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_for_list);
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit(); }



    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_quotes);
    setSupportActionBar(toolbar);
}

    protected abstract Fragment createFragment();
}

