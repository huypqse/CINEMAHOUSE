package cinemahouse.project.service;

import cinemahouse.project.dto.event.NotificationEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailService {

    @NonFinal
    @Value("${spring.mail.username}")
    String emailFrom;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String subject, String content, List<String> toList) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(emailFrom, "CINEMAHOUSE");
            helper.setTo(toList.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Error sending email to {}: {}", toList, e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "notification-delivery")
    public void sendEmailByKafka(NotificationEvent event)
            throws MessagingException, UnsupportedEncodingException {
        log.info("Received Kafka message to send email: {}", event);

        if (event == null) {
            log.error("Received null NotificationEvent. Skipping email processing.");
            return;
        }

        if (event.getRecipient() == null || event.getTemplateCode() == null || event.getSubject() == null) {
            log.error("Invalid NotificationEvent: missing recipient, templateCode, or subject. Event: {}", event);
            return;
        }

        try {
            Context context = new Context();
            context.setVariable("recipientName", event.getRecipient());

            if (event.getParam() != null) {
                context.setVariables(event.getParam());
            } else {
                log.warn("Event param is null, cannot set variables in email template.");
            }

            // Process email content using Thymeleaf template
            String htmlContent = templateEngine.process(event.getTemplateCode(), context);

            // Prepare email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(emailFrom, "Cinema House");
            helper.setTo(event.getRecipient());
            helper.setSubject(event.getSubject());
            helper.setText(htmlContent, true);

            // Send email
            mailSender.send(mimeMessage);
            log.info("Email sent to {} successfully!", event.getRecipient());

        } catch (Exception ex) {
            log.error("Error processing email for recipient {}: {}", event.getRecipient(), ex.getMessage(), ex);
            throw new RuntimeException("Failed to process Kafka message", ex);
        }
    }
}




