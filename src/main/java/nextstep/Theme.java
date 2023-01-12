package nextstep;

public class Theme {
    public static final Theme DEFAULT_THEME = Theme.of(0L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    private Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    private Theme(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static Theme of(Long id, String name, String desc, Integer price) {
        return new Theme(id, name, desc, price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getPrice() {
        return price;
    }
}
