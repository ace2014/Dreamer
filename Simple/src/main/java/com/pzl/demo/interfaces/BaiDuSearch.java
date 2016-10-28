package com.pzl.demo.interfaces;

import com.pzl.demo.bean.resp.ResponseBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */
public interface BaiDuSearch {
    @GET("s")
    Call<ResponseBean> search(@Query("ie") String encode, @Query("wd") String keywords);
}
