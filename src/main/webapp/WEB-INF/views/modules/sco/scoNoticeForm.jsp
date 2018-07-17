<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统公告设置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sco/scoNotice/list">公告信息</a></li>
		<li class="active"><a href="${ctx}/sco/scoNotice/form?id=${scoNotice.id}">公告信息<shiro:hasPermission name="sco:scoNotice:edit">${not empty scoNotice.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sco:scoNotice:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scoNotice" action="${ctx}/sco/scoNotice/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="office.id" value="${fns:getUser().office.id}" />
		<%--<form:hidden path="roleId"/>--%>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">采购机构：</label>
			<div class="controls">
				<form:select path="officeName" class="input-xlarge ">
					<form:option value="${fns:getUser().office.name}" label="${fns:getUser().office.name}"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属业务分类：</label>
			<div class="controls">
				<form:radiobuttons path="roleId" items="${fns:getDictList('sys_supplier')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">类型：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:radiobuttons path="type" items="${fns:getDictList('notice_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="500" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/cms/article" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布人：</label>
			<div class="controls">
				<form:input path="createByName.name" readonly="true" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="publishDate" type="text" readonly="readonly" maxlength="20" class="input-medium required"
					value="<fmt:formatDate value="${scoNotice.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sco:scoNotice:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>