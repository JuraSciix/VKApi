package com.vk.api.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class HttpTransporter {

    public static String execute(HttpBuildable b) throws IOException {
        return stream(new URL(b.toUrl()).openConnection().getInputStream());
    }

    private static String stream(InputStream stream) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
