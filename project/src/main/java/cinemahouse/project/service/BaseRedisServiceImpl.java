package cinemahouse.project.service;

import cinemahouse.project.service.interfaces.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BaseRedisServiceImpl<K, F, V> implements BaseRedisService<K, F, V> {
    private final RedisTemplate<K, V> redisTemplate; // Dùng để thao tác với dữ liệu dạng key-value trong Redis
    private final HashOperations<K, F, V> hashOperations; // Dùng để thao tác với dữ liệu dạng hash trong Redis



    @Override
    public void set(K key, V value) {
        // Lưu một giá trị vào Redis với key xác định
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setTimeToLive(K key, int timeoutInDays) {
        // Thiết lập thời gian sống (TTL) cho key, dữ liệu sẽ tự động bị xóa sau khoảng thời gian chỉ định
        redisTemplate.expire(key, timeoutInDays, TimeUnit.DAYS);

    }
    @Override
    public void setTimeToLive(K key, V value, int timeoutInDays) {
        // Lưu một giá trị vào Redis với key xác định và đồng thời thiết lập TTL
        redisTemplate.opsForValue().set(key,value, timeoutInDays, TimeUnit.MINUTES);

    }

    @Override
    public void hashSet(K key, F field, V value) {
        // Lưu giá trị vào một field cụ thể trong hash với key xác định
        hashOperations.put(key, field, value);
    }

    @Override
    public boolean hashExists(K key, F field) {
        // Kiểm tra xem field cụ thể có tồn tại trong hash hay không
        return hashOperations.hasKey(key, field);
    }

    @Override
    public Object get(K key) {
        // Lấy giá trị của key từ Redis
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Map<F, V> getField(K key) {
        // Lấy toàn bộ field và giá trị từ một hash với key xác định
        return hashOperations.entries(key);
    }

    @Override
    public Object hashGet(K key, F field) {
        // Lấy giá trị của một field cụ thể trong hash
        return hashOperations.get(key, field);
    }

    @Override
    public List<Object> hashGetByFieldPrefix(K key, F fieldPrefix) {
        // Lấy danh sách các giá trị của những field có prefix nhất định trong hash
        List<Object> objects = new ArrayList<>();

        Map<F, V> hashEntries = hashOperations.entries(key);

        for (Map.Entry<F, V> entry : hashEntries.entrySet()) {
            if (entry.getKey().toString().startsWith(fieldPrefix.toString())) {
                objects.add(entry.getValue());
            }
        }
        return objects;
    }

    @Override
    public Set<F> getFieldPrefixes(K key) {
        // Lấy danh sách tất cả các field trong hash
        return hashOperations.entries(key).keySet();
    }

    @Override
    public void delete(K key) {
        // Xóa toàn bộ dữ liệu liên quan đến key từ Redis
        redisTemplate.delete(key);
    }

    @Override
    public void delete(K key, F field) {
        // Xóa một field cụ thể từ hash với key xác định
        hashOperations.delete(key, field);
    }

    @Override
    public void delete(K key, List<F> fields) {
        // Xóa nhiều field từ hash với key xác định
        for (F field : fields) {
            hashOperations.delete(key, field);
        }
    }
}
