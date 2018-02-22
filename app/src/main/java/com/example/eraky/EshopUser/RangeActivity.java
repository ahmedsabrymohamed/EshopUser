package com.example.eraky.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
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

public class RangeActivity extends AppCompatActivity {
    private ArrayList<Product> importantProduct1,importantProduct2,importantProduct3;
    ArrayList<Product> products;
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        importantProduct1=new ArrayList<>();
        importantProduct2=new ArrayList<>();
        importantProduct3=new ArrayList<>();
        products=new ArrayList<>();
        categoryId=getIntent().getIntExtra("ID",1);
        GetData();

    }

    void GetData()
    {
        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<ProductsResponse> call = apiService.getAllProducts(Integer.toString(categoryId));
        call.enqueue(new retrofit2.Callback<ProductsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductsResponse> call
                    , @NonNull retrofit2.Response<ProductsResponse> response) {
                List<Product> a=response.body().getProducts();
                products.clear();
                importantProduct1.clear();
                importantProduct2.clear();
                importantProduct3.clear();

                if(a!=null)
                    products.addAll(a);
                for(int i=0;i<products.size();i++)
                {
                    if(products.get(i).getTrend()==1)
                        importantProduct1.add(products.get(i));
                    else if(products.get(i).getTrend()==2)
                        importantProduct2.add(products.get(i));
                    else if(products.get(i).getTrend()==3)
                        importantProduct3.add(products.get(i));
                }
                setImportantProduct();

            }
            @Override
            public void onFailure(@NonNull Call<ProductsResponse> call, @NonNull Throwable t) {
            }
        });

    }



    void setImportantProduct(){
        /////////////////////////////////////////////////////

        if(importantProduct1.size()>0)
        {
            ImageView imageView1=(ImageView) findViewById(R.id.low_range_first_important_product);
            TextView textView1=(TextView) findViewById(R.id.low_range_first_important_product_name);
            Picasso.with(this).load(importantProduct1.get(0).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView1);
            textView1.setText(importantProduct1.get(0).getName());
        }
        if(importantProduct1.size()>1) {
            ImageView imageView2=(ImageView) findViewById(R.id.low_range_second_important_product);
            TextView textView2 = (TextView) findViewById(R.id.low_range_second_important_product_name);
            Picasso.with(this).load(importantProduct1.get(1).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView2);
            textView2.setText(importantProduct1.get(1).getName());

        }
        if(importantProduct1.size()>2) {
            ImageView imageView3=(ImageView) findViewById(R.id.low_range_third_important_product);
            Picasso.with(this).load(importantProduct1.get(2).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView3);
            TextView textView3 = (TextView) findViewById(R.id.low_range_third_important_product_name);
            textView3.setText(importantProduct1.get(2).getName());

        }

        /////////////////////////////////////////////////////

        if(importantProduct2.size()>0)
        {
            ImageView imageView1=(ImageView) findViewById(R.id.mid_range_first_important_product);
            TextView textView1=(TextView) findViewById(R.id.mid_range_first_important_product_name);
            Picasso.with(this).load(importantProduct2.get(0).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView1);
            textView1.setText(importantProduct2.get(0).getName());
        }
        if(importantProduct2.size()>1) {
            ImageView imageView2=(ImageView) findViewById(R.id.mid_range_second_important_product);
            TextView textView2 = (TextView) findViewById(R.id.mid_range_second_important_product_name);
            Picasso.with(this).load(importantProduct2.get(1).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView2);
            textView2.setText(importantProduct2.get(1).getName());

        }
        if(importantProduct2.size()>2) {
            ImageView imageView3=(ImageView) findViewById(R.id.mid_range_third_important_product);
            Picasso.with(this).load(importantProduct2.get(2).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView3);
            TextView textView3 = (TextView) findViewById(R.id.mid_range_third_important_product_name);
            textView3.setText(importantProduct2.get(2).getName());
        }

        /////////////////////////////////////////////////////

        if(importantProduct3.size()>0)
        {
            ImageView imageView1=(ImageView) findViewById(R.id.high_range_first_important_product);
            TextView textView1=(TextView) findViewById(R.id.high_range_first_important_product_name);
            Picasso.with(this).load(importantProduct3.get(0).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView1);
            textView1.setText(importantProduct3.get(0).getName());
        }
        if(importantProduct3.size()>1) {
            ImageView imageView2=(ImageView) findViewById(R.id.high_range_second_important_product);
            TextView textView2 = (TextView) findViewById(R.id.high_range_second_important_product_name);
            Picasso.with(this).load(importantProduct3.get(1).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView2);
            textView2.setText(importantProduct3.get(1).getName());

        }
        if(importantProduct3.size()>2) {
            ImageView imageView3=(ImageView) findViewById(R.id.high_range_third_important_product);
            Picasso.with(this).load(importantProduct3.get(2).getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView3);
            TextView textView3 = (TextView) findViewById(R.id.high_range_third_important_product_name);
            textView3.setText(importantProduct3.get(2).getName());

        }
    }
    public void openDetils(View view) {}

    public void openDetils1(View view) {
        Intent intent=new Intent(this,ProductActivity.class);
        intent.putExtra("ID",categoryId);
        intent.putExtra("Range",1);
        this.startActivity(intent);
    }

    public void openDetils2(View view) {
        Intent intent=new Intent(this,ProductActivity.class);
        intent.putExtra("ID",categoryId);
        intent.putExtra("Range",2);
        this.startActivity(intent);
    }
    public void openDetils3(View view) {
        Intent intent=new Intent(this,ProductActivity.class);
        intent.putExtra("ID",categoryId);
        intent.putExtra("Range",3);
        this.startActivity(intent);
    }

    public void openDetilsForOne(View view) {
        Intent intent=new Intent(this,OneProductActivity.class);
        if(view.getId()==R.id.low_range_first_important_product)
        {
            intent.putExtra("Product",importantProduct1.get(0));

        }
        else if (view.getId()==R.id.low_range_second_important_product)
        {
            intent.putExtra("Product",importantProduct1.get(1));

        }
        else if (view.getId()==R.id.low_range_third_important_product)
        {
            intent.putExtra("Product",importantProduct1.get(2));

        }
        else if(view.getId()==R.id.mid_range_first_important_product)
        {
            intent.putExtra("Product",importantProduct2.get(0));

        }
        else if (view.getId()==R.id.mid_range_second_important_product)
        {
            intent.putExtra("Product",importantProduct2.get(1));

        }
        else if (view.getId()==R.id.mid_range_third_important_product)
        {
            intent.putExtra("Product",importantProduct2.get(2));

        }
        else if(view.getId()==R.id.high_range_first_important_product)
        {
            intent.putExtra("Product",importantProduct3.get(0));

        }
        else if (view.getId()==R.id.high_range_second_important_product)
        {
            intent.putExtra("Product",importantProduct3.get(1));

        }
        else if (view.getId()==R.id.high_range_third_important_product)
        {
            intent.putExtra("Product",importantProduct3.get(2));

        }
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
}
