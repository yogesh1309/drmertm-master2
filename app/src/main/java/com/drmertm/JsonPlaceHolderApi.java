package com.drmertm;

import com.drmertm.ModelClass.CitiesResponse;
import com.drmertm.ModelClass.ColonyResponse;
import com.drmertm.ModelClass.DeptResponse;
import com.drmertm.ModelClass.ForgotPasswordEmpResponse;
import com.drmertm.ModelClass.ForgotpasswordOtpResponse;
import com.drmertm.ModelClass.Form16Response;
import com.drmertm.ModelClass.GrieTypeResponse;
import com.drmertm.ModelClass.GrievanceApplyResponse;
import com.drmertm.ModelClass.GrievanceWidResponse;
import com.drmertm.ModelClass.IdCardResponse;
import com.drmertm.ModelClass.IdCardStatusResponse;
import com.drmertm.ModelClass.LoginResponse;
import com.drmertm.ModelClass.LogoutResponse;
import com.drmertm.ModelClass.MyGrievanceApplyLISTResponse;
import com.drmertm.ModelClass.OtpSendResponse;
import com.drmertm.ModelClass.OtpVerifyResponse;
import com.drmertm.ModelClass.SectionResponse;
import com.drmertm.ModelClass.Signup2Response;
import com.drmertm.ModelClass.SignupResponse;
import com.drmertm.ModelClass.StateResponse;
import com.drmertm.ModelClass.StationResponse;
import com.drmertm.ModelClass.UpdateProfileResponse;
import com.drmertm.ModelClass.UserDetailResponse;
import com.drmertm.ModelClass.ViewOfficeOrderResponse;
import com.drmertm.ModelClass.WorkingStationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface JsonPlaceHolderApi {

    // For Demo Purpose
    /*
    @GET("property_list")
    Call<List<PropertListReponse>> getListproperty();

    @FormUrlEncoded
    @POST("signup")
    Call<SignupResponse> signup(@Field("user_id") String user_id, @Field("name") String name,
                                @Field("email") String email, @Field("mobile") String mobile,
                                @Field("property_ids") String property_ids,
                                @Field("block_unit_id") String block_unit_id,
                                @Field("password") String password);

     */

    @FormUrlEncoded
    @POST("signupstep1")
    Call<SignupResponse> signup(@Field("employee_id") String employee_id, @Field("pan_no") String pan_no);


    @FormUrlEncoded
    @POST("otpsend")
    Call<OtpSendResponse> otpsend(@Field("emp_id") String emp_id, @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("otpverify")
    Call<OtpVerifyResponse> otpverify(@Field("emp_id") String emp_id, @Field("mobile") String mobile,
                                    @Field("otp") String otp);

    @FormUrlEncoded
    @POST("signupstep2")
    Call<Signup2Response> signup2(@Field("employee_id") String employee_id, @Field("pan_no") String pan_no,
                                    @Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("emp_id") String emp_id, @Field("password") String password,
                                @Field("fcm_token") String fcm_token,@Field("device_id") String device_id ,@Field("device_type") String device_type);


    @FormUrlEncoded
    @POST("logout")
    Call<LogoutResponse> logout(@Field("fcm_token_id") Integer fcm_token_id);

    @FormUrlEncoded
    @POST("id_card_detail")
    Call<IdCardStatusResponse> idcardstatus(@Field("emp_id") String emp_id);


    @FormUrlEncoded
    @POST("get_detail")
    Call<UserDetailResponse> userdetail(@Field("emp_id") String emp_id);

    @FormUrlEncoded
    @POST("section_list")
    Call<SectionResponse> section(@Field("department_id") String department_id);


    @GET("department_list")
    Call<DeptResponse> getListdept();

    @GET("department_all_list")
    Call<DeptResponse> getListdeptNew();

    @GET("idcard_service")
    Call<LogoutResponse> getServicestatus();



 /*   @GET("office_order")
    Call<ViewOfficeOrderResponse> getListofficeorder();
*/

    @FormUrlEncoded
    @POST("filter_office_order")
    Call<ViewOfficeOrderResponse> getListofficeorder(@Field("filter_type") String filter_type);

    /*
    @GET("grievance_type_list")
    Call<GrieTypeResponse> getListgrietype();
     */


    @FormUrlEncoded
    @POST("grievance_type_list")
    Call<GrieTypeResponse> getListgrietype(@Field("department_id") String department_id);


    @FormUrlEncoded
    @POST("changemobile_otp_send")
    Call<OtpSendResponse> otpsend2(@Field("emp_id") String emp_id,@Field("mobile") String mobile);



    @FormUrlEncoded
    @POST("changemobile_otpverify")
    Call<OtpSendResponse> otpverify2(@Field("emp_id") String emp_id,@Field("mobile") String mobile,@Field("otp") String otp);

    @GET("station_list")
    Call<StationResponse> getListstation();

    @GET("states_list")
    Call<StateResponse> getListstates();

    @GET("working_city_list")
    Call<WorkingStationResponse> getListworkingstation();

    @FormUrlEncoded
    @POST("city_list")
    Call<CitiesResponse> getListcities(@Field("state_id") String state_id);


    @FormUrlEncoded
    @POST("colony_list")
    Call<ColonyResponse> colonylist(@Field("station_id") String station_id);


    /*
    @Multipart
    @POST("grievance_apply")
    Call<GrievanceApplyResponse> getGrievanceApply(@Part("emp_id") String emp_id,
            @Part("department_id") String department_id,
            @Part("grievancetype_id") String grievancetype_id,
            @Part("section_id") String section_id,
            @Part("description") String description,
            @Part("attach_status") String attach_status,
            @Part("current_office") String current_office,
            @Part("station_id") String station_id,
            @Part("residential_address") String residential_address,
            @Part("state_id") String state_id,
            @Part("city_id") String city_id,
            @Part MultipartBody.Part image
            );

     */

    @POST("grievance_apply")
    Call<GrievanceApplyResponse> getGrievanceApply(@Body RequestBody file);

    @POST("update_profile")
    Call<UpdateProfileResponse> editprofile(@Body RequestBody file);

    /*
     @FormUrlEncoded
    @POST("grievance_apply")
    Call<GrievanceApplyResponse> getGrievanceApply2(@Field("emp_id") String emp_id,
                                                   @Field("department_id") String department_id,
                                                   @Field("grievancetype_id") String grievancetype_id,
                                                   @Field("section_id") String section_id,
                                                   @Field("description") String description,
                                                   @Field("attach_status") String attach_status,
                                                   @Field("current_office") String current_office,
                                                   @Field("station_id") String station_id,
                                                   @Field("residential_address") String residential_address,
                                                   @Field("state_id") String state_id,
                                                   @Field("city_id") String city_id,
                                                   @Field("attachment") String attachment
    );

     */

    @FormUrlEncoded
    @POST("grievance_apply")
    Call<GrievanceApplyResponse> getGrievanceApply2(@Field("emp_id") String emp_id,
                                                    @Field("department_id") String department_id,
                                                    @Field("grievancetype_id") String grievancetype_id,
                                                    @Field("working_city_id") String working_city_id,
                                                    @Field("colony_id") String colony_id,
                                                    @Field("description") String description,
                                                    @Field("attach_status") String attach_status,
                                                    @Field("current_office") String current_office,
                                                    @Field("station_id") String station_id,
                                                    @Field("residential_address") String residential_address,
                                                    @Field("state_id") String state_id,
                                                    @Field("city_id") String city_id,
                                                    @Field("attachment") String attachment
    );


    @FormUrlEncoded
    @POST("mygrievancelist")
    Call<MyGrievanceApplyLISTResponse> getGrievanceList(@Field("emp_id") String emp_id);


    @FormUrlEncoded
    @POST("forgotpassword")
    Call<ForgotPasswordEmpResponse> forgotpassword(@Field("emp_id") String emp_id);

    @FormUrlEncoded
    @POST("resetpassword")
    Call<ForgotpasswordOtpResponse> forgotpasswordotp(@Field("emp_id") String emp_id,@Field("otp") String otp,@Field("password") String password);

    @FormUrlEncoded
    @POST("grievance_withdrawal")
    Call<GrievanceWidResponse> grievancewid(@Field("emp_id") String emp_id, @Field("grievanceapply_id") String grievanceapply_id);


    @FormUrlEncoded
    @POST("form16")
    Call<Form16Response> form16(@Field("emp_id") String emp_id, @Field("year") String year);

    @FormUrlEncoded
    @POST("confidential_report")
    Call<Form16Response> confidentialreport(@Field("emp_id") String emp_id, @Field("year") String year);

    @Multipart
    @POST("idcard_generate")
    Call<IdCardResponse> idcardsubmit(@PartMap Map<String, RequestBody> data, @Part MultipartBody.Part emp_id,@Part MultipartBody.Part emp_sign,@Part List<MultipartBody.Part> multipledocs );


    /*@Multipart
    @POST("api/H1/update_vulcanizer")
    Call<ProfileUpdateResponse> updateProfilevulcanizer(@PartMap Map<String, RequestBody> data, @Part MultipartBody.Part image);*/

}
