package com.vk.api.longpoll.sessions;

import com.vk.api.longpoll.LongPollSession;
import com.vk.api.objects.ApiResponse;

public class GroupSession extends LongPollSession {

    public static GroupSession fromResponse(ApiResponse response) {
        return response.loadResponse(GroupSession.class);
    }
}
