package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.ThemeRequest;
import roomescape.controller.dto.ThemeResponse;
import roomescape.domain.Theme;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<ThemeResponse> getAllThemes() {
        List<Theme> themes = themeRepository.getAllThemes();
        return ThemeResponse.toList(themes);
    }

    public ThemeResponse getTheme(Long themeId) {
        Theme theme = themeRepository.getTheme(themeId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));
        return ThemeResponse.of(theme);
    }

    public Long createTheme(ThemeRequest themeRequest) {
        Theme theme = themeRequest.toEntity();
        return themeRepository.insertTheme(theme);
    }

    @Transactional
    public void changeTheme(Long themeId, ThemeRequest themeRequest) {
        Theme theme = themeRepository.getTheme(themeId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));
        themeRepository.changeTheme(theme.getId(), themeRequest.getName(), themeRequest.getDesc(), themeRequest.getPrice());
    }

    public void deleteTheme(Long themeId) {
        themeRepository.deleteTheme(themeId);
    }
}
