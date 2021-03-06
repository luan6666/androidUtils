package com.smz.lexunuser.net;


import com.smz.lexunuser.bean.FileBean;
import com.smz.lexunuser.bean.SearchInfoBean;
import com.smz.lexunuser.bean.VersionBean;
import com.smz.lexunuser.bean.WxPayBean;
import com.smz.lexunuser.ui.address.AddressBean;
import com.smz.lexunuser.ui.after_sales.ReturnDetailGoodsBean;
import com.smz.lexunuser.ui.arrears.AccountBean;
import com.smz.lexunuser.ui.arrears.ArrearsDetailBean;
import com.smz.lexunuser.ui.coupon.CenterListBean;
import com.smz.lexunuser.ui.coupon.CouponsBean;
import com.smz.lexunuser.ui.coupon.OrderCouponBean;
import com.smz.lexunuser.ui.fragment_home.car.CarBean;
import com.smz.lexunuser.ui.fragment_home.classify.ClassBean;
import com.smz.lexunuser.ui.fragment_home.home.HomeBean;
import com.smz.lexunuser.ui.fragment_home.home.HomeGoodsListBean;
import com.smz.lexunuser.ui.fragment_home.home.HomeStaffListBean;
import com.smz.lexunuser.ui.fragment_home.home.HomeStoreListBean;
import com.smz.lexunuser.ui.fragment_home.mine.MineOrderBean;
import com.smz.lexunuser.ui.general.CommentTagBean;
import com.smz.lexunuser.ui.general.PayInfoBean;
import com.smz.lexunuser.ui.goods.GoodsDetailBean;
import com.smz.lexunuser.ui.goods.GoodsEvaluateBean;
import com.smz.lexunuser.ui.login.CreateImUser;
import com.smz.lexunuser.ui.login.UserInfo;
import com.smz.lexunuser.ui.main.LoadingBean;
import com.smz.lexunuser.ui.msg.ChatBean;
import com.smz.lexunuser.ui.msg.CreateImBean;
import com.smz.lexunuser.ui.msg.MsgListBean;
import com.smz.lexunuser.ui.old_phone.OldDetailBean;
import com.smz.lexunuser.ui.old_phone.OldGoodsItemBean;
import com.smz.lexunuser.ui.old_phone.OldGoodsListBean;
import com.smz.lexunuser.ui.old_phone.OldInfoDetailBean;
import com.smz.lexunuser.ui.old_phone.OldPhoneListBean;
import com.smz.lexunuser.ui.order.ConfirmOrderBean;
import com.smz.lexunuser.ui.order.GoodsBean;
import com.smz.lexunuser.ui.order.GoodsItemBean;
import com.smz.lexunuser.ui.order.OrderBean;
import com.smz.lexunuser.ui.order.OrderDetailBean;
import com.smz.lexunuser.ui.pre.PreGoodsDetailBean;
import com.smz.lexunuser.ui.pre.PreGoodsListBean;
import com.smz.lexunuser.ui.pre.PreListBean;
import com.smz.lexunuser.ui.pre.PreOrderDetailBean;
import com.smz.lexunuser.ui.repair.RepairDetailBean;
import com.smz.lexunuser.ui.repair.RepairGoodsInfoBean;
import com.smz.lexunuser.ui.repair.RepairListBean;
import com.smz.lexunuser.ui.service.ReturnGoodsBean;
import com.smz.lexunuser.ui.tax.TaxBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @Author ldx
 * @CreateDate 2020/8/25 14:47
 * @Description ????????????
 */
public interface ApiService {
    /**
     * ????????????
     *
     * @param type
     * @return status ???1 ???????????? ?????????address
     */
    @POST("/api/appVersion")
    @NotNull
    Call<BaseRes<VersionBean>> versionControl(@Query("type") int type);

    /**
     * ???????????????
     *
     * @param token
     * @param code
     * @return status ???1 ???????????? ?????????address
     */
    @POST("/api/mbUserInfo/setExtend")
    Call<BaseRes<String>> changeExtend(@Query("token") String token, @Query("code") String code);

    /**
     * ????????????
     */
    @GET("/api/kaijiAd")
    Call<BaseRes<LoadingBean>> getLoadingList();

    /**
     * ????????????
     *
     * @param code
     * @return
     */
    @POST("/api/wxUserInfo")
    Call<BaseRes<UserInfo>> loginWx(@Query("code") String code, @Query("type") int type);

    /**
     * ????????????
     *
     * @param /api/common/upload
     * @return
     */
    @Multipart
    @POST("/api/common/upload")
    Call<BaseRes<FileBean>> uploadImage(@Part MultipartBody.Part file);


    /**
     * ???????????????,?????????????????????????????????,??????????????????????????????????????????
     *
     * @param aliOpenId
     * @return
     */
    @POST("/api/memberAliLogin")
    Call<BaseRes<UserInfo>> retailAliLogin(@Query("aliOpenId") String aliOpenId);

