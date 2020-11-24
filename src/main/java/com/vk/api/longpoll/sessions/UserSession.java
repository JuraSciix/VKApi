package com.vk.api.longpoll.sessions;

import com.vk.api.longpoll.LongPollSession;
import com.vk.api.objects.ApiResponse;

public class UserSession extends LongPollSession {

    public static UserSession fromResponse(ApiResponse response) {
        return response.loadType(UserSession.class);
    }

    @Override
    public String getServer() {
        return "https://" + super.getServer();
    }
}
