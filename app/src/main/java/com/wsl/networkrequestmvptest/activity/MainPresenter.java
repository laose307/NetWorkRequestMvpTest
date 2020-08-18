package com.wsl.networkrequestmvptest.activity;


import com.wsl.networkrequestmvp.base.ApiRetrofit;
import com.wsl.networkrequestmvp.base.mvp.BaseModel;
import com.wsl.networkrequestmvp.base.mvp.BaseObserver;
import com.wsl.networkrequestmvp.base.mvp.BasePresenter;
import com.wsl.networkrequestmvptest.ApiServer;

import java.util.List;

/**
 * File descripition:
 *
 * @author lp
 * @date 2018/6/19
 */

public class MainPresenter extends BasePresenter<MainView> {

    ApiServer apiServer= ApiRetrofit.getInstance().create(ApiServer.class);

    public MainPresenter(MainView baseView) {
        super(baseView);
    }

    public void commentAdd() {

        addDisposable(apiServer.getMain("year"), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onMainSuccess((BaseModel<List<MainBean>>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });


    }
}
