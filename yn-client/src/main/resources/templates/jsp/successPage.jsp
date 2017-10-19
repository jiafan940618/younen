<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx }/css/main.css">
<style type="text/css">
#qrcode{margin-top:10px}
</style>
<title>create pay success </title>
<style type="text/css">
#code{margin-top:10px}
</style>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.qrcode.min.js"></script>
<script type="text/javascript">
$(function(){
	var resultCode = "${resultCode}";
	if(resultCode == 1){
		$('#error').show();
	}else {
		var channel = "${channel}";
		if(channel == 'wxPubQR' ){
			$('#wxPubQR').show();
			$('#code').qrcode("${codeUrl}");
		}else if(channel == 'wxApp'){
			$('#wxApp').show();
		}else if(channel == 'wxMicro'){
			$('#wxMicro').show();
		}else if(channel == 'wxPub'){
			$('#wxPub').show();
		}else if(channel == 'alipayQR'){
			$('#alipayQR').show();
			$('#codeAlipay').qrcode("${codeUrl}");
		}else if(channel == 'alipayMicro'){
			$('#wxMicro').show();
		}
	}
})
</script>
	
</head>

<body>

	<div class="content">
		<div class="content_0">
			<div id="error" align="center" style="display:none">
	    		<label>错误信息:</label>
				<input type="text"  value="${ errCodeDes }"/><br/>
				<input type="text"  value="${ returnMsg }"/><br/>
			</div>
		
	    	<div id="wxPubQR" align="center" style="display:none">
	    		<label>二维码信息:</label>
				<div id="code"></div>
			</div>
			
	    	<div id="alipayQR" align="center" style="display:none">
	    		<label>二维码信息:</label>
				<div id="codeAlipay"></div>
			</div>
		    
		    <div id="wxApp" align="center" style="display:none">
		    	<label>请解析支付码信息，调用sdk控件发起支付</label><br/>
				<label>支付码信息:</label>
				<input type="text" name="payCode" value="${ payCode }"/><br/>
			</div>
			
			<div id="wxMicro" align="center" style="display:none">
				<label>支付成功（更新订单状态等处理）</label>
			</div>
			
			<div id="alipayMicro" align="center" style="display:none">
				<label>支付成功（更新订单状态等处理）</label>
			</div>
			
			<div id="wxPub" align="center" style="display:none">
				<label>请解析支付码信息，发起支付</label><br/>
				<label>支付码信息:</label>
				<input type="text" name="payCode" value="${ payCode }"/>
			</div>
		
		</div>
	</div>

</body>
</html>
