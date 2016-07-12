package com.andcup.android.app.integralwall.datalayer;

import com.andcup.android.app.integralwall.datalayer.model.AvatarEntity;
import com.andcup.android.app.integralwall.datalayer.model.CalendarEntity;
import com.andcup.android.app.integralwall.datalayer.model.CashingItemEntity;
import com.andcup.android.app.integralwall.datalayer.model.CheckEntity;
import com.andcup.android.app.integralwall.datalayer.model.CheckQbEntity;
import com.andcup.android.app.integralwall.datalayer.model.CompleteInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.DailyTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.ExperienceEntity;
import com.andcup.android.app.integralwall.datalayer.model.FamilyInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.FamilyMemberEntity;
import com.andcup.android.app.integralwall.datalayer.model.FirstLandingTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.HotTaskEntity;
import com.andcup.android.app.integralwall.datalayer.model.LoginEntity;
import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
import com.andcup.android.app.integralwall.datalayer.model.MyTaskQuickEntity;
import com.andcup.android.app.integralwall.datalayer.model.QbEntity;
import com.andcup.android.app.integralwall.datalayer.model.RaidersEntity;
import com.andcup.android.app.integralwall.datalayer.model.RankEntity;
import com.andcup.android.app.integralwall.datalayer.model.RewardEntity;
import com.andcup.android.app.integralwall.datalayer.model.ScoreDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.ShareInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskDetailEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskFeatureEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskLimitEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskQuickEntity;
import com.andcup.android.app.integralwall.datalayer.model.TaskUnionPlatformEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseErrorEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/9.
 */
public interface IntegralWallApis {
    /**
     * 登录注册相关接口
     */
    //游客登录接口
    @GET("/api/user/temp_account")
    Call<BaseEntity<LoginEntity>> visitor(@Query(Fields.UUID) String number,
                                          @Query(Fields.TIMESTAMP) long timestamp,
                                          @Query(Fields.SIGN) String sign);

    //登陆接口
    @GET("/api/user/login")
    Call<BaseEntity<LoginEntity>> login(@Query(Fields.PHONE) String number,
                                        @Query(Fields.PASSWORD) String password,
                                        @Query(Fields.TIMESTAMP) long timestamp,
                                        @Query(Fields.SIGN) String sign);

    //忘记密码接口
    @GET("/api/user/edit_password_by_verify_code")
    Call<BaseEntity> findPassword(@Query(Fields.PHONE) String number,
                                  @Query(Fields.VERIFY_CODE) String verifyCode,
                                  @Query(Fields.PASSWORD) String password,
                                  @Query(Fields.TIMESTAMP) long timestamp,
                                  @Query(Fields.SIGN) String sign);

    //发送验证码接口
    @GET("/api/user/send_verify_code")
    Call<BaseEntity> verifyCode(@Query(Fields.PHONE) String number,
                                @Query(Fields.TYPE) int type,
                                @Query(Fields.TYPE_BIND) int bindType,
                                @Query(Fields.TIMESTAMP) long timestamp,
                                @Query(Fields.SIGN) String sign);

    //三方授权登录接口
    @GET("/api/user/login_with")
    Call<BaseEntity<LoginEntity>> loginWithAuthorized(@Query(Fields.TYPE) String type,
                                                      @Query(Fields.CODE) String code,
                                                      @Query(Fields.LOCATION) String location,
                                                      @Query(Fields.NICK_NAME) String nickname,
                                                      @Query(Fields.AVATAR_URL) String avatar,
                                                      @Query(Fields.SEX) String sex,
                                                      @Query(Fields.ACCESS_TOKEN) String accessToken,
                                                      @Query(Fields.TIMESTAMP) long timestamp,
                                                      @Query(Fields.SIGN) String sign);

