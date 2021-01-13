package com.vk.api.exceptions;

import com.vk.api.longpoll.LongPollListener;

public class LongPollVersionException extends LongPollException {

    private final int minVersion;

    private final int maxVersion;

    public LongPollVersionException(int minVersion, int maxVersion) {
        super(LongPollListener.FAILED_INVALID_VERSION, "invalid version passed in 'version' parameter.");
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
    }

    @Override
    public int getMinVersion() {
        return minVersion;
    }

    @Override
    public int getMaxVersion() {
        return maxVersion;
    }
}
