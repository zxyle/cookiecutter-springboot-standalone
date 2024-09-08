package {{ cookiecutter.basePackage }}.biz.site.bank.card;

import {{ cookiecutter.basePackage }}.common.request.BaseRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.LuhnCheck;

import {{ cookiecutter.namespace }}.validation.constraints.NotBlank;

/**
 * 银行卡号 请求类
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BankCardAddRequest extends BaseRequest {

    /**
     * 银行卡号
     *
     * @mock 6622222222222222
     */
    @Length(max = 20, message = "银行卡号长度不能超过20个字符")
    @NotBlank(message = "银行卡号不能为空")
    @LuhnCheck(message = "银行卡号校验失败")
    private String cardNo;

}