package com.wsl.networkrequestmvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wsl.logs.BaseLogsActivity;
import com.wsl.networkrequestmvp.base.mvp.BaseModel;
import com.wsl.networkrequestmvp.base.mvp.BasePresenter;
import com.wsl.networkrequestmvp.base.mvp.BaseView;
import com.wsl.networkrequestmvp.promptdialog.PromptDialog;
import com.wsl.networkrequestmvp.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.wsl.networkrequestmvp.base.mvp.BaseObserver.NETWORK_ERROR;


/**
 * File descripition: activity基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends BaseLogsActivity implements BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    public Context mContext;
    protected P mPresenter;
    protected abstract P createPresenter();
    //错误提示框  警告框  成功提示框 加载进度框 （只是提供个案例 可自定义）
    private PromptDialog promptDialog;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mContext = this;
        if(getLayoutId()>0){
            setContentView(getLayoutId());
            mPresenter = createPresenter();
            bindUI(null);
        }

        setStatusBar();

        this.initToolbar(savedInstanceState);
        this.initData();

    }
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();
    /**
     * 处理顶部title
     *
     * @param savedInstanceState
     */
    protected abstract void initToolbar(Bundle savedInstanceState);
    /**
     * 数据初始化操作
     */
    protected abstract void initData();
    /**
     * 此处设置沉浸式地方
     */
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }
    /**
     * 封装toast方法（自行去实现）
     *
     * @param str
     */
    public void showToast(String str) {
    }

    public void showLongToast(String str) {
    }
    @Override
    public void showError(String msg) {
        showToast(msg);
    }
    /**
     * 返回所有状态  除去指定的值  可设置所有（根据需求）
     *
     * @param model
     */
    @Override
    public void onErrorCode(BaseModel model) {
        if (model.getCode() == NETWORK_ERROR) {

        }
    }
    //显示加载进度框回调
    @Override
    public void showLoading() {
        showLoadingDialog();
    }
    //隐藏进度框回调
    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }
    /**
     * 进度款消失
     */
    public void closeLoadingDialog() {
        if (promptDialog != null) {
            promptDialog.dismiss();
        }
    }
    /**
     * 加载中...
     */
    public void showLoadingDialog() {
        if (promptDialog == null) {
            promptDialog = new PromptDialog(this);
        }
        promptDialog.showLoading("加载中...",false);
    }
    @Override
    protected void onDestroy() {

        if(unbinder!=null){
            unbinder.unbind();
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
