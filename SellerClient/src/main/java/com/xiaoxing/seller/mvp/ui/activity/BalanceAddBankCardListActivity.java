package com.xiaoxing.seller.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.xiaoxing.seller.R;
import com.xiaoxing.seller.R2;
import com.xiaoxing.seller.di.component.DaggerBalanceAddBankCardListComponent;
import com.xiaoxing.seller.di.module.BalanceAddBankCardListModule;
import com.xiaoxing.seller.mvp.contract.BalanceAddBankCardListContract;
import com.xiaoxing.seller.mvp.presenter.BalanceAddBankCardListPresenter;
import com.xiaoxing.seller.mvp.ui.adapter.BalanceDetailsAdapter;
import com.xiaoxing.seller.mvp.ui.entity.BalanceDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.utils.ToolbarUtils;
import me.jessyan.armscomponent.commonsdk.core.RouterHub;
import me.jessyan.armscomponent.commonsdk.utils.Utils;

import static android.widget.LinearLayout.VERTICAL;
import static com.jess.arms.utils.Preconditions.checkNotNull;

@Route(path = RouterHub.SELLER_CLIENT_BALANCE_ADD_BANK_CARD_LIST_ACTIVITY)
public class BalanceAddBankCardListActivity extends BaseActivity<BalanceAddBankCardListPresenter> implements BalanceAddBankCardListContract.View, OnRefreshListener {


    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    private BalanceDetailsAdapter mAdapter;

    private View mEmptyLayout;
    private static boolean mIsNeedDemo = true;

    @BindView(R2.id.btnRight)
    Button mBtnRight;
    private View.OnClickListener mRightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Utils.navigation(BalanceAddBankCardListActivity.this, RouterHub.SELLER_CLIENT_BALANCE_VERIFIED_ACTIVITY);

        }
    };


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ToolbarUtils.initToolbarTitleBackWithRightButton(this, getString(R.string.seller_client_balance_add_bank_card), mRightListener);

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.FixedBehind).setPrimaryColorId(R.color.public_colorPrimary).setAccentColorId(android.R.color.white));
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));

        mEmptyLayout = findViewById(R.id.empty);

        ImageView image = (ImageView) findViewById(R.id.empty_image);
        image.setImageResource(R.drawable.ic_empty);

        TextView empty = (TextView) findViewById(R.id.empty_text);
        empty.setText("暂无数据下拉刷新");

        /*主动演示刷新*/
        if (mIsNeedDemo) {
            mRefreshLayout.getLayout().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mIsNeedDemo) {
                        mRefreshLayout.autoRefresh();
                    }
                }
            }, 3000);
            mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
                @Override
                public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                    mIsNeedDemo = false;
                }
            });
        }
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mRefreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {

                mRecyclerView.setLayoutManager(new LinearLayoutManager(BalanceAddBankCardListActivity.this));
                mRecyclerView.addItemDecoration(new DividerItemDecoration(BalanceAddBankCardListActivity.this, VERTICAL));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(mAdapter = new BalanceDetailsAdapter(loadModels()));
                //                mRecyclerView.setAdapter(new BaseRecyclerAdapter<Item>(Arrays.asList(Item.values()), simple_list_item_2, FragmentOrderList.this) {
                //                    @Override
                //                    protected void onBindViewHolder(SmartViewHolder holder, Item model, int position) {
                //                        holder.text(android.R.id.text1, model.name());
                ////                        holder.text(android.R.id.text2, model.name);
                ////                        holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
                //                    }
                //                });
                mRefreshLayout.finishRefresh();
                mEmptyLayout.setVisibility(View.GONE);
            }
        }, 2000);
    }

    /**
     * 模拟数据
     */
    private List<BalanceDetails> loadModels() {
        List<BalanceDetails> addressLists = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            BalanceDetails addressList = new BalanceDetails();
            addressList.setName("￥100" + i);
            addressList.setAddress("address" + i);
            addressList.setPhone("2018-06-0" + i);
            addressLists.add(addressList);
        }


        return addressLists;
    }


    @Override

    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBalanceAddBankCardListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .balanceAddBankCardListModule(new BalanceAddBankCardListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.public_smart_refresh_layout_title_right_button; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