    //三方授权登录接口
    @GET("/api/user/otherlogin")
    Call<BaseEntity<LoginEntity>> otherlogin(@Query(Fields.TYPE) String type,
                                             @Query(Fields.CODE) String code,
                                             @Query(Fields.LOCATION) String location,
                                             @Query(Fields.NICK_NAME) String nickname,
                                             @Query(Fields.AVATAR_URL) String avatar,
                                             @Query(Fields.SEX) String sex,
                                             @Query(Fields.ACCESS_TOKEN) String accessToken,
                                             @Query(Fields.TIMESTAMP) long timestamp,
                                             @Query(Fields.SIGN) String sign);

    //绑定
    @GET("/api/user/otherlogin_bind")
    Call<BaseEntity<LoginEntity>> otherlogin_bind(@Query(Fields.TYPE) String type,
                                                  @Query(Fields.CODE) String code,
                                                  @Query(Fields.ACCESS_TOKEN) String accessToken,
                                                  @Query(Fields.PHONE) String phone,
                                                  @Query(Fields.VERIFY_CODE) String verify_code,
                                                  @Query(Fields.TIMESTAMP) long timestamp,
                                                  @Query(Fields.SIGN) String sign);

    //绑定
    @GET("/api/user/otherlogin_registerbind")
    Call<BaseEntity<LoginEntity>> otherlogin_registerbind(@Query(Fields.TYPE) String type,
                                                          @Query(Fields.CODE) String code,
                                                          @Query(Fields.LOCATION) String location,
                                                          @Query(Fields.NICK_NAME) String nickname,
                                                          @Query(Fields.AVATAR_URL) String avatar,
                                                          @Query(Fields.SEX) String sex,
                                                          @Query(Fields.ACCESS_TOKEN) String accessToken,
                                                          @Query(Fields.TIMESTAMP) long timestamp,
                                                          @Query(Fields.PWD) String pwd,
                                                          @Query(Fields.VERIFY_CODE) String verify_code,
                                                          @Query(Fields.PHONE) String phone,
                                                          @Query(Fields.SIGN) String sign);

    //注册接口
    @FormUrlEncoded
    @POST("/api/user/register")
    Call<BaseEntity<LoginEntity>> register(@Field(Fields.UID) String uid,
                                           @Field(Fields.PHONE) String number,
                                           @Field(Fields.PASSWORD) String password,
                                           @Field(Fields.VERIFY_CODE) String verifyCode,
                                           @Field(Fields.INVITE_CODE) String inviteCode,
                                           @Field(Fields.TIMESTAMP) long timestamp,
                                           @Field(Fields.SIGN) String sign);

    /**
     * 用户相关接口
     */
    //获取用户信息接口
    @GET("/api/user/info")
    Call<BaseEntity<UserInfoEntity>> getUserInfo(@Query(Fields.UID) String uid,
                                                 @Query(Fields.TIMESTAMP) long timestamp,
                                                 @Query(Fields.SIGN) String sign);

    /**
     * 联盟任务接口
     */
    @GET("/api/union/union_platform")
    Call<BaseListEntry<TaskUnionPlatformEntity>> getUnionPlatform(@Query(Fields.UID) String uid,
                                                                  @Query(Fields.PAGE) int pageIndex,
                                                                  @Query(Fields.PAGE_SIZE) int pageSize,
                                                                  @Query(Fields.TIMESTAMP) long timestamp,
                                                                  @Query(Fields.SIGN) String sign);


    //新手奖励接口
    @GET("/api/task/newbie_task")
    Call<BaseListEntry<FirstLandingTaskEntity>> getFirstLandingTask(@Query(Fields.UID) String uid,
                                                                    @Query(Fields.TIMESTAMP) long time,
                                                                    @Query(Fields.SIGN) String sign);

    //每日任务接口
    @GET("/api/task/daily_task")
    Call<BaseListEntry<DailyTaskEntity>> getDailyTask(@Query(Fields.UID) String uid,
                                                      @Query(Fields.TIMESTAMP) long time,
                                                      @Query(Fields.SIGN) String sign);

