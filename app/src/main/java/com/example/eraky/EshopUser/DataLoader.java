package com.example.eraky.myapplication;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;



import java.util.List;

import retrofit2.Call;


public class DataLoader {

    private Context context;
    private ApiInterface apiService ;

    public DataLoader(Context context){

        this.context=context;
        apiService =
                APIClient.getClient().create(ApiInterface.class);
    }
    public void updateComment( String cid, String body, String cname, String email,String appear){
        Call<Response> call = apiService.updateComment(cid,body,cname,email,appear);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG);

            }
        });
    }
    public void updateCategory(String name, final String image, final String Category_id,final Uri uri){

        Call<Response> call = apiService.updateCategory(name,image,Category_id);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    if(response.body().getSuccess()!=0)
                    updateImage(image,uri,0,Category_id);
                    else
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void updateProduct(final String id, String Category_id
            ,String name
            ,String description
            ,String price
            ,String rate
            ,final String image
            ,final Uri uri,
            final String trend,
            final String prange){

        Call<Response> call = apiService.updateProduct(  id,  Category_id, name, description, price
                , rate
                , image,trend,prange);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    if(response.body().getSuccess()!=0&&uri!=null)
                        updateImage(image,uri,1,id);
                    else
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void deleteComment(String id){

        Call<Response> call = apiService.deleteComment(id);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void deleteCategory(String id){

        Call<Response> call = apiService.deleteCategory(id);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void deleteProduct(final String image,String id){

        Call<Response> call = apiService.deleteProduct(id);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    if(response.body().getSuccess()!=0)
                    deleteImage(image);
                    else
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void createComment(String pid, String body, String cname, String email){

        Call<Response> call = apiService.createComment(pid,body,cname,email);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void createCategory(String name, String image,final Uri uri){

        Call<Response> call = apiService.createCategory(name,image);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    if(response.body().getSuccess()!=0)
                        new ImageUploader().uploadMultipart(0,uri
                                ,Integer.toString(response.body().getId()),context);
                    else
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void createProduct(String Category_id
            ,String name
            ,String description
            ,String price
            ,String rate
            ,String image
            ,final Uri uri
            ,final String prange)
    {
        Call<Response> call = apiService.createProduct(Category_id, name, description, price,rate,image,prange);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    if(response.body().getSuccess()!=0)
                        new ImageUploader().uploadMultipart(1,uri
                                ,Integer.toString(response.body().getId()),context);
                    else
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void updateImage(String image, final Uri uri, final int type, final String id){
        Call<Response> call = apiService.deleteImage(image);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    if(response.body().getSuccess()!=0)
                        new ImageUploader().uploadMultipart(type,uri,id,context);
                    else
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void deleteImage(String image){
        Call<Response> call = apiService.deleteImage(image);
        call.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call
                    , @NonNull retrofit2.Response<Response> response) {

                if(response.body()!=null) {
                    if(response.body().getSuccess()!=0)
                        Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                // Log error here since request failed
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                //String s=s2.substring(s2.lastIndexOf("/")+1);
            }
        });
    }
    public  void getAllCommentsAdmin(){
        Call<CommentsResponse> call = apiService.getAllCommentsAdmin();
        call.enqueue(new retrofit2.Callback<CommentsResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommentsResponse> call
                    , @NonNull retrofit2.Response<CommentsResponse> response) {

                if(response.body()!=null) {
                    List<Comment> a = response.body().getComments();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentsResponse> call, @NonNull Throwable t) {
                // Log error here since request failed

            }
        });

    }
}
