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
 * Created by Eraky on 2/11/2018.
 */

public class ProductAdabter  extends RecyclerView.Adapter<ProductAdabter.ProductViewHolder> {

    private final ArrayList<Product> products;
    private final Context context;
    private final ProductAdabter.SetOncLickListener listener;


    public ProductAdabter(ArrayList<Product> products, Context context, ProductAdabter.SetOncLickListener listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ProductAdabter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View root=inflater.inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ProductAdabter.ProductViewHolder holder, int position) {
        Picasso.with(context).load(products.get(position).getImage()).resize(400,400).placeholder(R.drawable.no_image).into(holder.productImage);
        holder.name.setText(products.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public interface SetOncLickListener{
        void SetOnclick(Product product);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView productImage;
        final TextView name ;
        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            name=itemView.findViewById(R.id.product_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.SetOnclick(products.get(getAdapterPosition()));
        }
    }
}
