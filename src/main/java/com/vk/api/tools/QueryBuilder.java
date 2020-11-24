package com.vk.api.tools;

import com.vk.api.objects.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.StringJoiner;

public class QueryBuilder {

    public static String build(List<RequestParam> requestParams) {
        StringJoiner sj = new StringJoiner("&");

        for (RequestParam requestParam: requestParams) {
            sj.add(encode(requestParam.key) + '=' + encode(requestParam.value));
        }
        return sj.toString();
    }

    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError(e);
        }
    }
}
