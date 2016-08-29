package me.ilich.cbr.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.ilich.cbr.R;
import me.ilich.cbr.model.Valute;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_SOURCE_VALUTE = 1;
    private static final int REQUEST_CODE_SELECT_TARGET_VALUTE = 2;

    @Nullable
    private Valute sourceValute = null;
    @Nullable
    private Valute targetValute = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_SOURCE_VALUTE && resultCode == Activity.RESULT_OK) {

        } else if (requestCode == REQUEST_CODE_SELECT_TARGET_VALUTE && resultCode == Activity.RESULT_OK) {

        }
    }

    public void onChangeSourceValuteClick(View view) {
        startActivityForResult(ValuteListActivity.intent(this), REQUEST_CODE_SELECT_SOURCE_VALUTE);
    }

    public void onChangeTargetValuteClick(View view) {
        startActivityForResult(ValuteListActivity.intent(this), REQUEST_CODE_SELECT_TARGET_VALUTE);
    }

}
