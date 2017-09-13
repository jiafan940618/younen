package com.yn.kft.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.yn.kftentity.OrderPay;
import com.yn.model.BillOrder;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.utils.HttpsClientUtil;
import com.yn.utils.PropertyUtils;
import com.yn.utils.RequestUtils;
import com.yn.utils.SignUtil;


@Controller
@RequestMapping(value="/client/payOrder")
public class PayOrderAction {
	
	@Autowired
	private BillOrderService orderService;

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/createPay.htm")
	public String createPay(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		String outTradeNo=(String) request.getAttribute("outTradeNo");
		String channel =(String)request.getAttribute("channel");
		
		Map paramMap = request.getParameterMap();
		//根据订单号查询数据
	 //2017071014153897464
		//OrderPay orderPay =orderPayService.selectOrder(outTradeNo);
		
		BillOrder orderPay = orderService.findByTradeNo(outTradeNo);
		
		//OrderPay orderPay = new OrderPay();
		//获取参数
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
				request.setAttribute("channel", channel);
				/** channel参数比对的是用户要选择用那种支付方式*/
				if(channel.equals("wxPubQR")){
					//展示二维码，请商户调用第三方库将code_url生成二维码图片
					request.setAttribute("codeUrl", returnMap.get("codeUrl"));
				}else if(channel.equals("wxApp")){
					//请商户调用sdk控件发起支付
					request.setAttribute("payCode", returnMap.get("payCode"));
				}else if(channel.equals("wxMicro")){
					//支付成功后的处理，更新订单状态等			
				}else if(channel.equals("wxPub")){
					//request.setAttribute("payCode", returnMap.get("payCode"));
					
					/*OrderPay order=orderPayService.getOrder(returnMap);
					order.setOuttradeno(outTradeNo);
					orderPayService.updateAgainOrder(order);*/
					
					return "redirect:" + returnMap.get("payCode");
				}else if(channel.equals("alipayQR")){
					//展示二维码，请商户调用第三方库将code_url生成二维码图片
					System.out.println("支付的codeurl：----"+returnMap.get("codeUrl"));
					request.setAttribute("codeUrl", returnMap.get("codeUrl"));
				}else if(channel.equals("alipayApp")){
				
					}
			}else{
				request.setAttribute("resultCode", returnMap.get("resultCode"));
				request.setAttribute("errCodeDes", returnMap.get("errCodeDes"));
				request.setAttribute("returnMsg", returnMap.get("returnMsg"));}
		}else {/****do nothing***/}
		
		//添加数据到订单数据表
		/*OrderPay order=orderPayService.getOrder(returnMap);
		order.setOuttradeno(outTradeNo);
		orderPayService.updateAgainOrder(order);*/

		return "successPage";

	}
	
		public static void main(String[] args) {
		     /* Map<String,String> map = new HashMap<String,String>();
		      map.put("tradeType", "cs.pay.submit");
		      map.put("version", "1.3");
		      map.put("mchId", "000010000000000024");
		      map.put("channel", "alipayQR");
		      map.put("body", "分账交易测试");
		      map.put("outTradeNo", "tao_test_005");
		      map.put("sign", "656E72AF744DE642828E58221BDE68BA");
		      map.put("amount", "0.50");
		      map.put("currency", "CNY");
		      map.put("subject", "这是一个测试");
//		      map.put("splitInfo", "[{\"merchNo\":\"000010001000000013\",\"splitAmt\":\"100\",\"remark\":\"有线电视费\"},{\"merchNo\":\"000010001000000018\",\"splitAmt\":\"200\",\"remark\":\"宽带费\"}]");
			  Map<String, String> filterMap = SignUtil.paraFilter(map);
					
					//拼接
					String toSign = SignUtil.createLinkString(filterMap);
					
					//生成签名sign
					String sign = SignUtil.genSign("7f957562b40e4b0eaf5bc9ed4d1a78ca", toSign);
					filterMap.put("sign", sign);

					//转为json串
					String postStr = JSON.toJSONString(filterMap);
					
					//发送请求
					String returnStr = HttpsClientUtil.sendRequest("http://test.kftpay.com.cn:3080/cloud/cloudplatform/api/trade.html",postStr,"application/json");
					*/
			String str = "content=商户号,商户订单号,平台订单号,交易时间,交易类型,交易状态,货币种类,总金额,商户退款单号,平台退款单号,退款金额,退款状态,商品标题,附加数据\n" +
					
					"`000010000000000004,`704120942591311021,`01162502201704120000000001,`2017-04-12 09:58:46,`厦门民生微信扫码,`SUCCESS,`CNY,`0.01,`,`,`0,`REFUND,`,`\n" +
					"&resultCode=0&returnCode=0";
			
					Map<String, String> filterMap = new HashMap<String, String>();
			String sign = SignUtil.genSign("7f957562b40e4b0eaf5bc9ed4d1a78ca", str);
			filterMap.put("sign", sign);
			filterMap.put("tradeType", "cs.pay.submit");
			filterMap.put("version", "1.0");
			filterMap.put("mchId", "000010000000000024");
			filterMap.put("channel", "wxPubQR");
			filterMap.put("body", "这是一个测试");
			filterMap.put("outTradeNo", "010101");
			filterMap.put("amount", "0.2");
			filterMap.put("description", "附加描述");
			filterMap.put("currency", "CNY");
			filterMap.put("subject", "商品标题");
			
			Map<String, String> Map = SignUtil.paraFilter(filterMap);
	
			//拼接
			String toSign = SignUtil.createSignPlainText(filterMap, true);
			//生成签名sign
			String signs = SignUtil.genSign("7f957562b40e4b0eaf5bc9ed4d1a78ca", toSign);
			
			Map.put("sign", signs);
			
			String postStr = JSON.toJSONString(Map);
			
			//发送请求
			String returnStr = HttpsClientUtil.sendRequest("http://test.kftpay.com.cn:3080/cloud/cloudplatform/api/trade.html",postStr,"application/json");
					
					
		}

}
