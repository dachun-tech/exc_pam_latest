<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>打印验收单</title>
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
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
</head>
<body>


<c:if test="${param.action == 'view'}">
	<%--<table class="width_01" border="0" cellspacing="0" cellpadding="0">--%>
		<%--<tr>--%>
			<%--<td class="center"><h1>此页面为查看页面</h1></td>--%>
		<%--</tr>--%>
	<%--</table>--%>
</c:if>

<c:if test="${param.action == 'print'}">
	<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase='${ctxStatic}/sco/cab/smsx.cab#Version=6,4,438,06'></object>
	<div align="center" id="dayinDiv" >
			<%--<input type=button value="打印本页" onclick="printTure()"/>--%>
			<%--<input type=button value="页面设置" onclick="factory.printing.PageSetup()"/>--%>
			<%--<input type=button value="打印预览" onclick="printPreview()"/>--%>
<!-- 		<input type=button value="打印本页" onclick="printTure();"/> -->
	</div>
</c:if>


<c:set value="${fns:getUserById(scoFinalReport.createBy.id)}" var="cuurentUser" />

<div class="width_01">
	<div class="gdiv_01 m_t10">
		<table class="width_01 table_01" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
				<td>
					<h1 style="width:900px; float:left; text-align:center; padding:0px;">
						<c:if test="${scoFinalReport.type == '5'}">图文制作定点服务验收单</c:if>
						<c:if test="${scoFinalReport.type == '6'}">印刷定点服务验收单</c:if>
