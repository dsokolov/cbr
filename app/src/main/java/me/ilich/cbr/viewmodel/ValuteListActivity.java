package me.ilich.cbr.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import me.ilich.cbr.R;
import me.ilich.cbr.model.Valute;

public class ValuteListActivity extends ViewModelActivity implements ValuteListFragment.Callback {

    public static String EXTRA_VALUTE = "valute";
    private static final String EXTRA_SELECTED_VALUTE = "selected valute";

    public static Intent intent(Context context, Valute selectedValute) {
        Intent intent = new Intent(context, ValuteListActivity.class);
        intent.putExtra(EXTRA_SELECTED_VALUTE, (Parcelable) selectedValute);
        return intent;
    }

    public static Valute extractValute(Intent intent) {
        return intent.getParcelableExtra(EXTRA_VALUTE);
    }

    private Valute selectedValute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valute_list);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        selectedValute = getIntent().getParcelableExtra(EXTRA_SELECTED_VALUTE);
    }

    @Override
    protected void onBound() {
        super.onBound();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_content, ValuteListFragment.create(selectedValute)).commit();
    }

    @Override
    public void onValuteClick(Valute valute) {
        Intent data = new Intent();
        data.putExtra(EXTRA_VALUTE, (Parcelable) valute);
        setResult(RESULT_OK, data);
        finish();
    }

}
