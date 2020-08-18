package com.wsl.networkrequestmvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wsl.networkrequestmvp.base.mvp.BaseModel;
import com.wsl.networkrequestmvp.base.mvp.BasePresenter;
import com.wsl.networkrequestmvp.base.mvp.BaseView;
import com.wsl.networkrequestmvp.promptdialog.PromptDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * File descripition:   ftagment 基类
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    public View view;

    public Context mContext;
    protected P mPresenter;
    protected Unbinder mUnbinder;
    protected abstract P createPresenter();
    private PromptDialog promptDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        mPresenter = createPresenter();

        this.initToolbar(savedInstanceState);
        this.initData();

        return view;
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

    public void showToast(String str) {
    }

    public void showLongToast(String str) {
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void onErrorCode(BaseModel model) {
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
            promptDialog = new PromptDialog(getActivity());
        }
        promptDialog.showLoading("加载中...",false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.view = null;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDestroyView()
    {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
