<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>验收单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sco/scoAcceptanceReport/?type=${param.type }">打印查看验收单</a></li>
		<%--<shiro:hasPermission name="sco:scoAcceptanceReport:edit"><li><a href="${ctx}/sco/scoAcceptanceReport/form">添加活动验收单</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="scoAcceptanceReport" action="${ctx}/sco/scoAcceptanceReport/?type=${param.type }" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>单位编号：</label>
				<form:input path="department" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>验收单号</th>
				<th>生成时间</th>
				<th>采购单位编号</th>
				<th>商品数量</th>
				<th>总价</th>
				<th>发票编号</th>
				<th>状态</th>
				<shiro:hasPermission name="sco:scoAcceptanceReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoAcceptanceReport">
			<tr>
				<td>
						${scoAcceptanceReport.serialNumber}
				</td>
				<td>
					<fmt:formatDate value="${scoAcceptanceReport.buyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${scoAcceptanceReport.department}
				</td>
				<td>
					${scoAcceptanceReport.goodsNumbers}
				</td>
				<td>
					<fmt:formatNumber value="${scoAcceptanceReport.subtotal}" type="currency" pattern="￥0.00"/>
				</td>
				<td>
					${scoAcceptanceReport.invoicenum}
				</td>
				<td>
					${fns:getDictLabel(scoAcceptanceReport.state, "audit_report", "0")}
				</td>
				<shiro:hasPermission name="sco:scoAcceptanceReport:edit"><td>
					<c:if test="${fns:getDictValue('审核通过', 'audit_report', '2') == scoAcceptanceReport.state
					 || fns:getDictValue('默认通过', 'audit_report', '3') == scoAcceptanceReport.state }">
					<a href="${ctx}/sco/scoAcceptanceReport/report/print/print?id=${scoAcceptanceReport.id}&type=${param.type }" target="_blank">打印</a>
					</c:if>
					<a href="${ctx}/sco/scoAcceptanceReport/report/print/view?id=${scoAcceptanceReport.id}&type=${param.type }" target="_blank">查看</a>
					<c:if test="${fns:getDictValue('未完成', 'audit_report', '0') == scoAcceptanceReport.state
					 || fns:getDictValue('待审核', 'audit_report', '1') == scoAcceptanceReport.state }">
						<a href="${ctx}/sco/scoAcceptanceReport/form?id=${scoAcceptanceReport.id}&type=${param.type }">修改</a>
					<a href="${ctx}/sco/scoAcceptanceReport/delete?id=${scoAcceptanceReport.id}&type=${param.type }" onclick="return confirmx('确认要删除该验收单吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>