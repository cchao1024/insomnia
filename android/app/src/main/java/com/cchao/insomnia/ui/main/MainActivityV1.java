package com.cchao.insomnia.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.cchao.insomnia.R;
import com.cchao.simplelib.ui.activity.BaseActivity;

public class MainActivityV1 extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_v1);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.main_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
