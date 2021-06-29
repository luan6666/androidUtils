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
 * @Description 接口定义
 */
public interface ApiService {
    /**
     * 版本控制
     *
     * @param type
     * @return status 为1 强制更新 地址为address
     */
    @POST("/api/appVersion")
    @NotNull
    Call<BaseRes<VersionBean>> versionControl(@Query("type") int type);

    /**
     * 设置推广码
     *
     * @param token
     * @param code
     * @return status 为1 强制更新 地址为address
     */
    @POST("/api/mbUserInfo/setExtend")
    Call<BaseRes<String>> changeExtend(@Query("token") String token, @Query("code") String code);

    /**
     * 开机广告
     */
    @GET("/api/kaijiAd")
    Call<BaseRes<LoadingBean>> getLoadingList();

    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    @POST("/api/wxUserInfo")
    Call<BaseRes<UserInfo>> loginWx(@Query("code") String code, @Query("type") int type);

    /**
     * 上传图片
     *
     * @param /api/common/upload
     * @return
     */
    @Multipart
    @POST("/api/common/upload")
    Call<BaseRes<FileBean>> uploadImage(@Part MultipartBody.Part file);


    /**
     * 支付宝登录,检查是否已经有手机号了,如果没有跳转绑定手机号的页面
     *
     * @param aliOpenId
     * @return
     */
    @POST("/api/memberAliLogin")
    Call<BaseRes<UserInfo>> retailAliLogin(@Query("aliOpenId") String aliOpenId);

    /**
     * 第三方绑定手机号并且登录
     *
     * @param id        支付宝openId /微信openID
     * @param phone     手机号
     * @param phonecode 验证码
     * @param type      支付宝：1，微信：0
     * @return
     */
    @POST("/api/memberBindPhone")
    Call<BaseRes<UserInfo>> retailBindPhone(@Query("id") String id,
                                            @Query("unionid") String unionid,
                                            @Query("phone") String phone,
                                            @Query("phonecode") String phonecode,
                                            @Query("type") String type);

    /**
     * 获取商品的三级列表
     *
     * @param cate_id cate_id
     * @return
     */
    @POST("/api/goodsListByCate")
    Call<BaseRes<List<OldGoodsItemBean>>> goodsChildList(@Query("cate_id") int cate_id, @Query("type") int type, @Query("key") String key);

    /**
     * 获取商品的一级和二级列表
     *
     * @return
     */
    @POST("/api/goodsSearchInfo")
    Call<BaseRes<List<OldGoodsListBean>>> goodsTopList();

    /**
     * 根据条件筛选
     *
     * @param token
     * @param key       关键字
     * @param goods     品牌
     * @param cate      分类
     * @param attr      属性
     * @param lowPrice  最低价
     * @param hignPrice 最高价
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
     * 搜索结果
     *
     * @return
     */
    @POST("/api/goodsSearchInfo")
    Call<BaseRes<List<ClassBean>>> homeSearchInfo();

    /**
     * 搜索结果
     *
     * @return
     */
    @POST("api/mbMemberIndex/searchInfo")
    Call<BaseRes<SearchInfoBean>> searchInfo();


    /**
     * 商品详情
     *  旧的接口 /api/mbMemberGoods/detail
     * @param id 商品id
     * @return
     */
    @POST("api/mbMemberGoods/detailNewStructure")
    Call<BaseRes<GoodsDetailBean>> goodsDetail(@Query("token") String token, @Query("id") int id);

    /**
     * 预售商品详情
     *
     * @param id 商品id
     * @return
     */
    @POST("/api/mbPresale/detail")
    Call<BaseRes<PreGoodsDetailBean>> preGoodsDetail(@Query("token") String token, @Query("id") int id);

    /**
     * 预售商品付定金
     *
     * @param token
     * @param id    商品id
     * @return
     */
    @POST("/api/mbPresale/payDingJin")
    Call<BaseRes<WxPayBean>> prePayDepositWx(@Query("token") String token, @Query("id") int id, @Query("pay_way") int pay_way);

