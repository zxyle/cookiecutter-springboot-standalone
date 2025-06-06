package {{ cookiecutter.basePackage }}.common.request;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import {{ cookiecutter.basePackage }}.common.request.sort.Sortable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import {{ cookiecutter.namespace }}.validation.constraints.AssertTrue;
import {{ cookiecutter.namespace }}.validation.constraints.Pattern;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 带时间范围/模糊搜索/多字段排序/文件导出的请求对象，所有查询请求对象都应该继承此类
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class ConditionRequest extends BaseRequest {

    protected static final Map<Class<?>, Field[]> FIELD_CACHE = new ConcurrentHashMap<>();
    protected static final Map<Class<?>, List<String>> COLUMN_CACHE = new ConcurrentHashMap<>();
    protected static final List<String> DEFAULT_COLUMNS = Arrays.asList("id", "create_time", "update_time");
    protected static final String DEFAULT_ORDER = "asc";  // 默认排序方式
    protected static final String DEFAULT_COLUMN = "id";  // 默认排序字段

    /**
     * 是否导出Excel
     *
     * @mock false
     */
    protected Boolean export;

    /**
     * 多字段排序, 格式为：字段名:排序方式
     *
     * @mock name:asc,age:desc
     */
    @Pattern(regexp = "^[a-zA-Z:,]+$", message = "排序字段格式错误，只能包含英文字母、冒号、逗号")
    protected String sort;

    /**
     * 开始时间 yyyy-MM-dd HH:mm:ss
     *
     * @mock 2021-01-01 00:00:00
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime startTime;

    /**
     * 结束时间 yyyy-MM-dd HH:mm:ss
     *
     * @mock 2021-12-31 23:59:59
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime endTime;

    /**
     * 开始日期 yyyy-MM-dd
     *
     * @mock 2021-01-01
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate startDate;

    /**
     * 结束日期 yyyy-MM-dd
     *
     * @mock 2021-12-31
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate endDate;

    /**
     * 搜索关键字(支持模糊查询)
     * 较长的关键词可能会影响查询性能
     *
     * @mock 123
     */
    @Length(min = 2, max = 64, message = "搜索关键字长度必须在2-64字符之间")
    protected String keyword;

    /**
     * 是否调用过排序，避免重复排序
     *
     * @ignore
     */
    protected Boolean calledSort = false;

    /**
     * 根据请求类构建出查询条件
     */
    public <T> LambdaQueryWrapper<T> getWrapper() {
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        Field[] fields = FIELD_CACHE.computeIfAbsent(this.getClass(), Class::getDeclaredFields);
        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(QueryField.class))
                .forEach(field -> handleField(field, wrapper));

        if (!calledSort) {
            List<OrderItem> orderItems = getOrderItems();
            if (CollectionUtils.isNotEmpty(orderItems)) {
                for (OrderItem orderItem : orderItems) {
                    wrapper.orderBy(true, orderItem.isAsc(), orderItem.getColumn());
                }
            }
            calledSort = true;
        }

        // QueryWrapper转换成LambdaQueryWrapper
        return wrapper.lambda();
    }

    /**
     * 处理单个字段
     */
    protected <T> void handleField(Field field, QueryWrapper<T> wrapper) {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(this);
            if (fieldValue == null) {
                return;
            }

            QueryField annotation = field.getAnnotation(QueryField.class);
            if (annotation.skip()) {
                return;
            }

            String fieldName = getFieldName(field, annotation);
            Operator operator = annotation.operator();
            setQueryWrapper(operator, fieldName, fieldValue, wrapper);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
        }
    }

    /**
     * 获取字段名称
     */
    protected String getFieldName(Field field, QueryField annotation) {
        String fieldName = camelToUnderline(field.getName());
        if (StringUtils.isNotBlank(annotation.column())) {
            fieldName = annotation.column();
        }
        return fieldName;
    }

    /**
     * 设置查询条件
     *
     * @param operator   操作符
     * @param fieldName  字段名称
     * @param fieldValue 属性值
     * @param wrapper    查询条件
     */
    protected <T> void setQueryWrapper(Operator operator, String fieldName, Object fieldValue, QueryWrapper<T> wrapper) {
        switch (operator) {
            case EQ:
                wrapper.eq(fieldName, fieldValue);
                break;
            case NE:
                wrapper.ne(fieldName, fieldValue);
                break;
            case GE:
                wrapper.ge(fieldName, fieldValue);
                break;
            case LE:
                wrapper.le(fieldName, fieldValue);
                break;
            case GT:
                wrapper.gt(fieldName, fieldValue);
                break;
            case LT:
                wrapper.lt(fieldName, fieldValue);
                break;
            case LIKE:
                wrapper.like(fieldName, fieldValue);
                break;
            case LEFT_LIKE:
                wrapper.likeLeft(fieldName, fieldValue);
                break;
            case RIGHT_LIKE:
                wrapper.likeRight(fieldName, fieldValue);
                break;
            case NOT_LIKE:
                wrapper.notLike(fieldName, fieldValue);
                break;
            case IN:
                if (fieldValue instanceof Collection) {
                    wrapper.in(fieldName, (Collection) fieldValue);
                }
                if (fieldValue instanceof Object[]) {
                    wrapper.in(fieldName, (Object[]) fieldValue);
                }
                if (fieldValue instanceof String) {
                    String[] split = fieldValue.toString().split(",");
                    wrapper.in(fieldName, Arrays.asList(split));
                }
                break;
            case NOT_IN:
                if (fieldValue instanceof Collection) {
                    wrapper.notIn(fieldName, (Collection) fieldValue);
                }
                if (fieldValue instanceof Object[]) {
                    wrapper.notIn(fieldName, (Object[]) fieldValue);
                }
                if (fieldValue instanceof String) {
                    String[] split = fieldValue.toString().split(",");
                    wrapper.notIn(fieldName, Arrays.asList(split));
                }
                break;
            case BETWEEN:
                if (fieldValue instanceof Object[]) {
                    Object[] fv = (Object[]) fieldValue;
                    assert fv.length == 2;
                    wrapper.between(fieldName, fv[0], fv[1]);
                }

                if (fieldValue instanceof String) {
                    String[] split = fieldValue.toString().split(",");
                    assert split.length == 2;
                    wrapper.between(fieldName, split[0], split[1]);
                }
                break;
            case NOT_BETWEEN:
                if (fieldValue instanceof Object[]) {
                    Object[] fv = (Object[]) fieldValue;
                    wrapper.notBetween(fieldName, fv[0], fv[1]);
                }
                if (fieldValue instanceof String) {
                    String[] split = fieldValue.toString().split(",");
                    assert split.length == 2;
                    wrapper.notBetween(fieldName, split[0], split[1]);
                }
                break;
            case IS_NULL:
                wrapper.isNull(fieldName);
                break;
            case IS_NOT_NULL:
                wrapper.isNotNull(fieldName);
                break;
            default:
                log.warn("不支持的查询类型：{}", operator);
                break;
        }
    }

    /**
     * 创建排序项
     *
     * @param fieldName 字段名
     * @param isAsc     是否升序
     */
    protected OrderItem createOrderItem(String fieldName, boolean isAsc) {
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(camelToUnderline(fieldName));
        orderItem.setAsc(isAsc);
        return orderItem;
    }

    /**
     * 获取实体类的所有属性
     *
     * @param clazz 实体类
     */
    protected List<String> getColumns(Class<?> clazz) {
        // Sortable("asc|desc") 设置允许的排序号
        // Sortable fieldAnnotation = field.getAnnotation(Sortable.class);
        List<String> columns = new ArrayList<>(DEFAULT_COLUMNS);
        Field[] fields = FIELD_CACHE.computeIfAbsent(this.getClass(), Class::getDeclaredFields);
        columns.addAll(Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Sortable.class))
                .map(Field::getName)
                .map(ConditionRequest::camelToUnderline)
                .{% if cookiecutter.javaVersion in ["17", "21"] %}toList(){% else %}collect(Collectors.toList()){% endif %});
        return columns;
    }

    protected List<OrderItem> getOrderItems() {
        if (StringUtils.isBlank(sort)) {
            // 默认排序
            OrderItem orderItem = createOrderItem(DEFAULT_COLUMN, false);
            return Collections.singletonList(orderItem);
        }

        // 排序字段处理
        List<String> columns = COLUMN_CACHE.computeIfAbsent(this.getClass(), this::getColumns);
        return new ArrayList<>(Arrays.stream(sort.split(","))
                .map(str -> str.split(":"))
                .map(split -> createOrderItem(split[0], split.length == 1 ||
                        StringUtils.equalsIgnoreCase(split[1], DEFAULT_ORDER)))
                .filter(orderItem -> columns.contains(orderItem.getColumn()))  // 过滤掉不存在或不支持排序的字段
                .collect(Collectors.toMap(OrderItem::getColumn, o -> o, (o1, o2) -> o1))  // 去重
                .values());
    }

    /**
     * 驼峰转下划线
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        str = str.replaceAll(regex, replacement);
        return str.toLowerCase();
    }

    /**
     * 校验结束时间是否晚于开始时间
     */
    @AssertTrue(message = "结束时间必须晚于开始时间")
    private boolean isEndTimeValid() {
        return startTime == null || endTime == null || !endTime.isBefore(startTime);
    }

    /**
     * 校验结束日期是否晚于开始日期
     */
    @AssertTrue(message = "结束日期必须晚于开始日期")
    private boolean isEndDateValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}
