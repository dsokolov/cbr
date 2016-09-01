package me.ilich.cbr.viewmodel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.ilich.cbr.model.Model;

public class ViewModelFragment extends Fragment {

    private boolean isFirstStart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstStart = savedInstanceState == null;
    }

    protected boolean isFirstStart() {
        return isFirstStart;
    }

    protected Model getModel() {
        return ((ViewModelActivity) getActivity()).getModel();
    }

}
