package com.smz.lexunuser.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smz.lexunuser.util.city.CityBean;
import com.smz.lexunuser.util.city.DistrictBean;
import com.smz.lexunuser.util.city.ProvinceBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddressEncodeUtil {
    public static String getDetail(Context context, int pId, int cId, int aId) {
        String cityJson = AddressUtils.getJson(context, "china_city_data.json");
        Type type = new TypeToken<ArrayList<ProvinceBean>>() {
        }.getType();
        ArrayList<ProvinceBean> mProvinceBeanArrayList = new ArrayList<>();
        mProvinceBeanArrayList = new Gson().fromJson(cityJson, type);
        String aa = "";

        for (ProvinceBean provinceBean : mProvinceBeanArrayList) {
            if (provinceBean.getId() == pId) {
                for (CityBean cityBean : provinceBean.getCityList()) {
                    if (cityBean.getId() == cId) {
                        for (DistrictBean districtBean : cityBean.getCityList()) {
                            if (districtBean.getId() == aId) {
                                aa = provinceBean.getName() + cityBean.getName() + districtBean.getName();
                            }
                        }
                    }
                }
            }
        }
        return aa;
    }
}
