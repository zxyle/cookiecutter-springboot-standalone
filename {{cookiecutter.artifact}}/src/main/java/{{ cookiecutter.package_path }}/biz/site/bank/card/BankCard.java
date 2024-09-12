package {{ cookiecutter.basePackage }}.biz.site.bank.card;

import com.baomidou.mybatisplus.annotation.TableName;
import {{ cookiecutter.basePackage }}.common.entity.RecordEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 银行卡号
 */
@Data
@TableName("site_bank_card")
@EqualsAndHashCode(callSuper = false)
public class BankCard extends RecordEntity {

    /**
     * 用户ID
     *
     * @mock 20
     */
    private Integer userId;

    /**
     * 银行卡号
     *
     * @mock 6622222222222222
     */
    private String cardNo;

}