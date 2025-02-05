package cinemahouse.project.service;

import cinemahouse.project.dto.event.NotificationEvent;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j(topic = "EMAIL-SERVICE")
public class EmailService {

//    @NonFinal
    @Value("${spring.mail.username}")
   private String emailFrom;

//    @NonFinal
    @Value("${spring.sendgrid.from-email}")
   private String from;
    private final SendGrid sendGrid;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * Send email by SendGrid
     * @param to send email to someone
     * @param subject
     * @param text
     */
    public void send(String to, String subject, String text) {
        Email fromEmail = new Email(from);
        Email toEmail = new Email(to);

        Content content = new Content("text/plain", text);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            log.info("SendGrid Response: StatusCode={}, Body={}", response.getStatusCode(), response.getBody());

            if (response.getStatusCode() == 202) {
                log.info("Email sent successfully");
            } else {
                log.error("Email sent failed with status: {}", response.getStatusCode());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    public void emailViewTrailer(String to, String name, String link) throws IOException {
        log.info("Email view trailer started");

        Email fromEmail = new Email(from, "CinemaHouse");
        Email toEmail = new Email(to);

        String subject = "Cinema House View Trailer";
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("link", link);

        Mail mail = new Mail();
        mail.setFrom(fromEmail);
        mail.setSubject(subject);
        mail.setTemplateId("d-fb251c6e444a488e976c9ba97b0d2704");

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);

        // Thêm dữ liệu động vào email
        map.forEach(personalization::addDynamicTemplateData);

        // Quan trọng: Thêm personalization vào mail
        mail.addPersonalization(personalization);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        log.info("SendGrid Response: StatusCode={}, Body={}", response.getStatusCode(), response.getBody());

        if (response.getStatusCode() == 202) {
            log.info("Email sent successfully");
        } else {
            log.error("Email sending failed with status: {}", response.getStatusCode());
        }
    }
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




