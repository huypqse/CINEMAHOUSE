package cinemahouse.project.configuration;

import cinemahouse.project.constant.PredefinedRole;
import cinemahouse.project.entity.*;
import cinemahouse.project.entity.status.CinemaStatus;
import cinemahouse.project.entity.status.MovieStatus;
import cinemahouse.project.entity.status.RoomTypeStatus;
import cinemahouse.project.entity.status.ScreeningRoomStatus;
import cinemahouse.project.exception.AppException;
import cinemahouse.project.exception.ErrorCode;
import cinemahouse.project.repository.*;
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
            havingValue = "org.postgresql.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        MovieRepository movieRepository,
                                        MovieSearchRepository movieSearchRepository,
                                        CinemaRepository cinemaRepository,
                                        ScreeningRoomRepository screeningRoomRepository,
                                        RoomTypeRepository roomTypeRepository) {

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

            Movie movie = Movie.builder()
                    .name("Interstellar")
                    .actors("Matthew McConaughey, Anne Hathaway, Jessica Chastain")
                    .director("Christopher Nolan")
                    .ageLimit(12)
                    .content("A visually stunning and emotionally gripping story of space exploration and the bond between a father and his daughter.")
                    .language("English")
                    .coverPhoto("https://example.com/interstellar.jpg") // Replace with an actual URL if needed
                    .startDate(LocalDate.of(2014, 10, 26)) // Original premiere date
                    .releaseDate(LocalDate.of(2014, 11, 7))
                    .status(MovieStatus.COMING_SOON)
                    .build();
            movieRepository.save(movie);
            movieSearchRepository.index(movie);

            Movie film = Movie.builder()
                    .name("The Dark Knight")
                    .actors("Christian Bale, Heath Ledger, Gary Oldman, Morgan Freeman")
                    .director("Christopher Nolan")
                    .ageLimit(13)
                    .content("Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham City.")
                    .language("English")
                    .coverPhoto("https://example.com/dark-knight.jpg") // Replace with actual URL
                    .startDate(LocalDate.of(2008, 6, 18)) // Premiere date
                    .releaseDate(LocalDate.of(2008, 7, 18))
                    .status(MovieStatus.COMING_SOON)
                    .build();
            movieRepository.save(film);
            movieSearchRepository.index(film);

            Cinema cinema = Cinema.builder()
                    .name("CGV Vincom Center")
                    .address("72 Lê Thánh Tôn, Quận 1, TP.HCM")
                    .cinemaStatus(CinemaStatus.Open)
                    .build();
            cinemaRepository.save(cinema);
            RoomType roomType = RoomType.builder()
                    .name("VIP")
                    .status(RoomTypeStatus.VIP)
                    .build();
            roomTypeRepository.save(roomType);
            ScreeningRoom screeningRoom = ScreeningRoom.builder()
                    .name("Room 1")
                    .cinema(cinema)
                    .screeningRoomStatus(ScreeningRoomStatus.Available)
                    .roomType(roomType)
                    .build();
            screeningRoomRepository.save(screeningRoom);

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