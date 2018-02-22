package com.example.eraky.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eraky on 2/12/2018.
 */

public class CommentAdabter extends RecyclerView.Adapter<CommentAdabter.CommentViewHolder> {
    private final ArrayList<Comment> comments;
    private final Context context;
    private final SetOncLickListener listener;

    public CommentAdabter(ArrayList<Comment> comments, Context context, SetOncLickListener listener) {
        this.comments = comments;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View root=inflater.inflate(R.layout.item_comment,parent,false);
        return new CommentViewHolder(root);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.userComment.setText(comments.get(position).getBody());
        holder.userName.setText(comments.get(position).getCname());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public interface SetOncLickListener{
        void SetOnclick(Comment comment);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName;
        TextView userComment;
        public CommentViewHolder(View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.comment_user_name);
            userComment=itemView.findViewById(R.id.user_comment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.SetOnclick(comments.get(getAdapterPosition()));
        }
    }
}
