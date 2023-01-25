package nextstep.console;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import javax.sql.DataSource;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.ConsoleReservationRepository;
import nextstep.repository.ConsoleThemeRepository;
import nextstep.repository.ReservationRepository;
import nextstep.repository.ThemeRepository;
import nextstep.service.RoomEscapeService;
import nextstep.web.dto.ReservationRequest;

public class RoomEscapeApplication {

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    private static RoomEscapeService service;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataSource dataSource = createHikariDataSource();
        createDataBaseTables(dataSource);

        ThemeRepository themeRepository = new ConsoleThemeRepository(dataSource);
        Theme theme = themeRepository.save(new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));

        ReservationRepository reservationRepository = new ConsoleReservationRepository(dataSource);
        service = new RoomEscapeService(reservationRepository, themeRepository);

        while (true) {
            String input = getInput(scanner);
            try {
                if (input.startsWith(ADD)) {
                    Reservation reservation = addReservation(input, theme);
                    printReservation(reservation);
                }
                if (input.startsWith(FIND)) {
                    Reservation reservation = findReservation(input);
                    printReservation(reservation);
                    printReservationTheme(reservation.getTheme());
                }
                if (input.startsWith(DELETE)) {
                    deleteReservation(input);
                }
                if (input.equals(QUIT)) {
                    break;
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
        scanner.close();
    }

    private static void createDataBaseTables(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS RESERVATION\n"
                    + "(\n"
                    + "    id          bigint not null auto_increment,\n"
                    + "    date        date,\n"
                    + "    time        time,\n"
                    + "    name        varchar(20),\n"
                    + "    theme_id    bigint,\n"
                    + "    primary key (id)\n"
                    + ");");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS THEME\n"
                    + "(\n"
                    + "    id    bigint not null auto_increment,\n"
                    + "    name  varchar(20),\n"
                    + "    desc  varchar(255),\n"
                    + "    price int,\n"
                    + "    primary key (id)\n"
                    + ");");
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static DataSource createHikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:~/prod");
        config.setUsername("sa");
        config.setPassword("");
        return new HikariDataSource(config);
    }

    public static String getInput(Scanner scanner) {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: add {date},{time},{name} ex) add 2022-08-11,13:00,류성현");
        System.out.println("- 예약조회: find {id} ex) find 1");
        System.out.println("- 예약취소: delete {id} ex) delete 1");
        System.out.println("- 종료: quit");

        return scanner.nextLine();
    }

    public static Reservation addReservation(String input, Theme theme) {
        String params = input.split(" ")[1];
        String date = params.split(",")[0];
        String time = params.split(",")[1];
        String name = params.split(",")[2];

        Reservation reservation = service.createReservation(
                new ReservationRequest(name, LocalDate.parse(date), LocalTime.parse(time), theme.getId()));

        System.out.println("예약이 등록되었습니다.");
        return reservation;
    }

    public static Reservation findReservation(String input) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);

        return service.getReservation(id);
    }

    public static void deleteReservation(String input) {
        String params = input.split(" ")[1];
        Long id = Long.parseLong(params.split(",")[0]);

        service.deleteReservation(id);
    }

    public static void printReservation(Reservation reservation) {
        System.out.println("예약 번호: " + reservation.getId());
        System.out.println("예약 날짜: " + reservation.getDate());
        System.out.println("예약 시간: " + reservation.getTime());
        System.out.println("예약자 이름: " + reservation.getName());
    }

    public static void printReservationTheme(Theme theme) {
        System.out.println("예약 테마 이름: " + theme.getName());
        System.out.println("예약 테마 설명: " + theme.getDesc());
        System.out.println("예약 테마 가격: " + theme.getPrice());
    }
}
