package com.yn.web;

import com.yn.enums.NoticeEnum;
import com.yn.model.User;
import com.yn.service.NoticeService;
import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private NoticeService noticeService;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        User findOne = userService.findOne(id);


        // 更新记录为已读
        if (findOne != null) {
            Long userId = SessionCache.instance().getUserId();
            if (userId != null) {
                noticeService.update2Read(NoticeEnum.NEW_USER.getCode(), id, userId);
            }
        }


        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody UserVo userVo) {
        User user = new User();
        BeanCopy.copyProperties(userVo, user);
        userService.save(user);
        return ResultVOUtil.success(user);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        userService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(UserVo userVo) {
        User user = new User();
        BeanCopy.copyProperties(userVo, user);
        User findOne = userService.findOne(user);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(UserVo userVo, Long serverId, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        User user = new User();
        BeanCopy.copyProperties(userVo, user);
        Page<User> findAll = userService.findAll(user, serverId, pageable);


        // 判断是否已读
        Long userId = SessionCache.instance().getUserId();
        if (userId != null) {
            List<User> content = findAll.getContent();
            for (User one : content) {
                Boolean isNew = noticeService.findIsNew(NoticeEnum.NEW_USER.getCode(), one.getId(), userId);
                if (isNew) {
                    one.setIsRead(NoticeEnum.UN_READ.getCode());
                }
            }
        }


        return ResultVOUtil.success(findAll);
    }

}