<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算单统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
		<li class="active"><a href="${ctx}/sco/scoFinalReport/statistic?type=${type}">结算单统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="scoFinalReport" action="${ctx}/sco/scoFinalReport/statistic" method="post" class="breadcrumb form-search">
		<form:hidden path="type" />
		<ul class="ul-form">
			<li><label>统计年份：</label>
				<form:select path="year" class="input-small">
					<form:options items="${yearList}" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<form:select path="month" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${monthList}" htmlEscape="false"/>
				</form:select>
			</li>
			<c:if test="${fns:getDictValueBySuffix('tree_1', 'supplier_tree_', param.type, '') != ''}">
			<li><label>目录：</label>
				<form:select path="serTreeId" class="input-large"  onchange="serSubTree(this.value);">
					<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_brand_1', 'second_form_', param.type, '')}"/>
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>月份</th>
				<th>结算单数量</th>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '')}金额</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'second_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_title', 'second_form_', param.type, '')}金额</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'third_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_title', 'third_form_', param.type, '')}金额</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'fourth_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_title', 'fourth_form_', param.type, '')}金额</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'fifty_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_title', 'fifty_form_', param.type, '')}金额</th>
				</c:if>
				<th>采购总金额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="statistic" varStatus="status">
			<tr>
				<td align="center">${statistic.month}</td>
				<td align="center">${statistic.finalNumbers}</td>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '') != ''}">
					<td align="center"><fmt:formatNumber value="${statistic.sparepartAmt}" type="currency" pattern="￥0.00"/></td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'second_form_', param.type, '') != ''}">
					<td align="center"><fmt:formatNumber value="${statistic.laborAmt}" type="currency" pattern="￥0.00"/></td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'third_form_', param.type, '') != ''}">
					<td align="center"><fmt:formatNumber value="${statistic.tyreAmt}" type="currency" pattern="￥0.00"/></td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'fourth_form_', param.type, '') != ''}">
					<td align="center"><fmt:formatNumber value="${statistic.oilAmt}" type="currency" pattern="￥0.00"/></td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_title', 'fifty_form_', param.type, '') != ''}">
					<td align="center"><fmt:formatNumber value="${statistic.otherAmt}" type="currency" pattern="￥0.00"/></td>
				</c:if>
				<td align="center"><fmt:formatNumber value="${statistic.subtotal}" type="currency" pattern="￥0.00"/></td>
			</tr>
		</c:forEach>
		<tr>
			<td align="center">总计</td>
			<td align="center">${totalFinal}</td>
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'first_form_', param.type, '') != ''}">
				<td align="center"><fmt:formatNumber value="${totalSparepartAmt}" type="currency" pattern="￥0.00"/></td>
			</c:if>
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'second_form_', param.type, '') != ''}">
				<td align="center"><fmt:formatNumber value="${totalLaborAmt}" type="currency" pattern="￥0.00"/></td>
			</c:if>
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'third_form_', param.type, '') != ''}">
				<td align="center"><fmt:formatNumber value="${totalTyreAmt}" type="currency" pattern="￥0.00"/></td>
			</c:if>
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'fourth_form_', param.type, '') != ''}">
				<td align="center"><fmt:formatNumber value="${totalOilAmt}" type="currency" pattern="￥0.00"/></td>
			</c:if>
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'fifty_form_', param.type, '') != ''}">
				<td align="center"><fmt:formatNumber value="${totalOtherAmt}" type="currency" pattern="￥0.00"/></td>
			</c:if>
			<td align="center"><fmt:formatNumber value="${totalAmt}" type="currency" pattern="￥0.00"/></td>
		</tr>
		</tbody>
	</table>
	<div class="control-group">
		<label class="control-label" style="color: red;">
			*按年度统计<br>
		</label>
	</div>
</body>
</html>