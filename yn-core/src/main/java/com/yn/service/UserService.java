package com.yn.service;

import com.yn.dao.OrderDao;
import com.yn.dao.UserDao;
import com.yn.enums.NoticeEnum;
import com.yn.model.User;
import com.yn.utils.BeanCopy;
import com.yn.utils.MD5Util;
import com.yn.utils.ObjToMap;
import com.yn.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.Map.Entry;


@Service
public class UserService {


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
     * 根据 手机号 或 账号 查找用户
     *
     * @param param
     * @return
     */
    public User findByPhoneOrAccount(String param) {
        User user = findByPhone(param);
        if (user == null) {
            user = findByAccount(param);
        }

        return user;
    }


}
