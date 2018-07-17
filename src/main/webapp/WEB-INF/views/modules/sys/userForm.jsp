<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					}
					else if(element.attr("id") == "areaName"){
						console.log(error.text());
						error.appendTo(element.parent().parent());
					}
					else {
						error.insertAfter(element);
					}
				}
			});

			//触发车辆维修和办公用品之间的input属性是否显示
//			$('input:radio[name="roleIdList"]:checked').val()
			$("input:radio[name='roleIdList']").click(function (){
				if(this.value == "4"){
//					$("#area_select").show();
					$("#grade_input").show();
					$("#property_input").show();
//				} else if(this.value == "3"){
				} else {
//					$("#area_select").hide();
					$("#grade_input").hide();
					$("#property_input").hide();
				}
			});
			//打开页面时，显示对应业务的属性
//			$("input:radio[name='roleIdList'][value='3']").attr("checked",true);
			var roleIdchecked = $('input:radio[name="roleIdList"]:checked').val();
			if(roleIdchecked == "4"){
//				$("#area_select").show();
				$("#grade_input").show();
				$("#property_input").show();
//			} else if(roleIdchecked == "3") {
			} else {
//				$("#area_select").hide();
				$("#grade_input").hide();
				$("#property_input").hide();
			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="company.id"/>
		<form:hidden path="userType" />
		<sys:message content="${message}"/>
		<div class="control-group">
			<c:choose>
				<c:when test="${fns:getUser().isAdmin() || fns:getUser().id eq user.id}">
					<label class="control-label">用户角色:</label>
					<div class="controls">
						<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</c:when>
				<c:otherwise>
					<label class="control-label">所属业务分类：</label>
					<div class="controls">
						<form:radiobuttons path="roleIdList" items="${fns:getDictList('sys_supplier')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
								title="部门" url="/sys/office/treeData?type=2" cssClass="required" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账号:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="required userName"/>
				<span class="help-inline"><font color="red">*</font> 初始密码为111111 </span>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">工号:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="no" htmlEscape="false" maxlength="50" class="required"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">公司全称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">公司简称:</label>
			<div class="controls">
				<form:input path="companySimple" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人:</label>
			<div class="controls">
				<form:input path="linkman" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">确认密码:</label>--%>
			<%--<div class="controls">--%>
				<%--<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>--%>
				<%--<c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">固定电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="100" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行账号:</label>
			<div class="controls">
				<form:input path="bankNumber" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户行:</label>
			<div class="controls">
				<form:input path="bankName" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否允许登录:</label>
			<div class="controls">
				<form:select path="loginFlag">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">用户类型:</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select path="userType" class="input-xlarge">--%>
					<%--<form:option value="" label="请选择"/>--%>
					<%--<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
			<%--</div>--%>
		<%--</div>--%>

		<%--<div class="control-group" id="area_select">--%>
			<%--<label class="control-label">所在区县:</label>--%>
			<%--<div class="controls">--%>
				<%--<sys:treeselect id="area" name="area.id" value="${user.area.id}" labelName="area.name" labelValue="${user.area.name}"--%>
								<%--title="区域" url="/sys/area/treeData" cssClass="required" dataMsgRequired="请选择区县"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group" id="grade_input">
			<label class="control-label">资质等级:</label>
			<div class="controls">
				<form:input path="grade" htmlEscape="false" maxlength="100" />
			</div>
		</div>
		<div class="control-group" id="property_input">
			<label class="control-label">单位性质:</label>
			<div class="controls">
				<form:input path="property" htmlEscape="false" maxlength="100" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司简介:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司图片:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<c:if test="${not empty user.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<%--<div class="control-group">--%>
				<%--<label class="control-label">最后登陆:</label>--%>
				<%--<div class="controls">--%>
					<%--<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>--%>
				<%--</div>--%>
			<%--</div>--%>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>