<!-- 						具体费用项目 -->
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
			</tbody></table>
	</div>
	<div class="gdiv_01">
		<table class="width_01 table_02" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="width: 360px;">
					验收单号：${scoFinalReport.serialNumber}
				</td>
				<td>

				</td>
				<td style="width: 378px;">
					印刷单位：${cuurentUser.name}
				</td>
				<td>
					单位编号：${scoFinalReport.department}
				</td>
			</tr>
			<tr>
				<td>供货联系人：${scoFinalReport.linkman}</td>
				<td>&nbsp;</td>
				<td></td>
				<td>发票号：${scoFinalReport.invoicenum}</td>
			</tr>
			<tr>
				<td>联系电话：${scoFinalReport.mobile}</td>
				<td>&nbsp; </td>
				<td>采购日期：<fmt:formatDate value="${scoFinalReport.buyDate}" pattern="yyyy-MM-dd"/></td>
				<td>
					采购机构：${scoFinalReport.officeName}
				</td>
			</tr>
		</table>
	</div>




	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<%--<tr>--%>
				<%--<td class="td_01">印刷品类别</td>--%>
				<%--<td class="td_02"  colspan="9">--%>
					<%--<c:if test="${scoFinalReport.type == '6'}">--%>
						<%--<span><input type="checkbox">票据 </span>--%>
						<%--<span><input type="checkbox">证书 </span>--%>
						<%--<span><input type="checkbox">内部出版发行书籍报刊 </span>--%>
						<%--<span><input type="checkbox">内部资料 </span>--%>
						<%--<span><input type="checkbox">文件汇编 </span>--%>
						<%--<span><input type="checkbox">宣传品 </span>--%>
						<%--<span><input type="checkbox">包装制品 </span>--%>
						<%--<span><input type="checkbox">信封 </span>--%>
						<%--<span><input type="checkbox">广告牌 </span>--%>
						<%--<span><input type="checkbox">灯箱 </span>--%>
						<%--<span><input type="checkbox">展板 </span>--%>
						<%--<span><input type="checkbox">专用标牌 </span>--%>
						<%--<span><input type="checkbox">平面设计图 </span>--%>
						<%--<span><input type="checkbox">3D效果图 </span>--%>
						<%--<span><input type="checkbox">其他 </span>--%>
					<%--</c:if>--%>
					<%--<c:if test="${scoFinalReport.type == '5'}">--%>
						<%--<span><input type="checkbox">广告牌 </span>--%>
						<%--<span><input type="checkbox">灯箱 </span>--%>
						<%--<span><input type="checkbox">展板 </span>--%>
						<%--<span><input type="checkbox">专用标牌 </span>--%>
						<%--<span><input type="checkbox">平面设计图 </span>--%>
						<%--<span><input type="checkbox">3D效果图 </span>--%>
						<%--<span><input type="checkbox">其他 </span>--%>
					<%--</c:if>--%>
				<%--</td>--%>
			<%--</tr>--%>

			<%--<tr>--%>
				<%--<td class="td_01">印刷工艺</td>--%>
				<%--<td class="td_02"  colspan="9">--%>
					<%--<c:if test="${param.action == 'view' && scoFinalReport.type == '6'}">--%>
						<%--<span style="color:red;">--%>
							<%--例如：覆膜、装订、烫金（银）、模切、压痕、起凸、压凹、打孔、打号、UV上光、压纹、专色印刷等--%>
						<%--</span>--%>
					<%--</c:if>--%>
					<%--<c:if test="${scoFinalReport.type == '5'}">--%>
						<%--<span><input type="checkbox">无 </span>--%>
						<%--<span><input type="checkbox">有， </span>--%>
						<%--包括：_--%>
					<%--</c:if>--%>
				<%--</td>--%>
			<%--</tr>--%>



			<tr>
				<td class="td_01" rowspan="2">印刷品名称</td>
				<td class="center" rowspan="2">规格<br>（mm）</td>
				<td class="center" rowspan="2" style="width:100px;">页数</td>
				<td class="center" rowspan="2" style="width:100px;">数量</td>
				<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') != ''}">
					<td class="center" colspan="5">价格构成</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') == ''}">
					<td class="center" colspan="4">价格构成</td>
				</c:if>
				<td class="center" rowspan="2">采购金额</td>
			</tr>
			<tr>
				<td class="center">
					<c:if test="${scoFinalReport.type == '6'}">
						编辑<br>制版费
					</c:if>
					<c:if test="${scoFinalReport.type == '5'}">
						排版<br>设计费
					</c:if>
				</td>
				<td class="center">
					<c:if test="${scoFinalReport.type == '6'}">
						印刷原<br>材料费
					</c:if>
					<c:if test="${scoFinalReport.type == '5'}">
						印刷原<br>材料费
					</c:if>
				</td>
				<td class="center">
					<c:if test="${scoFinalReport.type == '6'}">
						装订<br>包装费
					</c:if>
					<c:if test="${scoFinalReport.type == '5'}">
						喷绘<br>制作费
					</c:if>
				</td>
				<td class="center">
					<c:if test="${scoFinalReport.type == '6'}">
						其他费用
					</c:if>
					<c:if test="${scoFinalReport.type == '5'}">
						包装<br>运杂费
					</c:if>
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') != ''}">
					<td class="center">其他费用</td>
				</c:if>
			</tr>

			<tr>
				<td class="center">${scoFinalReport.itemName}&nbsp;</td>
				<td class="center">${scoFinalReport.specs}&nbsp;</td>
				<td class="center">${scoFinalReport.pages}&nbsp;</td>
				<td class="center">${scoFinalReport.quantity}&nbsp;</td>
				<td class="right"><fmt:formatNumber value="${firstCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				<c:if test="${scoFinalReport.type == '6'}">
					<td class="right"><fmt:formatNumber value="${laborCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
					<td class="right"><fmt:formatNumber value="${thirdCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				</c:if>
				<c:if test="${scoFinalReport.type == '5'}">
					<td class="right"><fmt:formatNumber value="${thirdCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
					<td class="right"><fmt:formatNumber value="${laborCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				</c:if>
				<td class="right"><fmt:formatNumber value="${forthCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') != ''}">
					<td class="right"><fmt:formatNumber value="${scoFinalReport.otherPrice}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				</c:if>
				<td class="right"><fmt:formatNumber value="${allAmt}" type="currency" pattern="￥0.00"/>&nbsp;</td>
			</tr>

			<tr>
				<td class="td_01">合计</td>
				<td class="left" colspan="9">
					${allAmtChinese}
				</td>
			</tr>
			<tr>
				<td class="td_01">印刷验收</td>
				<td class="left" colspan="9">
					<c:if test="${param.action == 'view' && scoFinalReport.type == '6'}">
						<span style="color:red;">
								<%--（单项印刷金额达500元以上上传封面照片）--%>
						</span>
					</c:if>
					<span><input type="checkbox">合格 </span>
					<span><input type="checkbox">不合格 </span>
					<span><input type="checkbox">其它意见 </span>
					<%--${scoFinalReport.remarks}--%>
				</td>
			</tr>
		</table>
	</div>


	<div class="gdiv_01">
		<table class="width_01 table_04" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
				<td>开户银行：${cuurentUser.bankName}</td>
				<td>账号：${cuurentUser.bankNumber}</td>
				<td>联系人：${cuurentUser.linkman}</td>
			</tr>
			</tbody></table>
		<table>
			<tbody><tr><td style="vertical-align: top;">
				注：
			</td>
				<td>
					1. “价格构成”根据实际填写，无费用构成项可不填写；<br>
					2. 本结算单一式二联，第一联采购单位留存，作为报销入账凭证；第二联由供应商定期报采购机构。
				</td></tr>
			</tbody></table>
	</div>
