package com.xiaoxing.login.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.xiaoxing.gifloadingview.LoadingDialogUtil;
import com.xiaoxing.login.R;
import com.xiaoxing.login.di.component.DaggerProtocolComponent;
import com.xiaoxing.login.di.module.ProtocolModule;
import com.xiaoxing.login.mvp.contract.ProtocolContract;
import com.xiaoxing.login.mvp.presenter.ProtocolPresenter;

import me.jessyan.armscomponent.commonres.utils.ToolbarUtils;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;

import static com.jess.arms.utils.Preconditions.checkNotNull;

@Route(path = RouterHub.XIAO_XING_LOGIN_PROTOCOL_ACTIVITY)
public class ProtocolActivity extends BaseActivity<ProtocolPresenter> implements ProtocolContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProtocolComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .protocolModule(new ProtocolModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.xiaoxing_login_activity_protocol; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ToolbarUtils.initToolbarTitleBack(this, getString(R.string.xiaoxing_login_protocol));

    }

    @Override
    public void showLoading() {
        LoadingDialogUtil.showGifdialog1(getSupportFragmentManager(), R.drawable.loading);
    }

    @Override
    public void hideLoading() {
        LoadingDialogUtil.dismissDialog();
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
