package com.vk.api.objects;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.Objects;

import static com.vk.api.VKApi.GSON;

public class ApiResponse {

    @SerializedName("response")
    public JsonElement response;

    @SerializedName("execute_errors")
    public JsonElement executeErrors;

    public JsonElement getResponse() {
        return response;
    }

    public void setResponse(JsonElement response) {
        this.response = response;
    }

    public JsonElement getExecuteErrors() {
        return executeErrors;
    }

    public void setExecuteErrors(JsonElement executeErrors) {
        this.executeErrors = executeErrors;
    }

    public <T> T loadResponse(Class<T> typeClass) {
        return loadResponse((Type) typeClass);
    }

    public <T> T loadResponse(Type type) {
        return GSON.fromJson(response, type);
    }

    public <T> T loadExecuteErrors(Class<T> typeClass) {
        return loadResponse((Type) typeClass);
    }

    public <T> T loadExecuteErrors(Type type) {
        return GSON.fromJson(executeErrors, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiResponse)) {
            return false;
        }
        ApiResponse other = (ApiResponse) o;

        return Objects.equals(response, other.response) &&
                Objects.equals(executeErrors, other.executeErrors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, executeErrors);
    }

    @Override
    public String toString() {
        return "ApiResponse{response=" + response + ", executeErrors=" + executeErrors + '}';
    }
}
