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
			font-family: Verdana, Arial, Helvetica, sans-serif;	font-size:13px;
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
		.gdiv_01{width:100%; font-size:15px;}
		.table_01{margin:0px; padding:0px; border-bottom:3px solid #000; padding-bottom:10px;}
		.table_01 td{text-align:center; line-height:32px; font-weight:bold;}
		.table_02{margin:0px; padding:0px;font-size:15px;}
		.table_02 td{text-align:left; line-height:28px;}
		.biao_1234{float:left; line-height:40px; margin-top:10px;}
		.biao_1234 span{padding-left:10px;}
		.table_03{margin:0px; padding:0px;font-size:15px;border-collapse: collapse;border: none;}
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
		<td width="33%">
		    
			<c:if test="${fns:getDictValueBySuffix('lable_company_title', 'report_form_', scoAcceptanceReport.type, '') != ''}">
						${fns:getDictValueBySuffix('lable_company_title', 'report_form_', scoAcceptanceReport.type, '文案')}
					</c:if>
		
<!-- 		制&nbsp;作&nbsp;企&nbsp;业:&nbsp; -->
			：${fns:getUserById(scoAcceptanceReport.createBy.id).name}
		</td>
		<td width="33%" class="td1">验收单号: ${scoAcceptanceReport.serialNumber}        </td>
	</tr>
	<tr>
		<td width="33%" class="td1">采&nbsp;购&nbsp;人&nbsp;: ${scoAcceptanceReport.linkman}       </td>
		<td width="33%">企业联系人:&nbsp;
			${cuurentUser.linkman}
		</td>
		<td width="33%" class="td1">发票单号:&nbsp;${scoAcceptanceReport.invoicenum}        </td>
	</tr>
	<tr>
		<td width="33%" class="td1">联系电话: ${scoAcceptanceReport.mobile}       </td>
		<td width="33%">结&nbsp;算&nbsp;日&nbsp;期:&nbsp;
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
					<td align="center" style="width:13%;">
					
					<c:if test="${fns:getDictValueBySuffix('lable_category_type', 'report_form_', scoAcceptanceReport.type, '') != ''}">
						${fns:getDictValueBySuffix('lable_category_type', 'report_form_', scoAcceptanceReport.type, '文案')}
					</c:if>
					
<!-- 					印刷品类别 -->
					</td>
					
					<td align="center" style="width:10%;">
					<c:if test="${fns:getDictValueBySuffix('lable_item_name', 'report_form_', scoAcceptanceReport.type, '') != ''}">
						${fns:getDictValueBySuffix('lable_item_name', 'report_form_', scoAcceptanceReport.type, '文案')}
					</c:if>
<!-- 					印刷品名称（标题） -->
					</td>
					<td align="center" style="width:8%;">规格尺寸</td>
					<td align="center" style="width:5%;">页数</td>
					<td align="center" style="width:10%;">
					<c:if test="${fns:getDictValueBySuffix('lable_paper_type', 'report_form_', scoAcceptanceReport.type, '') != ''}">
						${fns:getDictValueBySuffix('lable_paper_type', 'report_form_', scoAcceptanceReport.type, '文案')}
					</c:if>
					
<!-- 					纸张种类 -->
					</td>
					<td align="center" style="width:25%;">工艺描述</td>
					<td align="center" style="width:10%;">单价(元)</td>
					<td align="center" style="width:10%;">计量单位</td>
					<td align="center" style="width:5%;">数量</td>
					<td align="center" style="width:10%;">总         价      (元)</td>
				</tr>
				<c:forEach items="${list}" var="scoGoodsAcceptance" varStatus="status">
				<tr>
					<td align="center">${scoGoodsAcceptance.printsId.subName}</td>
					<td align="center">${scoGoodsAcceptance.printsId.printName}</td>
					<td align="center">${scoGoodsAcceptance.printsId.specification}</td>
					<td align="center">${scoGoodsAcceptance.printsId.printPageSize}</td>
					<td align="center">${scoGoodsAcceptance.printsId.paperTypeName}</td>
					<td align="center" style="text-align:left">
					
					<c:choose>
				        <c:when test="${param.type == 6}">
				        		<c:if test="${scoGoodsAcceptance.elements!=null&&scoGoodsAcceptance.elements.size()>0 }">
	                                
									<c:forEach var="item" items="${scoGoodsAcceptance.elements }"> 
										   <c:if test="${item.selected }">
										   
					                                 <B> ${item.name }：</B>
					                                 <c:if test="${item.children!=null&&item.children.size()>0 }">
													<div>
													<c:forEach var="subitem" items="${item.children }"> 
													    <c:if test="${subitem.selected }">
														   ${subitem.name } 
													    </c:if>
													</c:forEach>
													</div>
													</c:if>
										   </c:if>
									    
									</c:forEach>
							   </c:if>
				        </c:when>
				        <c:when test="${param.type == 5}">
				        	${scoGoodsAcceptance.printsId.description}
				        </c:when>
				        <c:otherwise>
				        </c:otherwise>
				    </c:choose>
					
					</td>
					
					<td align="center"><fmt:formatNumber value="${scoGoodsAcceptance.printsId.price}" type="currency" pattern="￥0.00"/></td>
					<td align="center">${scoGoodsAcceptance.printsId.units}</td>
					<td align="center">${scoGoodsAcceptance.numbers}</td>
					<td align="center"><fmt:formatNumber value="${scoGoodsAcceptance.totalPrice}" type="currency" pattern="￥0.00"/></td>
				</tr>
				</c:forEach>

				<br />

				<tr>
					<td colspan=2 align="center">合计</td>
					<td colspan=7 >${totalAmtChinese}</td>
					<td align="right"><fmt:formatNumber value="${totalAmt}" type="currency" pattern="￥0.00"/></td>
				</tr>
				<tr>
				
					<td colspan=2 align="center">验收意见</td>
					<td colspan=4 align="center"><span><input type="checkbox">合格 </span>
					<span><input type="checkbox">不合格 </span>
					<span><input type="checkbox">其它意见 </span> </td>
					<td colspan=4 class="left">验收人（签字）</td>
				</tr>
			</table>
</div>

	<div class="gdiv_01" style="margin-top:10px;">
			<table class="width_01 table_02" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="33%" class="td1">公司全称：${fns:getUserById(scoAcceptanceReport.createBy.id).name}     </td>
					<td width="33%">地址：${fns:getUserById(scoAcceptanceReport.createBy.id).address}
					</td>
				</tr>
				<tr>
					<td width="33%" class="td1">开户银行：${fns:getUserById(scoAcceptanceReport.createBy.id).bankName}  </td>
					<td width="33%">银行账号：${fns:getUserById(scoAcceptanceReport.createBy.id).bankNumber}
					</td>
				</tr>
				<tr>
					<td width="33%" class="td1">联系人：${fns:getUserById(scoAcceptanceReport.createBy.id).linkman}   </td>
					<td width="33%">固定电话：   ${fns:getUserById(scoAcceptanceReport.createBy.id).phone}
					</td>
					<td width="33%">手机号码：${fns:getUserById(scoAcceptanceReport.createBy.id).mobile}
					</td>
				</tr>
			</table>
</div>

</div>
</body>
</html>