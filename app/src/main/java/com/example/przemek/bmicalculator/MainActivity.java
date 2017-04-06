package com.example.przemek.bmicalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.przemek.bmicalculator.tools.BMICalc;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //Input, buttons...
    @BindView(R.id.massInput) EditText massEditText;
    @BindView(R.id.heightInput) EditText heightEditText;
    @BindView(R.id.unitChooser) Spinner unitSpinner;
    @BindView(R.id.calculateButton) Button calculateButton;
    @BindView(R.id.resultTextView) TextView resultTextView;
    @BindView(R.id.shareButton) Button shareButton;

    SharedPreferences sharedPreferences;
    static boolean isBMICalculated = false;
    float BMI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences("com.example.przemek.bmicalculator", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveData();
                break;
            case R.id.menu_load:
                loadData();
                break;
            case R.id.menu_about:
                Intent i = new Intent(getApplicationContext(), AboutMeActivity.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                finish();
                System.exit(0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.shareButton)
    public void onShareButtonClick() {

        if (isBMICalculated) {
            //Share
            String _message = getResources().getString(R.string.bmi_share_text, BMI); //String.valueOf(R.string.bmiShareText + BMI);

            ShareCompat.IntentBuilder
                    .from(this)
                    .setText(_message)
                    .setType("text/plain")
                    .setChooserTitle(getResources().getString(R.string.bmi_share_text_label))
                    .startChooser();

        } else {
            showToast(R.string.invalid_data);
        }
    }


    @OnClick(R.id.calculateButton)
    public void processInput() {

        isBMICalculated = false;

        if (!isDataValid()) {
            showToast(R.string.invalid_data);
            return;
        }

        //calculate BMI
        float _mass = Float.valueOf(massEditText.getText().toString());
        float _height = Float.valueOf(heightEditText.getText().toString());
        switch(unitSpinner.getSelectedItemPosition()) {
            case 0: {
                BMI = BMICalc.calculateBMIKilograms(_mass, _height);
                break;
            }
            case 1: {
                BMI = BMICalc.calculateBMIPounds(_mass, _height);
                break;
            }
        }


        String _resultText = getResources().getString(R.string.result_ok, BMI);
        resultTextView.setText(_resultText);
        resultTextView.setTextColor(getResources().getColor(BMICalc.getColorRepresentation(BMI)));

        isBMICalculated = true;
    }


    private boolean isDataValid() {
        boolean _result = false;

        if (Objects.equals(massEditText.getText().toString(), "") || Objects.equals(heightEditText.getText().toString(), "")) {
            return false;
        }

        float _mass = Float.valueOf(massEditText.getText().toString());
        float _height = Float.valueOf(heightEditText.getText().toString());

        switch(unitSpinner.getSelectedItemPosition()) {
            case 0: {
                _result = BMICalc.isDataValidKilograms(_mass, _height);
                break;
            }
            case 1: {
                _result = BMICalc.isDataValidPounds(_mass, _height);
                break;
            }
        }

        return _result;
    }

    private void showToast(int id) {
        Context _context = getApplicationContext();
        int _duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(_context, getResources().getString(id), _duration);
        toast.show();
    }

    public void saveData() {
        if (!isDataValid()) {
            showToast(R.string.invalid_data);
            return;
        }

        SharedPreferences.Editor _editor = sharedPreferences.edit();
        _editor.putFloat(String.valueOf(R.string.mass_saved_val), Float.valueOf(massEditText.getText().toString()));
        _editor.putFloat(String.valueOf(R.string.height_saved_val), Float.valueOf(heightEditText.getText().toString()));
        _editor.apply();
        showToast(R.string.data_saved);
    }

    public void loadData() {
        float _mass = sharedPreferences.getFloat(String.valueOf(R.string.mass_saved_val), -1);
        float _height = sharedPreferences.getFloat(String.valueOf(R.string.height_saved_val), -1);

        if (_mass == -1 || _height == -1) {
            showToast(R.string.no_data_saved);
            return;
        }


        massEditText.setText(String.valueOf(_mass));
        heightEditText.setText(String.valueOf(_height));
        showToast(R.string.date_loaded);
        processInput();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean("bmiCalculated", isBMICalculated);
        savedInstanceState.putString("massEdit", massEditText.getText().toString());
        savedInstanceState.putString("heightEdit", heightEditText.getText().toString());
        savedInstanceState.putString("bmiRes", resultTextView.getText().toString());
        savedInstanceState.putFloat("bmi", BMI);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        isBMICalculated = savedInstanceState.getBoolean("bmiCalculated");
        massEditText.setText(savedInstanceState.getString("massEdit"));
        heightEditText.setText(savedInstanceState.getString("heightEdit"));
        resultTextView.setText(savedInstanceState.getString("bmiRes"));
        BMI = savedInstanceState.getFloat("bmi");
        resultTextView.setTextColor(getResources().getColor(BMICalc.getColorRepresentation(BMI)));
    }
}
