package com.xiaoxing.login.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.xiaoxing.gifloadingview.LoadingDialogUtil;
import com.xiaoxing.login.R;
import com.xiaoxing.login.R2;
import com.xiaoxing.login.di.component.DaggerLoginComponent;
import com.xiaoxing.login.di.module.LoginModule;
import com.xiaoxing.login.mvp.contract.LoginContract;
import com.xiaoxing.login.mvp.model.entity.Login;
import com.xiaoxing.login.mvp.presenter.LoginPresenter;
import com.xw.repo.XEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonres.utils.ToolbarUtils;
import me.jessyan.armscomponent.commonsdk.core.BaseConstants;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.ExitUtil;
import me.jessyan.armscomponent.commonsdk.utils.NetworkUtil;
import me.jessyan.armscomponent.commonsdk.utils.Utils;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static me.jessyan.armscomponent.commonres.utils.ControlKeyboardLayoutUtil.controlKeyboardLayout;

@Route(path = RouterHub.XIAO_XING_LOGIN_LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R2.id.xet_username)
    XEditText xetUsername;
    @BindView(R2.id.xet_password)
    XEditText xetPassword;
    @BindView(R2.id.btn_login)
    Button btnLogin;
//    @BindView(R2.id.ll_root)
//    LinearLayout mLLRoot;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.xiaoxing_login_activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ToolbarUtils.initToolbarTitleBack(this, getString(R.string.xiaoxing_login_login));
//        controlKeyboardLayout(mLLRoot, btnLogin);
        autoLogin();

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


    @OnClick({R2.id.tv_forgot_pwd, R2.id.tv_reg, R2.id.btn_login, R2.id.img_wechat})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_reg) {
            Utils.navigation(LoginActivity.this, RouterHub.XIAO_XING_LOGIN_REGISTER_SEND_PHONE_ACTIVITY);
        } else if (view.getId() == R.id.tv_forgot_pwd) {
            Utils.navigation(LoginActivity.this, RouterHub.XIAO_XING_LOGIN_FORGOT_PWD_SEND_PHONE_ACTIVITY);
        } else if (view.getId() == R.id.btn_login) {
            DeviceUtils.hideSoftKeyboard(this, btnLogin);
            login();
        } else if (view.getId() == R.id.img_wechat) {
            ArmsUtils.snackbarText("正在开发中...");
        }

    }

    private void login() {
//        if (NetworkUtil.checkNetworkAvailable(this)) {

        if (TextUtils.isEmpty(getUsername())) {
            ArmsUtils.snackbarText("帐户名/手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(getPassword())) {
            ArmsUtils.snackbarText("密码不能为空");
            return;
        }

        mPresenter.doLogin(this, getUsername(), getPassword());
//        }
    }

    @NonNull
    private String getPassword() {
        return xetPassword.getText().toString();
    }

    @NonNull
    private String getUsername() {
        return xetUsername.getText().toString();
    }

    @Override
    public void doLoginSuccess(Login login) {
        ArmsUtils.snackbarText(login.getMsg());
        if (login.getCode() == 200) {
            mSharedPreferencesHelper.putString(BaseConstants.TOKEN, login.getData().getToken());
            mSharedPreferencesHelper.putString(BaseConstants.UID, login.getData().getUser_id());
            mSharedPreferencesHelper.putString(BaseConstants.USERNAME, login.getData().getUser_name());
            mSharedPreferencesHelper.putString(BaseConstants.PASSWORD, getPassword());
            mSharedPreferencesHelper.putBoolean(BaseConstants.IS_LOGIN, true);
            Utils.navigation(LoginActivity.this, RouterHub.SALES_CLIENT_PAI_MAI_MAIN_ACTIVITY);
            killMyself();
        }


    }

    /**
     * 自动登陆
     */
    public void autoLogin() {
        if (NetworkUtil.checkNetworkAvailable(this)) {

            String username = mSharedPreferencesHelper.getString(BaseConstants.USERNAME);
            String pwd = mSharedPreferencesHelper.getString(BaseConstants.PASSWORD);
            if (username != null && pwd != null) {

                if (BaseConstants.AUTO_LOGIN) {
                    xetUsername.setText(username);
                    xetPassword.setText(pwd);
                    login();
                } else {
                    xetUsername.setText(mSharedPreferencesHelper.getString(BaseConstants.USERNAME));

                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitUtil.exit(this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
