package com.example.eraky.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ProductActivity extends AppCompatActivity implements ProductAdabter.SetOncLickListener {

    private RecyclerView productRecyclerView;
    private ProductAdabter adabter;
    private GridLayoutManager layoutManager;
    private ArrayList<Product> products;
    private ArrayList<Product> importantProduct;
    int categoryId;
    int range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_product);
        categoryId=getIntent().getIntExtra("ID",1);
        range=getIntent().getIntExtra("Range",0);
        importantProduct=new ArrayList<>();
        products=new ArrayList<>();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int productNumber=((int)dpWidth/101);
        adabter=new ProductAdabter(products,ProductActivity.this,ProductActivity.this);
        productRecyclerView=(RecyclerView) findViewById(R.id.product_list);
        layoutManager=new GridLayoutManager(ProductActivity.this,productNumber);
        productRecyclerView.setLayoutManager(layoutManager);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setAdapter(adabter);
        if(savedInstanceState!=null) {
            products = savedInstanceState.getParcelableArrayList("Data");
            adabter.notifyDataSetChanged();

        }else{
            getData();
        }
    }


    private void getData() {
        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<ProductsResponse> call = apiService.getAllProducts(Integer.toString(categoryId));
        call.enqueue(new retrofit2.Callback<ProductsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductsResponse> call
                    , @NonNull retrofit2.Response<ProductsResponse> response) {
                List<Product> a=response.body().getProducts();
                products.clear();
                if(a!=null)
                {
                    for(int i=0;i<a.size();i++)
                    {
                        if(a.get(i).getRange()==range)
                        {
                            products.add(a.get(i));
                            if(a.get(i).getTrend()==range)
                                importantProduct.add(a.get(i));
                        }
                    }
                    adabter.notifyDataSetChanged();
                }

                setImportantProduct();
            }
            @Override
            public void onFailure(@NonNull Call<ProductsResponse> call, @NonNull Throwable t) {
            }
        });


    }
    ImageView imageView1,imageView2,imageView3;
    TextView textView1,textView2,textView3;
    void setImportantProduct()
    {
        imageView1=(ImageView) findViewById(R.id.first_important_product);
        imageView2=(ImageView) findViewById(R.id.second_important_product);
        imageView3=(ImageView) findViewById(R.id.third_important_product);
        textView1=(TextView) findViewById(R.id.first_important_product_name);
        textView2 = (TextView) findViewById(R.id.second_important_product_name);
        textView3 = (TextView) findViewById(R.id.third_important_product_name);


        if(importantProduct.size()>0)
        {
            Picasso.with(this).load(importantProduct.get(0).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView1);
            textView1.setText(importantProduct.get(0).getName());
        }
        if(importantProduct.size()>1) {
            Picasso.with(this).load(importantProduct.get(1).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView2);
            textView2.setText(importantProduct.get(1).getName());
        }
        if(importantProduct.size()>2) {
            Picasso.with(this).load(importantProduct.get(2).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView3);
            textView3.setText(importantProduct.get(2).getName());
        }
    }


    @Override
    public void SetOnclick(Product product) {
        Intent intent=new Intent(this, OneProductActivity.class);
        intent.putExtra("Product",product);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();

    }


    public void openDetils(View view) {
        Intent intent=new Intent(this,OneProductActivity.class);
        if(view.getId()==R.id.first_important_product)
            intent.putExtra("Product",importantProduct.get(0));
        else if(view.getId()==R.id.second_important_product)
            intent.putExtra("Product", importantProduct.get(1));
        else if(view.getId()==R.id.third_important_product)
            intent.putExtra("Product",importantProduct.get(2));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                AuthUI.getInstance().signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
                break;
            case R.id.favorite:
                Intent intent=new Intent(this,MyFavouActivity.class);
                this.startActivity(intent);
                finish();
                break;
            case R.id.Home:
                Intent intent1=new Intent(this,AllCategoryActivity.class);
                this.startActivity(intent1);
                finish();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("Data", products);
    }
}