    /**
     * 预售商品付定金
     *
     * @param token
     * @param id    商品id
     * @return
     */
    @POST("/api/mbPresale/payDingJin")
    Call<BaseRes<String>> prePayDepositAli(@Query("token") String token, @Query("id") int id, @Query("pay_way") int pay_way);

    /**
     * 创建预售单
     *
     * @param token
     * @param id    商品id
     * @return
     */
    @POST("/api/mbPresale/create")
    Call<BaseRes<String>> createPreOrder(@Query("token") String token,
                                         @Query("id") int id,
                                         @Query("num") int num,
                                         @Query("presale_goods_id") int presale_goods_id);

    /**
     * 创建预售单
     *
     * @param token
     * @param id    商品id
     * @return
     */
    @POST("/api/mbPresale/orderDetail")
    Call<BaseRes<PreOrderDetailBean>> preOrderDetail(@Query("token") String token,
                                                     @Query("id") int id);


    /**
     * 根据验证码登录
     *
     * @param phone
     * @param phonecode
     * @return
     */
    @POST("/api/memberPhoneLogin")
    Call<BaseRes<UserInfo>> loginByCode(@Query("phone") String phone, @Query("phonecode") String phonecode);

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @POST("/api/phonecode")
    Call<BaseRes<String>> sendCode(@Query("phone") String phone);

    /**
     * 获取用户信息
     *
     * @return
     */
    @POST("/api/mbUserInfo/userInfo")
    Call<BaseRes<UserInfo>> getUserInfo(@Query("token") String token, @Query("lng") Double lng, @Query("lat") Double lat);

    /**
     * 根据id查询商品的评估详情
     *
     * @param goods_id 商品ID
     * @return
     */
    @POST("/api/mbOldGoodsHome/goodsAssessInfo")
    Call<BaseRes<OldInfoDetailBean>> oldGoodsInfoDetail(@Query("goods_id") int goods_id);

    /**
     * 根据id查询商品的评估详情
     *
     * @param goods_id 商品ID
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
     * 二手机评估列表
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
     * 二手机评估详情
     *
     * @param token
     * @return
     */
    @POST("/api/mbOldGoodsHome/assessDetail")
    Call<BaseRes<OldDetailBean>> assessDetail(
            @Query("token") String token,
            @Query("assess_id") int assess_id);

    /**
     * 券列表
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
     * 领券中心列表
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
     * 会员领券
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
     * 订单选择优惠券
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
     * 查询订单可用券列表
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
     * 地址列表
     *
     * @param token
     * @return
     */
    @POST("/api/mbAddress/list")
    Call<BaseRes<List<AddressBean>>> addressList(@Query("token") String token);

    /**
     * 创建地址
     *
     * @param token       分销商ID
     * @param province_id 省
     * @param city_id     市
     * @param area_id     区
     * @param address     收货地址
     * @param name        姓名
     * @param phone       手机号
     * @param is_default  是否默认1是0否
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
     * 修改地址
     *
     * @param id          地址id
     * @param province_id 省
     * @param city_id     市
     * @param area_id     区
     * @param address     收货地址
     * @param name        姓名
     * @param phone       手机号
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
     * 设置默认地址
     *
     * @param token token
     * @param id    收货地址ID
     * @return
     */
    @POST("/api/mbAddress/default")
    Call<BaseRes<String>> defaultAddress(@Query("token") String token, @Query("id") String id);

    /**
     * 收货地址详情
     *
     * @param id 地址id
     * @return
     */
    @POST("/api/mbAddress/detail")
    Call<BaseRes<AddressBean>> addressDetail(@Query("id") String id);

    /**
     * 删除地址
     *
     * @param token token
     * @param id    地址id
     * @return
     */
    @POST("/api/mbAddress/remove")
    Call<BaseRes<String>> deleteAddress(@Query("token") String token, @Query("id") String id);

    /**
     * 添加购物车
     *
     * @param token    token
     * @param goods_id 商品ID
     * @param item_id  规格拼接
     * @param num      数量
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
     * 修改购物车数量
     *
     * @param token token
     * @param id    购物车id
     * @param num   数量
     * @return
     */
    @POST("/api/mbCar/changeNum")
    Call<BaseRes<List<CarBean>>> editShopCarNum(@Query("token") String token,
                                                @Query("id") String id,
                                                @Query("num") String num);

