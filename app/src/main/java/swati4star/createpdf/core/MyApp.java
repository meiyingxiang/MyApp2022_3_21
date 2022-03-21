package swati4star.createpdf.core;


import androidx.multidex.MultiDexApplication;

import cn.leancloud.LeanCloud;


public class MyApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 提供 this、App ID 和 App Key 作为参数
        LeanCloud.initialize(this, "5wV0R5NUY7qKRIgY1rCAvcl2-MdYXbMMI",
                "XDHfEChKmxsi0nIvFjfF9XiC");
    }
}
