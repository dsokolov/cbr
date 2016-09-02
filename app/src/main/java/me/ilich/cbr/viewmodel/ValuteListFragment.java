package me.ilich.cbr.viewmodel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.ilich.cbr.R;
import me.ilich.cbr.model.Valute;

public class ValuteListFragment extends ViewModelFragment {

    private static final String ARG_SELECTED_VALUTE = "selected valute";

    private static final String STATE_VALUTES = "valutes";
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_SELECTED = 1;

    public static ValuteListFragment create(Valute selectedValute) {
        ValuteListFragment f = new ValuteListFragment();
        Bundle b = new Bundle();
        b.putParcelable(ARG_SELECTED_VALUTE, selectedValute);
        f.setArguments(b);
        return f;
    }
    private Adapter adapter = new Adapter();
    private ArrayList<Valute> valutes = new ArrayList<>();
    private Valute selectedValute;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        selectedValute = getArguments().getParcelable(ARG_SELECTED_VALUTE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_valute_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.valute_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (savedInstanceState == null) {
            valutes.addAll(getModel().getContent().getValute());
            int selectedItemIndex = 0;
            for (int i = 0; i < valutes.size(); i++) {
                if (valutes.get(i).equals(selectedValute)) {
                    selectedItemIndex = i;
                }
            }
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(selectedItemIndex);
        } else {
            valutes = savedInstanceState.getParcelableArrayList(STATE_VALUTES);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_VALUTES, valutes);
    }

    public interface Callback {

        void onValuteClick(Valute valute);

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView codeTextView;

        public ViewHolder(View view) {
            super(view);
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

    private class SelectedViewHolder extends ViewHolder {

        public SelectedViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(getContext()).inflate(R.layout.listitem_valute_selected, parent, false));
        }
    }

    private class NormalViewHolder extends ViewHolder {

        public NormalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(getContext()).inflate(R.layout.listitem_valute, parent, false));
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final ViewHolder vh;
            switch (viewType) {
                case VIEW_TYPE_NORMAL:
                    vh = new NormalViewHolder(parent);
                    break;
                case VIEW_TYPE_SELECTED:
                    vh = new SelectedViewHolder(parent);
                    break;
                default:
                    vh = null;
                    break;
            }
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Valute valute = valutes.get(position);
            holder.fill(valute);
        }

        @Override
        public int getItemViewType(int position) {
            Valute curValute = valutes.get(position);
            final int result;
            if (curValute.equals(selectedValute)) {
                result = VIEW_TYPE_SELECTED;
            } else {
                result = VIEW_TYPE_NORMAL;
            }
            return result;
        }

        @Override
        public int getItemCount() {
            return valutes.size();
        }

    }

}
