var s1, s2;
var w1, w2, w3, w4;

/* param:
*	pkey(该行订单主键)
*	pid(该行div的id)
*	cid(添加行div的id)
*/
function orderPreview(pkey,pid,cid,basePath)
{
	var urlString=basePath+'/order/order!findOrderDetail.action?oid='+pkey;
	jQuery.ajax({
		type: "POST",
		url: urlString,
		dataType: "json",
		success: function(json)
		{			
			//订单基本信息
			s1 = '<table class="iroaru">'+
				'<tr>'+
					'<th colspan=\'2\'>'+
						'订单商品'+
					'</th>'+
				'</tr>';
				
			if(json.ordersItemList!=null){
				for(var i=0;i<json.ordersItemList.length;i++){
							var temp='<tr>'+								
								'<td>'+
									json.ordersItemList[i].name+
									'<input type="hidden" name="ordersItemList'+i+'.orderItemId" value="'+json.ordersItemList[i].orderItemId+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.name" value="'+json.ordersItemList[i].name+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.barcode" value="'+json.ordersItemList[i].barcode+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.type" value="'+json.ordersItemList[i].type+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.cost" value="'+json.ordersItemList[i].cost+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.price" value="'+json.ordersItemList[i].price+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.nums" value="'+json.ordersItemList[i].nums+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.sendnum" value="'+json.ordersItemList[i].sendnum+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.store" value="'+json.ordersItemList[i].store+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.bn" value="'+json.ordersItemList[i].bn+'">'+
									'<input type="hidden" name="ordersItemList'+i+'.productsId" value="'+json.ordersItemList[i].productsId+'">'+
								'</td>'+								
								'<td>'+
									json.ordersItemList[i].price+" "+"*"+" "+json.ordersItemList[i].nums+
								'</td>'+								
							'</tr>';
							s1=s1+temp;						
				}
				s1=s1+'<input type="hidden" name="orderItemCounter" value="'+json.ordersItemList.length+'">'+
					  '<input type="hidden" name="orderMemo" value="'+json.orderDetail.memo+'">';
			}
			else{
				s1=s1+'<input type="hidden" name="orderItemCounter" value="0">';
			}
			
			s1=s1+'</table>';
			s2 = '<table name="order_detail">'+
				'<tr>'+
					'<input type="hidden" name="orderDetail.ordersId" value="'+json.orderDetail.ordersId+'">'+
					'<input type="hidden" name="orderDetail.membersId" value="'+json.orderDetail.membersId+'">'+
					'<input type="hidden" name="orderDetail.payed" value="'+json.orderDetail.payed+'">'+
					'<input type="hidden" name="orderDetail.state" value="'+json.orderDetail.state+'">'+
					'<input type="hidden" name="orderDetail.payState" value="'+json.orderDetail.payState+'">'+
					'<input type="hidden" name="orderDetail.finalAmount" value="'+json.orderDetail.finalAmount+'">'+
					'<input type="hidden" name="orderDetail.shipState" value="'+json.orderDetail.shipState+'">'+
					'<input type="hidden" name="orderDetail.cratetime" value="'+json.orderDetail.cratetime+'">'+
					'<input type="hidden" name="orderDetail.isProtect" value="'+json.orderDetail.isProtect+'">'+
					'<input type="hidden" name="orderDetail.itemnum" value="'+json.orderDetail.itemnum+'">'+
					'<input type="hidden" name="orderDetail.currency" value="'+json.orderDetail.currency+'">'+
					'<input type="hidden" name="orderDetail.zip" value="'+json.orderDetail.zip+'">'+
					'<input type="hidden" name="orderDetail.mobile" value="'+json.orderDetail.mobile+'">'+
					'<input type="hidden" name="orderDetail.shipAddr" value="'+json.orderDetail.shipAddr+'">'+
					'<th colspan="2">'+
						'商品价格'+
					'</th>'+
					'<th colspan="2">'+
						'订单其他信息'+
					'</th>'+
					'<th colspan="2">'+
						'购买人信息'+
					'</th>'+
					'<th colspan="2">'+
						'收货人信息'+
					'</th>'+
				'</tr>'+
				'<tr>'+
					'<td class="right">'+
						'商品总额:'+
					'</td>'+
					'<td>'+
						json.orderDetail.costItem+
					'</td>'+
					'<td class="right">'+
						'配送地区:'+
					'</td>'+
					'<td>'+
						json.orderDetail.shipAreaOrder+
						'<input type="hidden" name="orderDetail.shipAreaOrder" value="'+json.orderDetail.shipAreaOrder+'">'+
					'</td>'+
					'<td class="right">'+
						'用户名:'+
					'</td>'+
					'<td>'+
						json.orderDetail.uname+
					'</td>'+
					'<td class="right">'+
						'姓名:'+
					'</td>'+
					'<td>'+
						json.orderDetail.shipName+
						'<input type="hidden" name="orderDetail.shipname" value="'+json.orderDetail.shipName+'">'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td class="right">'+
						'配送费用:'+
					'</td>'+
					'<td>'+
						json.orderDetail.costFreight+
						'<input type="hidden" name="orderDetail.costFreight" value="'+json.orderDetail.costFreight+'">'+
					'</td>'+
					'<td class="right">'+
						'配送方式:'+
					'</td>'+
					'<td>'+
						json.orderDetail.shipping+
						'<input type="hidden" name="orderDetail.shipping" value="'+json.orderDetail.shipping+'">'+
					'</td>'+
					'<td class="right">'+
						'姓名:'+
					'</td>'+
					'<td>'+
						json.orderDetail.name+
						'<input type="hidden" name="orderDetail.name" value="'+json.orderDetail.name+'">'+
					'</td>'+
					
					'<td class="right">'+
						'邮编:'+
					'</td>'+
					'<td>'+
						json.orderDetail.shipZip+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td class="right">'+
						'商品税金:'+
					'</td>'+
					'<td>'+
						json.orderDetail.costTax+
						'<input type="hidden" name="orderDetail.costTax" value="'+json.orderDetail.costTax+'">'+
					'</td>'+
					'<td class="right">'+
						'配送保价:'+
					'</td>'+
					'<td>';
					var temp1= json.orderDetail.costProtect;
					if(temp1=='1')s2=s2+'是';
					else s2=s2+'否';
						s2=s2+'<input type="hidden" name="orderDetail.costProtect" value="'+json.orderDetail.costProtect+'">'+
					'</td>'+
					'<td class="right">'+
						'电话:'+
					'</td>'+					
					'<td>'+
						json.orderDetail.tel+
						'<input type="hidden" name="orderDetail.Tel" value="'+json.orderDetail.tel+'">'+
					'</td>'+
					'<td class="right">'+
						'电话:'+
					'</td>'+
					'<td>'+
						json.orderDetail.shipTel+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td class="right">'+
						'订单总额:'+
					'</td>'+
					'<td>'+
						json.orderDetail.totalAmount+
						'<input type="hidden" name="orderDetail.totalAmount" value="'+json.orderDetail.totalAmount+'">'+
					'</td>'+
					'<td class="right">'+
						'商品重量:'+
					'</td>'+
					'<td>'+
						json.orderDetail.weight+
					'</td>'+
					'<td class="right">'+
						'地区:'+
					'</td>'+
					'<td>'+
						json.orderDetail.area+
					'</td>'+
					'<td class="right">'+
						'手机:'+
					'</td>'+
					'<td>'+
						json.orderDetail.shipMobile+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td class="right" >'+
						'已支付金额:'+
					'</td>'+
					'<td>'+
						json.orderDetail.payed+
						'<input type="hidden" name="orderDetail.payed" value="'+json.orderDetail.payed+'">'+
					'</td>'+
					'<td class="right">'+
						'支付方式:'+
					'</td>'+
					'<td>'+
						json.orderDetail.payment+
					'</td>'+
					'<td class="right">'+
						'email:'+
					'</td>'+
					'<td>'+
						json.orderDetail.email+
					'</td>'+
					'<td class="right">'+
						'地区:'+
					'</td>'+
					'<td>'+
						json.orderDetail.shipArea+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td class="right">'+
						'是否开票:'+
					'</td>'+
					'<td>';
					temp1=json.orderDetail.isTax;
					if(temp1=='1')s2=s2+'是';
					else s2=s2+'否';
						s2=s2+'<input type="hidden" name="orderDetail.isTax" value="'+json.orderDetail.isTax+'">'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td class="right">'+
						'地址:'+
					'</td>'+
					'<td>'+
						json.orderDetail.shipAddr+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td class="right">'+
						'发票抬头:'+
					'</td>'+
					'<td>'+
						json.orderDetail.taxCompany+
						'<input type="hidden" name="orderDetail.taxCompany" value="'+json.orderDetail.taxCompany+'">'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td class="right">'+
						'可得积分:'+
					'</td>'+
					'<td>'+
						json.orderDetail.scoreG+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td class="right">'+
						'会员备注:'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td class="right">'+
						'订单备注:'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
					'<td>'+
					'</td>'+
				'</tr>'+
			'</table>';
			var unpaid=json.orderDetail.finalAmount-json.orderDetail.payed;
			var totalSendnum=0;
			if(json.ordersItemList!=null)
			for(var counterI=0;counterI<json.ordersItemList.length;counterI++)
				totalSendnum+=json.ordersItemList[counterI].sendnum;
				
			var totalUnsendnum=json.orderDetail.itemnum-totalSendnum;
			if(parseInt(json.orderDetail.payed)<parseInt(json.orderDetail.finalAmount)&&parseInt(json.orderDetail.payState)!=2&&parseInt(json.orderDetail.state)<2)
				jQuery("#"+pid+" input[name='payButton']").removeAttr('disabled');
			else
				jQuery("#"+pid+" input[name='payButton']").attr('disabled','disabled');
			if(parseInt(json.orderDetail.payed)>0&&parseInt(json.orderDetail.state)<2)
				jQuery("#"+pid+" input[name='refundButton']").removeAttr('disabled');
			else
				jQuery("#"+pid+" input[name='refundButton']").attr('disabled','disabled');
			if(totalUnsendnum>0&&parseInt(json.orderDetail.state)<2)
				jQuery("#"+pid+" input[name='deliveryButton']").removeAttr('disabled');
			else
				jQuery("#"+pid+" input[name='deliveryButton']").attr('disabled','disabled');
			if(totalSendnum>0&&parseInt(json.orderDetail.state)<2)
				jQuery("#"+pid+" input[name='returnDeliButton']").removeAttr('disabled');
			else
				jQuery("#"+pid+" input[name='returnDeliButton']").attr('disabled','disabled');
			if(json.orderDetail.state==0||json.orderDetail.state==1)
				jQuery("#"+pid+" input[name='finishButton']").removeAttr('disabled');
			else
				jQuery("#"+pid+" input[name='finishButton']").attr('disabled','disabled');
			if(json.orderDetail.state==0)
				jQuery("#"+pid+" input[name='abortButton']").removeAttr('disabled');
			else
				jQuery("#"+pid+" input[name='abortButton']").attr('disabled','disabled');
			
			jQuery("#"+pid+" .order_baseInfo").append(s1+s2);
		},
		error: function()
		{
			
		}
	});
	str = '<div class="order_state">'+
	'订单状态操作:'+
	'<input name="payButton" type="button" value="支付..." onclick="zhifu(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')"/>'+
	'<input name="refundButton" type="button" value="退款..." onclick="tuikuan(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')"/>'+
	'<input name="deliveryButton" type="button" value="发货..." onclick="fahuo(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')"/>'+
	'<input name="returnDeliButton" type="button" value="退货..." onclick="tuihuo(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')"/>'+
	'<input name="finishButton" type="button" value="完成..." onclick="wancheng(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')"/>'+
	'<input name="abortButton" type="button" value="作废..." onclick="zuofei(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')"/>'+
	'</div>'+
