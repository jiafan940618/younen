package com.yn.dao;

import com.yn.model.Station;
import com.yn.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Modifying
    @Query("update User set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update User set del=1,delDtm=(now()) where id in (:ids)")
    void deleteBatch(@Param("ids") List<Long> ids);

    @Query("SELECT COUNT(*) FROM User u WHERE u.createDtm>=?1 AND u.createDtm<?2 AND u.del=0")
    long countNum(Date startDtm, Date endDtm);

    @Query("SELECT COUNT(*) FROM User u WHERE u.del=0")
    long countNum();

    Page<User> findByIdIn(Set<Long> userIds, Pageable pageable);

    @Query("select u.id from User u where u.roleId in ?1 and u.del = 0")
    List<Long> findIdByRoleIds(List<Long> roleIds);
    
    @Transactional
    @Modifying
    @Query("update User set password= :#{#user.password} where id = :#{#user.id}")
	void updatePas(@Param("user") User user);
    
    //fullAddressText  userName  phone
    @Transactional
    @Modifying
    @Query("update User u set u.fullAddressText = :#{#user.fullAddressText} , u.userName = :#{#user.userName} , u.phone = :#{#user.phone} where u.id = :#{#user.id}")
	void updateUser(@Param("user") User user);
    
    @Transactional
    @Modifying
    @Query("update User set token= :#{#user.password} where id = :#{#user.id}")
	void updateToken(@Param("user") User user);
    
    @Query("select new User(id,phone,email,headImgUrl,fullAddressText,nickName) from User u where u.id =:id and u.del = 0")
    User findIdByuser(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update User set fullAddressText = :#{#user.fullAddressText}, nickName = :#{#user.nickName} ,"
    		+ " phone = :#{#user.phone},email = :#{#user.email},headImgUrl =:#{#user.headImgUrl} where id = :#{#user.id}")
	void updateNewUser(@Param("user") User user);
    
    //select w.money,u.integral,u.privilege_code_init from user u LEFT JOIN wallet w on u.id =w.user_id where u.del = 0 and u.id = 1
    @Query(value="select w.money,w.integral,u.privilege_code_init,u.nick_name from user u LEFT JOIN wallet w on u.id =w.user_id where u.del = 0 and u.id = :userId",nativeQuery=true)
    Object findUserPrice(@Param("userId") Long userId);
    
    @Query(value="SELECT u.id FROM user u WHERE u.token=?1 and u.del=0",nativeQuery=true)
    long findByToken(@Param("token")String token);
    
    
    
}
