package com.vk.api.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.Objects;

import static com.vk.api.VKApi.GSON;

public class ApiResponse {

    public JsonElement json;

    public ApiResponse(JsonElement json) {
        this.json = json;
    }

    public JsonElement getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    public <T> T loadType(Class<T> tClass) {
        return loadType((Type) tClass);
    }

    public <T> T loadType(Type type) {
        return GSON.fromJson(json, type);
    }

    @Override
    public boolean equals(Object o) {
        return (this == o) || (o instanceof ApiResponse && Objects.equals(json, ((ApiResponse) o).json));
    }

    @Override
    public int hashCode() {
        return Objects.hash(json);
    }
}
