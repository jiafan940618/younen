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

import com.yn.model.Wallet;
import com.yn.service.WalletService;
import com.yn.utils.BeanCopy;
import com.yn.utils.ResultData;
import com.yn.vo.WalletVo;

@RestController
@RequestMapping("/server/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        ResultData<Wallet> resultData = new ResultData<>();
        Wallet findOne = walletService.findOne(id);
        resultData.setData(findOne);
        return resultData;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody WalletVo walletVo) {
        ResultData<Wallet> resultData = new ResultData<>();
        Wallet wallet = new Wallet();
        BeanCopy.copyProperties(walletVo, wallet);
        walletService.save(wallet);
        resultData.setData(wallet);
        return resultData;
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        ResultData<Wallet> resultData = new ResultData<>();
        walletService.delete(id);
        return resultData;
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(WalletVo walletVo) {
        ResultData<Wallet> resultData = new ResultData<>();
        Wallet wallet = new Wallet();
        BeanCopy.copyProperties(walletVo, wallet);
        Wallet findOne = walletService.findOne(wallet);
        resultData.setData(findOne);
        return resultData;
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(WalletVo walletVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ResultData<Page<Wallet>> resultData = new ResultData<>();
        Wallet wallet = new Wallet();
        BeanCopy.copyProperties(walletVo, wallet);
        Page<Wallet> findAll = walletService.findAll(wallet, pageable);
        resultData.setData(findAll);
        return resultData;
    }
}
