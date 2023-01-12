package nextstep.dto;

import nextstep.domain.Theme;

public class ThemeResponse {
    private Long id;
    private String name;
    private String desc;
    private int price;

    public ThemeResponse(Theme theme) {
        this(
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
    }

    public ThemeResponse(Long id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }
}