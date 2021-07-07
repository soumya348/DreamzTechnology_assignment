package com.techx.dreamzassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
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

public class EditProfile extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    Button submit;
    EditText firstName,lastName,userName,password,address;
    String strFirstName,strLastName,strDob,strUserName,strPassword,strAddress,strQuestion,strImage,strYear,strDay,strMon,imageEncoded;
    Spinner question;

    CardView uplode;
    ImageView image,back;
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
        setContentView(R.layout.activity_edit_profile);

        submit=findViewById(R.id.edit_submit);
        firstName=findViewById(R.id.first_nameEdit);
        lastName=findViewById(R.id.last_nameEdit);
        dob=findViewById(R.id.dobEdit);
        userName=findViewById(R.id.username_enterEdit);
        password=findViewById(R.id.password_enterEdit);
        address=findViewById(R.id.addressEdit);
        question=findViewById(R.id.questionEdit);
        uplode=findViewById(R.id.edit);
        image=findViewById(R.id.image_edit);
        back=findViewById(R.id.back);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        question.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinner);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        question.setAdapter(aa);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        strUserName=sh.getString("username","0");
        strPassword=sh.getString("password","0");
        strFirstName=sh.getString("firstName","0");
        strLastName=sh.getString("lastName","0");
        strDob=sh.getString("dob","0");
        imageEncoded=sh.getString("image","0");
        strAddress=sh.getString("Address","0");
        strQuestion=sh.getString("question","0");
        strYear=sh.getString("year","0");
        strDay=sh.getString("day","0");
        strMon=sh.getString("mon","0");
        showDate(Integer.parseInt(strYear), Integer.parseInt(strYear)+1, Integer.parseInt(strYear));

        firstName.setText(strFirstName);
        lastName.setText(strLastName);
        dob.setText(strDob);
        userName.setText(strUserName);
        password.setText(strPassword);
        address.setText(strAddress);
        //question.setText(strQuestion);


        byte[] bytes = Base64.decode(imageEncoded,Base64.DEFAULT);
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        image.setImageBitmap(bitmap1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFirstName=firstName.getText().toString().trim();
                strLastName=lastName.getText().toString().trim();
                strUserName=userName.getText().toString().trim();
                strPassword=password.getText().toString();
                strAddress=address.getText().toString().trim();
                if(TextUtils.isEmpty(strFirstName) && TextUtils.isEmpty(strLastName) && TextUtils.isEmpty(strDob) && TextUtils.isEmpty(strUserName) && TextUtils.isEmpty(strPassword) && TextUtils.isEmpty(strAddress) && TextUtils.isEmpty(strQuestion) && TextUtils.isEmpty(imageEncoded)  ){
                    Toast.makeText(EditProfile.this,"please fill all the details",Toast.LENGTH_LONG).show();

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
                        Toast.makeText(EditProfile.this,"password must be 8 letters and contain at list one upper letter ",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


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
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),spinner[position] , Toast.LENGTH_LONG).show();
        strQuestion=spinner[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}