    //新手引导获取积分
    @FormUrlEncoded
    @POST("/api/score/newbie_guide_add_score")
    Call<BaseEntity> getFirstLandingScore(@Field(Fields.UID) String uid,
                                          @Field(Fields.STEP) int step,
                                          @Field(Fields.TIMESTAMP) long time,
                                          @Field(Fields.SIGN) String sign);

    //每日任务领取积分
    @FormUrlEncoded
    @POST("/api/score/daily_task_add_score")
    Call<BaseEntity> getDailyTaskAddScore(@Field(Fields.UID) String uid,
                                          @Field(Fields.TIMESTAMP) long time,
                                          @Field(Fields.SIGN) String sign);

    //

    /**
     * 获取奖励接口
     */
    //获取奖励接口
    @GET("/api/rule/invitelink")
    Call<BaseListEntry<RewardEntity>> getRewards(@Query(Fields.UID) String uid,
                                                 @Query(Fields.TIMESTAMP) long timestamp,
                                                 @Query(Fields.SIGN) String sign);

    //获取奖励接口
    @GET("/api/rule/strategy")
    Call<BaseListEntry<RaidersEntity>> getRaiders(@Query(Fields.UID) String uid,
                                                  @Query(Fields.TIMESTAMP) long timestamp,
                                                  @Query(Fields.SIGN) String sign);

    //    /**
//     * 红包信息接口
//     */
//    @GET("/api/luckymoney/info")
//    BaseEntry<LuckyMoneyInfo> getLuckyMoneyInfo(@Query(Fields.UID) String uid,
//                                                @Query(Fields.TIMESTAMP) long timestamp,
//                                                @Query(Fields.SIGN) String sign);
//
//    /**
//     * 大厅相关接口
//     */
//    //签到
    @FormUrlEncoded
    @POST("/api/signin/add")
    Call<BaseEntity<CalendarEntity>> signIn(@Field(Fields.UID) String uid,
                                            @Field(Fields.TIMESTAMP) long timestamp,
                                            @Field(Fields.SIGN) String sign);


    //获取签到日期列表
    @GET("/api/signin/get_signed_date")
    Call<BaseListEntry<String>> getSignedDateList(@Query(Fields.UID) String uid,
                                                  @Query(Fields.YEAR_MONTH) String ym,
                                                  @Query(Fields.TIMESTAMP) long timestamp,
                                                  @Query(Fields.SIGN) String sign);

    //
    //获取摇一摇次数
    @POST("/api/push/add_view")
    Call<BaseEntity> viewPush(@Query(Fields.UID) String uid,
                              @Query(Fields.PUSH_ID) String pushId,
                              @Query(Fields.TIMESTAMP) long timestamp,
                              @Query(Fields.SIGN) String sign);

    //
//    //摇一摇
//    @FormUrlEncoded
//    @POST("/api/signin/shake")
//    BaseEntry<Shake> shake(@Field(Fields.UID) String uid,
//                           @Field(Fields.TIMESTAMP) long timestamp,
//                           @Field(Fields.SIGN) String sign);
//
//    //拆红包
//    @FormUrlEncoded
//    @POST("/api/luckymoney/open")
//    BaseEntry<LuckyMoney> luckyMoney(@Field(Fields.UID) String uid,
//                                     @Field(Fields.RANK_TYPE) String type,
//                                     @Field(Fields.LUCKY_MONEY_ID) String lucky_money_id,
//                                     @Field(Fields.TIMESTAMP) long timestamp,
//                                     @Field(Fields.SIGN) String sign);
//    //领红包
//    @FormUrlEncoded
//    @POST("/api/luckymoney/get")
//    BaseEntry<GetLuckyMoney> getLuckyMoney(@Field(Fields.UID) String uid,
//                                           @Field(Fields.RANK_TYPE) String type,
//                                           @Field(Fields.TYPE_ID)   String type_id,
//                                           @Field(Fields.TIMESTAMP) long timestamp,
//                                           @Field(Fields.SIGN) String sign);
//
    //排行榜接口.
    @GET("/api/user/rank")
    Call<BaseListEntry<RankEntity>> getRank(@Query(Fields.RANK_TYPE) String type,
                                            @Query(Fields.NUMBER) int number,
                                            @Query(Fields.TIMESTAMP) long timestamp,
                                            @Query(Fields.SIGN) String sign);

