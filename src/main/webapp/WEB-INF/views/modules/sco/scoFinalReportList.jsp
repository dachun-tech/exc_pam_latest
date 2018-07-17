<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算单管理</title>
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
		<li class="active"><a href="${ctx}/sco/scoFinalReport/list?type=${param.type}">打印查看结算单</a></li>
		<%--<shiro:hasPermission name="sco:scoFinalReport:edit"><li><a href="${ctx}/sco/scoFinalReport/form?type=${param.type}">结算单添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="scoFinalReport" action="${ctx}/sco/scoFinalReport/list?type=${param.type}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<form:select path="year" class="input-small">
					<form:option value="" label="请选择年"/>
					<form:options items="${yearList}" htmlEscape="false"/>
				</form:select>
			</li>
			<li>&nbsp;
				<form:select path="month" class="input-small" >
					<form:option value="" label="请选择月"/>
					<form:options items="${monthList}"  htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-xlarge" >
					<form:option value="" label="--全部--"/>
					<form:options items="${auditReportList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>结算单号</th>
				<th>生成时间</th>
				<c:if test="${fns:getDictValueBySuffix('lable_item_name', 'report_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_item_name', 'report_form_', param.type, '文案')}</th>
				</c:if>
				<th>采购单位编号</th>
				<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '文案')}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_car_type', 'report_form_', param.type, '文案')}</th>
				</c:if>
				<th>发票编号</th>
				<th>状态</th>
				<shiro:hasPermission name="sco:scoFinalReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoFinalReport">
			<tr>
				<td>
					${scoFinalReport.serialNumber}
				</td>
				<td>
					<fmt:formatDate value="${scoFinalReport.buyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_item_name', 'report_form_', param.type, '') != ''}">
				<td>
					${scoFinalReport.itemName}
				</td>
				</c:if>
				<td>
					${scoFinalReport.department}
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '') != ''}">
					<td>
						${scoFinalReport.identifier}
					</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', param.type, '') != ''}">
					<td>
						
						 <c:if test="${scoFinalReport.serTreeId.name !=null}">
					   		 ${scoFinalReport.serTreeId.name} 
					    </c:if>
					    <c:if test="${scoFinalReport.subId.name !=null}">
					   		 > ${scoFinalReport.subId.name} 
					    </c:if>
					    <c:if test="${scoFinalReport.thirdId.name !=null}">
					   		 > ${scoFinalReport.thirdId.name} 
					    </c:if>
						
					</td>
				</c:if>
				<td>
						${scoFinalReport.invoicenum}
				</td>
				<td>
						${fns:getDictLabel(scoFinalReport.state, "audit_report", "0")}
				</td>
				<shiro:hasPermission name="sco:scoFinalReport:edit"><td>
					<c:if test="${fns:getDictValue('审核通过', 'audit_report', '2') == scoFinalReport.state
					 || fns:getDictValue('默认通过', 'audit_report', '3') == scoFinalReport.state }">
						<a href="${ctx}/sco/scoFinalReport/report/print?id=${scoFinalReport.id}&action=print" target="_blank">打印</a>
					</c:if>
					<a href="${ctx}/sco/scoFinalReport/report/view?id=${scoFinalReport.id}&action=view" target="_blank">查看</a>
					<c:if test="${fns:getDictValue('未完成', 'audit_report', '0') == scoFinalReport.state
					 || fns:getDictValue('待审核', 'audit_report', '1') == scoFinalReport.state }">
						<a href="${ctx}/sco/scoFinalReport/form?id=${scoFinalReport.id}&type=${param.type}">修改</a>
					<a href="${ctx}/sco/scoFinalReport/delete?id=${scoFinalReport.id}&type=${param.type}" onclick="return confirmx('确认要删除该结算单吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>