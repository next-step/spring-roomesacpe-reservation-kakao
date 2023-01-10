package roomescape.console.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

@DisplayName("콘솔용 데이터베이스 접근 테스트")
public class ReservationDAOTest {

    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=true";
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static final Theme THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);

    private Connection con;

    @BeforeEach
    void setUp() throws SQLException {
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        con.createStatement().executeUpdate("DROP TABLE RESERVATION IF EXISTS");
        con.createStatement().executeUpdate("CREATE TABLE RESERVATION("
                + "id bigint not null auto_increment, date date, time time,"
                + " name varchar(20), theme_name varchar(20), theme_desc  varchar(255),"
                + " theme_price int, primary key (id));");
    }

    @AfterEach
    void setDown() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    @DisplayName("예약 생성")
    @Test
    void addReservation() throws SQLException {
        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-01"),
                LocalTime.parse("13:00"), "test", THEME);
        ReservationDAO reservationDAO = new ReservationDAO(URL, USER, PASSWORD);
        reservationDAO.addReservation(reservation);
        ResultSet resultSet = con.createStatement()
                .executeQuery("SELECT count(*) FROM RESERVATION");
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getInt(1)).isEqualTo(1);
    }

    @DisplayName("예약 찾기")
    @Test
    void findReservation() throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO(URL, USER, PASSWORD);
        con.createStatement().executeUpdate("INSERT INTO RESERVATION"
                + "(id, name, date, time, theme_name, theme_desc, theme_price) "
                + "VALUES (1, 'test', '2022-08-22', '13:00', '워너고홈', '병맛 어드벤처 회사 코믹물', 29000)");
        Reservation reservation = reservationDAO.findReservation(1L);
        assertThat(reservation.getName()).isEqualTo("test");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.parse("2022-08-22"));
        assertThat(reservation.getTime()).isEqualTo(LocalTime.parse("13:00:00"));
        assertThat(reservation.getTheme().getName()).isEqualTo("워너고홈");
        assertThat(reservation.getTheme().getDesc()).isEqualTo("병맛 어드벤처 회사 코믹물");
        assertThat(reservation.getTheme().getPrice()).isEqualTo(29000);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO(URL, USER, PASSWORD);
        con.createStatement().executeUpdate("INSERT INTO RESERVATION"
                + "(id, name, date, time, theme_name, theme_desc, theme_price) "
                + "VALUES (1, 'test', '2022-08-22', '13:00', '워너고홈', '병맛 어드벤처 회사 코믹물', 29000)");
        reservationDAO.deleteReservation(1L);
        ResultSet resultSet = con.createStatement()
                .executeQuery("SELECT count(*) FROM RESERVATION");
        assertThat(resultSet.next()).isTrue();
        assertThat(resultSet.getInt(1)).isEqualTo(0);
    }
}