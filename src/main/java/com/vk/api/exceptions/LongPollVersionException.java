package com.vk.api.exceptions;

import com.vk.api.longpoll.LongPollListener;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        LongPollVersionException e = (LongPollVersionException) o;
        return minVersion == e.minVersion && maxVersion == e.maxVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minVersion, maxVersion);
    }
}
