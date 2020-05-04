package cn.jk.hscardfactory.utils;

import com.google.android.gms.ads.MobileAds;

import cn.jk.hscardfactory.main.MainActivity;



/**
 * Created by Administrator on 2017/7/28.
 */

public class AdMobUtil {

    public static final String myAppId = "ca-app-pub-9436584014969947~4595575897";

    public static final String myEleId = "ca-app-pub-9436584014969947/2966621846";


    public static void initAd(final MainActivity context) {
        MobileAds.initialize(context, myAppId);
    }
}
