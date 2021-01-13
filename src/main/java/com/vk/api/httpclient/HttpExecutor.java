package com.vk.api.httpclient;

import com.vk.api.exceptions.HttpExecutorException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

public final class HttpExecutor {

    public static String execute(HttpExecutable executable) {
        try {
            return execute(executable.toExecutableURL());
        } catch (IOException e) {
            throw new HttpExecutorException("I/O exception", e);
        }
    }

    private static String execute(String str) throws IOException {
        return readStream(new URL(str).openStream());
    }

    private static String readStream(InputStream stream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            StringWriter writer = new StringWriter();
            int c;

            while ((c = reader.read()) >= 0) {
                writer.write(c);
            }
            return writer.toString();
        }
    }
}
