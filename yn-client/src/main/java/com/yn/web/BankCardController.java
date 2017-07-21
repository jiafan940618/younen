package com.yn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.BankCard;
import com.yn.service.BankCardService;
import com.yn.utils.BeanCopy;
import com.yn.vo.BankCardVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/client/bankCard")
public class BankCardController {
    @Autowired
    BankCardService bankCardService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        BankCard findOne = bankCardService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BankCardVo bankCardVo) {
        BankCard bankCard = new BankCard();
        BeanCopy.copyProperties(bankCardVo, bankCard);
        bankCardService.save(bankCard);
        return ResultDataVoUtil.success(bankCard);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        bankCardService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BankCardVo bankCardVo) {
        BankCard bankCard = new BankCard();
        BeanCopy.copyProperties(bankCardVo, bankCard);
        BankCard findOne = bankCardService.findOne(bankCard);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(BankCardVo bankCardVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        BankCard bankCard = new BankCard();
        BeanCopy.copyProperties(bankCardVo, bankCard);
        Page<BankCard> findAll = bankCardService.findAll(bankCard, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}
