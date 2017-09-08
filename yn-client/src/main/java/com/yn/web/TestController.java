package com.yn.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Apolegamy;
import com.yn.model.NewServerPlan;
import com.yn.model.Server;
import com.yn.service.ApolegamyService;
import com.yn.service.DevideService;
import com.yn.service.NewServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.SystemConfigService;
import com.yn.utils.ResultData;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/test")
public class TestController {
	
	@Autowired
	private NewServerPlanService planService;
	
	  @Autowired
	ApolegamyService apolegamyService;
	  
	  
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
	
	 @RequestMapping(value = "/dotest")
	 @ResponseBody
	    public ResultData<Object> newTest() {
		
	List<Object> list = planService.selectServerPlan(1L);

			return ResultVOUtil.success(list);
	    }
	
	
	

}