    /**
     * 购物车列表
     *
     * @param token
     * @return
     */
    @POST("/api/mbCar/detail")
    Call<BaseRes<List<CarBean>>> shopCarList(@Query("token") String token);

    /**
     * 跳转到确认订单页面
     *
     * @param token token
     * @param aa    购物车ID数组
     * @return
     */
    @POST("/api/mbOrder/settle")
    Call<BaseRes<String>> goSettle(@Query("token") String token, @Query("cartids[]") Object[] aa);

    /**
     * 订单详情
     *
     * @param token token
     * @param id    订单id
     * @return
     */
    @POST("/api/mbOrder/detail")
    Call<BaseRes<ConfirmOrderBean>> getConfirmOrderDetail(@Query("token") String token, @Query("id") int id);

    /**
     * 订单详情
     *
     * @param token token
     * @param id    订单id
     * @return
     */
    @POST("/api/mbOrder/detail")
    Call<BaseRes<OrderDetailBean>> getOrderDetail(@Query("token") String token, @Query("id") int id);

    /**
     * 新建开票信息
     *
     * @param token              token
     * @param buyer_name         购方名
     * @param buyer_tax          购方纳税人识别号
     * @param buyer_address      购方地址
     * @param buyer_phone        购方电话
     * @param buyer_bank         购方开户银行
     * @param buyer_bank_account 购方银行账户
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
     * 编辑发票信息
     *
     * @param token              token
     * @param id                 开票信息ID
     * @param buyer_name         购方名
     * @param buyer_tax          购方纳税人识别号
     * @param buyer_address      购方地址
     * @param buyer_phone        购方电话
     * @param buyer_bank         购方开户银行
     * @param buyer_bank_account 购方银行账户
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
     * 删除开票信息
     *
     * @param token token
     * @param id    开票信息ID
     * @return
     */
    @POST("/api/mbBill/remove")
    Call<BaseRes<String>> deleteTax(@Query("token") String token,
                                    @Query("id") String id);

    /**
     * 订单申请开票
     *
     * @param token      token
     * @param order_id   订单ID
     * @param bill_class 1,普通发票：2，增值税专用发票
     * @param type       1,个人：2，企业
     * @param bill_id    如果type 为2 的话，需要填写 发票ID
     * @param take_type  bill_class 为2 的话，0,去门店，1，寄快递
     * @param address_id bill_class 为2 的话，take_type 1，寄快递 收货地址iD
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
     * 订单申请开票
     *
     * @param token      token
     * @param order_id   订单ID
     * @param bill_class 1,普通发票：2，增值税专用发票
     * @param type       1,个人：2，企业
     * @param bill_id    如果type 为2 的话，需要填写 发票ID
     * @param take_type  bill_class 为2 的话，0,去门店，1，寄快递
     * @param address_id bill_class 为2 的话，take_type 1，寄快递 收货地址iD
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
     * 订单查询发票
     *
     * @param token    token
     * @param order_id 订单ID
     * @param type     1,用户商品订单，2维修订单
     * @return
     */
    @POST("/api/mbOrderBill/orderBill")
    Call<BaseRes<TaxBean>> orderBill(@Query("token") String token,
                                     @Query("order_id") String order_id,
                                     @Query("type") String type
    );

    /**
     * 获取开票信息详情
     *
     * @param token token
     * @param id    开票信息ID
     * @return
     */
    @POST("/api/mbBill/detail")
    Call<BaseRes<TaxBean>> detailTax(@Query("token") String token,
                                     @Query("id") String id);

    /**
     * 获取开票信息列表
     *
     * @param token token
     * @return
     */
    @POST("/api/mbBill/list")
    Call<BaseRes<List<TaxBean>>> listTax(@Query("token") String token);

    /**
     * 设置默认开票信息
     *
     * @param token token
     * @param id    开票信息ID
     * @return
     */
    @POST("/api/mbBill/default")
    Call<BaseRes<String>> defaultTax(@Query("token") String token,
                                     @Query("id") String id);

