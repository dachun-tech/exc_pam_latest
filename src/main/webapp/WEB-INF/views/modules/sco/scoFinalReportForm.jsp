<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				ignore: "", // 开启hidden验证， 1.9版本后默认关闭
				rules: {
					picUrl: {
						required : function(){
							var agrtPrice = $("#price").val();
							if(agrtPrice!=null&&agrtPrice!=""){
								
								var price = 300;
								if(agrtPrice<price){
									return false;
								}
							}
							return true;
						}
					},
					price: {
						required : function(){
							var agrtPrice = $("#price").val();
							var quantity = $("#quantity").val();
							if(agrtPrice!=null&&agrtPrice!=""&&quantity!=null&&quantity!=""){
								var price = 100000;
								if(agrtPrice*quantity>price){
									return true;
								}
							}
							return false;

						}
					}
				},
				messages: {
					price: {
						required : "总金额不能超过100000元",
						maxlength : ""
					},
					picUrl: {
						required : "价格大于300元，请上传图片",
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

		function serThirdTree(treeId){
			var url = "${ctx}/sco/scoSerTree/serSubTree";
			//重置select2控件的值
			var thirdIdSelect = $("#thirdId");
			$("#thirdId option:not(:first)").remove();
			thirdIdSelect.select2("val","");

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
						thirdIdSelect.append(option);
					}
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li><a href="${ctx}/sco/scoFinalReport/list?type=${param.type}">查看打印结算单</a></li>--%>
		<li class="active"><a href="${ctx}/sco/scoFinalReport/form?id=${scoFinalReport.id}&type=${param.type}">结算单<shiro:hasPermission name="sco:scoFinalReport:edit">${not empty scoFinalReport.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sco:scoFinalReport:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scoFinalReport" action="${ctx}/sco/scoFinalReport/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<form:hidden path="id"/>
		<form:hidden path="type"/>
		<form:hidden path="office.id" value="${fns:getUser().office.id}" />

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
				<span class="help-inline"><font color="red">*</font> 首次填写单位编号请与采购机构联系</span>
			</div>
		</div>
		<c:if test="${fns:getDictValueBySuffix('lable_item_name', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_item_name', 'report_form_', param.type, '文案')}：</label>
				<div class="controls">
					<form:input path="itemName" htmlEscape="false" maxlength="128" class="input-xlarge required"/> &nbsp;
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '文案')}：</label>
				<div class="controls">
					<form:input path="identifier" htmlEscape="false" maxlength="128" class="input-xlarge required"/> &nbsp;
					<%--<c:if test="${fns:getDictValueBySuffix('lable_identifier_type', 'report_form_', param.type, '') != ''}">--%>
					<%--<form:radiobuttons path="identifierType" items="${fns:getDictList('car_identifier_4')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>--%>
					<%--</c:if>--%>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>

		<div class="control-group">
			<label class="control-label">发票编号：</label>
			<div class="controls">
				<form:input path="invoicenum" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">采购日期：</label>
			<div class="controls">
				<input name="buyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${scoFinalReport.buyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
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
		<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_car_type', 'report_form_', param.type, '文案')}：</label>
				<div class="controls">
					<form:select path="serTreeId" class="input-xlarge required" onchange="serSubTree(this.value);">
						<form:option value="" label="请选择品牌"/>
						<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					&nbsp;&nbsp;
					<%--<form:select path="subId" class="input-xlarge required" onchange="serThirdTree(this.value);">--%>
					<form:select path="subId" class="input-xlarge required">
						<form:option value="" label="请选择车系"/>
						<form:options items="${serSubTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					<%--&nbsp;&nbsp;--%>
					<%--<form:select path="thirdId" class="input-medium required">--%>
						<%--<form:option value="" label="请选择型号"/>--%>
						<%--<form:options items="${serThirdTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>--%>
					<%--</form:select>--%>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<c:if test="${fns:getDictValueBySuffix('lable_category_type', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_category_type', 'report_form_', param.type, '文案')}：</label>
				<div class="controls">
					<form:select path="serTreeId" class="input-xlarge required" onchange="serSubTree(this.value);">
						<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_select_name1', 'report_form_', param.type, '')}"/>
						<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					&nbsp;&nbsp;
					<%--<form:select path="subId" class="input-xlarge required" onchange="serThirdTree(this.value);">--%>
					<form:select path="subId" class="input-xlarge required">
						<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_select_name2', 'report_form_', param.type, '')}"/>
						<form:options items="${serSubTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					<%--&nbsp;&nbsp;--%>
					<%--<form:select path="thirdId" class="input-medium required">--%>
						<%--<form:option value="" label="请选择型号"/>--%>
						<%--<form:options items="${serThirdTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>--%>
					<%--</form:select>--%>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<c:if test="${fns:getDictValueBySuffix('lable_twcategory_type', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_twcategory_type', 'report_form_', param.type, '文案')}：</label>
				<div class="controls">
					<form:select path="serTreeId" class="input-xlarge required" >
						<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_select_name1', 'report_form_', param.type, '')}"/>
						<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					&nbsp;&nbsp;
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<c:if test="${fns:getDictValueBySuffix('lable_paper_type', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_paper_type', 'report_form_', param.type, '')}：</label>
				<div class="controls">
					<form:select path="paperTypeId" class="input-xlarge required"  >
						<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_select_name3', 'report_form_', param.type, '')}"/>
						<form:options items="${paperSerTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
					&nbsp;&nbsp;
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<%--规格--%>
		<c:if test="${fns:getDictValueBySuffix('lable_specs', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_specs', 'report_form_', param.type, '规格(mm)')}：</label>
				<div class="controls">
					<form:input path="specs" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<%--页数--%>
		<c:if test="${fns:getDictValueBySuffix('lable_pages', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_pages', 'report_form_', param.type, '页数')}：</label>
				<div class="controls">
					<form:input path="pages" htmlEscape="false" maxlength="12" class="input-large required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<%--数量--%>
		<c:if test="${fns:getDictValueBySuffix('lable_quantity', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_quantity', 'report_form_', param.type, '数量')}：</label>
				<div class="controls">
					<form:input path="quantity" htmlEscape="false" maxlength="12" class="input-large required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<%--单位--%>
		<c:if test="${fns:getDictValueBySuffix('lable_units', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_units', 'report_form_', param.type, '单位')}：</label>
				<div class="controls">
					<form:input path="units" htmlEscape="false" maxlength="12" class="input-large required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<%--单价--%>
		<c:if test="${fns:getDictValueBySuffix('lable_price', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_price', 'report_form_', param.type, '单价')}：</label>
				<div class="controls">
					<form:input id="price" path="price" htmlEscape="false" maxlength="100" class="input-large required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<%--其它费用--%>
		<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', param.type, '') != ''}">
			<div class="control-group">
				<label class="control-label">${fns:getDictValueBySuffix('lable_other_price', 'report_form_', param.type, '其它费用')}：</label>
				<div class="controls">
					<form:input path="otherPrice" htmlEscape="false" maxlength="12" class="input-large number isNumberGtZero"/>
				</div>
			</div>
		</c:if>
		
		<c:if test="${fns:getDictValueBySuffix('lable_descs', 'report_form_', param.type, '') != ''}">
		<div class="control-group">
			<label class="control-label">工艺描述：</label>
			<div class="controls">
				<c:choose>
			        <c:when test="${param.type == 6}">
			        	<c:if test="${descs!=null&&descs.size()>0 }">
						<table>
		                                
							<c:forEach var="item" items="${descs }"> 
							   
							   
							    <tr>
		                                 
		                                 <th style="width:60px;"> ${item.name }</th>
		                                 <c:if test="${item.children!=null&&item.children.size()>0 }">
										<td></td>
										<td>
										<form:checkboxes path="descs" items="${item.children}" itemLabel="name" itemValue="id" htmlEscape="false"/>
										</td>
		<%-- 								<c:forEach var="subitem" items="${item.children }">  --%>
		<%-- 									      <td> <form:checkbox type="checkbox" path="descs" value="${subitem.id }">${subitem.name }</td> --%>
		<%-- 								</c:forEach> --%>
										</c:if>
		                         </tr>
							  
							    
							</c:forEach>
		                 </table>
						</c:if>
			        </c:when>
			        <c:when test="${param.type == 5}">
						<form:textarea path="description" htmlEscape="false" rows="3" maxlength="254" class="input-xlarge"/>
						<span class="help-inline"><font color="red">例如：覆膜、彩色喷绘、压凹凸、压痕、烫印、裁切等</font></span>
			        </c:when>
			        <c:otherwise>
			        </c:otherwise>
			    </c:choose>
				
			</div>
		</div>
		</c:if>
		
		<c:if test="${fns:getDictValueBySuffix('lable_img', 'report_form_', param.type, '') != ''}">
		<div class="control-group">
			<label class="control-label">商品图片：</label>
			<div class="controls">
				<form:hidden id="picUrl" path="picUrl" htmlEscape="false" maxlength="350" class="input-xlarge required"/>
				<sys:ckfinder input="picUrl" type="images" uploadPath="/sco/scoFinalReport" selectMultiple="true"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		</c:if>
		

		<div class="control-group">
			<label class="control-label">特殊情况说明：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sco:scoFinalReport:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>