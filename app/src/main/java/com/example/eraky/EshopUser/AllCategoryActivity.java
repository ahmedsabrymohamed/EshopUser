package com.example.eraky.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllCategoryActivity extends AppCompatActivity implements CategoryAdabter.SetOncLickListener {
    private RecyclerView categoryRecyclerView;
    private CategoryAdabter adabter;
    GridLayoutManager layoutManager;
    ArrayList<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
        categories=new ArrayList<>();
        if(savedInstanceState!=null) {
            categories = savedInstanceState.getParcelableArrayList("Data");

        }else{
            getData();
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int categoryNumber=((int)dpWidth/101);
        getData();
        adabter=new CategoryAdabter(categories,this,this);
        categoryRecyclerView=(RecyclerView) findViewById(R.id.category_list);
        layoutManager=new GridLayoutManager(this,categoryNumber);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setAdapter(adabter);

    }

    private void getData() {
        ApiInterface apiService =
                APIClient.getClient().create(ApiInterface.class);

        Call<CategoriesResponse> call = apiService.getAllCategories();
        call.enqueue(new retrofit2.Callback<CategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call
                    , @NonNull retrofit2.Response<CategoriesResponse> response) {

                List<Category> a = response.body().getCategories();
                categories.clear();
                if(a!=null)
                categories.addAll(a);
                adabter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    public void SetOnclick(Category category) {
        Intent intent=new Intent(this,RangeActivity.class);
        intent.putExtra("ID",category.getCategory_id());
        this.startActivity(intent);
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
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("Data", categories);
    }
}