    /**
     * ????????????????????????????????????
     *
     * @param id        ?????????openId /??????openID
     * @param phone     ?????????
     * @param phonecode ?????????
     * @param type      ????????????1????????????0
     * @return
     */
    @POST("/api/memberBindPhone")
    Call<BaseRes<UserInfo>> retailBindPhone(@Query("id") String id,
                                            @Query("unionid") String unionid,
                                            @Query("phone") String phone,
                                            @Query("phonecode") String phonecode,
                                            @Query("type") String type);

    /**
     * ???????????????????????????
     *
     * @param cate_id cate_id
     * @return
     */
    @POST("/api/goodsListByCate")
    Call<BaseRes<List<OldGoodsItemBean>>> goodsChildList(@Query("cate_id") int cate_id, @Query("type") int type, @Query("key") String key);

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @POST("/api/goodsSearchInfo")
    Call<BaseRes<List<OldGoodsListBean>>> goodsTopList();

    /**
     * ??????????????????
     *
     * @param token
     * @param key       ?????????
     * @param goods     ??????
     * @param cate      ??????
     * @param attr      ??????
     * @param lowPrice  ?????????
     * @param hignPrice ?????????
     * @return
     */
    @POST("/api/mbMemberGoods/goodsList")
    Call<BaseRes<List<GoodsBean>>> homeGoodsList(@Query("token") String token,
                                                 @Query("key") String key,
                                                 @Query("goods_brand[]") List<String> goods,
                                                 @Query("goods_cate[]") List<String> cate,
                                                 @Query("goods_attr[]") List<String> attr,
                                                 @Query("price_di") String lowPrice,
                                                 @Query("price_gao") String hignPrice,
                                                 @Query("page") String page,
                                                 @Query("size") String size
    );

    /**
     * ????????????
     *
     * @return
     */
    @POST("/api/goodsSearchInfo")
    Call<BaseRes<List<ClassBean>>> homeSearchInfo();

    /**
     * ????????????
     *
     * @return
     */
    @POST("api/mbMemberIndex/searchInfo")
    Call<BaseRes<SearchInfoBean>> searchInfo();


    /**
     * ????????????
     *  ???????????? /api/mbMemberGoods/detail
     * @param id ??????id
     * @return
     */
    @POST("api/mbMemberGoods/detailNewStructure")
    Call<BaseRes<GoodsDetailBean>> goodsDetail(@Query("token") String token, @Query("id") int id);

    /**
     * ??????????????????
     *
     * @param id ??????id
     * @return
     */
    @POST("/api/mbPresale/detail")
    Call<BaseRes<PreGoodsDetailBean>> preGoodsDetail(@Query("token") String token, @Query("id") int id);

    /**
     * ?????????????????????
     *
     * @param token
     * @param id    ??????id
     * @return
     */
    @POST("/api/mbPresale/payDingJin")
    Call<BaseRes<WxPayBean>> prePayDepositWx(@Query("token") String token, @Query("id") int id, @Query("pay_way") int pay_way);

    /**
     * ?????????????????????
     *
     * @param token
     * @param id    ??????id
     * @return
     */
    @POST("/api/mbPresale/payDingJin")
    Call<BaseRes<String>> prePayDepositAli(@Query("token") String token, @Query("id") int id, @Query("pay_way") int pay_way);

    /**
     * ???????????????
     *
     * @param token
     * @param id    ??????id
     * @return
     */
    @POST("/api/mbPresale/create")
    Call<BaseRes<String>> createPreOrder(@Query("token") String token,
                                         @Query("id") int id,
                                         @Query("num") int num,
                                         @Query("presale_goods_id") int presale_goods_id);

    /**
     * ???????????????
     *
     * @param token
     * @param id    ??????id
     * @return
     */
    @POST("/api/mbPresale/orderDetail")
    Call<BaseRes<PreOrderDetailBean>> preOrderDetail(@Query("token") String token,
                                                     @Query("id") int id);


    /**
     * ?????????????????????
     *
     * @param phone
     * @param phonecode
     * @return
     */
    @POST("/api/memberPhoneLogin")
    Call<BaseRes<UserInfo>> loginByCode(@Query("phone") String phone, @Query("phonecode") String phonecode);

    /**
     * ???????????????
     *
     * @param phone
     * @return
     */
    @POST("/api/phonecode")
    Call<BaseRes<String>> sendCode(@Query("phone") String phone);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("/api/mbUserInfo/userInfo")
    Call<BaseRes<UserInfo>> getUserInfo(@Query("token") String token, @Query("lng") Double lng, @Query("lat") Double lat);

    /**
     * ??????id???????????????????????????
     *
     * @param goods_id ??????ID
     * @return
     */
    @POST("/api/mbOldGoodsHome/goodsAssessInfo")
    Call<BaseRes<OldInfoDetailBean>> oldGoodsInfoDetail(@Query("goods_id") int goods_id);

