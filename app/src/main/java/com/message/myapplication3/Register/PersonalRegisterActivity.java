package com.message.myapplication3.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.message.myapplication3.R;
import com.message.myapplication3.Users;

public class PersonalRegisterActivity extends AppCompatActivity {

    private TextInputLayout mDisplayName;
    private TextInputLayout mYOB;
    private TextInputLayout mNationality;
    private Button mNextBtn;
    private Button mBackBtn;


    private Toolbar mToolbar;

    private DatabaseReference mDatabase;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_register);

        //Toolbar Set
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Personal Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mRegProgress = new ProgressDialog(this);



        // Firebase Auth

        mAuth = FirebaseAuth.getInstance();


        // Android Fields

        mDisplayName = (TextInputLayout) findViewById(R.id.register_display_name);
        mYOB = (TextInputLayout) findViewById(R.id.register_year_of_birth);
        mNationality = (TextInputLayout) findViewById(R.id.reg_nationality);

        mNextBtn = (Button) findViewById(R.id.reg_next_btn);
        mBackBtn = (Button) findViewById(R.id.reg_back_btn);


        mNextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Users user = new Users();
                String email = getIntent().getStringExtra("email");
                String password = getIntent().getStringExtra("password");

                String name = mDisplayName.getEditText().getText().toString();
                String yob = mYOB.getEditText().getText().toString();
                int yearOfBirth = Integer.parseInt(yob);
                String nationality = mNationality.getEditText().getText().toString();


                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(yob) && !TextUtils.isEmpty(nationality)){
                    Log.d("Email", email);
                    Log.d("password", password);
                    Log.d("name", name);
                    Log.d("yob", String.valueOf(yearOfBirth));
                    Log.d("nationality", nationality);

                    Intent intent = new Intent(PersonalRegisterActivity.this, LanguageRegisterActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("name", name);
                    intent.putExtra("yearOfBirth", yob);
                    intent.putExtra("nationality", nationality);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(),"Email or Password is empty",Toast.LENGTH_SHORT).show();
                }


            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalRegisterActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
