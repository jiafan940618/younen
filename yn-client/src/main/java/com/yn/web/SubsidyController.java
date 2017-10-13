package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.yn.dao.CityDao;
import com.yn.dao.ProvinceDao;
import com.yn.dao.SubsidyDao;
import com.yn.model.Subsidy;
import com.yn.service.SubsidyService;
import com.yn.service.SystemConfigService;
import com.yn.utils.BeanCopy;
import com.yn.utils.ResultData;
import com.yn.vo.SubsidyVo;

@RestController
@RequestMapping("/client/subsidy")
public class SubsidyController {
	
	private static final Logger logger = LoggerFactory.getLogger(SubsidyController.class);
	
	@Autowired
	SystemConfigService systemConfigService;
    @Autowired
    SubsidyService subsidyService;
    @Autowired
    SubsidyDao subsidyDao;
    @Autowired
    ProvinceDao provinceDao;
    @Autowired
    CityDao cityDao;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Subsidy findOne = subsidyService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody SubsidyVo subsidyVo) {
        Subsidy subsidy = new Subsidy();
        BeanCopy.copyProperties(subsidyVo, subsidy);
        subsidyService.save(subsidy);
        return ResultVOUtil.success(subsidy);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        subsidyService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(SubsidyVo subsidyVo) {
        Subsidy subsidy = new Subsidy();
        BeanCopy.copyProperties(subsidyVo, subsidy);
        Subsidy findOne = subsidyService.findOne(subsidy);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(SubsidyVo subsidyVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Subsidy subsidy = new Subsidy();
        BeanCopy.copyProperties(subsidyVo, subsidy);
        Page<Subsidy> findAll = subsidyService.findAll(subsidy, pageable);
        return ResultVOUtil.success(findAll);
    }
    
	/**
	 * 模拟收益
	 * 
	 * @param subsidy
	 * @param sqm
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/monishouyi")
	public Object findByType(Subsidy subsidy, String sqm, com.yn.model.Page<Subsidy> page) {
		ResultData<Object> resultData = new ResultData<Object>();
		/*subsidy.setCityId(213L);
		subsidy.setType(1);
		sqm = "45";*/
	logger.info("传递的type:----- ---- --- ---- ---"+subsidy.getType());
	logger.info("传递的CityId:----- ---- --- ---- ---"+subsidy.getCityId());	
	logger.info("传递的sqm:----- ---- --- ---- ---"+sqm);
	
		Map<String, String> map = systemConfigService.getlist();

		DecimalFormat df = new DecimalFormat("#0.00");

		Subsidy sobe = subsidyService.findOne(subsidy);

		if (sobe != null) {
			// 植树参数
			Double plant_trees_prm = Double.valueOf(map.get("plant_trees_prm"));
			// co2减排参数
			Double CO2_prm = Double.valueOf(map.get("CO2_prm"));
			// SO减排参数
			Double SO_prm = Double.valueOf(map.get("SO_prm"));

			// 出售价格（元/度）
			Double sellPrice = sobe.getSellPrice();
			// 衰减率，0.8代表0.8%
			Double dampingRate = Double.valueOf(map.get("damping_rate"));
			// 按安装面积的80%计算装机容量
			Double area_capacity_per = Double.valueOf(map.get("area_capacity_per"));
			// 每平米发电多少瓦
			Double sqm_electric = Double.valueOf(map.get("sqm_electric"));
			// 每瓦多少钱
			Double watt_price = Double.valueOf(map.get("watt_price"));
			// 装机容量(KW) = 【安装面积（㎡） * 0.8（按安装面积的80%计算装机容量） * 每平米发电多少瓦(155.49)】 / 1000
			Double totalInstallCapacity = (Double.valueOf(sqm) * Double.valueOf(area_capacity_per) * sqm_electric)
					/ 1000;
			// 总造价(元) = 装机容量(KW) * 每瓦多少钱(11000)
			Double totalprice = totalInstallCapacity * watt_price;
			// 年发电量(kw·h) = 装机容量(KW) * 地区的年日照数(小时)
			Double yearTotalWatt = totalInstallCapacity * Double.valueOf(sobe.getSunCount());

			// 总的国家补贴(元)=年发电量*国家补贴参数（元/度）*国家补贴年限
			Double countrySub = Double.valueOf(map.get("country_subsidy")); //国家补贴参数（元/度）
			Double countrySubYear = Double.valueOf(map.get("country_subsidy_year")); //国家补贴年限
			Double countrySubTotal = countrySub * countrySubYear * yearTotalWatt;

			// 总的地方补贴(元)=年发电量*地方补贴参数（元/度）*地方补贴年限
			Double difangSub = sobe.getAreaSubsidyPrice();   
			int difangSubYear = sobe.getAreaSubsidyYear(); 
			Double difangSubTotal = difangSub * difangSubYear * yearTotalWatt;

			// 总的优能补贴(元)=年发电量*优能补贴参数（元/度）*优能补贴年限 ，说明：该地区的售电价格低于10.5元/w时没有优能补贴
			Double unSubTotal = 0d;   //总的优能补贴
			if (sobe.getUnSubsidyPrice() != null) {
				if (sellPrice > 10.5) {    
					Double unSub = sobe.getUnSubsidyPrice();//优能补贴参数（元/度）
					int unSubYear = sobe.getUnSubsidyYear();  //优能补贴年限
					unSubTotal = unSub * unSubYear * yearTotalWatt;
				}
			}
			// 一次性补贴(元)=装机容量*一次性补贴参数（元/kw）
			Double initialsubsidy = 0d;   //一次性补贴(元)
			
			if (sobe.getInitSubsidyPrice() != null) {
				initialsubsidy = sobe.getInitSubsidyPrice() * totalInstallCapacity; //装机容量
			}

			// 总补贴(元)=总的国家补贴+总的地方补贴+总的优能补贴+一次性补贴
			Double totalSub = countrySubTotal + difangSubTotal + unSubTotal + initialsubsidy;

			// 20年总节省(元)=年发电量*自用率*用电单价（元/度）*20(年)
			Double economicTotal = yearTotalWatt * sobe.getUsePrice() * (1 - sobe.getSellProportion()) * 20;
			// 20年总卖出(元)=年发电量*出售率*售电单价（元/度）*20(年)
			Double sellTotal = yearTotalWatt * sobe.getSellProportion() * sobe.getSellPrice() * 20;
			// 20年总收益 = (20年总节省电费+20年总卖出电费+总补贴) * (1-衰减率*20)
			Double twentyYearTotalIncome = (economicTotal + sellTotal + totalSub) * (1 - (dampingRate / 100) * 20);
			// 每年发电收益=20年总收益 / 20(年)
			Double everyYearTotalIncome = twentyYearTotalIncome / 20;

			// 地方补贴结束前的总收益（假设地方补贴年限是5年） = 五年省下的电费+五年卖出的电费+五年国家补贴+五年地方补贴+总的优能补贴
			Double beforeEndSubsidy = yearTotalWatt * sobe.getUsePrice() * (1 - sobe.getSellProportion()) * sobe.getAreaSubsidyYear()
					+ yearTotalWatt * sobe.getSellProportion() * sobe.getSellPrice() * sobe.getAreaSubsidyYear() + difangSubTotal
					+ unSubTotal + countrySub * yearTotalWatt * sobe.getAreaSubsidyYear();

			Double incomePerMax = 0d;
			if (beforeEndSubsidy == 0) { // 如果没有地方补贴
				
			} else { // 最大年收益率=(地方补贴结束前的总收益/地方补贴年限/总造价)*(1-衰减率*地方补贴年限）
				incomePerMax = (beforeEndSubsidy / sobe.getAreaSubsidyYear() / totalprice) * 100 * (1 - (dampingRate / 100) * sobe.getAreaSubsidyYear());
			}

			// 最小年收益率 = (20年总收益/20(年)) / 总造价
			Double incomePerMin = ((twentyYearTotalIncome / 20) / totalprice) * 100;

			Map<String, Object> rm = new HashMap<>();
			// 成本
			rm.put("totalprice", df.format(totalprice)); // 工程总造价
			rm.put("totalInstallCapacity", df.format(totalInstallCapacity)); // 机容量
			rm.put("sunCount", df.format(Double.valueOf(sobe.getSunCount()))); // 年日照数
			rm.put("yearTotalWatt", df.format(yearTotalWatt)); // 年发电量
			rm.put("treeNum", df.format(yearTotalWatt * plant_trees_prm)); // 植树棵树
																			// =
																			// 发电量
																			// *
																			// 植树参数
			rm.put("CO2Num", df.format(yearTotalWatt * CO2_prm)); // 减排二氧化碳多少吨
			rm.put("SONum", df.format(yearTotalWatt * SO_prm)); // 减排so
			// 收益
			rm.put("twentyYearTotalIncome", df.format(twentyYearTotalIncome));
			rm.put("yearIncome", df.format(everyYearTotalIncome)); // 发电收益(元/年)
			rm.put("selfuse", sobe.getSellPrice()); // 当地电价
			rm.put("guojiabutie", countrySub); // 国家补贴(元/度)
			rm.put("subsidy", sobe.getAreaSubsidyPrice()); // 地方补贴(元/度)
			rm.put("unlsubsidy", sobe.getUnSubsidyPrice()); // 优能补贴(元/度)
			rm.put("incomePerMax", String.valueOf(df.format(incomePerMax)) + "%");//最大收益率
			rm.put("incomePerMin", String.valueOf(df.format(incomePerMin)) + "%");  //最小收益率

			resultData.setData(rm);
		} else {
			Map<String, Object> rm = new HashMap<>();
			// 成本
			rm.put("totalprice", 0); // 工程总造价
			rm.put("totalInstallCapacity", 0);
			rm.put("sunCount", 0); // 年日照数
			rm.put("yearTotalWatt", 0); // 年发电量
			rm.put("treeNum", 0); // 植树棵树
			rm.put("CO2Num", 0); // 减排二氧化碳多少吨

			// 收益
			rm.put("twentyYearTotalIncome", 0);
			rm.put("yearIncome", 0); // 发电收益(元/年)
			rm.put("selfuse", 0); // 当地电价
			rm.put("guojiabutie", 0); // 国家补贴(元/度)
			rm.put("subsidy", 0); // 地方补贴(元/度)
			rm.put("unlsubsidy", 0); // 优能补贴(元/度)
			rm.put("incomePerMax", 0 + "%");
			rm.put("incomePerMin", 0 + "%");
			resultData.setData(rm);
		}

		return resultData;
	}
    
}