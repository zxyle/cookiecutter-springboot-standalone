package {{ cookiecutter.basePackage }}.biz.site.realname;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 证件类型枚举（用于实体类属性）
 */
@Getter
@ToString
@AllArgsConstructor
public enum IdTypeEnum {

    ID_CARD(0, "身份证"),
    PASSPORT(1, "护照"),
    HONG_KONG_AND_MACAO_RESIDENTS_TRAVEL_PERMIT(2, "港澳居民来往内地通行证"),
    TAIWAN_RESIDENTS_TRAVEL_PERMIT(3, "台湾居民来往大陆通行证"),
    ALIEN_RESIDENCE_PERMIT(4, "外国人居留证"),
    HONG_KONG_AND_MACAO_RESIDENTS_RESIDENCE_PERMIT(5, "港澳居民居住证"),
    TAIWAN_RESIDENTS_RESIDENCE_PERMIT(6, "台湾居民居住证");

    /**
     * 用于数据库存储的值
     */
    @EnumValue
    private final int code;

    /**
     * 用于前端展示的值
     */
    @JsonValue
    private final String desc;

}