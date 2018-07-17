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
			$("#searchForm").attr("action",ctx+"/admin/sco/scoFinalReport/exportexcel");
			$("#searchForm").submit();
			$("#searchForm").attr("action",ctx+"/admin/sco/scoFinalReport/auditList");
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/admin/sco/scoFinalReport/auditList?type=${param['type']}">
		查看审核验收单
		</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="scoFinalStatistic" action="${ctx}/admin/sco/scoFinalReport/auditList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="type" />
		<ul class="ul-form">
		    <li><label>统计年份：</label>
				<%--class="input-medium"  class="input-xlarge"--%>
				<form:select path="year" class="input-small">
					<form:options items="${yearList}"  htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<form:select path="month" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${monthList}" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="report.state" class="input-small" >
					<form:option value="" label="--全部--"/>
					<form:options items="${auditReportList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '') != ''}">
			<li><label>车牌号：</label>
				<form:select path="identifier" class="input-small" >
					<form:option value="" label="--全部--"/>
					<form:options items="${identifierList}" htmlEscape="false"/>
				</form:select>
			</li>
			</c:if>
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
				<%--<th>结算单号</th>--%>
				<th>生成时间</th>
				<th>供应商名称</th>
				<th>采购单位</th>
				<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '文案')}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_car_type', 'report_form_', param.type, '文案')}</th>
				</c:if>
				<th>总价</th>
				<th>发票编号</th>
				<th>状态</th>
				<shiro:hasPermission name="sco:adminScoFinalReport:edit"><th style="width: 40px;">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="finalStatistic">
			<tr>
				<%--<td>--%>
						<%--${scoFinalStatistic.report.serialNumber}--%>
				<%--</td>--%>
				<td>
					<fmt:formatDate value="${finalStatistic.report.buyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${finalStatistic.createBy.name}
				</td>
				<td>
						${finalStatistic.report.department}
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '') != ''}">
					<td>
							${finalStatistic.report.identifier}
					</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', param.type, '') != ''}">
					<td>
					    <c:if test="${finalStatistic.report.serTreeId.name !=null}">
					   		 ${finalStatistic.report.serTreeId.name} 
					    </c:if>
					    <c:if test="${finalStatistic.report.subId.name !=null}">
					   		 > ${finalStatistic.report.subId.name} 
					    </c:if>
					    <c:if test="${finalStatistic.report.thirdId.name !=null}">
					   		 > ${finalStatistic.report.thirdId.name} 
					    </c:if>
					</td>
				</c:if>
				<td>
					<fmt:formatNumber value="${finalStatistic.subtotal}" type="currency" pattern="￥0.00"/>
				</td>
				<td>
						${finalStatistic.report.invoicenum}
				</td>
				<td>
						${fns:getDictLabel(finalStatistic.report.state, "audit_report", "0")}
				</td>
				<shiro:hasPermission name="sco:adminScoFinalReport:edit">
					<td style="width:90px;">
<%-- 					<a href="${ctx}/sco/scoFinalReport/report/view?id=${finalStatistic.report.id}" target="_blank">查看</a> --%>
					<a href="${ctx}/sco/scoFinalReport/report/view?id=${finalStatistic.report.id}&action=print" target="_blank">查看</a>
					<a href="${ctx}/sco/scoFinalReport/admin/delete?id=${finalStatistic.report.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}&type=${param.type}" onclick="return confirmx('确认要删除该结算单吗？', this.href)">删除</a>
						<%--<c:if test="${fns:getDictValue('审核通过', 'audit_report', '2') != adminStatistic.report.state--%>
					 <%--&& fns:getDictValue('默认通过', 'audit_report', '3') != adminStatistic.report.state }">--%>
						<c:if test="${fns:getDictValue('待审核', 'audit_report', '1') == finalStatistic.report.state }">
					<a href="${ctx}/admin/sco/scoFinalReport/passAudit?id=${finalStatistic.report.id}&type=${param['type']}&state=${scoFinalStatistic.report.state}&createBy.id=${scoFinalStatistic.createBy.id}&department=${scoFinalStatistic.report.department}&pageNo=${page.pageNo}&pageSize=${page.pageSize}" onclick="return confirmx('确认要审核通过该结算单吗？', this.href)">审核</a>
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