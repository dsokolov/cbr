package me.ilich.cbr.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ilich.cbr.R;
import me.ilich.cbr.model.Model;
import me.ilich.cbr.model.Valute;

public class ValuteListFragment extends Fragment {

    public static ValuteListFragment create() {
        return new ValuteListFragment();
    }

    private Adapter adapter = new Adapter();
    private List<Valute> valutes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_valute_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        valutes.addAll(Model.getInstance().getValutes());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.valute_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView codeTextView;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(getContext()).inflate(R.layout.listitem_valute, parent, false));
            titleTextView = (TextView) itemView.findViewById(R.id.valute_title);
            codeTextView = (TextView) itemView.findViewById(R.id.valute_code);
        }

        public void fill(final Valute valute) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Callback) getActivity()).onValuteClick(valute);
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

    public interface Callback {

        void onValuteClick(Valute valute);

    }

}
