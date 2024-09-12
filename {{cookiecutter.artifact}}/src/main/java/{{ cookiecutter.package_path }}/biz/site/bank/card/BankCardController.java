package {{ cookiecutter.basePackage }}.biz.site.bank.card;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import {{ cookiecutter.basePackage }}.common.controller.AuthBaseController;
import {{ cookiecutter.basePackage }}.common.response.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import {{ cookiecutter.namespace }}.validation.Valid;
import {{ cookiecutter.namespace }}.validation.constraints.Positive;
import java.util.List;


/**
 * 银行卡管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/site/bank")
@RequiredArgsConstructor
public class BankCardController extends AuthBaseController {

    final BankCardService thisService;


    /**
     * 银行卡列表查询
     */
    @GetMapping("/cards")
    public R<List<BankCard>> list() {
        LambdaQueryWrapper<BankCard> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(BankCard::getId, BankCard::getCardNo);
        wrapper.eq(BankCard::getUserId, getUserId());
        wrapper.orderByDesc(BankCard::getId);
        return R.ok(thisService.list(wrapper));
    }

    /**
     * 绑定银行卡
     */
    @PostMapping("/cards")
    public R<BankCard> add(@Valid @RequestBody BankCardAddRequest req) {
        BankCard entity = new BankCard();
        entity.setCardNo(req.getCardNo());
        entity.setUserId(getUserId());
        BankCard result = thisService.insert(entity);
        return R.ok(result);
    }

    /**
     * 解绑银行卡
     *
     * @param id 银行卡id
     */
    @DeleteMapping("/cards/{id}")
    public R<Void> delete(@PathVariable @Positive Integer id) {
        BankCard entity = thisService.findById(id, getUserId());
        if (entity == null) {
            return R.fail("删除失败，银行卡不存在");
        }

        boolean deleted = thisService.deleteById(id);
        return deleted ? R.ok("删除银行卡成功") : R.fail("删除银行卡失败");
    }

}