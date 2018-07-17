<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<title>打印验收单</title>
	<style type="text/css">
		body{
			background-image:url(${ctxStatic}/sco/image/pattern_ysd.gif);
			margin-right:5px;
		}
		body,td,input{
			font-family: Verdana, Arial, Helvetica, sans-serif;
			font-size:13px;
		}
		h1{
			font-size:25px;
			font-weight:bold;
		}
		h2{
			font-size:16px;
		}
		table{
			width:100%;
		}
		.tb1{
		}
		.tb1 td{
			margin:5px;
		}
		.td1{
			width:35%;
		}
		.center{
			text-align:center;
		}
		.underline{
			text-decoration:underline;
		}
		.dotted{
			height:2px;
			border-top:none;
			border-left:none;
			border-right:none;
			border-bottom:dotted;
		}

	</style>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body >
<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase='${ctxStatic}/sco/cab/smsx.cab#Version=6,4,438,06'></object>
<div align="center" id="dayinDiv" >
	<input type=button value="打印本页" onclick="printTure()"/>
	<input type=button value="页面设置" onclick="factory.printing.PageSetup()"/>
	<input type=button value="打印预览" onclick="printPreview()"/>
</div>
<table>
	<tr>
		<td class="center"><h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验收单
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<%--<img  style="height:100px;width:100px" src="http://211.166.32.4:80/ysd/qrViewYsd.action?content=单据号：520151029112907" />--%>
			<img  style="height:100px;width:100px" src="${ctx}/sco/scoAcceptanceReport/report/qrCode?id=${scoAcceptanceReport.id}" />
		</h1></td>
	</tr>
	<tr>
		<td class="center">
			<h2>【第1联:采购单位报销入帐凭证】</h2>
		</td>
	</tr>
</table>
<table  border="0" cellspacing="0">
	<tr>
		<td width="33%" class="td1">采购单位及采购人: ${scoAcceptanceReport.department} - ${scoAcceptanceReport.linkman}       </td>
		<td width="33%">供&nbsp;货&nbsp;单&nbsp;位:&nbsp;
			${fns:getUserById(scoAcceptanceReport.createBy.id).name}
		</td>
		<td width="33%" class="td1">验收单号: ${scoAcceptanceReport.serialNumber}        </td>
	</tr>
	<tr>
		<td width="33%" class="td1">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话: ${scoAcceptanceReport.mobile}       </td>
		<td width="33%">供货联系人:&nbsp;
			${scoAcceptanceReport.linkman}
		</td>
		<td width="33%" class="td1">发票编号:&nbsp;${scoAcceptanceReport.invoicenum}        </td>
	</tr>
	<tr>
		<td width="33%" class="td1">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度: <fmt:formatDate value="${scoAcceptanceReport.buyDate}" pattern="yyyy"/>年度     </td>
		<td width="33%">采&nbsp;购&nbsp;日&nbsp;期:&nbsp;
			<fmt:formatDate value="${scoAcceptanceReport.buyDate}" pattern="yyyy-MM-dd"/>
		</td>
		<td width="33%">采购机构:&nbsp;${scoAcceptanceReport.officeName}
		</td>
	</tr>
</table>
<br/>
<table cellspacing="0" cellpadding="0" style="table-layout:fixed;word-break:break-all">
	<tr>
		<td>
			<table cellspacing="0" cellpadding="3" border="1"  style="border-color:Black;font-family:arial;border-collapse:collapse;width:100%" bordercolor="Black">
				<tr>
					<td align="center" style="width:30px;">序号</td>
					<td align="center">商品名称</td>
					<td align="center" style="width:60px;">型号</td>
					<td align="center">技术规格和主要配置</td>
					<td align="center" style="width:100px;">采购单位公开价(元)</td>
					<td align="center" style="width:80px;">实际采购单价(元)</td>
					<td align="center" style="width:60px;">实际采购折扣(%)</td>
					<td align="center" style="width:40px;">数量</td>
					<td align="center" style="width:30px;">单位</td>
					<td align="center" style="width:80px;">总         价      (元)</td>
				</tr>
				<c:forEach items="${list}" var="scoGoodsAcceptance" varStatus="status">
				<tr>
					<td align="center">${status.index+1}</td>
					<td align="center">${scoGoodsAcceptance.goodsId.name}</td>
					<td align="center">${scoGoodsAcceptance.goodsId.goodsModel}</td>
					<td align="center">${scoGoodsAcceptance.goodsId.specification}</td>
					<td align="center"><fmt:formatNumber value="${scoGoodsAcceptance.goodsId.normalPrice}" type="currency" pattern="￥0.00"/></td>
					<td align="right" ><fmt:formatNumber value="${scoGoodsAcceptance.goodsId.agrtPrice}" type="currency" pattern="￥0.00"/></td>
					<td align="right"><fmt:formatNumber value="${scoGoodsAcceptance.discountPercent}" type="percent"/></td>
					<td align="right">${scoGoodsAcceptance.numbers}</td>
					<td align="center">${scoGoodsAcceptance.goodsId.units}</td>
					<td align="right"><fmt:formatNumber value="${scoGoodsAcceptance.totalPrice}" type="currency" pattern="￥0.00"/></td>
				</tr>
				</c:forEach>
				<br />

				<tr>
					<td colspan=2 align="center">合计</td>
					<td colspan=7 >${totalAmtChinese}</td>
					<td align="right"><fmt:formatNumber value="${totalAmt}" type="currency" pattern="￥0.00"/></td>
				</tr>
				<tr>
					<td colspan=2 align="center">备注</td>
					<td colspan=8>${scoAcceptanceReport.remarks}</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<TABLE >
				<TR>
					<TD></TD>
				</TR>
			</TABLE>

			<table>
				<tr><td>