</div>
<br><br>
<div style="PAGE-BREAK-BEFORE: always"></div>




<div class="width_01"  style="margin-top:-40px;">
	<div class="gdiv_01 m_t10">
		<table class="width_01 table_01" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
				<td>
					<h1 style="width:900px; float:left; text-align:center; padding:0px;">
						<%--<c:if test="${scoFinalReport.type == '5'}">图文制作</c:if><c:if test="${scoFinalReport.type == '6'}">印刷</c:if>定点服务验收单--%>
						具体费用项目
					</h1>

					<img  style="height:100px;float:right;" src="${ctx}/sco/scoFinalReport/report/qrCode?id=${scoFinalReport.id}" />
				</td>

			</tr>
			<tr>
				<td class="center">
					<h2>【第2联:供应商定期报采购机构留存】</h2>
				</td>
			</tr>
			</tbody></table>
	</div>
	<div class="gdiv_01">
		<table class="width_01 table_02" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="width: 360px;">
					验收单号：${scoFinalReport.serialNumber}
				</td>
				<td>

				</td>
				<td style="width: 378px;">
					印刷单位：${cuurentUser.name}
				</td>
				<td>
					单位编号：${scoFinalReport.department}
				</td>
			</tr>
			<tr>
				<td>供货联系人：${scoFinalReport.linkman}</td>
				<td>&nbsp;</td>
				<td></td>
				<td>发票号：${scoFinalReport.invoicenum}</td>
			</tr>
			<tr>
				<td>联系电话：${scoFinalReport.mobile}</td>
				<td>&nbsp; </td>
				<td>采购日期：<fmt:formatDate value="${scoFinalReport.buyDate}" pattern="yyyy-MM-dd"/></td>
				<td>
					采购机构：${scoFinalReport.officeName}
				</td>
			</tr>
		</table>
	</div>





	<div class="gdiv_01">
		<table class="width_01 table_03" border="0" cellspacing="0" cellpadding="0">
			<%--<tr>--%>
				<%--<td class="td_01">印刷品类别</td>--%>
				<%--<td class="td_02"  colspan="9">--%>
					<%--<c:if test="${scoFinalReport.type == '6'}">--%>
						<%--<span><input type="checkbox">票据 </span>--%>
						<%--<span><input type="checkbox">证书 </span>--%>
						<%--<span><input type="checkbox">内部出版发行书籍报刊 </span>--%>
						<%--<span><input type="checkbox">内部资料 </span>--%>
						<%--<span><input type="checkbox">文件汇编 </span>--%>
						<%--<span><input type="checkbox">宣传品 </span>--%>
						<%--<span><input type="checkbox">包装制品 </span>--%>
						<%--<span><input type="checkbox">信封 </span>--%>
						<%--<span><input type="checkbox">广告牌 </span>--%>
						<%--<span><input type="checkbox">灯箱 </span>--%>
						<%--<span><input type="checkbox">展板 </span>--%>
						<%--<span><input type="checkbox">专用标牌 </span>--%>
						<%--<span><input type="checkbox">平面设计图 </span>--%>
						<%--<span><input type="checkbox">3D效果图 </span>--%>
						<%--<span><input type="checkbox">其他 </span>--%>
					<%--</c:if>--%>
					<%--<c:if test="${scoFinalReport.type == '5'}">--%>
						<%--<span><input type="checkbox">广告牌 </span>--%>
						<%--<span><input type="checkbox">灯箱 </span>--%>
						<%--<span><input type="checkbox">展板 </span>--%>
						<%--<span><input type="checkbox">专用标牌 </span>--%>
						<%--<span><input type="checkbox">平面设计图 </span>--%>
						<%--<span><input type="checkbox">3D效果图 </span>--%>
						<%--<span><input type="checkbox">其他 </span>--%>
					<%--</c:if>--%>
				<%--</td>--%>
			<%--</tr>--%>

			<%--<tr>--%>
				<%--<td class="td_01">印刷工艺</td>--%>
				<%--<td class="td_02"  colspan="9">--%>
					<%--<c:if test="${param.action == 'view' && scoFinalReport.type == '6'}">--%>
						<%--<span style="color:red;">--%>
							<%--例如：覆膜、装订、烫金（银）、模切、压痕、起凸、压凹、打孔、打号、UV上光、压纹、专色印刷等--%>
						<%--</span>--%>
					<%--</c:if>--%>
					<%--<c:if test="${scoFinalReport.type == '5'}">--%>
						<%--<span><input type="checkbox">无 </span>--%>
						<%--<span><input type="checkbox">有， </span>--%>
						<%--包括：_--%>
					<%--</c:if>--%>
				<%--</td>--%>
			<%--</tr>--%>



			<tr>
				<td class="td_01" rowspan="2">印刷品名称</td>
				<td class="center" rowspan="2">规格<br>（mm）</td>
				<td class="center" rowspan="2" style="width:100px;">页数</td>
				<td class="center" rowspan="2" style="width:100px;">数量</td>
				<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') != ''}">
					<td class="center" colspan="5">价格构成</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') == ''}">
					<td class="center" colspan="4">价格构成</td>
				</c:if>
				<td class="center" rowspan="2">采购金额</td>
			</tr>
			<tr>
				<td class="center">
					<c:if test="${scoFinalReport.type == '6'}">
						编辑<br>制版费
					</c:if>
					<c:if test="${scoFinalReport.type == '5'}">
						排版<br>设计费
					</c:if>
				</td>
				<td class="center">
					<c:if test="${scoFinalReport.type == '6'}">
						印刷原<br>材料费
					</c:if>
					<c:if test="${scoFinalReport.type == '5'}">
						印刷原<br>材料费
					</c:if>
				</td>
				<td class="center">
					<c:if test="${scoFinalReport.type == '6'}">
						装订<br>包装费
					</c:if>
					<c:if test="${scoFinalReport.type == '5'}">
						喷绘<br>制作费
					</c:if>
				</td>
				<td class="center">
					<c:if test="${scoFinalReport.type == '6'}">
						其他费用
					</c:if>
					<c:if test="${scoFinalReport.type == '5'}">
						包装<br>运杂费
					</c:if>
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') != ''}">
					<td class="center">其他费用</td>
				</c:if>
			</tr>

			<tr>
				<td class="center">${scoFinalReport.itemName}&nbsp;</td>
				<td class="center">${scoFinalReport.specs}&nbsp;</td>
				<td class="center">${scoFinalReport.pages}&nbsp;</td>
				<td class="center">${scoFinalReport.quantity}&nbsp;</td>
				<td class="right"><fmt:formatNumber value="${firstCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				<c:if test="${scoFinalReport.type == '6'}">
					<td class="right"><fmt:formatNumber value="${laborCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
					<td class="right"><fmt:formatNumber value="${thirdCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				</c:if>
				<c:if test="${scoFinalReport.type == '5'}">
					<td class="right"><fmt:formatNumber value="${thirdCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
					<td class="right"><fmt:formatNumber value="${laborCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				</c:if>
				<td class="right"><fmt:formatNumber value="${forthCost}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') != ''}">
					<td class="right"><fmt:formatNumber value="${scoFinalReport.otherPrice}" type="currency" pattern="￥0.00"/>&nbsp;</td>
				</c:if>
				<td class="right"><fmt:formatNumber value="${allAmt}" type="currency" pattern="￥0.00"/>&nbsp;</td>
			</tr>

			<tr>
				<td class="td_01">合计</td>
				<td class="left" colspan="9">
					${allAmtChinese}
				</td>
			</tr>
			<%--<tr>--%>
				<%--<td class="td_01">备注</td>--%>
				<%--<td class="left" colspan="9">--%>
					<%--<c:if test="${param.action == 'view' && scoFinalReport.type == '6'}">--%>
						<%--<span style="color:red;">--%>
								<%--&lt;%&ndash;（单项印刷金额达500元以上上传封面照片）&ndash;%&gt;--%>
						<%--</span>--%>
					<%--</c:if>--%>
					<%--${scoFinalReport.remarks}--%>
				<%--</td>--%>
			<%--</tr>--%>
			<tr>
				<td class="td_01">印刷验收</td>
				<td class="left" colspan="9">
					<c:if test="${param.action == 'view' && scoFinalReport.type == '6'}">
						<span style="color:red;">
								<%--（单项印刷金额达500元以上上传封面照片）--%>
						</span>
					</c:if>
					<span><input type="checkbox">合格 </span>
					<span><input type="checkbox">不合格 </span>
					<span><input type="checkbox">其它意见 </span>
					<%--${scoFinalReport.remarks}--%>
				</td>
			</tr>
		</table>
	</div>


	<div class="gdiv_01">
		<table class="width_01 table_04" border="0" cellspacing="0" cellpadding="0">
			<tbody><tr>
				<td>开户银行：${cuurentUser.bankName}</td>
				<td>账号：${cuurentUser.bankNumber}</td>
				<td>联系人：${cuurentUser.linkman}</td>
			</tr>
			</tbody></table>
		<table>
			<tbody><tr><td style="vertical-align: top;">
				注：
			</td>
				<td>
					1. “价格构成”根据实际填写，无费用构成项可不填写；<br>
					2. 本结算单一式二联，第一联采购单位留存，作为报销入账凭证；第二联由供应商定期报采购机构。
				</td></tr>
			</tbody></table>
	</div>
</div>



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
	//	//打印函数
		function printTure(){
			document.all("dayinDiv").style.display="none";//隐藏按钮
			factory.printing.Print(false); //调用控件打印
			document.all("dayinDiv").style.display="";//显示
		}

	//打印函数
//	function printTure(){
//		$("#dayinDiv").css('display','none');//隐藏按钮
//		window.print(); //调用控件打印
//		$("#dayinDiv").css('display','block');//显示
//	}

	function printPreview(){
		document.all("dayinDiv").style.display="none";//隐藏按钮
		factory.printing.Preview(); //调用控件打印
		document.all("dayinDiv").style.display="";//显示
	}
</script>

</body>
</html>