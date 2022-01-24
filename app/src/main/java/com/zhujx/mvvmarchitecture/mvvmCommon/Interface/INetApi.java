package com.zhujx.mvvmarchitecture.mvvmCommon.Interface;




import com.zhujx.mvvmarchitecture.mvvmCommon.network.ApiBase;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by andya on 2019-06-13
 * Describe: qiyukf api
 */
public interface INetApi {

    @POST("/chat/api/zhice/msg/send")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Call<ApiBase<Boolean>> sendMsgRecommend(@Body RequestBody requestBody, @Query("token") String token);
}
