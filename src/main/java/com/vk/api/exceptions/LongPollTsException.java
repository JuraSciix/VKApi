package com.vk.api.exceptions;

import com.vk.api.longpoll.LongPollListener;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        return (this == o) || (super.equals(o) && newTs == ((LongPollTsException) o).newTs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), newTs);
    }
}
