package cinemahouse.project.service.interfaces;

import cinemahouse.project.dto.event.NotificationEvent;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface EmailService {

    /**
     * Gửi email bằng SendGrid.
     * @param to Địa chỉ email người nhận.
     * @param subject Tiêu đề email.
     * @param text Nội dung email.
     */
    void send(String to, String subject, String text);

    /**
     * Gửi email với template xem trailer.
     * @param to Địa chỉ email người nhận.
     * @param name Tên người nhận.
     * @param link Đường dẫn trailer.
     * @throws IOException Nếu có lỗi xảy ra khi gửi email.
     */
    void emailViewTrailer(String to, String name, String link) throws IOException;

    /**
     * Gửi email đồng thời đến nhiều người.
     * @param subject Tiêu đề email.
     * @param content Nội dung email.
     * @param toList Danh sách địa chỉ email nhận.
     */
    void sendEmail(String subject, String content, List<String> toList);

    /**
     * Gửi email dựa trên sự kiện Kafka.
     * @param event Sự kiện thông báo email.
     * @throws MessagingException Nếu có lỗi khi gửi email.
     * @throws UnsupportedEncodingException Nếu có lỗi mã hóa.
     */
    void sendEmailByKafka(NotificationEvent event) throws MessagingException, UnsupportedEncodingException;
}
