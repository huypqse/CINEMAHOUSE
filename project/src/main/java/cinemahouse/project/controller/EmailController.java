package cinemahouse.project.controller;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


public interface EmailController {

     void send(@RequestParam String to, @RequestParam String subject, @RequestParam String body);


     void sendEmail(@RequestParam String to, @RequestParam String name, @RequestParam String link) throws IOException;

}
