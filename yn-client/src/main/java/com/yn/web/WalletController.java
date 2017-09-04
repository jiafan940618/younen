package com.yn.web;

import com.yn.vo.re.ResultVOUtil;
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

import com.yn.model.Wallet;
import com.yn.service.WalletService;
import com.yn.utils.BeanCopy;
import com.yn.vo.WalletVo;

@RestController
@RequestMapping("/client/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Wallet findOne = walletService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody WalletVo walletVo) {
        Wallet wallet = new Wallet();
        BeanCopy.copyProperties(walletVo, wallet);
        walletService.save(wallet);
        return ResultVOUtil.success(wallet);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        walletService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(WalletVo walletVo) {
        Wallet wallet = new Wallet();
        BeanCopy.copyProperties(walletVo, wallet);
        Wallet findOne = walletService.findOne(wallet);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(WalletVo walletVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Wallet wallet = new Wallet();
        BeanCopy.copyProperties(walletVo, wallet);
        Page<Wallet> findAll = walletService.findAll(wallet, pageable);
        return ResultVOUtil.success(findAll);
    }
}
