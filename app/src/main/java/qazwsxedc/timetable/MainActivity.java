package qazwsxedc.timetable;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    String mUsername;
    Uri mPhotoUrl;
    public CircleImageView profilepic;
    TextView Username;
    TextView email;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    LinearLayout signout;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main);
        if(!isNetworkAvaliable(this))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Please Connect to Internet")
                    .setMessage("Internet needs to be Turned on\n"
                    +"For Real Time Synchronzation")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        View v=navigationView.inflateHeaderView(R.layout.nav_header);
        profilepic=(CircleImageView)v.findViewById(R.id.Profilepic);
        Username=(TextView)v.findViewById(R.id.Username);
        email=(TextView)v.findViewById(R.id.email);
        if (mFirebaseUser == null) {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl();
            }
            else
            {
                profilepic
                        .setImageDrawable(ContextCompat
                                .getDrawable(MainActivity.this,
                                        R.drawable.cast_album_art_placeholder));
            }
        }
        mFirebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(MainActivity.this,SignInActivity.class));
                }
            }
        });
        User user=new User(mUsername,mPhotoUrl.toString(),"","",mFirebaseUser.getEmail());
        FirebaseDatabase.getInstance().getReference("Users").child(mFirebaseUser.getUid()).setValue(user);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.Groupchat:
                        startActivity(new Intent(MainActivity.this,ChatActivity.class));
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.syllabus:
                        startActivity(new Intent(MainActivity.this,Syllabus.class));
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.about:
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.Holidaysview:
                        startActivity(new Intent(MainActivity.this,Holidays.class));
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.signout:
                        mFirebaseAuth.signOut();
                        startActivity(new Intent(MainActivity.this,SignInActivity.class));
                        return true;
                }
                return true;
            }
        });
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Mon"));
        tabs.addTab(tabs.newTab().setText("Tue"));
        tabs.addTab(tabs.newTab().setText("Wed"));
        tabs.addTab(tabs.newTab().setText("Thurs"));
        tabs.addTab(tabs.newTab().setText("Fri"));
        tabs.addTab(tabs.newTab().setText("Sat"));
        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        Username.setText(mFirebaseUser.getDisplayName().toString());
        email.setText(mFirebaseUser.getEmail().toString());
        Glide.with(getApplicationContext())
                .load(mPhotoUrl)
                .override(36,36)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profilepic);

    }
    private void setupViewPager(ViewPager viewPager) {
        pagerAdapter adapter = new pagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Monday(), "Mon");
        adapter.addFragment(new Tuesday(), "Tue");
        adapter.addFragment(new Wednesday(), "Wed");
        adapter.addFragment(new Thursday(), "Thurs");
        adapter.addFragment(new Friday(), "Fri");
        adapter.addFragment(new Saturday(), "Sat");
        viewPager.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public  boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(ctx.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public void onBackPressed() {
        if(isNavDrawerOpen())
            closeDrawer();
        else
        super.onBackPressed();
    }
    protected boolean isNavDrawerOpen()
    {
        return mDrawerLayout!=null &&mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }
    protected void closeDrawer()
    {
        if(mDrawerLayout!=null)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}

