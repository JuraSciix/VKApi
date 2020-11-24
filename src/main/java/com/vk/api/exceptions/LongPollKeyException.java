package com.vk.api.exceptions;

import com.vk.api.longpoll.LongPollListener;

public class LongPollKeyException extends LongPollException {

    public LongPollKeyException() {
        super(LongPollListener.FAILED_KEY_EXPIRED, "the key has expired.");
    }
}
