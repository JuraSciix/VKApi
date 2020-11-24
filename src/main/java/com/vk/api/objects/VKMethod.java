package com.vk.api.objects;

import com.vk.api.VKApi;
import com.vk.api.httpclient.HttpBuildable;
import com.vk.api.tools.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class VKMethod implements HttpBuildable {

    public final String name;

    private final List<RequestParam> requestParams = new ArrayList<>();

    public VKMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public VKMethod setParam(String param, Object... value) {
        RequestParam requestParam;

        if ((requestParam = findParam(param)) != null) {
            requestParam.value = format(value);
            return this;
        }
        return addParam(param, value);
    }

    public VKMethod addParam(String param, Object... value) {
        requestParams.add(new RequestParam(param, format(value)));
        return this;
    }

    public RequestParam getParamOrNull(String param) {
        return findParam(param);
    }

    public boolean containsParam(String param) {
        return findParam(param) != null;
    }

    @Override
    public String toUrl() {
        return VKApi.HOST + name + '?' + QueryBuilder.build(requestParams);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VKMethod)) {
            return false;
        }
        VKMethod a = (VKMethod) o;
        return Objects.equals(name, a.name) &&
                Objects.equals(requestParams, a.requestParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, requestParams);
    }

    private RequestParam findParam(String param) {
        for (RequestParam requestParam: requestParams) {
            if (Objects.equals(requestParam.key, param)) {
                return requestParam;
            }
        }
        return null;
    }

    private String format(Object[] value) {
        StringJoiner sj = new StringJoiner(",");

        for (Object v: value) {
            if (v instanceof Boolean) {
                sj.add((Boolean) v ? "1" : "0");
            } else {
                sj.add(String.valueOf(v));
            }
        }
        return sj.toString();
    }
}
