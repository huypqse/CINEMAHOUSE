package cinemahouse.project.service.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseRedisService<K, F, V> {
    //đưa tất cả redis trạng thái hay dùng
    void set(K key, V value);

    void setTimeToLive(K key, int timeoutInDays);//set thoi gian du lieu ton tai trong redis

    void setTimeToLive(K key, V value, int timeoutInDays);//set thoi gian du lieu ton tai trong redis

    void hashSet(K key, F field, V value);//luu vao trong set

    boolean hashExists(K key, F field);//kiem tra trong redis co ton tai key va field trong redis k

    Object get(K key);

    public Map<F, V> getField(K key);

    Object hashGet(K key, F field);

    List<Object> hashGetByFieldPrefix(K key, F fieldPrefix);

    public Set<F> getFieldPrefixes(K key);

    void delete(K key);

    void delete(K key, F field);

    void delete(K key, List<F> fields);



}