'<div class="order_tab">'+
	'<a href="javascript:orderTabChange(\'order_baseInfo\',\''+pid+'\',\''+basePath+'\',\''+pkey+'\')" class="order_tab_on" id="'+pid+'_order_baseInfo">基本信息</a>'+
	'<a href="javascript:orderTabChange(\'order_goods\',\''+pid+'\',\''+basePath+'\',\''+pkey+'\')" id="'+pid+'_order_goods">商品</a>'+
	'<a href="javascript:orderTabChange(\'order_tuikuan\',\''+pid+'\',\''+basePath+'\',\''+pkey+'\')" id="'+pid+'_order_tuikuan">收退款记录</a>'+
	'<a href="javascript:orderTabChange(\'order_fatuihuo\',\''+pid+'\',\''+basePath+'\',\''+pkey+'\')" id="'+pid+'_order_fatuihuo">发退货记录</a>'+
//  '<a href="javascript:orderTabChange(\'order_youhui\',\''+pid+'\',\''+basePath+'\',\''+pkey+'\')" id="'+pid+'_order_youhui">优惠方案</a>'+
	'<a href="javascript:orderTabChange(\'order_beizhu\',\''+pid+'\',\''+basePath+'\',\''+pkey+'\')" id="'+pid+'_order_beizhu">订单备注</a>'+
	'<a href="javascript:orderTabChange(\'order_rizhi\',\''+pid+'\',\''+basePath+'\',\''+pkey+'\')" id="'+pid+'_order_rizhi">订单日志</a>'+
'</div>'+
'<div class="order_content">'+
	'<div class="order_baseInfo">'+
	'</div>'+
	'<div class="order_goods">'+
	'</div>'+
	'<div class="order_tuikuan">'+
	'</div>'+
	'<div class="order_fatuihuo">'+
	'</div>'+
	'<div class="order_youhui">'+
	'</div>'+
	'<div class="order_beizhu">'+
	'</div>'+
	'<div class="order_rizhi">'+
	'</div>'+
'</div>';
	return str;
}

function orderMemo(pkey,basePath,pid)
{
	var orderMemo=jQuery('#'+pid+' textarea[name=orderMemo]').val();
	jQuery.ajax({
					type: "POST",
					url: basePath+'/order/order!updateOrderMemo.action?oid='+pkey+'&orderMemo='+orderMemo,
					data: '',
					dataType: "json",
					success: function(json)
					{
						orderTabChange('order_beizhu',pid,basePath,pkey)
					},
					error: function()
					{
						return;
					}
				});
}


