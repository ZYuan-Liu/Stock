package com.example.stockrecommendations.util;

import com.example.stockrecommendations.entity.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lzy123 on 2018/7/24.
 */

public class EventBusUtil {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }

}
