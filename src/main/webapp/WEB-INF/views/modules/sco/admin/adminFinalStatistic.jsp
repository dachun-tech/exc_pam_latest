<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>结算单统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出统计数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/admin/sco/scoFinalReport/statistic/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});

		function page(n,s){
			$("#searchForm").attr("action","${ctx}/admin/sco/scoFinalReport/statistic");
			$("#searchForm").submit();
			return false;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/admin/sco/scoFinalReport/statistic?type=${param['type']}">结算单统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="scoFinalReport" action="${ctx}/admin/sco/scoFinalReport/statistic" method="post" class="breadcrumb form-search">
		<form:hidden path="type" />
		<ul class="ul-form">
			<li><label>年份：</label>
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
			<li><label>单位编号：</label>
				<form:input path="department" htmlEscape="false" maxlength="64" class="input-large"/>
			</li>
			<%--
			<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '') != ''}">
				<li><label>${fns:getDictValueBySuffix('lable_identifier', 'report_form_', param.type, '')}：</label>
					<form:input path="identifier" htmlEscape="false" maxlength="64" class="input-medium"/>
				</li>
			</c:if>
			 --%>
			<li class="clearfix"></li>
			<li><label>供应商：</label>
				<form:select path="createBy.id" class="input-large">
					<form:option value="" label="--全部--"/>
					<form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<%--
			<c:if test="${fns:getDictValueBySuffix('tree_1', 'supplier_tree_', param.type, '') != ''}">
			<li><label>目录：</label>
				<form:select path="serTreeId" class="input-large" >
					<form:option value="" label="请选择${fns:getDictValueBySuffix('lable_brand_1', 'second_form_', param.type, '')}"/>
					<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			</c:if>
			 --%>
			 
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>供应商</th>
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
        <c:if test="${list.size()>0 }">
		<c:forEach items="${list}" var="statistic" varStatus="status">
			<tr>
				<td style="text-align: center;" rowspan="${fn:length(statistic.statisticList)+1 } ">
						${statistic.createBy.name}<br><br>
					( 销售商品总数量: ${statistic.totalFinal}, 总金额: <fmt:formatNumber value="${statistic.totalAmt}" type="currency" pattern="￥0.00"/> )
							<br>
				</td>
			</tr>
			<c:forEach items="${statistic.statisticList}" var="suppliers" varStatus="status">
				<tr>
					<td align="center">${suppliers.month}</td>
					<td align="center">${suppliers.finalNumbers}</td>
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
					<td align="center"><fmt:formatNumber value="${suppliers.subtotal}" type="currency" pattern="￥0.00"/></td>
				</tr>
			</c:forEach>


		</c:forEach>
		<tr>
			<td style="text-align: center;">总计</td>
			<td align="center">&nbsp;</td>
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
		</c:if>
		</tbody>
	</table>
	<div class="control-group">
		<label class="control-label" style="color: red;">
			*按年度统计<br>
		</label>
	</div>
</body>
</html>