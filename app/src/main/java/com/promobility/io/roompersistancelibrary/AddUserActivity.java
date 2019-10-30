package com.promobility.io.roompersistancelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.promobility.io.roompersistancelibrary.database.AppDatabase;
import com.promobility.io.roompersistancelibrary.database.model.User;

import java.util.Date;

public class AddUserActivity extends AppCompatActivity {

    TextInputEditText textFirstName;
    TextInputEditText textLastName;
    TextInputEditText textEmail;
    TextInputEditText textPhoneNo;
    TextInputEditText textAddress;
    Button buttonAddUser;
    TextInputLayout textInputLayoutFirstName;
    TextInputLayout textInputLayoutEmail;

    AppDatabase database;

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, AddUserActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        database = AppDatabase.getDatabaseInstance(this);

        textFirstName = findViewById(R.id.textFirstName);
        textLastName = findViewById(R.id.textLastName);
        textEmail = findViewById(R.id.textEmail);
        textPhoneNo = findViewById(R.id.textPhoneNo);
        textAddress = findViewById(R.id.textAddress);
        buttonAddUser = findViewById(R.id.buttonAddUser);
        textInputLayoutFirstName = findViewById(R.id.textInputLayoutFirstName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textFirstName.getText().toString().trim().isEmpty()) {
                    textInputLayoutFirstName.setError("Enter First Name");
                    return;
                }

                if (textEmail.getText().toString().trim().isEmpty()) {
                    textInputLayoutEmail.setError("Enter Email");
                    return;
                }
                User mUser = new User(textFirstName.getText().toString(), textLastName.getText().toString(), textEmail.getText().toString(), textPhoneNo.getText().toString(), textAddress.getText().toString(), new Date(), new Date());
                database.userDao().insertUser(mUser);
                finish();
            }
        });

    }
}
