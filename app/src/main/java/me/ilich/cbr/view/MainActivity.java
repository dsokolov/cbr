package me.ilich.cbr.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import me.ilich.cbr.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadingFragment.create()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_content, ConverterFragment.create()).commit();
    }

}