    /**
     * ??????id???????????????????????????
     *
     * @param goods_id ??????ID
     * @return
     */
    @POST("/api/mbOldGoodsHome/submitAssess")
    Call<BaseRes<String>> submitOldGoodsInfo(
            @Query("token") String token,
            @Query("goods_id") int goods_id,
            @Query("itemArr[]") List<String> itemArr,
            @Query("attrArr[]") List<String> attrArr,
            @Query("thumbArr[]") List<String> thumbArr
    );

    /**
     * ?????????????????????
     *
     * @param token
     * @param status
     * @param page
     * @param size
     * @return
     */
    @POST("/api/mbOldGoodsHome/assessList")
    Call<BaseRes<List<OldPhoneListBean>>> oldPhoneList(
            @Query("token") String token,
            @Query("status") int status,
            @Query("page") int page,
            @Query("size") int size,
            @Query("key") String key
    );

    /**
     * ?????????????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbOldGoodsHome/assessDetail")
    Call<BaseRes<OldDetailBean>> assessDetail(
            @Query("token") String token,
            @Query("assess_id") int assess_id);

    /**
     * ?????????
     *
     * @param token
     * @param status
     * @return
     */
    @POST("/api/mbCoupon/list")
    Call<BaseRes<List<CouponsBean>>> getCouponList(
            @Query("token") String token,
            @Query("status") int status
    );

    /**
     * ??????????????????
     *
     * @param token
     * @param page
     * @param size
     * @return
     */
    @POST("/api/mbCoupon/centerList")
    Call<BaseRes<CenterListBean>> centerList(
            @Query("token") String token,
            @Query("page") int page,
            @Query("size") int size
    );

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbCoupon/memberGetCoupon")
    Call<BaseRes<String>> getCoupon(
            @Query("token") String token,
            @Query("coupon_id") int coupon_id
    );

    /**
     * ?????????????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbCoupon/addToOrder")
    Call<BaseRes<String>> couponToOrder(
            @Query("token") String token,
            @Query("order_id") int order_id,
            @Query("coupon_id_arr[]") List<String> coupon_id_arr
    );

    /**
     * ???????????????????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbCoupon/allCoupon")
    Call<BaseRes<List<OrderCouponBean>>> searchCouponToOrder(
            @Query("token") String token,
            @Query("order_id") int order_id
    );

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbAddress/list")
    Call<BaseRes<List<AddressBean>>> addressList(@Query("token") String token);

    /**
     * ????????????
     *
     * @param token       ?????????ID
     * @param province_id ???
     * @param city_id     ???
     * @param area_id     ???
     * @param address     ????????????
     * @param name        ??????
     * @param phone       ?????????
     * @param is_default  ????????????1???0???
     * @return
     */
    @POST("/api/mbAddress/create")
    Call<BaseRes<String>> createAddress(@Query("token") String token,
                                        @Query("province_id") int province_id,
                                        @Query("city_id") int city_id,
                                        @Query("area_id") int area_id,
                                        @Query("address") String address,
                                        @Query("name") String name,
                                        @Query("phone") String phone,
                                        @Query("is_default") int is_default

    );

    /**
     * ????????????
     *
     * @param id          ??????id
     * @param province_id ???
     * @param city_id     ???
     * @param area_id     ???
     * @param address     ????????????
     * @param name        ??????
     * @param phone       ?????????
     * @return
     */
    @POST("/api/mbAddress/edit")
    Call<BaseRes<String>> editAddress(@Query("token") String token,
                                      @Query("id") String id,
                                      @Query("province_id") int province_id,
                                      @Query("city_id") int city_id,
                                      @Query("area_id") int area_id,
                                      @Query("address") String address,
                                      @Query("name") String name,
                                      @Query("phone") String phone,
                                      @Query("is_default") int is_default
    );

    /**
     * ??????????????????
     *
     * @param token token
     * @param id    ????????????ID
     * @return
     */
    @POST("/api/mbAddress/default")
    Call<BaseRes<String>> defaultAddress(@Query("token") String token, @Query("id") String id);

    /**
     * ??????????????????
     *
     * @param id ??????id
     * @return
     */
    @POST("/api/mbAddress/detail")
    Call<BaseRes<AddressBean>> addressDetail(@Query("id") String id);

    /**
     * ????????????
     *
     * @param token token
     * @param id    ??????id
     * @return
     */
    @POST("/api/mbAddress/remove")
    Call<BaseRes<String>> deleteAddress(@Query("token") String token, @Query("id") String id);

    /**
     * ???????????????
     *
     * @param token    token
     * @param goods_id ??????ID
     * @param item_id  ????????????
     * @param num      ??????
     * @return
     */
    @POST("/api/mbCar/add")
    Call<BaseRes<List<CarBean>>> addShopCar(@Query("token") String token,
                                            @Query("goods_id") String goods_id,
                                            @Query("item_id") String item_id,
                                            @Query("num") String num,
                                            @Query("virtual[]") List<String> virtual

    );

