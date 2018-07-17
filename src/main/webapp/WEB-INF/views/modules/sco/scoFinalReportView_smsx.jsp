<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>打印结算单</title>
	<style type="text/css">
		body{padding:0px; margin:0px; font-size:12px; background:#fff; color:#000;
			<c:if test="${param.action == 'print'}">
			background-image:url(${ctxStatic}/sco/image/pattern_ser.jpg);
			</c:if>
		}
		body,td,input,div{
			font-family: Verdana, Arial, Helvetica, sans-serif;	font-size:20px;
		}
		.m_t10{margin-top:10px;}
		.p_b10{padding-bottom:10px;}
		.bor_bt01{
			/*border-bottom:1px solid #000;*/
		}
		.bor_bt02{
			/*border-bottom:2px solid #000;*/
		}
		.width_01{width:1000px; margin:0px auto;}
		.gdiv_01{width:100%; float:left;font-size:20px;}
		.table_01{margin:0px; padding:0px; border-bottom:3px solid #000; padding-bottom:10px;}
		.table_01 td{text-align:center; line-height:32px; font-weight:bold;}
		.table_02{margin:0px; padding:0px;font-size:20px;}
		.table_02 td{text-align:left; line-height:28px;}
		.biao_1234{float:left; line-height:40px; margin-top:10px;}
		.biao_1234 span{padding-left:10px;}
		.table_03{margin:0px; padding:0px;font-size:20px;border-collapse: collapse;border: none;}
		.table_03 td{line-height:32px;border: solid #000 1px;}
		.table_03 .td_01{width:100px; text-align:right; padding-right:20px;}
		.table_03 .td_02{width:300px; text-align:left;}
		.table_03 .td_03{width:150px; text-align:right; padding-right:20px;}
		.table_04{margin:0px; padding:0px;font-size:20px;}
		.table_04 td{width:33%; text-align:left; line-height:28px;}
		.center{text-align:center;}
	</style>
</head>
<body>

<c:if test="${param.action == 'view'}">
<table class="width_01" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="center"><h1>此页面为查看页面</h1></td>
	</tr>
</table>
</c:if>

<c:if test="${param.action == 'print'}">
<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase='${ctxStatic}/sco/cab/smsx.cab#Version=6,4,438,06'></object>
<div align="center" id="dayinDiv" >
	<input type=button value="打印本页" onclick="printTure()"/>
	<input type=button value="页面设置" onclick="factory.printing.PageSetup()"/>
	<input type=button value="打印预览" onclick="printPreview()"/>
</div>
</c:if>


<c:set value="${fns:getUserById(scoFinalReport.createBy.id)}" var="cuurentUser" />

<div class="width_01">
	<div class="gdiv_01 m_t10">
		<table class="width_01 table_01" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><h1 style="width:900px; float:left; text-align:center; padding:0px;">${cuurentUser.name}<br/><br/>结算单
				</h1>
					<%--<img  style="height:100px;float:right;" src="http://211.166.32.4:80/jsd/qrViewJsd.action?content=单据号：520151209234221" />--%>
					<img  style="height:100px;float:right;" src="${ctx}/sco/scoFinalReport/report/qrCode?id=${scoFinalReport.id}" />
				</td>

			</tr>
			<tr>
				<td class="center">
					<h2>【第1联:采购单位报销入帐凭证】</h2>
				</td>
			</tr>
		</table>
	</div>
	<div class="gdiv_01">
		<table class="width_01 table_02" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="width: 360px;">
					结算单号：${scoFinalReport.serialNumber}</td>
				<td>

				</td>
				<td style="width: 378px;">

				</td>
				<td>
					采购机构：${scoFinalReport.officeName}
				</td>
			</tr>
			<tr>
				<td>单位编号：${scoFinalReport.department}</td>
				<td>&nbsp;</td>
				<td></td>
				<td>发票号：${scoFinalReport.invoicenum}</td>
			</tr>
			<tr>
				<td>联系人：${scoFinalReport.linkman}</td>
				<td colspan="2">联系电话：${scoFinalReport.mobile}</td>
				<%--<td></td>--%>
				<td>
					<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', scoFinalReport.type, '') != ''}">
						${fns:getDictValueBySuffix('lable_identifier', 'report_form_', scoFinalReport.type, '文案')}：${scoFinalReport.identifier}
					</c:if>
				</td>
			</tr>
			<tr>
				<td>结算时间：<fmt:formatDate value="${scoFinalReport.buyDate}" pattern="yyyy-MM-dd"/></td>

				<td></td>
				<td colspan="2">
				<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', scoFinalReport.type, '') != ''}">
					${fns:getDictValueBySuffix('lable_car_type', 'report_form_', scoFinalReport.type, '文案')}: ${scoFinalReport.serTreeId.name}&gt;${scoFinalReport.subId.name}&gt;${scoFinalReport.thirdId.name}
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="biao_1234 width_01 bor_bt02"><span>收费结算表</span></div>
	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_01 bor_bt01">序号</td>
				<td class="td_02 bor_bt01">名称</td>
				<td class="td_03 bor_bt01"></td>
				<td class="td_04 bor_bt01"><span style="float:left; width:180px; text-align:right;">总金额（元）</span></td>
			</tr>

			<tr>
				<td class="td_01">1</td>
				<td class="td_02">材料费</td>
				<td class="td_03"></td>
				<td class="td_04">




					<span style="float:left; width:180px; text-align:right;"><fmt:formatNumber value="${materialsCost}" type="currency" pattern="￥0.00"/></span>
				</td>
			</tr>
			<tr>
				<td class="td_01">2</td>
				<td class="td_02">工时费</td>
				<td class="td_03"></td>
				<td class="td_04"><span style="float:left; width:180px; text-align:right;"><fmt:formatNumber value="${laborCost}" type="currency" pattern="￥0.00"/></span></td>
			</tr>
			<tr>
				<td class="td_01 bor_bt01"></td>
				<td class="td_02 bor_bt01">合计</td>
				<td class="td_03 bor_bt01"></td>
				<td class="td_04 bor_bt01"><span style="float:left; width:180px; text-align:right;"><fmt:formatNumber value="${totalAmt}" type="currency" pattern="￥0.00"/></span></td>
			</tr>
		</table>
	</div>
	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>*结算=工时费+材料费</td>
			</tr>
		</table>
	</div>



	<div class="biao_1234 width_01 bor_bt02"><span>材料费</span></div>
	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_01">序号</td>
				<td class="td_02">项目名称</td>
				<td class="td_03">数量</td>
				<td class="td_04"><span style="float:left; width:120px; text-align:right;">单价（元）</span><span style="padding-left:80px;">总金额（元）</span></td>
			</tr>


		<c:forEach items="${materialsList}" var="scoSerFinal" varStatus="status">
			<tr>
				<td class="td_01">${status.index+1}</td>
				<td class="td_02">${scoSerFinal.sparepart.name}${scoSerFinal.tyre.name}${scoSerFinal.oil.name}</td>
				<td class="td_03">${scoSerFinal.numbers}</td>
				<td class="td_04"><span style="float:left; width:120px; text-align:right;"><fmt:formatNumber value="${scoSerFinal.sparepart.agrtPrice}${scoSerFinal.tyre.agrtPrice}${scoSerFinal.oil.agrtPrice}" type="currency" pattern="￥0.00"/></span><span style="float:left; text-align:right; width:180px;"><fmt:formatNumber value="${scoSerFinal.totalPrice}" type="currency" pattern="￥0.00"/></span></td>
			</tr>
		</c:forEach>

			<tr>
				<td class="td_01 bor_bt01"></td>
				<td class="td_02 bor_bt01">合计</td>
				<td class="td_03 bor_bt01"></td>
				<td class="td_04 bor_bt01"><span style="float:left; width:120px; text-align:right;">&nbsp;</span><span style="float:left; text-align:right; width:180px;"><fmt:formatNumber value="${materialsCost}" type="currency" pattern="￥0.00"/></span></td>
			</tr>
		</table>
	</div>



	<div class="biao_1234 width_01 bor_bt02"><span>工时费</span></div>
	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_01">序号</td>
				<td class="td_02">项目名称</td>
				<td class="td_03">工时</td>
				<td class="td_04"><span style="float:left; width:120px; text-align:right;">单价（元）</span><span style="padding-left:80px;">总金额（元）</span></td>

			</tr>


		<c:forEach items="${laborList}" var="scoSerFinal" varStatus="status">
			<tr>
				<td class="td_01">${status.index+1}</td>
				<td class="td_02">${scoSerFinal.labor.name}</td>
				<td class="td_03">${scoSerFinal.numbers}</td>
				<td class="td_04"><span style="float:left; width:120px; text-align:right;"><fmt:formatNumber value="${scoSerFinal.labor.agrtPrice}" type="currency" pattern="￥0.00"/></span><span style="float:left; text-align:right; width:180px;"><fmt:formatNumber value="${scoSerFinal.totalPrice}" type="currency" pattern="￥0.00"/></span></td>
			</tr>
		</c:forEach>
			<tr>
				<td class="td_01 bor_bt01"></td>
				<td class="td_02 bor_bt01">合计</td>
				<td class="td_03 bor_bt01"></td>
				<td class="td_04 bor_bt01"><span style="float:left; width:120px; text-align:right;">&nbsp;</span><span style="float:left; text-align:right; width:180px;"><fmt:formatNumber value="${laborCost}" type="currency" pattern="￥0.00"/></span>
				</td>
			</tr>
		</table>
	</div>

	<div class="gdiv_01">
		<table class="width_01 table_04" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3">地址: ${cuurentUser.address}</td>
			</tr>
			<tr>
				<td>开户银行：${cuurentUser.bankName}</td>
				<td>传真: ${cuurentUser.fax}</td>
				<td>固定电话：${cuurentUser.phone}</td>
			</tr>
			<tr>
				<td>账号：${cuurentUser.bankNumber}</td>
				<td>联系人: ${cuurentUser.linkman}</td>
				<td>手机号码：${cuurentUser.mobile}</td>
			</tr>
			<tr>
				<td colspan="3">备注：${scoFinalReport.remarks}</td>
			</tr>
		</table>
		<table>
			<tr><td>

				注： 本结算单一式二联，第一联采购单位留存，作为报销入账凭证；第二联由供应商定期报采购机构。
			</td></tr>
		</table>
	</div>
</div>
<br/><br/>
<div style='PAGE-BREAK-BEFORE: always'></div>

<div class="width_01">
	<div class="gdiv_01 m_t10">
		<table class="width_01 table_01" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><h1 style="width:900px; float:left; text-align:center; padding:0px;">${cuurentUser.name}<br/><br/>结算单</h1>
					<%--<img  style="height:100px;float:right;" src="http://211.166.32.4:80/jsd/qrViewJsd.action?content=单据号：520151209234221" />--%>
					<img  style="height:100px;float:right;" src="${ctx}/sco/scoFinalReport/report/qrCode?id=${scoFinalReport.id}" />
				</td>

			</tr>
			<tr>
				<td class="center">
					<h2>【第2联:供应商定期报采购机构留存】</h2>
				</td>
			</tr>
		</table>
	</div>
	<div class="gdiv_01">
		<table class="width_01 table_02" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="width: 360px;">
					结算单号：${scoFinalReport.serialNumber}</td>
				<td>

				</td>
				<td style="width: 378px;">

				</td>
				<td>
					采购机构：${scoFinalReport.officeName}
				</td>
			</tr>
			<tr>
				<td>单位编号：${scoFinalReport.department}</td>
				<td>&nbsp;</td>
				<td></td>
				<td>发票号：${scoFinalReport.invoicenum}</td>
			</tr>
			<tr>
				<td>联系人：${scoFinalReport.linkman}</td>
				<td colspan="2">联系电话：${scoFinalReport.mobile}</td>
				<%--<td></td>--%>
				<td>
					<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', scoFinalReport.type, '') != ''}">
						${fns:getDictValueBySuffix('lable_identifier', 'report_form_', scoFinalReport.type, '文案')}：${scoFinalReport.identifier}
					</c:if>
				</td>
			</tr>
			<tr>
				<td>结算时间：<fmt:formatDate value="${scoFinalReport.buyDate}" pattern="yyyy-MM-dd"/></td>

				<td></td>
				<td colspan="2">
					<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', scoFinalReport.type, '') != ''}">
						${fns:getDictValueBySuffix('lable_car_type', 'report_form_', scoFinalReport.type, '文案')}: ${scoFinalReport.serTreeId.name}&gt;${scoFinalReport.subId.name}&gt;${scoFinalReport.thirdId.name}
					</c:if>
				</td>
				<%--<td></td>--%>
			</tr>
		</table>
	</div>
	<div class="biao_1234 width_01 bor_bt02"><span>收费结算表</span></div>
	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_01 bor_bt01">序号</td>
				<td class="td_02 bor_bt01">名称</td>
				<td class="td_03 bor_bt01"></td>
				<td class="td_04 bor_bt01"><span style="float:left; width:180px; text-align:right;">总金额（元）</span></td>
			</tr>

			<tr>
				<td class="td_01">1</td>
				<td class="td_02">材料费</td>
				<td class="td_03"></td>
				<td class="td_04">




					<span style="float:left; width:180px; text-align:right;"><fmt:formatNumber value="${materialsCost}" type="currency" pattern="￥0.00"/></span>
				</td>
			</tr>
			<tr>
				<td class="td_01">2</td>
				<td class="td_02">工时费</td>
				<td class="td_03"></td>
				<td class="td_04"><span style="float:left; width:180px; text-align:right;"><fmt:formatNumber value="${laborCost}" type="currency" pattern="￥0.00"/></span></td>
			</tr>
			<tr>
				<td class="td_01 bor_bt01"></td>
				<td class="td_02 bor_bt01">合计</td>
				<td class="td_03 bor_bt01"></td>
				<td class="td_04 bor_bt01"><span style="float:left; width:180px; text-align:right;"><fmt:formatNumber value="${totalAmt}" type="currency" pattern="￥0.00"/></span></td>
			</tr>
		</table>
	</div>
	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>*结算=工时费+材料费</td>
			</tr>
		</table>
	</div>



	<div class="biao_1234 width_01 bor_bt02"><span>材料费</span></div>
	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_01">序号</td>
				<td class="td_02">项目名称</td>
				<td class="td_03">数量</td>
				<td class="td_04"><span style="float:left; width:120px; text-align:right;">单价（元）</span><span style="padding-left:80px;">总金额（元）</span></td>
			</tr>



			<c:forEach items="${materialsList}" var="scoSerFinal" varStatus="status">
				<tr>
					<td class="td_01">${status.index+1}</td>
					<td class="td_02">${scoSerFinal.sparepart.name}${scoSerFinal.tyre.name}${scoSerFinal.oil.name}</td>
					<td class="td_03">${scoSerFinal.numbers}</td>
					<td class="td_04"><span style="float:left; width:120px; text-align:right;"><fmt:formatNumber value="${scoSerFinal.sparepart.agrtPrice}${scoSerFinal.tyre.agrtPrice}${scoSerFinal.oil.agrtPrice}" type="currency" pattern="￥0.00"/></span><span style="float:left; text-align:right; width:180px;"><fmt:formatNumber value="${scoSerFinal.totalPrice}" type="currency" pattern="￥0.00"/></span></td>
				</tr>
			</c:forEach>

			<tr>
				<td class="td_01 bor_bt01"></td>
				<td class="td_02 bor_bt01">合计</td>
				<td class="td_03 bor_bt01"></td>
				<td class="td_04 bor_bt01"><span style="float:left; width:120px; text-align:right;">&nbsp;</span><span style="float:left; text-align:right; width:180px;"><fmt:formatNumber value="${materialsCost}" type="currency" pattern="￥0.00"/></span></td>
			</tr>
		</table>
	</div>



	<div class="biao_1234 width_01 bor_bt02"><span>工时费</span></div>
	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="td_01">序号</td>
				<td class="td_02">项目名称</td>
				<td class="td_03">工时</td>
				<td class="td_04"><span style="float:left; width:120px; text-align:right;">单价（元）</span><span style="padding-left:80px;">总金额（元）</span></td>
			</tr>


			<c:forEach items="${laborList}" var="scoSerFinal" varStatus="status">
				<tr>
					<td class="td_01">${status.index+1}</td>
					<td class="td_02">${scoSerFinal.labor.name}</td>
					<td class="td_03">${scoSerFinal.numbers}</td>
					<td class="td_04"><span style="float:left; width:120px; text-align:right;"><fmt:formatNumber value="${scoSerFinal.labor.agrtPrice}" type="currency" pattern="￥0.00"/></span><span style="float:left; text-align:right; width:180px;"><fmt:formatNumber value="${scoSerFinal.totalPrice}" type="currency" pattern="￥0.00"/></span></td>
				</tr>
			</c:forEach>

			<tr>
				<td class="td_01 bor_bt01"></td>
				<td class="td_02 bor_bt01">合计</td>
				<td class="td_03 bor_bt01"></td>
				<td class="td_04 bor_bt01"><span style="float:left; width:120px; text-align:right;">&nbsp;</span><span style="float:left; text-align:right; width:180px;"><fmt:formatNumber value="${laborCost}" type="currency" pattern="￥0.00"/></span>

				</td>
			</tr>
		</table>
	</div>

	<div class="gdiv_01">
		<table class="width_01 table_04" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3">地址: ${cuurentUser.address}</td>
			</tr>
			<tr>
				<td>开户银行：${cuurentUser.bankName}</td>
				<td>传真: ${cuurentUser.fax}</td>
				<td>固定电话：${cuurentUser.phone}</td>
			</tr>
			<tr>
				<td>账号：${cuurentUser.bankNumber}</td>
				<td>联系人: ${cuurentUser.linkman}</td>
				<td>手机号码：${cuurentUser.mobile}</td>
			</tr>
			<tr>
				<td colspan="3">备注：${scoFinalReport.remarks}</td>
			</tr>
		</table>
		<table>
			<tr><td>

				注：本结算单一式二联，第一联采购单位留存，作为报销入账凭证；第二联由供应商定期报采购机构。
			</td></tr>
		</table>
	</div>
</div>
</body>
</html>
<script defer="defer">
	window.onunload=function(){
		if ( !factory.object ){
			alert("打印控件没有正确安装!\n可能是您还没有安装打印机，请在安装打印机后重新打开本页面");
			return;
		}else{
			//初始化
			factory.printing.header = "";
			factory.printing.footer = "";
			factory.printing.portrait = false;
			factory.printing.printBackground = true;
			factory.printing.leftMargin = "7.0";
			factory.printing.topMargin = "10.0";
			factory.printing.rightMargin = "7.0";
			factory.printing.bottomMargin = "10.0";
		}
	}
	//打印函数
	function printTure(){
		document.all("dayinDiv").style.display="none";//隐藏按钮
		factory.printing.Print(false); //调用控件打印
		document.all("dayinDiv").style.display="";//显示
	}

	function printPreview(){
		document.all("dayinDiv").style.display="none";//隐藏按钮
		factory.printing.Preview(); //调用控件打印
		document.all("dayinDiv").style.display="";//显示
	}
</script>