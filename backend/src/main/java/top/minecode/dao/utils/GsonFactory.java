package top.minecode.dao.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class GsonFactory {

    public static Gson create() {
        GsonBuilder builder = new GsonBuilder();
        // TODO: 2018/5/23 Register type adapter here
        return builder.create();
    }
}