    //
//    //热门任务接口.
    @GET("/api/task/hot")
    Call<BaseListEntry<HotTaskEntity>> getHotTask(@Query(Fields.UID) String uid,
                                                  @Query(Fields.NUMBER) int number,
                                                  @Query(Fields.TIMESTAMP) long timestamp,
                                                  @Query(Fields.SIGN) String sign);

    //
//    //轮播图.
//    @GET("/api/ad/home_slider")
//    BaseListEntry<Slider> getSlider(@Query(Fields.TIMESTAMP) long timestamp,
//                                    @Query(Fields.SIGN) String sign);
//
//    //搜索推荐接口
//    @GET("/api/task/search_recommend")
//    BaseEntry<Recommend> getRecommend(@Query(Fields.TIMESTAMP) long timestamp,
//                                      @Query(Fields.SIGN) String sign);
//
//    /**
//     * 任务相关接口
//     */
//    //获取任务列表接口.
    @GET("/api/task")
    Call<BaseListEntry<TaskFeatureEntity>> getFeatureTasks(@Query(Fields.UID) String uid,
                                                           @Query(Fields.CID) int cid,
                                                           @Query(Fields.PAGE) int pageIndex,
                                                           @Query(Fields.PAGE_SIZE) int pageSize,
                                                           @Query(Fields.TIMESTAMP) long timestamp,
                                                           @Query(Fields.SIGN) String sign);

    @GET("/api/task")
    Call<BaseListEntry<TaskLimitEntity>> getLimitTasks(@Query(Fields.UID) String uid,
                                                       @Query(Fields.CID) int cid,
                                                       @Query(Fields.LIMIT) int limit,
                                                       @Query(Fields.PAGE) int pageIndex,
                                                       @Query(Fields.PAGE_SIZE) int pageSize,
                                                       @Query(Fields.TIMESTAMP) long timestamp,
                                                       @Query(Fields.SIGN) String sign);

    //
//    //获取任务列表接口.
//    @GET("/api/task/my_task")
//    BaseListEntry<SignInTask> getSignInTasks(@Query(Fields.UID) String uid,
//                                             @Query(Fields.CID) int cid,
//                                             @Query(Fields.PAGE_SIZE) int pageSize,
//                                             @Query(Fields.PAGE) int pageIndex,
//                                             @Query(Fields.TIMESTAMP) long timestamp,
//                                             @Query(Fields.SIGN) String sign);
//
//
//    //获取任务列表接口.
//    @GET("/api/task")
//    BaseListEntry<SearchTask> getSearchTasks(@Query(Fields.UID) String uid,
//                                             @Query(Fields.CID) int cid,
//                                             @Query(Fields.KEYWORD) String keyword,
//                                             @Query(Fields.PAGE_SIZE) int pageSize,
//                                             @Query(Fields.PAGE) int pageIndex,
//                                             @Query(Fields.TIMESTAMP) long timestamp,
//                                             @Query(Fields.SIGN) String sign);
//
//
    //获取任务列表接口.
    @GET("/api/task/my_task")
    Call<BaseListEntry<MyTaskQuickEntity>> getMyQuickTasks(@Query(Fields.UID) String uid,
                                                           @Query(Fields.CID) int cid,
                                                           @Query(Fields.TASK_TYPE) int taskType,
                                                           @Query(Fields.TYPE) int type,
                                                           @Query(Fields.PAGE) int pageIndex,
                                                           @Query(Fields.PAGE_SIZE) int pageSize,
                                                           @Query(Fields.TIMESTAMP) long timestamp,
                                                           @Query(Fields.SIGN) String sign);

