package org.example;

import com.vk.api.VKApi;
import com.vk.api.exceptions.ApiException;
import com.vk.api.exceptions.LongPollException;
import com.vk.api.longpoll.LongPollListener;
import com.vk.api.longpoll.sessions.GroupSession;
import com.vk.api.objects.VKMethod;

public class Listener extends LongPollListener<GroupSession> {

    private final VKApi api;

    private int groupID;

    public Listener(VKApi api) {
        this.api = api;
    }

    public void reconnect(LongPollException e) throws ApiException {
        switch (e.getFailed()) {
            case FAILED_TS_DEPRECATED: getSession().setTs(e.getNewTs()); break;
            case FAILED_KEY_EXPIRED: case FAILED_SESSION_LOST: connect(); break;
            default: Main.printStackTrace(e);
        }
    }

    @Override
    public void connect() throws ApiException {
        setSession(GroupSession.fromResponse(api.call(new VKMethod("groups.getLongPollServer")
                .addParam("group_id", getGroupID()))));
    }

    private int getGroupID() {
        if (groupID == 0) {
            try {
                groupID = api.call(new VKMethod("groups.getById")).json
                        .getAsJsonArray().get(0)
                        .getAsJsonObject().get("id").getAsInt();
            } catch (ApiException e) {
                Main.printStackTrace(e);
            }
        }
        return groupID;
    }
}
