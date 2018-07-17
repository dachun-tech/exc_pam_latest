<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>印刷品添加</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出商品数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sco/scoPrints/export");
						$("#searchForm").submit();
						$("#searchForm").attr("action","${ctx}${url }");
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
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

		function addAcceptanceReport(goodsId){
			var url = "${ctx}/sco/scoGoodsAcceptance/addAcceptanceReport";
			var currThis = $("#add_"+goodsId);
			$.ajax({
				url: url,
				type:'POST',
				dataType: 'json',
				data:{
					arId:$("#arId").val(),
					goodsId:goodsId,
					type:$("#type").val()
				},
				cache: false,
				async: true,
				success: function(data, textStatus) {
					var rs = data;
					console.log(rs);
					if(rs.code == 0){
						currThis.html("加入成功");
						currThis.removeAttr("onclick");
						currThis.css("color","green");
					}
				}
			});
		}
		function optProduct(id,opt){
			
			var url = "${ctx}/sco/scoPrints/"+opt;
			$.ajax({
				url: url,
				type:'POST',
				dataType: 'json',
				data:{
					id:id
				},
				cache: false,
				async: true,
				success: function(data, textStatus) {
					var rs = data;
					$("#searchForm").submit();
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}${url }?type=${param.type}">商品列表</a></li>
		<shiro:hasPermission name="sco:scoPrints:edit"><li><a href="${ctx}/sco/scoPrints/form?type=${param.type}">商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="scoPrints" action="${ctx}${url }" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="handle" name="handle" type="hidden" value="${param.handle}"/>
		<input id="reportId" name="reportId" type="hidden" value="${param.reportId}"/>
		<input id="type" name="type" type="hidden" value="${param.type}"/>
		<input id="arId" name="arId" type="hidden" value="${param.arId}"/>
		
		<ul class="ul-form">
		
			<c:choose>
		        <c:when test="${param.type == 6}">
		             	
		             	<li><label>目录选择：</label>
							<form:select path="serTreeId" class="input-medium" onchange="serSubTree(this.value);">
								<form:option value="" label="请选择"/>
								<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</li>
						<li>
							<form:select path="subId" class="input-medium">
								<form:option value="" label="请选择"/>
								<form:options items="${serSubTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</li>
		        </c:when>
		        <c:when test="${param.type == 5}">
		             	
						<li><label>目录选择：</label>
							<form:select path="subId" class="input-medium">
								<form:option value="" label="请选择"/>
								<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</li>
		        </c:when>
		        <c:otherwise>
		        </c:otherwise>
		    </c:choose>
		
			
			<li><label>名称：</label>
				<form:input path="printName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<shiro:hasPermission name="sco:scoPrints:audit"> 
			<li><label>供应商：</label>
				<form:select path="createBy.id" class="input-xlarge">
					<form:option value="" label="--全部供应商--"/>
					<form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			</shiro:hasPermission>
			
			<li><label>价格：</label>
				<form:input path="minPrice" htmlEscape="false" maxlength="64" class="input-medium" style="width:50px;"/>
				~
				<form:input path="maxPrice" htmlEscape="false" maxlength="64" class="input-medium"  style="width:50px;"/>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-xlarge" >
					<form:option value="" label="--全部--"/>
					<form:options items="${auditReportList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="sco:scoPrints:audit"> 
				<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			</shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width: 100px;">生成日期</th>
				<th>名称</th>
				<th>计量单位</th>
				<th>单价(元)</th>
				<th>所属供应商</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoPrints" varStatus="status">
			<tr>
				<td>
						<%--${status.index+1}--%>
					<fmt:formatDate value="${scoPrints.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="color: #2fa4e7;">
						${scoPrints.printName}
					<%--<a href="${ctx}/sco/scoPrints/form?id=${scoPrints.id}">--%>
						<%--${scoPrints.name}--%>
					<%--</a>--%>
				</td>
				<td>
						${scoPrints.units}
				</td>
				<td>
					<fmt:formatNumber value="${scoPrints.price}" pattern="### ##0.00"/>
				</td>
<%-- 				<c:if test="${fns:getDictValueBySuffix('lable_brand', 'first_form_', param.type, '') != ''}"> --%>
<!-- 					<td> -->
<%-- 							${scoPrints.serTreeId.name} --%>
<!-- 					</td> -->
<!-- 					<td> -->
<%-- 							${scoPrints.subId.name} --%>
<!-- 					</td> -->
<%-- 				</c:if> --%>
				
				
				<td>
						${scoPrints.createBy.name}
				</td>
				<td>
					${fns:getDictLabel(scoPrints.state, 'product_audit', '默认通过')}
				</td>
				<td>
				    <shiro:hasPermission name="sco:scoPrints:edit">
<%-- 								<a href="${ctx}/sco/scoPrints/view?id=${scoPrints.id}&type=${param.type}">预览</a> --%>
					<c:choose>
						<c:when test="${fns:getDictValue('审核通过', 'product_audit', '') == scoPrints.state ||
							 fns:getDictValue('默认通过', 'product_audit', '') == scoPrints.state}">
								<c:choose>
									<c:when test="${param.handle == 'addPrints'}">
										<a id="add_${scoPrints.id}" href="javascript:addAcceptanceReport('${scoPrints.id}');" >加入验收单</a>
									</c:when>
									<c:otherwise>
										<a href="${ctx}/sco/scoPrints/view?id=${scoPrints.id}">预览</a>
<%-- 										<a href="${ctx}/sco/scoPrints/delete?id=${scoPrints.id}" onclick="return confirmx('确认要删除该${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '')}吗？', this.href)">删除</a> --%>
										<a href="javascript:optProduct('${scoPrints.id}','delete')">删除</a>
									</c:otherwise>
								</c:choose>
						</c:when>
						<c:otherwise>
							<a href="${ctx}/sco/scoPrints/form?id=${scoPrints.id}&type=${param.type}">修改</a>
							<a href="${ctx}/sco/scoPrints/view?id=${scoPrints.id}">预览</a>
<%-- 							<a href="${ctx}/sco/scoPrints/delete?id=${scoPrints.id}" onclick="return confirmx('确认要删除该${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '')}吗？', this.href)">删除</a> --%>
							<a href="javascript:optProduct('${scoPrints.id}','delete')">删除</a>
						</c:otherwise>
					</c:choose>
					</shiro:hasPermission>
					<shiro:hasPermission name="sco:scoPrints:audit"> 
						<a href="${ctx}/sco/scoPrints/view?id=${scoPrints.id}">预览</a>
						<a href="javascript:optProduct('${scoPrints.id}','auditDelete')">删除</a>
						<a href="javascript:optProduct('${scoPrints.id}','passAudit')">通过</a>
						<a href="javascript:optProduct('${scoPrints.id}','refuseAudit')">不通过</a>
<%-- 						<a href="${ctx}/sco/scoPrints/auditDelete?id=${scoPrints.id}" onclick="return confirmx('确认要删除该${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '')}吗？', this.href)">删除</a> --%>
<%-- 						<a href="${ctx}/sco/scoPrints/passAudit?id=${scoPrints.id}" onclick="return confirmx('确认要通过审核吗？', this.href)">通过</a> --%>
<%-- 						<a href="${ctx}/sco/scoPrints/refuseAudit?id=${scoPrints.id}" onclick="return confirmx('确认要不通过审核吗？', this.href)">不通过</a> --%>
					
				   </shiro:hasPermission>
					
				</td>
				
			</tr>
		</c:forEach>
		<c:if test="${param.handle == 'addPrints'}">
			<tr>
				<td colspan="7" style="text-align:right;">
					<span style="margin-right: 80px;">
						<input id="addConfirm" class="btn btn-primary" type="button" onclick="window.location='${ctx}/sco/scoAcceptanceReport/addPrints?id=${param.arId}&type=${param.type}';" value="确认"/>
					</span>
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>