package me.ilich.cbr.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import me.ilich.cbr.R;
import me.ilich.cbr.model.Model;
import me.ilich.cbr.model.ValCurs;
import me.ilich.cbr.model.Valute;

public class ConverterFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE_SELECT_SOURCE_VALUTE = 1;
    private static final int REQUEST_CODE_SELECT_TARGET_VALUTE = 2;
    private static final String STATE_SOURCE_VALUTE = "source valute";
    private static final String STATE_TARGET_VALUTE = "target valute";

    private static final String ARG_CONTENT = "content";

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.##");

    public static ConverterFragment create(ValCurs content) {
        ConverterFragment converterFragment = new ConverterFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CONTENT, content);
        converterFragment.setArguments(bundle);
        return converterFragment;
    }

    private ValCurs valCurs;
    private Valute sourceValute;
    private Valute targetValute;

    private TextInputLayout sourceTextInputLayout;
    private TextInputLayout targetTextInputLayout;
    private EditText sourceEditText;
    private EditText targetEditText;
    private Button sourceChangeButton;
    private Button targetChangeButton;
    private TextView detailsTextView;

    private SourceToTargetTextWatcher sourceToTargetTextWatcher = new SourceToTargetTextWatcher();
    private TargetToSourceTextWatcher targetToSourceTextWatcher = new TargetToSourceTextWatcher();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_SOURCE_VALUTE && resultCode == Activity.RESULT_OK) {
            Valute valute = ValuteListActivity.extractValute(data);
            if (valute.equals(targetValute)) {
                targetValute = sourceValute;
            }
            sourceValute = valute;
            sourceTextInputLayout.requestFocus();
            processButtonsLabels();
            convertFromTargetToSource();
        } else if (requestCode == REQUEST_CODE_SELECT_TARGET_VALUTE && resultCode == Activity.RESULT_OK) {
            Valute valute = ValuteListActivity.extractValute(data);
            if (valute.equals(sourceValute)) {
                sourceValute = targetValute;
            }
            targetValute = valute;
            targetTextInputLayout.requestFocus();
            processButtonsLabels();
            convertFromTargetToSource();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            valCurs = getArguments().getParcelable(ARG_CONTENT);
            for (Valute valute : valCurs.getValute()) {
                if (valute.getCharCode().equals("RUR")) {
                    sourceValute = valute;
                }
                if (valute.getCharCode().equals("USD")) {
                    targetValute = valute;
                }
            }
            if (sourceValute == null) {
                sourceValute = valCurs.getValute().get(0);
            }
            if (targetValute == null) {
                targetValute = valCurs.getValute().get(1);
            }
        } else {
            sourceValute = savedInstanceState.getParcelable(STATE_SOURCE_VALUTE);
            targetValute = savedInstanceState.getParcelable(STATE_TARGET_VALUTE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sourceTextInputLayout = (TextInputLayout) view.findViewById(R.id.amount_source);
        targetTextInputLayout = (TextInputLayout) view.findViewById(R.id.amount_target);
        if (sourceTextInputLayout.getEditText() != null) {
            sourceEditText = sourceTextInputLayout.getEditText();
        }
        if (targetTextInputLayout.getEditText() != null) {
            targetEditText = targetTextInputLayout.getEditText();
        }
        sourceChangeButton = (Button) view.findViewById(R.id.change_source_valute);
        sourceChangeButton.setOnClickListener(this);
        targetChangeButton = (Button) view.findViewById(R.id.change_target_valute);
        targetChangeButton.setOnClickListener(this);
        detailsTextView = (TextView) view.findViewById(R.id.details);
        sourceEditText.addTextChangedListener(sourceToTargetTextWatcher);
        targetEditText.addTextChangedListener(targetToSourceTextWatcher);
        processButtonsLabels();
        processDetailsText();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_SOURCE_VALUTE, sourceValute);
        outState.putParcelable(STATE_TARGET_VALUTE, targetValute);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_source_valute:
                onChangeSourceValuteClick(view);
                break;
            case R.id.change_target_valute:
                onChangeTargetValuteClick(view);
                break;
        }
    }

    public void onChangeSourceValuteClick(View view) {
        startActivityForResult(ValuteListActivity.intent(getContext()), REQUEST_CODE_SELECT_SOURCE_VALUTE);
    }

    public void onChangeTargetValuteClick(View view) {
        startActivityForResult(ValuteListActivity.intent(getContext()), REQUEST_CODE_SELECT_TARGET_VALUTE);
    }

    private void processButtonsLabels() {
        sourceChangeButton.setText(sourceValute.getName());
        targetChangeButton.setText(targetValute.getName());
    }

    private void processDetailsText() {
        if (TextUtils.isEmpty(sourceEditText.getText().toString())) {
            detailsTextView.setText(R.string.main_details_enter_source_amount);
        } else {
            String s = getString(R.string.main_details_pattern);
            String details = String.format(s, sourceEditText.getText().toString(), sourceValute.getCharCode(), targetEditText.getText().toString(), targetValute.getCharCode());
            detailsTextView.setText(details);
        }
    }

    private void convertFromSourceToTarget() {
        targetEditText.removeTextChangedListener(targetToSourceTextWatcher);
        try {
            double sourceAmount = Double.parseDouble(sourceEditText.getText().toString());
            double targetAmount = Model.getInstance().getConverter().convert(sourceValute, targetValute, sourceAmount);
            targetEditText.setText(DECIMAL_FORMAT.format(targetAmount));
        } catch (NumberFormatException e) {
            targetEditText.getText().clear();
        }
        targetEditText.addTextChangedListener(targetToSourceTextWatcher);
        processDetailsText();
    }


    private void convertFromTargetToSource() {
        sourceEditText.removeTextChangedListener(sourceToTargetTextWatcher);
        try {
            double sourceAmount = Double.parseDouble(targetEditText.getText().toString());
            double targetAmount = Model.getInstance().getConverter().convert(targetValute, sourceValute, sourceAmount);
            sourceEditText.setText(DECIMAL_FORMAT.format(targetAmount));
        } catch (NumberFormatException e) {
            sourceEditText.getText().clear();
        }
        sourceEditText.addTextChangedListener(sourceToTargetTextWatcher);
        processDetailsText();
    }

    private class SourceToTargetTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            convertFromSourceToTarget();
        }
    }

    private class TargetToSourceTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            convertFromTargetToSource();
        }
    }

}
