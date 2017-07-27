package com.trust.demo;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by TrustTinker on 2017/3/21.
 */
public interface GitHubInterface {
    @GET("{owner}/{repo}/{repos}")
    Call<ResponseBody> contributorsBySimpleGetCall(@Path("owner") String owner,
                                                   @Path("repo") String repo,
                                                   @Path("repos") String repos);

//    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
//    @FormUrlEncoded
    @POST("rest/gps/latest/")
    Call<ResponseBody> postResult(@Body RequestBody route);

}
