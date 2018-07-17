<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>公告信息</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<c:if test="${fn:contains(scoNotice.currentUser.roleIds, fns:getDictValue('超级管理员', 'sys_role', '1')) || fn:contains(scoNotice.currentUser.roleIds, fns:getDictValue('管理员', 'sys_role', '2'))}">
    <form:form id="searchForm" modelAttribute="scoNotice" action="${ctx}/sco/scoNotice/view" method="post"
               class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
            <li><label style="width: auto;">所属业务分类：</label>
                <form:select path="roleId" class="input-medium">
                    <form:option value="" label="全部"/>
                    <form:options items="${fns:getDictList('sys_supplier')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="clearfix"></li>
        </ul>
    </form:form>
</c:if>
<sys:message content="${message}"/>
<div>

    <c:choose>
        <c:when test="${fn:length(list)==0}">

            <table border="0" cellspacing="0" cellpadding="0"
                   style="width:100%; border-bottom:1px dashed #9a9a9a; float:left; padding-bottom:15px; margin-top:15px;">
                <tr>
                    <td style="width:48px; vertical-align:top;padding-left: 20px;">
                        <div style="align:center;line-height: 30px;">暂无公告</div>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <c:forEach items="${list}" var="scoNotice">
                <table border="0" cellspacing="0" cellpadding="0"
                       style="width:100%; border-bottom:1px dashed #9a9a9a; float:left; padding-bottom:15px; margin-top:15px;">
                    <tr>
                        <td style="width:48px; vertical-align:top;padding-left: 20px;">
                            <img src="${ctxStatic}/sco/image/gong_02.png" width="31" height="31">
                        </td>
                        <td style="vertical-align:top;padding-left: 10px;">
                            <div style="width:100%; font-size:18px; color:#333; line-height:22px;font-weight: bold;">
                                    ${scoNotice.title}
                            </div>
                            <div style="font-size:12px; color:#5d5d5d; line-height:22px; margin-top:6px; padding-right:10px;">
                                    ${scoNotice.content}
                            </div>
                        </td>
                        <td style="width:280px; vertical-align:top;">
                            <div style="width:280px; height:52px; line-height:26px; float:left;">
                                <li style="width:240px; float:left; height:26px; line-height:26px; padding-left:20px;">
                                    <span class="icon-time"> 发布时间： </span>
                                    <fmt:formatDate value="${scoNotice.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </li>
                                <li style="width:240px; float:left; height:26px; line-height:26px; padding-left:20px;">
                                    <span class="icon-user"> 发布人： </span>
                                        ${scoNotice.createByName.name}
                                </li>
                            </div>
                        </td>
                    </tr>
                </table>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<div style="width: 100%;position: relative;text-align: center;overflow: auto;padding:10px;">
    <p style="width:100%;text-align: center;font-size:14pt;"><a
            href="http://www.vegnet.com.cn/market/233.html">查看整个价目表</a></p>
    <%--<p style="width: 728px;position: relative;height:28px;line-height:28px;background:url(${ctxStatic}/sco/image/Price_gre.jpg); background-size:100% 100%!important;display: inline-block;border:1px solid lightgray;margin:0px;padding:0px;">--%>
    <%--<b style="width: 12%;display: inline-block; ">日期</b>--%>
    <%--<b style="width: 10%;display: inline-block;">品种</b>--%>
    <%--<b style="width: 30%;display: inline-block;">批发市场</b>--%>
    <%--<b style="width: 10%;display: inline-block;">最低价格</b>--%>
    <%--<b style="width: 11%;display: inline-block;">最高价格</b>--%>
    <%--<b style="width: 10%;display: inline-block;">平均价格</b>--%>
    <%--<b style="width: 10%;display: inline-block;">计量单位</b>--%>
    <%--</p><br>--%>
    <%--<div class="price-area"--%>
         <%--style="width:730px;height:255px;float: none;display:inline-block;border:none;margin: 0;padding:0;"></div>--%>
    <div style="width:1000px;height:420px;float: none;display:inline-block;border:none;margin: 0;padding:0;">
    <%--${priceData}--%>
    <iframe style="width:1000px;height:420px;float: none;display:inline-block;border:none;margin: 0;padding:0;" id="priceFrame" ></iframe>
    </div>
    <%--<iframe src="http://www.vegnet.com.cn/Price/IFrame"--%>
            <%--style="width:730px;height:255px;float: none;display:inline-block;border:none;margin: 0;padding:0;"></iframe>--%>
    <%--<iframe src="http://www.vegnet.com.cn/market/233.html?page=1"--%>
    <%--style="width:730px;height:255px;float: none;display:inline-block;border:none;margin: 0;padding:0;"></iframe>--%>
</div>
<style type="text/css">
    ul, li {
        list-style: none;
    }
</style>
<script>
    $(function () {
        var url = "http://www.vegnet.com.cn/Price/List?marketID=233";

        var nowTime = new Date();
        var nowYear = nowTime.getFullYear();
        var nowMonth = nowTime.getMonth() + 1;
        var nowDate = nowTime.getDate();
        if (nowTime.getDate() < 3) {
            if (nowMonth == 1) {
                nowMonth = 12;
                nowYear--;
            } else nowMonth--;
        }
        url += '&year=' + nowYear + '&month=' + nowMonth + '&day=0';
        // document.all.priceFrame.contentWindow.location = url;
        $('#priceFrame').attr('src', url);

    });
</script>
</body>
</html>