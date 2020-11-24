package com.vk.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.exceptions.*;
import com.vk.api.httpclient.HttpTransporter;
import com.vk.api.longpoll.LongPollHandler;
import com.vk.api.longpoll.LongPollListener;
import com.vk.api.longpoll.LongPollSession;
import com.vk.api.objects.ApiError;
import com.vk.api.objects.ApiResponse;
import com.vk.api.objects.VKMethod;

import java.io.IOException;
import java.util.Objects;

public final class VKApi {

    public static final String HOST = "https://api.vk.com/method/";
    public static final Gson GSON = new GsonBuilder().create();

    private final String accessToken;

    private final String version;

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
        if (!method.containsParam("access_token")) {
            method.addParam("access_token", accessToken);
        }
        if (!method.containsParam("v")) {
            method.addParam("v", version);
        }
        String result;

        try {
            result = HttpTransporter.execute(method);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonObject json = (JsonObject) JsonParser.parseString(result);

        if (json.has("error")) {
            throw new ApiException(GSON.fromJson(json.get("error"), ApiError.class));
        }
        return new ApiResponse(json.get("response"));
    }

    public LongPollHandler listen(LongPollListener<?> listener) throws LongPollException {
        if (!listener.hasSession()) {
            throw new IllegalStateException("listener " + listener + " has no session.");
        }
        LongPollSession session = listener.getSession();
        String result;

        try {
            result = HttpTransporter.execute(session);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonObject json = (JsonObject) JsonParser.parseString(result);

        if (json.has("failed")) {
            raiseFailed(json);
        }
        LongPollHandler handler = GSON.fromJson(json, LongPollHandler.class);
        session.setTs(handler.newTs);
        return handler;
    }

    private void raiseFailed(JsonObject data) throws LongPollException {
        switch (data.get("failed").getAsInt()) {
            case LongPollListener.FAILED_TS_DEPRECATED: throw new LongPollTsException(data.get("ts").getAsInt());
            case LongPollListener.FAILED_KEY_EXPIRED: throw new LongPollKeyException();
            case LongPollListener.FAILED_SESSION_LOST: throw new LongPollSessionException();
            case LongPollListener.FAILED_INVALID_VERSION: throw new LongPollVersionException(
                    data.get("min_version").getAsInt(), data.get("max_version").getAsInt());
            default: throw new RuntimeException(data.getAsString());
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
        VKApi a = (VKApi) o;
        return Objects.equals(accessToken, a.accessToken) && Objects.equals(version, a.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, version);
    }
}
