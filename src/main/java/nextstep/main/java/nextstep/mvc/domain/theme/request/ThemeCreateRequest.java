package nextstep.main.java.nextstep.mvc.domain.theme.request;

import lombok.Getter;

@Getter
public class ThemeCreateRequest {
    private String name;
    private String desc;
    private int price;
}