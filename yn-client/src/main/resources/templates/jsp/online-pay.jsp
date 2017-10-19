<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基金</title>
<link rel="stylesheet" type="text/css" href="%E5%9F%BA%E9%87%91_files/layout.css">
<link rel="stylesheet" type="text/css" href="%E5%9F%BA%E9%87%91_files/global.css">
<link rel="stylesheet" type="text/css" href="%E5%9F%BA%E9%87%91_files/font.css">
<link rel="stylesheet" type="text/css" href="%E5%9F%BA%E9%87%91_files/link.css">



	
	
<script type="text/javascript" src="%E5%9F%BA%E9%87%91_files/sea.js"></script><script type="text/javascript" src="%E5%9F%BA%E9%87%91_files/config.js"></script>
<style type="text/css">
.kft_width20 {
	width: 21.9%;
	text-align: left;
}
</style>
</head>
<body>
	<!--middle content-->
	


    <title>跳转页面</title>


    <form id="form" method="post" action="http://218.17.35.123:8085/cashier-web/trade">
        <input name="productNo" value="${map.productNo }" type="text">
        <input name="service" value="${map.service }" type="text">  
        <input name="version" value="${map.version }" type="text">
        <input name="language" value="${map.language }" type="text">
	<input name="signatureAlgorithm" value="${map.signatureAlgorithm }" type="text">  
        <input name="signatureInfo" value="${map.signatureInfo }" type="text">  
        <input name="merchantId" value="${map.merchantId }" type="text">
        <input name="callerIp" value="${map.callerIp }" type="text">       
	<!--<input name="extendParams" value="" type="text">-->          
        <!--<input name="returnUrl" value="" type="text">--> 
        <input name="notifyAddr" value="${map.notifyAddr }" type="text">  
        <!--<input name="reqNo" value="" type="text">--> 
        <input name="customerType" value="${map.customerType }" type="text">           

        <input name="orderNo" value="${map.orderNo }" type="text">           
        <input name="tradeTime" value="${map.tradeTime }" type="text">  
        <input name="payPurpose" value="${map.payPurpose }" type="text">             
        <input name="currency" value="${map.currency }" type="text">      
        <input name="tradeName" value="${map.tradeName }" type="text">
        <input name="subject" value="${map.subject }" type="text">
        <input name="description" value="${map.description }" type="text">  
        <input name="amount" value="${map.amount }" type="text">
        <!--<input name="singlePrice" value="" type="text">--> 
        <!--<input name="quantity" value="" type="text"> -->                
        <!--<input name="payerCustName" value="" type="text">-->               
        <!--<input name="payeeCustName" value="" type="text">--> 
        <input name="bankType" value="${map.bankType }" type="text"> 
        <input name="timeout" value="${map.timeout }" type="text">        
	<!--<input name="showUrl" value="" type="text"> -->   
        <!--<input name="merchantLogoUrl" value="" type="text">-->                                             
        <input name="cashierStyle" value="${map.cashierStyle }" type="text">   		
    </form>
    <script type="text/javascript">
    	 window.onload = function(){
    		document.getElementById('form').submit();
    	} 
    </script></body></html>