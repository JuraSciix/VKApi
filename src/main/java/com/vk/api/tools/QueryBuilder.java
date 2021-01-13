package com.vk.api.tools;

import com.vk.api.objects.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.StringJoiner;

public final class QueryBuilder {

    public static String build(List<RequestParam> params) {
        StringJoiner result = new StringJoiner("&");

        for (RequestParam param: params) {
            result.add(encode(param.key) + '=' + encode(param.value));
        }
        return result.toString();
    }

    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError(e);
        }
    }
}
