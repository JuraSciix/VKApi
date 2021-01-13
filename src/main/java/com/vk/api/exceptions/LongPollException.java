package com.vk.api.exceptions;

public class LongPollException extends Exception {

    public final int failed;

    public final String source;

    public LongPollException(int failed, String source) {
        this.failed = failed;
        this.source = source;
    }

    public int getFailed() {
        return failed;
    }

    public String getSource() {
        return source;
    }

    public int getNewTs() {
        throw new UnsupportedOperationException();
    }

    public int getMinVersion() {
        throw new UnsupportedOperationException();
    }

    public int getMaxVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getMessage() {
        return source + " (" + failed + ')';
    }
}
