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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Anshuman-HP on 28-12-2016.
 */

public class Thursday extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
    }
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<classs, Holder>
            mFirebaseAdapter;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    public Thursday() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thursday,container,false);
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.recy_thurs);
        mProgressBar = (ProgressBar)view. findViewById(R.id.progressbarthurs);
        mProgressBar.setVisibility(View.VISIBLE);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(false);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFirebaseDatabaseReference= FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter=new FirebaseRecyclerAdapter<classs, Holder>(classs.class,
                R.layout.card,
                Holder.class,
                mFirebaseDatabaseReference.child("Thursday")) {

            @Override
            protected void populateViewHolder(Holder viewHolder, classs model, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.classname.setText(model.getClassname().toString());
                viewHolder.teachername.setText(model.getTeacherName().toString());
                viewHolder.room.setText(model.getRoomNum().toString());
                viewHolder.time.setText("Time"+"\n"+model.getFrom()+"-"+model.getTo());
                viewHolder.type.setText(model.getType().toString());
                Glide.with(Thursday.this)
                        .load(model.getThumb())
                        .error(R.mipmap.ic_launcher)
                        .override(200, 200)
                        .centerCrop()
                        .into(viewHolder.thumb);

            }
        };
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);


        return view;
    }
}
