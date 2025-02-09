package cinemahouse.project.service.interfaces;

import cinemahouse.project.dto.request.EmailRequest;
import cinemahouse.project.dto.response.UserResponse;
import cinemahouse.project.entity.User;
import cinemahouse.project.entity.status.Account;
import cinemahouse.project.entity.status.Gender;
import cinemahouse.project.mapper.UserMapper;
import cinemahouse.project.repository.RoleRepository;
import cinemahouse.project.repository.UserRepository;
import cinemahouse.project.service.EmailServiceImpl;
import cinemahouse.project.service.OtpService;
import cinemahouse.project.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    //khi ma tao mock cho phuong thuc thi chi tao mock cho phuong thuc day thoi
    //trong cai phuong thuc day ma goi service khac thi chung ta khoi tao cac mock tuong ung

    private @Mock UserRepository userRepository;
    private @Mock UserMapper userMapper;
    private @Mock PasswordEncoder passwordEncoder;
    private @Mock RoleRepository roleRepository;
    private @Mock EmailServiceImpl emailService;
    private @Mock OtpService otpService;
    private @Mock KafkaTemplate<String, Object> kafkaTemplate;


    private static User Huy;
    private static User Admin;
    //b1 tao database de test
    @BeforeAll
    static void beforeAll() {
        Huy = new User();
        Huy.setUsername("Huy");
        Huy.setPassword("Huyquang123");
        Huy.setEmail("huypqsee@gmail.com");
        Huy.setGender(Gender.MALE);
        Huy.setPhoneNumber("1234567890");
        Huy.setBirthDate(LocalDate.of(2000, 5, 1));
        Huy.setOtp("123456");
        Huy.setOtpExpiryDate(LocalDateTime.of(2025, 2, 7, 10, 0));
        Huy.setAccount(Account.ACTIVE); // Assuming 'CUSTOMER' is one of the possible values

        // Create the Admin user
        Admin = new User();
        Admin.setUsername("Admin");
        Admin.setPassword("Adminquang123");
        Admin.setEmail("admin@gmail.com");
        Admin.setGender(Gender.MALE);
        Admin.setPhoneNumber("9876543210");
        Admin.setBirthDate(LocalDate.of(1990, 3, 15));
        Admin.setOtp("654321");
        Admin.setOtpExpiryDate(LocalDateTime.of(2025, 2, 7, 10, 0));
        Admin.setAccount(Account.ACTIVE); // Assuming 'ADMIN' is one of the possible values
    }
    //Before khoi tao trc khi chay unit test
    @BeforeEach
    void setUp() {
        //khoi tao buoc trien khai la UserService
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder, roleRepository, emailService, otpService, kafkaTemplate);

    }
    //after sau khi chay unit test
    @AfterEach
    void tearDown() {
    }

    @Test
    void findByEmail_Success() {
        when(userRepository.findByEmail("huypqse@gmail.com")).thenReturn(Optional.of(Huy));
        User user = userRepository.findByEmail("huypqse@gmail.com").orElseThrow(null);
        assertNotNull(user);
        assertEquals(Huy.getEmail(), user.getEmail());
    }
    @Test
    void findByEmail_Failure() {

        //đây là nạp dữ liệu vào
        when(userRepository.existsByEmail("huypqsee@gmail.com")).thenReturn(true);
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail(Huy.getEmail().trim());
        Boolean result = userService.findByEmail(emailRequest);
        assertEquals(true, result);
    }
    @Test
    void testGetListUsers_Success() {
        List<User> users = new ArrayList<>();
        users.add(Huy);
        users.add(Admin);
    //khi ma find phuong thuc thi tra ve list user
        when(userRepository.findAll()).thenReturn(users);

        //xong buoc gia lap goi phuong thuc can test
        List<UserResponse> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    void testGetListUsers_EmptyList() {
        List<User> users = new ArrayList<>();

        //khi ma find phuong thuc thi tra ve list user
        when(userRepository.findAll()).thenReturn(users);

        //xong buoc gia lap goi phuong thuc can test
        List<UserResponse> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}