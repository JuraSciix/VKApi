package com.vk.api.longpoll;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class LongPollHandler {

    @SerializedName("updates")
    public JsonArray json;

    @SerializedName("ts")
    public int newTs;

    public JsonArray getJson() {
        return json;
    }

    public void setJson(JsonArray json) {
        this.json = json;
    }

    public int getNewTs() {
        return newTs;
    }

    public void setNewTs(int newTs) {
        this.newTs = newTs;
    }

    public Stream<JsonElement> updates() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(json.iterator(),
                Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongPollHandler)) {
            return false;
        }
        LongPollHandler other = (LongPollHandler) o;

        return Objects.equals(json, other.json) &&
                newTs == other.newTs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(json, newTs);
    }

    @Override
    public String toString() {
        return "LongPollHandler{json=" + json + ", newTs=" + newTs + '}';
    }
}
