package com.example.abhi.classmate;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button Login;
    TextView Counter;
    TextInputEditText Username,Password;
    ImageView im1,im2;
    ConstraintLayout Cl;
    int counter=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = (Button)findViewById(R.id.login);
        Counter = (TextView)findViewById(R.id.counter);
        Username = (TextInputEditText) findViewById(R.id.user);
        Password = (TextInputEditText) findViewById(R.id.pass);
        im1=(ImageView)findViewById(R.id.imageView1);
        im2=(ImageView)findViewById(R.id.imageView2);
        Cl = (ConstraintLayout)findViewById(R.id.col);
        Cl.setBackgroundColor(Color.WHITE);
        Counter.setVisibility(View.GONE);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Username.getText().toString().length()==0){
                    Username.setError("Please Enter Username");
                }
                else{
                    if(Password.getText().toString().length()==0){
                        Password.setError("Please Enter Username");
                    }
                    else{
                        if(Username.getText().toString().equals("admin")&&
                                Password.getText().toString().equals("admin")) {
                            Intent homepage=new Intent(MainActivity.this,Homepage.class);
                            startActivity(homepage);
                        }
                        else{
                            Counter.setVisibility(View.VISIBLE);
                            Counter.setTextColor(Color.RED);
                            counter--;
                            Counter.setText(Integer.toString(counter));
                            Snackbar.make(Cl,"Wrong Input! you have "+counter+" Attempt left",Snackbar.LENGTH_LONG).show();
                            if(counter==0){
                                Login.setEnabled(false);
                            }
                        }
                    }
                }

            }
        });
    }

}