    //
    //获取任务列表接口.
    @GET("/api/task/my_task")
    Call<BaseListEntry<TaskQuickEntity>> getQuickTasks(@Query(Fields.UID) String uid,
                                                       @Query(Fields.CID) int cid,
                                                       @Query(Fields.TASK_TYPE) int type,
                                                       @Query(Fields.PAGE) int pageIndex,
                                                       @Query(Fields.PAGE_SIZE) int pageSize,
                                                       @Query(Fields.TIMESTAMP) long timestamp,
                                                       @Query(Fields.SIGN) String sign);

    //
    //获取任务详情信息.
    @GET("/api/task/detail")
    Call<BaseEntity<TaskDetailEntity>> getTaskDetail(@Query(Fields.UID) String uid,
                                                     @Query(Fields.ID) String id,
                                                     @Query(Fields.TIMESTAMP) long timestamp,
                                                     @Query(Fields.SIGN) String sign
    );

    //
    @FormUrlEncoded
    @POST("/api/task/completed_info_add")
    Call<BaseEntity<CompleteInfoEntity>> completeTask(@Field(Fields.UID) String uid,
                                                      @Field(Fields.TASK_ID) String taskId,
                                                      @Field(Fields.OPTION_ID) String optionId,
                                                      @Field(Fields.COMPLETE_INFO) String completeInfo,
                                                      @Field(Fields.MARK) String mark,
                                                      @Field(Fields.START_TIME) String startTime,
                                                      @Field(Fields.end_TIME) String endTime,
                                                      @Field(Fields.TIMESTAMP) long timestamp,
                                                      @Field(Fields.SIGN) String sign,
                                                      @Field(Fields.GUIDE) String guide
    );

    //
//    @POST("/api/task/today_completed_info")
//    BaseEntry<CompleteTaskInfo> getTaskCompleteInfo(@Query(Fields.UID) String uid,
//                                                    @Query(Fields.TASK_ID) String taskId,
//                                                    @Query(Fields.OPTION_ID) String optionId,
//                                                    @Query(Fields.TIMESTAMP) long timestamp,
//                                                    @Query(Fields.SIGN) String sign
//    );
//
//    @FormUrlEncoded
//    @POST("/api/task/join")
//    BaseEntry joinTask(@Field(Fields.UID) String uid,
//                       @Field(Fields.TASK_ID) String taskId,
//                       @Field(Fields.OPTION_ID) String optionId,
//                       @Field(Fields.TIMESTAMP) long timestamp,
//                       @Field(Fields.SIGN) String sign,
//                       @Field(Fields.GUIDE) String guide);
//
//    /**
//     * 积分兑换接口
//     */
//
    //获取物品兑换接口.
    @GET("/api/score/get_items")
    Call<BaseListEntry<CashingItemEntity>> getCashingItemList(@Query(Fields.TYPE) int type,
                                                              @Query(Fields.TIMESTAMP) long timestamp,
                                                              @Query(Fields.SIGN) String sign);

    //
//    //游龙点卡兑换接口.
//    @GET("/api/score/exchange_youlong_score")
//    BaseEntry exchangeCards(@Query(Fields.UID) String uid,
//                            @Query(Fields.ITEM_ID) String itemId,
//                            @Query(Fields.SIGN) String sign);
//
//
    //话费充值接口.
    @FormUrlEncoded
    @POST("/api/score/exchange_telephone_fare")
    Call<BaseErrorEntity> phoneFare(@Field(Fields.UID) String uid,
                                    @Field(Fields.PHONE) String mobile,
                                    @Field(Fields.ITEM_ID) String itemId,
                                    @Field(Fields.TIMESTAMP) long timestamp,
                                    @Field(Fields.SIGN) String sign
    );

    //
    //提取现金接口.
    @FormUrlEncoded
    @POST("/api/score/exchange_money")
    Call<BaseErrorEntity> checkCashingAlipay(@Field(Fields.UID) String uid,
                                             @Field(Fields.ITEM_ID) String itemId,
                                             @Field(Fields.ALIPAY) String aliPay,
                                             @Field(Fields.PHONE) String phone,
                                             @Field(Fields.NAME) String name,
                                             @Field(Fields.TIMESTAMP) long timestamp,
                                             @Field(Fields.SIGN) String sign);

