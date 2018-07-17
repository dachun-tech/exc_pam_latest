<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>轮胎管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出商品数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sco/scoTyre/export");
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
					serType: '3'
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
			
			var url = "${ctx}/sco/scoTyre/"+opt;
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
		<li class="active"><a href="${ctx}${url }?type=${param.type}">${fns:getDictValueBySuffix("lable_title", "third_form_", param.type, "")}列表</a></li>
		<shiro:hasPermission name="sco:scoTyre:edit"><li><a href="${ctx}/sco/scoTyre/form?type=${param.type}">${fns:getDictValueBySuffix("lable_title", "third_form_", param.type, "")}添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="scoTyre" action="${ctx}${url }" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="handle" name="handle" type="hidden" value="${param.handle}"/>
		<input id="reportId" name="reportId" type="hidden" value="${param.reportId}"/>
		<input id="type" name="type" type="hidden" value="${param.type}"/>
		<ul class="ul-form">
			<c:if test="${fns:getDictValueBySuffix('lable_brand', 'third_form_', param.type, '') != ''}">
				<li><label>目录选择：</label>
					<form:select path="serTreeId" class="input-medium">
						<form:option value="" label="请选择品牌"/>
						<form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
					</form:select>
				</li>
			</c:if>
			<li><label>${fns:getDictValueBySuffix("lable_1", "third_form_", param.type, "")}：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<shiro:hasPermission name="sco:scoTyre:audit">
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
			<shiro:hasPermission name="sco:scoTyre:audit"> 
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
				<c:if test="${fns:getDictValueBySuffix('lable_1', 'third_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix("lable_1", "third_form_", param.type, "文案")}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_2', 'third_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix("lable_2", "third_form_", param.type, "文案")}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_3', 'third_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix("lable_3", "third_form_", param.type, "文案")}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_4', 'third_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix("lable_4", "third_form_", param.type, "文案")}</th>
				</c:if>
				<th>挂牌价</th>
				<th>协议价</th>
				<c:if test="${fns:getDictValueBySuffix('lable_brand', 'third_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_brand', 'third_form_', param.type, '文案')}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_commitment', 'third_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_commitment', 'third_form_', param.type, '服务承诺')}</th>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_remarks', 'third_form_', param.type, '') != ''}">
					<th>${fns:getDictValueBySuffix('lable_remarks', 'third_form_', param.type, '描述')}</th>
				</c:if>
				<th>所属供应商</th>
				<th>状态</th>
				
				<shiro:hasPermission name="sco:scoTyre:edit"><th>操作</th></shiro:hasPermission>
				<shiro:hasPermission name="sco:scoTyre:audit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scoTyre" varStatus="status">
			<tr>
				<td>
					<%--${status.index+1}--%>
					<fmt:formatDate value="${scoTyre.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="color: #2fa4e7;">
					<%--<a href="${ctx}/sco/scoTyre/form?id=${scoTyre.id}">--%>
					<%--${scoTyre.name}--%>
					<%--</a>--%>
					${scoTyre.name}
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_2', 'third_form_', param.type, '') != ''}">
					<td>
						${scoTyre.specs}
					</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_3', 'third_form_', param.type, '') != ''}">
					<td>
						${scoTyre.loadIndex}
					</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_4', 'third_form_', param.type, '') != ''}">
					<td>
						${scoTyre.speedGrade}
					</td>
				</c:if>
				<td>
					<fmt:formatNumber value="${scoTyre.normalPrice}" pattern="### ##0.00"/>
				</td>
				<td>
					<fmt:formatNumber value="${scoTyre.agrtPrice}" pattern="### ##0.00"/>
				</td>
				<c:if test="${fns:getDictValueBySuffix('lable_brand', 'third_form_', param.type, '') != ''}">
					<td>
							${scoTyre.serTreeId.name}
					</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_commitment', 'third_form_', param.type, '') != ''}">
					<td>${scoTyre.commitment}</td>
				</c:if>
				<c:if test="${fns:getDictValueBySuffix('lable_remarks', 'third_form_', param.type, '') != ''}">
					<td>${scoTyre.remarks}</td>
				</c:if>
				
				<td>
						${scoTyre.createBy.name}
				</td>
				
				<td>
					${fns:getDictLabel(scoTyre.state, 'product_audit', '默认通过')}
				</td>
				<shiro:hasPermission name="sco:scoTyre:edit"><td>
					<c:choose>
						<c:when test="${param.handle == 'addSer'}">
							<a id="add_${scoTyre.id}" href="javascript:;" onclick="addFinalReport('${scoTyre.id}');return false;">加入结算单</a>
						</c:when>
						<c:otherwise>
						    <a href="${ctx}/sco/scoTyre/formView?id=${scoTyre.id}&type=${param.type}">
								预览
							</a>
							<%--<a href="${ctx}/sco/scoTyre/form?id=${scoTyre.id}&type=${param.type}">修改</a>--%>
<%-- 							<a href="${ctx}/sco/scoTyre/delete?id=${scoTyre.id}" onclick="return confirmx('确认要删除该${fns:getDictValueBySuffix('lable_title', 'third_form_', param.type, '')}吗？', this.href)">删除</a> --%>
								<a href="javascript:optProduct('${scoTyre.id}','delete')">删除</a>
						</c:otherwise>
					</c:choose>
				</td></shiro:hasPermission>
				
				<shiro:hasPermission name="sco:scoTyre:audit"><td>
<%-- 				<a href="${ctx}/sco/scoTyre/auditDelete?id=${scoTyre.id}" onclick="return confirmx('确认要删除该${fns:getDictValueBySuffix('lable_title', 'third_form_', param.type, '')}吗？', this.href)">删除</a> --%>
				
<%-- 				<a href="${ctx}/sco/scoTyre/passAudit?id=${scoTyre.id}" onclick="return confirmx('确认要通过审核吗？', this.href)">通过</a> --%>
<%-- 				<a href="${ctx}/sco/scoTyre/refuseAudit?id=${scoTyre.id}" onclick="return confirmx('确认要不通过审核吗？', this.href)">不通过</a> --%>
					
					<a href="javascript:optProduct('${scoTyre.id}','auditDelete')">删除</a>
					<a href="javascript:optProduct('${scoTyre.id}','passAudit')">通过</a>
					<a href="javascript:optProduct('${scoTyre.id}','refuseAudit')">不通过</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>

		<c:if test="${param.handle == 'addSer'}">
			<tr>
				<td colspan="8" style="text-align:right;">
					<span style="margin-right: 80px;">
						<input id="addConfirm" class="btn btn-primary" type="button" onclick="window.location='${ctx}/sco/scoFinalReport/addSer?id=${param.reportId}&type=${scoTyre.type}';" value="确认"/>
					</span>
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>