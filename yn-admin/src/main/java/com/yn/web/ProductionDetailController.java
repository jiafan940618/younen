package com.yn.web;


import com.yn.model.ProductionDetail;
import com.yn.service.ProductionDetailService;
import com.yn.utils.BeanCopy;
import com.yn.vo.re.ProductionDetailVo;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Created by engrossing on 2017/12/20.
 */

@RestController
@RequestMapping("/server/productionDetail")

public class ProductionDetailController {


    @Autowired
    ProductionDetailService productionDetailService;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        ProductionDetail findOne = productionDetailService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody ProductionDetailVo productionDetailVo) {

        ProductionDetail productionDetail = new ProductionDetail();
        BeanCopy.copyProperties(productionDetailVo, productionDetail);
        productionDetailService.save(productionDetail);
        return ResultVOUtil.success(productionDetail);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        productionDetailService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(ProductionDetailVo productionDetailVo) {
        ProductionDetail banner = new ProductionDetail();
        BeanCopy.copyProperties(productionDetailVo, banner);
        ProductionDetail findOne = productionDetailService.findOne(banner);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(ProductionDetailVo productionDetailVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ProductionDetail banner = new ProductionDetail();
        BeanCopy.copyProperties(productionDetailVo, banner);
        Page<ProductionDetail> findAll = productionDetailService.findAll(banner, pageable);
        return ResultVOUtil.success(findAll);
    }




}
