package example.bot;

import com.vk.api.VKApi;

public class Main {

    private static final String ACCESS_TOKEN = "";
    private static final String API_VERSION = "5.900";

    public static void main(String[] args) {
        System.out.println("Запуск...");
        new Bot(new VKApi(ACCESS_TOKEN, API_VERSION));
    }
}
