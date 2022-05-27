package {{ cookiecutter.basePackage }}.biz.sys.service.config;

public class CaptchaConst {
    // 去掉1 l L 0 o O
    public static final String CHARACTERS = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ";

    public static final int LENGTH = 4;

    public static final int WIDTH = 160;
    public static final int HEIGHT = 70;

    public static final int FONT_SIZE = 45;
    public static final String FONT_NAMES = "宋体,楷体,微软雅黑";

}
