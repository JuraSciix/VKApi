package com.vk.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.exceptions.*;
import com.vk.api.longpoll.LongPollHandler;
import com.vk.api.longpoll.LongPollListener;
import com.vk.api.longpoll.LongPollSession;
import com.vk.api.objects.ApiError;
import com.vk.api.objects.ApiResponse;
import com.vk.api.objects.VKMethod;

import java.util.Objects;

import static com.vk.api.httpclient.HttpExecutor.execute;

public final class VKApi {

    public static final String HOST = "https://api.vk.com/method/";
    public static final Gson GSON = new GsonBuilder().create();

    public final String accessToken;

    public final String version;

    public VKApi(String accessToken, double version) {
        this(accessToken, Double.toString(version));
    }

    public VKApi(String accessToken, String version) {
        this.accessToken = accessToken;
        this.version = version;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getVersion() {
        return version;
    }

    public ApiResponse call(VKMethod method) throws ApiException {
        checkMethod(method);
        JsonObject json = JsonParser.parseString(execute(method)).getAsJsonObject();

        if (json.has("error")) {
            apiError(json.getAsJsonObject("error"));
        }
        return GSON.fromJson(json, ApiResponse.class);
    }

    private void checkMethod(VKMethod method) {
        if (method == null) {
            throw new NullPointerException();
        }
        if (!method.containsParam("access_token")) {
            method.setParam("access_token", accessToken);
        }
        if (!method.containsParam("v")) {
            method.setParam("v", version);
        }
    }

    private void apiError(JsonObject json) throws ApiException {
        throw new ApiException(GSON.fromJson(json, ApiError.class));
    }

    public LongPollHandler listen(LongPollListener<?> listener) throws LongPollException {
        checkListener(listener);
        LongPollSession session = listener.getSession();
        JsonObject json = JsonParser.parseString(execute(session)).getAsJsonObject();

        if (json.has("failed")) {
            throwFailed(json);
        }
        LongPollHandler handler = GSON.fromJson(json, LongPollHandler.class);
        session.setTs(handler.newTs);
        return handler;
    }

    private void checkListener(LongPollListener<?> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        if (!listener.hasSession()) {
            throw new IllegalStateException("listener must have a session");
        }
    }

    private void throwFailed(JsonObject data) throws LongPollException {
        switch (data.get("failed").getAsInt()) {
            case LongPollListener.FAILED_TS_DEPRECATED: throw new LongPollTsException(data.get("ts").getAsInt());
            case LongPollListener.FAILED_KEY_EXPIRED: throw new LongPollKeyException();
            case LongPollListener.FAILED_SESSION_LOST: throw new LongPollSessionException();
            case LongPollListener.FAILED_INVALID_VERSION: throw new LongPollVersionException(
                    data.get("min_version").getAsInt(), data.get("max_version").getAsInt());
            default: throw new IllegalArgumentException(data.getAsString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VKApi)) {
            return false;
        }
        VKApi other = (VKApi) o;

        return Objects.equals(accessToken, other.accessToken) &&
                Objects.equals(version, other.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, version);
    }

    @Override
    public String toString() {
        return "VKApi{accessToken='" + accessToken + "', version='" + version + "'}";
    }
}
