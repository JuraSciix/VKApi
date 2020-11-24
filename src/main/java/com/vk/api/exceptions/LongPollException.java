package com.vk.api.exceptions;

import java.util.Objects;

public abstract class LongPollException extends Exception {

    private final int failed;

    private final String source;

    protected LongPollException(int failed, String source) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongPollException)) {
            return false;
        }
        LongPollException a = (LongPollException) o;
        return failed == a.failed && Objects.equals(source, a.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(failed, source);
    }
}
