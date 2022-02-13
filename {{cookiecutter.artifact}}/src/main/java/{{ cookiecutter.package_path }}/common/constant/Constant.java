package {{ cookiecutter.basePackage }}.common.constant;

public class Constant {

    public static class Response {
        public static final String SUCCESS_CODE = "0";
        public static final String ERROR_CODE = "999";
        public static final String SUCCESS_MESSAGE = "成功";
        public static final String ERROR_MESSAGE = "失败";
        public static final String ERROR_EXCEPTION = "服务异常";
        public static final String FORBIDDEN_MESSAGE = "权限不足";
    }

    public static final class Jwt {
        public static final String SECRET = "{{ random_ascii_string(43) }}";
    }

    public static class ErrorCode {
        public static class Mould {
            public static final String COMMON = "001";
            public static final String MERCHANT = "002";    // 商户
            public static final String PAYMENT = "003";     // 支付
            public static final String ACCOUNT = "004";     // 账户
            public static final String BILL = "005";        // 账单
            public static final String MESSAGE = "007";     // 消息
            public static final String DATAEXTERNAL = "012";// 外部数据服务
            public static final String DATALAW = "013";     // 外部数据服务
            public static final String MONITOR = "014";     // 监控
            public static final String SCENE = "016";       // 场景
            public static final String ANALYSE = "017";     // 分析工具
            public static final String REPORT = "018";      //

            public static final String BASE = "019";
            public static final String TAX = "022";         //
        }

        public static class Rank {
            public static final String SYSTEM = "01";       // 系统
            public static final String NETWORK = "02";      // 网络
            public static final String BUSINESS = "03";     // 业务
        }
    }

}
