package {{ cookiecutter.basePackage }}.biz.site.bank.card;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 银行卡号 数据访问类
 */
@Mapper
public interface BankCardMapper extends BaseMapper<BankCard> {

}