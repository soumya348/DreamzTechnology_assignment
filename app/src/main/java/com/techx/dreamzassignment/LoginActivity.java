package com.techx.dreamzassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText userName,password;
    String strUserName,strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login_submit);
        userName=findViewById(R.id.username);
        password=findViewById(R.id.password);

        strUserName=userName.getText().toString().trim();
        strPassword=password.getText().toString().trim();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String user=sh.getString("username","0");
        String pass=sh.getString("password","0");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if( TextUtils.isEmpty(strUserName) && TextUtils.isEmpty(strPassword)){
//                   if(user.equals(strUserName) && pass.equals(strPassword)){
//                       startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                       finish();
//                   }
//                   else {
//                       Toast.makeText(LoginActivity.this,"please enter correct details or register",Toast.LENGTH_LONG).show();
//                   }
//
//                }else {
//                    Toast.makeText(LoginActivity.this,"please fill all the details",Toast.LENGTH_LONG).show();
//                }
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    public void onResister(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}