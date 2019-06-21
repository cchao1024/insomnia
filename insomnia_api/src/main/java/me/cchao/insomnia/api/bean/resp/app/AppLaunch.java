package me.cchao.insomnia.api.bean.resp.app;

import me.cchao.insomnia.api.bean.resp.user.UpdateUser;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * app启动初始化参数
 *
 * @author : cchao
 * @version 2019-04-13
 */
@Data
@Accessors(chain = true)
public class AppLaunch {
    int lastAndroidVersion;
    String versionUpdateMsg;
    UpdateUser userInfo;

}
