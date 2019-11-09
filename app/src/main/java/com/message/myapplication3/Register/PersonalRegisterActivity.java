package com.message.myapplication3.Register;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.message.myapplication3.R;

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
    }
}
