package com.yn.service;

import com.yn.dao.ServerDao;
import com.yn.dao.UserDao;
import com.yn.dao.mapper.ServerMapper;
import com.yn.enums.NoticeEnum;
import com.yn.enums.RoleEnum;
import com.yn.enums.ServerTypeEnum;
import com.yn.model.NewServerPlan;
import com.yn.model.Server;
import com.yn.model.User;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.NewPlanVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

@Service
public class ServerService {
	
	
	@Autowired
	UserService uservice;
    @Autowired
    ServerDao serverDao;
    @Autowired
    UserDao userDao;
    @Autowired
    private NoticeService noticeService;
    
    private static DecimalFormat df = new DecimalFormat("0.00");
	private static DecimalFormat df1 = new DecimalFormat("0000");
	private static Random rd = new Random();
    private  static	SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
			'5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };
	/** (不能与自定义进制有重复) */
	private static final char b = 'o';
	/** 进制长度 */
	private static final int binLen = r.length;


	public static String toSerialCode(long id, int s) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全 
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}   
    
	 /** 生成订单号*/
    public String getOrderCode(Long serverId){
    	
    	return toSerialCode(serverId, 4) + format.format(System.currentTimeMillis())
		+ df1.format(rd.nextInt(9999));
    }

    public Long findcityCount(String cityName){
    	
    	return serverDao.findcityCount(cityName);
    }
  
  public  Long findCount(){
	  
	return serverDao.findCount();
    }
    
    public Server findOne(Long id) {
        return serverDao.findOne(id);
    }

    @Transactional
    public void save(Server server) {
        if (server.getId() != null) {
            Server one = serverDao.findOne(server.getId());
            try {
                BeanCopy.beanCopy(server, one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            serverDao.save(one);
            updateUserRoleId(server);
        } else {
            serverDao.save(server);
        }
    }

    public void delete(Long id) {
        serverDao.delete(id);

        // 删除未读信息
        noticeService.delete(NoticeEnum.NEW_SERVER.getCode(), id);
    }

    public void deleteBatch(List<Long> id) {
        serverDao.deleteBatch(id);
    }

    public Server findOne(Server server) {
        Specification<Server> spec = getSpecification(server);
        Server findOne = serverDao.findOne(spec);
        return findOne;
    }

    public List<Server> findAll(List<Long> list) {
        return serverDao.findAll(list);
    }

    public Page<Server> findAll(Server server, Pageable pageable) {
        Specification<Server> spec = getSpecification(server);
        Page<Server> findAll = serverDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Server> findAll(Server server) {
        Specification<Server> spec = RepositoryUtil.getSpecification(server);
        return serverDao.findAll(spec);
    }
    
    public Page<Server> findLocalServer(Server server, Pageable pageable) {
		Page<Server> findLocalServer = serverDao.findByServerCityIdsLikeAndDel(server.getServerCityIds(), 0, pageable);
		return findLocalServer;
	}

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Specification<Server> getSpecification(Server server) {
        server.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(server);
        return (Root<Server> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Predicate conjunction = cb.conjunction();
            List<Expression<Boolean>> expressions = conjunction.getExpressions();
            Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm") && !entry.getKey().equals("queryEndDtm")) {
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

            // 根据【项目负责人/业务员电话/服务商名称】搜索
            String queryStr = server.getQuery();
            if (!StringUtils.isEmpty(queryStr)) {
                Predicate[] predicates = new Predicate[3];
                predicates[0] = cb.like(root.get("companyName"), "%" + queryStr + "%");
                predicates[1] = cb.like(root.get("personInCharge"), "%" + queryStr + "%");
                predicates[2] = cb.like(root.get("salesmanPhone"), "%" + queryStr + "%");
                expressions.add(cb.or(predicates));
            }
            
        
            // 根据日期筛选
            String queryStartDtm = server.getQueryStartDtm();
            String queryEndDtm = server.getQueryEndDtm();
            if (!StringUtils.isEmpty(queryStartDtm)) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"), DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
            }
            if (!StringUtils.isEmpty(queryEndDtm)) {
                expressions.add(cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
            }

            return conjunction;
        };
    }

    /**
     * 修改服务商的认证状态时，服务商用户的roleId要跟着变
     *
     * @param server
     */
    private void updateUserRoleId(Server server) {
        if (server.getType() != null) {
            User user = userDao.findOne(server.getUserId());
            if (server.getType().equals(ServerTypeEnum.NOT_AUTHENTICATED.getCode())) {
                user.setRoleId(RoleEnum.NOT_AUTHENTICATED_SERVER.getRoleId());
            } else if (server.getType().equals(ServerTypeEnum.AUTHENTICATED.getCode())) {
                user.setRoleId(RoleEnum.AUTHENTICATED_SERVER.getRoleId());
            }
            userDao.save(user);
        }
    }
    
  public Server findbylegalPersonPhone(String phone) {
    	
    	Server server = new Server();
    	server.setLegalPersonPhone(phone);
    	
		return findOne(server);
    }
   public Server findbycompanyEmail(String email) {
	   Server server = new Server();
	   server.setCompanyEmail(email);;
		return findOne(server);
    }
   public Server findbyuserName(String userName) {
	   User server = new User();
   		server.setNickName(userName);
   		
   		User user = uservice.findOne(server);
   		if(user == null){
   			return null;
   		}
   		
   		Long userId = user.getId();
   		
    	Server server2 =findOne(userId);
   		
		return server2;
   }
   

   public List<Server> getpage(Page<Server> page){
		List<Server> list = page.getContent();
		Set<NewServerPlan> doset =  new  HashSet<NewServerPlan>();
		for (Server server2 : list) {
			Set<NewServerPlan> set = server2.getNewServerPlan();

			int i=0;
			 for (NewServerPlan newServerPlan : set) {
				 if(i==0){
					 doset.add(newServerPlan);
					 server2.setNewServerPlan(null);
					 server2.setNewServerPlan(doset);
					 break;
				 }
			}

		}
		return list;
   }
   
   public NewPlanVo getPlan(NewServerPlan newserverPlan,User user,Integer num, Double serPrice,Double apoPrice,Double price){
	   
	   Server server = findOne(newserverPlan.getServerId());
	   NewPlanVo newPlanVo = new NewPlanVo();
	   
	

		/** 后面得加上该字段*/
		newPlanVo.setWarPeriod(newserverPlan.getWarPeriod().intValue());
		newPlanVo.setSerPrice(serPrice);
		newPlanVo.setApoPrice(apoPrice);
		newPlanVo.setNum(num);
		newPlanVo.setCompanyName(server.getCompanyName());
		newPlanVo.setPhone(user.getPhone());
		newPlanVo.setUserName(user.getUserName());
		newPlanVo.setAddress(user.getAddressText());
		newPlanVo.setId(newserverPlan.getId().intValue());
		newPlanVo.setServerId(newserverPlan.getServerId().intValue());
		newPlanVo.setMaterialJson(newserverPlan.getMaterialJson());
		
		newPlanVo.setUnitPrice(BigDecimal.valueOf(newserverPlan.getUnitPrice()));
		newPlanVo.setCapacity(num);
		newPlanVo.setInvstername(newserverPlan.getInverter().getBrandName() + "   " + newserverPlan.getInverter().getModel());
		newPlanVo.setBrandname(newserverPlan.getSolarPanel().getBrandName() + "   " + newserverPlan.getSolarPanel().getModel());
		newPlanVo.setAllMoney(price);
		
		return newPlanVo;
   }
   
   
   
}