    /**
     * ?????????????????????
     *
     * @param token token
     * @param id    ?????????id
     * @param num   ??????
     * @return
     */
    @POST("/api/mbCar/changeNum")
    Call<BaseRes<List<CarBean>>> editShopCarNum(@Query("token") String token,
                                                @Query("id") String id,
                                                @Query("num") String num);

    /**
     * ???????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbCar/detail")
    Call<BaseRes<List<CarBean>>> shopCarList(@Query("token") String token);

    /**
     * ???????????????????????????
     *
     * @param token token
     * @param aa    ?????????ID??????
     * @return
     */
    @POST("/api/mbOrder/settle")
    Call<BaseRes<String>> goSettle(@Query("token") String token, @Query("cartids[]") Object[] aa);

    /**
     * ????????????
     *
     * @param token token
     * @param id    ??????id
     * @return
     */
    @POST("/api/mbOrder/detail")
    Call<BaseRes<ConfirmOrderBean>> getConfirmOrderDetail(@Query("token") String token, @Query("id") int id);

    /**
     * ????????????
     *
     * @param token token
     * @param id    ??????id
     * @return
     */
    @POST("/api/mbOrder/detail")
    Call<BaseRes<OrderDetailBean>> getOrderDetail(@Query("token") String token, @Query("id") int id);

    /**
     * ??????????????????
     *
     * @param token              token
     * @param buyer_name         ?????????
     * @param buyer_tax          ????????????????????????
     * @param buyer_address      ????????????
     * @param buyer_phone        ????????????
     * @param buyer_bank         ??????????????????
     * @param buyer_bank_account ??????????????????
     * @return
     */
    @POST("/api/mbBill/create")
    Call<BaseRes<String>> createTax(@Query("token") String token,
                                    @Query("buyer_name") String buyer_name,
                                    @Query("buyer_tax") String buyer_tax,
                                    @Query("buyer_address") String buyer_address,
                                    @Query("buyer_phone") String buyer_phone,
                                    @Query("buyer_bank") String buyer_bank,
                                    @Query("buyer_bank_account") String buyer_bank_account

    );

    /**
     * ??????????????????
     *
     * @param token              token
     * @param id                 ????????????ID
     * @param buyer_name         ?????????
     * @param buyer_tax          ????????????????????????
     * @param buyer_address      ????????????
     * @param buyer_phone        ????????????
     * @param buyer_bank         ??????????????????
     * @param buyer_bank_account ??????????????????
     * @return
     */
    @POST("/api/mbBill/edit")
    Call<BaseRes<String>> editTax(@Query("token") String token,
                                  @Query("id") String id,
                                  @Query("buyer_name") String buyer_name,
                                  @Query("buyer_tax") String buyer_tax,
                                  @Query("buyer_address") String buyer_address,
                                  @Query("buyer_phone") String buyer_phone,
                                  @Query("buyer_bank") String buyer_bank,
                                  @Query("buyer_bank_account") String buyer_bank_account);


    /**
     * ??????????????????
     *
     * @param token token
     * @param id    ????????????ID
     * @return
     */
    @POST("/api/mbBill/remove")
    Call<BaseRes<String>> deleteTax(@Query("token") String token,
                                    @Query("id") String id);

    /**
     * ??????????????????
     *
     * @param token      token
     * @param order_id   ??????ID
     * @param bill_class 1,???????????????2????????????????????????
     * @param type       1,?????????2?????????
     * @param bill_id    ??????type ???2 ????????????????????? ??????ID
     * @param take_type  bill_class ???2 ?????????0,????????????1????????????
     * @param address_id bill_class ???2 ?????????take_type 1???????????? ????????????iD
     * @return
     */
    @POST("/api/mbOrderBill/askForOrderBill")
    Call<BaseRes<Object>> orderCreateTax(@Query("token") String token,
                                         @Query("order_id") int order_id,
                                         @Query("bill_class") int bill_class,
                                         @Query("type") int type,
                                         @Query("bill_id") int bill_id,
                                         @Query("take_type") int take_type,
                                         @Query("address_id") int address_id

    );

    /**
     * ??????????????????
     *
     * @param token      token
     * @param order_id   ??????ID
     * @param bill_class 1,???????????????2????????????????????????
     * @param type       1,?????????2?????????
     * @param bill_id    ??????type ???2 ????????????????????? ??????ID
     * @param take_type  bill_class ???2 ?????????0,????????????1????????????
     * @param address_id bill_class ???2 ?????????take_type 1???????????? ????????????iD
     * @return
     */
    @POST("/api/mbOrderBill/askForFixBill")
    Call<BaseRes<String>> fixCreateTax(@Query("token") String token,
                                       @Query("order_id") int order_id,
                                       @Query("bill_class") int bill_class,
                                       @Query("type") int type,
                                       @Query("bill_id") int bill_id,
                                       @Query("take_type") int take_type,
                                       @Query("address_id") int address_id

    );

