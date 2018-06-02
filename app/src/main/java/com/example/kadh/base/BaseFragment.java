package com.example.kadh.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kadh.app.App;
import com.example.kadh.component.ActivityComponent;
import com.example.kadh.component.AppComponent;
import com.example.kadh.component.DaggerActivityComponent;
import com.example.kadh.module.ActivityModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: kadh
 * @email : 36870855@qq.com
 * @date : 2018/5/28
 * @blog : http://www.nicaicaicai.com
 * @desc :
 */

public abstract class BaseFragment extends Fragment {

    protected View              parentView;
    protected Context           mContext;
    protected LayoutInflater    inflater;
    protected FragmentActivity  mActivity;
    private   Unbinder          mUnbinder;
    protected ActivityComponent mActivityComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        mActivity = getSupportActivity();
        mContext = mActivity;
        this.inflater = inflater;
        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(getActivity()))
                .build();
        setupActivityComponent(App.getApp().getAppComponent());
        attachView();
        initDatas();
        configViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @LayoutRes
    public abstract int getLayoutResId();

    protected abstract void configViews();

    protected abstract void initDatas();

    protected abstract void attachView();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    protected View getParentView() {
        return parentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

}