function orderTabChange(id,pid,basePath,pkey)
{
	if(jQuery("#"+pid+" .order_content>."+id).css("display")=="block"){
		return;
	}
	if(jQuery("#"+pid+" .order_content>."+id).html()=='')
	{
		var ss = '';
		switch(id)
		{
			case 'order_baseInfo':
				break;
			case 'order_goods'://商品
						ss = '<table class="iroaru" name="order_items">'+
							'<tr>'+
								'<th>'+
									'货号'+
								'</th>'+
								'<th>'+
									'商品名称'+
								'</th>'+
								'<th>'+
									'分类'+
								'</th>'+
								'<th>'+
									'成本价格'+
								'</th>'+
								'<th>'+
									'实际价格'+
								'</th>'+
								'<th>'+
									'购买量'+
								'</th>'+
								'<th>'+
									'已发货量'+
								'</th>'+
							'</tr>';
					var counter=jQuery('#'+pid+' input[name=orderItemCounter]').val();
					if(counter>0){
						for(var i=0;i<counter;i++){
							var temp='<tr>'+
								'<td>'+
									jQuery('#'+pid+' input[name=ordersItemList'+i+'.barcode]').val()+
								'</td>'+
								'<td>'+
									jQuery('#'+pid+' input[name=ordersItemList'+i+'.name]').val()+
								'</td>'+
								'<td>'+
									jQuery('#'+pid+' input[name=ordersItemList'+i+'.type]').val()+
								'</td>'+
								'<td>'+
									jQuery('#'+pid+' input[name=ordersItemList'+i+'.cost]').val()+
								'</td>'+
								'<td>'+
									jQuery('#'+pid+' input[name=ordersItemList'+i+'.price]').val()+
								'</td>'+
								'<td>'+
									jQuery('#'+pid+' input[name=ordersItemList'+i+'.nums]').val()+
								'</td>'+
								'<td>'+
									jQuery('#'+pid+' input[name=ordersItemList'+i+'.sendnum]').val()+
								'</td>'+
							'</tr>';
							ss=ss+temp;
						}
					}
						ss=ss+'</table>';
						jQuery("#"+pid+" .order_content>."+id).append(ss);
					;
				break;
			case 'order_tuikuan'://收退款记录
				jQuery.ajax({
					type: "POST",
					url: basePath+'/order/order!findPaymentAndRefundByOrderId.action?oid='+pkey,
					data: '',
					dataType: "json",
					success: function(json)
					{
						ss = '<div class="table_title">收款单据列表</div>'+
						'<table class="iroaru">'+
							'<tr>'+
								'<th>'+
									'单据日期'+
								'</th>'+
								'<th>'+
									'支付金额'+
								'</th>'+
								'<th>'+
									'支付方式'+
								'</th>'+
								'<th>'+
									'状态'+
								'</th>'+
							'</tr>';
					if(json.paymentList!=null){
						var tempPayment=null;
						for(var i=0;i<json.paymentList.length;i++){
							ss=ss+'<tr>'+
								'<td>'+
									json.paymentList[i].TBegin+
								'</td>'+
								'<td>'+
									json.paymentList[i].curMoney+
								'</td>'+
								'<td>'+
									json.paymentList[i].payType+
								'</td>'+
								'<td>';
								tempPayment=json.paymentList[i].status;
								if(tempPayment=='0')ss=ss+'成功';
								else if(tempPayment=='1')ss=ss+'失败';
								else ss=ss+'准备';
								ss=ss+'</td>'+
							'</tr>';
						}
					}
						ss=ss+'</table>'+
						'<div class="table_title">退款单据列表</div>'+
						'<table class="iroaru">'+
							'<tr>'+
								'<th>'+
									'单据日期'+
								'</th>'+
								'<th>'+
									'支付金额'+
								'</th>'+
								'<th>'+
									'支付方式'+
								'</th>'+
								'<th>'+
									'状态'+
								'</th>'+
							'</tr>';
					if(json.refundList!=null){
					 	var tempRefund=null;
						for(var i=0;i<json.refundList.length;i++){
						ss=ss+'<tr>'+
								'<td>'+
									json.refundList[i].TReady+
								'</td>'+
								'<td>'+
									json.refundList[i].money+
								'</td>'+
								'<td>'+
									json.refundList[i].payType+
								'</td>'+
								'<td>';
									tempRefund=json.refundList[i].status;
									if(tempRefund=='0')ss=ss+'成功';
									else if(tempRefund=='1')ss=ss+'失败';
									else ss=ss+'准备';
								ss=ss+'</td>'+
							'</tr>';
						}
					}
						ss=ss+'</table>';
						jQuery("#"+pid+" .order_content>."+id).append(ss);
					},
					error: function()
					{
						return;
					}
				});
				break;
			case 'order_fatuihuo'://发退货记录
				jQuery.ajax({
					type: "POST",
					url: basePath+'/order/order!findDeliveryAndReturnByOrderId.action?oid='+pkey,
					data: '',
					dataType: "json",
					success: function(json)
					{
						ss = '<div class="table_title">发货单据列表</div>'+
						'<table class="iroaru">'+
							'<tr>'+
								'<th>'+
									'建立日期'+
								'</th>'+
								'<th>'+
									'发货单号'+
								'</th>'+
								'<th>'+
									'物流公司'+
								'</th>'+
								'<th>'+
									'收件人'+
								'</th>'+
								'<th>'+
									'配送方式'+
								'</th>'+
							'</tr>';
							
						if(json.deliveryList!=null){
							for(var i = 0;i<json.deliveryList.length;i++){
								ss=ss+'<tr>'+
									'<td>'+
										json.deliveryList[i].TBegin+
									'</td>'+
									'<td>'+
										json.deliveryList[i].deliveryId+
									'</td>'+
									'<td>'+
										json.deliveryList[i].logiName+
									'</td>'+
									'<td>'+
										json.deliveryList[i].shipName+
									'</td>'+
									'<td>'+
										json.deliveryList[i].delivery+
									'</td>'+
								'</tr>';
							}
						}
						ss=ss+'</table>'+
						'<div class="table_title">退货单据列表</div>'+
						'<table class="iroaru">'+
							'<tr>'+
								'<th>'+
									'建立日期'+
								'</th>'+
								'<th>'+
									'退货单号'+
								'</th>'+
								'<th>'+
									'物流单号'+
								'</th>'+
								'<th>'+
									'退货人'+
								'</th>'+
								'<th>'+
									'配送方式'+
								'</th>'+
							'</tr>';
						if(json.returnDeliveryList!=null){
							for(var j=0;j<json.returnDeliveryList.length;j++){
								ss=ss+'<tr>'+
									'<td>'+
										json.returnDeliveryList[j].TBegin+
									'</td>'+
									'<td>'+
										json.returnDeliveryList[j].id+
									'</td>'+
									'<td>'+
										json.returnDeliveryList[j].shippingNum+
									'</td>'+
									'<td>'+
										json.returnDeliveryList[j].name+
									'</td>'+
									'<td>'+
										json.returnDeliveryList[j].delivery+
									'</td>'+
								'</tr>';
							}
						}
						ss=ss+'</table>';
						jQuery("#"+pid+" .order_content>."+id).append(ss);
					},
					error: function()
					{
						return;
					}
				});
				break;
			case 'order_youhui'://优惠方案
				jQuery.ajax({
					type: "POST",
					url: '',
					data: '',
					dataType: "json",
					success: function(json)
					{
						ss = '<table class="iroaru">'+
							'<tr>'+
								'<th>'+
									'优惠方案'+
								'</th>'+
								'<th>'+
									'优惠金额'+
								'</th>'+
							'</tr>'+
							'<tr>'+
								'<td>'+
									'G4820050C1B519-1'+
								'</td>'+
								'<td>'+
									'1'+
								'</td>'+
							'</tr>'+
						'</table>';
						jQuery("#"+pid+" .order_content>."+id).append(ss);	
					},
					error: function()
					{
						return;
					}
				});
				break;
			case 'order_beizhu':
				var temp=jQuery('#'+pid+' input[name=orderMemo]').val()
				var ss='<textarea id="orderMemo" name="orderMemo">'+temp+'</textarea><br /><input type="button" value=" 保存 " onclick="orderMemo(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')">';				
				jQuery("#"+pid+" .order_beizhu."+id).append(ss);
				break;
			case 'order_rizhi'://订单日志
				jQuery.ajax({
					type: "POST",
					url: basePath+'/order/order!findOrderLogs.action?oid='+pkey,
					data: '',
					dataType: "json",
					success: function(json)
					{
						var i=0;
						ss = '<table class="iroaru">'+
								'<tr>'+
									'<th>'+
										'序号'+
									'</th>'+
									'<th>'+
										'时间'+
									'</th>'+
									'<th>'+
										'操作人'+
									'</th>'+
									'<th>'+
										'结果'+
									'</th>'+
									'<th>'+
										'行为'+
									'</th>'+
									'<th>'+
										'备注'+
									'</th>'+
								'</tr>';
					if(json.orderLogs!=null){
						for(i=0;i<json.orderLogs.length;i++){
							var temp ='<tr>'+
									'<td>'+
										json.orderLogs[i].logId+
									'</td>'+
									'<td>'+
										json.orderLogs[i].acttime+
									'</td>'+
									'<td>'+
										json.orderLogs[i].adminName+
									'</td>'+
									'<td>'+
										json.orderLogs[i].result+
									'</td>'+
									'<td>'+
										json.orderLogs[i].behavior+
									'</td>'+
									'<td>'+
										json.orderLogs[i].logText+
									'</td>'+
								'</tr>';	
							ss=ss+temp;
						}	
					}
						ss=ss+'</table>';
						jQuery("#"+pid+" .order_content>."+id).append(ss);				
					},
					error: function()
					{
						alert('error');
						return;
					}
				});
				break;
		}
//		if(ss!=''){
//			jQuery("#"+pid+" .order_content>."+id).append(ss);
//		}
	}
	
	jQuery("#"+pid+" .order_content>div").hide();
	jQuery("#"+pid+" .order_content>."+id).show();
	jQuery("#"+pid+" .order_tab a").removeClass("order_tab_on");
	jQuery("#"+pid+"_"+id).addClass("order_tab_on");
}

