package me.cchao.insomnia.bean.resp.app;

import lombok.Data;
import lombok.experimental.Accessors;
import me.cchao.insomnia.bean.resp.user.UpdateUser;

/**
 * app启动初始化参数
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
