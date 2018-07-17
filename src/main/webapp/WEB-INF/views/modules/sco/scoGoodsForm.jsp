<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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

			$("#inputForm").validate({
				ignore: "", // 开启hidden验证， 1.9版本后默认关闭
				rules: {
					picUrl: {
						required : function(){
							var agrtPrice = $("#agrtPrice").val();
							var price = 500;
							if(agrtPrice<price){
								return false;
							}else{
								return true;
							}
						}
					}
				},
				messages: {
					picUrl: {
						required : "价格大于500元，请上传图片",
						maxlength : "您选择的图片过多,建议3张"
					}
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
					} else {
						error.insertAfter(element);
					}
				}
			});
		});


		function secMenuTree(goodsTreeId){
			var url = "${ctx}/sco/scoGoods/secMenuTree";
			//重置select2控件的值
			var subNameSelect = $("#subName");
			$("#subName option:not(:first)").remove();
			subNameSelect.select2("val","");
			$("#subId").val("");

			$.ajax({
				url: url,
				dataType: 'json',
				data:{
					goodsTreeId:goodsTreeId
				},
				cache: false,
				async: true,
				success: function(data, textStatus) {
					var secTreeList = data;
					for(var node in secTreeList){
						var option = "<option value='"+secTreeList[node].name+"' id='"+secTreeList[node].id+"'>"+secTreeList[node].name+"</option>";
						subNameSelect.append(option);
					}
				}
			});
		}
		function secSubIdVal(){
			$("#subId").val($("#subName").find("option:selected").attr("id"));
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li><a href="${ctx}/sco/scoGoods/list">商品列表</a></li>--%>
		<li class="active"><a href="${ctx}/sco/scoGoods/form?id=${scoGoods.id}">商品<shiro:hasPermission name="sco:scoGoods:edit">${not empty scoGoods.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sco:scoGoods:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scoGoods" action="${ctx}/sco/scoGoods/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="subId"/>
		<form:hidden path="state" />

		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">目录：</label>
			<div class="controls" id="selectTreeConsole">
				<form:select path="goodsTreeId" class="input-xlarge required" onchange="secMenuTree(this.value);">
					<form:option value="" label="全部商品"/>
					<form:options items="${goodsTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				&nbsp;&nbsp;
				<form:select path="subName" class="input-xlarge required" onchange="secSubIdVal()">
					<form:option value="" label="--请选择--"/>
					<form:options items="${goodsSecTreeList}" itemLabel="name" itemValue="name" id="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> 请选择二级目录</span>
				<%--<form:input path="subName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> 填写二级目录，如打印机、笔记本</span>--%>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">二级目录名称：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="subName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> 填写二级目录，如打印机、笔记本</span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">商品名称：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> 填写描述性商品名称，如博文418仿皮本</span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">品牌：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="brand" htmlEscape="false" maxlength="64" class="input-xlarge required"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> 请尽量填写中文，如lenovo，应为联想</span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">商品型号：</label>
			<div class="controls">
				<form:input path="goodsModel" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
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
		<div class="control-group">
			<label class="control-label">计量单位：</label>
			<div class="controls">
				<form:input path="units" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格参数：</label>
			<div class="controls">
				<form:textarea path="specification" htmlEscape="false" rows="4" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">条形码：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:radiobuttons path="isExistCode" items="${fns:getDictList('goods_bar_code')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>--%>
				<%--&nbsp;&nbsp;--%>
				<%--<form:input path="barCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>--%>
				<%--<span class="help-inline"><img src="${ctxStatic}/sco/image/txm.jpg" width="144" height="30"><font color="red">*</font> 如右图红色区域，输入前12位</span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">商品图片：</label>
			<div class="controls">
				<form:hidden id="picUrl" path="picUrl" htmlEscape="false" maxlength="350" class="input-xlarge"/>
				<sys:ckfinder input="picUrl" type="images" uploadPath="/sco/scoGoods" selectMultiple="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sco:scoGoods:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>