function zhifu(pkey,basePath,pid)
{
	//支付弹出框	
			w1 = '<table width="90%">'+
				'<tr>'+
					'<th>订单号：</th>'+
					'<td>'+jQuery('#'+pid+' input[name=orderDetail.ordersId]').val()+
					'<input type="hidden" name="orderPayment.ordersId" value="'+jQuery('#'+pid+' input[name=orderDetail.ordersId]').val()+'">'+
					'</td>'+
					'<th>下单日期：</th>'+
					'<td>'+jQuery('#'+pid+' input[name=orderDetail.cratetime]').val()+'</td>'+
				'</tr>';
				w1+='<tr>'+
					'<th>订单总金额：</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.totalAmount]').val()+
						'<input type="hidden" name="orderPayment.money" value="'+jQuery('#'+pid+' input[name=orderDetail.totalAmount]').val()+'">'+
					'</td>'+
					'<th>已收金额：</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.payed]').val()+
						'<input type="hidden" name="orderPayment.paycost" value="'+jQuery('#'+pid+' input[name=orderDetail.payed]').val()+'">'+
					'</td>'+
				'</tr>'+
			'</table>'+
			'<table width="90%">'+
				'<tr>'+
					'<th>收款银行：</th>'+
					'<td colspan="3"><input name="orderPayment.bank" type="text" name="bank" autocomplete="off" vtype="text" />&nbsp;&nbsp;&nbsp;&nbsp;'+
							'</td>'+
				'</tr>'+
				'<tr>'+
					'<th>收款帐号：</th>'+
					'<td colspan="3"><input name="orderPayment.payAccount" type="text" name="account" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>收款金额：</th>'+
					'<td colspan="3"><input name="orderPayment.curMoney" value="'+(jQuery('#'+pid+' input[name=orderDetail.totalAmount]').val()-jQuery('#'+pid+' input[name=orderDetail.payed]').val())+'" type="text" name="money" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>付款类型：</th>'+
					'<td colspan="3"><label>'+
						'<input value="online" type="radio" name="orderPayment.payType" vtype="radio" />'+
						'在线支付</label>'+
							'<label>'+
								'<input value="offline" checked="checked" type="radio" name="orderPayment.payType" vtype="radio" />'+
								'线下支付</label>'+
							'<label>'+
								'<input value="deposit" type="radio" name="orderPayment.payType" vtype="radio" />'+
								'预存款支付</label></td>'+
				'</tr>'+
				'<tr>'+
					'<th>支付方式：</th>'+
					'<td><select name="orderPayment.paymethod" vtype="select" selected="" value="" type="select" labelcolumn="custom_name" valuecolumn="id">'+
						'<option label="预存款支付" selected="selected" value="1">预存款支付</option>'+
						'<option label="我付了储值卡支付（OK卡等）" value="25">我付了储值卡支付（OK卡等）</option>'+
						'<option label="易宝网上支付" value="24">易宝网上支付</option>'+
						'<option label="财付通（特别推荐） " value="23">财付通（特别推荐）</option>'+
						'<option label="支付宝" value="17">支付宝</option>'+
					'</select></td>'+
					'<th>客户支付货币:</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.currency]').val()+
						'<input type="hidden" name="orderPayment.currency" value="'+jQuery('#'+pid+' input[name=orderDetail.currency]').val()+'">'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<th>是否开票：</th>'+
					'<td>';
					if(jQuery('#'+pid+' input[name=orderDetail.isTax]').val()==0){
						w1=w1+'否';
					}
					else{
						w1=w1+'是';
					}
					w1=w1+'<input type="hidden" name="orderPayment.isTax" value="'+jQuery('#'+pid+' input[name=orderDetail.isTax]').val()+'">'+
					'</td>'+
					'<th>商品税金：</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.costTax]').val()+
						'<input type="hidden" name="orderPayment.costTax" value="'+jQuery('#'+pid+' input[name=orderDetail.costTax]').val()+'">'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<th>发票抬头：</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.taxCompany]').val()+
					'</td>'+
					'<th>付款人：</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.name]').val()+'" type="text" name="orderPayment.memberName autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>收款单备注：</th>'+
					'<td colspan="3"><textarea rows="2" cols="40" name="orderPayment.memo"  value=""></textarea></td>'+
				'</tr>'+
			'</table>';
					
			showWin();
			jQuery(".order_win").append('<div class="winTitle" onmousedown="moveStart(this,event)">支付</div>'+
				w1 +
				'<div style="text-align:center"><input type="button" value=" 确认 " onclick="zhifu_submit(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value=" 取消 " onclick="hideWin()" /></div>');
		
}
function tuikuan(pkey,basePath,pid)
{
	//退款弹出框
	jQuery.ajax({
		type: "POST",
		url: basePath+'/order/order!findOrderDetail.action?oid='+pkey,
		dataType: "json",
		success: function(json)
		{
			w2 = '<table width="90%">'+
				'<tr>'+
					'<th>订单号:</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.ordersId]').val()+					
					'<input value="'+jQuery('#'+pid+' input[name=orderDetail.ordersId]').val()+'" type="hidden" name="orderRefund.ordersId" />'+
					'</td>'+
					'<th>下单日期:</th>'+
					'<td>'+jQuery('#'+pid+' input[name=orderDetail.cratetime]').val()+'</td>'+
				'</tr>'+
				'<tr>'+
					'<th>订单金额:</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.totalAmount]').val()+
						'</td>'+
					'<th>已付金额:</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.payed]').val()+
						'</td>'+
				'</tr>'+
			'</table>'+
			'<table border="0" cellspacing="2" cellpadding="2" width="90%">'+
				'<tr>'+
					'<th>退款类型:</th>'+
					'<td><label>'+
						'<input value="online" type="radio" name="orderRefund.payType" vtype="radio" />'+
						'在线支付</label>'+
							'<label>'+
								'<input value="offline" checked="checked" type="radio" name="orderRefund.payType" vtype="radio" />'+
								'线下支付</label>'+
							'<label>'+
								'<input value="deposit" type="radio" name="orderRefund.payType" vtype="radio" />'+
								'预存款支付</label></td>'+
					'<th>退款方式:</th>'+
					'<td><select name="orderRefund.paymethod" vtype="select" selected="" value="" type="select" labelcolumn="custom_name" valuecolumn="id">'+
						'<option label="预存款支付" selected="selected" value="1">预存款支付</option>'+
						'<option label="我付了储值卡支付（OK卡等）" value="25">我付了储值卡支付（OK卡等）</option>'+
						'<option label="易宝网上支付" value="24">易宝网上支付</option>'+
						'<option label="财付通（特别推荐） " value="23">财付通（特别推荐）</option>'+
						'<option label="支付宝" value="17">支付宝</option>'+
					'</select></td>'+
					
				'</tr>'+
				'<tr>'+
					'<th>退款银行:</th>'+
					'<td><input name="orderRefund.bank" type="text" autocomplete="off" vtype="text" />'+
							'</td>'+
					'<th>客户支付货币:</th>'+
					'<td>'+
						jQuery('#'+pid+' input[name=orderDetail.currency]').val()+
						'<input type="hidden" name="orderRefund.currency" value="'+jQuery('#'+pid+' input[name=orderDetail.currency]').val()+'">'+
					'</td>'+
				'</tr>'+
				'<tr>'+
					'<th>退款帐号:</th>'+
					'<td><input name="orderRefund.payAccount" type="text" autocomplete="off" vtype="text" /></td>'+
					'<th>收款人:</th>'+
					'<td><input value="" type="text" name="orderRefund.name" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>退款金额:</th>'+
					'<td><input value="" type="text" name="orderRefund.money" autocomplete="off" vtype="text" /></td>'+
					'<th>扣除用户积分:</th>'+
					'<td><input value="" type="text" name="orderRefund_score" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'</tr>'+
				'<tr>'+
					'<th>退款单备注：</th>'+
					'<td colspan="3"><textarea rows="2" cols="40" name="orderRefund.memo" value=""></textarea></td>'+
				'</tr>'+
			'</table>';
			showWin();
			jQuery(".order_win").append('<div class="winTitle" onmousedown="moveStart(this,event)">退款</div>'+
				w2 +
				'<div style="text-align:center"><input type="button" value=" 确认 " onclick="tuikuan_submit(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value=" 取消 " onclick="hideWin()" /></div>');
		},
		error: function()
		{
		}
	});
}
function fahuo(pkey,basePath,pid)
{
	//发货弹出框
			w3 = '<table cellspacing="0" cellpadding="0" width="90%">'+
				'<tr>'+
					'<th>订单号:</th>'+
					'<td>'+jQuery('#'+pid+' input[name=orderDetail.ordersId]').val()+
					'<input type="hidden" name="orderDelivery.ordersId" value="'+jQuery('#'+pid+' input[name=orderDetail.ordersId]').val()+'">'+
					'</td>'+
					'<th>下单日期:</th>'+
					'<td>'+jQuery('#'+pid+' input[name=orderDetail.cratetime]').val()+'</td>'+
				'</tr>'+
				'<tr>'+
					'<th>配送方式:</th>'+
					'<td><select name="delivery_type" vtype="select" selected="'+
						jQuery('#'+pid+' input[name=orderDetail.shipping]').val()+'" value="" type="select" labelcolumn="dt_name" valuecolumn="dt_name">';
					
					var temp1=jQuery('#'+pid+' input[name=orderDetail.shipping]').val();

					if(temp1=='顺丰快递'){
						w3=w3+
						'<option label="顺丰快递"  value="顺丰快递" selected>顺丰快递</option>'+
						'<option label="EMS快递" value="EMS快递">EMS快递</option>'+
						'<option label="FedEx联邦快递" value="FedEx联邦快递">FedEx联邦快递</option>';
					}else if(temp1=='EMS快递'){
						w3=w3+
						'<option label="顺丰快递"  value="顺丰快递">顺丰快递</option>'+
						'<option label="EMS快递" value="EMS快递" selected>EMS快递</option>'+
						'<option label="FedEx联邦快递" value="FedEx联邦快递">FedEx联邦快递</option>';	
					}else{
						w3=w3+
						'<option label="顺丰快递"  value="顺丰快递">顺丰快递</option>'+
						'<option label="EMS快递" value="EMS快递">EMS快递</option>'+
						'<option label="FedEx联邦快递" value="FedEx联邦快递" selected>FedEx联邦快递</option>';
					}
						
					
					w3=w3+'</select></td>'+
					'<th>配送费用:</th>'+
					'<td>'+jQuery('#'+pid+' input[name=orderDetail.costFreight]').val()+'</td>'+
					'<input type="hidden" name="delivery.money" value="'+jQuery('#'+pid+' input[name=orderDetail.costFreight]').val()+'">'+
				'</tr>'+
				'<tr>'+
					'<th>配送地区:</th>'+
					'<td>'+jQuery('#'+pid+' input[name=orderDetail.shipAreaOrder]').val()+'</td>'+
					'<th>是否保价:</th>';
				if(jQuery('#'+pid+' input[name=orderDetail.isProtect]').val()=="1")
					w3+='<td>是</td>';
					else
					w3+='<td>否</td>';
				w3+='</tr>'+
			'</table>'+
			'<table cellspacing="0" cellpadding="0" width="90%">'+
				'<tr>'+
					'<th>物流公司:</th>'+
					'<td><select name="delivery_com" vtype="select" type="select" labelcolumn="name" valuecolumn="corp_id">'+
						
						'<option label="中通速递" value="1">中通速递</option>'+
						'<option label="邮政EMS" value="5">邮政EMS</option>'+
						'<option label="圆通速递" value="10">圆通速递</option>'+
						'<option label="FedEx" value="11">FedEx</option>'+
						'</select></td>'+
					'<th>物流单号:</th>'+
					'<td><input type="text" name="delivery_shippingNum" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>物流费用:</th>'+
					'<td><input value="" type="text" name="delivery_shippingCost" autocomplete="off" vtype="text" /></td>'+
					'<th>物流保价:</th>'+
					'<td><label>'+
						'<input value="false" checked="checked" type="radio" name="delivery_is_protect" vtype="radio" />'+
						'否</label>'+
							'<label>'+
								'<input value="true" type="radio" name="delivery_is_protect" vtype="radio" />'+
								'是</label></td>'+
				'</tr>'+
				'<tr>'+
					'<th>保价费用:</th>'+
					'<td><input value="" type="text" name="cost_freight" autocomplete="off" vtype="text" /></td>'+
					'<th></th>'+
					'<td></td>'+
				'</tr>'+
				'<tr>'+
					'<th>收货人姓名:</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.shipname]').val()+'" type="text" name="shipName" autocomplete="off" vtype="text" /></td>'+
					'<th>电话:</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.Tel]').val()+'" type="text" name="shipTel" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>手机:</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.mobile]').val()+'" type="text" name="shipMobile" autocomplete="off" vtype="text" /></td>'+
					'<th>邮政编码:</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.zip]').val()+'" type="text" name="shipZip" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>地区:</th>'+
					'<td colspan="3"><span vtype="" package="mainland">'+
						'<input type="hidden" name="ship_area" />'+
						'<select name="shipArea">'+
							'<option selected="selected" value="_NULL_">请选择...</option>'+
							'<option value="1" has_c="true">﻿北京</option>'+
							'<option value="21" has_c="true">上海</option>'+
							'<option value="42" has_c="true">天津</option>'+
							'<option value="62" has_c="true">重庆</option>'+
							'<option value="104" has_c="true">安徽</option>'+
							'<option value="227" has_c="true">福建</option>'+
							'<option value="322" has_c="true">甘肃</option>'+
							'<option value="423" has_c="true">广东</option>'+
							'<option value="566" has_c="true">广西</option>'+
							'<option value="690" has_c="true">贵州</option>'+
							'<option value="788" has_c="true">海南</option>'+
							'<option value="814" has_c="true">河北</option>'+
							'<option value="998" has_c="true">河南</option>'+
							'<option value="1176" has_c="true">黑龙江</option>'+
							'<option value="1320" has_c="true">湖北</option>'+
							'<option value="1436" has_c="true">湖南</option>'+
							'<option value="1573" has_c="true">吉林</option>'+
							'<option value="1643" has_c="true">江苏</option>'+
							'<option value="1763" has_c="true">江西</option>'+
							'<option value="1874" has_c="true">辽宁</option>'+
							'<option value="1989" has_c="true">内蒙古</option>'+
							'<option value="2103" has_c="true">宁夏</option>'+
							'<option value="2130" has_c="true">青海</option>'+
							'<option value="2182" has_c="true">山东</option>'+
							'<option value="2340" has_c="true">山西</option>'+
							'<option value="2471" has_c="true">陕西</option>'+
							'<option value="2589" has_c="true">四川</option>'+
							'<option value="2792" has_c="true">西藏</option>'+
							'<option value="2873" has_c="true">新疆</option>'+
							'<option value="2987" has_c="true">云南</option>'+
							'<option value="3133" has_c="true">浙江</option>'+
							'<option value="3235" has_c="true">香港</option>'+
							'<option value="3239" has_c="true">澳门</option>'+
							'<option value="3242" has_c="true">台湾</option>'+
						'</select>'+
					'</span></td>'+
				'</tr>'+
				'<tr>'+
					'<th>地址:</th>'+
					'<td colspan="3"><input value="'+jQuery('#'+pid+' input[name=orderDetail.shipAddr]').val()+'" type="text" name="shipAddr" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>发货单备注:</th>'+
					'<td colspan="3"><textarea name="shipMemo" vtype="textarea" type="textarea"></textarea></td>'+
				'</tr>'+
			'</table>'+
			'<table cellspacing="0" cellpadding="0" width="90%">'+
				'<tr class="head">'+
					'<th>货号</th>'+
					'<th>商品名称</th>'+
					'<th>当前库存</th>'+
					'<th>购买数量</th>'+
					'<th>已发货</th>'+
					'<th>此单发货数量</th>'+
				'</tr>';
				var counter=parseInt(jQuery('#'+pid+' input[name=orderItemCounter]').val());
			
				for(var i=0;i<counter;i++){
				w3=w3+'<tr>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.barcode]').val()+'</td>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.name]').val()+'</td>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.store]').val()+'</td>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.nums]').val()+'</td>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.sendnum]').val()+'</td>'+
					'<td><input value="'+(jQuery('#'+pid+' input[name=ordersItemList'+i+'.nums]').val()-jQuery('#'+pid+' input[name=ordersItemList'+i+'.sendnum]').val())+'" type="text" name="delivery_num'+i+'" autocomplete="off" vtype="text" /></td>'+
					
					
					'<input type="hidden" name="deliveryItemList'+i+'.bn" value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.bn]').val()+'">'+
					'<input type="hidden" name="deliveryItemList'+i+'.productsId" value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.productsId]').val()+'">'+
					'<input type="hidden" name="deliveryItemList'+i+'.name" value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.name]').val()+'">'+
					'<input type="hidden" name="deliveryItemList'+i+'.type" value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.type]').val()+'">'+
					'<input type="hidden" name="deliveryItemList'+i+'.sendNum" value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.sendnum]').val()+'">'+
					'<input type="hidden" name="deliveryItemList'+i+'.nums" value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.nums]').val()+'">'+
				'</tr>';
				}
				
			w3+='</table>';
			showWin();
			jQuery(".order_win").append('<div class="winTitle" onmousedown="moveStart(this,event)">发货</div>'+
				w3 +
				'<div style="text-align:center"><input type="button" value=" 确认 " onclick="fahuo_submit(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value=" 取消 " onclick="hideWin()" /></div>');
		
}
function tuihuo(pkey,basePath,pid)
{
	//退货弹出框
			w4 = '<table cellspacing="0" cellpadding="0" width="90%">'+
				'<tr>'+
					'<th>订单号:</th>'+
						'<td>'+jQuery('#'+pid+' input[name=orderDetail.ordersId]').val()+
					'<input type="hidden" name="orderReturnDelivery.ordersId" value="'+jQuery('#'+pid+' input[name=orderDetail.ordersId]').val()+'">'+
					'<th>下单日期:</th>'+
					'<td>'+jQuery('#'+pid+' input[name=orderDetail.cratetime]').val()+'</td>'+
				'</tr>'+
			'</table>'+
			'<table cellspacing="0" cellpadding="0" width="90%">'+
				'<tr>'+
					'<th>退货原因:</th>'+
					'<td><select name="return_Reasons" value="">'+
						'<option selected="selected" value="质量原因">质量原因</option>'+
						'<option value="无理由">无理由</option>'+
						'<option value="其他">其他</option>'+
					'</select></td>'+
					'<th>配送方式:</th>'+
					'<td><select name="return_delivery_type" vtype="select" selected="" value="" type="select" labelcolumn="dt_name" valuecolumn="dt_name">'+
						'<option label="顺丰快递" value="顺丰快递">顺丰快递</option>'+
						'<option label="EMS快递"  value="EMS快递">EMS快递</option>'+
						'<option label="FedEx联邦快递" value="FedEx联邦快递">FedEx联邦快递</option>'+
					'</select></td>'+
				'</tr>'+
				'<tr>'+
					'<th>物流公司:</th>'+
					'<td><select name="return_delivery_shipping" vtype="select" selected="" value="" type="select" labelcolumn="name" valuecolumn="corp_id">'+
						'<option label="中通速递"  value="1">中通速递</option>'+
						'<option label="邮政EMS" value="5">邮政EMS</option>'+
						'<option label="圆通速递" value="10">圆通速递</option>'+
						'<option label="FedEx" value="11">FedEx</option>'+
					'</select></td>'+
					'<th>物流单号:</th>'+
					'<td><input type="text" name="return_delivery_shippingNum" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>配送费用:</th>'+
					'<td><input value="" type="text" name="return_delivery_costFreight" autocomplete="off" vtype="text" /></td>'+
					'<!--    <th>发货日期:</th>      <td>NULL</td>-->'+
					'<th>是否保价:</th>'+
					'<td><label>'+
						'<input value="false" checked="checked" type="radio" name="tuihuo_is_protect" vtype="radio" />'+
						'否</label>'+
							'<label>'+
								'<input value="true" type="radio" name="tuihuo_is_protect" vtype="radio" />'+
								'是</label></td>'+
				'</tr>'+
				'<tr>'+
					'<th>退货人姓名:</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.shipname]').val()+'" type="text" name="return_delivery_name" autocomplete="off" vtype="text" /></td>'+
					'<th>电话:</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.Tel]').val()+'" type="text" name="return_delivery_tel" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>手机:</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.mobile]').val()+'" type="text" name="return_delivery_mobile" autocomplete="off" vtype="text" /></td>'+
					'<th>邮政编码:</th>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=orderDetail.zip]').val()+'" type="text" name="return_delivery_zip" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>退货地区:</th>'+
					'<td colspan="3"><span vtype="" package="mainland">'+
						'<input type="hidden" name="ship_area" />'+
						'<select name="return_delivery_area">'+
							'<option selected="selected" value="_NULL_">请选择...</option>'+
							'<option value="1" has_c="true">﻿北京</option>'+
							'<option value="21" has_c="true">上海</option>'+
							'<option value="42" has_c="true">天津</option>'+
							'<option value="62" has_c="true">重庆</option>'+
							'<option value="104" has_c="true">安徽</option>'+
							'<option value="227" has_c="true">福建</option>'+
							'<option value="322" has_c="true">甘肃</option>'+
							'<option value="423" has_c="true">广东</option>'+
							'<option value="566" has_c="true">广西</option>'+
							'<option value="690" has_c="true">贵州</option>'+
							'<option value="788" has_c="true">海南</option>'+
							'<option value="814" has_c="true">河北</option>'+
							'<option value="998" has_c="true">河南</option>'+
							'<option value="1176" has_c="true">黑龙江</option>'+
							'<option value="1320" has_c="true">湖北</option>'+
							'<option value="1436" has_c="true">湖南</option>'+
							'<option value="1573" has_c="true">吉林</option>'+
							'<option value="1643" has_c="true">江苏</option>'+
							'<option value="1763" has_c="true">江西</option>'+
							'<option value="1874" has_c="true">辽宁</option>'+
							'<option value="1989" has_c="true">内蒙古</option>'+
							'<option value="2103" has_c="true">宁夏</option>'+
							'<option value="2130" has_c="true">青海</option>'+
							'<option value="2182" has_c="true">山东</option>'+
							'<option value="2340" has_c="true">山西</option>'+
							'<option value="2471" has_c="true">陕西</option>'+
							'<option value="2589" has_c="true">四川</option>'+
							'<option value="2792" has_c="true">西藏</option>'+
							'<option value="2873" has_c="true">新疆</option>'+
							'<option value="2987" has_c="true">云南</option>'+
							'<option value="3133" has_c="true">浙江</option>'+
							'<option value="3235" has_c="true">香港</option>'+
							'<option value="3239" has_c="true">澳门</option>'+
							'<option value="3242" has_c="true">台湾</option>'+
						'</select>'+
					'</span></td>'+
				'</tr>'+
				'<tr>'+
					'<th>退货地址:</th>'+
					'<td colspan="3"><input value="" type="text" name="return_delivery_addr" autocomplete="off" vtype="text" /></td>'+
				'</tr>'+
				'<tr>'+
					'<th>退货单备注:</th>'+
					'<td colspan="3"><textarea name="return_delivery_memo" vtype="textarea" type="textarea"></textarea></td>'+
				'</tr>'+
			'</table>'+
			'<table cellspacing="0" cellpadding="0" width="90%">'+
				'<tr class="head">'+
					'<th>货号</th>'+
					'<th>商品名称</th>'+
					'<th>购买数量</th>'+
					'<th>已发货数量</th>'+
					'<th>此单退货数量</th>'+
				'</tr>';
				var counter=parseInt(jQuery('#'+pid+' input[name=orderItemCounter]').val());
			
				for(var i=0;i<counter;i++){
				w4=w4+'<tr>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.barcode]').val()+'</td>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.name]').val()+'</td>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.nums]').val()+'</td>'+
					'<td>'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.sendnum]').val()+'</td>'+
					'<td><input value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.sendnum]').val()+'" type="text" name="return_delivery_num'+i+'" autocomplete="off" vtype="text" /></td>'+
					
					'<input type="hidden" name="ordersItemList'+i+'.sendNum" value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.sendnum]').val()+'">'+
					'<input type="hidden" name="ordersItemList'+i+'.nums" value="'+jQuery('#'+pid+' input[name=ordersItemList'+i+'.nums]').val()+'">'+
					'<tr>';
					
					}
			w4+='</table>';
			
			showWin();
			jQuery(".order_win").append('<div class="winTitle" onmousedown="moveStart(this,event)">退货</div>'+
				w4 +
				'<div style="text-align:center"><input type="button" value=" 确认 " onclick="tuihuo_submit(\''+pkey+'\',\''+basePath+'\',\''+pid+'\')" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value=" 取消 " onclick="hideWin()" /></div>');
		
}
function wancheng(pkey,basePath,pid)
{
	var memberID=jQuery('#'+pid+' input[name=orderDetail.membersId]').val();//用户ID
	
	var finishedOrderID=jQuery('#'+pid+' input[name=orderDetail.ordersId]').val();
	if(window.confirm("确定完成吗?"))
	{
		jQuery.ajax({
			type: "POST",
			url: basePath+'/order/order!finishOrder.action',
			dataType: "json",
			data: 'memberID='+memberID+'&finishedOrderID='+finishedOrderID ,
			success: function(json)
			{
				jQuery().gridRefresh();
			
				var inn = jQuery().adPreview(pkey,pid,'pre'+pid);
				if(!inn||inn=='')
				{
					jQuery().previewError(pid);
					return;
				}
				jQuery("#"+pid).append('<div id="pre'+pid+'" style="display:none;"></div>');
				jQuery("#pre"+pid).append(inn);
				jQuery("#"+pid+" div").css("display","");
				jQuery().previewSuccess(pid,'pre'+pid);
			},
			error: function()
			{
				alert("Ajaxtransmission error!");
			}
		});
	}
}
function zuofei(pkey,basePath,pid)
{
	var memberID=jQuery('#'+pid+' input[name=orderDetail.membersId]').val();//用户ID
	var finishedOrderID=jQuery('#'+pid+' input[name=orderDetail.ordersId]').val();
	if(window.confirm("确定作废吗?"))
	{
		jQuery.ajax({
			type: "POST",
			url: basePath+'/order/order!abortOrder.action',
			dataType: "json",
			data: 'finishedOrderID='+finishedOrderID+'&memberID='+memberID,
			success: function(json)
			{
				jQuery().gridRefresh();
			
				var inn = jQuery().adPreview(pkey,pid,'pre'+pid);
				if(!inn||inn=='')
				{
					jQuery().previewError(pid);
					return;
				}
				jQuery("#"+pid).append('<div id="pre'+pid+'" style="display:none;"></div>');
				jQuery("#pre"+pid).append(inn);
				jQuery("#"+pid+" div").css("display","");
				jQuery().previewSuccess(pid,'pre'+pid);
			},
			error: function()
			{
				alert("Ajaxtransmission error!");
			}
		});
	}
}
function zhifu_submit(pkey,basePath,pid)
{
	var orderPayment_ordersId=jQuery(' input[name=orderPayment.ordersId]').val();//订单ID
	var orderPayment_membersId=jQuery('#'+pid+' input[name=orderDetail.membersId]').val();//用户ID 
	var orderPayment_payed=jQuery(' input[name=orderPayment.paycost]').val();//已付金额
	var orderPayment_money=jQuery(' input[name=orderPayment.money]').val();//总金额
	var orderPayment_bank=jQuery(' input[name=orderPayment.bank]').val();//收款银行
	var orderPayment_payAccount=jQuery(' input[name=orderPayment.payAccount]').val();//收款账号
	var orderPayment_curMoney=jQuery(' input[name=orderPayment.curMoney]').val();//收款金额
	var orderPayment_currency=jQuery(' input[name=orderPayment.currency]').val();//货币类型
	var orderPayment_paymethodObj=jQuery(' select[name=orderPayment.paymethod]');//支付方式
	var orderPayment_memo=jQuery(' textarea[name=orderPayment.memo]').val();//备注
	var orderPayment_paymethod=orderPayment_paymethodObj[0].options[orderPayment_paymethodObj[0].selectedIndex].text;
	
	var tempPament_payType=jQuery(' input[name=orderPayment.payType]');//付款类型
	var orderPayment_payType=null;
	if(tempPament_payType[0].checked=='true')orderPayment_payType='online';
	else if(tempPament_payType[1].checked=='true')orderPayment_payType='offline';
	else orderPayment_payType='deposit';
	
	
	var dataString='orderPayment.paycost='+orderPayment_payed+'&orderPayment.money='+orderPayment_money+
		'&orderPayment.ordersId='+orderPayment_ordersId+'&orderPayment.bank='+orderPayment_bank+
		'&orderPayment.account='+orderPayment_payAccount+
		'&orderPayment.curMoney='+orderPayment_curMoney+
		'&orderPayment.paymethod='+orderPayment_paymethod+'&orderPayment.currency='+orderPayment_currency+
		'&orderPayment.memo='+orderPayment_memo+'&orderPayment.membersId='+orderPayment_membersId+'&orderPayment.payType='+orderPayment_payType;
	jQuery.ajax({
		type: "POST",
		url: basePath+'/order/order!receivePayment.action',
		dataType: "json",
		data: dataString,
		success: function(json)
		{
			
				
			jQuery().gridRefresh();
			
			var inn = jQuery().adPreview(pkey,pid,'pre'+pid);
			if(!inn||inn=='')
			{
				jQuery().previewError(pid);
				return;
			}
			jQuery("#"+pid).append('<div id="pre'+pid+'" style="display:none;"></div>');
			jQuery("#pre"+pid).append(inn);
			jQuery("#"+pid+" div").css("display","");
			jQuery().previewSuccess(pid,'pre'+pid);
			
			hideWin();

		},
		error: function()
		{
			alert("Ajax Transmission error");
			hideWin();
		}
	});
}
function tuikuan_submit(pkey,basePath,pid)
{
	var orderRefund_ordersId=jQuery(' input[name=orderRefund.ordersId]').val();//订单ID
	var orderRefund_membersId=jQuery('#'+pid+' input[name=orderDetail.membersId]').val();//用户ID
	var orderRefund_account=jQuery(' input[name=orderRefund.payAccount]').val();//退款账户
	var orderRefund_paymethodObj=jQuery(' select[name=orderRefund.paymethod]')[0];//退款方式
	var orderRefund_paymethod=orderRefund_paymethodObj.options[orderRefund_paymethodObj.selectedIndex].text;
	var orderRefund_bank=jQuery(' input[name=orderRefund.bank]').val();//退款银行
	var orderRefund_money=jQuery(' input[name=orderRefund.money]').val();//退款金额
	var orderRefund_currency=jQuery(' input[name=orderRefund.currency]').val();//货币类型
	var orderRefund_memo=jQuery(' textarea[name=orderRefund.memo]').val();//退款单备注
	var orderRefund_score=jQuery(' input[name=orderRefund_score]').val();//扣除积分（还未使用）
	
	var tempOrderRefund_payType=jQuery(' input[name=orderRefund.payType]');//退款类型
	var orderRefund_payType=null;
	if(tempOrderRefund_payType[0].checked=='true')orderRefund_payType='online';
	else if(tempOrderRefund_payType[1].checked=='true')orderRefund_payType='offline';
	else orderRefund_payType='deposit';
	
	jQuery.ajax({
		type: "POST",
		url: basePath+'/order/order!refund.action',
		data: 'orderRefund.ordersId='+orderRefund_ordersId+
		'&orderRefund.paymethod='+orderRefund_paymethod+'&orderRefund.bank='+orderRefund_bank+
		'&orderRefund.money='+orderRefund_money+'&orderRefund.currency='+orderRefund_currency+
		'&orderRefund.score='+orderRefund_score+'&orderRefund.membersId='+orderRefund_membersId+
		'&orderRefund.account='+orderRefund_account+'&orderRefund.memo='+orderRefund_memo+'&orderRefund.payType='+orderRefund_payType,
		dataType: "json",
		success: function(json)
		{	
			alert("修改成功！");
			
			jQuery().gridRefresh();
			
			var inn = jQuery().adPreview(pkey,pid,'pre'+pid);
			if(!inn||inn=='')
			{
				jQuery().previewError(pid);
				return;
			}
			jQuery("#"+pid).append('<div id="pre'+pid+'" style="display:none;"></div>');
			jQuery("#pre"+pid).append(inn);
			jQuery("#"+pid+" div").css("display","");
			jQuery().previewSuccess(pid,'pre'+pid);
			
			hideWin();
		},
		error: function()
		{
			alert("Ajax transmission error!");
			hideWin()
		}
	});
}
function fahuo_submit(pkey,basePath,pid)
{
var ordersId=jQuery(' input[name=orderDelivery.ordersId]').val();//订单号
var membersId=jQuery('#'+pid+' input[name=orderDetail.membersId]').val();//用户表主键
var shipName=jQuery(' input[name=shipName]').val();//收获人姓名
var shipMobile=jQuery(' input[name=shipMobile]').val();//收货人移动电话
var shipTel=jQuery(' input[name=shipTel]').val();//收货人电话
var shipZip=jQuery(' input[name=shipZip]').val();//收货人邮编
var shipAddr=jQuery(' input[name=shipAddr]').val();//收货人地址
var shipMemo=jQuery(' textarea[name=shipMemo]').val();//备注;
var delivery_shipCost=jQuery(' input[name=delivery_shippingCost]').val();//物流费用
var logiNameObj=jQuery(' select[name=delivery_com]')[0];//物流公司
var logiName=logiNameObj.options[logiNameObj.selectedIndex].text;
var deliveryObj=jQuery(' select[name=delivery_type]');//快递类型（配送方式）
var delivery=deliveryObj[0].options[deliveryObj[0].selectedIndex].text;
var shipAreaObj=jQuery(' select[name=shipArea]');//收货地区
var shipArea=shipAreaObj[0].options[shipAreaObj[0].selectedIndex].text;

var tempIsProtect=jQuery(' input[name=delivery_is_protect]');//是否保价
var isProtect=null;
if(tempIsProtect[0].checked=='true')isProtect='0';
else isProtect='1';

var counter=parseInt(jQuery('#'+pid+' input[name=orderItemCounter]').val());//orderItem数量
var orderDeliveryItems=Array(counter);
var orderItemString="";
for(var i=0;i<counter;i++)
{
	
	if(jQuery(' input[name=deliveryItemList'+i+'.productsId]').val()=="null")
		orderItemString+=":"+",";
		else
		orderItemString+=jQuery(' input[name=deliveryItemList'+i+'.productsId]').val()+",";
	if(jQuery(' input[name=deliveryItemList'+i+'.type]').val()=="null")
		orderItemString+=":"+",";
		else
		orderItemString+=jQuery('input[name=deliveryItemList'+i+'.type]').val()+",";
	if(jQuery(' input[name=deliveryItemList'+i+'.bn]').val()=="null")
		orderItemString+=":"+",";
		else
		orderItemString+=jQuery(' input[name=deliveryItemList'+i+'.bn]').val()+",";
	if(jQuery(' input[name=deliveryItemList'+i+'.name]').val()=="null")
		orderItemString+=":"+",";
		else
		orderItemString+=jQuery(' input[name=deliveryItemList'+i+'.name]').val()+",";
	if(jQuery(' input[name=deliveryItemList'+i+'.sendNum]').val()=="null")
		orderItemString+=":"+",";
		else
		orderItemString+=jQuery(' input[name=deliveryItemList'+i+'.sendNum]').val();
	orderItemString+="~";	
}
var orderItemListString="";

var tempNum1=null;
var tempNum2=null;
var tempNum3=null;
for(var i=0;i<counter;i++){
	orderItemListString+=jQuery(' input[name=ordersItemList'+i+'.orderItemId]').val()+
	","+jQuery(' input[name=delivery_num'+i+']').val()+"~";	
	
	tempNum1=jQuery(' input[name=deliveryItemList'+i+'.nums]').val()
	tempNum2=jQuery(' input[name=deliveryItemList'+i+'.sendNum]').val();
	tempNum3=jQuery(' input[name=delivery_num'+i+']').val();
	
	if(tempNum1-tempNum2<tempNum3){
	alert("发货数量过大！");
	return;
	}
	else if(tempNum3<0){
	alert("发货数量不能为负数！");
	return;
	}
}

	jQuery.ajax({
		type: "POST",
		url: basePath+'/order/order!delivery.action',
		data: 'orderDelivery.ordersId='+ordersId+'&orderDelivery.shipName='+shipName+
		'&orderDelivery.shipMobile='+shipMobile+'&orderDelivery.shipTel='+shipTel+
		'&orderDelivery.shipZip='+shipZip+'&orderDelivery.shipAddr='+shipAddr+
		'&orderDelivery.delivery='+delivery+'&orderDelvery.money='+delivery_shipCost+
		'&orderDelivery.shipArea='+shipArea+'&orderDelivery.memo='+shipMemo+
		'&deliveryItemString='+orderItemString+'&orderItemListString='+orderItemListString+
		'&orderDelivery.logiName='+logiName+'&orderDelivery.membersId='+membersId+'&orderDelivery.isProtect='+isProtect,
		dataType: "json",
		success: function(json)
		{
			jQuery().gridRefresh();
			
			var inn = jQuery().adPreview(pkey,pid,'pre'+pid);
			if(!inn||inn=='')
			{
				jQuery().previewError(pid);
				return;
			}
			jQuery("#"+pid).append('<div id="pre'+pid+'" style="display:none;"></div>');
			jQuery("#pre"+pid).append(inn);
			jQuery("#"+pid+" div").css("display","");
			jQuery().previewSuccess(pid,'pre'+pid);
			
			hideWin();
		},
		error: function()
		{
			
		}
	});
}
function tuihuo_submit(pkey,basePath,pid)
{
var ordersId=jQuery(' input[name=orderReturnDelivery.ordersId]').val();//订单号
var returnName=jQuery(' input[name=return_delivery_name]').val();//退获人姓名
var returnMobile=jQuery(' input[name=return_delivery_mobile]').val();//退货人移动电话
var returnTel=jQuery(' input[name=return_delivery_tel]').val();//退货人电话
var returnZip=jQuery(' input[name=return_delivery_zip]').val();//退货人邮编
var returnAddr=jQuery(' input[name=return_delivery_addr]').val();//退货人地址
var returnMemo=jQuery(' textarea[name=return_delivery_memo]').val();//备注
var shipCost=jQuery(' input[name=return_delivery_costFreight]').val();//配送费用
var deliveryShipNum=jQuery(' input[name=return_delivery_shippingNum]').val();//物流单号

var tempIsProtect=jQuery(' input[name=tuihuo_is_protect]');//是否保价
var isProtect=null;
if(tempIsProtect[0].checked=='true')isProtect='0';
else isProtect='1';

var deliveryReasonObj=jQuery(' select[name=return_Reasons]')[0];//退货原因
var deliveryReason=deliveryReasonObj.options[deliveryReasonObj.selectedIndex].text;
var deliveryObj=jQuery(' select[name=return_delivery_type]')[0];//快递类型（配送方式）
var delivery=deliveryObj.options[deliveryObj.selectedIndex].text;
var deliveryCompObj=jQuery(' select[name=return_delivery_shipping]')[0];//物流公司
var deliveryComp=deliveryCompObj.options[deliveryCompObj.selectedIndex].text;
var shipAreaObj=jQuery(' select[name=return_delivery_area]')[0];//退货地区
var shipArea=shipAreaObj.options[shipAreaObj.selectedIndex].text;

var counter=parseInt(jQuery('#'+pid+' input[name=orderItemCounter]').val());//orderItem数量
var orderItemListString="";
var tempNum1=null;
var tempNum2=null;
var tempNum3=null;
for(var i=0;i<counter;i++){
	orderItemListString+=jQuery('#'+pid+' input[name=ordersItemList'+i+'.orderItemId]').val()+
	","+jQuery(' input[name=return_delivery_num'+i+']').val()+"~";
	
	tempNum1=jQuery(' input[name=ordersItemList'+i+'.nums]').val()
	tempNum2=jQuery(' input[name=ordersItemList'+i+'.sendNum]').val();
	tempNum3=jQuery(' input[name=return_delivery_num'+i+']').val();
	
	if(tempNum2<tempNum3){
	alert("退货数量过大！");
	return;
	}
	else if(tempNum3<0){
	alert("退货数量不能为负数！");
	return;
	}
}
	jQuery.ajax({
		type: "POST",
		url: basePath+'/order/order!returnDelivery.action',
		data: 'orderReturnDelivery.ordersId='+ordersId+'&orderReturnDelivery.returnReasons='+deliveryReason+
		'&orderReturnDelivery.shipping='+deliveryComp+'&orderReturnDelivery.name='+returnName+
		'&orderReturnDelivery.shipArea='+shipArea+'&orderReturnDelivery.shipAddr='+returnAddr+
		'&orderReturnDelivery.costFreight='+shipCost+'&orderReturnDelivery.remark='+returnMemo+
		'&orderReturnDelivery.phone='+returnTel+'&orderReturnDelivery.mobile='+returnMobile+
		'&orderReturnDelivery.zip='+returnZip+'&orderReturnDelivery.delivery='+delivery+
		'&orderReturnDelivery.shippingNum='+deliveryShipNum+'&orderItemListString='+orderItemListString,
		dataType: "json",
		success: function(json)
		{
			jQuery().gridRefresh();
			
			var inn = jQuery().adPreview(pkey,pid,'pre'+pid);
			if(!inn||inn=='')
			{
				jQuery().previewError(pid);
				return;
			}
			jQuery("#"+pid).append('<div id="pre'+pid+'" style="display:none;"></div>');
			jQuery("#pre"+pid).append(inn);
			jQuery("#"+pid+" div").css("display","");
			jQuery().previewSuccess(pid,'pre'+pid);
			
			hideWin();
		},
		error: function()
		{
			
		}
	});
}

