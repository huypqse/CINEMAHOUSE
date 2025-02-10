package cinemahouse.project;


import cinemahouse.project.controller.AuthenticationController;
import cinemahouse.project.controller.EmailController;
import cinemahouse.project.controller.UserController;
import cinemahouse.project.controller.impl.AuthenticationControllerImpl;
import cinemahouse.project.controller.impl.EmailControllerImpl;
import cinemahouse.project.controller.impl.UserControllerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

	@InjectMocks
	private AuthenticationControllerImpl authenticationController;

	@InjectMocks
	private EmailControllerImpl emailController;

	@InjectMocks
	private UserControllerImpl userController;
	@Test
	void contextLoads() {
		Assertions.assertNotNull(authenticationController);
		Assertions.assertNotNull(emailController);
		Assertions.assertNotNull(userController);


	}

}
