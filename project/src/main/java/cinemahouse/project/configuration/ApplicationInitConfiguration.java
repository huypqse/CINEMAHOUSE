package cinemahouse.project.configuration;

import cinemahouse.project.constant.PredefinedRole;
import cinemahouse.project.entity.Movie;
import cinemahouse.project.entity.Role;
import cinemahouse.project.entity.User;
import cinemahouse.project.entity.status.MovieStatus;
import cinemahouse.project.exception.AppException;
import cinemahouse.project.exception.ErrorCode;
import cinemahouse.project.repository.MovieRepository;
import cinemahouse.project.repository.RoleRepository;
import cinemahouse.project.repository.UserRepository;
import cinemahouse.project.repository.search.MovieSearchRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfiguration {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin@gmail.com";
    @NonFinal
    static final String ADMIN_PASSWORD = "123456";
    @NonFinal
    static final String ADMIN_FULL_NAME = "Phan Quang Huy";
    @NonFinal
    static final String USER_NAME = "huypqsee@gmail.com";
    @NonFinal
    static final String USER_PASSWORD = "123456";
    @NonFinal
    static final String USER_FULL_NAME = "Phan Quang Huy";
    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, MovieRepository movieRepository, MovieSearchRepository movieSearchRepository) {
        log.info("Initializing application.....");

        return args -> {
            Set<Role> userRole = roleRepository.findByName(PredefinedRole.USER_ROLE).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            if (userRole.isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .build());
            }

            Set<Role> adminRole = roleRepository.findByName(PredefinedRole.ADMIN_ROLE).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            if (adminRole.isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .build());
            }

//            Movie movie = Movie.builder()
//                    .name("Doreamon")
//                    .actors("Nobita")
//                    .director(ADMIN_FULL_NAME)
//                    .ageLimit(13)
//                    .content("So funny")
//                    .language("English")
//                    .coverPhoto("jejej")
//                    .startDate(LocalDate.now())
//                    .releaseDate(LocalDate.now())
//                    .status(MovieStatus.COMING_SOON)
//                    .build();
//            movieRepository.save(movie);
//            movieSearchRepository.index(movie);

            if (userRepository.findByEmail(ADMIN_USER_NAME).isEmpty() && userRepository.findByEmail(USER_NAME).isEmpty()) {
                Set<Role> roleADM = roleRepository.findByName(PredefinedRole.ADMIN_ROLE)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
                Set<Role> roleUser = roleRepository.findByName(PredefinedRole.USER_ROLE)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
                User admin = User.builder()
                        .email(ADMIN_USER_NAME)
                        .username(ADMIN_FULL_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roleADM)
                        .birthDate(LocalDate.of(2004, 7, 20))
                        .build();
                User user = User.builder()
                        .email(USER_NAME)
                        .username(USER_FULL_NAME)
                        .password(passwordEncoder.encode(USER_PASSWORD))
                        .roles(roleUser)
                        .birthDate(LocalDate.of(2004, 7, 20))
                        .build();

                userRepository.save(admin);
                userRepository.save(user);

                log.warn("Admin user has been created with default password: 123456, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}