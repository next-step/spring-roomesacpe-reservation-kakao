package nextstep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationRequestDTO {

    private final LocalDate date;

    private final LocalTime time;

    private final String name;

    @JsonProperty("theme_id")
    private final Long themeId;
}
