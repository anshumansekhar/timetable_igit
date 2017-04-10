package qazwsxedc.timetable;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Detail extends AppCompatActivity {
    ImageView subPhoto;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef,reference,ref;
    String subname,suburl;
    RecyclerView detailrecycler;
    FirebaseRecyclerAdapter<Module,detailholder> adapter;
    ProgressBar pf;
    CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Syllabus");
        pf=new ProgressBar(this);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        auth = FirebaseAuth.getInstance(app);
        Intent i=getIntent();
        subname=i.getStringExtra("Subject_CODE");
        databaseRef = database.getReference("Syllabus");
        databaseRef=database.getReferenceFromUrl(databaseRef.toString()+"/"+subname);
        subPhoto=(ImageView)collapsingToolbarLayout.findViewById(R.id.app_bar_image);
        detailrecycler=(RecyclerView)findViewById(R.id.detailrecycler);
        pf=(ProgressBar)findViewById(R.id.progressbardetail);
        reference=databaseRef.child("Subphotourl");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                suburl=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Glide.with(getApplicationContext())
                .load(suburl)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(subPhoto);
        adapter=new FirebaseRecyclerAdapter<Module, detailholder>
                (Module.class,
                        R.layout.detailscard,
                        detailholder.class,
                        databaseRef.child("Module")) {
            @Override
            protected void populateViewHolder(detailholder viewHolder, Module model, int position) {
                pf.setVisibility(View.INVISIBLE);
                viewHolder.modulename.setText(""+model.getNumber().toString());
                viewHolder.Content.setText(model.getContent().toString());

            }
        };
        detailrecycler.setAdapter(adapter);
        detailrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
