<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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


		function addAcceptanceReport(goodsId){
			var url = "${ctx}/sco/scoGoodsAcceptance/addAcceptanceReport";
			var currThis = $("#add_"+goodsId);
			$.ajax({
				url: url,
				type:'POST',
				dataType: 'json',
				data:{
					arId:'${param.arId}',
					goodsId:goodsId
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
		<li class="active"><a href="${ctx}/sco/scoGoods/">商品列表</a></li>
		<%--<shiro:hasPermission name="sco:scoGoods:edit"><li><a href="${ctx}/sco/scoGoods/form">商品添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="scoGoods" action="${ctx}/sco/scoGoods/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="handle" name="handle" type="hidden" value="${param.handle}"/>
		<input id="arId" name="arId" type="hidden" value="${param.arId}"/>
		<ul class="ul-form">
			<li><label>目录：</label>
				<form:select path="goodsTreeId" class="input-xlarge" onchange="secMenuTree(this.value);">
					<form:option value="" label="全部商品"/>
					<form:options items="${goodsTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<form:select path="subId" class="input-xlarge required">
					<form:option value="" label="--请选择--"/>
					<form:options items="${goodsSecTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>协议价：</label>
				<form:input path="minPrice" htmlEscape="false" maxlength="64" class="input-medium" style="width:50px;"/>
				~
				<form:input path="maxPrice" htmlEscape="false" maxlength="64" class="input-medium"  style="width:50px;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>商品型号</th>
				<%--<th>品牌</th>--%>
				<th>协议价</th>
				<th>挂牌价</th>
				<th>所属目录</th>
				<%--<th>二级目录</th>--%>
				<th>状态</th>
				<shiro:hasPermission name="sco:scoGoods:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoGoods">
			<tr>
				<td style="color: #2fa4e7;">
						${scoGoods.name}
					<%--<a href="${ctx}/sco/scoGoods/form?id=${scoGoods.id}">--%>
					<%--${scoGoods.name}--%>
					<%--</a>--%>
				</td>
				<td>
					${scoGoods.goodsModel}
				</td>
				<%--<td>--%>
					<%--${scoGoods.brand}--%>
				<%--</td>--%>
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
					${fns:getDictLabel(scoGoods.state, 'product_audit', '')}
				</td>
				<shiro:hasPermission name="sco:scoGoods:edit"><td>
					<c:choose>
						<c:when test="${param.handle == 'addGoods'}">
							<c:if test="${fns:getDictValue('审核通过', 'product_audit', '') == scoGoods.state ||
							 fns:getDictValue('默认通过', 'product_audit', '') == scoGoods.state}">
								<a id="add_${scoGoods.id}" href="javascript:;" onclick="addAcceptanceReport('${scoGoods.id}');return false;">加入验收单</a>
							</c:if>
							&nbsp;
						</c:when>
						<c:otherwise>
							<%--<a href="${ctx}/sco/scoGoods/form?id=${scoGoods.id}">修改</a>--%>
<%-- 							<a href="${ctx}/sco/scoGoods/delete?id=${scoGoods.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a> --%>
							<a href="javascript:optProduct('${scoGoods.id}','delete')">删除</a>
							<a href="${ctx}/sco/scoGoods/view?id=${scoGoods.id}">预览</a>
						</c:otherwise>
					</c:choose>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>

		<tr>
			<td colspan="7" style="text-align:right;">
			<c:choose>
				<c:when test="${param.handle == 'addGoods'}">
					<span style="margin-right: 80px;">
						<input id="addAcceptance" class="btn btn-primary" type="button" onclick="window.location='${ctx}/sco/scoAcceptanceReport/addGoods?id=${param.arId}';" value="确认"/>
					</span>
				</c:when>
				<c:otherwise>
					<span style="margin-right: 120px;">
						<input id="addGoodsGoOn" class="btn btn-primary" type="button" onclick="window.location='${ctx}/sco/scoGoods/form';" value="添加商品"/>
					</span>
				</c:otherwise>
			</c:choose>
			</td>
		</tr>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

</body>
</html>