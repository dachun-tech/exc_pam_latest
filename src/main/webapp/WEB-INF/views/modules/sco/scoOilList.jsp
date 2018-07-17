<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机油管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出商品数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sco/scoOil/export");
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

		function addFinalReport(serId){
			var url = "${ctx}/sco/scoSerFinal/addFinalReport";
			var currThis = $("#add_"+serId);
			$.ajax({
				url: url,
				type:'POST',
				dataType: 'json',
				data:{
					reportId:'${param.reportId}',
					serId:serId,
					serType: '4'
				},
				cache: false,
				async: true,
				success: function(data, textStatus) {
					var rs = data;
					if(rs.code == 0){
						currThis.html("加入成功");
						currThis.removeAttr("onclick");
						currThis.css("color","green");
					}
				}
			});
		}
		
		function optProduct(id,opt){
			
			var url = "${ctx}/sco/scoOil/"+opt;
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
		<li class="active"><a href="${ctx}${url }?type=${param.type}">${fns:getDictValueBySuffix("lable_title", "fourth_form_", param.type, "")}列表</a></li>
		<shiro:hasPermission name="sco:scoOil:edit"><li><a href="${ctx}/sco/scoOil/form?type=${param.type}">${fns:getDictValueBySuffix("lable_title", "fourth_form_", param.type, "")}添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="scoOil" action="${ctx}${url }" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="handle" name="handle" type="hidden" value="${param.handle}"/>
		<input id="reportId" name="reportId" type="hidden" value="${param.reportId}"/>
		<input id="type" name="type" type="hidden" value="${param.type}"/>
		<ul class="ul-form">
			<c:if test="${fns:getDictValueBySuffix('lable_brand', 'fourth_form_', param.type, '') != ''}">
				<li><label>目录选择：</label>
					<form:select path="serTreeId" class="input-medium">
						<form:option value="" label="请选择品牌"/>
						<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
				</li>
			</c:if>
			<li><label>${fns:getDictValueBySuffix("lable_1", "fourth_form_", param.type, "文案")}：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<shiro:hasPermission name="sco:scoOil:audit">
			<li><label>供应商：</label>
				<form:select path="createBy.id" class="input-xlarge">
					<form:option value="" label="--全部供应商--"/>
					<form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			</shiro:hasPermission>
			<li><label>协议价：</label>
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
			
			<shiro:hasPermission name="sco:scoOil:audit"> 
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
				<c:if test="${fns:getDictValueBySuffix('lable_1', 'fourth_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix("lable_1", "fourth_form_", param.type, "文案")}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_2', 'fourth_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix("lable_2", "fourth_form_", param.type, "文案")}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_3', 'fourth_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix("lable_3", "fourth_form_", param.type, "文案")}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_4', 'fourth_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix("lable_4", "fourth_form_", param.type, "文案")}</th>
				</c:if>
				<th>挂牌价</th>
				<th>协议价</th>
				<c:if test="${fns:getDictValueBySuffix('lable_brand', 'fourth_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_brand', 'fourth_form_', param.type, '文案')}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_commitment', 'fourth_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_commitment', 'fourth_form_', param.type, '服务承诺')}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_remarks', 'fourth_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_remarks', 'fourth_form_', param.type, '描述')}</th>
				</c:if>
				<th>所属供应商</th>
				<th>状态</th>
				<shiro:hasPermission name="sco:scoOil:edit"><th>操作</th></shiro:hasPermission>
				<shiro:hasPermission name="sco:scoOil:audit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoOil" varStatus="status">
			<tr>
				<td>
						<%--${status.index+1}--%>
					<fmt:formatDate value="${scoOil.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="color: #2fa4e7;">
					${scoOil.name}
					<%--<a href="${ctx}/sco/scoOil/form?id=${scoOil.id}">--%>
					<%--${scoOil.name}--%>
					<%--</a>--%>
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_2', 'fourth_form_', param.type, '') != ''}">
					<td>
						${scoOil.qualityGrade}
					</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_3', 'fourth_form_', param.type, '') != ''}">
					<td>
						${scoOil.viscosityGrade}
					</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_4', 'fourth_form_', param.type, '') != ''}">
					<td>
						${scoOil.capacityn}
					</td>
				</c:if>
				<td>
					<fmt:formatNumber value="${scoOil.normalPrice}" pattern="### ##0.00"/>
				</td>
				<td>
					<fmt:formatNumber value="${scoOil.agrtPrice}" pattern="### ##0.00"/>
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_brand', 'fourth_form_', param.type, '') != ''}">
					<td>
							${scoOil.serTreeId.name}
					</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_commitment', 'fourth_form_', param.type, '') != ''}">
					<td>${scoOil.commitment}</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_remarks', 'fourth_form_', param.type, '') != ''}">
					<td>${scoOil.remarks}</td>
				</c:if>
				
				<td>
						${scoOil.createBy.name}
				</td>
				<td>
					${fns:getDictLabel(scoOil.state, 'product_audit', '默认通过')}
				</td>
				<shiro:hasPermission name="sco:scoOil:edit"><td>
					<c:choose>
						<c:when test="${param.handle == 'addSer'}">
							<a id="add_${scoOil.id}" href="javascript:;" onclick="addFinalReport('${scoOil.id}');return false;">加入结算单</a>
						</c:when>
						<c:otherwise>
							
						    <a href="${ctx}/sco/scoOil/formView?id=${scoOil.id}&type=${param.type}">
								预览
							</a>
							<%--<a href="${ctx}/sco/scoOil/form?id=${scoOil.id}&type=${param.type}">修改</a>--%>
<%-- 							<a href="${ctx}/sco/scoOil/delete?id=${scoOil.id}" onclick="return confirmx('确认要删除该${fns:getDictValueBySuffix('lable_title', 'fourth_form_', param.type, '')}吗？', this.href)">删除</a> --%>
								<a href="javascript:optProduct('${scoOil.id}','delete')">删除</a>
						</c:otherwise>
					</c:choose>
					
				</td></shiro:hasPermission>
				<shiro:hasPermission name="sco:scoOil:audit"><td>
<%-- 				<a href="${ctx}/sco/scoOil/auditDelete?id=${scoOil.id}" onclick="return confirmx('确认要删除该${fns:getDictValueBySuffix('lable_title', 'fourth_form_', param.type, '')}吗？', this.href)">删除</a> --%>
<%-- 				<a href="${ctx}/sco/scoOil/passAudit?id=${scoOil.id}" onclick="return confirmx('确认要通过审核吗？', this.href)">通过</a> --%>
<%-- 				<a href="${ctx}/sco/scoOil/refuseAudit?id=${scoOil.id}" onclick="return confirmx('确认要不通过审核吗？', this.href)">不通过</a> --%>
					
					<a href="javascript:optProduct('${scoOil.id}','auditDelete')">删除</a>
					<a href="javascript:optProduct('${scoOil.id}','passAudit')">通过</a>
					<a href="javascript:optProduct('${scoOil.id}','refuseAudit')">不通过</a>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		<c:if test="${param.handle == 'addSer'}">
			<tr>
				<td colspan="8" style="text-align:right;">
					<span style="margin-right: 80px;">
						<input id="addConfirm" class="btn btn-primary" type="button" onclick="window.location='${ctx}/sco/scoFinalReport/addSer?id=${param.reportId}&type=${scoOil.type}';" value="确认"/>
					</span>
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>