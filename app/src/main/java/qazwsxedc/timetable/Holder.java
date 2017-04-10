package qazwsxedc.timetable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Anshuman-HP on 28-12-2016.
 */

public class Holder extends RecyclerView.ViewHolder {

    public TextView classname;
    public TextView time;
    public TextView type;
    public TextView room;
    public TextView teachername;
    public ImageView thumb;



    public Holder(final View itemView) {
        super(itemView);
        classname = (TextView) itemView.findViewById(R.id.classname);
        time = (TextView) itemView.findViewById(R.id.time);
        type = (TextView) itemView.findViewById(R.id.type);
        room = (TextView) itemView.findViewById(R.id.room);
        teachername = (TextView) itemView.findViewById(R.id.Teachername);
        thumb = (ImageView) itemView.findViewById(R.id.image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(),"IN Progress",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
