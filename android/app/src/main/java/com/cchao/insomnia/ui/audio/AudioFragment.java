package com.cchao.insomnia.ui.audio;

import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.cchao.insomnia.BR;
import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.AudioPlayItemBinding;
import com.cchao.insomnia.databinding.ComposeFragmentBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.AudioManager;
import com.cchao.insomnia.manager.GlobalHelper;
import com.cchao.insomnia.model.javabean.compose.AudioBean;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.model.event.CommonEvent;
import com.cchao.simplelib.ui.adapter.AbstractPagerAdapter;
import com.cchao.simplelib.ui.adapter.DataBindQuickAdapter;
import com.cchao.simplelib.ui.fragment.BaseStatefulFragment;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编排音频
 *
 * @author cchao
 * @version 2019-07-31.
 */
public class AudioFragment extends BaseStatefulFragment<ComposeFragmentBinding> {
    List<List<AudioBean>> mAudioList;
    Map<AudioBean, View> mPlayAudioMap = new HashMap<>();
    LinearLayout mContainer;
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.compose_fragment;
    }

    @Override
    protected void initEventAndData() {
        mContainer = mDataBind.container;
        mViewPager = mDataBind.viewPager;
        onLoadData();
        initPageAdapter();
    }

    @Override
    public void onEvent(CommonEvent event) {
        super.onEvent(event);
        switch (event.getCode()) {
            case Constants.Event.play_audio:
                addItem(event.getBean());
                break;
            case Constants.Event.pause_audio:
                removeItem(event.getBean());
                break;
        }
    }

    private void initPageAdapter() {
        mViewPager.setAdapter(new AbstractPagerAdapter<List<AudioBean>>(mContext, R.layout.recycler, mAudioList) {
            @Override
            public void convert(View convertView, int position, List<AudioBean> item) {
                initAudioAdapter(convertView.findViewById(R.id.recycler_view), mAudioList.get(position));
            }
        });
        mViewPager.setOffscreenPageLimit(5);

        // init indicator
        CircleNavigator circleNavigator = new CircleNavigator(mContext);
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(mAudioList.size());
        circleNavigator.setCircleColor(Color.parseColor("#88229FFD"));
        circleNavigator.setCircleClickListener(index -> {
            //
            mViewPager.setCurrentItem(index);
        });
        mDataBind.magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(mDataBind.magicIndicator, mViewPager);
    }

    private void initAudioAdapter(RecyclerView recyclerView, List<AudioBean> data) {
        DataBindQuickAdapter<AudioBean> adapter;
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(adapter = new DataBindQuickAdapter<AudioBean>(R.layout.audio_item, data) {
            @Override
            protected void convert(DataBindViewHolder helper, AudioBean item) {
                helper.getBinding().setVariable(BR.bean, item);
                ((ImageView) helper.getView(R.id.image)).setImageResource(item.getImage());
                mPlayAudioMap.put(item, helper.itemView);
            }
        });
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int mSpace = UiHelper.dp2px(10);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = mSpace * 3 / 2;
                outRect.right = 0;
                outRect.top = 0;
                outRect.left = mSpace / 2;
            }
        });

        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            AudioManager.play(adapter.getItem(position));
        });
    }

    @Override
    protected void onLoadData() {
        mAudioList = GlobalHelper.getAudioList();
    }

    /**
     * 将播放中的音频，显示到界面
     */
    private void addItem(AudioBean bean) {
        AudioPlayItemBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.audio_play_item, mContainer, false);
        binding.setClick(v -> {
            switch (v.getId()) {
                case R.id.close:
                    AudioManager.pause(bean);
                    break;
            }
        });
        binding.progressBar.setProgress((int) (bean.getVolume() * 100));
        binding.image.setImageResource(bean.getImage());
        binding.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bean.setVolume(progress / 100f);
                AudioManager.changeVolume(bean);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.getRoot().setTag(bean.getSrc());
        mContainer.addView(binding.getRoot());
        toggleAudioPlay(true, bean);
    }

    private void removeItem(AudioBean bean) {
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            if (mContainer.getChildAt(i).getTag().equals(bean.getSrc())) {
                mContainer.removeViewAt(i);
                break;
            }
        }
        toggleAudioPlay(false, bean);
    }

    /**
     * 音频播放状态切换时，修改显示样式
     *
     * @param toggle 开关
     * @param bean   实体
     */
    void toggleAudioPlay(boolean toggle, AudioBean bean) {
        if (mPlayAudioMap.get(bean) == null) {
            return;
        }
        int drawable = toggle ? R.drawable.audio_item_play_bg : R.drawable.audio_item_def_bg;

        View view = mPlayAudioMap.get(bean).findViewById(R.id.audio_container);
        // 修改背景，阴影
        view.setBackgroundResource(drawable);
        ViewCompat.setElevation(view, toggle ? 8 : 0);
        UiHelper.setVisibleElseGone(mDataBind.tip, mContainer.getChildCount() == 0);

        // 着色
        if (toggle) {
            ImageViewCompat.setImageTintList(view.findViewById(R.id.image), ColorStateList.valueOf(UiHelper.getColor(R.color.eee_color)));
        } else {
            ImageViewCompat.setImageTintList(view.findViewById(R.id.image), null);
        }
    }
}
