package com.example.stockrecommendations.util;

import android.content.Context;
import android.content.res.Resources;

import com.example.stockrecommendations.R;


/**
 * Created by lzy123 on 2019/08/05.
 */
public class ADFilterTool {
    public static String getClearAdDivJs(Context context) {
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDiv);
        for (int i = 0; i < adDivs.length; i++) {
            js += "var adDiv" + i + "= document.getElementById('" + adDivs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
        }
        return js;
    }
}