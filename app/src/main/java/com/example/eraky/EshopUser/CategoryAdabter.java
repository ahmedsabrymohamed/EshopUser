package com.example.eraky.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eraky on 2/10/2018.
 */

public class CategoryAdabter extends RecyclerView.Adapter<CategoryAdabter.CategoryViewHolder> {
    private final ArrayList<Category> categories;
    private final Context context;
    private final SetOncLickListener listener;

    public CategoryAdabter(ArrayList<Category> categories, Context context, SetOncLickListener listener) {
        this.categories = categories;
        this.context = context;
        this.listener=listener;

    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View root=inflater.inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(root);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

       Picasso.with(context).load(categories.get(position).getImage()).resize(400,400).placeholder(R.drawable.no_image).into(holder.categoryImage);
        holder.name.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(categories!=null)
        return categories.size();
        return 0;
    }

    public interface SetOncLickListener{
        void SetOnclick(Category category);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView categoryImage;
        final TextView name ;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryImage=itemView.findViewById(R.id.category_image);
            name=itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.SetOnclick(categories.get(getAdapterPosition()));
        }
    }

}
