package com.vk.api.exceptions;

import com.vk.api.longpoll.LongPollListener;

public class LongPollTsException extends LongPollException {

    private final int newTs;

    public LongPollTsException(int newTs) {
        super(LongPollListener.FAILED_TS_DEPRECATED, "the event history is out of date or has been partially lost.");
        this.newTs = newTs;
    }

    @Override
    public int getNewTs() {
        return newTs;
    }
}