    //提取现金接口.
    @FormUrlEncoded
    @POST("/api/score/exchange_money/bank")
    Call<BaseErrorEntity> checkCashingBank(@Field(Fields.UID) String uid,
                                           @Field(Fields.REAL_NAME) String realName,
                                           @Field(Fields.ITEM_ID) String itemId,
                                           @Field(Fields.BANK) String bank,
                                           @Field(Fields.BANK_NUMBER) String bankNumber,
                                           @Field(Fields.PHONE) String phone,
                                           @Field(Fields.TIMESTAMP) long timestamp,
                                           @Field(Fields.SIGN) String sign);

    //
//    //获取积分支付明细接口.
//    @GET("/api/score/")
//    BaseEntry<List<ScorePayFor>> getScoreFor(@Query(Fields.TYPE) int type,
//                                             @Query(Fields.SIGN) String sign);
//
//
    //获取积分支付明细接口.
    @GET("/api/score/detail")
    Call<BaseListEntry<ScoreDetailEntity>> getScoreDetail(@Query(Fields.UID) String uid,
                                                          @Query(Fields.TYPE) int type,
                                                          @Query(Fields.DATE) String date,
                                                          @Query(Fields.PAGE) int pageIndex,
                                                          @Query(Fields.PAGE_SIZE) int pageSize,
                                                          @Query(Fields.TIMESTAMP) long timestamp,
                                                          @Query(Fields.SIGN) String sign);

    //
//    /**
//     * 家族信息相关接口
//     */
//
//    //获取家庭信息.
    @GET("/api/user/family_info")
    Call<BaseEntity<FamilyInfoEntity>> getFamilyInfo(@Query(Fields.UID) String uid,
                                                     @Query(Fields.TIMESTAMP) long timestamp,
                                                     @Query(Fields.SIGN) String sign
    );

    //
    //获取家庭信息.
    @GET("/api/user/family_member")
    Call<BaseEntity<FamilyMemberEntity>> getFamilyMembers(@Query(Fields.UID) String uid,
                                                          @Query(Fields.KEYWORD) String keyword,
                                                          @Query(Fields.PAGE) int pageIndex,
                                                          @Query(Fields.PAGE_SIZE) int pageSize,
                                                          @Query(Fields.MEMBER_LEVEL) int level,
                                                          @Query(Fields.TIMESTAMP) long timestamp,
                                                          @Query(Fields.SIGN) String sign
    );

    //
//    //获取家庭信息.
//    @GET("/api/task/family_popularize")
//    BaseEntry<Popularize> getPopularize(@Query(Fields.UID) String uid,
//                                        @Query(Fields.KEYWORD) String keyword,
//                                        @Query(Fields.PAGE) int pageIndex,
//                                        @Query(Fields.PAGE_SIZE) int pageSize,
//                                        @Query(Fields.POPULARIZE_STATE) int state,
//                                        @Query(Fields.TIMESTAMP) long timestamp,
//                                        @Query(Fields.SIGN) String sign
//    );
//
//

    /**
     * 设置相关接口
     */
    //意见反馈
    @FormUrlEncoded
    @POST("/api/ask/add")
    Call<BaseEntity> feedBack(@Field(Fields.UID) String uid,
                              @Field(Fields.QUESTION) String question,
                              @Field(Fields.TIMESTAMP) long time,
                              @Field(Fields.SIGN) String sign
    );

    //
//
//    /**
//     * 文件上传
//     */
//    @Multipart
//    @POST("/api/task/upload_image")
//    BaseEntry upload(@Part(Fields.IMAGE) TypedFile file,
//                     @Part(Fields.UID) String uid,
//                     @Part(Fields.MARK) String mark,
//                     @Part(Fields.TIMESTAMP) long time,
//                     @Part(Fields.SIGN) String sign);
//
    @Multipart
    @POST("/api/user/upload_avatar")
    Call<BaseEntity<AvatarEntity>> editAvatar(@Part(Fields.UID) int uid,
                                              @Part(Fields.AVATAR_FILE) RequestBody file,
                                              @Part(Fields.TIMESTAMP) long time,
                                              @Part(Fields.SIGN) RequestBody sign);

