package com.development.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.development.community.ui.accountEdit.AccountFragment;

public class editAccountActivity extends AppCompatActivity implements AccountFragment.switchActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        FragmentManager fm = getSupportFragmentManager();
        AccountFragment af = new AccountFragment();
        fm.beginTransaction().add(R.id.editAccountFragment, af).commit();

    }

    @Override
    public void switchActivity() {
        Intent intent = new Intent(editAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
