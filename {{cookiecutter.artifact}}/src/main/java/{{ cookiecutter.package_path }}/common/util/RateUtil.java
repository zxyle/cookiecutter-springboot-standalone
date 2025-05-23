package {{ cookiecutter.basePackage }}.common.util;

import java.text.NumberFormat;

/**
 * 百分比计算工具类
 */
public final class RateUtil {

    private RateUtil() {
    }

    /**
     * 设置精确到小数点后2位
     */
    public static final int DIGIT = 2;

    /**
     * 计算百分比
     * 4 / 20 = 20%
     *
     * @param divisor  除数
     * @param dividend 被除数
     * @return 带百分号的百分数
     */
    public static String cal(double divisor, double dividend, int digit) {
        if (dividend <= 0 || divisor <= 0) {
            return "0%";
        }

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(digit);
        // numberFormat.setMinimumFractionDigits(DIGIT);
        String result = numberFormat.format(divisor / dividend * 100);
        return result + "%";
    }

    public static String cal(double divisor, double dividend) {
        return cal(divisor, dividend, DIGIT);
    }

    public static String cal(float divisor, float dividend) {
        return cal(divisor, dividend, DIGIT);
    }

}

