package com.yn.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yn.vo.NewUserVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yn.dao.DevideDao;
import com.yn.dao.UserDao;
import com.yn.model.User;
import com.yn.service.DevideService;
import com.yn.service.OssService;
import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.CodeUtil;
import com.yn.utils.Constant;
import com.yn.utils.MD5Util;
import com.yn.utils.RandomUtil;
import com.yn.utils.ResultData;
import com.yn.utils.RongLianSMS;

@Controller
@RequestMapping(value = "/client/userLogin")
public class UserLoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	
	@Autowired
	private OssService oss;
	
	@Autowired
	private DevideDao devide01;
	
	@Autowired
	private DevideService province01;
	@Autowired
    private UserService userService;
	@Autowired
    UserDao userDao;

	    @RequestMapping(value = "/login")
	    @ResponseBody
	    public Object appLogin(@RequestParam(value = "phone", required = true) String phone,
	    		@RequestParam(value = "password", required = true) String password,
	    		HttpServletResponse response,HttpSession session) {

	    	   	 User user =null; 
	        user = userService.findByPhone(phone);
	        if(user == null){
	        	 user = userService.findByEamil(phone); 
	        	 if(user == null){
	        		 user = userService.findByUserName(phone); 
	        	 }
	        }
	        if (user == null) {
	        	logger.info("----- --- ----- 该用户不存在！");
	            return ResultVOUtil.error(777, Constant.NO_THIS_USER);
	        } else if (!user.getPassword().equals(MD5Util.GetMD5Code(password))) {
	        	logger.info("----- --- ----- 密码错误！");
	            return ResultVOUtil.error(777, Constant.PASSWORD_ERROR);
	        }


	        user.setToken(userService.getToken(user));
	        userService.updateToken(user);

	        SessionCache.instance().setUser(user);
	        
	        user.setPassword(null);
	        Object object = ResultVOUtil.success(user);
	        
	        NewUserVo userVo = new NewUserVo();
	        
	        userVo.setEmail(user.getEmail());
	        userVo.setFullAddressText(user.getFullAddressText());
	        userVo.setId(user.getId());
	        userVo.setNickName(user.getNickName());
	        userVo.setUserName(user.getUserName());
	        userVo.setPhone(user.getPhone());
	        userVo.setToken(userService.getToken(user)); 
	        userVo.setHeadImgUrl(user.getHeadImgUrl());
	        
	        
	        logger.info("---- ---- --- --- - --- - --- ----结束");
	        
	        return ResultVOUtil.success(userVo);
	    }

	 
	   @ResponseBody
	   @RequestMapping(value = "/apologin")
	    public ResultData apoLogin(UserVo userVo, HttpServletRequest request, HttpServletResponse response,
				HttpSession httpSession) {
		   
		   userVo.setPhone("13450699433");
		   userVo.setPassword("123456");
		   
	  logger.info("---- ---- --- --- - --- - --- -----传递的phone:"+userVo.getPhone());
	  logger.info("---- ---- --- --- - --- - --- -----传递的password:"+userVo.getPassword());
		 
		 
	    	   	 User newuser =null; 
	    	   	newuser = userService.findByPhone(userVo.getPhone());
	        if(newuser == null){
	        	newuser = userService.findByEamil(userVo.getPhone()); 
	        	 if(newuser == null){
	        		 newuser = userService.findByUserName(userVo.getPhone()); 
	        	 }
	        }
	        if (newuser == null) {
	        	logger.info("----- --- ----- 该用户不存在！");
	            return ResultVOUtil.error(777, Constant.NO_THIS_USER);
	        } else if (!newuser.getPassword().equals(MD5Util.GetMD5Code(userVo.getPassword()))) {
	        	logger.info("----- --- ----- 密码错误！");
	            return ResultVOUtil.error(777, Constant.PASSWORD_ERROR);
	        }


	        newuser.setToken(userService.getToken(newuser));
	        userService.updateToken(newuser);

	        SessionCache.instance().setUser(newuser);
	        newuser.setPassword(null);
	        Object object = ResultVOUtil.success(newuser);
	        
	        NewUserVo userVo01 = new NewUserVo();
	        
	        userVo01.setEmail(newuser.getEmail());
	        userVo01.setFullAddressText(newuser.getFullAddressText());
	        userVo01.setId(newuser.getId());
	        userVo01.setNickName(newuser.getNickName());
	        userVo01.setUserName(newuser.getUserName());
	        userVo01.setPhone(newuser.getPhone());
	        userVo01.setToken(userService.getToken(newuser)); 
	        userVo01.setHeadImgUrl(newuser.getHeadImgUrl());
	        
	      //  httpSession.setAttribute("user", userVo01);
	        
	        logger.info("---- ---- --- --- - --- - --- ----结束");
	        
	        return ResultVOUtil.success(userVo01);
	    }

    /**
     * 图形验证码
     */
    @RequestMapping(value = "/getCode", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 生成随机字串,参数为图形验证码长度
        String code = CodeUtil.generateCode(4);

        // 存入会话session
        HttpSession httpSession = request.getSession(true);
        SessionCache.instance().setCode(code);
        // 生成图片
        int w = 200, h = 80;
        CodeUtil.outputImage(w, h, response.getOutputStream(), code);
        System.out.println(code);
    }

    @RequestMapping(value = "/getMd5", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(String code) {
        String getMD5Code = MD5Util.GetMD5Code(code);
        return ResultVOUtil.success(getMD5Code);
    }
    
    /**
     * 密码找回
     */
    @RequestMapping(value="/updataPas")
    @ResponseBody
    public ResultData<Object> Updatepas(@RequestParam(value = "phone", required = true) String phone,
    		@RequestParam(value="password") String password,@RequestParam(value="nextpass") String nextpassword,@RequestParam(value="code") String code){
    	
    	User user = userService.findByPhone(phone);
    	
    	String code02 = SessionCache.instance().getCode();
    	
    	 if(!user.getPassword().equals(MD5Util.GetMD5Code(password))){
    		 return ResultVOUtil.error(777, Constant.NO_THIS_USER);
    	 }else if(!password.equals(nextpassword)){
    		 return ResultVOUtil.error(777, Constant.PASSWORD_AGAIN_ERROR);
    	 }else if(!code02.equals(code)){
    		 return ResultVOUtil.error(777, Constant.CODE_ERROR);
    	 }
  
		return ResultVOUtil.success(user);
    }
    //'phone':findPwdPhone,'code4findpsw':findPwdCode,'password':newPwd2
    @ResponseBody
	@RequestMapping(value = "/updatePassword")
	public ResultData<Object> updatePassword(UserVo user, String code4findpsw, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
    	
		if (user.getPhone() == null || user.getPhone().equals("")) {
			logger.info("---- ---- ---- ---- 电话不能为空 ");
			return  ResultVOUtil.error(777, Constant.PHONE_NULL);
		}
		User selectByPhone = userService.findByPhone(user.getPhone());
		if (selectByPhone == null) {
			logger.info("---- ---- ---- ---- 用户不存在！ ");
			return ResultVOUtil.error(777, Constant.NO_THIS_USER);
		}
		
		Long code4registerTime = (Long)httpSession.getAttribute("codeUserTime");
		
		Object attribute2 = httpSession.getAttribute("codeUser");
		if (attribute2 == null) {
			logger.info("---- ---- ---- ---- 短信验证码不能为空！ ");
			return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		if (!code4findpsw.equals(attribute2) || !user.getPhone().equals(httpSession.getAttribute("user_phone"))) {
			logger.info("---- ---- ---- ---- 短信验证码错误！ ");
			return ResultVOUtil.error(777, Constant.CODE_ERROR);
		}

		selectByPhone.setPassword(MD5Util.GetMD5Code(user.getPassword()));
		
		userService.updatePas(selectByPhone);
		selectByPhone.setPassword(null);
		
		return ResultVOUtil.success(selectByPhone);
	}
    
    
    /**
     * 短信验证码
     */
    @ResponseBody
   @RequestMapping(value="/sendInf")
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
	//String code = Propertie.getProperty("code");
	 /** 拿到短信验证码*/  
	String code = RongLianSMS.sendCode(phone);
	 
	 if(null == code){
		 logger.info(" ------ 短信发送失败！");
		return ResultVOUtil.error(777, Constant.CODE_FALSE);
	 }
	 
	 
	 /** 处理验证码的时间*/
	Long code4registerTime = System.currentTimeMillis();
	 
	 httpSession.setAttribute("codeUserTime", code4registerTime);
	
	 httpSession.setAttribute("codeUser", code);
	 
	 httpSession.setAttribute("user_phone", phone);
	 
	 logger.info("短信验证码为：--- --- -- --- -- "+code);

	return  ResultVOUtil.success(code); 
   }
    
    
    /** 
     * 修改密码的短信验证
     * */
    @ResponseBody
    @RequestMapping(value="/updatapasInf")
    public  ResultData<Object> getInformation(@RequestParam(value = "phone", required = true) String phone, HttpSession httpSession,HttpServletResponse response){
 	   
    	if(null == phone || phone.equals("")){
    		logger.info(" ------ 手机号不能为空");
    		return ResultVOUtil.error(777, Constant.PHONE_NULL);
    	}
    	User user = userService.findByPhone(phone);
    	
    	if(user == null){
    		logger.info(" ------ 手机号错误或已存在");
    		return ResultVOUtil.error(777, Constant.PHONE_NOTEXIST);
    	}
    	
    	 /** 测试的短信验证.*/
    	//String code = Propertie.getProperty("code");
    	 /** 拿到短信验证码*//** 测试的短信验证*/
    	String code = RongLianSMS.sendCode(phone);
    	 
    	 if(null == code){
    		 logger.info(" ------ 短信发送失败！");
    		return ResultVOUtil.error(777, Constant.CODE_FALSE);
    	 }
    	 /** 处理验证码的时间*/
    	Long code4registerTime = System.currentTimeMillis();
    	 
    	 httpSession.setAttribute("codeUserTime", code4registerTime);
    	
    	 httpSession.setAttribute("codeUser", code);
    	 
    	 httpSession.setAttribute("user_phone", phone);
    	 
    	 logger.info("短信验证码为：--- --- -- --- -- "+code);

    	return  ResultVOUtil.success(code); 
       }
   
    /** 手机端的注册*/
    @ResponseBody
	@RequestMapping(value = "/register_phone",method = {RequestMethod.POST})
	public ResultData<Object> register_phone(UserVo user, String code4register, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
    	
    	 User newUser = new User();
         BeanCopy.copyProperties(user, newUser);

		if (user.getPhone() == null || user.getPhone().equals("") ) {
			logger.info(" -- -- --- 电话不能为空");
			 return ResultVOUtil.error(777, Constant.PHONE_NULL);
		}
		
		User user2 = new User();
		user2.setPhone(user.getPhone());
		User selectByPhone = userService.findByPhone(user.getPhone());
		if (selectByPhone != null) {
			logger.info(" -- -- --- 该电话已注册");
			return ResultVOUtil.error(777, Constant.PHONE_EXIST);
		}
		
		Long code4registerTime = (Long)httpSession.getAttribute("codeUserTime");
		
		if (code4registerTime==null) {
			logger.info(" -- -- --- 短信验证码不能为空！");
			return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		
		// 如果短信验证码超过了5分钟,获得短信验证码的时间
		Long spaceTime = System.currentTimeMillis() - code4registerTime;
		if(spaceTime > 300000){
			logger.info(" -- -- --- 短信验证码已失效！");
			return ResultVOUtil.error(777, Constant.CODE_AGAIN);
		}
		
		String attribute2 = (String)httpSession.getAttribute("codeUser");
		if (attribute2 == null) {
			logger.info(" -- -- --- 短信验证码不能为空！");
			
			return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		/** 比较俩次的验证码是否一样 或者 俩次电话不一样都返回一个验证码错误*/
		if (!code4register.equals(attribute2) || !user.getPhone().equals(httpSession.getAttribute("user_phone"))) {
			
			logger.info(" -- -- --- 验证码错误");
			
			return ResultVOUtil.error(777, Constant.CODE_ERROR);
		}


		newUser.setPassword(MD5Util.GetMD5Code(newUser.getPassword()));
		
		String privilegeCodeInit =	RandomUtil.generateOnlyNumber(); 
		newUser.setPrivilegeCodeInit(privilegeCodeInit);
		
		/** 此时添加时，会添加俩张表，wallet，user表*/
		userService.save(newUser);
    	
    	
		return ResultVOUtil.success(newUser);
    }
    
   /**
    * 用户注册 第一步
    * */
 	@ResponseBody
	@RequestMapping(value = "/register_user",method = {RequestMethod.POST})
	public ResultData<Object> register1(UserVo user, String code4register, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		
 		 User newUser = new User();
         BeanCopy.copyProperties(user, newUser);

		if (user.getPhone() == null || user.getPhone().equals("") ) {
			logger.info(" -- -- --- 电话不能为空");
			 return ResultVOUtil.error(777, Constant.PHONE_NULL);
		}
		
		User user2 = new User();
		user2.setPhone(user.getPhone());
		User selectByPhone = userService.findByPhone(user.getPhone());
		if (selectByPhone != null) {
			logger.info(" -- -- --- 该电话已注册");
			return ResultVOUtil.error(777, Constant.PHONE_EXIST);
		}
		
		Long code4registerTime = (Long)httpSession.getAttribute("codeUserTime");
		
		if (code4registerTime==null) {
			logger.info(" -- -- --- 短信验证码不能为空！");
			return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		
		// 如果短信验证码超过了5分钟,获得短信验证码的时间
		Long spaceTime = System.currentTimeMillis() - code4registerTime;
		if(spaceTime > 300000){
			logger.info(" -- -- --- 短信验证码已失效！");
			return ResultVOUtil.error(777, Constant.CODE_AGAIN);
		}
		
		String attribute2 = (String)httpSession.getAttribute("codeUser");
		if (attribute2 == null) {
			logger.info(" -- -- --- 短信验证码不能为空！");
			
			return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		/** 比较俩次的验证码是否一样 或者 俩次电话不一样都返回一个验证码错误*/
		if (!code4register.equals(attribute2) || !user.getPhone().equals(httpSession.getAttribute("user_phone"))) {
			
			logger.info(" -- -- --- 验证码错误");
			
			return ResultVOUtil.error(777, Constant.CODE_ERROR);
		}

		httpSession.setAttribute("register_user", newUser);
		
		return ResultVOUtil.success(newUser);
	}

	/**
	 * 注册，步骤二
	 * 昵称，姓名，性别，地址
	 */
	@ResponseBody
	@RequestMapping(value ="/register02_user",method = {RequestMethod.POST})
	public ResultData<Object> register2(User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {

		User attribute = (User)httpSession.getAttribute("register_user");
		

		if (attribute == null) {
			logger.info("----- ----- ------ ------ ------ 请先完成第一步操作！");
			return ResultVOUtil.error(777, Constant.NO_FIRST);
		}
		
		if(user.getNickName()==null){
			logger.info(" ---- --- --- ---- ---- ---- 昵称不能为空！");
			return ResultVOUtil.error(777, Constant.NICK_NULL);
		}
		
		if(userService.findByNickName(user.getNickName())!=null){
			logger.info(" ---- ---- --- ---  ---  --- 昵称已存在！");
			return ResultVOUtil.error(777, Constant.NICK_EXIST);
		}
		
		if(user.getUserName()==null){
			logger.info(" ---- ---- --- ---  ---  --- 名字不能为空！");
			
			return ResultVOUtil.error(777, Constant.NAME_NULL);
		}
		if(user.getSex()==null){
			logger.info(" ---- ---- --- ---  ---  --- 性别不能为空！");
			
			return ResultVOUtil.error(777, Constant.SEX_NULL);
		}
		if(user.getAddressText().length() > 225){
			logger.info(" ---- ---- --- ---  ---  --- 详细地址太长！");
			return ResultVOUtil.error(777, Constant.ADDRESS_LONG);
		}

		if (attribute instanceof User) {
			User new_name = (User) attribute;
			  //** MD5加密以后密码*//*
			user.setPassword(MD5Util.GetMD5Code(new_name.getPassword()));
			user.setPhone(new_name.getPhone());
			
			if(user.getEmail() == null || user.getEmail().equals("")){
				user.setEmail(new_name.getEmail());
			}
			
		}
		
		user.setRoleId(6L);
		user.setAccount("null");
		
		String privilegeCodeInit =	RandomUtil.generateOnlyNumber(); 
		user.setPrivilegeCodeInit(privilegeCodeInit);
		
		/** 此时添加时，会添加俩张表，wallet，user表*/
		userService.save(user);
		//httpSession.setAttribute("user", user);
		return ResultVOUtil.success(user);
	}
	
	/** 服务商上传营业执照等*/
	 @RequestMapping(value="/doupload")
	 @ResponseBody
	  public ResultData<Object> getupload(MultipartHttpServletRequest request,HttpSession session) throws UnsupportedEncodingException{
		  request.setCharacterEncoding("UTF-8");
		  String finaltime =null;
		  
		  String realpath = "/opt/Test";
		  /** 测试路径*/
		//  String realpath ="D://Software//huo";
		//创建一个通用的多部分解析器  
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        //判断 request 是否有文件上传,即多部分请求  
	        if(multipartResolver.isMultipart(request)){  
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            //取得request中的所有文件名  
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	                //记录上传过程起始时的时间，用来计算上传时间  
	                int pre = (int) System.currentTimeMillis();  
	                //取得上传文件  
	                MultipartFile file = multiRequest.getFile(iter.next());  
	                
	                ResultData<Object>  data =  userService.getresult(file);
	                
	                if(data.getCode() == 200){
	                	 finaltime  =  oss.upload(file, realpath);

	 	                /** 取得文件以后得把文件保存在本地路径*/
	 	              
		 	               if(finaltime.equals("101") ){
		 	            	   return   ResultVOUtil.error(777, Constant.FILE_ERROR);
		 	                }
		 	               if(finaltime.equals("102") ){
		 	            	   return   ResultVOUtil.error(777, Constant.FILE_NULL);
		 	                }
		 	                //记录上传该文件后的时间  
		 	                int finaltime01 = (int) System.currentTimeMillis();  
	                }
	                if(data.getCode() ==777){
	                	return data;
	                }
	            }	
	        }
	        
	        String  businessLicenseImgUrl =(String)  session.getAttribute("businessLicenseImgUrl");
		     
		     logger.info("---- ---- ----- ------- --- businessLicenseImgUrl："+businessLicenseImgUrl);
		     
		     if(null != businessLicenseImgUrl && !businessLicenseImgUrl.equals("")){
		    	 
		    	 String finaltime01 =  businessLicenseImgUrl+","+finaltime;
		    	 
		    	 logger.info("---- ---- ----- ------- --- 串接："+finaltime01);
		    	 
		    	 session.setAttribute("businessLicenseImgUrl", finaltime01);
		     }else{
		    	 session.setAttribute("businessLicenseImgUrl", finaltime);
		     }
	      

	       logger.info(finaltime);
	        return ResultVOUtil.success(finaltime);   
	 }
	 
	 /** 服务商上传资质等*/
	 @RequestMapping(value="/moreupload")
	 @ResponseBody
	  public ResultData<Object> getupload02(MultipartHttpServletRequest request,HttpSession session) throws UnsupportedEncodingException{
		  request.setCharacterEncoding("UTF-8");
		  String finaltime =null;
		  
		  String realpath = "/opt/Test";
		  /** 测试路径*/
	 // String realpath ="D://Software//huo";
		//创建一个通用的多部分解析器  
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        //判断 request 是否有文件上传,即多部分请求  
	        if(multipartResolver.isMultipart(request)){  
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            //取得request中的所有文件名  
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	                //记录上传过程起始时的时间，用来计算上传时间  
	                int pre = (int) System.currentTimeMillis();  
	                //取得上传文件  
	                MultipartFile file = multiRequest.getFile(iter.next());  
	                
	                ResultData<Object>  data =  userService.getresult(file);
	              logger.info("----- - ----- --- 返回的号码为："+data.getCode());
	                
	                if(data.getCode() == 200){
	                	 finaltime  =  oss.upload(file, realpath);

	 	                /** 取得文件以后得把文件保存在本地路径*/
	 	              
		 	               if(finaltime.equals("101") ){
		 	            	   return   ResultVOUtil.error(777, Constant.FILE_ERROR);
		 	                }
		 	               if(finaltime.equals("102") ){
		 	            	   return   ResultVOUtil.error(777, Constant.FILE_NULL);
		 	                }
		 	                //记录上传该文件后的时间  
		 	                int finaltime01 = (int) System.currentTimeMillis();  
	                }else  if(data.getCode() ==777){
	                	return data;
	                }
	            }	
	        }
	        
	        String  qualificationsImgUrl =(String)  session.getAttribute("qualificationsImgUrl");
		     
		     logger.info("---- ---- ----- ------- --- qualificationsImgUrl："+qualificationsImgUrl);
		     
		     if(null != qualificationsImgUrl && !qualificationsImgUrl.equals("")){
		    	 
		    	String finaltime01 =  qualificationsImgUrl+","+finaltime;
		    	 
		    	 logger.info("---- ---- ----- ------- --- 串接："+finaltime01);
		    	 
		    	 session.setAttribute("qualificationsImgUrl", finaltime01);
		     }else{
		    	 session.setAttribute("qualificationsImgUrl", finaltime);
		     }
  
	       logger.info("---- ---- ----- ------- --- 上传的图片为："+finaltime);
	        return ResultVOUtil.success(finaltime);   
	 }
	 
	 //produces="text/html;charset=utf-8" ResultData<Object> 
	 /** 用户,服务商上传头像*/
	 @RequestMapping(value="/findupload",produces="text/html;charset=utf-8")
	 @ResponseBody
	 public Object  imageUpload(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws UnsupportedEncodingException {
		 ResultData<Object>	  data =null;
		
		 String realpath = "/opt/Test";
		 /** 测试路径*/
		 // String realpath ="D://Software//huo";
		 
		 logger.info(" ---- ----- ----- ----- 路径为："+realpath);
		 
		 MultipartHttpServletRequest  multipartrequest = (MultipartHttpServletRequest)request;
		 		
		List<MultipartFile> multipartFile =  multipartrequest.getFiles("logo_img");
		 
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		 String finaltime =null;
		if(null != multipartFile && !multipartFile.isEmpty()){
			MultipartFile file =	multipartFile.get(0);
			
		  data =  userService.getresult(file);
			  /** 如果上传的图片符合大小与尺寸则执行*/
			  if(data.getCode() == 200){
			  
			 finaltime  =  oss.upload(file, realpath);
				 if(finaltime.equals("102") ){
					 System.out.println(finaltime);
						 
						 map.put("finaltime", Constant.FILE_NULL);
							
							String result = String.valueOf(JSONObject.fromObject(map));
						 return result;
					 }
				}else{
					
				 map.put("finaltime", data.getMsg());
				 String result = String.valueOf(JSONObject.fromObject(map));
				 return result;
			}
		}else{
			 /** 相反就放入的错误的信息*/
			map.put("finaltime", data.getMsg());
			String result = String.valueOf(JSONObject.fromObject(map));
			return result;
		}	

		logger.info(" ---- ----- ----- ----- 路径为："+finaltime);
		
		/** 这里可以放状态等等 */
		map.put("finaltime", finaltime);
		
		String result = String.valueOf(JSONObject.fromObject(map));
		 session.setAttribute("finaltime", finaltime);

		return  result;
	   }


}
