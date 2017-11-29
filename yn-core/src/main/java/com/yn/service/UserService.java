package com.yn.service;

import com.yn.dao.OrderDao;
import com.yn.dao.UserDao;
import com.yn.enums.NoticeEnum;
import com.yn.model.User;
import com.yn.utils.BeanCopy;
import com.yn.utils.MD5Util;
import com.yn.utils.ObjToMap;
import com.yn.utils.ResultData;
import com.yn.utils.StringUtil;
import com.yn.vo.UserVo;
import com.yn.vo.WalletVo;
import com.yn.vo.re.ResultVOUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;


@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;
    @Autowired
    WalletService walletService;
    @Autowired
    OrderDao orderDao;
    @Autowired
    private NoticeService noticeService;


    public User findOne(Long id) {
        return userDao.findOne(id);
    }
    
    public void updatePas(User user){
    	userDao.updatePas(user);
    }
    
    public void updateUser(User user){
    	userDao.updateUser(user);
    }

    public void updateNewUser(User user){
    	
    	userDao.updateNewUser(user);
    }

    public void save(User user) {
        if (user.getId() != null) {
            User one = userDao.findOne(user.getId());
            try {
                BeanCopy.beanCopy(user, one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userDao.save(one);
        } else {
            userDao.save(user);
        }

        walletService.createWallet(user);
    }

    public void delete(Long id) {
        userDao.delete(id);

        // 删除未读信息
        noticeService.delete(NoticeEnum.NEW_USER.getCode(), id);
    }

	  public  User findIdByuser(Long id){
		  
		return userDao.findIdByuser(id);
		  
	  }
    
    
    public void deleteBatch(List<Long> id) {
        userDao.deleteBatch(id);
    }

    public User findOne(User user) {
        Specification<User> spec = getSpecification(user);
        User findOne = userDao.findOne(spec);
        return findOne;
    }

    public List<User> findAll(List<Long> list) {
        return userDao.findAll(list);
    }

    public Page<User> findAll(User user, Pageable pageable) {
        Specification<User> spec = getSpecification(user);
        Page<User> findAll = userDao.findAll(spec, pageable);
        return findAll;
    }

    public List<User> findAll(User user) {
        Specification<User> spec = getSpecification(user);
        return userDao.findAll(spec);
    }

    public Page<User> findAll(User user, Long serverId, Pageable pageable) {
        Page<User> findAll = null;
        // 服务商登陆，只可以看到自己的下单用户
        if (serverId != null) {
            Set<Long> userIds = orderDao.findUserId(serverId);
            findAll = userDao.findByIdIn(userIds, pageable);
        } else {
            // 管理员可以看到所有用户
            Specification<User> spec = getSpecification(user);
            findAll = userDao.findAll(spec, pageable);
        }
        return findAll;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Specification<User> getSpecification(User user) {
        user.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(user);
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Predicate conjunction = cb.conjunction();
            List<Expression<Boolean>> expressions = conjunction.getExpressions();
            Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (!entry.getKey().equals("query")) {
                    Object value = entry.getValue();
                    if (value instanceof Map) {
                        Iterator<Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
                        while (iterator1.hasNext()) {
                            Entry<String, Object> entry1 = iterator1.next();
                            expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
                        }
                    } else {
                        expressions.add(cb.equal(root.get(entry.getKey()), value));
                    }
                }
            }

            // 用户名，手机号，联系地址
            if (!StringUtil.isEmpty(user.getQuery())) {
                Predicate[] predicates = new Predicate[3];
                predicates[0] = cb.like(root.get("userName"), "%" + user.getQuery() + "%");
                predicates[1] = cb.like(root.get("phone"), "%" + user.getQuery() + "%");
                predicates[2] = cb.like(root.get("fullAddressText"), "%" + user.getQuery() + "%");
                expressions.add(cb.or(predicates));
            }

            return conjunction;
        };
    }

    /**
     * 更新用户token
     *
     * @param user
     * @return
     */
    public User updateToken(User user) {
    	  String token = getToken(user);
    	  
    	  logger.info("生成的userId为："+user.getId()+"生成的token为："+token);
          user.setToken(token);
        User result = userDao.save(user);
        return result;
    }

    /**
     * 生成登陆token
     *
     * @param user
     * @return
     */
    public String getToken(User user) {
        Long loginDtm = System.currentTimeMillis();
        String token = "";
        if (!StringUtil.isEmpty(user.getPhone())) {
            token = user.getPhone() + loginDtm;
        } else {
            UUID uuid = UUID.randomUUID();
            String uStr = uuid.toString();
            String[] split = uStr.split("-");
            token = split[split.length - 1] + loginDtm;
        }
        return MD5Util.GetMD5Code(token);
    }


    /**
     * 根据phone查找用户
     *
     * @param phone
     * @return
     */
    public User findByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        return findOne(user);
    }


    /**
     * 根据account查找用户
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        User user = new User();
        user.setAccount(account);
        return findOne(user);
    }


    /**
     * 根据 手机号 或 账号 或者eamil 查找用户
     *
     * @param param
     * @return
     */
    public User findByPhoneOrAccountOrEamil(String param) {
        User user = findByPhone(param);
        
        if (user == null) {
        	 user = findByEamil(param);
            if(null == user){
            	if(null != param){
            		user = findByAccount(param);	
            	}	 
            }
        }

        return user;
    }
    



    
    /**
     * 根据email查找用户
     * @param phone
     * @return
     */
    
    public User findByEamil(String Eamil) {
        User user = new User();
        user.setEmail(Eamil);
       
        return findOne(user);
    }
    
    /**
     * 根据用户名查找用户
     * @param phone
     * @return
     */
    

    
    public User findByNickName(String NickName) {
        User user = new User();
        user.setNickName(NickName);
       
        return findOne(user);
    }
    
    public static ResultData<Object> checkMsgTimeOut(String sessionName, HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		
		Long codeTime = (Long)httpSession.getAttribute(sessionName);
		if (codeTime==null) {
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		// 如果短信验证码超过了5分钟
		Long spaceTime = System.currentTimeMillis() - codeTime;
		if(spaceTime > 300000){
			httpSession.setAttribute(sessionName,null);
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("该验证码已失效，请重新获取");
			return resultData;
		}
		return resultData;
	}
    
    /** 个人中心数据的查询*/
    public WalletVo findUserPrice(Long userId){
    	
    	Object object = userDao.findUserPrice(userId);
    	
    	Object[] obj = (Object[])object;
    	BigDecimal money = (BigDecimal)obj[0];
    	BigDecimal integral =(BigDecimal)obj[1];
    	String privilegeCodeInit = (String)obj[2];
    	String nickName = (String)obj[3];
    	
    	WalletVo walletVo = new WalletVo();
    	walletVo.setMoney(money.doubleValue());
    	walletVo.setIntegral(integral.doubleValue());
    	walletVo.setPrivilegeCodeInit(privilegeCodeInit);
    	walletVo.setNickName(nickName);

		return walletVo;	
    }
    
    
  
    public ResultData<Object> getresult(MultipartFile file){
    	Map<String,String> map = new HashMap<String, String>();

    	String loghead = "----- ----- ----- "; 
    	 String fileName = file.getOriginalFilename();  
         // 获取上传文件扩展名  
         String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());  
         // 对扩展名进行小写转换  
         fileExt = fileExt.toLowerCase();  
         // 图片文件大小过滤  
         if (!"jpg".equals(fileExt) && !"jpeg".equals(fileExt) && !"png".equals(fileExt) && !"bmp".equals(fileExt)  
                 && !"gif".equals(fileExt)) {  
        	 logger.info(loghead + "上传失败:无效图片文件类型"); 

             return ResultVOUtil.error(777, "上传失败:无效图片文件类型");
         }  
         long fileSize = file.getSize();  
         logger.info(loghead + "fileInfo:fileName=" + fileName + "&fileSize=" + fileSize);  
         if (fileSize <= 0) {  
        	 logger.info(loghead + "上传失败:文件为空");  
        	
             return ResultVOUtil.error(777, "上传失败:文件为空");
         } else if (fileSize > (2 * 1024 * 1024)) {  
        	 logger.info(loghead + "上传失败:文件大小不能超过2M");
        
             return ResultVOUtil.error(777, "上传失败:文件大小不能超过2M");
         }
         
        
		return ResultVOUtil.success(null);
         
    }
    
    /**
     * 用户退出更新token
     *
     * @param user
     * @return
     */
    public void updateTokenBeforeLogout(User user) {
        String token = getToken(user);
        user.setToken(token);
        userDao.updateTokenBeforeLogout(user);
    }
    
   /**
    * 用户一键注册
    */
    
    public void saveQuickly(User user) {
     User newUser =new User();
     newUser.setPhone(user.getPhone());
     newUser.setPassword("123456");//默认：123456
     newUser.setAccount(user.getPhone());
     newUser.setAddressText(user.getAddressText());
     newUser.setFullAddressText(user.getAddressText());
     newUser.setCityId(213L);
     newUser.setCityText("东莞市");
     newUser.setProvinceId(19L);
     newUser.setProvinceText("广东省");
     newUser.setUserName(user.getUserName());
     newUser.setNickName(user.getUserName());
     newUser.setRoleId(6L);//默认是普通用户
     
    }

}
