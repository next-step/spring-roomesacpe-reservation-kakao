package nextstep.domain.repository;

import nextstep.domain.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    Long save(Theme theme);
    Optional<Theme> findThemeById(Long id);
    List<Theme> getAllThemes();
    boolean deleteThemeById(Long id);
}