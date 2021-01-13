package com.vk.api.objects;

import com.vk.api.VKApi;
import com.vk.api.httpclient.HttpExecutable;
import com.vk.api.tools.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VKMethod implements HttpExecutable {

    private static class Formatter {

        public static String format(Object o) {
            if (o == null) {
                return "";
            }
            if (o.getClass() == Boolean.class) {
                return (boolean) o ? "1" : "0";
            }
            return String.valueOf(o);
        }
    }

    public final String name;

    public final List<RequestParam> params = new ArrayList<>();

    public VKMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<RequestParam> getParams() {
        return params;
    }

    public VKMethod setParam(String name, Object value) {
        RequestParam param;

        if ((param = getParam(name)) != null) {
            param.value = Formatter.format(value);
        } else {
            addParam(name, value);
        }
        return this;
    }

    public VKMethod addParam(String name, Object value) {
        params.add(new RequestParam(name, Formatter.format(value)));
        return this;
    }

    public boolean containsParam(String name) {
        return getParam(name) != null;
    }

    private RequestParam getParam(String name) {
        for (RequestParam param: params) {
            if (Objects.equals(param.key, name)) {
                return param;
            }
        }
        return null;
    }

    @Override
    public String toExecutableURL() {
        return VKApi.HOST + name + '?' + QueryBuilder.build(params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VKMethod)) {
            return false;
        }
        VKMethod other = (VKMethod) o;

        return Objects.equals(name, other.name) &&
                Objects.equals(params, other.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, params);
    }

    @Override
    public String toString() {
        return "VKMethod{name='" + name + "', params=" + params + '}';
    }
}
