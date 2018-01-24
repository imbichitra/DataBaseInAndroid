package com.example.bpradhan.validation;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DataBase db;
    private EditText pin1;
    private EditText pin2;
    private CheckBox rem_user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String PREF_NAME="prefs";
    private static final String KEY_REMEMBER="remember";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASS="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DataBase(this);

        sharedPreferences=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        pin1=(EditText)findViewById(R.id.editText4);
        pin2=(EditText)findViewById(R.id.editText5);
        rem_user=(CheckBox) findViewById(R.id.checkBox);

        if(sharedPreferences.getBoolean(KEY_REMEMBER,false))
            rem_user.setChecked(true);
        else
            rem_user.setChecked(false);
        pin1.setText(sharedPreferences.getString(KEY_USERNAME,""));
        pin2.setText(sharedPreferences.getString(KEY_PASS,""));
    }
    public void validate(View view) {
        if(rem_user.isChecked()){
            editor.putString(KEY_USERNAME,pin1.getText().toString().trim());
            editor.putString(KEY_PASS,pin2.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER,true);
            editor.apply();
        }
        else{
            editor.putBoolean(KEY_REMEMBER,false);
            editor.remove(KEY_USERNAME);
            editor.remove(KEY_PASS);
            editor.apply();
        }

    }
    public void saveData(View view){
        boolean isSave=db.insertData(pin1.getText().toString().trim(),pin2.getText().toString().trim());
        if(isSave)
            Toast.makeText(this, "data saved", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "not saved", Toast.LENGTH_SHORT).show();
    }
    public void getAllData(View view){
        Cursor res=db.getData();
        if(res.getCount()==0){
            sowMessage("Error","Nothing found");
            return;
        }else{
            StringBuffer buffer=new StringBuffer();
            while(res.moveToNext()){
                buffer.append("email :"+res.getString(0)+"\n");
                buffer.append("password :"+res.getString(1)+"\n\n");
            }
            sowMessage("Data",buffer.toString());
        }
    }
    public void sowMessage(String title,String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void updateData(View view){
        boolean isUpdate=db.updateData(pin1.getText().toString().trim(),pin2.getText().toString().trim());
        if(isUpdate)
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "not update", Toast.LENGTH_SHORT).show();
    }
    public void deleteAllData(View view){
        Integer i=db.deleteData(pin1.getText().toString().trim());
        if(i>=0)
            Toast.makeText(this, "1 row deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "no row deleted", Toast.LENGTH_SHORT).show();
    }

}
