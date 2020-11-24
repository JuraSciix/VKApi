package com.vk.api.longpoll;

import com.vk.api.exceptions.ApiException;

public abstract class LongPollListener<T extends LongPollSession> {

    public static final int FAILED_TS_DEPRECATED = 1;
    public static final int FAILED_KEY_EXPIRED = 2;
    public static final int FAILED_SESSION_LOST = 3;
    public static final int FAILED_INVALID_VERSION = 4;

    private T session;

    public final void setSession(T session) {
        this.session = session;
    }

    public final T getSession() {
        return session;
    }

    public final boolean hasSession() {
        return session != null;
    }

    public abstract void connect() throws ApiException;
}
