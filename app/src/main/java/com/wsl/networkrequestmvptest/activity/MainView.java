package com.wsl.networkrequestmvptest.activity;


import com.wsl.networkrequestmvp.base.mvp.BaseView;

/**
 * File descripition:
 */

public interface MainView<T> extends BaseView {
    void onMainSuccess(T o);
}
