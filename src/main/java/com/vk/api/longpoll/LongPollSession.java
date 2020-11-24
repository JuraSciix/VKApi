package com.vk.api.longpoll;

import com.vk.api.httpclient.HttpBuildable;

import java.util.Objects;

public abstract class LongPollSession implements HttpBuildable {

    public static final int DEFAULT_WAIT = 25;

    public static final LongPollMode DEFAULT_MODE = LongPollMode.GET_ATTACHMENTS;

    public static final int DEFAULT_VERSION = 10;

    private static final String URL = "%s?act=a_check&key=%s&ts=%d&wait=%d&mode=%d&version=%d";

    public String server;

    public String key;

    public int ts;

    public int wait = DEFAULT_WAIT;

    public LongPollMode mode = DEFAULT_MODE;

    public int version = DEFAULT_VERSION;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public LongPollMode getMode() {
        return mode;
    }

    public void setMode(LongPollMode mode) {
        this.mode = mode;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toUrl() {
        return String.format(URL, getServer(), getKey(), getTs(), getWait(), getMode().value, getVersion());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongPollSession)) {
            return false;
        }
        LongPollSession a = (LongPollSession) o;
        return Objects.equals(server, a.server) &&
                Objects.equals(key, a.key) &&
                ts == a.ts &&
                wait == a.wait &&
                mode == a.mode &&
                version == a.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, key, ts, wait, mode, version);
    }
}
