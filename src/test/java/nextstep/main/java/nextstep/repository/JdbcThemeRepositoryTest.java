package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class JdbcThemeRepositoryTest {

    public static final Long NON_EXIST_THEME_ID = 0L;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private JdbcThemeRepository jdbcThemeRepository;

    @BeforeEach
    void setUp() {
        jdbcThemeRepository = new JdbcThemeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("테마 생성 테스트")
    void createTest() {
        Theme expectedTheme = new Theme("테마이름", "테마설명", 22000);
        Theme actualTheme = jdbcThemeRepository.save(expectedTheme);

        assertThat(actualTheme.getName()).isEqualTo(expectedTheme.getName());
        assertThat(actualTheme.getDesc()).isEqualTo(expectedTheme.getDesc());
        assertThat(actualTheme.getPrice()).isEqualTo(expectedTheme.getPrice());
    }

    @Test
    @DisplayName("테마 단건 조회 테스트")
    void findBydIdTest() {
        Theme savedTheme = jdbcThemeRepository.save(new Theme("테마이름", "테마설명", 22000));

        assertThat(jdbcThemeRepository.findById(savedTheme.getId())
                .get())
                .isEqualTo(savedTheme);
        assertThat(jdbcThemeRepository.findById(NON_EXIST_THEME_ID)
                .isEmpty()).isTrue();
    }
}