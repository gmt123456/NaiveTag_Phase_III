package top.minecode.domain.user.requester;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class AccountLog {

    private final String change;
    private final String changeType;
    private final String balance;
    private final String time;

    public AccountLog(String change, String changeType, String balance, String time) {
        this.change = change;
        this.changeType = changeType;
        this.balance = balance;
        this.time = time;
    }

    public static class AccountLogSerializer implements JsonSerializer<AccountLog> {

        @Override
        public JsonElement serialize(AccountLog accountLog, Type type,
                                     JsonSerializationContext jsonSerializationContext) {
            JsonArray array = new JsonArray(4);
            array.add(accountLog.change);
            array.add(accountLog.changeType);
            array.add(accountLog.balance);
            array.add(accountLog.time);
            return array;
        }
    }
}
