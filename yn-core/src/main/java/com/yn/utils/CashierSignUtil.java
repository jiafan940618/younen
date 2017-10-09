package com.yn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.soofa.apache.commons.codec.binary.Base64;

import com.lycheepay.gateway.client.http.HttpUploadFile;
import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;

public class CashierSignUtil {
	
/**
 * 签约测试
 */
	private final static String charset = "UTF-8";

	private static Map<String, String> param = null;
	private static String signatureInfo = "";
	

	public static void main(String[] args) {
		String caPath="C:\\pfx\\";
		//请求报文加签
		sign(caPath+"pfx.pfx", "123456");
		sign01("kft123456");
		//同步报文验签
		verifySign(caPath+"pfx.cer");
		//异步报文验签
		verifySign_1(caPath+"20KFT.cer");	
	}

	/**
	 * 异步回调验签
	 * @param cerPath
	 */
	private static void verifySign_1(String cerPath) {
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("amount", "1.00");
			parameters.put("orderNo", "kft1234567896677889905");
			parameters.put("status", "1");
			parameters.put("statusDesc", "交易成功");
			//parameters.put("reqNo", "");
			parameters.put("callerIp", "10.37.20.46");
			parameters.put("language", "zh_CN");
		
			Map<String, String> parameters_1 = paramsFilter(parameters);
			String createLinkString = createLinkString(parameters_1);
			byte[] datas = createLinkString.getBytes(charset);
			// 得到的报文中的签名
			String signMsg = "VGcLb/wXtix2UkTndzlTZkS2epOW8IOAQZDt8Xj9gcdlWhziR+r2e8YjhZxQaVbyCV97gla8P8e8MSFHeM9xDgPlXDsnbjf/24En6yyZqEY7dWIiN5S4HCtmrXwxcxiHi+22PFvsOuAS2QPYSUCDfMuh7Y8QaIHxrlv2PZ3KoLyGI0VtGEjgBgjsTsSVqjhXMobn55ATGjbw+qDJehg7wpWUkDQf9fhHkx2d4Ymt/ijiUuFGbLl65i6thhG8VbCHOkg0gnTq/PpT0Z9l5QBAfl2WQTtBQI78MJbVLo/Jp13GGkWbD+OcDckhMWpnNWwEFy/apSGfd6YhouRfpDIKwA==";
			byte[] sign = Base64.decodeBase64(signMsg);
			boolean verifySign = verifySign(datas, sign,cerPath);
			System.out.println("验签名结果：" + verifySign);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
	/**
	 * 异步回调验签
	 * @param cerPath
	 */
	public static boolean verifySign_2(String cerPath,Map<String, String> parameters,String signMsg) {
		boolean verifySign=false;
		try {
		
			Map<String, String> parameters_1 = paramsFilter(parameters);
			String createLinkString = createLinkString(parameters_1);
			byte[] datas = createLinkString.getBytes(charset);
			byte[] sign = Base64.decodeBase64(signMsg);
			verifySign= verifySign(datas, sign,cerPath);
			System.out.println("验签名结果：" + verifySign);
		} catch (Exception e) {
			e.printStackTrace();
		}
         return verifySign;
	}
	
	
	/**
	 * 同步报文验证签约
	 * @param cerPath
	 */
	private static void verifySign(String cerPath) {
		try {
			Map<String, String> parameters = paramsFilter(param);
			String createLinkString = createLinkString(parameters);
			byte[] datas = createLinkString.getBytes(charset);
			// 得到的报文中的签名
			String signMsg = signatureInfo;
			byte[] sign = Base64.decodeBase64(signMsg);
			boolean verifySign = verifySign(datas, sign,cerPath);
			System.out.println("验签名结果：" + verifySign);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void sign(String pfxPath,String psw) {
		try {
			SignProvider signProvider = new KeystoreSignProvider("PKCS12",pfxPath,
					psw.toCharArray(), null, psw.toCharArray());
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("productNo", "1FA00AAA");
			parameters.put("service", "proxy_onlineBank_direct_service");
			parameters.put("version", "1.0.0-TEST");
			parameters.put("language", "BG");
			parameters.put("signatureAlgorithm", "RSA");
			parameters.put("merchantId", "2014030600048235");
			parameters.put("callerIp", "218.17.35.123");
		//	parameters.put("extendParams", "");
		//parameters.put("returnUrl", "http://10.36.160.29:8080/cashierDemo/returnUrl.jsp");
			parameters.put("notifyAddr", "http://10.37.20.140:28080/kftpay/cfm/kftResult");
			parameters.put("customerType", "1");
			
			//订单号
			parameters.put("orderNo", "kft1234567896677889905");
			parameters.put("tradeTime", "2016-12-08 15:00:00");
			parameters.put("payPurpose", "1");
			parameters.put("currency", "CNY");
			parameters.put("tradeName", "交易名称");
			parameters.put("subject", "商品名称");
			parameters.put("description", "商品描述");
			parameters.put("amount", "1");
			//parameters.put("singlePrice", "");
			//parameters.put("quantity", "");
			//parameters.put("payerCustName", "");
			//parameters.put("payeeCustName", "");
			parameters.put("bankType", "1051000");
			parameters.put("timeout", "5m");
			//parameters.put("showUrl", "");
			//parameters.put("merchantLogoUrl", "");
			parameters.put("cashierStyle", "1");
			param = parameters;
			Map<String, String> signParameters = signParameters(signProvider,
					parameters, null);
			signatureInfo = signParameters.get("signatureInfo");
			System.out.println(signatureInfo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String sign01(String orderNo) {
		try {
			
			String pfxPath="C:\\pfx\\pfx.pfx";
			String  psw="123456";
			SignProvider signProvider = new KeystoreSignProvider("PKCS12",pfxPath,
					psw.toCharArray(), null, psw.toCharArray());
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("productNo", "1FA00AAA");
			parameters.put("service", "proxy_onlineBank_direct_service");
			parameters.put("version", "1.0.0-TEST");
			parameters.put("language", "BG");
			parameters.put("signatureAlgorithm", "RSA");
			parameters.put("merchantId", "2014030600048235");
			parameters.put("callerIp", "218.17.35.123");
		//	parameters.put("extendParams", "");
			parameters.put("returnUrl", "http://10.36.160.29:8080/cashierDemo/returnUrl.jsp");
			parameters.put("notifyAddr", "http://10.36.160.29:8080/cashierDemo/notify.do");
			parameters.put("customerType", "1");
			
			//订单号
			parameters.put("orderNo", orderNo);
			parameters.put("tradeTime", "2016-12-08 15:00:00");
			parameters.put("payPurpose", "1");
			parameters.put("currency", "CNY");
			parameters.put("tradeName", "交易名称");
			parameters.put("subject", "商品名称");
			parameters.put("description", "商品描述");
			parameters.put("amount", "1");
			//parameters.put("singlePrice", "");
			//parameters.put("quantity", "");
			//parameters.put("payerCustName", "");
			//parameters.put("payeeCustName", "");
			parameters.put("bankType", "1051000");
			parameters.put("timeout", "5m");
			//parameters.put("showUrl", "");
			//parameters.put("merchantLogoUrl", "");
			parameters.put("cashierStyle", "1");
			param = parameters;
			Map<String, String> signParameters = signParameters(signProvider,
					parameters, null);
			signatureInfo = signParameters.get("signatureInfo");
			System.out.println("商户POST请求后加签的密文:"+signatureInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signatureInfo;
	}

	private static Map<String, String> signParameters(
			SignProvider signProvider,
			final Map<String, String> originalParameters,
			List<HttpUploadFile> uploadFiles) throws Exception {
		final Map<String, String> parameters = paramsFilter(originalParameters);
		final String prestr = createLinkString(parameters);
		Charset encoding = Charset.forName(charset);
		String encodeBase64String = signProvider.sign(prestr.getBytes(charset),
				encoding);
		parameters.put("signatureInfo", encodeBase64String);
		parameters.put("signatureAlgorithm", "RSA");
		return parameters;
	}

	/**
	 * 除去参数中的空值和签名参数
	 * 
	 * @param parameters
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	private static Map<String, String> paramsFilter(
			final Map<String, String> parameters) {
		final Map<String, String> result = new HashMap<String, String>(
				parameters.size());
		if (parameters == null || parameters.size() <= 0) {
			return result;
		}
		String value = null;
		for (final String key : parameters.keySet()) {
			value = parameters.get(key);
			if (value == null || "".equals(value)
					|| key.equalsIgnoreCase("signatureAlgorithm")
					|| key.equalsIgnoreCase("signatureInfo")) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

		
		public static String sign(String pfxPath,String psw,Map<String, String> parameters) {
			try {
				SignProvider signProvider = new KeystoreSignProvider("PKCS12",pfxPath,
						psw.toCharArray(), null, psw.toCharArray());
				
				param = parameters;
				Map<String, String> signParameters = signParameters(signProvider,
						parameters, null);
				signatureInfo = signParameters.get("signatureInfo");
				System.out.println(signatureInfo);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return signatureInfo;
		}

	/**
	 * 对请求参数排序，并按照接口规范中所述"参数名=参数值"的模式用"&"字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数
	 * @return 拼接后字符串
	 */
	private static String createLinkString(final Map<String, String> params) {

		final List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		final StringBuilder sb = new StringBuilder();
		String key = null;
		String value = null;
		for (int i = 0; i < keys.size(); i++) {
			key = keys.get(i);
			value = params.get(key);
			sb.append(key).append("=").append(value);
			// 最后一组参数,结尾不包括'&'
			if (i < keys.size() - 1) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * 验证签名,从证书中获取公钥来验证签名是否正确
	 * </p>
	 * 
	 * @param data
	 *            传输的数据
	 * @param sign
	 *            对传输数据的签名
	 * @param certificateContent
	 *            证书内容的2进制形式
	 * @return
	 * @throws Exception
	 * @author 汪一鸣
	 */
	public static boolean verifySign(byte[] data, byte[] sign,
			String certificateContent) throws Exception {
		X509Certificate certificate = (X509Certificate) getCertificate(certificateContent);
		Signature signature = Signature
				.getInstance(certificate.getSigAlgName());
		// 由证书初始化签名,使用了证书中的公钥
		signature.initVerify(certificate);
		signature.update(data);
		return signature.verify(sign);
	}

	/**
	 * <p>
	 * 从证书文件读取证书.'.crt'和'.cer'文件都可以读取 .cer是IE导出的公钥证书（der格式）
	 * </p>
	 * 
	 * @param certificatePath
	 *            证书文件路径:可以直接加载指定的文件,例如"file:C:/kft.cer"
	 * @throws Exception
	 * @author 汪一鸣
	 */
	private static Certificate getCertificate(String certificatePath)
			throws Exception {
		File certificate = new File(certificatePath);
		if (certificate == null
				|| (certificate.exists() && certificate.isDirectory())) {
			throw new IllegalArgumentException("certificatePath["
					+ certificatePath + "]必须是一个已经存在的文件,不能为空,且不能是一个文件夹");
		}
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(certificate);
			// 实例化证书工厂
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Certificate cert = cf.generateCertificate(inputStream);
			return cert;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}