    /**
     * ??????????????????
     *
     * @param token    token
     * @param order_id ??????ID
     * @param type     1,?????????????????????2????????????
     * @return
     */
    @POST("/api/mbOrderBill/orderBill")
    Call<BaseRes<TaxBean>> orderBill(@Query("token") String token,
                                     @Query("order_id") String order_id,
                                     @Query("type") String type
    );

    /**
     * ????????????????????????
     *
     * @param token token
     * @param id    ????????????ID
     * @return
     */
    @POST("/api/mbBill/detail")
    Call<BaseRes<TaxBean>> detailTax(@Query("token") String token,
                                     @Query("id") String id);

    /**
     * ????????????????????????
     *
     * @param token token
     * @return
     */
    @POST("/api/mbBill/list")
    Call<BaseRes<List<TaxBean>>> listTax(@Query("token") String token);

    /**
     * ????????????????????????
     *
     * @param token token
     * @param id    ????????????ID
     * @return
     */
    @POST("/api/mbBill/default")
    Call<BaseRes<String>> defaultTax(@Query("token") String token,
                                     @Query("id") String id);

    /**
     * ??????????????????
     *
     * @param store_id store_id
     * @return
     */
    @POST("/api/mbPresale/list")
    Call<BaseRes<List<PreGoodsListBean>>> preList(@Query("store_id") int store_id);

    /**
     * ??????????????????
     *
     * @param token token
     * @return
     */
    @POST("/api/mbPresale/orderList")
    Call<BaseRes<List<PreListBean>>> preOrderList(@Query("token") String token, @Query("status") int status);

    /**
     * ????????????????????????????????????
     *
     * @param token token
     * @param type  1,????????????2?????????
     * @param num   ??????
     * @param name  ??????
     * @return
     */
    @POST("/api/mbOldGoodsOrder/editMemberAccount")
    Call<BaseRes<String>> editMemberAccount(
            @Query("token") String token,
            @Query("type") int type,
            @Query("num") String num,
            @Query("name") String name);

    /**
     * ????????????????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbOldGoodsOrder/findMemberAccount")
    Call<BaseRes<List<PayInfoBean>>> findMemberAccount(@Query("token") String token);

    /**
     * ???????????????????????????,????????????
     *
     * @param token          token
     * @param assess_id      ?????????ID
     * @param delivery_type  ???????????? 1,?????????2????????????3????????????
     * @param user_address
     * @param user_name
     * @param user_phone
     * @param logistics_name
     * @param logistics_home
     * @param pay_type       ????????????,??????????????????????????????
     * @return
     */
    @POST("/api/mbOldGoodsOrder/submitOldGoodsOrder")
    Call<BaseRes<List<PayInfoBean>>> submitOldOrder(@Query("token") String token,
                                                    @Query("assess_id") int assess_id,
                                                    @Query("delivery_type") int delivery_type,
                                                    @Query("user_address") String user_address,
                                                    @Query("user_name") String user_name,
                                                    @Query("user_phone") String user_phone,
                                                    @Query("logistics_name") String logistics_name,
                                                    @Query("logistics_home") String logistics_home,
                                                    @Query("pay_type") int pay_type

    );

    /**
     * ????????????
     *
     * @param token
     * @param assess_id
     * @return
     */
    @POST("/api/mbOldGoodsHome/assessCancel")
    Call<BaseRes<String>> cancelAssess(@Query("token") String token, @Query("assess_id") int assess_id);

    /**
     * ????????????
     *
     * @param token
     * @param order_id
     * @param pay_type
     * @return
     */
    @POST("/api/mbOldGoodsOrder/setPayType")
    Call<BaseRes<String>> oldSetPayType(@Query("token") String token,
                                        @Query("order_id") int order_id,
                                        @Query("pay_type") int pay_type);

    /**
     * ????????????
     *
     * @param token
     * @param order_id
     * @return
     */
    @POST("/api/mbOldGoodsOrder/cancelOldOrder")
    Call<BaseRes<String>> cancelRecovery(@Query("token") String token, @Query("order_id") int order_id);

    /**
     * ????????????
     *
     * @param goods_id
     * @return
     */
    @POST("/api/mbFixHome/fixFaultInfo")
    Call<BaseRes<RepairGoodsInfoBean>> repairInfoList(
            @Query("goods_id") int goods_id
    );

