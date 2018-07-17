<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零配件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			jQuery.validator.addMethod("isNumberGtZero", function(value, element) {
				value=parseFloat(value);
				return this.optional(element) || value>0;
			}, "数字必须大于0");

			jQuery.validator.addMethod("isGtNormalPrice", function(value, element) {
				value=parseFloat(value);
				var flag = true;
				var normalPrice = parseFloat($("#normalPrice").val());
				flag = this.optional(element) || value <= normalPrice;
				return flag;
			}, "必须小于挂牌价");

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

		function serSubTree(treeId){
			var url = "${ctx}/sco/scoSerTree/serSubTree";
			//重置select2控件的值
			var subIdSelect = $("#subId");
			$("#subId option:not(:first)").remove();
			subIdSelect.select2("val","");

			$.ajax({
				url: url,
				dataType: 'json',
				data:{
					id:treeId
				},
				cache: false,
				async: true,
				success: function(data, textStatus) {
					var secTreeList = data;
					for(var node in secTreeList){
						var option = "<option value='"+secTreeList[node].id+"'>"+secTreeList[node].name+"</option>";
						subIdSelect.append(option);
					}
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sco/scoSparepart/list?type=${param.type}">${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '')}列表</a></li>
		<li class="active"><a href="${ctx}/sco/scoSparepart/form?id=${scoSparepart.id}&type=${param.type}">${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '')}<shiro:hasPermission name="sco:scoSparepart:edit">${not empty scoSparepart.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sco:scoSparepart:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scoSparepart" action="${ctx}/sco/scoSparepart/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="type" />
		<sys:message content="${message}"/>
		<c:if test="${fns:getDictValueBySuffix('lable_brand', 'first_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_brand', 'first_form_', param.type, '文案')}：</label>
				<div class="controls">
					<form:select path="serTreeId" class="input-xlarge required" onchange="serSubTree(this.value);">
						<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_brand_1', 'first_form_', param.type, '')}"/>
						<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					&nbsp;&nbsp;
					<form:select path="subId" class="input-xlarge required">
						<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_brand_2', 'first_form_', param.type, '')}"/>
						<form:options items="${serSubTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>

		<div class="control-group">
			<label class="control-label">${fns:getDictValueBySuffix('lable_1', 'first_form_', param.type, '文案')}：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${fns:getDictValueBySuffix('lable_2', 'first_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_2', 'first_form_', param.type, '文案')}：</label>
				<div class="controls">
					<form:input path="units" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<c:if test="${fns:getDictValueBySuffix('lable_3', 'first_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix("lable_3", "first_form_",param.type, "文案")}：</label>
				<div class="controls">
					<form:input path="specs" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">挂牌价：</label>
			<div class="controls">
				<form:input path="normalPrice" htmlEscape="false" class="input-xlarge required number isNumberGtZero"/>
				<span class="help-inline"><font color="red">*</font> 人民币（元）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">协议价：</label>
			<div class="controls">
				<form:input path="agrtPrice" htmlEscape="false" class="input-xlarge required number isNumberGtZero isGtNormalPrice"/>
				<span class="help-inline"><font color="red">*</font> 人民币（元）</span>
			</div>
		</div>
		<c:if test="${fns:getDictValueBySuffix('lable_commitment', 'first_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_commitment', 'first_form_', param.type, '服务承诺')}：</label>
				<div class="controls">
					<form:textarea path="commitment" htmlEscape="false" rows="3" maxlength="254" class="input-xlarge"/>
				</div>
			</div>
		</c:if>
		<c:if test="${fns:getDictValueBySuffix('lable_remarks', 'first_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_remarks', 'first_form_', param.type, '描述')}：</label>
				<div class="controls">
					<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="254" class="input-xlarge"/>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sco:scoSparepart:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>