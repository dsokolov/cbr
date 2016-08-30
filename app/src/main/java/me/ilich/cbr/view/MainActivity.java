package me.ilich.cbr.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

import me.ilich.cbr.R;
import me.ilich.cbr.model.Model;
import me.ilich.cbr.model.Valute;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_SOURCE_VALUTE = 1;
    private static final int REQUEST_CODE_SELECT_TARGET_VALUTE = 2;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.##");

    private Valute sourceValute = new Valute("", "", "", 1, "Рубли", 1.0);
    private Valute targetValute = new Valute("", "", "", 1, "Доллары США", 64.0);

    private ViewGroup containerConverter;
    private TextInputLayout sourceTextInputLayout;
    private TextInputLayout targetTextInputLayout;
    private EditText sourceEditText;
    private EditText targetEditText;
    private Button sourceChangeButton;
    private Button targetChangeButton;

    private SourceToTargetTextWatcher sourceToTargetTextWatcher = new SourceToTargetTextWatcher();
    private TargetToSourceTextWatcher targetToSourceTextWatcher = new TargetToSourceTextWatcher();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        sourceTextInputLayout = (TextInputLayout) findViewById(R.id.amount_source);
        targetTextInputLayout = (TextInputLayout) findViewById(R.id.amount_target);
        if (sourceTextInputLayout.getEditText() != null) {
            sourceEditText = sourceTextInputLayout.getEditText();
        }
        if (targetTextInputLayout.getEditText() != null) {
            targetEditText = targetTextInputLayout.getEditText();
        }
        sourceChangeButton = (Button) findViewById(R.id.change_source_valute);
        targetChangeButton = (Button) findViewById(R.id.change_target_valute);
        sourceEditText.addTextChangedListener(sourceToTargetTextWatcher);
        targetEditText.addTextChangedListener(targetToSourceTextWatcher);
        processControllsState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_SOURCE_VALUTE && resultCode == Activity.RESULT_OK) {
            Valute valute = ValuteListActivity.extractValute(data);
            if (valute.equals(targetValute)) {
                targetValute = sourceValute;
            }
            sourceValute = valute;
            sourceTextInputLayout.requestFocus();
            processControllsState();
            convertFromTargetToSource();
        } else if (requestCode == REQUEST_CODE_SELECT_TARGET_VALUTE && resultCode == Activity.RESULT_OK) {
            Valute valute = ValuteListActivity.extractValute(data);
            if (valute.equals(sourceValute)) {
                sourceValute = targetValute;
            }
            targetValute = valute;
            targetTextInputLayout.requestFocus();
            processControllsState();
            convertFromTargetToSource();
        }
    }

    public void onChangeSourceValuteClick(View view) {
        startActivityForResult(ValuteListActivity.intent(this), REQUEST_CODE_SELECT_SOURCE_VALUTE);
    }

    public void onChangeTargetValuteClick(View view) {
        startActivityForResult(ValuteListActivity.intent(this), REQUEST_CODE_SELECT_TARGET_VALUTE);
    }

    private void processControllsState() {
        sourceChangeButton.setText(sourceValute.getName());
        targetChangeButton.setText(targetValute.getName());
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
