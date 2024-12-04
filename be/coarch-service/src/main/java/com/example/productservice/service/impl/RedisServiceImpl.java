package com.example.productservice.service.impl;

import com.example.productservice.model.dto.response.CourseResponse;
import com.example.productservice.model.entity.Course;
import com.example.productservice.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate<String,Object> template;
    @Autowired
    ObjectMapper redisMapper;

    @Override
    public void clear() {
        template.getConnectionFactory().getConnection().serverCommands().flushAll();
    }
//    Xóa tất cả các dữ liệu được lưu trữ trong Redis, tức là làm sạch toàn bộ cache
//    Dùng khi cần reset toàn bộ cache, ví dụ sau khi cập nhật dữ liệu lớn trong hệ thống


    @Override
    public void lPush(String key, String o) {
        System.out.println(template.opsForList().leftPush(key,o));
    }
    //Thêm một phần tử vào đầu danh sách (list) trong Redis với key cụ thể
    //Sử dụng template.opsForList().leftPush() để thao tác với danh sách (list) trong Redis
    //Thêm dữ liệu vào hàng đợi (queue) kiểu FIFO

    @Override
    public String rPop(String key) {
        return (String) template.opsForList().rightPop(key);
    }
    //Lấy và loại bỏ phần tử cuối danh sách từ Redis
    //Dùng để xử lý dữ liệu từ cuối hàng đợi (queue)

    @Override
    public boolean check(String key) {
        return template.hasKey(key);
    }

    @Override
    public String get(String key) {
        return (String) template.opsForValue().get(key);
    }
    //Kiểm tra xem Redis có chứa key cụ thể hay không
    // Tránh các thao tác không cần thiết trên Redis nếu key không tồn tại

    @Override
    public void lPushAll(String key, List<?> list) {
        System.out.println(template.opsForList().leftPushAll(key, list));
    }
    //Thêm một danh sách (list) phần tử vào đầu danh sách Redis với key cụ thể,Phù hợp cho việc thêm nhiều phần tử vào hàng đợi
    //Dùng template.opsForList().leftPushAll() để thêm toàn bộ phần tử trong danh sách vào Redis

    @Override
    public ResponseEntity<?> getAllCourse(int page) {
        try {
            String json = (String) template.opsForValue().get("course_list");
            List<Course> list =redisMapper.readValue(json, new TypeReference<List<Course>>() {
            });
            return ResponseEntity.ok(list.stream().limit(10).skip(page*9));
        }
        catch (Exception e){
            System.out.println("no response");
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getCourseById(long id) {
        try {
            String json = (String) template.opsForValue().get("course" + id);
            CourseResponse c = redisMapper.convertValue(json, new TypeReference<CourseResponse>() {});
            return ResponseEntity.ok(c);
        } catch (Exception e) {
            return null;
        }
    }

    public void setValueRedis(String key, Object value, int ttl, TimeUnit t) {
        if (ttl != 0)
            template.opsForValue().set(key, value, ttl, t);
        else
            template.opsForValue().set(key, value);
    }

    public void setHash(String key, String hashKey, Object value) {
        template.opsForHash().put(key, hashKey, value);
    }

    public Object getHash(String key, String hashKey) {
        return template.opsForHash().get(key, hashKey);
    }

    public Map<Object, Object> getAllHash(String key) {
        return template.opsForHash().entries(key);
    }

    public void deleteHash(String key, String hashKey) {
        template.opsForHash().delete(key, hashKey);
    }

    public boolean hasHashKey(String key, String hashKey) {
        return template.opsForHash().hasKey(key, hashKey);
    }

    public Long incrementHash(String key, String hashKey, long delta) {
        return template.opsForHash().increment(key, hashKey, delta);
    }

    public Course getCourseForHash(String redisKey, Long courseId) {
        String courseIdAsString = String.valueOf(courseId);
        Object courseJson = template.opsForHash().get(redisKey, courseIdAsString);
        if (courseJson != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(courseJson.toString(), Course.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    public void setValueRedisHash(String key, Map<String, Object> valueMap, int ttl, TimeUnit timeUnit) {
        template.opsForHash().putAll(key, valueMap);
        if (ttl > 0) {
            template.expire(key, ttl, timeUnit);
        }
    }

    public void setValueRedisQuantity(String key, Object value, int ttl, TimeUnit t) {
        if (value instanceof Course) {
            Course course = (Course) value;

            if (course.getQuantity() == 0) {
                template.delete(key);
                System.out.println("Deleted course with id " + course.getId() + " due to quantity being 0.");
                return;
            }
        }
        if (ttl != 0) {
            template.opsForValue().set(key, value, ttl, t);
        } else {
            template.opsForValue().set(key, value);
        }
    }

    public void deleteCourse(String redisKey, Long courseId) {
        template.opsForHash().delete(redisKey, String.valueOf(courseId));
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCourse(String redisKey, Long courseId, Course course) {
        try {
            String courseJson = redisMapper.writeValueAsString(course);
            template.opsForHash().put(redisKey, String.valueOf(courseId), courseJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error converting Course object to JSON for Redis", e);
        }
    }
}
