package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.dto.ThemeRequest;
import nextstep.repository.ThemeJdbcTemplateDao;
import nextstep.service.ThemeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@DisplayName("Theme Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    ThemeService themeService;
    @Autowired
    ThemeJdbcTemplateDao themeJdbcTemplateDao;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        ThemeRequest requestDto = new ThemeRequest(
                "테마이름",
                "테마설명",
                22000
        );
        themeService.create(requestDto);
    }

    @AfterEach
    void afterEach() {
        themeJdbcTemplateDao.clear();
    }

    @DisplayName("생성 - 정상적인 양식으로 요청시 성공해야 한다.")
    @Test
    void createNormally() {
        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름2");
            put("desc", "테마설명");
            put("price", "22000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/2");
    }

    @DisplayName("생성 - 잘못된 양식(값)으로 요청시 예외처리 되어야 한다.")
    @ParameterizedTest
    @CsvSource(value = {
            ";테마설명;22000",
            "테마이름2;;22000",
            "테마이름2;테마설명;",
            "테마이름2;테마설명;-1",
            "테마이름2;테마설명;a"
    }, delimiter = ';')
    void createInvalidValue(String name, String desc, String price) {
        Map<String, String> request = new HashMap<>() {{
            put("name", name);
            put("desc", desc);
            put("price", price);
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("생성 - 이미 등록된 테마이름으로 요청시 예외처리 되어야 한다.")
    @Test
    void createAlreadyCreatedName() {
        Map<String, String> request = new HashMap<>() {{
            put("name", "테마이름");
            put("desc", "테마설명");
            put("price", "22000");
        }};

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("조회(단일) - 등록된 테마의 id 로 요청시 조회 되어야 한다.")
    @Test
    void retrieveOneNormally() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is("테마이름"))
                .body("desc", is("테마설명"))
                .body("price", is(22000));
    }

    @DisplayName("조회(단일) - 등록되지 않은 id 로 요청시 예외처리 되어야 한다.")
    @Test
    void retrieveOneInvalidId() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/2")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("조회(단일) - 잘못된 id 로 요청시 예외처리 되어야 한다.")
    @Test
    void retrieveOneWrongParameter() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/a")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("조회(목록) - 목록 조회 요청시 조회 되어야 한다.")
    @Test
    void retrieveAllNormally() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }
}