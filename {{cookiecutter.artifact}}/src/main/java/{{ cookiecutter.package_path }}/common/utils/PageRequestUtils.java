package {{ cookiecutter.basePackage}}.common.utils;

import {{ cookiecutter.basePackage}}.common.constant.Constant;

public class PageRequestUtils {

    public static Integer checkPageNum(Integer pageNum) {
        return (pageNum == null || pageNum <= 0) ? Constant.Page.DEFAULT_CURRENT : pageNum;
    }

    public static Integer checkPageSize(Integer pageSize) {
        return (pageSize == null || pageSize <= 0 || pageSize >= 50) ? Constant.Page.DEFAULT_PAGE_SIZE : pageSize;
    }
}
