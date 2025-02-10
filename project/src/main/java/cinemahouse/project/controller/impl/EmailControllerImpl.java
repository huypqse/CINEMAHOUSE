package cinemahouse.project.controller.impl;

import cinemahouse.project.controller.EmailController;
import cinemahouse.project.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "EMAIL-CONTROLLER")
public class EmailControllerImpl implements EmailController {
    private final EmailServiceImpl emailService;

    @GetMapping("/send-email")
    @Override
    public void send(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        log.info("Sending email to " + to);
        emailService.send(to, subject, body);
        log.info("Email sent");
    }

    @GetMapping("/view-trailer-email")
    @Override
    public void sendEmail(@RequestParam String to, @RequestParam String name, @RequestParam String link) throws IOException {
        log.info("Sending email to " + to);
       emailService.emailViewTrailer(to, name, link);
    }
}
