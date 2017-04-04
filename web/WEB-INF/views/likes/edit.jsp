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
            <li><a href="javascript:void(0)">爱好管理</a></li>
            <li><a href="javascript:void(0)">修改爱好</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>修改爱好</span>
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
                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">爱好标题</label>
                        <input type="hidden" id="likeid" name="likeid" value="${like.likeid}">

                        <div class="col-sm-4">
                            <input type="text" id="likename" class="form-control" placeholder="公告标题" value="${like.likename}"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">排序数字</label>

                        <div class="col-sm-4">
                            <input type="text" id="topnum" class="form-control" placeholder="越大越靠前" value="${like.topnum}"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否可用</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="is_use">
                                <option value="0" ${like.is_use=='0'?'selected':''}>否</option>
                                <option value="1" ${like.is_use=='1'?'selected':''}>是</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveP()">确定</button>
                            <button type="button" class="btn btn-primary" onclick="javascript :history.back(-1)">返回
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function saveP() {
        var likeid = $("#likeid").val();
        var is_use = $("#is_use").val();
        var likename = $("#likename").val();
        var topnum = $("#topnum").val();

        if (likename.replace(/\s/g, '') == '') {
            alert("请输入标题！");
            return;
        }

        if (is_use.replace(/\s/g, '') == '') {
            alert("请选择是否可用！");
            return;
        }

        var regInt = /^([0-9]\d*)$/;
        if (topnum.replace(/\s/g, '') == '') {
            alert("排序不能为空");
            return;
        } else {
            if (!regInt.test(topnum)) {
                alert("排序必须是整数！");
                return;
            }
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/likes/edit.do",
            data: {"likename": likename, "topnum": topnum, "is_use": is_use, "likeid": likeid},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("执行成功");
                    window.location.href = "#module=likes/list"+ "&_t=" + new Date().getTime();
                } else {
                    alert("执行失败，请检查")
                }
            }
        });
    }

</script>