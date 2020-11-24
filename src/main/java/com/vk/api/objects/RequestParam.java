package com.vk.api.objects;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RequestParam {

    @SerializedName("key")
    public String key;

    @SerializedName("value")
    public String value;

    public RequestParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestParam)) {
            return false;
        }
        RequestParam a = (RequestParam) o;
        return Objects.equals(key, a.key) && Objects.equals(value, a.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
