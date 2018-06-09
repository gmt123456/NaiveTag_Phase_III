package top.minecode.service.util;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/27.
 * Description:
 *
 * @author Liao
 */
public class SimpleCache<V extends CacheItem> {

    private ConcurrentHashMap<String, V> map = new ConcurrentHashMap<>();

    public V get(String key) {
        V v = map.get(key);
        if (v == null)
            return null;

        v.updateTime();
        return v;
    }

    public String add(V v, Function<V, String> keyFunction) {
        Optional<Map.Entry<String, V>> record = map.entrySet()
                .stream().filter(e -> e.getValue().equals(v)).findFirst();

        // If already in the cache, return this key directly
        if (record.isPresent()) {
            return record.get().getKey();
        }

        // Add it to the map and return the key
        String key = keyFunction.apply(v);
        map.put(key, v);
        return key;
    }

    public void remove(String key) {
        map.remove(key);
    }

    public List<CacheItem> refresh() {
        // Scan the token user map and delete users whose time
        // exceed the expireTime
        Map<String, V> newMap = map.entrySet().stream().filter(e -> e.getValue().isActive())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Get items which is expired in this refreshing
        Set<String> keySet = new HashSet<>(map.keySet());
        keySet.removeAll(newMap.keySet());
        List<CacheItem> expiredItems = keySet.stream().map(map::get).collect(Collectors.toList());

        // Update map
        map.clear();
        map.putAll(newMap);

        return expiredItems;
    }

    @Override
    public String toString() {
        return "SimpleCache{" +
                "map=" + map +
                '}';
    }
}
