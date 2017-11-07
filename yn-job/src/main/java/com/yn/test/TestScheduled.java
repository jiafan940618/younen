package com.yn.test;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.CommentDao;
import com.yn.model.Comment;
import com.yn.model.ElecDataHour;
import com.yn.service.ElecDataHourService;

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

	@Autowired
	private ElecDataHourService elecDataHourService;
	
	private int index = 0;
	
	@Scheduled(fixedDelay = 10 * 1000)
	public void myTest() {
		Comment comment = new Comment();
		comment.setCreateDtm(new Date());
		comment.setDetailedEvaluation("Test"+index++);
		comment.setOrderId((long)index);
		comment.setServerEfficiency((double)index);
		comment.setServerQuality((double)index);
//		comment.setId(8L);
		Comment save = commentDao.save(comment);
		System.out.println(save);
	}
	
	
	//@Scheduled(fixedDelay = 10 * 1000)
	private void job(){
		List<ElecDataHour> dataByMonth = elecDataHourService.findAllDataByMonthOrYear(1,-1,-1);
		System.out.println(dataByMonth.size());
	}
	
	
	@RequestMapping("/doSave")
	public Object myTestC() {
		Comment comment = new Comment();
		comment.setCreateDtm(new Date());
		comment.setDetailedEvaluation("Test"+index++);
		comment.setOrderId((long)index);
		comment.setServerEfficiency((double)index);
		comment.setServerQuality((double)index);
		comment.setId(666L);
		Comment save = commentDao.save(comment);
		System.out.println(save);
		return save;
	}
}
