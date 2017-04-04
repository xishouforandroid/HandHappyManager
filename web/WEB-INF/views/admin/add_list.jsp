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
            <li><a href="javascript:void(0)">管理员</a></li>
            <li><a href="javascript:void(0)">添加管理员</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box ui-draggable ui-droppable">
            <div class="box-header">
                <div class="box-name ui-draggable-handle">
                    <i class="fa fa-table"></i>
                    <span>添加管理员</span>
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
                        <div class="col-sm-4">
                            <input type="text" id="keywords" placeholder="用户名 手机号" value="${query.keyword}"
                                   id="keywords" class="form-control" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="ischeck">
                            <option value="">--选择审核状态--</option>
                            <option value="0" ${query.ischeck=='0'?'selected':''}>未审核</option>
                            <option value="1" ${query.ischeck=='1'?'selected':''}>已审核</option>
                            <option value="2" ${query.ischeck=='2'?'selected':''}>未通过</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="mm_emp_type">
                            <option value="">--选择注册类型--</option>
                            <option value="0" ${query.mm_emp_type=='0'?'selected':''}>苗木经营</option>
                            <option value="1" ${query.mm_emp_type=='1'?'selected':''}>苗木会员</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="mm_emp_company_type">
                            <option value="">--选择公司类型--</option>
                            <option value="0" ${query.mm_emp_company_type=='0'?'selected':''}>苗木</option>
                            <option value="1" ${query.mm_emp_company_type=='1'?'selected':''}>园林</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <select class="form-control" id="mm_level_id">
                            <option value="">--选择VIP星级--</option>
                            <c:forEach items="${listLevels}" var="e" varStatus="st">
                                <option value="${e.mm_level_id}" ${query.mm_level_id == e.mm_level_id?'selected':''}>${e.mm_level_name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" onclick="searchOrder('1')"
                            class="btn form-control btn-warning btn-sm btn-block">查找
                    </button>
                </form>
                <%--<p>For basic styling add the base class <code>.table</code> to any <code>&lt;table&gt;</code>.</p>--%>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th>电话</th>
                        <th>用户类型</th>
                        <th>公司名称</th>
                        <th>公司类型</th>
                        <th>所属地区</th>
                        <th>诚信</th>
                        <th>协会</th>
                        <th>星级</th>
                        <th>到期时间</th>
                        <th>审核状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td>${e.mm_emp_nickname}</td>
                            <td>${e.mm_emp_mobile}</td>
                            <td>${e.mm_emp_type}</td>
                            <td>${e.mm_emp_company}</td>
                            <td>
                                <c:if test="${e.mm_emp_company_type=='0'}">苗木</c:if>
                                <c:if test="${e.mm_emp_company_type=='1'}">园林</c:if>
                            </td>
                            <td>${e.provinceName}${e.cityName}${e.areaName}</td>
                            <td>
                                <c:if test="${e.is_chengxin=='0'}">否</c:if>
                                <c:if test="${e.is_chengxin=='1'}">是</c:if>
                            </td>
                            <td>
                                <c:if test="${e.is_miaomu=='0'}">否</c:if>
                                <c:if test="${e.is_miaomu=='1'}">是</c:if>
                            </td>
                            <td>${e.levelName}</td>
                            <td>${e.mm_emp_endtime}</td>
                            <td>
                                <c:if test="${e.ischeck=='0'}">未审核</c:if>
                                <c:if test="${e.ischeck=='1'}">已审核</c:if>
                                <c:if test="${e.ischeck=='2'}">未通过</c:if>
                            </td>
                            <td>
                                <a class="btn btn-default btn-sm"
                                   href="#module=/emp/listAddManager/detail&mm_emp_id=${e.mm_emp_id}"
                                   role="button">添加</a>
                            </td>
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
        var mm_emp_type = $("#mm_emp_type").val();
        var mm_emp_company_type = $("#mm_emp_company_type").val();
        var mm_level_id = $("#mm_level_id").val();
        var ischeck = $("#ischeck").val();
        var keywords = $("#keywords").val();
        if (_index <= ${page.pageCount} && _index >= 1) {
            window.location.href = "#module=/emp/listAddManager&page=" + page + "&size=" + size + "&mm_emp_type=" + mm_emp_type + "&keyword=" + keywords
            + "&mm_emp_company_type=" + mm_emp_company_type
            + "&mm_level_id=" + mm_level_id
            + "&ischeck=" + ischeck + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }
    function nextPage(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var mm_emp_type = $("#mm_emp_type").val();
        var mm_emp_company_type = $("#mm_emp_company_type").val();
        var mm_level_id = $("#mm_level_id").val();
        var ischeck = $("#ischeck").val();
        var keywords = $("#keywords").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/emp/listAddManager&page=" + page + "&size=" + size + "&keyword=" + keywords
            + "&mm_emp_type=" + mm_emp_type
            + "&mm_emp_company_type=" + mm_emp_company_type
            + "&mm_level_id=" + mm_level_id
            + "&ischeck=" + ischeck + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

    function searchOrder(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var mm_emp_type = $("#mm_emp_type").val();
        var mm_emp_company_type = $("#mm_emp_company_type").val();
        var mm_level_id = $("#mm_level_id").val();
        var ischeck = $("#ischeck").val();
        var keywords = $("#keywords").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/emp/listAddManager&page=" + page + "&size=" + size + "&keyword=" + keywords
            + "&mm_emp_type=" + mm_emp_type
            + "&mm_emp_company_type=" + mm_emp_company_type
            + "&mm_level_id=" + mm_level_id
            + "&ischeck=" + ischeck + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }
</script>


