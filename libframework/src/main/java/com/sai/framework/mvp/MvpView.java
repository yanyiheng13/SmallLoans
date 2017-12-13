package com.sai.framework.mvp;

import android.content.Context;

public interface MvpView {
    void showLoadDialog();
    void hideLoadDialog();
    Context getContext();

}
