package example.bot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.VKApi;
import com.vk.api.exceptions.ApiException;
import com.vk.api.exceptions.LongPollException;
import com.vk.api.objects.VKMethod;

public class Bot {

    private final VKApi api;

    public Bot(VKApi api) {
        this.api = api;

        try {
            start(new GroupListener(api));
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println("Бот выключен.");
    }

    private void start(GroupListener listener) throws ApiException {
        System.out.println("Подключение к серверу...");
        listener.connect();
        System.out.println("Бот запущен!");

        while (listener.hasSession()) {
            try {
                api.listen(listener).updates()
                        .map(JsonElement::getAsJsonObject)
                        .filter(json -> json.get("type").getAsString().equals("message_new"))
                        .map(json -> json.getAsJsonObject("object").getAsJsonObject("message"))
                        .forEach(this::update);
            } catch (LongPollException e) {
                listener.reconnect(e);
            }
        }
    }

    private void update(JsonObject update) {
        int peerId = update.get("peer_id").getAsInt();
        String[] texts = update.get("text").getAsString().split("\n|<br>");

        for (int i = 0; i < Math.min(texts.length, 10); i++) {
            texts[i] = texts[i].trim();

            if (texts[i].equalsIgnoreCase("!test")) {
                long t = System.currentTimeMillis();
                int offset = getTime() - update.get("date").getAsInt();
                double r = (System.currentTimeMillis() - t) / 1000d;

                send(peerId, "Offset: " + offset + ". Time: " + r + " s.");
            }
        }
    }

    private int getTime() {
        try {
            return api.call(new VKMethod("utils.getServerTime")).response.getAsInt();
        } catch (ApiException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void send(int peerId, String message) {
        try {
            api.call(new VKMethod("messages.send")
                    .setParam("random_id", 0)
                    .setParam("peer_id", peerId)
                    .setParam("message", message));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
