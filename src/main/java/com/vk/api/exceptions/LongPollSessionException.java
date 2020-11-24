package com.vk.api.exceptions;

import com.vk.api.longpoll.LongPollListener;

public class LongPollSessionException extends LongPollException {

    public LongPollSessionException() {
        super(LongPollListener.FAILED_SESSION_LOST, "information has been lost.");
    }
}
