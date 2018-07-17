<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车辆维修管理</title>
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
		
		
		function resetForm(form1){
			var strs= new Array(); //定义一数组 
			strs=form1.split(","); //字符分割 
			var url = "${ctx}"+strs[0]+"?type=${param.type}";
			$("#searchForm").attr("action",url);
			$("#searchForm").attr("modelAttribute",strs[1]);
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}${url }?type=${param.type}">审核商品列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="${sco }" action="${ctx}${url }?type=${param.type}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="handle" name="handle" type="hidden" value="${param.handle}"/>
		<input id="reportId" name="reportId" type="hidden" value="${param.reportId}"/>
		<ul class="ul-form">
				<c:if test="${fns:getDictValueBySuffix('lable_brand', 'first_form_', param.type, '') != ''}">
				<li><label>车型：</label>
					<form:select path="serTreeId" class="input-medium" onchange="serSubTree(this.value);">
						<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_brand_1', 'first_form_', param.type, '')}"/>
						<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
				</li>
				<li>
					<form:select path="subId" class="input-medium">
						<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_brand_2', 'first_form_', param.type, '')}"/>
						<form:options items="${serSubTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
				</li>
				</c:if>
				<li><label>状态：</label>
					<form:select path="state" class="input-xlarge" >
						<form:option value="" label="--全部--"/>
						<form:options items="${auditReportList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label>供应商：</label>
					<form:select path="createBy.id" class="input-xlarge">
						<form:option value="" label="--全部供应商--"/>
						<form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label>名称：</label>
					<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
				</li>
				
				<li><label>商品种类：</label>
					<form:select path="kind" class="input-xlarge"  onchange="resetForm(this.value);">
						<form:options items="${kindList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th style="width: 100px;">项目名称</th>
				<th>所属供应商</th>
				<th>议价</th>
				<th>挂牌价</th>
				<th>车型</th>
				<th>状态</th>
				<shiro:hasPermission name="sco:scoSparepart:audit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoSparepart" varStatus="status">
			<tr>
				<td style="color: #2fa4e7;">
						${scoSparepart.name}
				</td>
				<td style="color: #2fa4e7;">
						${scoSparepart.createBy.name}
				</td>
				<td>
					<fmt:formatNumber value="${scoSparepart.normalPrice}" pattern="### ##0.00"/>
				</td>
				<td>
					<fmt:formatNumber value="${scoSparepart.agrtPrice}" pattern="### ##0.00"/>
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_brand', 'first_form_', param.type, '') != ''}">
					<td>
							${scoSparepart.serTreeId.name}
				    <c:if test="${scoSparepart.subId.name !=null}">			
							>
							${scoSparepart.subId.name}
					</c:if>		
					</td>
				</c:if>
				
				<td>
					${fns:getDictLabel(scoSparepart.state, 'product_audit', '')}
				</td>
				<shiro:hasPermission name="sco:scoSparepart:audit"><td>
				
				<a href="${ctx}/sco/${sco }/passAudit?id=${scoSparepart.id}" onclick="return confirmx('确认要通过审核吗？', this.href)">通过</a>
				<a href="${ctx}/sco/${sco }/refuseAudit?id=${scoSparepart.id}" onclick="return confirmx('确认要不通过审核吗？', this.href)">不通过</a>

				<a href="${ctx}/sco/${sco }/formView?id=${scoSparepart.id}&type=${param.type}">
						查看
					</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>