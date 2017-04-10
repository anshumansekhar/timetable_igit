package qazwsxedc.timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Anshuman-HP on 28-12-2016.
 */

public class Monday extends Fragment {

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<classs, Holder>
            mFirebaseAdapter;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    public Monday() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_monday,container,false);
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.recy_mon);
        mProgressBar = (ProgressBar)view. findViewById(R.id.progressbarmon);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(false);
        mFirebaseDatabaseReference= FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter=new FirebaseRecyclerAdapter<classs, Holder>(classs.class,
                R.layout.card,
                Holder.class,
                mFirebaseDatabaseReference.child("Monday")) {
            @Override
            protected void populateViewHolder(Holder viewHolder, classs model, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.classname.setText(model.getClassname().toString());
                viewHolder.teachername.setText(model.getTeacherName().toString());
                viewHolder.room.setText(model.getRoomNum().toString());
                viewHolder.time.setText("Time"+"\n"+model.getFrom().toString()+"-"+model.getTo().toString());
                viewHolder.type.setText(model.getType().toString());
                Glide.with(getContext())
                        .load(model.getThumb().toString())
                        .centerCrop()
                        .override(200,200)
                        .into(viewHolder.thumb);
            }
        };
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
        return view;
    }
}

