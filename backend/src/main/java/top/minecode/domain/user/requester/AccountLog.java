package top.minecode.domain.user.requester;

import com.google.gson.*;
import top.minecode.po.log.RequesterAccountLogPO;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

    public AccountLog(RequesterAccountLogPO logPO) {
        change = logPO.getDollars();
        changeType = logPO.getType().toString();
        balance = logPO.getBalance();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time = format.format(logPO.getTime());
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
