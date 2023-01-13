package nextstep.domains.theme.controller;

import io.restassured.RestAssured;
import nextstep.domain.theme.dto.ThemeRequestDto;
import nextstep.domain.theme.repository.ThemeRepository;
import nextstep.domain.theme.service.ThemeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.core.Is.is;

@DisplayName("Theme Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private ThemeService themeService;
    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        themeRepository.clear();
        ThemeRequestDto requestDto = new ThemeRequestDto(
                "워너고홈",
                "병맛 어드벤처 회사 코믹물",
                29_000
        );
        themeService.add(requestDto);
    }

    @AfterEach
    void afterEach() {
        themeRepository.clear();
    }

    @DisplayName("Theme - 테마 생성")
    @Test
    void add() {
        ThemeRequestDto requestDto = new ThemeRequestDto(
                "방탈출1",
                "방탈출 설명",
                28_000
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/2");
    }

    @DisplayName("Theme - 테마 생성 - 동일 이름 테마 생성 불가")
    @Test
    void addExceptionTest() {
        ThemeRequestDto requestDto = new ThemeRequestDto(
                "워너고홈",
                "병맛 어드벤처 회사 코믹물",
                29_000
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("이미 존재하는 이름의 테마입니다."));
    }
}
