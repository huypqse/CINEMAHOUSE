package cinemahouse.project.configuration;

import cinemahouse.project.constant.PredefinedRole;
import cinemahouse.project.entity.*;
import cinemahouse.project.entity.status.*;
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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    @NonFinal
    static final String CINEMA_NAME = "CGV Vincom Center";
    @NonFinal
    static final String ROOM_TYPE_VIP = "VIP";
    @Bean
//    @ConditionalOnProperty(
//            prefix = "spring",
//            value = "datasource.driverClassName",
//            havingValue = "org.postgresql.Driver"
//    )
    ApplicationRunner applicationRunner(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        MovieRepository movieRepository,
                                        MovieSearchRepository movieSearchRepository,
                                        CinemaRepository cinemaRepository,
                                        ScreeningRoomRepository screeningRoomRepository,
                                        RoomTypeRepository roomTypeRepository,
                                        ScreeningSessionRepository screeningSessionRepository,
                                        SeatTypeRepository seatTypeRepository,
                                        TicketRepository ticketRepository,
                                        TicketPriceRepository ticketPriceRepository, SeatRepository seatRepository, MovieTypeRepository movieTypeRepository) {


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
            Set<MovieType> movieTypes = new HashSet<>();
            MovieType movieType = MovieType.builder()
                    .type("Honor")
                    .movieTypeStatus(MovieTypeStatus.ACTIVE)
                    .build();
            movieTypeRepository.save(movieType);
            movieTypes.add(movieType);
            MovieType movieTypess = MovieType.builder()
                    .type("Funny")
                    .movieTypeStatus(MovieTypeStatus.ACTIVE)
                    .build();
            movieTypeRepository.save(movieTypess);
           movieTypes.add(movieTypess);
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
                    .movieTypes(movieTypes)
                    .build();
            movieRepository.save(movie);
           // movieSearchRepository.index(movie);

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
                    .movieTypes(movieTypes)
                    .build();
            movieRepository.save(film);
          //  movieSearchRepository.index(film);
            Optional<Cinema> optionalCinema = cinemaRepository.findByName(CINEMA_NAME);

            if (optionalCinema.isEmpty()) {
                Cinema cinema = Cinema.builder()
                        .name("CGV Vincom Center")
                        .address("72 Lê Thánh Tôn, Quận 1, TP.HCM")
                        .cinemaStatus(CinemaStatus.Open)
                        .build();
                cinemaRepository.save(cinema);
            } else {
                Cinema cinemahouse = optionalCinema.get();
                System.out.println("Cinema already exists: " + cinemahouse.getName());
            }

            List<RoomType> roomTypeList = roomTypeRepository.findAll();
            if (roomTypeList.isEmpty()) {
                RoomType roomType = RoomType.builder()
                        .name("VIP")
                        .status(RoomTypeStatus.VIP)
                        .build();
                roomTypeRepository.save(roomType);
                RoomType roomTypeRegular = RoomType.builder()
                        .name("Standar")
                        .status(RoomTypeStatus.Standard)
                        .build();
                roomTypeRepository.save(roomTypeRegular);
                RoomType roomTypeLuxary = RoomType.builder()
                        .name("Luxury")
                        .status(RoomTypeStatus.Luxury)
                        .build();
                roomTypeRepository.save(roomTypeLuxary);
            } else {
                System.out.println("RoomType already exists: " + roomTypeList.size());
            }

            ScreeningRoom screeningRoom = ScreeningRoom.builder()
                    .name("Room 1")
                    .cinema(cinemaRepository.findByName(CINEMA_NAME).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_EXISTED)))
                    .screeningRoomStatus(ScreeningRoomStatus.Available)
                    .roomType(roomTypeRepository.findByName(ROOM_TYPE_VIP).orElseThrow(() -> new AppException(ErrorCode.ROOMTYPE_VIP__NOT_EXISTED)))
                    .build();
            screeningRoomRepository.save(screeningRoom);
//            ScreeningSession screeningSession = ScreeningSession.builder()
//                    .screeningRoom(screeningRoom)
//                    .movie(movie)
//                    .startDate(LocalDate.now()) // Today's date for the screening
//                    .startTime(Instant.now())  // Current time as the session's start time
//                    .endTime(Instant.now().plus(Duration.ofHours(2))) // Assuming the session lasts 2 hours
//                    .screeningSessionStatus(ScreeningSessionStatus.Ongoing) // Enum for current status
//                    .build();
//            screeningSessionRepository.save(screeningSession);
//            ScreeningSession screeningSessions = ScreeningSession.builder()
//                    .screeningRoom(screeningRoom)
//                    .movie(film)
//                    .startDate(LocalDate.now().plusDays(1)) // Today's date for the screening
//                    .startTime(Instant.now().plus(Duration.ofHours(1)))  // Current time as the session's start time
//                    .endTime(Instant.now().plus(Duration.ofHours(3))) // Assuming the session lasts 2 hours
//                    .screeningSessionStatus(ScreeningSessionStatus.Ongoing) // Enum for current status
//                    .build();
//            screeningSessionRepository.save(screeningSessions);
//
//
//            SeatType seatVIP = SeatType.builder()
//                    .name("VIP") // Specify seat type name, e.g., "VIP"
//                    .build();
//            seatTypeRepository.save(seatVIP);
//            SeatType seatRegular = SeatType.builder()
//                    .name("Regular") // Specify seat type name, e.g., "VIP"
//                    .build();
//            seatTypeRepository.save(seatRegular);
//
//            // Tạo 50 ghế VIP
//            for (int i = 1; i <= 50; i++) {
//                Seat seat = Seat.builder()
//                        .row("A" + i)  // Specify the row, e.g., "A"
//                        .column(i) // Số cột sẽ từ 1 đến 50
//                        .status(SeatStatus.AVAILABLE) // Set status
//                        .seatType(seatVIP) // Set seat type to VIP
//                        .build();
//                seatRepository.save(seat);
//            }
//
//            // Tạo 50 ghế Regular
//            for (int i = 1; i <= 50; i++) {
//                Seat seat = Seat.builder()
//                        .row("B" + i)  // Specify the row, e.g., "B"
//                        .column(i) // Số cột sẽ từ 1 đến 50
//                        .status(SeatStatus.AVAILABLE) // Set status
//                        .seatType(seatRegular) // Set seat type to Regular
//                        .build();
//                seatRepository.save(seat);
//            }
//            TicketPrice ticketPriceVIP = TicketPrice.builder().price(200.000).status(TicketPriceStatus.VIP)
//            .build();
//            ticketPriceRepository.save(ticketPriceVIP);
//            TicketPrice ticketPriceRegular = TicketPrice.builder().price(100.000).status(TicketPriceStatus.NORMAL)
//                    .build();
//            ticketPriceRepository.save(ticketPriceRegular);
//            Ticket ticket = Ticket.builder()
//                    .status(TicketStatus.AVAILABLE)
//                    .build();
//            ticketRepository.save(ticket);
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