    /**
     * ??????????????????
     *
     * @param token    token
     * @param goods_id ??????ID
     * @param itemArr  ??????ID??????
     * @param attrArr  ?????????ID
     * @param thumbArr ????????????
     * @return
     */
    @POST("/api/mbFixOrder/submitFixOrder")
    Call<BaseRes<String>> submitFixOrder(@Query("token") String token,
                                         @Query("goods_id") int goods_id,
                                         @Query("itemArr[]") List<String> itemArr,
                                         @Query("attrArr[]") List<String> attrArr,
                                         @Query("thumbArr[]") List<String> thumbArr,
                                         @Query("remark") String remark

    );

    /**
     * ??????????????????
     *
     * @param token
     * @param status
     * @return
     */
    @POST("/api/mbFixOrder/list")
    Call<BaseRes<List<RepairListBean>>> fixOrderList(@Query("token") String token,
                                                     @Query("status") int status,
                                                     @Query("key") String key

    );

    /**
     * ????????????????????????
     *
     * @param token       token
     * @param fx_order_id ?????????ID
     * @return
     */
    @POST("/api/mbFixOrder/reject")
    Call<BaseRes<String>> rejectFixOrder(@Query("token") String token, @Query("fx_order_id") int fx_order_id);

    /**
     * ????????? ????????????
     *
     * @param token       token
     * @param fx_order_id ?????????ID
     * @return
     */
    @POST("/api/mbFixOrder/cancel")
    Call<BaseRes<String>> cancelFixOrder(@Query("token") String token, @Query("fx_order_id") int fx_order_id);

    /**
     * ????????? ??????
     *
     * @param token       token
     * @param fx_order_id ?????????ID
     * @param pay_way     ????????????:1,?????????2?????????
     * @return
     */
    @POST("/api/mbFixOrder/pay")
    Call<BaseRes<String>> payFixOrderAli(@Query("token") String token,
                                         @Query("fx_order_id") int fx_order_id,
                                         @Query("pay_way") int pay_way);

    /**
     * ????????? ??????
     *
     * @param token       token
     * @param fx_order_id ?????????ID
     * @param pay_way     ????????????:1,?????????2?????????
     * @return
     */
    @POST("/api/mbFixOrder/pay")
    Call<BaseRes<WxPayBean>> payFixOrderWX(@Query("token") String token,
                                           @Query("fx_order_id") int fx_order_id,
                                           @Query("pay_way") int pay_way);

    /**
     * ???????????????
     *
     * @param token       token
     * @param fx_order_id ?????????ID
     * @return
     */
    @POST("/api/mbFixOrder/detail")
    Call<BaseRes<RepairDetailBean>> fixOrderDetail(@Query("token") String token, @Query("fx_order_id") int fx_order_id);

    /**
     * ????????????,????????????????????????
     *
     * @param token          token
     * @param fx_order_id    ?????????ID
     * @param delivery_type  ???????????? 1,?????????2????????????3????????????
     * @param user_address
     * @param user_name
     * @param user_phone     //     * @param send_at        ??????/???????????????
     * @param logistics_name ????????????
     * @param logistics_home ????????????
     * @return
     */
    @POST("/api/mbFixOrder/accept")
    Call<BaseRes<String>> acceptFixOrder(@Query("token") String token,
                                         @Query("fx_order_id") int fx_order_id,
                                         @Query("delivery_type") int delivery_type,
                                         @Query("user_address") int user_address,
                                         @Query("user_name") String user_name,
                                         @Query("user_phone") String user_phone,
//                                         @Query("send_at") int send_at,
                                         @Query("logistics_name") String logistics_name,
                                         @Query("logistics_home") String logistics_home
    );

    /**
     * ???????????????????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbComment/commentTag")
    Call<BaseRes<List<CommentTagBean>>> commentTagList(@Query("token") String token);

    /**
     * ???????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbJieMoney/detail")
    Call<BaseRes<ArrearsDetailBean>> arrearsDetail(@Query("token") String token, @Query("id") int id);

    /**
     * ?????????????????????
     *
     * @param token
     * @param order_id
     * @param order_sn
     * @param tags
     * @param star
     * @param remark
     * @return
     */
    @POST("/api/mbFixOrder/submitComment")
    Call<BaseRes<String>> submitFixOderComment(@Query("token") String token,
                                               @Query("order_id") int order_id,
                                               @Query("order_sn") String order_sn,
                                               @Query("tags[]") List<String> tags,
                                               @Query("star") int star,
                                               @Query("remark") String remark
    );

    /**
     * ???????????????
     *
     * @param token
     * @param order_id
     * @param tags     ????????????
     * @param star     ??????
     * @param remark   ??????
     * @return
     */
    @POST("/api/mbComment/create")
    Call<BaseRes<String>> submitOrderComment(@Query("token") String token,
                                             @Query("order_id") int order_id,
                                             @Query("tags[]") List<String> tags,
                                             @Query("star") int star,
                                             @Query("remark") String remark
    );

    /**
     * ??????????????????
     *
     * @param goods_id
     * @return
     */
    @POST("/api/mbComment/goodsDetail")
    Call<BaseRes<GoodsEvaluateBean>> goodsComment(
            @Query("goods_id") int goods_id
    );


