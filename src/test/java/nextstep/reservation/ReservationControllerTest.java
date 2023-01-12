//package nextstep.reservation;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import nextstep.reservation.entity.Reservation;
//import nextstep.reservation.entity.Theme;
//import nextstep.reservation.service.ReservationService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//import static nextstep.reservation.exception.ReservationExceptionCode.DUPLICATE_TIME_RESERVATION;
//import static org.hamcrest.core.Is.is;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ReservationControllerTest {
//
//    @LocalServerPort
//    int port;
//    @Autowired
//    private ReservationService reservationService;
//    private Reservation reservation;
//
//    @BeforeEach
//    void setUp() {
//        RestAssured.port = port;
//        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
//        reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", theme);
//    }
//
//    @AfterEach
//    void tearDown() {
//        reservationService.clear();
//    }
//
//    @DisplayName("예약 생성")
//    @Test
//    void createReservation() {
//
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(reservation)
//                .when().post("/reservations")
//                .then().log().all()
//                .statusCode(HttpStatus.CREATED.value());
//    }
//
//    @DisplayName("이미 예약이 존재하는 시간에 예약 생성 시도할 때 예외 발생")
//    @Test
//    void createReservationDuplicate() {
//        Reservation reservationDuplicated = new Reservation(null, reservation.getDate(), reservation.getTime(), "name2", reservation.getTheme());
//        createReservation(reservation);
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(reservationDuplicated)
//                .when().post("/reservations")
//                .then().log().all()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .body(is(DUPLICATE_TIME_RESERVATION.getMessage()));
//    }
//
//    @DisplayName("예약 조회")
//    @Test
//    void showReservation() {
//        Response createResponse = createReservation(reservation);
//        String id = createResponse.getHeader("Location").split("/")[2]; // Redirect 주소: /reservations/id
//        RestAssured.given().log().all()
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/reservations/" + id)
//                .then().log().all()
//                .statusCode(HttpStatus.OK.value())
//                .body("date", is(reservation.getDate().toString()))
//                .body("time", is(reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
//    }
//
//    @Test
//    @DisplayName("id를 통한 예약 삭제")
//    void deleteReservation() {
//        Response response = createReservation(reservation);
//        String id = response.getHeader("Location").split("/")[2]; // Redirect 주소: /reservations/id
//        RestAssured.given().log().all()
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .when().delete("/reservations/" + id)
//                .then().log().all()
//                .statusCode(HttpStatus.NO_CONTENT.value());
//    }
//
//    @Test
//    @DisplayName("예약 전체 삭제")
//    void clearAll() {
//        RestAssured.given().log().all()
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .when().delete("/reservations")
//                .then().log().all()
//                .statusCode(HttpStatus.NO_CONTENT.value());
//    }
//
//    private Response createReservation(Reservation reservation) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(reservation)
//                .when().post("/reservations")
//                .then().log().all()
//                .extract()
//                .response();
//    }
//}