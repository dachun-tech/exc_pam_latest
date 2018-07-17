<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务类型管理</title>
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

		//加减
		var setAmount={
			reg:function(x){
				if("" == $.trim(x)){
					return false;
				}
				var reg = new RegExp("^([0-9]+)?$").test(x);
				return reg;
			},
			amount:function(obj,mode){
				var x=$(obj).val();
				if (mode){
					x++;
				}else{
					x--;
				}
				return x;
			},
			reduce:function(obj,id){
				var x=this.amount(obj,false);
				if (x>0){
					$(obj).val(x);

				}else{
					$(obj).val(1);

				}
				updateSerFinal($(obj));
			},
			add:function(obj,id){
				var x=this.amount(obj,true);
				$(obj).val(x);
				updateSerFinal($(obj));

			},
			modify:function(obj,id){
				var x=$(obj).val();
				if (!this.reg(x)){
					$(obj).val(1);
				}else{
					$(obj).val(x);
				}
				updateSerFinal($(obj));
			}
		};

		function updateSerFinal(obj){
			var url = "${ctx}/sco/scoSerFinal/updateSerFinal";
			$.ajax({
				url: url,
				type:'POST',
				dataType: 'json',
				data:{
					id:obj.attr("sfId"),
					numbers:obj.val()
				},
				cache: false,
				async: true,
				success: function(data, textStatus) {
					var rs = data;
					location.replace(location);
				}
			});
		}

		function checkTotalAmt(totalAmt, href){
			var referencePrice = 50000.00;
			<c:choose>
				<c:when test="${scoFinalReport.type == 4}">
					referencePrice = 50000.00;
				</c:when>
				<c:otherwise>referencePrice = 100000.00;</c:otherwise>
			</c:choose>
			if(totalAmt > referencePrice){
				alertx('总金额不能大于'+referencePrice+'元,请调整后提交!');
				return false;
			}
//			return confirmx('确认提交结算单吗,提交后将不可更改?', href);
			return window.location = href;
		}
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<tr>
			<td style="width: 100px;">
				<c:if test="${fns:getDictValueBySuffix('lable_item_name', 'report_form_', scoFinalReport.type, '') == ''}">
					<c:set value="3" var="dm1_3"></c:set>
				</c:if>
				采购机构
			</td>
			<td colspan="${dm1_3}">
				${scoFinalReport.officeName}
			</td>
			<c:if test="${fns:getDictValueBySuffix('lable_item_name', 'report_form_', scoFinalReport.type, '') != ''}">
				<td style="width: 100px;">
						${fns:getDictValueBySuffix('lable_item_name', 'report_form_', scoFinalReport.type, '文案')}
				</td>
				<td colspan="1">
					${scoFinalReport.itemName}
				</td>
			</c:if>
		</tr>
		<tr>
			<td>
				单位编号
				<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', scoFinalReport.type, '') == ''}">
					<c:set value="3" var="dm3"></c:set>
				</c:if>
			</td>
			<td colspan="${dm3}">
				${scoFinalReport.department}
			</td>
			<c:if test="${fns:getDictValueBySuffix('lable_identifier', 'report_form_', scoFinalReport.type, '') != ''}">
				<td style="width: 130px;">
					${fns:getDictValueBySuffix('lable_identifier', 'report_form_', scoFinalReport.type, '文案')}
				</td>
				<td>
					${scoFinalReport.identifier}
				</td>
			</c:if>
		</tr>
		<tr>
			<td>
				发票编号
			</td>
			<td>
				${scoFinalReport.invoicenum}
			</td>
			<td>
				采购日期
			</td>
			<td>
				<fmt:formatDate value="${scoFinalReport.buyDate}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
			<td>
				联系人
			</td>
			<td>
				${scoFinalReport.linkman}
			</td>
			<td>
				手机号码
			</td>
			<td>
				${scoFinalReport.mobile}
			</td>
		</tr>
		<c:if test="${fns:getDictValueBySuffix('lable_car_type', 'report_form_', scoFinalReport.type, '') != ''}">
			<tr>
				<td>
					${fns:getDictValueBySuffix('lable_car_type', 'report_form_', scoFinalReport.type, '文案')}
				</td>
				<td colspan="3">
					 <c:if test="${scoFinalReport.serTreeId.name !=null}">
					   		 ${scoFinalReport.serTreeId.name} 
					    </c:if>
					    <c:if test="${scoFinalReport.subId.name !=null}">
					   		 > ${scoFinalReport.subId.name} 
					    </c:if>
					    <c:if test="${scoFinalReport.thirdId.name !=null}">
					   		 > ${scoFinalReport.thirdId.name} 
					    </c:if>
				</td>
			</tr>
		</c:if>
		<c:if test="${fns:getDictValueBySuffix('lable_specs', 'report_form_', scoFinalReport.type, '') != ''}">
			<tr>
				<td>
						${fns:getDictValueBySuffix('lable_specs', 'report_form_', scoFinalReport.type, '文案')}
				</td>
				<td>
					${scoFinalReport.specs}
				</td>
				<td>
						${fns:getDictValueBySuffix('lable_pages', 'report_form_', scoFinalReport.type, '文案')}
				</td>
				<td>
						${scoFinalReport.pages}
				</td>
			</tr>
		</c:if>
		<c:if test="${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '') != ''}">
			<tr>
				<c:if test="${fns:getDictValueBySuffix('lable_quantity', 'report_form_', scoFinalReport.type, '') != ''}">
					<td>
							${fns:getDictValueBySuffix('lable_quantity', 'report_form_', scoFinalReport.type, '文案')}
					</td>
					<td>
							${scoFinalReport.quantity}
					</td>
				</c:if>
				<td>
					${fns:getDictValueBySuffix('lable_other_price', 'report_form_', scoFinalReport.type, '文案')}
				</td>
				<td colspan="3">
					<fmt:formatNumber value="${scoFinalReport.otherPrice}" type="currency" pattern="￥0.00"/>
				</td>
			</tr>
		</c:if>
	</table>
	<shiro:hasPermission name="sco:scoFinalReport:edit">
		<div style="height: 20px;padding-left: 10px;">
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'first_form_', scoFinalReport.type, '') != ''}">
				<a href="${ctx}/sco/scoSparepart/list?reportId=${scoFinalReport.id}&handle=addSer&type=${scoFinalReport.type}">添加${fns:getDictValueBySuffix('lable_title', 'first_form_', scoFinalReport.type, '')}</a>&nbsp;&nbsp;
			</c:if>
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'second_form_', scoFinalReport.type, '') != ''}">
				<a href="${ctx}/sco/scoLabor/list?reportId=${scoFinalReport.id}&handle=addSer&type=${scoFinalReport.type}">添加${fns:getDictValueBySuffix('lable_title', 'second_form_', scoFinalReport.type, '')}</a>&nbsp;&nbsp;
			</c:if>
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'third_form_', scoFinalReport.type, '') != ''}">
				<a href="${ctx}/sco/scoTyre/list?reportId=${scoFinalReport.id}&handle=addSer&type=${scoFinalReport.type}">添加${fns:getDictValueBySuffix('lable_title', 'third_form_', scoFinalReport.type, '')}</a>&nbsp;&nbsp;
			</c:if>
			<c:if test="${fns:getDictValueBySuffix('lable_title', 'fourth_form_', scoFinalReport.type, '') != ''}">
				<a href="${ctx}/sco/scoOil/list?reportId=${scoFinalReport.id}&handle=addSer&type=${scoFinalReport.type}">添加${fns:getDictValueBySuffix('lable_title', 'fourth_form_', scoFinalReport.type, '')}</a>&nbsp;&nbsp;
			</c:if>
		</div>
	</shiro:hasPermission>
		<hr />

	<c:if test="${fn:length(firstList) != 0}">
		<table id="contentTable1" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<td style="color: #8f8f73;font-weight: bold;">  &nbsp;<span class="icon-arrow-right"></span>&nbsp;${fns:getDictValueBySuffix('lable_title', 'first_form_', scoFinalReport.type, '')}清单 </td>
				</tr>
				<tr>
					<th style="width: 15%;">${fns:getDictValueBySuffix('lable_1', 'first_form_', scoFinalReport.type, '文案')}</th>
					<th style="width: 15%;">公开价(元)</th>
					<th style="width: 15%;">采购单价(元)</th>
					<th style="width: 15%;">折扣(%)</th>
					<th style="width: 12%;">数量</th>
					<th style="width: 15%;">总价</th>
					<shiro:hasPermission name="sco:scoFinalReport:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${firstList}" var="scoSerFinal">
				<tr>
					<td>
							${scoSerFinal.sparepart.name}
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.sparepart.normalPrice}" type="currency" pattern="￥0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.sparepart.agrtPrice}" type="currency" pattern="￥0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.discountPercent}" type="percent"/>
					</td>
					<td style="text-align: center;">
						<div class="shuliang">
							<a onclick="setAmount.reduce('#buy-num_${scoSerFinal.id}')" href="javascript:;" class="icon-minus"></a>
							<input id="buy-num_${scoSerFinal.id}" sfId="${scoSerFinal.id}" name="num" onblur="setAmount.modify('#buy-num_${scoSerFinal.id}');" value="${scoSerFinal.numbers}" maxlength="20" style="width: 60px;text-align: center;" />
							<a onclick="setAmount.add('#buy-num_${scoSerFinal.id}')" href="javascript:;" class="icon-plus"></a>
						</div>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.totalPrice}" type="currency" pattern="￥0.00"/>
					</td>

					<shiro:hasPermission name="sco:scoFinalReport:edit"><td>
						<a href="${ctx}/sco/scoSerFinal/delete?id=${scoSerFinal.id}&type=${scoFinalReport.type}" onclick="return confirmx('确认要删除该服务类型吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>

	<c:if test="${fn:length(secondList) != 0}">
		<table id="contentTable2" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<td style="color: #8f8f73;font-weight: bold;">  &nbsp;<span class="icon-arrow-right"></span>&nbsp;${fns:getDictValueBySuffix('lable_title', 'second_form_', scoFinalReport.type, '')}清单 </td>
			</tr>
			<tr>
				<th style="width: 15%;">${fns:getDictValueBySuffix('lable_1', 'second_form_', scoFinalReport.type, '文案')}</th>
				<th style="width: 15%;">公开价(元)</th>
				<th style="width: 15%;">采购单价(元)</th>
				<th style="width: 15%;">折扣(%)</th>
				<th style="width: 12%;">结算工时</th>
				<th style="width: 15%;">总价</th>
				<shiro:hasPermission name="sco:scoFinalReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${secondList}" var="scoSerFinal">
				<tr>
					<td>
							${scoSerFinal.labor.name}
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.labor.normalPrice}" type="currency" pattern="￥0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.labor.agrtPrice}" type="currency" pattern="￥0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.discountPercent}" type="percent"/>
					</td>
					<td style="text-align: center;">
						<div class="shuliang">
							<a onclick="setAmount.reduce('#buy-num_${scoSerFinal.id}')" href="javascript:;" class="icon-minus"></a>
							<input id="buy-num_${scoSerFinal.id}" sfId="${scoSerFinal.id}" name="num" onblur="setAmount.modify('#buy-num_${scoSerFinal.id}');" value="${scoSerFinal.numbers}" maxlength="4" style="width: 60px;text-align: center;" />
							<a onclick="setAmount.add('#buy-num_${scoSerFinal.id}')" href="javascript:;" class="icon-plus"></a>
						</div>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.totalPrice}" type="currency" pattern="￥0.00"/>
					</td>

					<shiro:hasPermission name="sco:scoFinalReport:edit"><td>
						<a href="${ctx}/sco/scoSerFinal/delete?id=${scoSerFinal.id}&type=${scoFinalReport.type}" onclick="return confirmx('确认要删除该服务类型吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>

	<c:if test="${fn:length(thirdList) != 0}">
		<table id="contentTable2" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<td style="color: #8f8f73;font-weight: bold;">  &nbsp;<span class="icon-arrow-right"></span>&nbsp;${fns:getDictValueBySuffix('lable_title', 'third_form_', scoFinalReport.type, '')}清单 </td>
			</tr>
			<tr>
				<th style="width: 15%;">${fns:getDictValueBySuffix('lable_1', 'third_form_', scoFinalReport.type, '文案')}</th>
				<th style="width: 15%;">公开价(元)</th>
				<th style="width: 15%;">采购单价(元)</th>
				<th style="width: 15%;">折扣(%)</th>
				<th style="width: 12%;">数量</th>
				<th style="width: 15%;">总价</th>
				<shiro:hasPermission name="sco:scoFinalReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${thirdList}" var="scoSerFinal">
				<tr>
					<td>
							${scoSerFinal.tyre.name}
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.tyre.normalPrice}" type="currency" pattern="￥0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.tyre.agrtPrice}" type="currency" pattern="￥0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.discountPercent}" type="percent"/>
					</td>
					<td style="text-align: center;">
						<div class="shuliang">
							<a onclick="setAmount.reduce('#buy-num_${scoSerFinal.id}')" href="javascript:;" class="icon-minus"></a>
							<input id="buy-num_${scoSerFinal.id}" sfId="${scoSerFinal.id}" name="num" onblur="setAmount.modify('#buy-num_${scoSerFinal.id}');" value="${scoSerFinal.numbers}" maxlength="4" style="width: 60px;text-align: center;" />
							<a onclick="setAmount.add('#buy-num_${scoSerFinal.id}')" href="javascript:;" class="icon-plus"></a>
						</div>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.totalPrice}" type="currency" pattern="￥0.00"/>
					</td>

					<shiro:hasPermission name="sco:scoFinalReport:edit"><td>
						<a href="${ctx}/sco/scoSerFinal/delete?id=${scoSerFinal.id}&type=${scoFinalReport.type}" onclick="return confirmx('确认要删除该服务类型吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>

	<c:if test="${fn:length(forthList) != 0}">
		<table id="contentTable2" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<td style="color: #8f8f73;font-weight: bold;">  &nbsp;<span class="icon-arrow-right"></span>&nbsp;${fns:getDictValueBySuffix('lable_title', 'fourth_form_', scoFinalReport.type, '')}清单 </td>
			</tr>
			<tr>
				<th style="width: 15%;">${fns:getDictValueBySuffix('lable_1', 'fourth_form_', scoFinalReport.type, '文案')}</th>
				<th style="width: 15%;">公开价(元)</th>
				<th style="width: 15%;">采购单价(元)</th>
				<th style="width: 15%;">折扣(%)</th>
				<th style="width: 12%;">数量</th>
				<th style="width: 15%;">总价</th>
				<shiro:hasPermission name="sco:scoFinalReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${forthList}" var="scoSerFinal">
				<tr>
					<td>
							${scoSerFinal.oil.name}
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.oil.normalPrice}" type="currency" pattern="￥0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.oil.agrtPrice}" type="currency" pattern="￥0.00"/>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.discountPercent}" type="percent"/>
					</td>
					<td style="text-align: center;">
						<div class="shuliang">
							<a onclick="setAmount.reduce('#buy-num_${scoSerFinal.id}')" href="javascript:;" class="icon-minus"></a>
							<input id="buy-num_${scoSerFinal.id}" sfId="${scoSerFinal.id}" name="num" onblur="setAmount.modify('#buy-num_${scoSerFinal.id}');" value="${scoSerFinal.numbers}" maxlength="4" style="width: 60px;text-align: center;" />
							<a onclick="setAmount.add('#buy-num_${scoSerFinal.id}')" href="javascript:;" class="icon-plus"></a>
						</div>
					</td>
					<td>
						<fmt:formatNumber value="${scoSerFinal.totalPrice}" type="currency" pattern="￥0.00"/>
					</td>

					<shiro:hasPermission name="sco:scoFinalReport:edit"><td>
						<a href="${ctx}/sco/scoSerFinal/delete?id=${scoSerFinal.id}&type=${scoFinalReport.type}" onclick="return confirmx('确认要删除该服务类型吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>

	<shiro:hasPermission name="sco:scoFinalReport:edit">
		<div style="padding-top: 20px;padding-left: 10px;">
			合计总金额: <span id="totalAmt"><fmt:formatNumber value="${totalAmt}" type="currency" pattern="￥0.00"/></span>元 &nbsp;&nbsp;
			<a href="${ctx}/sco/scoFinalReport/addSerFinish?id=${scoFinalReport.id}" onclick="return checkTotalAmt('${totalAmt}', this.href)">录入完毕</a>&nbsp;&nbsp;
			<a href="${ctx}/sco/scoFinalReport/report/view?id=${scoFinalReport.id}&action=view" target="_blank">打印预览</a>&nbsp;&nbsp;
			<%--<span class="help-inline"><font color="red">*单个验收单总金额不能超过50000元人民币。</font> </span>--%>
		</div>
		<span class="help-inline"><font color="red">
			<c:choose>
				<c:when test="${scoFinalReport.type == 4}">*单个结算单总金额不能超过50000元人民币。</c:when>
				<c:otherwise>*单个结算单总金额不能超过100000元人民币。</c:otherwise>
			</c:choose>
		</font> </span>
	</shiro:hasPermission>
</body>
</html>