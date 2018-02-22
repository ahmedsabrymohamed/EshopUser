package com.example.eraky.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("create_category.php")
    Call<Response> createCategory(@Query("name") String name,
                                  @Query("image") String image);

    @GET("create_product.php")
    Call<Response>createProduct(@Query("Category_id") String Category_id,
                                @Query("name") String name,
                                @Query("description") String description,
                                @Query("price") String price,
                                @Query("rate") String rate,
                                @Query("image") String image,
                                @Query("prange") String prange
    );
    @GET("create_comment.php")
    Call<Response>createComment(@Query("pid") String pid,
                                @Query("body") String body,
                                @Query("cname") String cname,
                                @Query("email") String email);
    @GET("delete_comment.php")
    Call<Response> deleteComment(@Query("cid") String cid);
    @GET("delete_product.php")
    Call<Response>deleteProduct(@Query("id") String id);
    @GET("deleteCategory.php")
    Call<Response>deleteCategory(@Query("Category_id") String Category_id);
    @GET("update_comment.php")
    Call<Response> updateComment(@Query("cid") String cid,
                                 @Query("body") String body,
                                 @Query("cname") String cname,
                                 @Query("email") String email,
                                 @Query("appear") String appear);

    @GET("update_product.php")
    Call<Response>updateProduct(@Query("id") String id,
                                @Query("Category_id") String Category_id,
                                @Query("name") String name,
                                @Query("description") String description,
                                @Query("price") String price,
                                @Query("rate") String rate,
                                @Query("image") String image,
                                @Query("trend") String trend,
                                @Query("prange") String prange
    );
    @GET("update_category.php")
    Call<Response>updateCategory(@Query("name") String name,
                                 @Query("image") String image,
                                 @Query("Category_id") String Category_id);

    @GET("getAll_comments.php")
    Call<CommentsResponse> getAllComments(@Query("pid") String pid);

    @GET("getAll_product.php")
    Call<ProductsResponse>getAllProducts(@Query("Category_id") String Category_id);

    @GET("getOneProduct.php")
    Call<ProductsResponse>getOneProduct(@Query("id") String pid);

    @GET("getAll_Categories.php")
    Call<CategoriesResponse>getAllCategories();

    @GET("deleteimage.php")
    Call<Response> deleteImage(@Query("file") String image);

    @GET("getAll_CommentsAdmin.php")
    Call<CommentsResponse>getAllCommentsAdmin();

    @GET("getCommentInfo.php")
    Call<CommentInfo>getCommentInfo(@Query("pid") String pid);
    @GET("isRated.php")
    Call<RateResponse>isRated(@Query("pid") String pid, @Query("uemail") String uemail);
    @GET("updateRate.php")
    Call<RateResponse>updateRate(@Query("pid") String pid, @Query("uemail") String uemail
            , @Query("rate") String rate);

}
