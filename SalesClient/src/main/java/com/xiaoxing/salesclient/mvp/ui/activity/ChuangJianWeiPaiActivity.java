package com.xiaoxing.salesclient.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.xiaoxing.salesclient.di.component.DaggerChuangJianWeiPaiComponent;
import com.xiaoxing.salesclient.di.module.ChuangJianWeiPaiModule;
import com.xiaoxing.salesclient.mvp.contract.ChuangJianWeiPaiContract;
import com.xiaoxing.salesclient.mvp.presenter.ChuangJianWeiPaiPresenter;

import me.jessyan.armscomponent.commonres.utils.ToolbarUtils;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import xiaoxing.com.salesclient.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@Route(path = RouterHub.SALES_CLIENT_CHUANG_JIAN_WEI_PAI_ACTIVITY)
public class ChuangJianWeiPaiActivity extends BaseActivity<ChuangJianWeiPaiPresenter> implements ChuangJianWeiPaiContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChuangJianWeiPaiComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .chuangJianWeiPaiModule(new ChuangJianWeiPaiModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.sales_client_activity_chuang_jian_wei_pai; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ToolbarUtils.initToolbarTitleBack(this, "微拍发布");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
