package com.yn.web;

import com.yn.enums.RoleEnum;
import com.yn.enums.UserSexEnum;
import com.yn.model.User;
import com.yn.service.UserService;
import com.yn.utils.BeanCopy;
import com.yn.utils.KeyUtil;
import com.yn.utils.MD5Util;
import com.yn.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Xiang on 2017/7/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserService userService;

    @Test
    public void save() throws Exception {
        UserVo userVo = new UserVo();
        userVo.setPhone("13202298757");
        userVo.setPassword(MD5Util.GetMD5Code("123456"));
        userVo.setEmail("2710@qq.com");
        userVo.setNickName("九唔搭八");
        userVo.setUserName("上高");
        userVo.setSex(UserSexEnum.UNKNOWN.getCode());
        userVo.setProvinceId(19L);
        userVo.setProvinceText("广东省");
        userVo.setCityId(199L);
        userVo.setCityText("深圳市");
        userVo.setAddressText("龙岗区大运软件小镇");
        userVo.setFullAddressText("广东省深圳市龙岗区大运软件小镇");
        userVo.setPrivilegeCodeInit(KeyUtil.genUniqueKey());
        userVo.setRoleId(RoleEnum.NOT_AUTHENTICATED_SERVER.getRoleId());

        User user = new User();
        BeanCopy.copyProperties(userVo, user);
        userService.save(user);
    }

}