<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备份我的商品</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出商品数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sco/scoGoodsImpExp/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
//			$("#btnImport").click(function(){
//				$.jBox($("#importBox").html(), {title:"导入商品数据", buttons:{"关闭":true},
//					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
//			});
		});
	</script>
</head>
<body>
	<%--<div id="importBox" class="hide">--%>
		<%--<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"--%>
			  <%--class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>--%>
			<%--<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　--%>
			<%--<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>--%>
			<%--<a href="${ctx}/sco/scoGoodsImpExp/import/template">下载模板</a>--%>
		<%--</form>--%>
	<%--</div>--%>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="sco:scoSetting:edit"><li class="active"><a>导出我的商品</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<form:form id="searchForm" modelAttribute="scoGoods" action="${ctx}/sco/scoGoodsImpExp/export" method="post" class="breadcrumb form-search ">
	<table class="table table-striped table-bordered table-condensed">
		<tbody>
			<tr>
				<td style="text-align: left;padding-left: 15px; line-height: 30px;" colspan="2">
					<i class=" icon-arrow-right" style="font-size: 18px; font-weight: bold;color: #999;"> 导出商品</i>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;padding-right: 20px; width: 150px;">
					待导出数据：
				</td>
				<td>
					<div style=" word-spacing:10px;">
						${count} 条
					</div>
				</td>
			</tr>
			<tr>
				<td style="text-align: left;padding-left: 20px; width: 150px;" colspan="2">
					<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				</td>
			</tr>
		</tbody>
	</table>
	</form:form>

	<%--<hr/>--%>

	<%--<table class="table table-striped table-bordered table-condensed">--%>
		<%--<tbody>--%>
		<%--<tr>--%>
			<%--<td style="text-align: left;padding-left: 15px; line-height: 30px;" colspan="2">--%>
				<%--<i class=" icon-arrow-right" style="font-size: 18px; font-weight: bold;color: #999;"> 导入商品</i>--%>
			<%--</td>--%>
		<%--</tr>--%>
		<%--<tr>--%>
			<%--<td style="text-align: left;padding-left: 20px; width: 150px;" colspan="2">--%>
				<%--<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>--%>
			<%--</td>--%>
		<%--</tr>--%>
		<%--</tbody>--%>
	<%--</table>--%>
</body>
</html>