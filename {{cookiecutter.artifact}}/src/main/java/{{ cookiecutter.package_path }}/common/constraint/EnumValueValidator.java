package {{ cookiecutter.basePackage }}.common.constraint;

import {{ cookiecutter.namespace }}.validation.ConstraintValidator;
import {{ cookiecutter.namespace }}.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Integer> {

    private EnumValue enumValue;

    @Override
    public void initialize(EnumValue enumValue) {
        this.enumValue = enumValue;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Class<? extends ValueEnum> enumClass = enumValue.enumClass();
        return Arrays.stream(enumClass.getEnumConstants()).anyMatch(i -> Objects.equals(i.getValue(), value));
    }

}
