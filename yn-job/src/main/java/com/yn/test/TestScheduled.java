package com.yn.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.CommentDao;
import com.yn.model.Comment;

/**
 * 
 * @ClassName: TestScheduled
 * @Description: 测试jpa的save方法
 * 	表示Scheduled执行jpa的save方法的时候并没有卵用，并且有两种可能如下：
 * 		1.<有可能>向你抛出了一条select，但是save()执行后返回的对象里是确实有值的，但是数据库没有，不要怀疑没事物。
 * 		2.<有可能>在save()执行后返回的对象为null，并且没有任何的sql语句输出（最可怕）。
 * 		3.saveAndFlush也是垃圾。
 * @author lzyqssn
 * @date 2017年10月16日
 *
 */
//@Component
//@RestController
public class TestScheduled {

	@Autowired
	private CommentDao commentDao;
	
	private int index = 0;
	
	@Scheduled(fixedDelay = 10 * 1000)
	public void myTest() {
		Comment comment = new Comment();
		comment.setCreateDtm(new Date());
		comment.setDetailedEvaluation("Test"+index++);
		comment.setOrderId((long)index);
		comment.setServerEfficiency((double)index);
		comment.setServerQuality((double)index);
		Comment save = commentDao.save(comment);
		System.out.println(save.getId());
	}
	
	@RequestMapping("/doSave")
	public Object myTestC() {
		Comment comment = new Comment();
		comment.setCreateDtm(new Date());
		comment.setDetailedEvaluation("Test"+index++);
		comment.setOrderId((long)index);
		comment.setServerEfficiency((double)index);
		comment.setServerQuality((double)index);
		Comment save = commentDao.save(comment);
		return save;
	}
}
