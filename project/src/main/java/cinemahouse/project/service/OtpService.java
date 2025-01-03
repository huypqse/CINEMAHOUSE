package cinemahouse.project.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtpService {
    BaseRedisServiceImpl<String, String, Object> redisService;
    public void saveOtp(String email, String otp){
            redisService.setTimeToLive(email, otp, 30);
    }

    public String getOtp(String email) {
        return (String) redisService.get(email);
    }

    public void deleteOtp(String email) {
        redisService.delete(email);
    }
}