    /**
     * 文件上传
     */
    @Multipart
    @POST("/api/task/upload_image")
    Call<BaseEntity> uploadImage(@Part(Fields.SINGLE_FILE) RequestBody file,
                                 @Part(Fields.UID) RequestBody uid,
                                 @Part(Fields.MARK) RequestBody mark,
                                 @Part(Fields.TIMESTAMP) long time,
                                 @Part(Fields.SIGN) RequestBody sign);

    /**
     * 文件上传
     */
    @Multipart
    @POST("/api/task/upload_images")
    Call<BaseEntity> uploadImages(@Part(Fields.UID) RequestBody uid,
                                  @PartMap Map<String, RequestBody> images,
                                  @Part(Fields.MARK) RequestBody mark,
                                  @Part(Fields.TIMESTAMP) long time,
                                  @Part(Fields.SIGN) RequestBody sign);

    /**
     * 个人资料修改
     */
    @FormUrlEncoded
    @POST("/api/user/edit/birthday")
    Call<BaseEntity> editBirthday(@Field(Fields.UID) String uid,
                                  @Field(Fields.BIRTHDAY) String birthday,
                                  @Field(Fields.TIMESTAMP) long time,
                                  @Field(Fields.SIGN) String sign
    );


    @FormUrlEncoded
    @POST("/api/user/edit/sex")
    Call<BaseEntity> editSex(@Field(Fields.UID) String uid,
                             @Field(Fields.SEX) int sex,
                             @Field(Fields.TIMESTAMP) long time,
                             @Field(Fields.SIGN) String sign
    );

    //
//    @FormUrlEncoded
//    @POST("/api/user/edit_receiving_address")
//    BaseEntry editAddress(@Field(Fields.UID) String uid,
//                          @Field(Fields.NAME) String name,
//                          @Field(Fields.PHONE) String phoneNumber,
//                          @Field(Fields.ADDRESS) String address,
//                          @Field(Fields.REMARK) String remark,
//                          @Field(Fields.TIMESTAMP) long time,
//                          @Field(Fields.SIGN) String sign
//    );
//
    @FormUrlEncoded
    @POST("/api/user/edit_password_by_old_password")
    Call<BaseEntity> editPassword(@Field(Fields.UID) String uid,
                                  @Field(Fields.OLD_PASSWORD) String password,
                                  @Field(Fields.NEW_PASSWORD) String newPassword,
                                  @Field(Fields.TIMESTAMP) long time,
                                  @Field(Fields.SIGN) String sign
    );

    @FormUrlEncoded
    @POST("/api/app/installed_app_add")
    Call<BaseEntity> addApps(@Field(Fields.PACKAGE) String options,
                             @Field(Fields.TIMESTAMP) long time,
                             @Field(Fields.SIGN) String sign
    );

    //
    @FormUrlEncoded
    @POST("/api/user/edit/nickname")
    Call<BaseEntity> editNickName(@Field(Fields.UID) String uid,
                                  @Field(Fields.NICK_NAME) String nickName,
                                  @Field(Fields.TIMESTAMP) long time,
                                  @Field(Fields.SIGN) String sign);

    //
//    @GET("/api/user/get_receiving_address")
//    BaseEntry<Address> getAddress(@Query(Fields.UID) String uid,
//                                  @Query(Fields.TIMESTAMP) long time,
//                                  @Query(Fields.SIGN) String sign
//    );
//
    @FormUrlEncoded
    @POST("/api/user/edit/invite_code")
    Call<BaseEntity> inviteCode(@Field(Fields.UID) String uid,
                                @Field(Fields.INVITE_CODE) String inviteCode,
                                @Field(Fields.TIMESTAMP) long time,
                                @Field(Fields.SIGN) String sign);

