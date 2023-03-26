package com.example.onlinevotingsystemproject.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinevotingsystemproject.R;
import com.example.onlinevotingsystemproject.data.model.Topic;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder> {
    private final Context context;
    private final List<Topic> topics;
    private OnItemClickListener listener;

    public TopicsAdapter(@NonNull Context context, List<Topic> topics, OnItemClickListener listener) {
        this.context = context;
        this.topics = topics;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topic_list_item, parent, false);
        return new TopicViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.titleTextView.setText(topic.getTitle());
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;

        public TopicViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.topic_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}