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
	<%--<c:set value="/sco/scoPrints/list" var="tabUrl"/>--%>
	<%--<c:if test="${param.origin == 'manager'}">--%>
		<%--<c:set value="/sco/scoPrints/auditList" var="tabUrl"/>--%>
	<%--</c:if>--%>
	<%--<li><a href="${ctx}${tabUrl}">商品列表</a></li>--%>
	<li><a href="javascript:void(0);" onclick="history.go(-1)">商品列表</a></li>
	<li class="active"><a href="">商品预览</a></li>
</ul><br/>
<c:set var="user" value="${fns:getUserById(scoPrints.createBy.id)}" />
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
				商品名称：${scoPrints.printName}
			</p>
			<%--<p>--%>
				<%--商品编号：${scoPrints.goodsNo}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--注册商标：${scoPrints.brand}--%>
			<%--</p>--%>
			<p>
				供&nbsp;应&nbsp;商：${user.name}
			</p>
			<p>
				计量单位：${scoPrints.units}
			</p>
			<p>
				规格参数：${scoPrints.specification}
			</p>
			<p>
				页数：${scoPrints.printPageSize}
			</p>
			<p>
				工艺描述：
				
				<c:choose>
			        <c:when test="${scoPrints.type == 6}">
			        	<c:if test="${descs!=null&&descs.size()>0 }">
						<table>
		                                
							<c:forEach var="item" items="${descs }"> 
							   <c:if test="${item.selected }">
							   
							    <tr>
		                                 <th> ${item.name }</th>
		                                 <c:if test="${item.children!=null&&item.children.size()>0 }">
			
										<c:forEach var="subitem" items="${item.children }"> 
										    <c:if test="${subitem.selected }">
											   <td> ${subitem.name }</td>
										    </c:if>
										</c:forEach>
										</c:if>
		                         </tr>
							   </c:if>
							    
							</c:forEach>
		                 </table>
						</c:if>
			        </c:when>
			        <c:when test="${scoPrints.type == 5}">
			        	${scoPrints.description}
			        </c:when>
			        <c:otherwise>
			        </c:otherwise>
			    </c:choose>
				
				
<%-- 					<json:object>   --%>
<%-- 				<json:array  var="item" items="${scoPrints.description}">   --%>
<%-- 					<json:object>   --%>
<%-- 					  <json:property name="id" value="${item.id}"/>   --%>
<%-- 					  <json:property name="name" value="${item.name}"/>   --%>
<%-- 					  <json:array name="subitems" var="subitem" items="${item.children}">   --%>
<%-- 					    <json:object>   --%>
<%-- 						  <json:property name="id" value="${subitem.id}"/>   --%>
<%-- 						  <json:property name="name" value="${subitem.name}"/>   --%>
<%-- 					    </json:object>   --%>
<%-- 					  </json:array>   --%>
<%-- 					</json:object> --%>
<%-- 				</json:array>    --%>
<%-- 					</json:object> --%>
			</p>
			<p>
				单&nbsp;价：<font color="red"><fmt:formatNumber value="${scoPrints.price}" type="currency" pattern="￥0.00"/></font>
			</p>
		</td>
	</tr>
</table>
<hr />
<%--<table class="table table-bordered table-condensed" style="background-color:white;">--%>
	<%--<tr>--%>
		<%--<td>--%>
			<%--<p>--%>
				<%--商品型号：${scoPrints.goodsModel}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--计量单位：${scoPrints.units}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--规格参数：${scoPrints.specification}--%>
			<%--</p>--%>
			<%--<p>--%>
				<%--条形码：${scoPrints.barCode}--%>
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