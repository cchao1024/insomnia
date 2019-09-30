package com.cchao.insomnia.view;

import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialog;
import android.util.Pair;
import android.view.LayoutInflater;

import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.MusicItemMenuListBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.GlobalHelper;
import com.cchao.insomnia.manager.MusicPlayer;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.insomnia.model.javabean.fall.FallMusic;
import com.cchao.simplelib.core.UiHelper;
import com.cchao.simplelib.ui.interfaces.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cchao
 * @version 2019-05-23.
 */
public class Dialogs {
    /**
     * 弹出更多的选项
     */
    public static void showMusicItemMenu(LayoutInflater layoutInflater, FallMusic item, BaseView baseView) {
        BottomSheetDialog dialog = new BottomSheetDialog(layoutInflater.getContext());
        MusicItemMenuListBinding binding = DataBindingUtil.inflate(layoutInflater
            , R.layout.music_item_menu_list, null, false);
        binding.name.setText(UiHelper.getString(R.string.music_colon) + item.getName());
        binding.setClicker(click -> {
            switch (click.getId()) {
                case R.id.next_play:
                    MusicPlayer.addToPlayList(item);
                    dialog.dismiss();
                    break;
                case R.id.wish:
                    UserManager.addWish(item.getId(), baseView);
                    dialog.dismiss();
                    break;
                case R.id.download:
                    UiHelper.showToast(R.string.developing);
                    dialog.dismiss();
                    break;
                case R.id.share:
                    GlobalHelper.shareMusic(layoutInflater.getContext(), item.getSrc());
                    dialog.dismiss();
                    break;
            }
        });

        dialog.setContentView(binding.getRoot());
        dialog.show();
    }

    public static void showDebug(LayoutInflater layoutInflater) {
        List<Pair<String, Runnable>> options = new ArrayList<>();
        options.add(Pair.create("切换账号【八级大狂风】", () -> UserManager.setToken(Constants.Token.daodao)));
        options.add(Pair.create("切换账号【暗夜枫佑翼】", () -> UserManager.setToken(Constants.Token.anye)));
        options.add(Pair.create("切换账号【东山一哥】", () -> UserManager.setToken(Constants.Token.dongshan)));
        options.add(Pair.create("切换账号【360硬盘】", () -> UserManager.setToken(Constants.Token.y360)));

        String[] arr = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            arr[i] = options.get(i).first;
        }

        UiHelper.showItemsDialog(layoutInflater.getContext(), "开发者选项", arr, (dialog, which) -> {
            if (arr[which].startsWith("切换账号")) {
                UiHelper.showToast("请重启应用");
            }
            options.get(which).second.run();
        });
    }
}
