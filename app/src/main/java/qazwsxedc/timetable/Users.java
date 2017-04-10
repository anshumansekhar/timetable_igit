package qazwsxedc.timetable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class Users extends AppCompatActivity {
    DatabaseReference ref;
    ListView users;
    FirebaseListAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ref= FirebaseDatabase.getInstance().getReference().child("Users");
        users=(ListView)findViewById(R.id.userslist);
        adapter=new FirebaseListAdapter<User>(this, User.class,
                R.layout.usercard, ref) {
            @Override
            protected void populateView(View v, User model, int position) {
                TextView Username = (TextView)v.findViewById(R.id.Username);
                TextView Email = (TextView)v.findViewById(R.id.email);
                CircleImageView propic=(CircleImageView)v.findViewById(R.id.userpic);
                Username.setText(model.getName().toString());
                Email.setText(model.getEmail().toString());
                Glide.with(getApplicationContext())
                        .load(model.getPhotoURL())
                        .override(100,100)
                        .centerCrop()
                        .into(propic);
            }
        };
        users.setAdapter(adapter);
    }
}
