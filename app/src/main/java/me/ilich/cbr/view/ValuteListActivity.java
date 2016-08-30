package me.ilich.cbr.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ilich.cbr.R;
import me.ilich.cbr.model.Valute;

public class ValuteListActivity extends AppCompatActivity {

    public static String EXTRA_VALUTE = "valute";

    public static Intent intent(Context context) {
        return new Intent(context, ValuteListActivity.class);
    }

    public static Valute extractValute(Intent intent) {
        return intent.getParcelableExtra(EXTRA_VALUTE);
    }

    private Adapter adapter = new Adapter();
    private List<Valute> valutes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valute_list);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.valute_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //TODO заполнить список
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView codeTextView;

        public ViewHolder(ViewGroup parent) {
            super(getLayoutInflater().inflate(R.layout.listitem_valute, parent, false));
            titleTextView = (TextView) itemView.findViewById(R.id.valute_title);
            codeTextView = (TextView) itemView.findViewById(R.id.valute_code);
        }

        public void fill(final Valute valute) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_VALUTE, valute);
                    setResult(RESULT_OK, data);
                }
            });
            titleTextView.setText(valute.getName());
            codeTextView.setText(valute.getCharCode());
        }

    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Valute valute = valutes.get(position);
            holder.fill(valute);
        }

        @Override
        public int getItemCount() {
            return valutes.size();
        }

    }

}
