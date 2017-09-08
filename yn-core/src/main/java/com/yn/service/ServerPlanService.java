package com.yn.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.ServerPlanDao;
import com.yn.model.ServerPlan;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.ServerPlanVo;

@Service
public class ServerPlanService {
    @Autowired
    ServerPlanDao serverPlanDao;
    
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

    public ServerPlan findOne(Long id) {
        return serverPlanDao.findOne(id);
    }

    public void save(ServerPlan serverPlan) {
        if(serverPlan.getId()!=null){
        	ServerPlan one = serverPlanDao.findOne(serverPlan.getId());
            try {
                BeanCopy.beanCopy(serverPlan,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            serverPlanDao.save(one);
        }else {
            serverPlanDao.save(serverPlan);
        }
    }

    public void delete(Long id) {
        serverPlanDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		serverPlanDao.deleteBatch(id);
	}

    public ServerPlan findOne(ServerPlan serverPlan) {
        Specification<ServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
        ServerPlan findOne = serverPlanDao.findOne(spec);
        return findOne;
    }

    public List<ServerPlan> findAll(List<Long> list) {
        return serverPlanDao.findAll(list);
    }

    public Page<ServerPlan> findAll(ServerPlan serverPlan, Pageable pageable) {
        Specification<ServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
        Page<ServerPlan> findAll = serverPlanDao.findAll(spec, pageable);
        return findAll;
    }

    public List<ServerPlan> findAll(ServerPlan serverPlan) {
        Specification<ServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
        return serverPlanDao.findAll(spec);
    }
    

}
