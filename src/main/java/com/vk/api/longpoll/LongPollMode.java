package com.vk.api.longpoll;

public enum LongPollMode {

    GET_ATTACHMENTS(2),
    RET_EXTENDED_EVENTS(8),
    RET_PTS(32),
    RET_EXTRA_FIELDS(64),
    RET_RANDOM_ID(128);

    public final int value;

    LongPollMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
