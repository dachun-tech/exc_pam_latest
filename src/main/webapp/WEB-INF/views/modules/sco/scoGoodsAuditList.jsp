<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出商品数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sco/scoGoods/auditList/export");
						$("#searchForm").submit();
						$("#searchForm").attr("action","${ctx}/sco/scoGoods/auditList");
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sco/scoGoods/auditList");
			$("#searchForm").submit();
        	return false;
        }

		function secMenuTree(goodsTreeId){
			var url = "${ctx}/sco/scoGoods/secMenuTree";
			//重置select2控件的值
			var subIdSelect = $("#subId");
			$("#subId option:not(:first)").remove();
			subIdSelect.select2("val","");

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
						var option = "<option value='"+secTreeList[node].id+"'>"+secTreeList[node].name+"</option>";
						subIdSelect.append(option);
					}
				}
			});
		}
		
		function optProduct(id,opt){
			
			var url = "${ctx}/sco/scoGoods/"+opt;
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
		<li class="active"><a href="${ctx}/sco/scoGoods/auditList">查看审核商品</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="scoGoods" action="${ctx}/sco/scoGoods/auditList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>状态：</label>
				<form:select path="state" class="input-mini ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('product_audit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<form:select path="createBy.id" class="input-xlarge">
					<form:option value="" label="全部供应商"/>
					<form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="clearfix"></li>
			<li><label>目录：</label>
				<form:select path="goodsTreeId" class="input-large" onchange="secMenuTree(this.value);">
					<form:option value="" label="全部商品"/>
					<form:options items="${goodsTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<form:select path="subId" class="input-large">
					<form:option value="" label="--请选择--"/>
					<form:options items="${goodsSecTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>协议价：</label>
				<form:input path="minPrice" htmlEscape="false" maxlength="64" class="input-medium" style="width:50px;"/>
				~
				<form:input path="maxPrice" htmlEscape="false" maxlength="64" class="input-medium"  style="width:50px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
<!-- 				<th>商品型号</th> -->
				<th>品牌型号</th>
				<%--<th>品牌</th>--%>
				<th>所属供应商</th>
				<th>协议价</th>
				<th>挂牌价</th>
				<th>所属目录</th>
				<%--<th>二级目录</th>--%>
				<th>状态</th>
				<shiro:hasPermission name="sco:adminScoGoods:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoGoods">
			<tr>
				<td>
<%-- 					<a href="javascript:void(0)" onclick="window.location.href='${ctx}/sco/scoGoods/form?id=${scoGoods.id}'"> --%>
					<a href="javascript:void(0)">
					${scoGoods.name}</a>
				</td>
				<td>
					${scoGoods.goodsModel}
				</td>
				<%--<td>--%>
					<%--${scoGoods.brand}--%>
				<%--</td>--%>
				<td>
						${scoGoods.createBy.name}
				</td>
				<td>
					<fmt:formatNumber value="${scoGoods.agrtPrice}" pattern="### ##0.00"/>
				</td>
				<td>
					<fmt:formatNumber value="${scoGoods.normalPrice}" pattern="### ##0.00"/>
				</td>
				<td>
					${scoGoods.goodsTreeName}
				</td>
				<%--<td>--%>
					<%--${scoGoods.subName}--%>
				<%--</td>--%>
				<td>
					${fns:getDictLabel(scoGoods.state, 'product_audit', '2')}
				</td>
				<shiro:hasPermission name="sco:adminScoGoods:edit"><td>
					<a href="javascript:optProduct('${scoGoods.id}','auditDelete')">删除</a>
					<a href="javascript:optProduct('${scoGoods.id}','passAudit')">通过</a>
					<a href="javascript:optProduct('${scoGoods.id}','refuseAudit')">不通过</a>
<%-- 					<a href="${ctx}/sco/scoGoods/auditDelete?id=${scoGoods.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a> --%>
<%-- 					<a href="${ctx}/sco/scoGoods/passAudit?id=${scoGoods.id}" onclick="return confirmx('确认要通过审核吗？', this.href)">通过</a> --%>
<%-- 					<a href="${ctx}/sco/scoGoods/refuseAudit?id=${scoGoods.id}" onclick="return confirmx('确认要不通过审核吗？', this.href)">不通过</a> --%>
					<a href="${ctx}/sco/scoGoods/view?id=${scoGoods.id}&origin=manager">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		<c:if test="${param.handle == 'addGoods'}">
			<tr>
				<td colspan="6" style="text-align:right;">
					<span style="margin-right: 80px;">
						<input id="addAcceptance" class="btn btn-primary" type="button" onclick="window.location='${ctx}/sco/scoAcceptanceReport/addGoods?id=${param.arId}';" value="确认"/>
					</span>
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

</body>
</html>