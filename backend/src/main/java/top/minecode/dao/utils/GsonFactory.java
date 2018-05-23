package top.minecode.dao.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.minecode.domain.user.requester.AccountLog;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class GsonFactory {

    public static Gson create() {
        GsonBuilder builder = new GsonBuilder();
        // TODO: 2018/5/23 Register type adapter here
        builder.registerTypeAdapter(AccountLog.class, new AccountLog.AccountLogSerializer());
        return builder.create();
    }
}
