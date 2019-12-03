package com.techno.takhdimprovider.Interfaces;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by ritesh on 29/9/18.
 */

public interface LoadInterface {


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("login")
    Call<ResponseBody> login(@Query("mobile") String mobile,
                             @Query("lat") String lat,
                             @Query("lon") String lon,
                             @Query("type") String type,
                             @Query("register_id") String register_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("verify_otp")
    Call<ResponseBody> verify_otp(@Query("user_id") String user_id,
                                  @Query("otp") String otp);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_my_parts")
    Call<ResponseBody> get_my_parts(@Query("provider_id") String provider_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("category")
    Call<ResponseBody> category();

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_part_booking")
    Call<ResponseBody> get_part_booking(@Query("provider_id") String provider_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("services_list")
    Call<ResponseBody> services_list(@Query("category_id") String category_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_parts")
    Call<ResponseBody> get_parts(@Query("cat_id") String cat_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_request_details")
    Call<ResponseBody> get_request_details(@Query("request_id") String request_id);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_Provider_request")
    Call<ResponseBody> get_Provider_request(@Query("provider_id") String provider_id,
                                            @Query("status") String status);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("add_request_offer")
    Call<ResponseBody> add_request_offer(@Query("provider_id") String provider_id,
                                         @Query("request_id") String request_id,
                                         @Query("cost") String cost,
                                         @Query("time") String time,
                                         @Query("details") String details);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("add_report")
    Call<ResponseBody> add_report(@Query("provider_id") String provider_id,
                                         @Query("request_id") String request_id,
                                         @Query("fullname") String fullname,
                                         @Query("email") String email,
                                         @Query("details") String details);



    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update_profile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @Multipart
    @POST("provider_signup")
    Call<ResponseBody> signup(@Query("name") String name,
                              @Query("rp_number") String rp_number,
                              @Query("email") String email,
                              @Query("company_name") String company_name,
                              @Query("cr_number") String cr_number,
                              @Query("mobile") String mobile,
                              @Query("authority_name") String authority_name,
                              @Query("auth_pr_no") String auth_pr_no,
                              @Query("com_email") String com_email,
                              @Query("lat") String lat,
                              @Query("lon") String lon,
                              @Query("types") String type,
                              @Query("register_id") String register_id,
                              @Part MultipartBody.Part body,
                              @Part MultipartBody.Part body2);


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update_profile %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @Multipart
    @POST("update_profile")
    Call<ResponseBody> updateProfile(@Query("user_id") String user_id,
                                     @Query("username") String username,
                                     @Query("mobile") String mobile,
                                     @Query("email") String email,
                                     @Query("address") String address,
                                     @Part MultipartBody.Part body);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_profile")
    Call<ResponseBody> get_profile(@Query("user_id") String user_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("get_conversation")
    Call<ResponseBody> getConversetion(@Query("sender_id") String sender_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("get_chat")
    Call<ResponseBody> getChat1(@Query("sender_id") String sender_id,
                                @Query("receiver_id") String receiver_id);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @GET("insert_chat")
    Call<ResponseBody> insertChat(@Query("sender_id") String sender_id,
                                  @Query("receiver_id") String receiver_id,
                                  @Query("chat_message") String chat_message);

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% login %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("add_parts")
    Call<ResponseBody> add_parts(@Query("provider_id") String provider_id,
                             @Query("parts_id") String parts_id,
                             @Query("company_name") String company_name,
                             @Query("warranty") String warranty,
                             @Query("price") String price,
                             @Query("description") String description);

    @POST("get_comment")
    Call<ResponseBody> getComment(@Query("service_id") String service_id);
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% add_comment %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    @POST("add_comment")
    Call<ResponseBody> addComment(@Query("service_id") String service_id,@Query("user_id") String user_id,@Query("comment") String comment);
}
