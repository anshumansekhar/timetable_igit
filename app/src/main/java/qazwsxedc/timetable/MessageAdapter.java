package qazwsxedc.timetable;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anshuman-HP on 09-01-2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private final Activity activity;
    List<Message> messages = new ArrayList<>();

    public MessageAdapter(Activity activity) {
        this.activity = activity;
    }

    public void addMessage(Message chat) {
        messages.add(chat);
        notifyItemInserted(messages.size());
    }
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(activity, activity.getLayoutInflater().inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