    @GET("/api/user/get_experience_log")
    Call<BaseListEntry<ExperienceEntity>> getMyExperience(@Query(Fields.UID) String uid,
                                                          @Query(Fields.PAGE) int pageIndex,
                                                          @Query(Fields.PAGE_SIZE) int pageSize,
                                                          @Query(Fields.TIMESTAMP) long time,
                                                          @Query(Fields.SIGN) String sign
    );

    //
    //获取分享邀请信息
    @GET("/api/user/get_share_info")
    Call<BaseEntity<ShareInfoEntity>> getMyShareInfo(@Query(Fields.UID) String uid,
                                                     @Query(Fields.TIMESTAMP) long time,
                                                     @Query(Fields.SIGN) String sign
    );

    @FormUrlEncoded
    @POST("/api/user/newbieGuideShareCompleted")
    Call<BaseEntity> getShareSuccessful(@Field(Fields.UID) String uid,
                                        @Field(Fields.TIMESTAMP) long time,
                                        @Field(Fields.SIGN) String sign
    );
//


    //
//    @FormUrlEncoded
//    @POST("/api/user/skipNewbieGuide")
//    BaseEntry getFirstLandingSkip(@Field(Fields.UID) String uid,
//                                  @Field(Fields.TIMESTAMP) long time,
//                                  @Field(Fields.SIGN) String sign
//    );
//
//    @GET("/api/union/get_user_union_total_score")
//    BaseEntry<TotalScore> getUnionTotalScore(@Query(Fields.UID) String uid,
//                                             @Query(Fields.UNION_ID) String unionId,
//                                             @Query(Fields.TIMESTAMP) long time,
//                                             @Query(Fields.SIGN) String sign);
//
    @GET("/api/score/check_is_exchange_qb")
    Call<CheckQbEntity> checkQbHasExChange(@Query(Fields.UID) String uid,
                                           @Query(Fields.TIMESTAMP) long time,
                                           @Query(Fields.SIGN) String sign
    );

    //
    @FormUrlEncoded
    @POST("/api/score/exchange_qb")
    Call<QbEntity> qbExchange(@Field(Fields.UID) String uid,
                              @Field(Fields.QQ) String qq,
                              @Field(Fields.ITEM_ID) int itemId,
                              @Field(Fields.TIMESTAMP) long time,
                              @Field(Fields.SIGN) String sign);

    //
    @GET("/api/app/disable_device_info")
    Call<BaseErrorEntity<CheckEntity>> checkAndroid(@Query(Fields.SIM_STATE) int simState,
                                                    @Query(Fields.SERIAL) String serial,
                                                    @Query(Fields.TIMESTAMP) long time,
                                                    @Query(Fields.SIGN) String sign
    );

    @FormUrlEncoded
    @POST("/api/app/add_disable_device_info")
    Call<BaseEntity> addDisableDeviceInfo(@Field(Fields.DEVICE_INFO) String deviceInfo,
                                          @Field(Fields.TIMESTAMP) long time,
                                          @Field(Fields.SIGN) String sign);

    @GET("/api/note/index")
    Call<BaseListEntry<MsgEntity>> getMsgList(@Query(Fields.UID) String uid,
                                              @Query(Fields.PAGE) int pageIndex,
                                              @Query(Fields.PAGE_SIZE) int pageSize,
                                              @Query(Fields.READ_STATE) int readed,
                                              @Query(Fields.TIMESTAMP) long time,
                                              @Query(Fields.SIGN) String sign);

    @GET("/api/note/read")
    Call<BaseEntity> readMsg(@Query(Fields.UID) String uid,
                             @Query(Fields.MSG_ID) String msgId,
                             @Query(Fields.TIMESTAMP) long time,
                             @Query(Fields.SIGN) String sign);

    @GET("/api/note/detail")
    Call<BaseEntity<MsgEntity>> getMsgDetail(@Query(Fields.UID) String uid,
                                             @Query(Fields.MSG_ID) String msgId,
                                             @Query(Fields.TIMESTAMP) long time,
                                             @Query(Fields.SIGN) String sign);
}
