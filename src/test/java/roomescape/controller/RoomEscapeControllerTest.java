package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@DisplayName("Http Method")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomEscapeControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    /**
     * RoomEscapeController > createReservation 메서드
     */
    @DisplayName("Http Method - POST")
    @Test
    void createUser() {
        Reservation reservation = new Reservation(1L, LocalDate.of(2022,8,11), LocalTime.of(13,0,0), "name", new Theme("Theme", "병맛 어드벤처 회사 코믹물", 30000));

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    /**
     * HttpMethodController > showUser 메서드
     */
    @DisplayName("Http Method - GET")
    @Test
    void showUser() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/http-method/users")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2));
    }
}