    /**
     * 预售活动列表
     *
     * @param store_id store_id
     * @return
     */
    @POST("/api/mbPresale/list")
    Call<BaseRes<List<PreGoodsListBean>>> preList(@Query("store_id") int store_id);

    /**
     * 预售订单列表
     *
     * @param token token
     * @return
     */
    @POST("/api/mbPresale/orderList")
    Call<BaseRes<List<PreListBean>>> preOrderList(@Query("token") String token, @Query("status") int status);

    /**
     * 二手机收款信息修改或添加
     *
     * @param token token
     * @param type  1,支付宝，2，微信
     * @param num   账号
     * @param name  名称
     * @return
     */
    @POST("/api/mbOldGoodsOrder/editMemberAccount")
    Call<BaseRes<String>> editMemberAccount(
            @Query("token") String token,
            @Query("type") int type,
            @Query("num") String num,
            @Query("name") String name);

    /**
     * 查询客户收款信息
     *
     * @param token
     * @return
     */
    @POST("/api/mbOldGoodsOrder/findMemberAccount")
    Call<BaseRes<List<PayInfoBean>>> findMemberAccount(@Query("token") String token);

    /**
     * 二手机接受评估价格,提交回去
     *
     * @param token          token
     * @param assess_id      评估单ID
     * @param delivery_type  取件方式 1,上门，2寄快递，3去门店。
     * @param user_address
     * @param user_name
     * @param user_phone
     * @param logistics_name
     * @param logistics_home
     * @param pay_type       支付方式,微信和支付宝只有一种
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
     * 取消评估
     *
     * @param token
     * @param assess_id
     * @return
     */
    @POST("/api/mbOldGoodsHome/assessCancel")
    Call<BaseRes<String>> cancelAssess(@Query("token") String token, @Query("assess_id") int assess_id);

    /**
     * 取消评估
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
     * 取消评估
     *
     * @param token
     * @param order_id
     * @return
     */
    @POST("/api/mbOldGoodsOrder/cancelOldOrder")
    Call<BaseRes<String>> cancelRecovery(@Query("token") String token, @Query("order_id") int order_id);

    /**
     * 故障查询
     *
     * @param goods_id
     * @return
     */
    @POST("/api/mbFixHome/fixFaultInfo")
    Call<BaseRes<RepairGoodsInfoBean>> repairInfoList(
            @Query("goods_id") int goods_id
    );

    /**
     * 提交维修订单
     *
     * @param token    token
     * @param goods_id 商品ID
     * @param itemArr  规格ID数组
     * @param attrArr  故障项ID
     * @param thumbArr 图片数组
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
     * 维修订单列表
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
     * 维修单不接受报价
     *
     * @param token       token
     * @param fx_order_id 维修单ID
     * @return
     */
    @POST("/api/mbFixOrder/reject")
    Call<BaseRes<String>> rejectFixOrder(@Query("token") String token, @Query("fx_order_id") int fx_order_id);

    /**
     * 维修单 取消订单
     *
     * @param token       token
     * @param fx_order_id 维修单ID
     * @return
     */
    @POST("/api/mbFixOrder/cancel")
    Call<BaseRes<String>> cancelFixOrder(@Query("token") String token, @Query("fx_order_id") int fx_order_id);

    /**
     * 维修单 支付
     *
     * @param token       token
     * @param fx_order_id 维修单ID
     * @param pay_way     支付方式:1,微信，2支付宝
     * @return
     */
    @POST("/api/mbFixOrder/pay")
    Call<BaseRes<String>> payFixOrderAli(@Query("token") String token,
                                         @Query("fx_order_id") int fx_order_id,
                                         @Query("pay_way") int pay_way);

    /**
     * 维修单 支付
     *
     * @param token       token
     * @param fx_order_id 维修单ID
     * @param pay_way     支付方式:1,微信，2支付宝
     * @return
     */
    @POST("/api/mbFixOrder/pay")
    Call<BaseRes<WxPayBean>> payFixOrderWX(@Query("token") String token,
                                           @Query("fx_order_id") int fx_order_id,
                                           @Query("pay_way") int pay_way);

