package me.ilich.cbr.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import me.ilich.cbr.R;
import me.ilich.cbr.model.Valute;

public class ValuteListActivity extends AppCompatActivity implements ValuteListFragment.Callback {

    public static String EXTRA_VALUTE = "valute";

    public static Intent intent(Context context) {
        return new Intent(context, ValuteListActivity.class);
    }

    public static Valute extractValute(Intent intent) {
        return intent.getParcelableExtra(EXTRA_VALUTE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valute_list);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container_content, ValuteListFragment.create()).commit();
    }

    @Override
    public void onValuteClick(Valute valute) {
        Intent data = new Intent();
        data.putExtra(EXTRA_VALUTE, valute);
        setResult(RESULT_OK, data);
        finish();
    }

}
