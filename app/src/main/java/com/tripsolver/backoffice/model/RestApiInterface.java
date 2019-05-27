package com.tripsolver.backoffice.model;

/**
 * Created by lenovo on 4/24/2019.
 */

import com.tripsolver.backoffice.view.ReportsData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RestApiInterface {

    @POST("Bookingactivity")

    @FormUrlEncoded
    Call<ResponseBody> savePost(
                                @Field("b2c_idn") String b2cid,
                              @Field("booking_status") String bookingstatus

                            );

    @POST("tripsol_bk_login")
    @FormUrlEncoded
    Call<ResponseBody> loginPost(
            @Field("username") String usernamevalue,
            @Field("password") String passwordvalue,
            @Field("bof_name") String bofname,
            @Field("user_ip") String userip

    );

    @POST("bk_Faredetails")
    @FormUrlEncoded
    Call<ResponseBody> faredetPost(
            @Field("itin_sel_id") String usernamevalue

    );

    @POST("Profile_bk")
    @FormUrlEncoded
    Call<ResponseBody> myprofilepost(
            @Field("agency_idn") String agencyid

    );


    @POST("bk_customerdetails")
    @FormUrlEncoded
    Call<ResponseBody> passengerdetService(
            @Field("bookingid") String bookingid,

            @Field("b2c_idn") String b2cid

    );

    @POST("Reports_bk")
    @FormUrlEncoded
    Call<ResponseBody> reportsdata(

            @Field("b2c_idn") String b2cid

    );

    @Headers({"Content-Type: application/json"})
    @POST("bookingReports_bk")
    Call<ResponseBody> bookingreportsdata(
            @Body ReportsData.FooRequest body);

    @POST("viewitineries")
    @FormUrlEncoded
    Call<ResponseBody> viewItinerary(

            @Field("itin_sel_idn") String itinid

    );

    @POST("activitescount_bk")
    @FormUrlEncoded
    Call<ResponseBody> activityaccount(

            @Field("days") String days,
            @Field("b2c_idn") String b2cid

    );

    @POST("Viewmultycityiten")
    @FormUrlEncoded
    Call<ResponseBody> viewMulticityItinerary(

            @Field("itin_sel_idn") String itinid,

            @Field("triptype") String triptype

    );



/*    @GET("")
    Call<ResponseBody> ipAdressResponse();*/

    @GET
    public Call<ResponseBody> ipAdressResponse(@Url String url);

   /* @GET("movie/top_rated")
    Call<TicketBookingsResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<TicketBookingsResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}