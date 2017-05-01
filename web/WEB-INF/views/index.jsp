<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="zh_CH">
<head>
    <meta charset="utf-8">
    <title>幸福牵手吧后台管理系统</title>
    <meta name="description" content="description">
    <meta name="author" content="DevOOPS">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/plugins/bootstrap/bootstrap.css" rel="stylesheet">
    <link href="/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">
    <link href="/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
    <link href="/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
    <link href="/plugins/xcharts/xcharts.min.css" rel="stylesheet">
    <link href="/plugins/select2/select2.css" rel="stylesheet">
    <link href="/plugins/justified-gallery/justifiedGallery.css" rel="stylesheet">
    <link href="/css/style_v2.css" rel="stylesheet">
    <link href="/plugins/chartist/chartist.min.css" rel="stylesheet">
    <link href="/css/cat.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="../../img/favicon.ico"/>
    <%--icon css 20170405--%>
    <link rel="stylesheet" href="/plugins/icon/css/style.css"/>

    <%--<script src="http://getbootstrap.com/docs-assets/js/html5shiv.js"></script>--%>
    <%--<script src="http://getbootstrap.com/docs-assets/js/respond.min.js"></script>--%>

    <%--<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main.css?v=1.0"/>--%>
    <%--<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=98ec561802a0063ec6d8301ae2321505"></script>--%>

</head>
<body>
<!--Start Header-->
<div id="screensaver">
    <canvas id="canvas"></canvas>
    <i class="fa fa-lock" id="screen_unlock"></i>
</div>
<div id="modalbox">
    <div class="devoops-modal">
        <div class="devoops-modal-header">
            <div class="modal-header-name">
                <span>Basic table</span>
            </div>
            <div class="box-icons">
                <a class="close-link">
                    <i class="fa fa-times"></i>
                </a>
            </div>
        </div>
        <div class="devoops-modal-inner">
        </div>
        <div class="devoops-modal-bottom">
        </div>
    </div>
</div>
<header class="navbar">
    <div class="container-fluid expanded-panel">
        <div class="row">
            <div id="logo" class="col-xs-8 col-sm-5"><a href="javascript:void(0);">幸福牵手吧后台管理系统</a></div>
            <div id="top-panel" class="col-xs-4 col-sm-7">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 top-panel-right">
                        <ul class="nav navbar-nav pull-right panel-menu">
                            <li class="dropdown">
                                <a href="javascript:void(0);" class="dropdown-toggle account" data-toggle="dropdown">
                                    <div class="avatar">
                                        <img src="${admin.manager_cover}" class="img-circle" alt="avatar"/>
                                    </div>
                                    <i class="fa fa-angle-down pull-right"></i>

                                    <div class="user-mini pull-right">
                                        <span class="welcome">欢迎</span>
                                        <span>${admin.manager_admin}</span>
                                    </div>
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="/logout.do">
                                            <i class="fa fa-power-off"></i>
                                            <span>退出</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<!--End Header-->
