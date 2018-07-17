<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
				updateGoodsAcceptance($(obj));
			},
			add:function(obj,id){
				var x=this.amount(obj,true);
				$(obj).val(x);
				updateGoodsAcceptance($(obj));

			},
			modify:function(obj,id){
				var x=$(obj).val();
				if (!this.reg(x)){
					$(obj).val(1);
				}else{
					$(obj).val(x);
				}
				updateGoodsAcceptance($(obj));
			}
		};

		function updateGoodsAcceptance(obj){
			var url = "${ctx}/sco/scoGoodsAcceptance/updateGoodsAcceptance";
			$.ajax({
				url: url,
				type:'POST',
				dataType: 'json',
				data:{
					id:obj.attr("gaId"),
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
			if(totalAmt > 100000.00){
				alertx('总金额不能大于100000.00元,请调整后提交!');
				return false;
			}
//			return confirmx('确认提交验收单吗,提交后将不可更改?', href);
			return window.location = href;
		}
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<tr>
			<td>
				采购机构
			</td>
			<td>
				${scoAcceptanceReport.officeName}
			</td>
			<td>
				单位编号
			</td>
			<td>
				${scoAcceptanceReport.department}
			</td>
		</tr>
		<tr>
			<td>
				采购日期
			</td>
			<td>
				<fmt:formatDate value="${scoAcceptanceReport.buyDate}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
				发票编号
			</td>
			<td>
				${scoAcceptanceReport.invoicenum}
			</td>
		</tr>
		<tr>
			<td>
				联系人
			</td>
			<td>
				${scoAcceptanceReport.linkman}
			</td>
			<td>
				手机号码
			</td>
			<td>
				${scoAcceptanceReport.mobile}
			</td>
		</tr>
	</table>
	<p>
		<hr />
	</p>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>单价(元)</th>
				<th>单位</th>
				<th>数量</th>
				<th>总价</th>
				<shiro:hasPermission name="sco:scoGoodsAcceptance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="scoGoodsAcceptance">
			<tr>
				<td>
					${scoGoodsAcceptance.printsId.printName}
				</td>
				<td>
					<fmt:formatNumber value="${scoGoodsAcceptance.printsId.normalPrice}" type="currency" pattern="￥0.00"/>
				</td>
				<td>
					${scoGoodsAcceptance.printsId.units}
				</td>
				<td style="text-align: center;">
					<div class="shuliang">
						<a onclick="setAmount.reduce('#buy-num_${scoGoodsAcceptance.id}')" href="javascript:;" class="icon-minus"></a>
						<input id="buy-num_${scoGoodsAcceptance.id}" gaId="${scoGoodsAcceptance.id}" name="num" onblur="setAmount.modify('#buy-num_${scoGoodsAcceptance.id}');" value="${scoGoodsAcceptance.numbers}" maxlength="20" style="width: 60px;text-align: center;" />
						<a onclick="setAmount.add('#buy-num_${scoGoodsAcceptance.id}')" href="javascript:;" class="icon-plus"></a>
					</div>
					<%--${scoGoodsAcceptance.numbers}--%>
				</td>
				<td>
					<fmt:formatNumber value="${scoGoodsAcceptance.totalPrice}" type="currency" pattern="￥0.00"/>
				</td>
				<shiro:hasPermission name="sco:scoGoodsAcceptance:edit"><td>
					<a href="${ctx}/sco/scoGoodsAcceptance/delete?id=${scoGoodsAcceptance.id}&type=${scoAcceptanceReport.type}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		<tr>
			<td>
				合计
			</td>
			<td colspan="4">
				${totalAmtChinese}
			</td>
			<td>
				<fmt:formatNumber value="${totalAmt}" type="currency" pattern="￥0.00"/>
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				备注
			</td>
			<td colspan="5">
				${scoAcceptanceReport.remarks}
			</td>
		</tr>
		</tbody>
	</table>
	<shiro:hasPermission name="sco:scoGoodsAcceptance:edit">
		<div style="padding-top: 20px;padding-left: 10px;">
		    <c:choose>
		    	<c:when test="${scoAcceptanceReport.type==6 || scoAcceptanceReport.type==5 }">
					<a href="${ctx}/sco/scoPrints/list?arId=${scoGoodsAcceptance.id}&handle=addPrints&type=${scoAcceptanceReport.type}">添加商品到验收单</a>&nbsp;&nbsp;
		    	</c:when>
		    	<c:otherwise>
					<a href="${ctx}/sco/scoGoods/list?arId=${scoGoodsAcceptance.id}&handle=addGoods&type=${scoAcceptanceReport.type}">添加商品到验收单</a>&nbsp;&nbsp;
		    	</c:otherwise>
		    </c:choose>
			<a href="${ctx}/sco/scoAcceptanceReport/addGoodsFinish?id=${scoAcceptanceReport.id}&type=${scoAcceptanceReport.type}" onclick="return checkTotalAmt('${totalAmt}', this.href)">录入完毕</a>&nbsp;&nbsp;
			<a href="${ctx}/sco/scoAcceptanceReport/report/print/view?id=${scoAcceptanceReport.id}&type=${scoAcceptanceReport.type}" target="_blank">打印预览</a>&nbsp;&nbsp;
			<span class="help-inline"><font color="red">*单个验收单总金额不能超过100000元人民币。</font> </span>
		</div>
	</shiro:hasPermission>
	<div class="pagination">${page}</div>
</body>
</html>