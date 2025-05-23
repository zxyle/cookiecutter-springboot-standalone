package {{ cookiecutter.basePackage }}.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * 日期 format格式
 */
public final class DateFormatConst {

    private DateFormatConst() {
    }

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final DateTimeFormatter YYYYMMDD_DTF = DateTimeFormatter.ofPattern(YYYYMMDD);
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final DateTimeFormatter YYYY_MM_DD_DTF = DateTimeFormatter.ofPattern(YYYY_MM_DD);

    public static final String YYMMDD = "yyMMdd";
    public static final String YY_MM_DD = "yy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS_DTF = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
    public static final String YYYY_MM_DD_T_HH_MM_SS_ZZ = "yyyy-MM-dd'T'HH:mm:ssZZ";

    public static final String YYYY_M_D_CHN = "yyyy年M月d日";
    public static final DateTimeFormatter YYYY_MM_DD_CHN = DateTimeFormatter.ofPattern(YYYY_M_D_CHN);

    public static final String HH_MM_SS = "HH:mm:ss";

    public static final String TIMEZONE = "GMT+8";

}

