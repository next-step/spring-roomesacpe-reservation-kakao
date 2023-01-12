package nextstep.service;

import nextstep.domain.Theme;
import nextstep.dto.ThemeRequest;
import nextstep.dto.ThemeResponse;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.exception.InvalidRequestException;
import nextstep.repository.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) {
        if (themeDao.findByName(themeRequest.getName()).isPresent()) {
            throw new InvalidRequestException(ErrorCode.THEME_NAME_DUPLICATED);
        }
        Theme theme = new Theme(
                themeRequest.getName(),
                themeRequest.getDesc(),
                themeRequest.getPrice()
        );
        return themeDao.save(theme);
    }

    public ThemeResponse retrieveOne(Long id) {
        Optional<Theme> theme = themeDao.findById(id);
        if (theme.isEmpty()) {
            throw new InvalidRequestException(ErrorCode.THEME_NOT_FOUND);
        }
        return new ThemeResponse(theme.get());
    }

    public List<ThemeResponse> retrieveAll() {
        return themeDao.findAll()
                .stream()
                .map(ThemeResponse::new)
                .collect(Collectors.toList());
    }
}