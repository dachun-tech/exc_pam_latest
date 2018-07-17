<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品预览</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<%--<c:set value="/sco/scoGoods/list" var="tabUrl"/>--%>
	<%--<c:if test="${param.origin == 'manager'}">--%>
		<%--<c:set value="/sco/scoGoods/auditList" var="tabUrl"/>--%>
	<%--</c:if>--%>
	<%--<li><a href="${ctx}${tabUrl}">商品列表</a></li>--%>
	<li><a href="javascript:void(0);" onclick="history.go(-1)">商品列表</a></li>
	<li class="active"><a href="">商品预览</a></li>
</ul><br/>
<c:set var="user" value="${fns:getUserById(scoGoods.createBy.id)}" />
<table class="table table-bordered table-condensed" style="background-color:white;">
	<tr>
		<td style="width: 420px;">
			<div class="control-group">
				<div class="controls">
					<c:choose>
						<c:when test="${picList == null}">
							<img src="${ctxStatic}/sco/image/nopic.gif">
						</c:when>
						<c:otherwise>
							<c:forEach items="${picList}" var="pic">
								<img src="${pic}" style="width:420px;height:315px;" >
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</td>
		<td style="padding-top: 15px; padding-left: 15px; vertical-align:text-top;">
			<p>
				商品名称：${scoGoods.name}
			</p>
			<%--<p>--%>
				<%--商品编号：${scoGoods.goodsNo}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--注册商标：${scoGoods.brand}--%>
			<%--</p>--%>
			<p>
				供&nbsp;应&nbsp;商：${user.name}
			</p>
			<p>
				商品型号：${scoGoods.goodsModel}
			</p>
			<p>
				计量单位：${scoGoods.units}
			</p>
			<p>
				规格参数：${scoGoods.specification}
			</p>
			<p>
				挂&nbsp;牌&nbsp;价：<font color="red"><fmt:formatNumber value="${scoGoods.normalPrice}" type="currency" pattern="￥0.00"/></font>
			</p>
			<p>
				协&nbsp;议&nbsp;价：<font color="red"><fmt:formatNumber value="${scoGoods.agrtPrice}" type="currency" pattern="￥0.00"/></font>
			</p>
			<p>
				折&nbsp;扣&nbsp;率：<font color="red"><fmt:formatNumber value="${ (scoGoods.agrtPrice / scoGoods.normalPrice)}" type="percent"/></font>
			</p>
		</td>
	</tr>
</table>
<hr />
<%--<table class="table table-bordered table-condensed" style="background-color:white;">--%>
	<%--<tr>--%>
		<%--<td>--%>
			<%--<p>--%>
				<%--商品型号：${scoGoods.goodsModel}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--计量单位：${scoGoods.units}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--规格参数：${scoGoods.specification}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--条形码：${scoGoods.barCode}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--联系人：${user.linkman}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--联系手机：${user.mobile}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--联系电话：${user.phone}--%>
			<%--</p>--%>
		<%--</td>--%>
	<%--</tr>--%>
<%--</table>--%>

<%--<div class="form-actions">--%>
	<%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
<%--</div>--%>

</body>
</html>