package qazwsxedc.timetable;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Syllabus extends AppCompatActivity {
    RecyclerView syllabusrecycler;
    ProgressBar pg;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;
    FirebaseUser user;
    FirebaseRecyclerAdapter<SyllabusObject,SubjectHolder> subjectadapter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        setTitle("Syllabus");
        if(!isNetworkAvaliable(this))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Please Connect to Internet")
                    .setMessage("Internet needs to be Turned on")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        pg=(ProgressBar)findViewById(R.id.pr);
        pg.setVisibility(View.VISIBLE);
        syllabusrecycler=(RecyclerView)findViewById(R.id.syllabusrecycler);
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(Syllabus.this,SignInActivity.class));
        }
        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        auth = FirebaseAuth.getInstance(app);
        databaseRef = database.getReference("Syllabus");
        subjectadapter=new FirebaseRecyclerAdapter<SyllabusObject, SubjectHolder>
                (SyllabusObject.class,R.layout.subject_card,SubjectHolder.class,databaseRef) {
            @Override
            protected void populateViewHolder(SubjectHolder viewHolder, SyllabusObject model, int position) {
                pg.setVisibility(View.INVISIBLE);
                viewHolder.subjectname.setText(model.getSubjectName().toString());
                viewHolder.subcode.setText(model.getSubcode().toString());
                viewHolder.credits.setText("Credits "+model.getCredits().toString());
                Glide.with(getApplicationContext())
                        .load(model.getSubphotourl())
                        .override(100, 100)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.subphoto);

            }
        };

        GridLayoutManager layoutManager=new GridLayoutManager(getBaseContext(),2);
        syllabusrecycler.setLayoutManager(layoutManager);
        syllabusrecycler.setItemAnimator(new DefaultItemAnimator());
        syllabusrecycler.setAdapter(subjectadapter);
    }
    public  boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}









