package util;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;

/**
 * Created by 胡海波 on 2016/9/24.
 */
public class YourApplication extends Application {
    public void onCreate() {
        super.onCreate();
        // ... your codes
        Unicorn.init(this, "appKey", options(), new UnicornImageLoader() {
            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {

            }
        }); // appKey 可以在七鱼管理系统-\>设置-\>APP接入 页面找到
        // ... your codes
    }

    // 如果返回值为null，则全部使用默认参数。
    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();

        return options;
    }

}