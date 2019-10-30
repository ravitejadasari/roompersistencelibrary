package com.promobility.io.roompersistancelibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.promobility.io.roompersistancelibrary.adapter.UserAdapter;
import com.promobility.io.roompersistancelibrary.database.AppDatabase;
import com.promobility.io.roompersistancelibrary.database.model.User;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements UserAdapter.Callback {

    RecyclerView mRecyclerView;
    FloatingActionButton fab;
    AppDatabase database;

    UserAdapter mUserAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        database = AppDatabase.getDatabaseInstance(this);
        setUp();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserActivity.startActivity(MainActivity.this);
            }
        });

    }

    private void setUp() {
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mUserAdapter = new UserAdapter(new ArrayList<User>());
        mUserAdapter.setCallback(this);
//        prepareDemoContent();
        mRecyclerView.setAdapter(mUserAdapter);

    }

    private void prepareDemoContent() {
        User mUser1 = new User("Ramesh", "Chandra", "ramesh@gmail.com", null, null, new Date(), new Date());
        database.userDao().insertUser(mUser1);

        User mUser2 = new User("Sachin", "Kalal", "chnada@gmail.com", null, null, new Date(), new Date());
        database.userDao().insertUser(mUser2);

        User mUser3 = new User("Amit", "Kumar", "arun@gmail.com", null, null, new Date(), new Date());
        database.userDao().insertUser(mUser3);

        User mUser4 = new User("Kapil", "sharma", "kapil@gmail.com", null, null, new Date(), new Date());
        database.userDao().insertUser(mUser4);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserAdapter.addItems(database.userDao().getAll());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }

    @Override
    public void onDeleteClick(User mUser) {
        database.userDao().delete(mUser);
        mUserAdapter.addItems(database.userDao().getAll());
    }

}