    /**
     * ?????????????????????????????????
     *
     * @param token
     * @param lat   ??????
     * @param lng   ??????
     * @return
     */
    @POST("/api/mbMemberGoods/storeList")
    Call<BaseRes<List<HomeStoreListBean>>> storeList(@Query("token") String token,
                                                     @Query("lat") double lat,
                                                     @Query("lng") double lng
    );

    /**
     * ????????????ID??????????????????
     *
     * @param token
     * @param store_id
     * @return
     */
    @POST("/api/mbMemberGoods/staffList")
    Call<BaseRes<List<HomeStaffListBean>>> staffList(@Query("token") String token,
                                                     @Query("store_id") int store_id
    );

    /**
     * ????????????
     *
     * @param token
     * @param store_id ??????ID
     * @param staff_id ??????ID
     * @return
     */
    @POST("/api/mbSelectStaff/selectStaff")
    Call<BaseRes<String>> selectStaff(@Query("token") String token,
                                      @Query("store_id") int store_id,
                                      @Query("staff_id") int staff_id

    );

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("/api/mbMemberIndex/indexInfo")
    Call<BaseRes<HomeBean>> homeInfo();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST("/api/mbMemberGoods/itemList")
    Call<BaseRes<List<GoodsItemBean>>> goodsItem(@Query("id") int id);

    /**
     * ????????????
     *
     * @param token
     * @param goods_id
     * @param item_id
     * @param num
     * @return
     */
    @POST("/api/mbOrder/add")
    Call<BaseRes<String>> buyNow(@Query("token") String token,
                                 @Query("goods_id") int goods_id,
                                 @Query("item_id") String item_id,
                                 @Query("num") int num,
                                 @Query("virtual[]") List<String> virtual
    );

    /**
     * ????????????????????????
     *
     * @param store_id
     * @param goods_brand
     * @param goods_cate
     * @param price_di
     * @param price_gao
     * @return
     */
    @POST("/api/mbMemberIndex/indexGoodsInfo")
    Call<BaseRes<List<HomeGoodsListBean>>> homeGoodsList(@Query("store_id") int store_id,
                                                         @Query("goods_brand[]") List<String> goods_brand,
                                                         @Query("goods_cate[]") List<String> goods_cate,
                                                         @Query("price_di") Integer price_di,
                                                         @Query("price_gao") Integer price_gao,
                                                         @Query("size") int size

    );

    /**
     * ????????????
     *
     * @param token
     * @param status
     * @return
     */
    @POST("/api/mbOrder/orderList")
    Call<BaseRes<OrderBean>> orderList(@Query("token") String token, @Query("status") int status, @Query("key") String key);

    /**
     * ??????---->??????????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbOrder/orderIng")
    Call<BaseRes<MineOrderBean>> mineOrder(@Query("token") String token);

    /**
     * ?????????,????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbOrder/receipt")
    Call<BaseRes<String>> confirmOrder(@Query("token") String token, @Query("id") int id);

    /**
     * ?????????,????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbOrder/cancelOrder")
    Call<BaseRes<String>> cancelOrder(@Query("token") String token, @Query("id") int id);

    /**
     * ???????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbJieMoney/list")
    Call<BaseRes<List<ArrearsDetailBean>>> arrearsList(@Query("token") String token,
                                                       @Query("page") int page,
                                                       @Query("size") int size,
                                                       @Query("status") int type);

    /**
     * ???????????????
     *
     * @param token
     * @param pay_way 1???app?????????2???????????????3??????????????????
     * @return
     */
    @POST("/api/mbJieMoney/payOrder")
    Call<BaseRes<WxPayBean>> arrearsPayWx(@Query("token") String token,
                                          @Query("id") int id,
                                          @Query("pay_way") int pay_way);

    /**
     * ???????????????
     *
     * @param token
     * @param pay_way 1???app?????????2???????????????3??????????????????
     * @return
     */
    @POST("/api/mbJieMoney/payOrder")
    Call<BaseRes<String>> arrearsPayAli(@Query("token") String token,
                                        @Query("id") int id,
                                        @Query("pay_way") int pay_way);

    /**
     * ????????????????????????
     *
     * @return
     */
    @GET("/api/sfAccount/list")
    Call<BaseRes<AccountBean>> accountList(@Query("token") String token);

    /**
     * @param token
     * @param order_id      ??????ID
     * @param pay_way       ???????????????1?????????2?????????
     * @param address_id    ????????????ID
     * @param delivery_type ????????????,0??????,1??????
     * @param remark        ??????
     * @return
     */
    @POST("/api/mbOrder/payOrder")
    Call<BaseRes<String>> sureOrder(@Query("token") String token,
                                    @Query("order_id") int order_id,
                                    @Query("pay_way") int pay_way,
                                    @Query("address_id") int address_id,
                                    @Query("delivery_type") int delivery_type,
                                    @Query("coupon_arr[]") List<String> coupon_arr,
                                    @Query("remark") String remark);

