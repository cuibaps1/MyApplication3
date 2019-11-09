package com.message.myapplication3.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.message.myapplication3.MainActivity;
import com.message.myapplication3.R;
import com.message.myapplication3.Users;

import java.util.HashMap;

public class LanguageRegisterActivity extends AppCompatActivity {

    private TextInputLayout mNTLanguage;
    private TextInputLayout mSecondLanguage;
    private TextInputLayout mLearningLanguage1;
    private Button mCreateBtn;
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
        setContentView(R.layout.activity_language_register);


        //Toolbar Set
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Language Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mRegProgress = new ProgressDialog(this);



        // Firebase Auth

        mAuth = FirebaseAuth.getInstance();


        // Android Fields

        mNTLanguage = (TextInputLayout) findViewById(R.id.native_language);
        mSecondLanguage = (TextInputLayout) findViewById(R.id.second_language);
        mLearningLanguage1 = (TextInputLayout) findViewById(R.id.learning_language_1);

        mNextBtn = (Button) findViewById(R.id.reg_next_btn);
        mBackBtn = (Button) findViewById(R.id.reg_back_btn);
        mCreateBtn = (Button) findViewById(R.id.reg_create_btn);


        mCreateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email = getIntent().getStringExtra("email");
                String password = getIntent().getStringExtra("password");
                String name = getIntent().getStringExtra("name");
                String yearOfBirth = getIntent().getStringExtra("yearOfBirth");
                String nationality = getIntent().getStringExtra("nationality");

                String ntLanguage = mNTLanguage.getEditText().getText().toString();
                String secondLanguage = mSecondLanguage.getEditText().getText().toString();
                String learningLanguage1 = mLearningLanguage1.getEditText().getText().toString();


                if(!TextUtils.isEmpty(ntLanguage) && !TextUtils.isEmpty(secondLanguage) && !TextUtils.isEmpty(learningLanguage1)){
                    Log.d("Email", email);
                    Log.d("password", password);
                    Log.d("name", name);
                    Log.d("yearOfBirth", yearOfBirth);
                    Log.d("nationality", nationality);
                    Log.d("native Language", ntLanguage);
                    Log.d("second language", secondLanguage);
                    Log.d("1st learning language", learningLanguage1);

                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account !");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(email, password, name, yearOfBirth, nationality, ntLanguage, secondLanguage, learningLanguage1);

                } else {
                    Toast.makeText(getApplicationContext(),"Email or Password is empty",Toast.LENGTH_SHORT).show();
                }


            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LanguageRegisterActivity.this, PersonalRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void register_user(String email, String password,
                               final String name, final String yearOfBirth, final String nationality,
                               final String ntLanguage, final String secondLanguage, final String learningLanguage1) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", name);
                    userMap.put("year_of_birth", yearOfBirth);
                    userMap.put("nationality",nationality);
                    userMap.put("native_language", ntLanguage);
                    userMap.put("second_language", secondLanguage);
                    userMap.put("learning_language_1", learningLanguage1);
                    userMap.put("status", "Hi there I'm using Chat App.");
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");
                    userMap.put("device_token", device_token);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mRegProgress.dismiss();

                                Intent mainIntent = new Intent(LanguageRegisterActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();

                            }

                        }
                    });


                } else {

                    mRegProgress.hide();
                    Toast.makeText(LanguageRegisterActivity.this, "Could not sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
