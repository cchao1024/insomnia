package com.cchao.insomnia.ui.play;

import android.view.View;

import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.PlayFragmentBinding;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.ui.fragment.BaseStatefulFragment;

/**
 * @author cchao
 * @version 2019-05-13.
 */
public class PlayFragment extends BaseStatefulFragment<PlayFragmentBinding> implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.play_fragment;
    }

    @Override
    protected void initEventAndData() {
        mDataBind.setClick(this);
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sleep_478:
                Router.turnTo(mContext, Play478Activity.class).start();
                break;
            case R.id.count_sheep:
                Router.turnTo(mContext, CountSheepActivity.class).start();
                break;
        }
    }
}