    /**
     * @param token
     * @param order_id      ??????ID
     * @param pay_way       ???????????????1?????????2?????????
     * @param address_id    ????????????ID
     * @param delivery_type ????????????,0??????,1??????
     * @param remark        ??????
     * @return
     */
    @POST("/api/mbOrder/payOrder")
    Call<BaseRes<WxPayBean>> wxsureOrder(@Query("token") String token,
                                         @Query("order_id") int order_id,
                                         @Query("pay_way") int pay_way,
                                         @Query("address_id") int address_id,
                                         @Query("delivery_type") int delivery_type,
                                         @Query("coupon_arr[]") List<String> coupon_arr,
                                         @Query("remark") String remark);

    /**
     * ????????????
     *
     * @param token
     * @param avatar
     * @return
     */
    @POST("/api/mbCenter/editAvatar")
    Call<BaseRes<String>> changeAvatar(@Query("token") String token,
                                       @Query("avatar") String avatar);

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbCenter/editName")
    Call<BaseRes<String>> changeName(@Query("token") String token,
                                     @Query("name") String name);

    /**
     * ????????????
     *
     * @param token
     * @param order_type
     * @param id
     * @return
     */
    @POST("/api/mbShouHou/detail")
    Call<BaseRes<ReturnDetailGoodsBean>> serviceDetail(@Query("token") String token,
                                                       @Query("order_type") int order_type,
                                                       @Query("id") int id);

    /**
     * ???????????????
     *
     * @param token
     * @param phone
     * @param phonecode
     * @return
     */
    @POST("/api/mbCenter/reBindPhone")
    Call<BaseRes<String>> changePhone(@Query("token") String token,
                                      @Query("phone") String phone,
                                      @Query("phonecode") String phonecode);

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbShouHou/shouHouList")
    Call<BaseRes<List<ReturnGoodsBean>>> serviceList(@Query("token") String token);

    /**
     * ????????????
     *
     * @param token
     * @param order_id       ??????ID
     * @param goods_info_str ????????????
     * @param reason         ????????????
     * @param remark         ????????????
     * @param photo          ??????
     * @return
     */
    @POST("/api/mbShouHou/changeOrder")
    Call<BaseRes<String>> exchangeGoods(@Query("token") String token,
                                        @Query("order_id") int order_id,
                                        @Query("goods_info_str") String goods_info_str,
                                        @Query("reason") String reason,
                                        @Query("remark") String remark,
                                        @Query("photo[]") List<String> photo);

    /**
     * ??????
     *
     * @param token
     * @param order_id       ??????ID
     * @param goods_info_str ????????????
     * @param reason         ????????????
     * @param remark         ????????????
     * @param photo          ??????
     * @return
     */
    @POST("/api/mbShouHou/returnOrder")
    Call<BaseRes<String>> returnGoods(@Query("token") String token,
                                      @Query("order_id") int order_id,
                                      @Query("goods_info_str") String goods_info_str,
                                      @Query("reason") String reason,
                                      @Query("remark") String remark,
                                      @Query("photo[]") List<String> photo);

    /**
     * ??????deviceToken,????????????
     *
     * @param token
     * @param device_id
     * @param device_token
     * @return
     */
    @POST("/api/mbUserInfo/bindDeviceToken")
    Call<BaseRes<String>> bindDevice(@Query("token") String token,
                                     @Query("device_id") int device_id,
                                     @Query("device_token") String device_token);

    /**
     * ??????deviceToken,????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbUserInfo/setPersonBillName")
    Call<BaseRes<String>> changeTax(@Query("token") String token,
                                    @Query("name") String name);

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbEasemob/create")
    Call<BaseRes<CreateImBean>> createIm(@Query("token") String token);

    /**
     * ???????????????????????????
     *
     * @param token
     * @return
     */
    @GET("/api/mbEasemob/userinfo")
    Call<BaseRes<CreateImUser>> initIm(@Query("token") String token);

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbEasemob/replay")
    Call<BaseRes<String>> imReplay(@Query("token") String token,
                                   @Query("id") int id,
                                   @Query("content") String content);

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbEasemob/msgList")
    Call<BaseRes<List<ChatBean>>> chatList(@Query("token") String token,
                                           @Query("id") int id,
                                           @Query("page") int page,
                                           @Query("size") int size);

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @GET("/api/mbEasemob/list")
    Call<BaseRes<List<MsgListBean>>> imList(@Query("token") String token);

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbEasemob/notReadMsg")
    Call<BaseRes<String>> notRead(@Query("token") String token);

    /**
     * ????????????
     *
     * @param token
     * @return
     */
    @POST("/api/mbUserInfo/zhuXiao")
    Call<BaseRes<String>> logoff(@Query("token") String token);
}





