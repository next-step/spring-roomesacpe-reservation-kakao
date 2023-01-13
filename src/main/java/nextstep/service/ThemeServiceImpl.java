package nextstep.service;

import lombok.RequiredArgsConstructor;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import nextstep.dto.ThemeResponseDto;
import nextstep.mapstruct.ThemeMapper;
import nextstep.repository.ThemeRepository;

@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    @Override
    public Theme createTheme(ThemeCreateDto themeCreateDto) {
        return themeRepository.save(themeCreateDto);
    }

    @Override
    public boolean editTheme(ThemeEditDto themeEditDto) {
        return themeRepository.update(themeEditDto) != 0;
    }

    @Override
    public ThemeResponseDto findTheme(Long id) {
        Theme theme = themeRepository.findById(id).orElse(null);
        if(theme == null){
            return null;
        }
        return ThemeMapper.INSTANCE.themeToThemeResponseDto(theme);
    }

    @Override
    public void deleteById(Long id) {
        themeRepository.deleteById(id);
    }
}
