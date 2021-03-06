package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.xiaoxing.welcome.mvp.ui.SplashActivity;
import com.xiaoxing.welcome.mvp.ui.WelcomeActivity;
import com.xiaoxing.welcome.mvp.ui.WelcomeGuideActivity;
import java.lang.Override;
import java.lang.String;
import java.util.Map;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER. */
public class ARouter$$Group$$welcome implements IRouteGroup {
  @Override
  public void loadInto(Map<String, RouteMeta> atlas) {
    atlas.put("/welcome/SplashActivity", RouteMeta.build(RouteType.ACTIVITY, SplashActivity.class, "/welcome/splashactivity", "welcome", null, -1, -2147483648));
    atlas.put("/welcome/WelcomeActivity", RouteMeta.build(RouteType.ACTIVITY, WelcomeActivity.class, "/welcome/welcomeactivity", "welcome", null, -1, -2147483648));
    atlas.put("/welcome/WelcomeGuideActivity", RouteMeta.build(RouteType.ACTIVITY, WelcomeGuideActivity.class, "/welcome/welcomeguideactivity", "welcome", null, -1, -2147483648));
  }
}