function showWin()
{
	jQuery(document.body).append('<div class="order_mask"></div><div class="order_win"></div>');
}
function hideWin()
{
	jQuery(".order_mask").remove();
	jQuery(".order_win").remove();
}
var aimDiv;//移动层
var ox;//鼠标在层内的x坐标
var oy;//鼠标在层内的y坐标
var dl,dt;//松开鼠标时记录层的left和top
//鼠标按下,开始移动
function moveStart(elm,e){
	
	//拖动时禁止选中文字
	document.onselectstart = function()
	{
		return false;
	}

	//添加鼠标移动事件
	document.onmousemove = function()
	{
		divMove(e);
	}
	
	//添加鼠标抬起事件
	document.onmouseup = function()
	{
		moveEnd(e);
	}
	
	//移动层
	aimDiv = elm.parentNode;
	
//	if(dl!=null)
//	{
//		//非第一次拖动
//		aimDiv.style.left = dl + 'px';
//		aimDiv.style.top = dt + 'px';
//		ox = event.clientX - dl;
//		oy = event.clientY - dt;
//	}
//	else
//	{
		//第一次拖动
		ox = event.clientX - jQuery(aimDiv).offset().left;
		oy = event.clientY - jQuery(aimDiv).offset().top;
//	}
}

//鼠标移动,拖动层
function divMove(e){
	aimDiv.style.left = (e.clientX - ox) + 'px';
	aimDiv.style.top = (e.clientY - oy) + 'px';
}

//鼠标抬起,拖动结束
function moveEnd(e){
	//记录层的位置
	//dl = e.clientX - ox;
	//dt = e.clientY - oy;
	//取消鼠标移动和抬起事件
	document.onselectstart = '';
	document.onmousemove = '';
	document.onmouseup = '';
}