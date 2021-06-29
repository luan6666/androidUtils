package com.smz.lexunuser.base.net;


import com.smz.lexunuser.net.BaseRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author ldx
 * @CreateDate 2020/9/3 14:59
 * @Description 返回的回调
 */
public abstract class BaseCallBack<T> implements Callback<BaseRes<T>> {
    @Override
    public void onResponse(Call<BaseRes<T>> call, Response<BaseRes<T>> response) {
        assert response.body() != null;

        if (response.code() != 200 && response.body().msg.equals("用户不存在,请先绑定手机号")) {
            fail(response.body().result.toString());
        } else {
            if (response.body().code == 200) {
                success(response.body());
            } else {
                fail(response.message());
            }
        }
    }

    public abstract void success(BaseRes<T> baseRes);

    public abstract void fail(String msg);

    @Override
    public void onFailure(Call<BaseRes<T>> call, Throwable t) {
        fail(t.getMessage());
    }
}