<!-- 					注：1. “采购时网上报价”指该品牌商品制造厂商或中国销售总部的对外公众网站上公开报价，即“挂牌价”；<BR> -->
<!-- 					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.  -->
					注：本验收单一式二联，第一联采购单位留存，作为报销入账凭证；第二联由供应商定期报采购机构。
				</td></tr>
			</table>
		</td>
	</tr>
</table>
<div style='PAGE-BREAK-BEFORE: always'></div>
<table>
	<tr>
		<td class="center"><h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验收单
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img  style="height:100px;width:100px" src="${ctx}/sco/scoAcceptanceReport/report/qrCode?id=${scoAcceptanceReport.id}" />
		</h1></td>
	</tr>
	<tr>
		<td class="center">
			<h2>【第2联:供应商定期报采购机构留存】</h2>
		</td>
	</tr>
</table>
<table  border="0" cellspacing="0">
	<tr>
		<td width="33%" class="td1">采购单位及采购人: ${scoAcceptanceReport.department} - ${scoAcceptanceReport.linkman}       </td>
		<td width="33%">供&nbsp;货&nbsp;单&nbsp;位:&nbsp;
			${fns:getUserById(scoAcceptanceReport.createBy.id).name}
		</td>
		<td width="33%" class="td1">验收单号: ${scoAcceptanceReport.serialNumber}        </td>
	</tr>
	<tr>
		<td width="33%" class="td1">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话: ${scoAcceptanceReport.mobile}       </td>
		<td width="33%">供货联系人:&nbsp;
			${scoAcceptanceReport.linkman}
		</td>
		<td width="33%" class="td1">发票编号:&nbsp;${scoAcceptanceReport.invoicenum}        </td>
	</tr>
	<tr>
		<td width="33%" class="td1">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度: <fmt:formatDate value="${scoAcceptanceReport.buyDate}" pattern="yyyy"/>年度     </td>
		<td width="33%">采&nbsp;购&nbsp;日&nbsp;期:&nbsp;
			<fmt:formatDate value="${scoAcceptanceReport.buyDate}" pattern="yyyy-MM-dd"/>
		</td>
		<td width="33%">采购机构:&nbsp;${scoAcceptanceReport.officeName}
		</td>
	</tr>
</table>
<br/>
<table cellspacing="0" cellpadding="0" style="table-layout:fixed;word-break:break-all">
	<tr>
		<td>
			<table cellspacing="0" cellpadding="3" border="1"  style="border-color:Black;font-family:arial;border-collapse:collapse;width:100%" bordercolor="Black">
				<tr>
					<td align="center" style="width:30px;">序号</td>
					<td align="center">商品名称</td>
					<td align="center" style="width:60px;">型号</td>
					<td align="center">技术规格和主要配置</td>
					<td align="center" style="width:100px;">采购单位公开价(元)</td>
					<td align="center" style="width:80px;">实际采购单价(元)</td>
					<td align="center" style="width:60px;">实际采购折扣(%)</td>
					<td align="center" style="width:40px;">数量</td>
					<td align="center" style="width:30px;">单位</td>
					<td align="center" style="width:80px;">总         价      (元)</td>
				</tr>

				<c:forEach items="${list}" var="scoGoodsAcceptance" varStatus="status">
					<tr>
						<td align="center">${status.index+1}</td>
						<td align="center">${scoGoodsAcceptance.goodsId.name}</td>
						<td align="center">${scoGoodsAcceptance.goodsId.goodsModel}</td>
						<td align="center">${scoGoodsAcceptance.goodsId.specification}</td>
						<td align="center"><fmt:formatNumber value="${scoGoodsAcceptance.goodsId.normalPrice}" type="currency" pattern="￥0.00"/></td>
						<td align="right" ><fmt:formatNumber value="${scoGoodsAcceptance.goodsId.agrtPrice}" type="currency" pattern="￥0.00"/></td>
						<td align="right"><fmt:formatNumber value="${scoGoodsAcceptance.discountPercent}" type="percent"/></td>
						<td align="right">${scoGoodsAcceptance.numbers}</td>
						<td align="center">${scoGoodsAcceptance.goodsId.units}</td>
						<td align="right"><fmt:formatNumber value="${scoGoodsAcceptance.totalPrice}" type="currency" pattern="￥0.00"/></td>
					</tr>
				</c:forEach>

				<br />

				<tr>
					<td colspan=2 align="center">合计</td>
					<td colspan=7 >${totalAmtChinese}</td>
					<td align="right"><fmt:formatNumber value="${totalAmt}" type="currency" pattern="￥0.00"/></td>
				</tr>
				<tr>
					<td colspan=2 align="center">备注</td>
					<td colspan=8>${scoAcceptanceReport.remarks}</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<TABLE >
				<TR>
					<TD></TD>
				</TR>
			</TABLE>

			<table>
				<tr><td>

<!-- 					注：1. “采购时网上报价”指该品牌商品制造厂商或中国销售总部的对外公众网站上公开报价，即“挂牌价”；<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.  -->
					注：本验收单一式二联，第一联采购单位留存，作为报销入账凭证；第二联由供应商定期报采购机构。
				</td></tr>
			</table>
		</td>
	</tr>

</table>

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
