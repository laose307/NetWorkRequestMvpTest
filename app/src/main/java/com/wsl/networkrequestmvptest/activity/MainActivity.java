package com.wsl.networkrequestmvptest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wsl.networkrequestmvp.base.BaseActivity;
import com.wsl.networkrequestmvp.base.mvp.BaseModel;
import com.wsl.networkrequestmvptest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter> implements MainView<BaseModel<List<MainBean>>> {


    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rlt_base)
    RelativeLayout rltBase;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
//        tv_msg = findViewById(R.id.tv_msg);
//        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.commentAdd();
            }
        });

//        B
    }





    @Override
    public void onMainSuccess(BaseModel<List<MainBean>> o) {
          tvMsg.setText(o.getData().toString());
    }


}
