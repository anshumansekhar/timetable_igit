package qazwsxedc.timetable;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Anshuman-HP on 09-01-2017.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "ChatMessageViewHolder";
    private final Activity activity;

    TextView name, message;
    ImageView image;
    ProgressBar progressBar;

    CircleImageView profilepic;
    public MessageViewHolder(Activity activity,View itemView) {
        super(itemView);
        this.activity = activity;
        name = (TextView) itemView.findViewById(R.id.Usernametext);
        message = (TextView) itemView.findViewById(R.id.messengerTextView);
        profilepic=(CircleImageView)itemView.findViewById(R.id.messengerImageView);
        image= new ImageView(activity);

        ((ViewGroup)itemView).addView(image);
    }
    public void bind(Message chat) {
        name.setText(chat.name);
        Glide.with(activity.getBaseContext())
                .load(chat.photoUrl)
                .override(36,36)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profilepic);

        if (chat.text.startsWith("https://firebasestorage.googleapis.com/") || chat.text.startsWith("content://")) {
            message.setVisibility(View.INVISIBLE);
            image.setVisibility(View.VISIBLE);
            Glide.with(activity)
                    .load(chat.text)
                    .placeholder(R.drawable.ic_autorenew_black_24dp)
                    .into(image);
        }
        else {
            message.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            message.setText(chat.text);

        }
    }

}