    /**
     * 维修单详情
     *
     * @param token       token
     * @param fx_order_id 维修单ID
     * @return
     */
    @POST("/api/mbFixOrder/detail")
    Call<BaseRes<RepairDetailBean>> fixOrderDetail(@Query("token") String token, @Query("fx_order_id") int fx_order_id);

    /**
     * 接受价格,提交商品配送方式
     *
     * @param token          token
     * @param fx_order_id    维修单ID
     * @param delivery_type  取件方式 1,上门，2寄快递，3去门店。
     * @param user_address
     * @param user_name
     * @param user_phone     //     * @param send_at        上门/去门店时间
     * @param logistics_name 快递名称
     * @param logistics_home 快递编码
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
     * 获取所有评价的标签
     *
     * @param token
     * @return
     */
    @POST("/api/mbComment/commentTag")
    Call<BaseRes<List<CommentTagBean>>> commentTagList(@Query("token") String token);

    /**
     * 欠款单详情
     *
     * @param token
     * @return
     */
    @POST("/api/mbJieMoney/detail")
    Call<BaseRes<ArrearsDetailBean>> arrearsDetail(@Query("token") String token, @Query("id") int id);

    /**
     * 维修单提交评价
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
     * 商品单评价
     *
     * @param token
     * @param order_id
     * @param tags     标签数组
     * @param star     星级
     * @param remark   内容
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
     * 商品评价列表
     *
     * @param goods_id
     * @return
     */
    @POST("/api/mbComment/goodsDetail")
    Call<BaseRes<GoodsEvaluateBean>> goodsComment(
            @Query("goods_id") int goods_id
    );


    /**
     * 根据经纬度获取门店列表
     *
     * @param token
     * @param lat   纬度
     * @param lng   经度
     * @return
     */
    @POST("/api/mbMemberGoods/storeList")
    Call<BaseRes<List<HomeStoreListBean>>> storeList(@Query("token") String token,
                                                     @Query("lat") double lat,
                                                     @Query("lng") double lng
    );

    /**
     * 根据门店ID查询员工列表
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
     * 选择员工
     *
     * @param token
     * @param store_id 门店ID
     * @param staff_id 员工ID
     * @return
     */
    @POST("/api/mbSelectStaff/selectStaff")
    Call<BaseRes<String>> selectStaff(@Query("token") String token,
                                      @Query("store_id") int store_id,
                                      @Query("staff_id") int staff_id

    );

    /**
     * 获取首页数据
     *
     * @return
     */
    @POST("/api/mbMemberIndex/indexInfo")
    Call<BaseRes<HomeBean>> homeInfo();

    /**
     * 商品规格列表
     *
     * @return
     */
    @POST("/api/mbMemberGoods/itemList")
    Call<BaseRes<List<GoodsItemBean>>> goodsItem(@Query("id") int id);

    /**
     * 立即购买
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
     * 首页下面商品列表
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
     * 订单列表
     *
     * @param token
     * @param status
     * @return
     */
    @POST("/api/mbOrder/orderList")
    Call<BaseRes<OrderBean>> orderList(@Query("token") String token, @Query("status") int status, @Query("key") String key);

    /**
     * 我的---->进行中的订单
     *
     * @param token
     * @return
     */
    @POST("/api/mbOrder/orderIng")
    Call<BaseRes<MineOrderBean>> mineOrder(@Query("token") String token);

    /**
     * 商品单,确认收货
     *
     * @param token
     * @return
     */
    @POST("/api/mbOrder/receipt")
    Call<BaseRes<String>> confirmOrder(@Query("token") String token, @Query("id") int id);

    /**
     * 商品单,取消订单
     *
     * @param token
     * @return
     */
    @POST("/api/mbOrder/cancelOrder")
    Call<BaseRes<String>> cancelOrder(@Query("token") String token, @Query("id") int id);

