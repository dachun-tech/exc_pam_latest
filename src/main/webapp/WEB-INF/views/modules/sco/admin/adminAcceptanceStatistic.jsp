<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>验收单统计</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnExport").click(function () {
// 				top.$.jBox.confirm("确认要导出统计数据吗？","系统提示",function(v,h,f){
// 					if(v=="ok"){
// 						$("#searchForm").attr("action","${ctx}/admin/sco/scoAcceptanceReport/statistic/export");
// 						$("#searchForm").submit();
// 					}
// 				},{buttonsFocus:1});
// 				top.$('.jbox-body .jbox-icon').css('top','55px');
                $("#searchForm").attr("action", "${ctx}/admin/sco/scoAcceptanceReport/statistic/export");
                $("#searchForm").submit();
                $("#searchForm").attr("action", "${ctx}/admin/sco/scoAcceptanceReport/statistic");
            });


// 			if($("#serTreeId").val()!=null&&$("#serTreeId").val()!=""){
// 				serSubTree($("#serTreeId").val());
// 			}
        });

        function page(n, s) {
            $("#searchForm").attr("action", "${ctx}/admin/sco/scoAcceptanceReport/statistic");
            $("#searchForm").submit();
            return false;
        }

        function secMenuTree(goodsTreeId) {
            var url = "${ctx}/sco/scoGoods/secMenuTree";
            //重置select2控件的值
            var subIdSelect = $("#subId");
            $("#subId option:not(:first)").remove();
            subIdSelect.select2("val", "");

            $.ajax({
                url: url,
                dataType: 'json',
                data: {
                    goodsTreeId: goodsTreeId
                },
                cache: false,
                async: true,
                success: function (data, textStatus) {
                    var secTreeList = data;
                    for (var node in secTreeList) {
                        var option = "<option value='" + secTreeList[node].id + "'>" + secTreeList[node].name + "</option>";
                        subIdSelect.append(option);
                    }
                }
            });
        }


        function serSubTree(treeId) {
            var url = "${ctx}/sco/scoSerTree/serSubTree";
            //重置select2控件的值
            var subIdSelect = $("#subId");
            $("#subId option:not(:first)").remove();
            subIdSelect.select2("val", "");

            $.ajax({
                url: url,
                dataType: 'json',
                data: {
                    id: treeId
                },
                cache: false,
                async: true,
                success: function (data, textStatus) {
                    var secTreeList = data;
                    for (var node in secTreeList) {
                        var option = "<option value='" + secTreeList[node].id + "'>" + secTreeList[node].name + "</option>";
                        subIdSelect.append(option);
                    }
                }
            });
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/admin/sco/scoAcceptanceReport/statistic">验收单统计</a></li>
</ul>
<input type="hidden" value="${serTreeId }" id="serTreeId">

<form:form id="searchForm" modelAttribute="adminScoAcceptanceStatistic"
           action="${ctx}/admin/sco/scoAcceptanceReport/statistic" method="post" class="breadcrumb form-search">
    <form:hidden path="type"/>
    <ul class="ul-form">
        <li><label>统计时间：</label>
                <%--class="input-medium"  class="input-xlarge"--%>
            <input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20"
                   class="input-mini Wdate"
                   value="${beginDate}"
                   onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',isShowClear:false});"/> ~
            <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
                   value="${endDate}"
                   onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                <%--<form:select path="year" class="input-small">--%>
                <%--<form:options items="${yearList}" itemLabel="year" itemValue="year" htmlEscape="false"/>--%>
                <%--</form:select>--%>
        </li>
        <%--<li>--%>
            <%--<form:select path="month" class="input-small">--%>
                <%--<form:option value="" label=""/>--%>
                <%--<form:options items="${monthList}" htmlEscape="false"/>--%>
            <%--</form:select>--%>
        <%--</li>--%>
        <li><label>供应商：</label>
            <form:select path="createBy.id" class="input-xlarge">
                <form:option value="" label="全部"/>
                <form:options items="${userList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>采购单位：</label>
            <form:select path="report.department" class="input-xlarge">
                <form:option value="" label="--请选择--"/>
                <form:options items="${departmentList}" itemLabel="department" itemValue="department"
                              htmlEscape="false"/>
            </form:select>
                <%--<form:input path="report.department" htmlEscape="false" maxlength="64" class="input-small"/>--%>
        </li>
        <li><label>商品名称：</label>
            <form:select path="goodsName" class="input-xlarge">
                <form:option value="" label="--请选择--"/>
                <form:options items="${goodsNameList}" itemLabel="name" itemValue="name" htmlEscape="false"/>
            </form:select>
                <%--<form:input path="goodsName" htmlEscape="false" maxlength="64" class="input-small"/>--%>
        </li>
        <li class="clearfix"></li>


        <c:choose>
            <c:when test="${param.type == 6}">
                <li><label>目录：</label>
                    <form:select path="goodsTreeId" class="input-xlarge required" onchange="serSubTree(this.value);">
                        <form:option value="" label="请选择"/>
                        <form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                    </form:select>
                    &nbsp;&nbsp;
                </li>
                <li>
                    <form:select path="subId" class="input-xlarge required">
                        <form:option value="" label="请选择"/>
                        <form:options items="${serSubTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </li>
            </c:when>
            <c:when test="${param.type == 5}">
                <li><label>目录：</label>
                </li>
                <li>
                    <form:select path="subId" class="input-xlarge required">
                        <form:option value="" label="请选择"/>
                        <form:options items="${serTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </li>
            </c:when>
            <c:otherwise>
                <li><label>目录：</label>
                    <form:select path="goodsTreeId" class="input-small" onchange="secMenuTree(this.value);">
                        <form:option value="" label="全部商品"/>
                        <form:options items="${goodsTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                    </form:select>
                </li>
                <li>
                    <form:select path="subId" class="input-small">
                        <form:option value="" label="--请选择--"/>
                        <form:options items="${goodsSecTreeList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                    </form:select>
                </li>
            </c:otherwise>
        </c:choose>
            <%--<li><label>单位编号：</label>--%>
            <%--<form:input path="report.department" htmlEscape="false" maxlength="64" class="input-large"/>--%>
            <%--</li>--%>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
            <shiro:hasPermission name="sco:adminAcceptanceReport:edit">
                <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
            </shiro:hasPermission>
        </li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>供应商</th>
        <th>统计时间</th>
        <th>验收单数量</th>
        <th>商品总数量</th>
        <th>采购总金额</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${list}" var="statistic" varStatus="status">
        <tr>
            <td style="text-align: center;" rowspan="${fn:length(statistic.statisticList) } ">
                    ${statistic.createBy.name}<br><br>
                ( 销售商品总数量: ${statistic.totalGoods}, 总金额: <fmt:formatNumber value="${statistic.totalAmt}" type="currency"
                                                                           pattern="￥0.00"/> )
                <br>
            </td>
        <c:forEach items="${statistic.statisticList}" var="suppliers" varStatus="status">
                <%--<td align="center">${suppliers.year}.${suppliers.month}.1 ~--%>
                    <%--<%--%>
                        <%--String date = (String) "${suppliers.year}" +"."+ "${suppliers.month}"+".1";--%>
                    <%--%>--%>
                <%--</td>--%>
                <td align="center">${(beginDate == null || beginDate == "")? suppliers.year : beginDate} ~ ${(endDate == null || endDate =="")? suppliers.month:endDate}</td>
                <td align="center">${suppliers.receivingNumbers}</td>
                <td align="center">${suppliers.goodsNumbers}</td>
                <td align="center"><fmt:formatNumber value="${suppliers.subtotal}" type="currency"
                                                     pattern="￥0.00"/></td>
        </c:forEach>

        </tr>

    </c:forEach>
    <tr>
        <td style="text-align: center;">总计</td>
        <td align="center">&nbsp;</td>
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