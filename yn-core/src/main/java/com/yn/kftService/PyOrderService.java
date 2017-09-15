package com.yn.kftService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yn.model.BillOrder;
import com.yn.service.BillOrderService;
import com.yn.utils.HttpsClientUtil;
import com.yn.utils.RequestUtils;
import com.yn.utils.SignUtil;
import com.yn.vo.re.ResultVOUtil;


@Service
public class PyOrderService {
	
	@Autowired
	private BillOrderService orderService;
	@Autowired 
	PyOrderService pyOrderService;
	
	public Map<String, String> getOrder(Map paramMap,BillOrder orderPay,String channel){
		
		Map<String, String> param = RequestUtils.getRequestMapValue(paramMap);	
		param.put("tradeType", "cs.pay.submit");
		param.put("version", "1.0");
		 /** 测试mchid */
		param.put("mchId", "000010000000000024");
		
		param.put("channel",channel);  /** 付款方式*/
		param.put("body", "这是一个测试!");   /** 商品描述*/
		param.put("terminalType","pc"); /** 终端类型*/
		param.put("outTradeNo", orderPay.getTradeNo());    /** 订单号*/
		 //转换金额
		String aomout = orderPay.getMoney().toString();     
		param.put("amount", aomout);
		param.put("description", null);   /** 附加数据*/
		
		param.put("timePaid", null);     /**订单支付时间 */
		param.put("timeExpire", null);   /** 订单失效时间 */
		param.put("extra", null);        /*** 扩展字段 */
		
		param.put("subject", null);    /** 商品标题 */ 
		//过滤空值或null
	
		return param;
	}
	
	
	public Object getMap(HttpServletRequest request,String outTradeNo,String channel){
		
		Map paramMap = request.getParameterMap();

		BillOrder orderPay = orderService.findByTradeNo(outTradeNo);

		//获取参数
	Map<String, String>  param=	getOrder(paramMap, orderPay, channel);
	
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
		Map<String, String> map = new HashMap<String, String>();
		/** 验签 测试key */
		if(SignUtil.validSign(filterMap, "7f957562b40e4b0eaf5bc9ed4d1a78ca")){
			String returnCode = returnMap.get("returnCode");
			String resultCode = returnMap.get("resultCode");
			   /** 如果数据返回正常*/
			if(returnCode.equals("0")&&resultCode.equals("0")){
				//根据不同渠道类型做处理
			
				
				if(channel.equals("wxPubQR")){
				//	request.setAttribute("codeUrl", returnMap.get("codeUrl"));
					System.out.println("支付的codeurl：----"+returnMap.get("codeUrl"));
					map.put("codeUrl", returnMap.get("codeUrl"));
					
				   return	ResultVOUtil.success(map);
					//展示二维码，请商户调用第三方库将code_url生成二维码图片
				}else if(channel.equals("wxApp")){
					System.out.println("支付的payCode：----"+returnMap.get("payCode"));
					
					map.put("payCode", returnMap.get("payCode"));
					//请商户调用sdk控件发起支付
					/** request.setAttribute("payCode", returnMap.get("payCode"));*/
					  return	ResultVOUtil.success(map);
				}else if(channel.equals("alipayQR")){
					//展示二维码，请商户调用第三方库将code_url生成二维码图片
					System.out.println("支付的codeurl：----"+returnMap.get("codeUrl"));
					
					map.put("codeUrl", returnMap.get("codeUrl"));
					  return	ResultVOUtil.success(map);
				}else if(channel.equals("alipayApp")){
					 /** 未授权*/
	
				}
				
			}else{
				
			  return	ResultVOUtil.error(777,returnMap.get("errCodeDes"));
			}
		}else {
			/****do nothing***/
			 return	ResultVOUtil.error(777,"网络繁忙，请稍后重试!");
		}
		return null;
	}
}
