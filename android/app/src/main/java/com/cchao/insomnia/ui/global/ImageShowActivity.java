package com.cchao.insomnia.ui.global;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.ImageView;

import com.cchao.insomnia.R;
import com.cchao.insomnia.databinding.ImageShowActivityBinding;
import com.cchao.insomnia.global.Constants;
import com.cchao.insomnia.manager.GlobalHelper;
import com.cchao.insomnia.manager.UserManager;
import com.cchao.insomnia.util.ImageHelper;
import com.cchao.simplelib.core.GlideApp;
import com.cchao.simplelib.core.Logs;
import com.cchao.simplelib.core.RxHelper;
import com.cchao.simplelib.ui.activity.BaseTitleBarActivity;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author cchao
 * @version 8/12/18.
 */
public class ImageShowActivity extends BaseTitleBarActivity<ImageShowActivityBinding> {

    PhotoView mPhotoView;
    String mImageUrl;
    long id;

    @Override
    protected int getLayout() {
        return R.layout.image_show_activity;
    }

    @Override
    protected void initEventAndData() {
        setTitleText(getString(R.string.display_image));

        id = getIntent().getLongExtra(Constants.Extra.ID, 0);
        mImageUrl = getIntent().getStringExtra(Constants.Extra.IMAGE_URL);

        mPhotoView = mDataBind.photoView;
        onLoadData();

        if (id != 0) {
            // 加入收藏
            addTitleMenuItem(R.drawable.wish_button, view -> {
                UserManager.addWish(id, this);
            });
        }

        // share
        addTitleMenuItem(R.drawable.share, view -> {
            GlobalHelper.shareImage(mContext, mImageUrl);
        });
    }

    @Override
    protected void onLoadData() {
        GlideApp.with(mContext)
            .load(mImageUrl)
            .centerInside()
            .into(mPhotoView);
    }

    /**
     * 保存图片
     */
    private void saveImage(ImageView imageView) {
        if (imageView == null || imageView.getDrawable() == null) {
            return;
        }
        try {
            String rootPath = getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator;
            String savePath = rootPath + System.currentTimeMillis() + ".gif";

            // 开线程保存图片
            addSubscribe(Observable.create((ObservableOnSubscribe<Boolean>) e -> {
                boolean result = false;
                try {
                    result = ImageHelper.save(((BitmapDrawable) imageView.getDrawable()).getBitmap()
                        , new File(savePath), Bitmap.CompressFormat.JPEG, false);
                } catch (Exception ex) {
                    e.onError(ex);
                }
                e.onNext(result);
                e.onComplete();
            }).compose(RxHelper.toMain())
                .subscribe(s -> {
                    if (s) {
                        showText(getString(R.string.save_to) + rootPath);
                    }
                }, RxHelper.getErrorTextConsumer(this)));
        } catch (Exception exception) {
            Logs.logException(exception);
        }
    }
}
