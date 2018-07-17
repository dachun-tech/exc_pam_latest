<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统公告设置管理</title>
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
		<li class="active"><a href="${ctx}/sco/scoNotice/list">公告信息</a></li>
		<shiro:hasPermission name="sco:scoNotice:edit"><li><a href="${ctx}/sco/scoNotice/form?roleId=${scoNotice.roleId}">公告信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="scoNotice" action="${ctx}/sco/scoNotice/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label style="width: auto;">所属业务分类：</label>
				<form:select path="roleId" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('sys_supplier')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>标题</th>
				<th>发布日期</th>
				<th>公告类型</th>
				<th>发布人</th>
				<shiro:hasPermission name="sco:scoNotice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoNotice">
			<tr>
				<td><a href="${ctx}/sco/scoNotice/form?id=${scoNotice.id}">
					${scoNotice.title}
				</a></td>
				<td>
					<fmt:formatDate value="${scoNotice.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(scoNotice.type, 'notice_type', '')}
				</td>
				<td>
					${scoNotice.createByName.name}
				</td>
				<shiro:hasPermission name="sco:scoNotice:edit"><td>
    				<a href="${ctx}/sco/scoNotice/form?id=${scoNotice.id}">修改</a>
					<a href="${ctx}/sco/scoNotice/delete?id=${scoNotice.id}" onclick="return confirmx('确认要删除该系统公告设置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>