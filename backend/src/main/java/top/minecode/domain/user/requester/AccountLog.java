package top.minecode.domain.user.requester;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class AccountLog {

    private final double change;
    private final String changeType;
    private final double balance;
    private final String time;

    public AccountLog(double change, String changeType, double balance, String time) {
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