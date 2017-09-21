package com.yn.kft.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.yn.kftService.PyOrderService;
import com.yn.model.BillOrder;
import com.yn.service.BillOrderService;
import com.yn.utils.HttpsClientUtil;
import com.yn.utils.PropertyUtils;
import com.yn.utils.SignUtil;
import com.yn.vo.re.ResultVOUtil;


@Controller
@RequestMapping(value="/client/payOrder")
public class PayOrderAction {
	
	@Autowired
	private BillOrderService orderService;
	@Autowired 
	PyOrderService pyOrderService;
	

	private String key;
	private String requestUrl;
	private String mchId;
	
	public  PayOrderAction(){
		mchId = PropertyUtils.getProperty("mchId");
		key = PropertyUtils.getProperty("key");
		 requestUrl = PropertyUtils.getProperty("requestUrl");
	}
			
	@RequestMapping(value = "/payStart.htm")
	public String queryIndex(HttpServletRequest httpServletRequest) {
		String mchId = PropertyUtils.getProperty("mchId");
		httpServletRequest.setAttribute("mchId", mchId);
		httpServletRequest.setAttribute("outTradeNo", System.currentTimeMillis());
		return "payStart";
	}
  
	
	
	//https://qr.alipay.com/bax006707avarcn0xceg00a9
	 //@RequestParam(value="outTradeNo") String outTradeNo,@RequestParam(value="channel") String channel
	 /** 参数传一个订单号查询数据最后填充至map集合*/
//	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/createPay.htm")
	public Object createPay(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String outTradeNo=(String) request.getAttribute("outTradeNo");
		String channel =(String)request.getAttribute("channel");
		
		Map paramMap = new HashMap();

		BillOrder orderPay = orderService.findByTradeNo(outTradeNo);

		//获取参数
	Map<String, String>  param=	pyOrderService.getOrder( orderPay, channel);
	
	Map<String, String> filterMap = SignUtil.paraFilter(param);
	//String channel = filterMap.get("channel");   /** 支付类型*/
	//拼接
	
	String toSign = SignUtil.createSignPlainText(filterMap, true);
	/** 生成签名sign 测试key：7f957562b40e4b0eaf5bc9ed4d1a78ca
	 * 生产：key：详见conf.properties
	 ** */
	String sign = SignUtil.genSign("7f957562b40e4b0eaf5bc9ed4d1a78ca", toSign);
	filterMap.put("sign", sign);
	
	//转为json串
	String postStr = JSON.toJSONString(filterMap);
		/** 发送请求 测试requestUrl：http://test.kftpay.com.cn:3080/cloud/cloudplatform/api/trade.html 
		 * 生产url：https://jhpay.kftpay.com.cn/cloud/cloudplatform/api/trade.html
		 * */
		String returnStr = HttpsClientUtil.sendRequest(
				"http://test.kftpay.com.cn:3080/cloud/cloudplatform/api/trade.html"
				,postStr,"application/json");
		
		//解析返回串
		Map<String, String> returnMap = (Map<String, String>)JSON.parse(returnStr);
		
		/** 验签 测试key */
		if(SignUtil.validSign(filterMap, "7f957562b40e4b0eaf5bc9ed4d1a78ca")){
			String returnCode = returnMap.get("returnCode");
			String resultCode = returnMap.get("resultCode");
			   /** 如果数据返回正常*/
			if(returnCode.equals("0")&&resultCode.equals("0")){
				//根据不同渠道类型做处理
			Map<String, String> map = new HashMap<String, String>();
				
				if(channel.equals("wxPubQR")){
				//	request.setAttribute("codeUrl", returnMap.get("codeUrl"));
					System.out.println("支付的codeurl：----"+returnMap.get("codeUrl"));
					map.put("codeUrl", returnMap.get("codeUrl"));
					return ResultVOUtil.success(map);
					//展示二维码，请商户调用第三方库将code_url生成二维码图片
				}else if(channel.equals("wxApp")){
					System.out.println("支付的payCode：----"+returnMap.get("payCode"));
					
					map.put("payCode", returnMap.get("payCode"));
					//请商户调用sdk控件发起支付
					/** request.setAttribute("payCode", returnMap.get("payCode"));*/
					return ResultVOUtil.success(map);
					
				}else if(channel.equals("alipayQR")){
					//展示二维码，请商户调用第三方库将code_url生成二维码图片
					System.out.println("支付的codeurl：----"+returnMap.get("codeUrl"));
					
					map.put("codeUrl", returnMap.get("codeUrl"));
					
					return ResultVOUtil.success(returnMap);

				}else if(channel.equals("alipayApp")){
					 /** 未授权*/

				}
				
			}else{
				
				return ResultVOUtil.error(777,"支付失败,详情请咨询客服!");
			}
		}else {
			/****do nothing***/
			    return ResultVOUtil.error(777,"网络繁忙，请稍后重试!");
		}

		return ResultVOUtil.success(null);

	}
	
	

}
