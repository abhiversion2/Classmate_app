package com.example.abhi.classmate;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class Homepage extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    TextView From,To,Date;
    EditText Name,Subject;
    Spinner spinner;
    Button OK;
    ConstraintLayout Cl;
    int mYear,mMonth,mDay,mHour,mMinute;
    String[] CSClass={"Select Class","FYCS","SYCS","TYCS"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        From = (TextView) findViewById(R.id.tfrom);
        To = (TextView) findViewById(R.id.to);
        Date = (TextView)findViewById(R.id.date);
        Name = (EditText) findViewById(R.id.name);
        Subject = (EditText) findViewById(R.id.subject);
        OK = (Button) findViewById(R.id.ok);
        Cl = (ConstraintLayout)findViewById(R.id.col);
        Cl.setBackgroundColor(Color.WHITE);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,CSClass) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        OK.setOnClickListener(this);
        From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c=Calendar.getInstance();
                mHour=c.get(Calendar.HOUR_OF_DAY);
                mMinute=c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(Homepage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        From.setText(convertDate(hourOfDay) + ":" + convertDate(minute));
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();
            }
        });
        To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c=Calendar.getInstance();
                mHour=c.get(Calendar.HOUR_OF_DAY);
                mMinute=c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(Homepage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        To.setText(convertDate(hourOfDay) + ":" + convertDate(minute));
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();
            }
        });
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Homepage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int dayOfMonth, int monthOfYear, int year) {
                        Date.setText(convertDate(year) + ":" + convertDate((monthOfYear + 1)) + ":" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(Name.getText().toString().length() == 0){
            Name.setError("Please enter name");
        }
        else{
            if(Subject.getText().toString().length() == 0){
                Subject.setError("Please enter subject");
            }
            else{
                if(spinner.getSelectedItem().toString() == "FYCS"){
                    Intent fycs = new Intent(this, Fycs.class);
                    startActivity(fycs);
                }
            }
        }
        if(Name.getText().toString().length() == 0){
            Name.setError("Please enter name");
        }
        else{
            if(Subject.getText().toString().length() == 0){
                Subject.setError("Please enter subject");
            }
            else{
                if(spinner.getSelectedItem().toString() == "SYCS"){
                    Intent sycs = new Intent(this, Sycs.class);
                    startActivity(sycs);
                }
            }
        }
        if(Name.getText().toString().length() == 0){
            Name.setError("Please enter name");
        }
        else{
            if(Subject.getText().toString().length() == 0){
                Subject.setError("Please enter subject");
            }
            else{
                if(spinner.getSelectedItem().toString() == "TYCS"){
                    String dtext = Date.getText().toString();
                    String ctext = spinner.getSelectedItem().toString();
                    String ftext = From.getText().toString();
                    String ttext = To.getText().toString();
                    String mtext = Name.getText().toString();
                    String stext = Subject.getText().toString();
                    Intent tycs = new Intent(view.getContext(), Tycs.class);
                    tycs.putExtra("mydate",dtext);
                    tycs.putExtra("myclass",ctext);
                    tycs.putExtra("myfrom",ftext);
                    tycs.putExtra("myto",ttext);
                    tycs.putExtra("myname",mtext);
                    tycs.putExtra("mysubject",stext);
                    startActivity(tycs);
                }
            }
        }
    }
}
