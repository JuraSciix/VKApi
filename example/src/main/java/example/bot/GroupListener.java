package example.bot;

import com.vk.api.VKApi;
import com.vk.api.exceptions.ApiException;
import com.vk.api.exceptions.LongPollException;
import com.vk.api.longpoll.LongPollListener;
import com.vk.api.longpoll.sessions.GroupSession;
import com.vk.api.objects.ApiResponse;
import com.vk.api.objects.VKMethod;

public class GroupListener extends LongPollListener<GroupSession> {

    private final VKApi api;

    public GroupListener(VKApi api) {
        this.api = api;
    }

    public void reconnect(LongPollException e) throws ApiException {
        switch (e.getFailed()) {
            case FAILED_TS_DEPRECATED:
                getSession().setTs(e.getNewTs());
                break;
            case FAILED_KEY_EXPIRED:
            case FAILED_SESSION_LOST:
                connect();
                break;
            default:
                setSession(null); // disconnect
                e.printStackTrace();
        }
    }

    @Override
    public void connect() throws ApiException {
        setSession(GroupSession.fromResponse(connect0()));
    }

    private ApiResponse connect0() throws ApiException {
        return api.call(new VKMethod("groups.getLongPollServer").setParam("group_id", getId()));
    }

    private int getId() throws ApiException {
        return api.call(new VKMethod("groups.getById")).response
                .getAsJsonArray().get(0)
                .getAsJsonObject().get("id").getAsInt();
    }
}