    /**
     * 欠款单列表
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
     * 欠款单支付
     *
     * @param token
     * @param pay_way 1，app微信，2，支付宝，3，微信小程序
     * @return
     */
    @POST("/api/mbJieMoney/payOrder")
    Call<BaseRes<WxPayBean>> arrearsPayWx(@Query("token") String token,
                                          @Query("id") int id,
                                          @Query("pay_way") int pay_way);

    /**
     * 欠款单支付
     *
     * @param token
     * @param pay_way 1，app微信，2，支付宝，3，微信小程序
     * @return
     */
    @POST("/api/mbJieMoney/payOrder")
    Call<BaseRes<String>> arrearsPayAli(@Query("token") String token,
                                        @Query("id") int id,
                                        @Query("pay_way") int pay_way);

    /**
     * 获取付款账户列表
     *
     * @return
     */
    @GET("/api/sfAccount/list")
    Call<BaseRes<AccountBean>> accountList(@Query("token") String token);

    /**
     * @param token
     * @param order_id      订单ID
     * @param pay_way       付款类型：1微信，2支付宝
     * @param address_id    收货地址ID
     * @param delivery_type 配送方式,0自提,1配送
     * @param remark        备注
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
     * @param order_id      订单ID
     * @param pay_way       付款类型：1微信，2支付宝
     * @param address_id    收货地址ID
     * @param delivery_type 配送方式,0自提,1配送
     * @param remark        备注
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
     * 更换头像
     *
     * @param token
     * @param avatar
     * @return
     */
    @POST("/api/mbCenter/editAvatar")
    Call<BaseRes<String>> changeAvatar(@Query("token") String token,
                                       @Query("avatar") String avatar);

    /**
     * 更换头像
     *
     * @param token
     * @return
     */
    @POST("/api/mbCenter/editName")
    Call<BaseRes<String>> changeName(@Query("token") String token,
                                     @Query("name") String name);

    /**
     * 售后详情
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
     * 更换手机号
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
     * 售后列表
     *
     * @param token
     * @return
     */
    @POST("/api/mbShouHou/shouHouList")
    Call<BaseRes<List<ReturnGoodsBean>>> serviceList(@Query("token") String token);

    /**
     * 换货接口
     *
     * @param token
     * @param order_id       订单ID
     * @param goods_info_str 商品信息
     * @param reason         换货原因
     * @param remark         换货说明
     * @param photo          图片
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
     * 退货
     *
     * @param token
     * @param order_id       订单ID
     * @param goods_info_str 商品信息
     * @param reason         换货原因
     * @param remark         换货说明
     * @param photo          图片
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
     * 绑定deviceToken,用做推送
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
     * 绑定deviceToken,用做推送
     *
     * @param token
     * @return
     */
    @POST("/api/mbUserInfo/setPersonBillName")
    Call<BaseRes<String>> changeTax(@Query("token") String token,
                                    @Query("name") String name);

    /**
     * 创建聊天
     *
     * @param token
     * @return
     */
    @POST("/api/mbEasemob/create")
    Call<BaseRes<CreateImBean>> createIm(@Query("token") String token);

    /**
     * 初始化用户账户信息
     *
     * @param token
     * @return
     */
    @GET("/api/mbEasemob/userinfo")
    Call<BaseRes<CreateImUser>> initIm(@Query("token") String token);

    /**
     * 消息回复
     *
     * @param token
     * @return
     */
    @POST("/api/mbEasemob/replay")
    Call<BaseRes<String>> imReplay(@Query("token") String token,
                                   @Query("id") int id,
                                   @Query("content") String content);

    /**
     * 聊天列表
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
     * 消息列表
     *
     * @param token
     * @return
     */
    @GET("/api/mbEasemob/list")
    Call<BaseRes<List<MsgListBean>>> imList(@Query("token") String token);

    /**
     * 消息列表
     *
     * @param token
     * @return
     */
    @POST("/api/mbEasemob/notReadMsg")
    Call<BaseRes<String>> notRead(@Query("token") String token);

    /**
     * 消息列表
     *
     * @param token
     * @return
     */
    @POST("/api/mbUserInfo/zhuXiao")
    Call<BaseRes<String>> logoff(@Query("token") String token);
}





