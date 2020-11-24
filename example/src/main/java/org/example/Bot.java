package org.example;

import com.google.gson.JsonObject;
import com.vk.api.VKApi;
import com.vk.api.exceptions.ApiException;
import com.vk.api.exceptions.LongPollException;
import com.vk.api.objects.VKMethod;

public class Bot {

    private final VKApi api;

    public Bot(VKApi api) {
        this.api = api;
    }

    public void connect() {
        Listener listener = new Listener(api);

        System.out.println("Подключение к серверу...");
        try {
            listener.connect();
        } catch (ApiException e) {
            Main.printStackTrace(e);
        }
        System.out.println("Бот запущен.");

        while (listener.hasSession()) {
            try {
                api.listen(listener).updates()
                        .map(e -> (JsonObject) e)
                        .filter(e -> e.get("type").getAsString().equals("message_new"))
                        .map(e -> e.getAsJsonObject("object").getAsJsonObject("message"))
                        .forEach(e -> new Thread(() -> update(e)).start());
            } catch (LongPollException e) {
                try {
                    listener.reconnect(e);
                } catch (ApiException ex) {
                    Main.printStackTrace(ex);
                }
            }
        }
        System.out.println("Бот выключен.");
    }

    private void update(JsonObject message) {
        int peerID = message.get("peer_id").getAsInt();
        String text = message.get("text").getAsString();

        if (text.equalsIgnoreCase("!test")) {
            int id;
            long time = System.currentTimeMillis();
            id = ok(peerID);
            time = System.currentTimeMillis() - time;
            if (id == 0) {
                time(peerID, message.get("conversation_message_id").getAsInt(), time, true);
            } else {
                time(peerID, id, time, false);
            }
        }
    }

    private int ok(int peerID) {
        try {
            return api.call(new VKMethod("messages.send")
                    .addParam("peer_id", peerID)
                    .addParam("random_id", 0)
                    .addParam("message", "ok")).json.getAsInt();
        } catch (ApiException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void time(int peerID, int id, long time, boolean conv) {
        try {
            api.call(new VKMethod("messages.edit")
                    .addParam("peer_id", peerID)
                    .addParam((conv ? "conversation_" : "") + "message_id", id)
                    .addParam("message", (time / 1000d) + "s."));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
