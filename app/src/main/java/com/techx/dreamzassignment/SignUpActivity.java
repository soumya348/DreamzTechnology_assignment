package com.techx.dreamzassignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    Button submit;
    EditText firstName,lastName,userName,password,address;
    Spinner question;
    String strFirstName,strLastName,strDob,strUserName,strPassword,strAddress,strQuestion,imageEncoded,strYear,strMon,strDay;
    CardView uplode;
    ImageView image;
    Uri imgUri;
    String[] spinner = { "India", "USA", "China", "Japan"};

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dob;
    private int year, month, day;
    private static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        submit=findViewById(R.id.signUp_submit);
        firstName=findViewById(R.id.first_name);
        lastName=findViewById(R.id.last_name);
        dob=findViewById(R.id.dob);
        userName=findViewById(R.id.username_enter);
        password=findViewById(R.id.password_enter);
        address=findViewById(R.id.address);
        question=findViewById(R.id.question);
        uplode=findViewById(R.id.upload);
        image=findViewById(R.id.image_upload);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        question.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinner);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        question.setAdapter(aa);

        uplode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery =new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"select picture"),PICK_IMAGE);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFirstName=firstName.getText().toString().trim();
                strLastName=lastName.getText().toString().trim();
                strUserName=userName.getText().toString().trim();
                strPassword=password.getText().toString();
                strAddress=address.getText().toString().trim();
                if(TextUtils.isEmpty(strFirstName) && TextUtils.isEmpty(strLastName) && TextUtils.isEmpty(strDob) && TextUtils.isEmpty(strUserName) && TextUtils.isEmpty(strPassword) && TextUtils.isEmpty(strAddress) && TextUtils.isEmpty(strQuestion) && TextUtils.isEmpty(imageEncoded)  ){
                    Toast.makeText(SignUpActivity.this,"please fill all the details",Toast.LENGTH_LONG).show();

                }else {

                    if(strPassword.length() == 8){
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("firstName", strFirstName);
                        myEdit.putString("lastName", strLastName);
                        myEdit.putString("dob", strDob);
                        myEdit.putString("username", strUserName);
                        myEdit.putString("password", strPassword);
                        myEdit.putString("Address", strAddress);
                        myEdit.putString("year", strYear);
                        myEdit.putString("day", strDay);
                        myEdit.putString("mon", strMon);
                        myEdit.putString("image", imageEncoded);
                        myEdit.putString("question", strQuestion);
                        myEdit.apply();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }else {
                        Toast.makeText(SignUpActivity.this,"password must be 8 letters and contain at list one upper letter ",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void showDate(int year, int i, int day) {
        dob.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        strDob=dob.getText().toString().trim();

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    strYear = Integer.toString(arg1 );
                    strMon = Integer.toString(arg2 );
                    strDay = Integer.toString(arg3 );

                    showDate(arg1, arg2+1, arg3);
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imgUri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                //image.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

                byte[] bytes = Base64.decode(imageEncoded,Base64.DEFAULT);
                Bitmap bitmap1 =BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                image.setImageBitmap(bitmap1);

            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),spinner[position] , Toast.LENGTH_LONG).show();
        strQuestion=spinner[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "[a-zA-Z]{8,24}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}