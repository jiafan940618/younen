package com.yn.web;


import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yn.dao.CityDao;
import com.yn.dao.DevideDao;
import com.yn.dao.ProvinceDao;
import com.yn.dao.UserDao;
import com.yn.model.Brand;
import com.yn.model.City;
import com.yn.model.Decideinfo;
import com.yn.model.Devide;
import com.yn.model.Inverter;
import com.yn.model.Province;
import com.yn.model.Server;
import com.yn.model.SolarPanel;
import com.yn.model.User;
import com.yn.model.newPage;
import com.yn.service.BrandService;
import com.yn.service.DecideinfoService;
import com.yn.service.InverterService;
import com.yn.service.ServerService;
import com.yn.service.SolarPanelSerice;
import com.yn.service.SolarPanelService;
import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.MD5Util;
import com.yn.utils.ResultData;
import com.yn.utils.RongLianSMS;
import com.yn.vo.NewUserVo;
import com.yn.vo.QualificationsVo;
import com.yn.vo.ServerVo;
import com.yn.vo.SolarPanelVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;


@RestController
@RequestMapping("/client/server")
public class ServerController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServerController.class);
	@Autowired 
	SolarPanelService solarPanelService;
	@Autowired
	SolarPanelSerice solarService;
	@Autowired
	InverterService inverterService;
	@Autowired
	private BrandService brandService;
	@Autowired
    private UserService userService;
	@Autowired
    UserDao userDao;
	@Autowired
	DevideDao devideDao;
	@Autowired
	private CityDao city;
	@Autowired
	private ProvinceDao province;
    @Autowired
    ServerService serverService;
    @Autowired
    UserService userservice;
    @Autowired
    DecideinfoService  decideinfoService;
    
    
	@RequestMapping(value = "/select", method = { RequestMethod.POST })
	@ResponseBody
	public Object findOne(Long id) {
		Server findOne = serverService.findOne(id);
		return ResultVOUtil.success(findOne);
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object save(@RequestBody ServerVo serverVo) {
		Server server = new Server();
		BeanCopy.copyProperties(serverVo, server);
		serverService.save(server);
		return ResultVOUtil.success(server);
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Object delete(Long id) {
		serverService.delete(id);
		return ResultVOUtil.success();
	}

	@ResponseBody
	@RequestMapping(value = "/findOne", method = { RequestMethod.POST })
	public Object findOne(ServerVo serverVo) {
		Server server = new Server();
		BeanCopy.copyProperties(serverVo, server);
		Server findOne = serverService.findOne(server);
		return ResultVOUtil.success(findOne);
	}

	@RequestMapping(value = "/findAll", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findAll(ServerVo serverVo,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Server server = new Server();
		BeanCopy.copyProperties(serverVo, server);
		Page<Server> findAll = serverService.findAll(server, pageable);
		return ResultVOUtil.success(findAll);
	}

	@RequestMapping(value = "/findLocalServer", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findLocalServer(ServerVo serverVo,
			@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Server server = new Server();
		BeanCopy.copyProperties(serverVo, server);
		Page<Server> findLocalServer = serverService.findLocalServer(server, pageable);
		return ResultVOUtil.success(findLocalServer);
	}
	
	@ResponseBody
	@RequestMapping(value = "/login")
	public ResultData<Object> login(UserVo userVo, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		 
		 User user =null; 
	        user = userService.findByPhone(userVo.getPhone());
	        if(user == null){
	        	 user = userService.findByEamil(userVo.getPhone()); 
	        	 if(user == null){
	        		 user = userService.findByUserName(userVo.getPhone()); 
	        	 }
	        }
	        if (user == null) {
	        	logger.info("----- --- ----- 该用户不存在！");
	            return ResultVOUtil.error(777, Constant.NO_THIS_USER);
	        } else if (!user.getPassword().equals(MD5Util.GetMD5Code(userVo.getPassword()))) {
	        	logger.info("----- --- ----- 密码错误！");
	            return ResultVOUtil.error(777, Constant.PASSWORD_ERROR);
	        }
	        
	        Server server = new Server();
	        server.setUserId(user.getId());
	         
	        server = serverService.findOne(server);
	        if(null == server){
	        	logger.info("----- --- ----- 抱歉,用户未注册服务商的身份");
	            return ResultVOUtil.error(777, Constant.PASSWORD_ERROR);
	        }


	        user.setToken(userService.getToken(user));
	        userService.updateToken(user);
	       
	        user.setPassword(null);

	        httpSession.setAttribute("server", server);
	        
	        logger.info("---- ---- --- --- - --- - --- ----结束");
	        
	        return ResultVOUtil.success(server);

		
	}
    
    /**短信验证用的是用户注册的短信验证*/
    @ResponseBody
	@RequestMapping(value = "/register1")
	public ResultData<Object> register1(User user,Server server,String code, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
	
		if (user.getPhone() == null) {
		
			return ResultVOUtil.error(777, Constant.PHONE_NULL);
		}
		//对企业邮箱的写一个正则表达式
		// 如果短信验证码超过了5分钟
		Long code4serverRegTime = (Long)httpSession.getAttribute("code4serverTime");
		if (code4serverRegTime==null) {
			 return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		Long spaceTime = System.currentTimeMillis() - code4serverRegTime;
		if(spaceTime > 300000){
			httpSession.setAttribute("code4serverReg",null);
			 return ResultVOUtil.error(777, Constant.CODE_AGAIN);
		}

		Object attribute2 = httpSession.getAttribute("code4server");
		if (attribute2==null) {
			return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		if (!code.equals(attribute2)) {
			return ResultVOUtil.error(777, Constant.CODE_ERROR);
		}
		User user2 =new User();
		user2.setPhone(user.getPhone());
		// userBiz.selectOneByExample(user2);
		 
		User selectByPhone = userservice.findByPhone(user.getPhone());
		if ( null != selectByPhone ) {

			return ResultVOUtil.error(777, Constant.PHONE_EXIST);	
		}
		httpSession.setAttribute("server", server);
		httpSession.setAttribute("user", user);

		
		
		return ResultVOUtil.success(user);
	}
    
    /**
     * 商户短信验证码的验证
     * 第一步
     * 1、添加服务商电话的参数放在一起处理
     * */
   @ResponseBody
 	@RequestMapping(value = "/checkCode")
 	public ResultData<Object> checkCode(String code,String phone,HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
 	  
 		User user = userService.findByPhone(phone);
 	  
 	  if( null== phone || phone.equals("")){  
 		 logger.info("--- -- --- 电话不能为空！");
 		 return ResultVOUtil.error(777, Constant.PHONE_NULL);
 	  }
 	  
 	  if(null !=user){
 		 logger.info("--- --- -- - 电话号码已存在！");
 		 return ResultVOUtil.error(777, Constant.PHONE_EXIST);
 	  }
 	 

 		String code4serverReg = (String)httpSession.getAttribute("code4server");
 		// 检测短信验证码是否过期 ，待定
 		Long codeTime = (Long)httpSession.getAttribute("code4serverTime");
 		if (codeTime==null) {
 			 logger.info(" --- ---- 短信验证不能为空！");
 			return ResultVOUtil.error(777, Constant.CODE_NULL);
 		}
 		
 		Long spaceTime = System.currentTimeMillis() - codeTime;
 		if(spaceTime > 100000){
 			httpSession.setAttribute("code4registerTime",null);
 			 logger.info("---- -- -- -短信验证码已失效！");
 			
 			return ResultVOUtil.error(777, Constant.CODE_AGAIN);
 		}
 		
 		
 		if(StringUtils.isEmpty(code4serverReg)){
 			 logger.info(" -- --- ---- 短信验证不能为空！");
 			
 			return ResultVOUtil.error(777, Constant.CODE_NULL);
 		}
 		if(!code4serverReg.equals(code)){
 			 logger.info(" --- ---- 短信验证码已失效！");
 			
 			return ResultVOUtil.error(777, Constant.CODE_AGAIN);
 		}
 		

 		return ResultVOUtil.success(null);
 	}
    
   /**
    * 服务商的短信请求
    * **/
   @ResponseBody
   @RequestMapping(value="/serverInf")
   public  ResultData<Object> getRongLian(@RequestParam(value = "phone", required = true) String phone, HttpSession httpSession,HttpServletResponse response){
	   
	  
	   
	if(null == phone || phone.equals("")){
		
		logger.info(" ------ 手机号不能为空");
		return ResultVOUtil.error(777, Constant.PHONE_NULL);
	}
	
	User user = userService.findByPhone(phone);
	
	if(user != null){
	
		logger.info(" ------ 该手机号已存在");
		return ResultVOUtil.error(777, Constant.PHONE_EXIST);
	}

	 /** 测试的短信验证.*/
		//String servercode = Propertie.getProperty("code");
	 /** 拿到短信验证码*/  
	 String servercode =  RongLianSMS.sendCode(phone);
	 
	 if(null == servercode){
		 logger.info(" ------ 短信发送失败！");
		return ResultVOUtil.error(777, Constant.CODE_FALSE);
	 }
	 
	 Long code4registerTime = System.currentTimeMillis();
	 
	 httpSession.setAttribute("code4serverTime", code4registerTime);
	
	 httpSession.setAttribute("code4server", servercode);
	 
	 httpSession.setAttribute("phone", phone);

	 
	 logger.info("短信验证码： ----- "+servercode);
	 

	return ResultVOUtil.success(null);
	 
   }	 
   /** 
    * 服务商第二步的处理添加数据
    * */
   @ResponseBody
	@RequestMapping(value = "/register_Server")
	public ResultData<Object> register2(String server,HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
	
		ServerVo serverVo = JSON.parseObject(server, ServerVo.class);
		/** 此时这里的参数与前端页面对应，测试再说，同时服务商的电话，定为法人代表电话*/
		serverVo.setPassword(MD5Util.GetMD5Code(serverVo.getPassword()));
		serverVo.setLegalPersonPhone(serverVo.getLegalPersonPhone());
		serverVo.setLegalPerson(serverVo.getLegalPerson());
		// 判断该手机号是否已经被注册
		Server cs = new Server();
		cs.setLegalPersonPhone(serverVo.getLegalPersonPhone());
		Server selectByPhone = serverService.findOne(cs);
		if (selectByPhone != null) {
			logger.info(" ---- ---- --- --- 电话已被注册");
			return ResultVOUtil.error(777, Constant.PHONE_EXIST);
		}
		if(serverVo.getCompanyAddress().length() > 250){
			logger.info(" ---- ---- --- --- 城市字段不能过长");
			
			return ResultVOUtil.error(777, Constant.CITY_LONG);
		}

		 Server server01= new Server();
		 BeanCopy.copyProperties(serverVo, server01);
		 
		 String log = (String) httpSession.getAttribute("finaltime");
		   
		 if(null != log && !log.equals("")){
			 server01.setCompanyLogo(log);
		 }
	 
		  String  qualificationsImgUrl =(String)  httpSession.getAttribute("qualificationsImgUrl");
		  String  businessLicenseImgUrl =(String)  httpSession.getAttribute("businessLicenseImgUrl");
		  
		  if(null == qualificationsImgUrl){
			  logger.info(" ---- ---- --- --- 资质照片不能为空!");
			  return ResultVOUtil.error(777, Constant.ficationsImgUrl_NULL);
		  }
		  if(null == businessLicenseImgUrl){
			  logger.info(" ---- ---- --- --- 营业执照不能为空!");
			  return ResultVOUtil.error(777, Constant.LicenseImgUrl_NULL);
		  }

		logger.info("--- ---- ---- ---- ---- ---- --- registeredDtm "+server01.getRegisteredDtm());
		logger.info("--- ---- ---- ---- ---- ---- --- email "+serverVo.getEmail());
		logger.info("--- ---- ---- ---- ---- ---- --- password "+serverVo.getPassword());
		logger.info("--- ---- ---- ---- ---- ---- --- 公司名称：companyName "+server01.getCompanyName());
		logger.info("--- ---- ---- ---- ---- ---- --- 公司地址：companyAddress "+server01.getCompanyAddress());
		logger.info("--- ---- ---- ---- ---- ---- --- 注册资金：registeredCapital "+server01.getRegisteredCapital());
		logger.info("--- ---- ---- ---- ---- ---- --- 法定代表人：legalPerson "+server01.getLegalPerson());
		logger.info("--- ---- ---- ---- ---- ---- --- 法定代表人电话：legalPersonPhone "+server01.getLegalPersonPhone());
		logger.info("--- ---- ---- ---- ---- ---- --- 业务员电话：salesmanPhone "+server01.getSalesmanPhone());
		logger.info("--- ---- ---- ---- ---- ---- --- 公司资产：companyAssets "+server01.getCompanyAssets());
		logger.info("--- ---- ---- ---- ---- ---- --- 资质证明：aptitude "+server01.getAptitude());
		logger.info("--- ---- ---- ---- ---- ---- --- 一年的营业额 ：oneYearTurnover "+server01.getOneYearTurnover());
		logger.info("--- ---- ---- ---- ---- ---- --- {0:不接受该结算方式,1:三个月,2:六个月}-bankDraft "+server01.getBankDraft());
		logger.info("--- ---- ---- ---- ---- ---- --- EPC 总承包集成工程实施能力]{0:否,1:是}：hadEpc "+server01.getHadEpc());
		logger.info("--- ---- ---- ---- ---- ---- --- serverCityText "+server01.getServerCityText());
		logger.info("--- ---- ---- ---- ---- ---- --- serverCityIds "+server01.getServerCityIds());
		logger.info("--- ---- ---- ---- ---- ---- --- 最大施工人数:maxNumberOfBuilder "+server01.getMaxNumberOfBuilder());
		logger.info("--- ---- ---- ---- ---- ---- --- 装机容量：tolCapacityOfYn "+serverVo.getTolCapacityOfYn());
		logger.info("--- ---- ---- ---- ---- ---- --- 营业执照图片地址：businessLicenseImgUrl "+businessLicenseImgUrl);
		logger.info("--- ---- ---- ---- ---- ---- --- 资质照片地址：qualificationsImgUrl "+qualificationsImgUrl);
		logger.info("--- ---- ---- ---- ---- ---- --- 设计工程费用：designPrice "+server01.getDesignPrice());
		logger.info("--- ---- ---- ---- ---- ---- --- 市场价格 20KW（含）以下   元/瓦:priceaRing "+server01.getPriceaRing());
		logger.info("--- ---- ---- ---- ---- ---- --- 市场价格 20KW以上  元/瓦:pricebRing "+server01.getPricebRing());
		logger.info("--- ---- ---- ---- ---- ---- --- sqmElectric "+serverVo.getSqmElectric());
		logger.info("--- ---- ---- ---- ---- ---- --- 项目负责人：personInCharge "+serverVo.getPersonInCharge());
		 /** 关联与server有关的对象，然后保存*/
		 User user =new User();
		 user.setEmail(serverVo.getEmail());
		 user.setAddressText(serverVo.getCompanyAddress());
		 user.setPassword(serverVo.getPassword());
		 user.setPhone(serverVo.getPhone());
		 user.setDel(0);
		 userservice.save(user);
		 
		 User user2 = userservice.findByPhone(user.getPhone());
		 
		server01.setUserId(user2.getId());
		server01.setQualificationsImgUrl(qualificationsImgUrl);
		server01.setBusinessLicenseImgUrl(businessLicenseImgUrl);
		server01.setDel(0);
		serverService.save(server01);
		
		Server	NEWserver = serverService.findbylegalPersonPhone(server01.getLegalPersonPhone());
		
		
		logger.info("--- ---- ---- ---- ---- ---- --- 逆变器的id：inverterId "+serverVo.getDecideinfo().getInverterId());
		logger.info("--- ---- ---- ---- ---- ---- --- 逆变器的备注：invremark "+serverVo.getDecideinfo().getInvremark());
		logger.info("--- ---- ---- ---- ---- ---- --- 逆变器的供货价格：invsupplyPrice "+serverVo.getDecideinfo().getInvsupplyPrice());
		logger.info("--- ---- ---- ---- ---- ---- --- 电池板的供货价格：solsupplyPrice "+serverVo.getDecideinfo().getSolsupplyPrice());
		logger.info("--- ---- ---- ---- ---- ---- --- 电池板的id：solarpanelId "+serverVo.getDecideinfo().getSolarpanelId());
		logger.info("--- ---- ---- ---- ---- ---- --- 电池板的solremark：solremark "+serverVo.getDecideinfo().getSolremark());
		logger.info("--- ---- ---- ---- ---- ---- --- serverId：serverId "+NEWserver.getId());
		
		Decideinfo decideinfo=	serverVo.getDecideinfo();
		decideinfo.setServerId(NEWserver.getId());
		decideinfo.setDel(0);
		decideinfoService.save(decideinfo);
		
		
		httpSession.setAttribute("server", server01);

		return ResultVOUtil.success(server01);

	}
   
  
   /**
    * 省份
    * @return
    */
   @ResponseBody
  @RequestMapping(value = "/findProvince")
   public ResultData<Object> findProvince(){
	   
	   List<Province> province01 = province.selectProvince();
	   
	   newPage<Province> page =  new newPage<Province>();
	   
	   page.setList(province01);
	   
	   return ResultVOUtil.success(page);
   }
   
   /**
    * 区县
    * */
   @ResponseBody
   @RequestMapping(value = "/findCity")
    public ResultData<Object> findCity(@RequestParam(value = "id", required = true) Long id){
	   List<City> citys =city.findbycity(id);
	   
	   newPage<City> page =  new newPage<City>();
	   
	   page.setList(citys);

 	   return ResultVOUtil.success(page);
    }
   
   /**
    * 页面加载逆变器品牌
    * */
   
   @ResponseBody
  	@RequestMapping(value = "/select_inverter")
  	public ResultData<Object> selectByExample(String parentId, newPage<Brand> page, HttpServletRequest request,
  			HttpServletResponse response, HttpSession httpSession) {

  		logger.info("传来的值为 ： ------ --------- -------"+parentId);
  		
  				List<Brand> selectByExample = brandService.selectBrand(parentId);
  	
  				for (Brand devide : selectByExample) {
  					logger.info("品牌： ***"+devide.getBrandName()+" -- -- "+ devide.getId());
  				}

  	
  		return ResultVOUtil.success(selectByExample);

  	}
   
   /** 加载品牌*/
   @ResponseBody
   @RequestMapping(value = "/select_all")
   public ResultData<Object> selectByExample(String parentId, newPage<Devide> page, HttpServletRequest request){
	   
	List<Inverter> inverter =   inverterService.getinverter();
	List<SolarPanel> solar =  solarService.getsolar();
	   
	return ResultVOUtil.newsuccess(inverter, solar);   
   }
   
   /**
    * 页面加载型号
    * */
   @ResponseBody
 	@RequestMapping(value = "/select_solar")
 	public ResultData<Object> selectBysolar(Devide deviceType, newPage page, HttpServletRequest request,
 			HttpServletResponse response, HttpSession httpSession) {
	   //0:电池板,1:逆变器,2:其他材料
	   //1、电池板 3、逆变器
	   
	  /* deviceType.setType(1);
	   deviceType.setParentId(2L);*/
	   logger.info("传来的类型的值为 ： ------ --------- -------"+deviceType.getType());
	   logger.info("传来的型号的值为 ： ------ --------- -------"+deviceType.getParentId());
	   
	   Long id =deviceType.getParentId();
	   if(deviceType.getType() == 0){
		   
		   List<SolarPanel> listpanel =   solarService.selectSolarPanel(id);
		   
		   return ResultVOUtil.success(listpanel);
		  
		   
	   }else if(deviceType.getType() == 1){
		   List<Inverter> list = inverterService.selectInverter(id);
		   
		   return ResultVOUtil.success(list);
	   }
	   

 		return ResultVOUtil.success(null);

 	}
   
   /** 显示型号的详细数据*/
   @ResponseBody
	@RequestMapping(value = "/findInvSol")
	public Object findInvSol(Devide deviceType) {
	   /*deviceType.setId(2L);
	   deviceType.setType(1);*/
	   logger.info("传来的类型的值为 ： ------ --------- -------"+deviceType.getType());
	   logger.info("传来的型号的值为 ： ------ --------- -------"+deviceType.getId());
		
		 if(deviceType.getType() == 0){
			 
			 SolarPanel solarPanel	= solarPanelService.findOne(deviceType.getId());
			 
			   return ResultVOUtil.success(solarPanel);
			  
			   
		   }else if(deviceType.getType() == 1){
			 
			   Inverter inverter =inverterService.findOne(deviceType.getId());
			   
			   return ResultVOUtil.success(inverter);
		   }
		
		
		 return ResultVOUtil.success(null);
	}
   
   
   
   /**
	 * 根据服务商可服务的城市来，显示省和城市
	 */
	@ResponseBody
	@RequestMapping(value = "/findProAndCity")
	public Object findProAndCity( HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<>();

		return resultData;
	}
	
	/**
	 * 分页显示服务商
	 */
	@ResponseBody
	@RequestMapping(value = "/find")
	public ResultData<Object> find(com.yn.model.Page<Server> page) {
		/*page.setIndex(1);
		page.setType(0);
		page.setCityName("南京市");*/
		List<SolarPanelVo> solar = null;
		List<QualificationsVo> quali =null;
		List<Object> list = null;
		Integer totalCount =0;
		if(null == page.getCityName() || page.getCityName().equals("")){
			
		 list =  solarService.findObject(page);
		 	
		 totalCount = serverService.findCount(page);
			
		if(totalCount <= 0){
			page.setTotal(1);
		}else{
			 page.setTotal(totalCount%page.getLimit() == 0 ? totalCount/page.getLimit() : (totalCount-totalCount%page.getLimit())/page.getLimit()+1);	
		}
		
		 
		 logger.info("--- --- --- --- --- --- --- "+page.getTotal());
		}else{
			
			 list =  solarService.findtwoObject(page);
			 
			 totalCount = serverService.findcityCount(page);
			 if(totalCount <= 0){
					page.setTotal(1);
				}else{
				page.setTotal(totalCount%page.getLimit() == 0 ? totalCount/page.getLimit() : (totalCount-totalCount%page.getLimit())/page.getLimit()+1);
				}
			
			 logger.info("--- --- --- --- --- --- --- "+page.getTotal());
		}
			solar  =solarService.getpanel(list);
			
			List<Long> ids = new LinkedList<Long>();
			 for (SolarPanelVo solarPanelVo : solar) {
				 if(ids.size()!=0){
					 ids.remove(0);
				 }
				
				logger.info("服务商信息为："+solarPanelVo.getS_id() +" -- -- "+solarPanelVo.getCompanyName()+" -- "+solarPanelVo.getCompanyLogo());
				ids.add(solarPanelVo.getS_id());
				
				 List<Object> list02 = solarService.findquatwoObject(ids);
				 
				  quali = solarService.getqua(list02);
				 for (QualificationsVo qualificationsVo : quali) {
					 logger.info("资质为："+qualificationsVo.getId()+" -- "+qualificationsVo.getImgUrl());
				}	
				 solarPanelVo.setList(quali); 
			}

		return ResultVOUtil.newsuccess(page, solar);
	}	
}