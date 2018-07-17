<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>添加印刷品</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			jQuery.validator.addMethod("isNumberGtZero", function(value, element) {
				value=parseFloat(value);
				return this.optional(element) || value>0;
			}, "数字必须大于0");


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
			
// 			paperSerSubTree($("#paperSerSubId").val());
// 			if($("#serTreeId").val()!=null&&$("#serTreeId").val()!=""){
// 				serSubTree($("#serTreeId").val());
// 			}
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
		function paperSerSubTree(treeId){
			var url = "${ctx}/sco/scoSerTree/serSubTree";
			//重置select2控件的值
			var subIdSelect = $("#paperTypeId");
			$("#paperTypeId option:not(:first)").remove();
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
		<li><a href="${ctx}/sco/scoPrints/list?type=${param.type}">商品列表</a></li>
		<li class="active"><a href="${ctx}/sco/scoPrints/form?id=${scoPrints.id}&type=${param.type}">商品<shiro:hasPermission name="sco:scoPrints:edit">${not empty scoPrints.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sco:scoPrints:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<input type="hidden" value="${paperSerSubId }" id="paperSerSubId">
	<input type="hidden" value="${serTreeId }" id="serTreeId">
	<form:form id="inputForm" modelAttribute="scoPrints" action="${ctx}/sco/scoPrints/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="type" />
		<sys:message content="${message}"/>
		<div class="control-group">
		    <c:choose>
		        <c:when test="${param.type == 6}">
		             	<label class="control-label">印刷品种类：</label>
						<div class="controls">
							<form:select path="serTreeId" class="input-xlarge required" onchange="serSubTree(this.value);">
								<form:option value="" label="请选择"/>
								<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
							&nbsp;&nbsp;
							<form:select path="subId" class="input-xlarge required">
								<form:option value="" label="请选择"/>
								<form:options items="${serSubTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
		        </c:when>
		        <c:when test="${param.type == 5}">
		        <input type="hidden" value="${serTreeId }" name="serTreeId">
		             	<label class="control-label">图文制作商品类别：</label>
						<div class="controls">
							<form:select path="subId" class="input-xlarge required">
								<form:option value="" label="请选择"/>
								<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
		        </c:when>
		        <c:otherwise>
		        </c:otherwise>
		    </c:choose>
		
		</div>
		<div class="control-group">
		    <c:choose>
		        <c:when test="${param.type == 6}">
		        	<label class="control-label">纸张种类：</label>
					<div class="controls">
						<form:select path="paperTypeId" class="input-xlarge required">
							<form:option value="" label="请选择"/>
							<form:options items="${paperTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
		        </c:when>
		        <c:when test="${param.type == 5}">
		        	<label class="control-label">原材料种类：</label>
					<div class="controls">
						<form:select path="paperTypeId" class="input-xlarge required">
							<form:option value="" label="请选择"/>
							<form:options items="${paperTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
		        </c:when>
		        <c:otherwise>
		        </c:otherwise>
		    </c:choose>
			
		</div>

		<div class="control-group">
			<label class="control-label">商品名称（标题）：</label>
			<div class="controls">
				<form:input path="printName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格尺寸（mm）：</label>
			<div class="controls">
				<form:input path="specification" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">页数：</label>
			<div class="controls">
				<form:input path="printPageSize" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单价：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" class="input-xlarge required number isNumberGtZero"/>
				<span class="help-inline"><font color="red">*</font> 人民币（元）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计量单位：</label>
			<div class="controls">
				<form:input path="units" htmlEscape="false" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工艺描述：</label>
			<div class="controls">
				<c:choose>
			        <c:when test="${param.type == 6}">
			        	<c:if test="${descs!=null&&descs.size()>0 }">
						<table id="desctable">
		                                
							<c:forEach var="item" items="${descs }"> 
							   
							   
							    <tr>
		                                 
		                                 <th> ${item.name }</th>
		                                 <c:if test="${item.children!=null&&item.children.size()>0 }">
										<td></td>
										<td>
										<form:checkboxes path="descs" items="${item.children}" itemLabel="name" itemValue="id" htmlEscape="false" class="input-xlarge required"/>
										</td>
		<%-- 								<c:forEach var="subitem" items="${item.children }">  --%>
		<%-- 									      <td> <form:checkbox type="checkbox" path="descs" value="${subitem.id }">${subitem.name }</td> --%>
		<%-- 								</c:forEach> --%>
										</c:if>
		                         </tr>
							  
							    
							</c:forEach>
		                 </table>
		                 <span class="help-inline" id="desctip"><font color="red">*</font></span>
						</c:if>
			        </c:when>
			        <c:when test="${param.type == 5}">
						<form:textarea path="description" htmlEscape="false" rows="3" maxlength="254" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">例如：覆膜、彩色喷绘、压凹凸、压痕、烫印、裁切等</font></span>
			        </c:when>
			        <c:otherwise>
			        </c:otherwise>
			    </c:choose>
				
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">商品图片：</label>
			<div class="controls">
				<form:input  id="picUrl" path="picUrl" htmlEscape="false" maxlength="350" class="input-xlarge required" readonly="true"/>
				<sys:ckfinder input="picUrl" type="images" uploadPath="/sco/scoPrints" selectMultiple="true" />
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sco:scoPrints:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>