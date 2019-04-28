package com.example.hiveride;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);

        getSupportActionBar().setLogo(R.drawable.hive_button);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
                if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                }
            }

        });

        ListFragment fragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, fragment).commit();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageView fab1 = (ImageView) findViewById(R.id.fab_action1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment account = new AccountPage();
                if(!isSignedIn()){
                    onPause();
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
                else{
                    initiateFragment(account);
                }
            }
        });

        ImageView fab2 = (ImageView) findViewById(R.id.fab_action3);
        fab2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment mapFrag = new Map();
                initiateFragment(mapFrag);

            }
        });
    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getSupportFragmentManager().popBackStackImmediate();

                        Context context = getApplicationContext();
                        CharSequence text = "Hope to see you soon!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
    }

    public boolean isSignedIn(){
        if(GoogleSignIn.getLastSignedInAccount(this) == null)
            return false;
        else
            return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String name = account.getDisplayName();

            Context context = getApplicationContext();
            CharSequence text = "Welcome back, " + name + " !" ;
            int duration = Toast.LENGTH_SHORT;

            onResume();

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Oopsie", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())    {
            case android.R.id.home:

                while(getSupportFragmentManager().getBackStackEntryCount() > 0)
                    getSupportFragmentManager().popBackStackImmediate();
                return true;

            case R.id.action_settings:
                if(isSignedIn()) {
                    Fragment frg = new CreateRide();
                    initiateFragment(frg);
                }else {
                    CharSequence text = "You must log in in order to create a ride.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void initiateFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        if(fm.getBackStackEntryCount() > 0){
            if(fm.findFragmentByTag(fragment.getClass().getName()) != null){
                if(fm.getFragments().get(fm.getFragments().size() - 1).getClass().getName().equals(fragment.getClass().getName())){
                    fm.popBackStackImmediate();
                    fm.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right, R.anim.slide_in_right)
                            .replace(R.id.main_fragment, fragment, fragment.getClass().getName())
                            .addToBackStack(null).commit();
                    return;
                }

                Fragment previous = fm.getFragments().get(fm.getFragments().size() - 1);

                while(fm.getBackStackEntryCount() > 0)
                    fm.popBackStackImmediate();

                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right, R.anim.slide_in_right)
                        .replace(R.id.main_fragment, previous, previous.getClass().getSimpleName())
                        .addToBackStack(null).commit();

                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right, R.anim.slide_in_right)
                        .replace(R.id.main_fragment, fragment, fragment.getClass().getName())
                        .addToBackStack(null).commit();
            }
            else{

                fm.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right, R.anim.slide_in_right)
                        .replace(R.id.main_fragment, fragment, fragment.getClass().getName())
                        .addToBackStack(null).commit();
            }

        }
        else{
            fm.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right, R.anim.slide_in_right)
                    .replace(R.id.main_fragment, fragment, fragment.getClass().getName())
                    .addToBackStack(null).commit();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        signOut();
    }
}
