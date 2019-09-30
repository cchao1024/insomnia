package com.cchao.insomnia.ui.main;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cchao.insomnia.R;
import com.cchao.insomnia.api.Apis;
import com.cchao.insomnia.api.RetrofitHelper;
import com.cchao.insomnia.databinding.HomeDrawerMenuItemBinding;
import com.cchao.insomnia.databinding.MainActivityBinding;
import com.cchao.insomnia.databinding.MainFeedBackDialogBinding;
import com.cchao.insomnia.databinding.MainTimeDownDialogBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.MusicPlayer;
import com.cchao.insomnia.manager.TimeCountHelper;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.insomnia.model.javabean.home.NavItem;
import com.cchao.insomnia.model.javabean.user.UserBean;
import com.cchao.insomnia.ui.account.EditUserInfoActivity;
import com.cchao.insomnia.ui.account.UserInfoActivity;
import com.cchao.insomnia.ui.account.WishListActivity;
import com.cchao.insomnia.ui.audio.AudioFragment;
import com.cchao.insomnia.ui.fall.FallFragment;
import com.cchao.insomnia.ui.global.FragmentContainerActivity;
import com.cchao.insomnia.ui.play.PlayFragment;
import com.cchao.insomnia.ui.post.PostBoxFragment;
import com.cchao.insomnia.view.Dialogs;
import com.cchao.simplelib.Const;
import com.cchao.simplelib.LibCore;
import com.cchao.simplelib.core.ImageLoader;
import com.cchao.simplelib.core.Logs;
import com.cchao.simplelib.core.PrefHelper;
import com.cchao.simplelib.core.Router;
import com.cchao.simplelib.core.RxBus;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.activity.BaseActivity;
import com.cchao.simplelib.ui.fragment.BaseFragment;
import com.cchao.simplelib.ui.web.WebViewActivity;
import com.cchao.simplelib.util.LanguageUtil;
import com.cchao.simplelib.util.StringHelper;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * 主页
 *
 * @author cchao
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private LinearLayout mDrawerLinear;
    private ImageView mUserPhotoImage;
    TextView mCountDownTextView;

    final int[] mTabTitleArr = {R.string.tab_name_audio, R.string.tab_name_0, R.string.tab_name_1};
    List<BaseFragment> mFragments = new ArrayList<>();
    private MainActivityBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mBinding.setClick(this);

        initEvent();
        findViews();
        initToolbar();
        initTabLayout();
        initDrawerLayout();
        initMenu();
    }

    private void initEvent() {
        addSubscribe(RxBus.getDefault().toObservable(UserBean.class)
            .compose(RxHelper.toMain())
            .subscribe(userBean -> {
                updateUserViews();
            }));

        addSubscribe(RxBus.get().toObservable(event -> {
            switch (event.getCode()) {
                case Constants.Event.update_count_down:
                    mCountDownTextView.setText(event.getContent());
                    break;
            }
        }));
    }

    private void findViews() {
        mTabLayout = mBinding.tabLayout;
        mViewPager = mBinding.viewPager;
        mDrawerLayout = mBinding.drawerLayout;
        mToolbar = mBinding.mainToolbar;
    }

    /**
     * tabLayout绑定ViewPager 懒加载Fragment
     */
    private void initTabLayout() {
        mFragments.add(new AudioFragment());
        mFragments.add(new FallFragment());
        mFragments.add(new PostBoxFragment());

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mTabTitleArr.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(mTabTitleArr[position]);
            }

        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mFragments.size());
    }

    private void initToolbar() {
        //实现侧滑菜单状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(UiHelper.getColor(R.color.transparent));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.ic_logo);
//        mToolbar.setLogo(R.drawable.ic_main_nav_logo);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_count:
                showTimeDownDialog();
                break;
            case R.id.action_game:
                Router.turnTo(mContext, FragmentContainerActivity.class)
                    .putExtra(Constants.Extra.TITLE, "Game")
                    .putExtra(Constants.Extra.Fragment, Constants.Container.PlayGame)
                    .start();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initMenu() {
        mDrawerLinear = findViewById(R.id.home_drawer_linear);
        mUserPhotoImage = findViewById(R.id.icon_portrait);
        updateUserViews();

        // 填充左侧item实体
        List<NavItem> menus = new ArrayList<>();
        menus.add(NavItem.of(Constants.Drawer.Wish, R.string.wish_list, R.drawable.drawer_wish, NavItem.Margin.top));
        menus.add(NavItem.of(Constants.Drawer.TimeDown, R.string.time_down, R.drawable.drawer_time, NavItem.Margin.bottom));
        // divier
        menus.add(NavItem.of(Constants.Drawer.Divider, "", 0, NavItem.Margin.none));

        menus.add(NavItem.of(Constants.Drawer.Lang, R.string.select_language, R.drawable.ic_language, NavItem.Margin.top));
        menus.add(NavItem.of(Constants.Drawer.FeedBack, R.string.feed_back, R.drawable.drawer_feedback, NavItem.Margin.none));
        menus.add(NavItem.of(Constants.Drawer.AboutUs, R.string.about_us, R.drawable.drawer_about, NavItem.Margin.none));
        menus.add(NavItem.of(Constants.Drawer.Settings, R.string.settings, R.drawable.drawer_settings, NavItem.Margin.bottom));

        // 加入到linearLayout
        for (int i = 0; i < menus.size(); i++) {
            NavItem item = menus.get(i);
            View itemView;
            // 分割线
            if (item.ID == Constants.Drawer.Divider) {
                itemView = new View(mContext);
                itemView.setBackgroundColor(UiHelper.getColor(R.color.divider_e5));
                itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , UiHelper.dp2px(10)));
                mDrawerLinear.addView(itemView);
                continue;
            } else {
                HomeDrawerMenuItemBinding binding = DataBindingUtil.inflate(mLayoutInflater
                    , R.layout.home_drawer_menu_item, mDrawerLinear, false);
                binding.menuIcon.setImageResource(item.mIconRes);
                binding.menuText.setText(item.mLabelText);

                itemView = binding.getRoot();
                mDrawerLinear.addView(binding.getRoot());

                LinearLayout.LayoutParams layoutParams = ((LinearLayout.LayoutParams) binding.getRoot().getLayoutParams());

                if (item.getMargin() == NavItem.Margin.top) {
                    layoutParams.setMargins(0, UiHelper.dp2px(8), 0, 0);
                } else if (item.getMargin() == NavItem.Margin.bottom) {
                    layoutParams.setMargins(0, 0, 0, UiHelper.dp2px(8));
                }

                // countDown 临时保存 value
                if (item.ID == Constants.Drawer.TimeDown) {
                    mCountDownTextView = binding.menuValue;
                }
            }

            itemView.setOnClickListener(v -> clickMenuItem(item.ID));
        }
    }

    private void clickMenuItem(int menu) {
        switch (menu) {
            case Constants.Drawer.Wish:
                Router.turnTo(mContext, WishListActivity.class).start();
                break;
            case Constants.Drawer.FeedBack:
                showFeedBackDialog();
                break;
            case Constants.Drawer.AboutUs:
                Router.turnTo(mContext, WebViewActivity.class)
                    .putExtra(Const.Extra.Web_View_Tile, getString(R.string.about_us))
                    .putExtra(Const.Extra.Web_View_Url, Apis.aboutUs)
                    .start();
                break;
            case Constants.Drawer.TimeDown:
                showTimeDownDialog();
                break;
            case Constants.Drawer.Settings:
                if (LibCore.getInfo().isDebug()) {
                    Dialogs.showDebug(mLayoutInflater);
                }
                Router.turnTo(mContext, SettingsActivity.class).start();
                break;

            case Constants.Drawer.Lang:
                ArrayMap<String, Integer> map = new ArrayMap<>();
                map.put(UiHelper.getString(R.string.lang_ZH), R.string.lang_abbr_ZH);
                map.put(UiHelper.getString(R.string.lang_ZH_TW), R.string.lang_abbr_ZH_TW);
                map.put(UiHelper.getString(R.string.lang_EN), R.string.lang_abbr_EN);
                map.put(UiHelper.getString(R.string.lang_ES), R.string.lang_abbr_ES);
                map.put(UiHelper.getString(R.string.lang_FR), R.string.lang_abbr_FR);
                map.put(UiHelper.getString(R.string.lang_IT), R.string.lang_abbr_IT);
                map.put(UiHelper.getString(R.string.lang_RU), R.string.lang_abbr_RU);

                int curSelectIndex = 0;
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    if (UiHelper.getString(entry.getValue()).equals(LanguageUtil.Cur_Language)) {
                        curSelectIndex = map.indexOfKey(entry.getKey());
                        break;
                    }
                }

                String[] array = map.keySet().toArray(new String[]{});
                new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.select_language))
                    .setSingleChoiceItems(array, curSelectIndex, (dialog, which) -> {
                        dialog.dismiss();
                        LanguageUtil.changeLanguage(UiHelper.getString(map.get(array[which])));
                        recreate();
                    }).show();

                break;
            default:
                break;
        }
        closeDrawer();
    }

    private void showFeedBackDialog() {
        MainFeedBackDialogBinding binding = DataBindingUtil.inflate(mLayoutInflater
            , R.layout.main_feed_back_dialog, null, false);

        // 弹出对话框
        Dialog dialog = new AlertDialog.Builder(mContext)
            .setView(binding.getRoot())
            .show();

        // 填入邮箱
        binding.email.setText(UserManager.getUserBean().getEmail());
        binding.setClick(view -> {
            switch (view.getId()) {
                case R.id.submit:
                    if (StringHelper.isEmpty(binding.content)) {
                        showText(R.string.feed_back_hint);
                        return;
                    }

                    UiHelper.setVisibleElseGone(binding.submit, false);
                    UiHelper.setVisibleElseGone(binding.progress, true);

                    ArrayMap<String, String> map = new ArrayMap<>();
                    map.put("content", binding.content.getText().toString());
                    map.put("email", binding.email.getText().toString());

                    addSubscribe(RetrofitHelper.getApis().feedBack(map)
                        .compose(RxHelper.toMain())
                        .subscribe(respBean -> {
                            showText(respBean.getMsg());
                            if (respBean.isCodeSuc()) {
                                dialog.dismiss();
                            }

                            UiHelper.setVisibleElseGone(binding.submit, true);
                            UiHelper.setVisibleElseGone(binding.progress, false);
                        }, throwable -> {
                            Logs.logException(throwable);
                            showError();

                            UiHelper.setVisibleElseGone(binding.submit, true);
                            UiHelper.setVisibleElseGone(binding.progress, false);
                        }));
                    break;
            }
        });
    }

    private void closeDrawer() {
        RxHelper.timerConsumer(250, aLong -> {
            if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * 计时关闭app
     */
    private void showTimeDownDialog() {
        MainTimeDownDialogBinding binding = DataBindingUtil.inflate(mLayoutInflater
            , R.layout.main_time_down_dialog, null, false);

        // update event
        Disposable disposable = RxBus.get().toObserveCode(Constants.Event.update_count_down, event -> {
            binding.countDownNow.setText(event.getContent());
        });
        addSubscribe(disposable);
        // 没有计时任务就隐藏表
        if (StringUtils.isEmpty(TimeCountHelper.mCurCountTimeStr)) {
            binding.countingField.setVisibility(View.GONE);
        }

        // 弹出对话框
        Dialog dialog = new AlertDialog.Builder(mContext)
            .setView(binding.getRoot())
            .show();

        binding.setClick(view -> {
            switch (view.getId()) {
                case R.id.cancel:
                    TimeCountHelper.cancel();
                    mCountDownTextView.setText("");
                    break;
                case R.id.count_10:
                    startCount(10 * 60, ((TextView) view).getText());
                    break;
                case R.id.count_20:
                    startCount(20 * 60, ((TextView) view).getText());
                    break;
                case R.id.count_30:
                    startCount(30 * 60, ((TextView) view).getText());
                    break;
                case R.id.count_45:
                    startCount(45 * 60, ((TextView) view).getText());
                    break;
                case R.id.count_60:
                    startCount(60 * 60, ((TextView) view).getText());
                    break;
            }
            dialog.dismiss();
            disposable.dispose();
        });
    }

    void startCount(int second, CharSequence content) {
        TimeCountHelper.startCountDown(second, () -> System.exit(0));
        showText(String.format(getString(R.string.exit_after_second), content));
    }

    private void updateUserViews() {
        UserBean userInfoBean = UserManager.getUserBean();

        ImageLoader.loadImageCircle(mUserPhotoImage, userInfoBean.getAvatar(), R.drawable.default_portrait);

        mBinding.userName.setText(userInfoBean.getNickName());
        mBinding.userEmail.setText(userInfoBean.getEmail());
        int day = (int) ((System.currentTimeMillis() - PrefHelper.getLong(Constants.Prefs.INIT_TIME_STAMP)) / (1000 * 60 * 60 * 24L));
        mBinding.hasRunning.setText(String.format(getString(R.string.has_run_day), day));
        if (UserManager.isVisitor()) {
            mBinding.userEmail.setText(R.string.no_bind_email);
        }
    }

    long mExitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showText(getString(R.string.exit_again));
            mExitTime = System.currentTimeMillis();
        } else {
            MusicPlayer.getMediaPlayer().reset();
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_field:
                if (UserManager.isVisitor()) {
                    Router.turnTo(mContext, EditUserInfoActivity.class).start();
                } else {
                    Router.turnTo(mContext, UserInfoActivity.class).start();
                }
                break;
        }
    }
}
