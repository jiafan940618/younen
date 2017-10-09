package com.yn.service.kftService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yn.model.Wallet;
import com.yn.service.BillOrderService;
import com.yn.service.WalletService;
import com.yn.utils.Constant;
import com.yn.utils.HttpsClientUtil;
import com.yn.utils.SignUtil;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;

@Service
public class TestOrderService {

	private static final Logger logger = LoggerFactory.getLogger(TestOrderService.class);
	
	@Autowired
	private BillOrderService orderService;
	@Autowired
	WalletService walletService;
	
	public Map<String, String> getOrder(BillOrderVo billOrderVo){
		logger.info("进入方法的订单号："+billOrderVo.getTradeNo());
		logger.info("进入方法的金额："+billOrderVo.getMoney());
		logger.info("进入方法的类型："+billOrderVo.getChannel());
		logger.info("进入方法的字符串："+billOrderVo.getDescription());

		logger.info("进入方法的mchId："+billOrderVo.getMchId());
		
		Map<String, String> param =new HashMap<String, String>();
		param.put("tradeType", "cs.pay.submit");
		param.put("version", "1.0");
		 /** 测试mchid */
		param.put("mchId", billOrderVo.getMchId());
		
		param.put("channel",billOrderVo.getChannel());  /** 付款方式*/
		param.put("body", "开发环境测试!");   /** 商品描述*/
		param.put("terminalType","pc"); /** 终端类型*/
		param.put("outTradeNo", billOrderVo.getTradeNo());    /** 订单号*/

		 //转换金额
		String aomout = billOrderVo.getMoney().toString();
		param.put("amount", aomout);
		param.put("description", billOrderVo.getDescription());   /** 附加数据*/
		
		param.put("timePaid", null);     /**订单支付时间 */
		param.put("timeExpire", null);   /** 订单失效时间 */
	     /*** 扩展字段 */

		param.put("notifyUrl", "http://3ad9d9c5.ngrok.io/client/sign/doresult");
		
		param.put("subject", "这是测试商品1");    /** 商品标题 */ 
		//过滤空值或null
	
		return param;
	}
	
	
	public Object getMap(HttpServletRequest request,BillOrderVo billOrderVo){
		
		String key = billOrderVo.getKey();
		String resultUrl = billOrderVo.getRequestUrl();
		//获取参数
	Map<String, String>  param=	getOrder(billOrderVo);
	
	Map<String, String> filterMap = SignUtil.paraFilter(param);
	String channel = billOrderVo.getChannel();   /** 支付类型*/
	//拼接
	
	String toSign = SignUtil.createSignPlainText(filterMap, true);
	/** 生成签名sign 测试key：7f957562b40e4b0eaf5bc9ed4d1a78ca
	 * 生产：key：详见conf.properties
	 ** */
	String sign = SignUtil.genSign(key, toSign);
	filterMap.put("sign", sign);
	
	//转为json串
	String postStr = JSON.toJSONString(filterMap);
		/** 发送请求 测试requestUrl：http://test.kftpay.com.cn:3080/cloud/cloudplatform/api/trade.html 
		 * 生产url：https://jhpay.kftpay.com.cn/cloud/cloudplatform/api/trade.html
		 * */
		String returnStr = HttpsClientUtil.sendRequest(resultUrl,postStr,"application/json");
		
		logger.info(returnStr);
		
		//解析返回串
		Map<String, String> returnMap = (Map<String, String>)JSON.parse(returnStr);
		Map<String, String> map = new HashMap<String, String>();
		/** 验签 测试key */
		if(SignUtil.validSign(filterMap, key)){
			
			String returnCode = returnMap.get("returnCode");
			logger.info("返回的returnCode：--- -- - -- - - - - - -- -"+returnCode);
			
			String resultCode = returnMap.get("resultCode");
			logger.info("返回的resultCode：---- --- -- - - - --"+resultCode);
			   /** 如果数据返回正常*/
			if(returnCode.equals("0") && resultCode.equals("0")){
				//根据不同渠道类型做处理

				if(channel.equals("wxPubQR")){
				//	request.setAttribute("codeUrl", returnMap.get("codeUrl"));
					System.out.println("支付的codeurl：----"+returnMap.get("codeUrl"));
					map.put("codeUrl", returnMap.get("codeUrl"));
					request.setAttribute("map", returnMap);
					 return	 ResultVOUtil.success(map);
						  // ResultVOUtil.success(map);
					//展示二维码，请商户调用第三方库将code_url生成二维码图片
				}else if(channel.equals("wxApp")){
					System.out.println("支付的payCode：----"+returnMap.get("payCode"));
					request.setAttribute("map", map);
					
					map.put("payCode", returnMap.get("payCode"));
					//请商户调用sdk控件发起支付
					/** request.setAttribute("payCode", returnMap.get("payCode"));*/
					 return	 ResultVOUtil.success(map);
				}else if(channel.equals("alipayQR")){
					//展示二维码，请商户调用第三方库将code_url生成二维码图片
					System.out.println("支付的codeurl：----"+returnMap.get("codeUrl"));
					
					map.put("codeUrl", returnMap.get("codeUrl"));

					  return	 ResultVOUtil.success(map);
				}else if(channel.equals("alipayApp")){
					 /** 未授权*/
	
				}
				
			}else{
				
				logger.info("返回响应结果：--- ---- ---- -- --- --"+returnMap.get("errCodeDes"));
				map.put("errCodeDes", returnMap.get("errCodeDes"));
				
			   return	 ResultVOUtil.success(map);
			}
		}else {
			/****do nothing***/
			 return	map;
		}
		return null;
	}
	
	
	
	/** 处理优能余额支付*/
	public Object payBalance(BillOrderVo billOrderVo){
		
		Wallet wallet =	walletService.findWalletByUser(billOrderVo.getUserId());
		/** 余额*/
		BigDecimal balancePrice =	wallet.getMoney();
	   
		 /** 余额与支付的钱比较*/
		if(balancePrice.compareTo(billOrderVo.getMoney()) == -1){
			
			return ResultVOUtil.error(777,Constant.MONEY_LITTLE);
		}
	 
		BigDecimal money =balancePrice.subtract(billOrderVo.getMoney());

		wallet.setMoney(money);
		
		walletService.updatePrice(wallet);
		
		return ResultVOUtil.success("支付成功！");
	}
}