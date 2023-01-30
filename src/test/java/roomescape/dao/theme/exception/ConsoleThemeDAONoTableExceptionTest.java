package roomescape.dao.theme.exception;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.connection.ConnectionManager;
import roomescape.dao.theme.ConsoleThemeDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Theme;

@DisplayName("콘솔용 데이터베이스 접근 - 테마 테이블이 존재하지 않을 경우 null 리턴")
@JdbcTest
@Rollback(false)
@ActiveProfiles("test")
@Sql(value = {"classpath:/drop.sql"})
@Sql(value = {"classpath:/drop.sql", "classpath:/schema.sql", "classpath:/data.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ConsoleThemeDAONoTableExceptionTest {

    private static final String NAME_DATA = "워너고홈";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    @Autowired
    private DataSource dataSource;

    private ThemeDAO themeDAO;

    @BeforeEach
    void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(dataSource);
        themeDAO = new ConsoleThemeDAO(connectionManager);
    }

    @DisplayName("테마 생성) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenCreateIfNotExistingTable() {
        Theme theme = new Theme(NAME_DATA, DESC_DATA, PRICE_DATA);
        assertThat(themeDAO.create(theme)).isNull();
    }

    @DisplayName("테마 조회) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenFindIfNotExistingTable() {
        assertThat(themeDAO.find(1L)).isNull();
    }

    @DisplayName("테마 목록 조회) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenListIfNotExistingTable() {
        assertThat(themeDAO.list()).isNull();
    }

    @DisplayName("테마 존재 확인) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenExistIfNotExistingTable() {
        Theme theme = new Theme(NAME_DATA, DESC_DATA, PRICE_DATA);
        assertThat(themeDAO.exist(theme)).isNull();
    }

    @DisplayName("테마 아이디 존재 확인) 테이블이 존재하지 않을 경우 null 리턴")
    @Test
    void returnNullWhenExistIdIfNotExistingTable() {
        assertThat(themeDAO.existId(1L)).isNull();
    }
}
