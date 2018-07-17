<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sco/scoGoods/statistic">商品统计</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>商品所属目录</th>
				<th>商品数量</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="statistic" varStatus="status">
			<tr>
				<td align="center">${status.index+1}</td>
				<td align="center">${statistic.goodsTreeName}</td>
				<td align="center">${statistic.numbers}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="control-group">
		<label class="control-label" style="color: red;">
			*当前商品统计所属目录分类条数<br>
		</label>
	</div>
</body>
</html>