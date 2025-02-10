package cinemahouse.project.controller;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.request.EmailRequest;
import cinemahouse.project.dto.response.UserResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import cinemahouse.project.service.UserServiceImpl;
import cinemahouse.project.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    private static UserResponse Huy;
    private static UserResponse Admin;

    //tien hanh set up khoi tao data de test
    @BeforeAll
    static void setUp() {
        Huy = new UserResponse();
        Huy.setUsername("Huy");
        Huy.setEmail("huypqsee@gmail.com");


        // Create the Admin user
        Admin = new UserResponse();
        Admin.setUsername("Admin");
        Admin.setEmail("admin@gmail.com");

    }
    @Test
    //@WithMockUser(authorities = {"admin", "manager"})
    void testGetUser() throws Exception {
//        ApiResponse<Boolean> booleanApiResponse = new ApiResponse<>();
//        booleanApiResponse.setCode(200);
//        booleanApiResponse.setMessage("Success");
//        booleanApiResponse.setResult(Boolean.TRUE);
//        EmailRequest emailRequest = new EmailRequest();
//        emailRequest.setEmail("huypqsee@gmail.com");
//        when(userService.findByEmail(emailRequest)).thenReturn(true);
//        mockMvc.perform(post("/api/v1/user/check-exists-user").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
        when(userService.findByEmail(Mockito.any(EmailRequest.class))).thenReturn(true);

        mockMvc.perform(post("/api/v1/user/check-exists-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"huypqsee@gmail.com\"}"))
                .andExpect(status().isOk());
    }

}
