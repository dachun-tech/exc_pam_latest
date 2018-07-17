<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>验收单审核</title>
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
		
		function exportexcel(){
			$("#searchForm").attr("action",ctx+"/admin/sco/scoAcceptanceReport/exportexcel");
			$("#searchForm").submit();
			$("#searchForm").attr("action",ctx+"/admin/sco/scoAcceptanceReport/auditList");
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/admin/sco/scoAcceptanceReport/auditList?type=${param.type}">
			<c:if test="${param.type == '3'}">物资定点采购验收单</c:if>
			<c:if test="${param.type == '5'}">图文制作定点服务验收单</c:if>
			<c:if test="${param.type == '6'}">印刷定点服务验收单</c:if>
		</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="adminScoAcceptanceStatistic" action="${ctx}/admin/sco/scoAcceptanceReport/auditList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="${param.type}"/>
		<ul class="ul-form">
		    <li><label>统计年份：</label>
				<%--class="input-medium"  class="input-xlarge"--%>
				<form:select path="year" class="input-small">
					<form:options items="${yearList}" itemLabel="year" itemValue="year" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<form:select path="month" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${monthList}" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="report.state" class="input-xlarge" >
					<form:option value="" label="--全部--"/>
					<form:options items="${auditReportList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>供应商：</label>
				<form:select path="createBy.id" class="input-xlarge">
					<form:option value="" label="--全部供应商--"/>
					<form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>单位编号：</label>
				<form:input path="report.department" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>总价：</label>
				<form:input path="minSubtotal" htmlEscape="false" maxlength="64" class="input-medium" style="width:50px;"/>
				~
				<form:input path="maxSubtotal" htmlEscape="false" maxlength="64" class="input-medium"  style="width:50px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input onclick="exportexcel()" class="btn btn-primary" type="button" value="导出excel"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>生成时间</th>
				<th>所属供应商</th>
				<th>采购单位编号</th>
				<th>商品数量</th>
				<th>总价</th>
				<th>发票编号</th>
				<th>状态</th>
				<shiro:hasPermission name="sco:scoAcceptanceReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="adminStatistic">
			<tr>
				<td>
					<fmt:formatDate value="${adminStatistic.report.buyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${adminStatistic.createBy.name}
				</td>
				<td>
						${adminStatistic.report.department}
				</td>
				<td>
						${adminStatistic.goodsNumbers}
				</td>
				<td>
					<fmt:formatNumber value="${adminStatistic.subtotal}" type="currency" pattern="￥0.00"/>
				</td>
				<td>
						${adminStatistic.report.invoicenum}
				</td>
				<td>
						${fns:getDictLabel(adminStatistic.report.state, "audit_report", "0")}
				</td>
				<shiro:hasPermission name="sco:scoAcceptanceReport:edit">
					<td>
					
					
				<c:if test="${param.type == '3'}">
					<a href="${ctx}/sco/scoAcceptanceReport/report/print?id=${adminStatistic.report.id}&type=${param.type}" target="_blank">查看</a>
				</c:if>
				<c:if test="${param.type == '5'}">
					<a href="${ctx}/sco/scoAcceptanceReport/report/print/print?id=${adminStatistic.report.id}&type=${param.type}" target="_blank">查看</a>
				</c:if>
				<c:if test="${param.type == '6'}">
					<a href="${ctx}/sco/scoAcceptanceReport/report/print/print?id=${adminStatistic.report.id}&type=${param.type}" target="_blank">查看</a>
				</c:if>
					
					
					
					<a href="${ctx}/sco/scoAcceptanceReport/admin/delete?id=${adminStatistic.report.id}&type=${param.type}" onclick="return confirmx('确认要删除该验收单吗？', this.href)">删除</a>
						<%--<c:if test="${fns:getDictValue('审核通过', 'audit_report', '2') != adminStatistic.report.state--%>
					 <%--&& fns:getDictValue('默认通过', 'audit_report', '3') != adminStatistic.report.state }">--%>
						<c:if test="${fns:getDictValue('待审核', 'audit_report', '1') == adminStatistic.report.state }">
					<a href="${ctx}/sco/scoAcceptanceReport/passAudit?id=${adminStatistic.report.id}&state=${adminScoAcceptanceStatistic.report.state}&createBy.id=${adminScoAcceptanceStatistic.createBy.id}&department=${adminScoAcceptanceStatistic.report.department}&pageNo=${page.pageNo}&pageSize=${page.pageSize}&type=${param.type}" onclick="return confirmx('确认要审核通过该验收单吗？', this.href)">审核</a>
						</c:if>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
