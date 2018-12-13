package com.example.abhi.classmate;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Tycs extends AppCompatActivity implements View.OnClickListener {
    TextView Date, Class, From, To, Name, Subject,Logout;
    EditText AddStudent;
    ImageView Back;
    ListView Names;
    String TName, TSubject;
    Button ClearAll, Submit, AddB;
    DatabaseHelper myDB;
    private DatabaseReference mDatabase;
    ConnectionDetector cd;
    ConstraintLayout Cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tycs);
        Name = (TextView) findViewById(R.id.name);
        Subject = (TextView) findViewById(R.id.subject);
        AddStudent = (EditText) findViewById(R.id.addstudent);
        Date = (TextView) findViewById(R.id.showdate);
        Class = (TextView) findViewById(R.id.showclass);
        From = (TextView) findViewById(R.id.tfrom);
        To = (TextView) findViewById(R.id.tto);
        Logout =(TextView)findViewById(R.id.logout);
        Back = (ImageView) findViewById(R.id.back);
        Names = (ListView) findViewById(R.id.showdata);
        ClearAll = (Button) findViewById(R.id.clearall);
        Submit = (Button) findViewById(R.id.submit);
        AddB = (Button) findViewById(R.id.addb);
        Cl = (ConstraintLayout)findViewById(R.id.col);
        Cl.setBackgroundColor(Color.WHITE);
        myDB = new DatabaseHelper(this);
        cd = new ConnectionDetector(this);
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        if (data.getCount() == 0) {
            Snackbar.make(Cl,"There are no contents in this list!",Snackbar.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));
                ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, theList);
                Names.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                Names.setAdapter(listAdapter);
            }
        }
        AddB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = AddStudent.getText().toString();
                if (AddStudent.length() != 0) {
                    AddData(newEntry);
                    AddStudent.setText("");
                } else {
                    Snackbar.make(Cl,"You must put something in the text field!",Snackbar.LENGTH_LONG).show();
                }
            }
        });
        fetchdata();
        submit();
        ClearAll.setOnClickListener(this);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Tycs.this, Homepage.class);
                startActivity(back);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout=new Intent(Tycs.this,MainActivity.class);
                startActivity(logout);
                Toast.makeText(Tycs.this, "Logout Succesfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void AddData(String newEntry) {

        boolean insertData = myDB.addData(newEntry);

        if(insertData==true){
            Snackbar.make(Cl,"Data Successfully Inserted! Go Back and Load Again",Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(Cl,"Something went wrong :(.",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        int count = this.Names.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            this.Names.setItemChecked(i, false);
        }
    }

    public void fetchdata(){
        Date.setText(getIntent().getStringExtra("mydate"));
        Class.setText(getIntent().getStringExtra("myclass"));
        From.setText(getIntent().getStringExtra("myfrom"));
        To.setText(getIntent().getStringExtra("myto"));
        TName=getIntent().getExtras().getString("myname");
        Name.setText(TName);
        TSubject=getIntent().getExtras().getString("mysubject");
        Subject.setText(TSubject);
    }

    public void submit(){
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cd.isConnnected()) {
                    String Data = "Date: "+Date.getText().toString()+
                            "\nTime: "+From.getText().toString()+" - "+To.getText().toString()+
                            "\nName: "+Name.getText().toString()+
                            "\nSubject: "+Subject.getText().toString()+
                            "\nClass: "+Class.getText().toString()+"\n";
                    String selected = "";
                    int cntChoice = Names.getCount();
                    SparseBooleanArray sparseBooleanArray = Names.getCheckedItemPositions();
                    for(int j = 0; j < cntChoice; j++){
                        if(sparseBooleanArray.get(j)) {
                            selected += Names.getItemAtPosition(j).toString() + "\n";
                            System.out.println("Checking list while adding:" + Names.getItemAtPosition(j).toString());
                        }
                    }
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.push().setValue(Data+selected);
                    Snackbar.make(Cl,"Saved to Firebase",Snackbar.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(Tycs.this);
                    ab.setMessage("No Internet Connection Detected ! Want to save data offline")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String selected = "";
                                    int cntChoice = Names.getCount();
                                    String DateAndTime = Date.getText().toString()+ " "+From.getText().toString()+" - "+To.getText().toString();
                                    String Data = "Date: "+Date.getText().toString()+
                                            "\nTime: "+From.getText().toString()+" - "+To.getText().toString()+
                                            "\nName: "+Name.getText().toString()+
                                            "\nSubject: "+Subject.getText().toString()+
                                            "\nClass: "+Class.getText().toString()+
                                            "\nTotal Present: "+cntChoice+"\n";
                                    File file=getFilesDir();
                                    SparseBooleanArray sparseBooleanArray = Names.getCheckedItemPositions();
                                    for(int j = 0; j < cntChoice; j++){
                                        if(sparseBooleanArray.get(j)) {
                                            selected += Names.getItemAtPosition(j).toString() + "\n";
                                            System.out.println("Checking list while adding:" + Names.getItemAtPosition(j).toString());
                                        }
                                    }
                                    String state;
                                    state = Environment.getExternalStorageState();
                                    try {
                                        if (Environment.MEDIA_MOUNTED.equals(state)) {
                                            File Root = Environment.getExternalStorageDirectory();
                                            File Dir = new File(Root.getAbsolutePath() + "/Classmate");
                                            if (!Dir.exists()) {
                                                Dir.mkdir();
                                            }
                                            File files = new File(Dir, DateAndTime+".txt");
                                            FileOutputStream fos = new FileOutputStream(files);
                                            fos.write(Data.getBytes());
                                            fos.write(selected.getBytes());
                                            fos.close();
                                            Toast.makeText(getApplicationContext(), "Attendence Saved to " + file + "/" + DateAndTime, Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Snackbar.make(Cl,"SD Card Not Found",Snackbar.LENGTH_LONG).show();
                                        }
                                    }catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = ab.create();
                    alert.setTitle("Alert");
                    alert.show();
                }
            }
        });
    }
}
