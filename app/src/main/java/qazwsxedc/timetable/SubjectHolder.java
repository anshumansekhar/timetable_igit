package qazwsxedc.timetable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anshuman-HP on 13-01-2017.
 */

public class SubjectHolder extends RecyclerView.ViewHolder{
    TextView subjectname,credits,subcode;
    ImageView subphoto;
    public SubjectHolder(View itemView) {
        super(itemView);
        subjectname=(TextView)itemView.findViewById(R.id.subjectname);
        credits=(TextView)itemView.findViewById(R.id.subjectcredits);
        subcode=(TextView)itemView.findViewById(R.id.subjectcode);
        subphoto=(ImageView)itemView.findViewById(R.id.subjectimage);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("Subject_CODE",subcode.getText().toString().trim());
                context.startActivity(intent);
            }
        });


    }


}
