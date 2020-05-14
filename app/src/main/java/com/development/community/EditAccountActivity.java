package com.development.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.development.community.ui.accountEdit.AccountFragment;

/**
 * Activity which loads the AccountFragment, which allows the user to edit their profile.
 */
public class EditAccountActivity extends AppCompatActivity implements AccountFragment.switchActivity {

    /**
     * Runs when the activity is opened and sets the fragment to AccountFragment
     * @param savedInstanceState Allows data to be restored if there is a saved instance
     */
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
        super.onBackPressed();
    }
}
