<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>验收单统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sco/scoAcceptanceReport/statistic">验收单统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="scoAcceptanceStatistic" action="${ctx}/sco/scoAcceptanceReport/statistic" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>统计年份：</label>
				<form:select path="year" class="input-xlarge">
					<form:options items="${yearList}" itemLabel="year" itemValue="year" htmlEscape="false"/>
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
				<th>月份</th>
				<th>验收单数量</th>
				<th>商品总数量</th>
				<th>采购总金额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="statistic" varStatus="status">
			<tr>
				<td align="center">${statistic.month}</td>
				<td align="center">${statistic.receivingNumbers}</td>
				<td align="center">${statistic.goodsNumbers}</td>
				<td align="center"><fmt:formatNumber value="${statistic.subtotal}" type="currency" pattern="￥0.00"/></td>
			</tr>
		</c:forEach>
		<tr>
			<td align="center">总计</td>
			<td align="center">${totalReceiving}</td>
			<td align="center">${totalGoods}</td>
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