package cinemahouse.project.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Đăng ký các endpoint để ứng dụng giao tiếp qua WebSocket.
     * Phương thức này khai báo một endpoint tại đường dẫn "/ws" và cho phép kết nối từ các nguồn cụ thể.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // Thêm endpoint WebSocket tại "/ws"
        stompEndpointRegistry.addEndpoint("/ws")
                // Chỉ cho phép các yêu cầu từ "http://localhost:5173"
                .setAllowedOrigins("http://localhost:5173")
                // Sử dụng SockJS nếu trình duyệt không hỗ trợ WebSocket gốc
                .withSockJS();
    }

    /**
     * Cấu hình message broker, định nghĩa các tiền tố (prefix) cho đường dẫn gửi và nhận tin nhắn.
     * Message broker chịu trách nhiệm trung chuyển tin nhắn giữa client và server.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Kích hoạt message broker đơn giản với tiền tố "/topic"
        // Client có thể subscribe để nhận tin nhắn từ các đường dẫn bắt đầu với "/topic"
        registry.enableSimpleBroker("/topic");

        // Định nghĩa tiền tố "/app" cho các đường dẫn gửi tin nhắn từ client đến server
        // Các client sẽ gửi tin nhắn đến server qua các endpoint bắt đầu bằng "/app"
        registry.setApplicationDestinationPrefixes("/app");
    }
}
