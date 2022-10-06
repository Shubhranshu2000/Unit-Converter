package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mLengthTextView;
    private TextView mMassTextView;
    private TextView mTimeTextView;
    private TextView mTemperatureTextView;
    private Spinner mSpinner1;
    private Spinner mSpinner2;
    private int mLenId;
    private int mMassId;
    private int mTimeId;
    private int mTempId;
    public EditText mEditText1, mEditText2;
    private String mConverterType = "length";
    private int mCurEditText = 1;

    public void setmCurEditText(int id){
        mCurEditText = id;
    }

    public int getCurEditText(){
        return mCurEditText;
    }

    public void setmConverterType(String s){
        mConverterType = s;
    }

    public String getType(){
        return mConverterType;
    }

    private ArrayAdapter<CharSequence> arrayAdapter1, arrayAdapter2;

    int maxLength = 15;

    public InputFilter[] inputFilter = new InputFilter[]{new InputFilter(){
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Log.i("Filter", "source=" + source + ",start=" + start + ",end=" + end
                    + ",dest=" + dest.toString() + ",dstart=" + dstart
                    + ",dend=" + dend);
            if(dest.length() < 15) return source;
            Toast.makeText(MainActivity.this, "Can't enter more than 15 digits", Toast.LENGTH_SHORT).show();
            return "";
        }
    }};

    private TextWatcher textWatcher = new TextWatcher() {
        private boolean ignore = false;
        String beforeText = "";

        public String changeString(String beforeText, String cur){
            if(beforeText.equals("0") && (cur.length() > 1 && !cur.substring(cur.length() - 1).equals("."))){
                return cur.substring(cur.length() - 1);
            }
            cur = removeLeadingZeros(cur);
            return cur;
        }

        public String removeLeadingZeros(String curString){
            if(curString.length() < 2) return curString;
            int i = 0;
            boolean decimalPresent = false;
            while(curString.charAt(i) == '0' || curString.charAt(i) == '.'){
                if(curString.charAt(i) == '.'){
                    decimalPresent = true;
                    break;
                }
                i++;
            }
            if(!decimalPresent)
                return curString.substring(i);
            if(curString.charAt(0) == '.') curString = "0" + curString;
            return curString;
        }
        @Override
        public void beforeTextChanged(CharSequence source, int i, int i1, int i2) {
            Log.i("BeforeTextChanged", "source=" + source + " ,i=" + i + " ,i1=" + i1 + " ,i2=" + i2);
            beforeText = source.toString();
            int selectionPosition = mEditText2.getSelectionEnd();
            Log.i("BeforeTextChanged", "Selection Positon=" + selectionPosition);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String curString = changeString(beforeText, charSequence.toString());

            if(mCurEditText == 1){
                if(ignore) return;
                ignore = true;
                mEditText1.setText(curString);
                mEditText1.setSelection(curString.length());
                mEditText2.setText(curString);
                ignore = false;
            }
            else{
                if(ignore) return;
                ignore = true;
                mEditText2.setText(curString);
                mEditText2.setSelection(curString.length());
                mEditText1.setText(curString);
                ignore = false;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //private EditViewUpdate editViewUpdate = new EditViewUpdate();

    private View.OnClickListener viewOnClickLinstener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            //Toast.makeText(MainActivity.this, view.getId()+"is Clicked", Toast.LENGTH_SHORT).show();
            int id = view.getId();
            setSpinner(id);
        }
    };

    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            //Toast.makeText(MainActivity.this, view+"", Toast.LENGTH_SHORT).show();
            int id = view.getId();
            if(id == mEditText1.getId()){
                mCurEditText = 1;

                Toast.makeText(MainActivity.this, "editTextView 1 is changed", Toast.LENGTH_SHORT).show();
                mEditText1.addTextChangedListener(textWatcher);
            }
            else if (id == mEditText2.getId()){
                mCurEditText = 2;

                Toast.makeText(MainActivity.this, "editTextView 2 is changed", Toast.LENGTH_SHORT).show();
                mEditText2.addTextChangedListener(textWatcher);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLengthTextView = (TextView) findViewById(R.id.lengthTextView);
        mMassTextView = (TextView) findViewById(R.id.massTextView);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
        mTemperatureTextView = (TextView) findViewById(R.id.temperatureTextView);

        mLenId = mLengthTextView.getId();
        mMassId = mMassTextView.getId();
        mTimeId = mTimeTextView.getId();
        mTempId = mTemperatureTextView.getId();

        mSpinner1 = (Spinner) findViewById(R.id.spinner1);
        mSpinner2 = (Spinner) findViewById(R.id.spinner2);

        mEditText1 = (EditText) findViewById(R.id.editTextNumber1);
        mEditText2 = (EditText) findViewById(R.id.editTextNumber2);

        mLengthTextView.setOnClickListener(viewOnClickLinstener);
        mMassTextView.setOnClickListener(viewOnClickLinstener);
        mTimeTextView.setOnClickListener(viewOnClickLinstener);
        mTemperatureTextView.setOnClickListener(viewOnClickLinstener);

//        mEditText1.setOnClickListener(viewOnClickLinstener);
//        mEditText2.setOnClickListener(viewOnClickLinstener);

        arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner1.setAdapter(arrayAdapter1);
        mSpinner2.setAdapter(arrayAdapter2);

        mLengthTextView.setTextColor(Color.parseColor("#DDCD8A28"));

        mEditText1.setFilters(inputFilter);
        mEditText2.setFilters(inputFilter);

        mEditText1.setOnFocusChangeListener(onFocusChangeListener);
        mEditText2.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void setSpinner(int id){

        if(id == mLenId){
            arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mLengthTextView.setTextColor(Color.parseColor("#DDCD8A28"));
            setmConverterType("length");
        }
        else if(id == mMassId){
            arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.mass_units, android.R.layout.simple_spinner_item);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.mass_units, android.R.layout.simple_spinner_item);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mMassTextView.setTextColor(Color.parseColor("#DDCD8A28"));
            setmConverterType("mass");
        }
        else if(id == mTimeId){
            arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.time_units, android.R.layout.simple_spinner_item);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.time_units, android.R.layout.simple_spinner_item);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTimeTextView.setTextColor(Color.parseColor("#DDCD8A28"));
            setmConverterType("time");
        }
        else{
            arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.temperature_units, android.R.layout.simple_spinner_item);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.temperature_units, android.R.layout.simple_spinner_item);
            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTemperatureTextView.setTextColor(Color.parseColor("#DDCD8A28"));
            setmConverterType("temperature");
        }
        setTextColor(id);
        mSpinner1.setAdapter(arrayAdapter1);
        mSpinner2.setAdapter(arrayAdapter2);
    }

    private void setTextColor(int id){
        int[] tId = {mLenId, mMassId, mTimeId, mTempId};
        for(int i = 0; i < 4; i++){
            if(tId[i] != id){
                if(i == 0){
                    mLengthTextView.setTextColor(Color.parseColor("#808080"));
                }
                else if(i == 1){
                    mMassTextView.setTextColor(Color.parseColor("#808080"));
                }
                else if(i == 2){
                    mTimeTextView.setTextColor(Color.parseColor("#808080"));
                }
                else{
                    mTemperatureTextView.setTextColor(Color.parseColor("#808080"));
                }
            }
        }
    }

    private void converter(){
        String type = getType();
        int select1 = mSpinner1.getSelectedItemPosition();
        int select2 = mSpinner2.getSelectedItemPosition();
        if(type.equals("length")){
            //LengthConverter.convert()
        }
    }
}