<!--Start Container-->
<div id="main" class="container-fluid">
    <div class="row">
        <div id="sidebar-left" class="col-xs-4 col-sm-2">
            <ul class="nav main-menu">
                <li>
                    <a href="javascript:void(0);" class="active" onclick="toPage('mainPage','')">
                        <i class="fa fa-home"></i>
                        <span>主页</span>
                    </a>
                </li>

                <c:if test="${um:permission('LIST_ROLE', sessionScope.powers)||um:permission('ADD_ROLE', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-user-md"></i>
                            <span>角色管理</span>
                        </a>
                        <ul class="dropdown-menu">
                                <c:if test="${um:permission('ADD_ROLE', sessionScope.powers)}">
                                    <li><a href="javascript:void(0);" onclick="toPage('/role/add','')">添加角色</a></li>
                                </c:if>
                                <c:if test="${um:permission('LIST_ROLE', sessionScope.powers)}">
                                    <li><a href="javascript:void(0);" onclick="toPage('/role/list','')">角色列表</a></li>
                                </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('MANAGER_LIST_MANAGE', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-user"></i>
                            <span>管理员</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('MANAGER_LIST_MANAGE', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/admin/list','1')">管理员列表</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('LIST_EMPLOYEE_JY', sessionScope.powers)||um:permission('LIST_EMPLOYEE_EMP', sessionScope.powers)||um:permission('LIST_EMPLOYEE_CHENGXIN', sessionScope.powers)||um:permission('LIST_EMPLOYEE_RENZHENG', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-group"></i>
                            <span>会员管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('LIST_EMPLOYEE_EMP', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/emp/list','1')">会员管理</a></li>
                            </c:if>
                            <c:if test="${um:permission('LIST_EMPLOYEE_JY', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/emp/listku','1')">会员库管理</a></li>
                            </c:if>

                            <c:if test="${um:permission('LIST_EMPLOYEE_RENZHENG', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/emp/listhy','1')">认证会员</a></li>
                            </c:if>

                            <c:if test="${um:permission('LIST_EMPLOYEE_CHENGXIN', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/emp/listcx','1')">诚信会员</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('PHOTO_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-user"></i>
                            <span>相册管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('PHOTO_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/photos/list','1')">相册列表</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('TEL_KEFU_MANAGE', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-phone"></i>
                            <span>客服电话</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('TEL_KEFU_MANAGE', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/kefu/list','1')">客服电话</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('ADD_LIKES', sessionScope.powers)||um:permission('LIKES_LIST', sessionScope.powers) || um:permission('GROUPS_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-bullhorn"></i>
                            <span>爱好管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('ADD_LIKES', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('likes/toAdd','')">添加爱好</a>
                                </li>
                            </c:if>
                            <c:if test="${um:permission('LIKES_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('likes/list','1')">爱好列表</a>
                                </li>
                            </c:if>
                            <c:if test="${um:permission('GROUPS_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('groups/list','1')">群组列表</a>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('ORDER_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-shopping-cart"></i>
                            <span>订单管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('ORDER_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('orders/list','1')">订单列表</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('SUGGEST_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-envelope"></i>
                            <span>意见反馈</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('SUGGEST_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/suggest/list','1')">意见反馈</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('REPORT_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-envelope"></i>
                            <span>投诉中心</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('REPORT_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/report/list','1')">投诉中心</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('ADD_NOTICE', sessionScope.powers)||um:permission('NOTICE_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-bullhorn"></i>
                            <span>公告管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('ADD_NOTICE', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('noticeController/toAdd','')">添加公告</a>
                                </li>
                            </c:if>
                            <c:if test="${um:permission('NOTICE_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('noticeController/list','1')">公告列表</a>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('ADD_NEWS', sessionScope.powers)||um:permission('NEWS_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-bullhorn"></i>
                            <span>资讯管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('ADD_NEWS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('newsController/toAdd','')">添加资讯</a>
                                </li>
                            </c:if>
                            <c:if test="${um:permission('NEWS_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('newsController/list','1')">资讯列表</a>
                                </li>
                            </c:if>

                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('ABOUT_US_MANA', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-info"></i>
                            <span>关于我们</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('ABOUT_US_MANA', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('aboutUs/add','')">关于我们</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('UPDATE_VERSION_CODE_MAANGER_AD_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">版本管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('UPDATE_VERSION_CODE_MAANGER_AD_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/versionCodeController/toEdit','')">版本管理</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
        <!--Start Content-->
        <div id="content" class="col-xs-12 col-sm-10">
            <div id="about">
                <div class="about-inner">
                    <h4 class="page-header">Open-source admin theme for you</h4>

                    <p>DevOOPS team</p>

                    <p>Homepage - <a href="http://devoops.me" target="_blank">http://devoops.me</a></p>

                    <p>Email - <a href="mailto:devoopsme@gmail.com">devoopsme@gmail.com</a></p>

                    <p>Twitter - <a href="http://twitter.com/devoopsme" target="_blank">http://twitter.com/devoopsme</a>
                    </p>

                    <p>Donate - BTC 123Ci1ZFK5V7gyLsyVU36yPNWSB5TDqKn3</p>
                </div>
            </div>
            <div class="preloader">
                <img src="/img/devoops_getdata.gif" style="width: 100%" alt="preloader"/>
            </div>
            <div id="ajax-content"></div>
        </div>
        <!--End Content-->
    </div>
</div>

<script src="/plugins/jquery/jquery.min.js"></script>
<script src="/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="/plugins/bootstrap/bootstrap.min.js"></script>
<script src="/plugins/justified-gallery/jquery.justifiedGallery.min.js"></script>
<script src="/plugins/tinymce/tinymce.min.js"></script>
<script src="/plugins/tinymce/jquery.tinymce.min.js"></script>
<script src="/js/devoops.js"></script>
<script src="/js/china2.js"></script>
<script type="text/javascript" src="/js/md5.js"></script>
<script type="text/javascript" src="/js/cookie.js"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="/js/Util.js"></script>

</body>
<script type="text/javascript">
    (function (window, undefined) {
        var currentHash;

        function decodeChineseWords(params) {
            if (params["cn"] == undefined || params["cn"] == "") {
                return;
            }
            var cns = params["cn"].split(","), i;
            for (i = 0; i < cns.length; i++) {
                params[cns[i]] = decodeURIComponent(params[cns[i]]);
            }
        }

        function checkHash() {
            var newHash = window.location.hash;
            if (newHash == "") {
//                window.location.hash = "#module=main";
                return;
            }
            if (newHash == currentHash) return;
            currentHash = newHash;
            var paramsString = currentHash.substring(1);
            var paramsArray = paramsString.split("&");
            var params = {};
            for (var i = 0; i < paramsArray.length; i++) {
                var tempArray = paramsArray[i].split("=");
                params[tempArray[0]] = tempArray[1];
            }
            var _url = params["module"].replace(/\./g, "/") + ".do?_t=" + new Date().getTime();
            delete params["module"];
            decodeChineseWords(params);
            $.ajax({
                url: _url, type: "post", data: params, success: function (data) {
                    var editor;
                    while ((editor = Util.editors.shift()) != undefined) {
                        editor.destroy();
                    }
                    $("#content").html(data);
                }
            });
        }

        window.setInterval(checkHash, 100);
    })(window);

    function toPage(_url, _page) {
        if (_page != '') {
            window.location.href = "#module=" + _url + "&page=" + _page + "&_t=" + new Date().getTime();
        } else {
            window.location.href = "#module=" + _url + "&_t=" + new Date().getTime();
        }
    }

</script>
</html>
