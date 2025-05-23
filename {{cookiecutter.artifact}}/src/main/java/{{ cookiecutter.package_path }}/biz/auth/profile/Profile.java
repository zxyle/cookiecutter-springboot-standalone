package {{ cookiecutter.basePackage }}.biz.auth.profile;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.constant.RegexConst;
import {{ cookiecutter.basePackage }}.common.entity.BaseEntity;
import {{ cookiecutter.basePackage }}.common.enums.GenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import {{ cookiecutter.namespace }}.validation.constraints.PastOrPresent;
import {{ cookiecutter.namespace }}.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 用户资料
 */
@Data
@TableName("auth_profile")
@EqualsAndHashCode(callSuper = false)
public class Profile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 出生日期 yyyy-MM-dd
     *
     * @mock 1990-01-01
     */
    @PastOrPresent(message = "出生日期必须是过去或现在的日期")
    private LocalDate birthday;

    /**
     * 年龄
     *
     * @mock 30
     */
    @Range(min = 0, max = 120, message = "年龄必须在0-120之间")
    private Integer age;

    /**
     * 性别 男/女
     *
     * @mock 男
     */
    private String gender;

    /**
     * 头像url
     */
    @URL(message = "头像url格式不正确")
    private String avatar;

    /**
     * 昵称/名字/真实姓名（只用于展示）
     *
     * @mock 会飞的猪
     */
    @Length(max = 16, message = "昵称长度不能超过16个中英文字符")
    private String nickname;

    /**
     * QQ
     *
     * @mock 10001
     */
    @Length(min = 5, max = 10, message = "QQ长度不能超过10位数字")
    @Pattern(regexp = RegexConst.REGEX_QQ, message = "QQ由5-10位数字组成")
    private String qq;

    /**
     * 微信
     *
     * @mock wx123456
     */
    @Length(min = 6, max = 20, message = "微信长度必须在6-20个中英文字符之间")
    private String wechat;

    /**
     * 微博
     *
     * @mock weibo123456
     */
    @Length(min = 4, max = 30, message = "微博长度必须在4-30个中英文字符之间")
    private String weibo;

    /**
     * 简介
     *
     * @mock 一只会飞的猪
     */
    @Length(max = 255, message = "简介长度不能超过255个中英文字符")
    private String intro;

    /**
     * 地址
     *
     * @mock 杭州市西湖区
     */
    @Length(max = 255, message = "地址长度不能超过255个中英文字符")
    private String address;

    /**
     * 地区编码（参照area接口）
     *
     * @mock 330100
     */
    @Length(max = 6, message = "地区长度不能超过6位数字")
    @Pattern(regexp = "\\d+", message = "地区只能是数字")
    private String region;

    /**
     * 学校
     *
     * @mock 浙江大学
     */
    @Length(max = 32, message = "学校长度不能超过32个中英文字符")
    private String school;

    /**
     * 学历
     *
     * @mock 本科
     */
    @Length(max = 16, message = "学历长度不能超过16个中英文字符")
    private String education;

    /**
     * 专业
     *
     * @mock 计算机科学与技术
     */
    @Length(max = 32, message = "专业长度不能超过32个中英文字符")
    private String major;

    /**
     * 公司
     *
     * @mock 阿里巴巴
     */
    @Length(max = 32, message = "公司长度不能超过32个中英文字符")
    private String company;

    /**
     * 职位
     *
     * @mock 软件工程师
     */
    @Length(max = 32, message = "职位长度不能超过32个中英文字符")
    private String job;

    /**
     * 行业
     *
     * @mock 互联网
     */
    @Length(max = 32, message = "行业长度不能超过32个中英文字符")
    private String industry;

    /**
     * 职业
     *
     * @mock 软件工程师
     */
    @Length(max = 32, message = "职业长度不能超过32个中英文字符")
    private String profession;

    /**
     * 固定电话
     *
     * @mock 0571-12345678
     */
    @Length(max = 16, message = "固定电话长度不能超过16个中英文字符")
    private String telephone;

    public Integer getAge() {
        // 根据生日计算年龄
        if (birthday != null && age == null) {
            age = DateUtil.ageOfNow(Date.from(birthday.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        return age;
    }
}
