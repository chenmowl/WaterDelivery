package com.eme.waterdelivery.model.net;


import com.eme.waterdelivery.App;

import java.io.File;

/**
 * net
 *
 * Created by dijiaoliang on 17/3/2.
 */
public final class ApiConfig {

    //主机名
    public static final String API_HOST="http://news-at.zhihu.com";//知乎
    public static final String WATER_HOST="http://192.168.50.247:8080";//知乎

    //端口
    public static final String PORT="";

    //正式发布时要做修改
    public static final boolean DEBUG = Boolean.parseBoolean("true");

    public static final String PATH_DATA = App.getAppInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

}
