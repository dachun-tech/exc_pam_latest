<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>查看验收单</title>
	<meta name="decorator" content="default"/>
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
		/*.gdiv_01{width:100%; float:left;font-size:20px;}*/
		.gdiv_01{width:100%; font-size:20px;}
		.table_01{margin:0px; padding:0px; border-bottom:3px solid #000; padding-bottom:10px;}
		.table_01 td{text-align:center; line-height:32px; font-weight:bold;}
		.table_02{margin:0px; padding:0px;font-size:20px;}
		.table_02 td{text-align:left; line-height:28px;}
		.biao_1234{float:left; line-height:40px; margin-top:10px;}
		.biao_1234 span{padding-left:10px;}
		.table_03{margin:0px; padding:0px;font-size:20px;border-collapse: collapse;border: none;}
		.table_03 td{line-height:32px;border: solid #000 1px;}
		.table_03 .td_01{width:220px; text-align:right; padding-right:10px;}
		.table_03 .td_02{width:200px; text-align:left;}
		.table_03 .td_03{width:150px; text-align:right; padding-right:20px;}
		.table_04{margin:0px; padding:0px;font-size:20px;}
		.table_04 td{width:33%; text-align:left; line-height:28px;}
		.center{text-align:center;}
		.right{width:130px;text-align:right;padding-right:5px;}
		.left{width:200px;text-align:left;padding-left:10px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body>
<%--<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase='http://211.166.32.4:80/print/smsx.cab#Version=6,4,438,06'></object>--%>
<div align="center" id="dayinDiv" >
</div>
<c:set value="${fns:getUserById(scoAcceptanceReport.createBy.id)}" var="cuurentUser" />
<!--页面显示区域-->
<%--<table width="100%">--%>
	<%--<tr>--%>
		<%--<td class="center"><h1 style="color: black;">此页面为查看页面</h1></td>--%>
		<%--<td style="width:100px;"></td>--%>
	<%--</tr>--%>
<%--</table>--%>
<table>
	<tr>
		<td class="center">
			<h2><!-- 【第2联:供应商留存】 --></h2>
		</td>
	</tr>
</table>
<div class="width_01">
   <div class="gdiv_01 m_t10">
		<table class="width_01 table_01" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
				<td>
					<h1 style="width:900px; float:left; text-align:center; padding:0px;">
<%-- 						<c:if test="${scoFinalReport.type == '5'}">图文制作定点服务验收单</c:if> --%>
<%-- 						<c:if test="${scoFinalReport.type == '6'}">印刷定点服务验收单</c:if> --%>
<!-- 						具体费用项目 -->
					</h1>
					<%--<img  style="height:100px;float:right;" src="http://211.166.32.4:80/jsd/qrViewJsd.action?content=单据号：520151209234221" />--%>
<%-- 					<img  style="height:100px;float:right;" src="${ctx}/sco/scoFinalReport/report/qrCode?id=${scoFinalReport.id}" /> --%>
				</td>

			</tr>
			<tr>
				<td class="center">
<!-- 					<h2>【第1联:采购单位报销入帐凭证】</h2> -->
				</td>
			</tr>
			</tbody></table>
	</div>

	<div class="gdiv_01">
<table class="width_01 table_02" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="33%" class="td1">采购单位: ${scoAcceptanceReport.department}       </td>
		<td width="33%">供&nbsp;货&nbsp;单&nbsp;位:&nbsp;
			${fns:getUserById(scoAcceptanceReport.createBy.id).name}
		</td>
		<td width="33%" class="td1">验收单号: ${scoAcceptanceReport.serialNumber}        </td>
	</tr>
	<tr>
		<td width="33%" class="td1">采&nbsp;购&nbsp;人&nbsp;: ${scoAcceptanceReport.linkman}       </td>
		<td width="33%">供货联系人:&nbsp;
			${cuurentUser.linkman}
		</td>
		<td width="33%" class="td1">发票编号:&nbsp;${scoAcceptanceReport.invoicenum}        </td>
	</tr>
	<tr>
		<td width="33%" class="td1">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话: ${scoAcceptanceReport.mobile}       </td>
		<td width="33%">采&nbsp;购&nbsp;日&nbsp;期:&nbsp;
			<fmt:formatDate value="${scoAcceptanceReport.buyDate}" pattern="yyyy-MM-dd"/>
		</td>
		<td width="33%">采购机构:&nbsp;${scoAcceptanceReport.officeName}
		</td>
	</tr>
</table>
</div>
	<div class="gdiv_01">
	<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
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
					<td align="right" ><fmt:formatNumber value="${scoGoodsAcceptance.goodsId.normalPrice}" type="currency" pattern="￥0.00"/></td>
					<td align="right"><fmt:formatNumber value="${scoGoodsAcceptance.goodsId.agrtPrice}" type="currency" pattern="￥0.00"/></td>
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
</div>
</div>
</body>
</html>