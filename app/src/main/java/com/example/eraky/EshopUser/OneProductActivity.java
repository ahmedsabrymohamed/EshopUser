package com.example.eraky.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

public class OneProductActivity extends AppCompatActivity implements CommentAdabter.SetOncLickListener {
    private ArrayList<Comment> comments;
    private CommentAdabter adabter;
    private LinearLayoutManager layoutManager;
    RecyclerView commentRecyclerView;
    private Product product;
    ImageView imageView;
    RatingBar ratingBar;
    myDbAdapter helper;
    FirebaseAuth mAuth;
    Button rateIt;
    Button add_comment;
    int fav;
    EditText editText;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_product);
        mAuth=FirebaseAuth.getInstance();
        imageView2=findViewById(R.id.LoveProduct);
        rateIt=findViewById(R.id.rateIt);
        add_comment=findViewById(R.id.add_comment);
        product=getIntent().getParcelableExtra("Product");
        if(savedInstanceState!=null)
        {
            rateIt.setVisibility(savedInstanceState.getInt("rateIt"));
        }
        else{
            isRating();
        }
        editText=(EditText) findViewById(R.id.commentText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(editText.getText().toString().trim().isEmpty())
                        add_comment.setEnabled(false);
                    else
                        add_comment.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imageView=(ImageView) findViewById(R.id.product_details_image) ;
        helper = new myDbAdapter(this);
        String data = helper.getData();
        List<String> Mylist=new ArrayList<>(Arrays.asList(data.split(" ")));
        fav=0;
        for(int i=0;i<Mylist.size();i++)
        {
            if(Mylist.get(i).equals(Integer.toString(product.getId())))
            {
                fav=1;
                Picasso.with(this).load(R.drawable.like).into(imageView2);
                break;
            }
        }
        Picasso.with(this).load(product.getImage()).resize(200,200).placeholder(R.drawable.no_image).into(imageView);
        TextView textView=(TextView) findViewById(R.id.product_details_name);
        textView.setText(product.getName());
        TextView textView1=(TextView) findViewById(R.id.Description);
        textView1.setText(product.getDescription());
        ratingBar=(RatingBar) findViewById(R.id.product_rate);
        ratingBar.setRating((float) product.getRate());

        comments=new ArrayList<>();
        commentRecyclerView=(RecyclerView) findViewById(R.id.comment_list);
        layoutManager=new LinearLayoutManager(this);
        adabter=new CommentAdabter(comments,this,this);
        commentRecyclerView.setLayoutManager(layoutManager);
        commentRecyclerView.setAdapter(adabter);
    }

    private void getCommentData() {



    }

    @Override
    public void SetOnclick(Comment comment) {

    }
    @Override
    protected void onStart() {
        super.onStart();
        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<CommentsResponse> call = apiService.getAllComments(Integer.toString(product.getId()));
        call.enqueue(new retrofit2.Callback<CommentsResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommentsResponse> call
                    , @NonNull retrofit2.Response<CommentsResponse> response) {
                List<Comment> a=response.body().getComments();
                comments.clear();
                if(a!=null) {
                    for(int i=0;i<a.size();i++)
                    {
                        if(a.get(i).getAppear()==1)
                            comments.add(a.get(i));
                    }
                }
                adabter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<CommentsResponse> call, @NonNull Throwable t) {
                // Log error here since request failed

            }
        });
    }

    public void AddComment(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null&&!editText.getText().toString().equals("")) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String s=editText.getText().toString();
            DataLoader dataLoader=new DataLoader(this);
            dataLoader.createComment(Integer.toString(product.getId()),s,name,email);
            Toast.makeText(this,"Your comment Under Review",Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(this,"Please make shure to write a comment First",Toast.LENGTH_LONG).show();

        }
    }

    public void changeRate(View view) {
        //Toast.makeText(this,"Thank You For The Rate",Toast.LENGTH_LONG).show();
        rateIt.setVisibility(View.GONE);
        updateRating();
    }
    ImageView imageView2;
    public void MakeFavourit(View view) {
        if(fav==0)
        {
            Picasso.with(this).load(R.drawable.like).into(imageView2);
            String pid=Integer.toString(product.getId());
            long id = helper.insertData(pid);
            if(id<=0)
            {
                Toast.makeText(this,"Not Added something Wrong",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"Added To favorite",Toast.LENGTH_LONG).show();
                fav=1;
            }
        }
        else if(fav==1)
        {
            Picasso.with(this).load(R.drawable.dislike).into(imageView2);
            String pid=Integer.toString(product.getId());
            int a=helper.delete(pid);
            if(a<=0)
            {
                Toast.makeText(this,"Not Added something Wrong",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(this,"Delete from favorite",Toast.LENGTH_LONG).show();
                fav=0;
            }

        }



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
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateRating() {
        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<RateResponse> call = apiService.updateRate(Integer.toString(product.getId())
                ,mAuth.getCurrentUser().getEmail(),Float.toString(ratingBar.getRating()));
        call.enqueue(new retrofit2.Callback<RateResponse>() {
            @Override
            public void onResponse(@NonNull Call<RateResponse> call
                    , @NonNull retrofit2.Response<RateResponse> response) {

                if(response.body()!=null)
                {
                    ratingBar.setRating(response.body().getRate());
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onFailure(@NonNull Call<RateResponse> call, @NonNull Throwable t) {
            }
        });

    }
    private void isRating() {

        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        Call<RateResponse> call = apiService.isRated(Integer.toString(product.getId())
                ,"\""+mAuth.getCurrentUser().getEmail()+"\"");
        call.enqueue(new retrofit2.Callback<RateResponse>() {
            @Override
            public void onResponse(@NonNull Call<RateResponse> call
                    , @NonNull retrofit2.Response<RateResponse> response) {


                if(response.body()!=null) {
                    if(response.body().getSuccess()!=0)
                        rateIt.setVisibility(response.body().getRate()==0?View.VISIBLE:View.GONE);
                    else
                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(@NonNull Call<RateResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("rateIt",rateIt.getVisibility());

    }
}
