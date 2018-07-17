<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统审核设置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="sco:scoSetting:edit"><li class="active"><a>系统设置</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<form:form id="searchForm" modelAttribute="scoSetting" action="${ctx}/sco/scoSetting/update" method="post" class="breadcrumb form-search">
		<%--<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>--%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tbody>
		<c:forEach items="${list}" var="scoSetting">
			<tr>
				<td style="text-align: right;padding-right: 20px; width: 180px;">
					${scoSetting.name} : </td>
				<td>

					<div style=" word-spacing:10px;">
						<c:if test="${scoSetting.attribute == 'workYsdConfig'}">
							<input type="radio" name="workYsdConfig_${scoSetting.id}" id="workYsdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="workYsdConfig1">无需审核</label>
							<input type="radio" name="workYsdConfig_${scoSetting.id}" id="workYsdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="workYsdConfig2">必须审核</label>
						</c:if>
						<c:if test="${scoSetting.attribute == 'approveConfig'}">
							<input type="radio" name="approveConfig_${scoSetting.id}" id="approveConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="approveConfig1">无需审核</label>
							<input type="radio" name="approveConfig_${scoSetting.id}" id="approveConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="approveConfig2">必须审核</label>
						</c:if>
						<c:if test="${scoSetting.attribute == 'carJsdConfig'}">
							<input type="radio" name="carJsdConfig_${scoSetting.id}" id="carJsdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="carJsdConfig1">无需审核</label>
							<input type="radio" name="carJsdConfig_${scoSetting.id}" id="carJsdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="carJsdConfig2">必须审核</label>
						</c:if>
						<c:if test="${scoSetting.attribute == 'printingJsdConfig'}">
							<input type="radio" name="printingJsdConfig_${scoSetting.id}" id="printingJsdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="printingJsdConfig1">无需审核</label>
							<input type="radio" name="printingJsdConfig_${scoSetting.id}" id="printingJsdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="printingJsdConfig2">必须审核</label>
						</c:if>
						<c:if test="${scoSetting.attribute == 'imageTextJsdConfig'}">
							<input type="radio" name="imageTextJsdConfig_${scoSetting.id}" id="imageTextJsdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="imageTextJsdConfig1">无需审核</label>
							<input type="radio" name="imageTextJsdConfig_${scoSetting.id}" id="imageTextJsdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="imageTextJsdConfig2">必须审核</label>
						</c:if>
						<c:if test="${scoSetting.attribute == 'inkjetJsdConfig'}">
							<input type="radio" name="inkjetJsdConfig_${scoSetting.id}" id="inkjetJsdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="inkjetJsdConfig1">无需审核</label>
							<input type="radio" name="inkjetJsdConfig_${scoSetting.id}" id="inkjetJsdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="inkjetJsdConfig2">必须审核</label>
						</c:if>
						<c:if test="${scoSetting.attribute == 'videoJsdConfig'}">
							<input type="radio" name="videoJsdConfig_${scoSetting.id}" id="videoJsdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="videoJsdConfig1">无需审核</label>
							<input type="radio" name="videoJsdConfig_${scoSetting.id}" id="videoJsdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="videoJsdConfig2">必须审核</label>
						</c:if>
						<c:if test="${scoSetting.attribute == 'softJsdConfig'}">
							<input type="radio" name="softJsdConfig_${scoSetting.id}" id="softJsdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="softJsdConfig1">无需审核</label>
							<input type="radio" name="softJsdConfig_${scoSetting.id}" id="softJsdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="softJsdConfig2">必须审核</label>
						</c:if>
					</div>


					<%--<div style=" word-spacing:10px;">--%>
						<%--<c:if test="${scoSetting.attribute == 'approveConfig'}">--%>
							<%--<input type="radio" name="approveConfig_${scoSetting.id}" id="approveConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="approveConfig1">仅审核无图片商品</label>--%>
							<%--<input type="radio" name="approveConfig_${scoSetting.id}" id="approveConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="approveConfig2">仅审核无条形码商品</label>--%>
							<%--<input type="radio" name="approveConfig_${scoSetting.id}" id="approveConfig3" <c:if test="${scoSetting.value == '3'}">checked="checked"</c:if> value="3"/><label for="approveConfig3">审核无图片和无条形码商品</label>--%>
							<%--<input type="radio" name="approveConfig_${scoSetting.id}" id="approveConfig4" <c:if test="${scoSetting.value == '4'}">checked="checked"</c:if> value="4"/><label for="approveConfig4">无需审核</label>--%>
							<%--<input type="radio" name="approveConfig_${scoSetting.id}" id="approveConfig5" <c:if test="${scoSetting.value == '5'}">checked="checked"</c:if> value="5"/><label for="approveConfig5">必须审核</label>--%>
						<%--</c:if>--%>
						<%--<c:if test="${scoSetting.attribute == 'ysdConfig'}">--%>
							<%--<input type="radio" name="ysdConfig_${scoSetting.id}" id="ysdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="ysdConfig1">无需审核</label>--%>
							<%--<input type="radio" name="ysdConfig_${scoSetting.id}" id="ysdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="ysdConfig2">必须审核</label>--%>
						<%--</c:if>--%>
						<%--<c:if test="${scoSetting.attribute == 'spaConfig'}">--%>
							<%--<input type="radio" name="spaConfig_${scoSetting.id}" id="spaConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="spaConfig1">无需审核</label>--%>
							<%--<input type="radio" name="spaConfig_${scoSetting.id}" id="spaConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="spaConfig2">必须审核</label>--%>
						<%--</c:if>--%>
						<%--<c:if test="${scoSetting.attribute == 'mhConfig'}">--%>
							<%--<input type="radio" name="mhConfig_${scoSetting.id}" id="mhConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="mhConfig1">无需审核</label>--%>
							<%--<input type="radio" name="mhConfig_${scoSetting.id}" id="mhConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="mhConfig2">必须审核</label>--%>
						<%--</c:if>--%>
						<%--<c:if test="${scoSetting.attribute == 'oilConfig'}">--%>
							<%--<input type="radio" name="oilConfig_${scoSetting.id}" id="oilConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="oilConfig1">无需审核</label>--%>
							<%--<input type="radio" name="oilConfig_${scoSetting.id}" id="oilConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="oilConfig2">必须审核</label>--%>
						<%--</c:if>--%>
						<%--<c:if test="${scoSetting.attribute == 'tyreConfig'}">--%>
							<%--<input type="radio" name="tyreConfig_${scoSetting.id}" id="tyreConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="tyreConfig1">无需审核</label>--%>
							<%--<input type="radio" name="tyreConfig_${scoSetting.id}" id="tyreConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="tyreConfig2">必须审核</label>--%>
						<%--</c:if>--%>
						<%--<c:if test="${scoSetting.attribute == 'jsdConfig'}">--%>
							<%--<input type="radio" name="jsdConfig_${scoSetting.id}" id="jsdConfig1" <c:if test="${scoSetting.value == '1'}">checked="checked"</c:if> value="1"/><label for="jsdConfig1">无需审核</label>--%>
							<%--<input type="radio" name="jsdConfig_${scoSetting.id}" id="jsdConfig2" <c:if test="${scoSetting.value == '2'}">checked="checked"</c:if> value="2"/><label for="jsdConfig2">必须审核</label>--%>
						<%--</c:if>--%>
					<%--</div>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div class="form-actions">
			<shiro:hasPermission name="sco:scoSetting:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
	<div class="pagination">${page}</div>
</body>
</html>