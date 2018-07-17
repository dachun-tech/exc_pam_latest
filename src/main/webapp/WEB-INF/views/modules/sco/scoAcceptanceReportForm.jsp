<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>验收单管理</title>
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
		<%--<li><a href="${ctx}/sco/scoAcceptanceReport/">打印查看验收单</a></li>--%>
		<li class="active"><a href="${ctx}/sco/scoAcceptanceReport/form?type=${param.type }&id=${scoAcceptanceReport.id}"><shiro:hasPermission name="sco:scoAcceptanceReport:edit">${not empty scoAcceptanceReport.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sco:scoAcceptanceReport:edit">查看</shiro:lacksPermission>验收单</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scoAcceptanceReport" action="${ctx}/sco/scoAcceptanceReport/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="serialNumber"/>
		<form:hidden path="type"/>

		<form:hidden path="office.id" value="${fns:getUser().office.id}" />
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
			<label class="control-label">单位编号：</label>
			<div class="controls">
				<form:input path="department" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">* 首次填写单位编号请与采购机构联系</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="linkman" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">采购日期：</label>
			<div class="controls">
				<input name="buyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required "
					value="<fmt:formatDate value="${scoAcceptanceReport.buyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">* 填写购买商品的日期</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票编号：</label>
			<div class="controls">
				<form:input path="invoicenum" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特殊情况说明：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sco:scoAcceptanceReport:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>