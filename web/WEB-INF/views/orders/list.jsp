<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="javascript:void(0)" onclick="toPage('mainPage','')">主页</a></li>
            <li><a href="javascript:void(0)">订单管理</a></li>
            <li><a href="javascript:void(0)">订单列表</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box ui-draggable ui-droppable">
            <div class="box-header">
                <div class="box-name ui-draggable-handle">
                    <i class="fa fa-table"></i>
                    <span>订单列表</span>
                </div>
                <div class="box-icons">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="expand-link">
                        <i class="fa fa-expand"></i>
                    </a>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
                    </a>
                </div>
                <div class="no-move"></div>
            </div>
            <div class="box-content">
                <form class="form-inline">
                    <div class="form-group">
                        <select class="form-control" id="status">
                            <option value="">--选择订单状态--</option>
                            <option value="0" ${query.status=='0'?'selected':''}>未支付</option>
                            <option value="1" ${query.status=='1'?'selected':''}>已支付</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="trade_type">
                            <option value="">--选择支付方式--</option>
                            <option value="0" ${query.trade_type=='0'?'selected':''}>支付宝</option>
                            <option value="1" ${query.trade_type=='1'?'selected':''}>微信</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="is_dxk_order">
                            <option value="">--选择费用类型--</option>
                            <option value="0" ${query.is_dxk_order=='0'?'selected':''}>认证服务费</option>
                            <option value="1" ${query.is_dxk_order=='1'?'selected':''}>诚信保证金</option>
                        </select>
                    </div>
                    <button type="submit" onclick="searchOrder('1')"
                            class="btn form-control btn-warning btn-sm btn-block">查找
                    </button>
                </form>

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>订单号</th>
                        <th>金额</th>
                        <th>会员姓名</th>
                        <th>手机号</th>
                        <th>订单状态</th>
                        <th>支付方式</th>
                        <th>费用类型</th>
                        <th>费用说明</th>
                        <th>订单生成时间</th>

                        <%--<th>操作</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td>${e.order_no}</td>
                            <td>${e.payable_amount}</td>
                            <td>${e.nickname}</td>
                            <td>${e.mobile}</td>
                            <td>
                                <c:if test="${e.status=='0'}">未支付</c:if>
                                <c:if test="${e.status=='1'}">已支付</c:if>
                            </td>
                            <td>
                                <c:if test="${e.trade_type=='0'}">支付宝</c:if>
                                <c:if test="${e.trade_type=='1'}">微信</c:if>
                            </td>
                            <td>
                                <c:if test="${e.is_dxk_order=='0'}">认证服务费</c:if>
                                <c:if test="${e.is_dxk_order=='1'}">诚信保证金</c:if>
                            </td>
                            <td>${e.order_cont}</td>
                            <td>${um:format(e.create_time, 'yyyy-MM-dd HH:mm:ss')}</td>
                            <%--<td>--%>
                                <%--<a class="btn btn-default btn-sm" href="javascript:void (0)"--%>
                                   <%--onclick="editRole('${e.likeid}')" role="button">详情</a>--%>
                                <%--<a class="btn btn-default btn-sm" href="javascript:void (0)"--%>
                                   <%--onclick="addGropups('${e.likeid}','${e.likename}')" role="button">群组</a>--%>
                            <%--</td>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div style="margin-top: 20px;border-top: 1px solid #dedede;padding-bottom:15px; height: 50px">
                    <span style="line-height:28px;margin-top:25px;padding-left:10px; float: left">共${page.count}条/${page.pageCount}页</span>
                    <ul class="pagination" style="padding-left:100px; float: right">
                        <li>
                            <a style="margin-right:20px">每页显示&nbsp;<select name="size" id="size"
                                                                           onchange="nextPage('1')">
                                <option value="10" ${query.size==10?'selected':''}>10</option>
                                <option value="20" ${query.size==20?'selected':''}>20</option>
                                <option value="30" ${query.size==30?'selected':''}>30</option>
                                <option value="100" ${query.size==100?'selected':''}>100</option>
                            </select>&nbsp;条</a>
                        </li>
                        <c:choose>
                            <c:when test="${page.page == 1}">
                                <li><a href="javascript:void(0)">首页</a></li>
                                <li><a href="javascript:void(0)"><span class="left">《</span></a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0);" onclick="nextPage('1')">首页</a></li>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.page-1}')"><span
                                        class="left">《</span></a></li>
                            </c:otherwise>
                        </c:choose>
                        <li><a style="height: 30px; width: 100px">第<input style="width: 40px;height:20px;" type="text"
                                                                          id="index" name="index"
                                                                          onkeyup="searchIndex(event)"
                                                                          value="${page.page}"/> 页</a></li>

                        <c:choose>
                            <c:when test="${page.page == page.pageCount}">
                                <li><a href="javascript:void(0)"><span class="right">》</span></a></li>
                                <li><a href="javascript:void(0)">末页</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.page+1}')"><span
                                        class="right">》</span></a></li>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.pageCount}')">末页</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>


            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function searchIndex(e) {
        if (e.keyCode != 13) return;
        var _index = $("#index").val();
        var size = getCookie("contract_size");
        var status = $("#status").val();
        var is_dxk_order = $("#is_dxk_order").val();
        var trade_type = $("#trade_type").val();
        if (_index <= ${page.pageCount} && _index >= 1) {
            window.location.href = "#module=/orders/list&page=" + _index + "&size=" + size
            + "&status=" + status
            + "&is_dxk_order=" + is_dxk_order
            + "&trade_type=" + trade_type
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }
    function nextPage(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var status = $("#status").val();
        var is_dxk_order = $("#is_dxk_order").val();
        var trade_type = $("#trade_type").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/orders/list&page=" + page + "&size=" + size
            + "&status=" + status
            + "&is_dxk_order=" + is_dxk_order
            + "&trade_type=" + trade_type
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

    function searchOrder(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var status = $("#status").val();
        var is_dxk_order = $("#is_dxk_order").val();
        var trade_type = $("#trade_type").val();

        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/orders/list&page=" + page
            + "&size=" + size
            + "&status=" + status
            + "&is_dxk_order=" + is_dxk_order
            + "&trade_type=" + trade_type
            + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

</script>


