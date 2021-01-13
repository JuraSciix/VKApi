package com.vk.api.longpoll;

import com.google.gson.annotations.SerializedName;
import com.vk.api.httpclient.HttpExecutable;

import java.util.Objects;

public abstract class LongPollSession implements HttpExecutable {

    public static final int DEFAULT_WAIT = 25;

    public static final LongPollMode DEFAULT_MODE = LongPollMode.GET_ATTACHMENTS;

    public static final int DEFAULT_VERSION = 10;

    private static final String URL = "%s?act=a_check&key=%s&ts=%d&wait=%d&mode=%d&version=%d";

    @SerializedName("server")
    public String server;

    @SerializedName("key")
    public String key;

    @SerializedName("ts")
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
    public String toExecutableURL() {
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
        LongPollSession other = (LongPollSession) o;

        return Objects.equals(server, other.server) &&
                Objects.equals(key, other.key) &&
                ts == other.ts &&
                wait == other.wait &&
                mode == other.mode &&
                version == other.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, key, ts, wait, mode, version);
    }

    @Override
    public String toString() {
        return "LongPollSession{server='" + server +
                "', key='" + key +
                "', ts=" + ts +
                ", wait=" + wait +
                ", mode=" + mode +
                ", version=" + version + '}';
    }
}
