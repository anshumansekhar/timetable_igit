package qazwsxedc.timetable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Anshuman-HP on 13-01-2017.
 */

public class detailholder extends RecyclerView.ViewHolder {
    TextView modulename,Content;
    public detailholder(View itemView) {
        super(itemView);
        modulename=(TextView)itemView.findViewById(R.id.modulename);
        Content=(TextView)itemView.findViewById(R.id.Content);
    }
}
