package org.example;

import com.vk.api.VKApi;

public class Main {

    private static final String ACCESS_TOKEN = "";
    private static final String API_VERSION = "5.700";

    public static void main(String[] args) {
        System.out.println("Запуск...");
        Bot bot = new Bot(new VKApi(ACCESS_TOKEN, API_VERSION));
        bot.connect();
    }

    public static void printStackTrace(Throwable th) {
        th.printStackTrace();
        System.exit(1);
    }
}
