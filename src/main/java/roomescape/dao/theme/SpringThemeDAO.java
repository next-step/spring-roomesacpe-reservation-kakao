package roomescape.dao.theme;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.dao.theme.preparedstatementcreator.ExistThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.InsertThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ListThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.RemoveThemePreparedStatementCreator;
import roomescape.dto.Theme;
import roomescape.exception.BadRequestException;

public class SpringThemeDAO extends ThemeDAO {

    private final JdbcTemplate jdbcTemplate;

    public SpringThemeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private <T> void validateResult(List<T> result) {
        if (result.size() != 1) {
            throw new BadRequestException();
        }
    }

    @Override
    public boolean exist(Theme theme) {
        List<Boolean> result = jdbcTemplate.query(
                new ExistThemePreparedStatementCreator(theme), getExistRowMapper());
        validateResult(result);
        return result.get(0);
    }

    @Override
    public Long create(Theme theme) {
        validate(theme);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new InsertThemePreparedStatementCreator(theme), keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public List<Theme> list() {
        return jdbcTemplate.query(
                new ListThemePreparedStatementCreator(), getRowMapper());
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(
                new RemoveThemePreparedStatementCreator(id));
    }
}
