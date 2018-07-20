package com.xiaoxing.login.mvp.presenter;

import android.app.Application;
import android.content.Context;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.xiaoxing.login.mvp.contract.RegisterVerificationCodeContract;
import com.xiaoxing.login.mvp.model.entity.Login;
import com.xiaoxing.login.mvp.model.entity.SmsSend;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class RegisterVerificationCodePresenter extends BasePresenter<RegisterVerificationCodeContract.Model, RegisterVerificationCodeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RegisterVerificationCodePresenter(RegisterVerificationCodeContract.Model model, RegisterVerificationCodeContract.View rootView) {
        super(model, rootView);
    }

    public void smsSend(String phone, String code) {

        mModel.smsSend(phone, code).subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<SmsSend>(mErrorHandler) {
                    @Override
                    public void onNext(SmsSend smsSend) {
                        mRootView.smsSendSuccess(smsSend);
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
