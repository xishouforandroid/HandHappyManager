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
            <li><a href="javascript:void (0);">会员管理</a></li>
            <li><a href="javascript:void (0);">编辑会员</a></li>
        </ol>

    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>编辑会员</span>
                </div>
            </div>
            <div class="box-content">
                <form class="form-horizontal" role="form">
                    <input type="hidden" value="${emp.empid}" id="empid">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">姓名</label>

                        <div class="col-sm-4">
                            <input type="text" id="nickname" class="form-control" value="${emp.nickname}"
                                    data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">头像</label>

                        <div class="col-sm-10 col-md-2">
                            <img class="img-thumbnail" name="imagePath" id="imageDiv" style="cursor: pointer"
                                 src="${emp.cover}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage('fileUpload','imageDiv')"
                                   style="float: left;"/><br/><br/>
                            <font color="red">*如果需要修改头像，请右键“图片另存为”，修改之后重新上传！</font>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">身份证</label>

                        <div class="col-sm-10 col-md-2">
                            <img class="img-thumbnail" name="imagePath1" id="imageDiv1" style="cursor: pointer"
                                 src="${emp.cardpic}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload1" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage('fileUpload1','imageDiv1')"
                                   style="float: left;"/><br/><br/>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">手机号</label>

                        <div class="col-sm-4">
                            <input type="text" id="mobile" class="form-control" value="${emp.mobile}" readonly="true"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">个性签名</label>

                        <div class="col-sm-4">
                            <input type="text" id="sign" class="form-control" value="${emp.sign}"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">年龄</label>

                        <div class="col-sm-4">
                            <input type="text" id="age" class="form-control" value="${emp.age}"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">身高</label>

                        <div class="col-sm-4">
                            <input type="text" id="heightl" class="form-control" value="${emp.heightl}"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">学历</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="education">
                                <option value="1" ${emp.education=='1'?'selected':''}>高中及以下</option>
                                <option value="2" ${emp.education=='2'?'selected':''}>中专</option>
                                <option value="3" ${emp.education=='3'?'selected':''}>专科</option>
                                <option value="4" ${emp.education=='4'?'selected':''}>本科</option>
                                <option value="5" ${emp.education=='5'?'selected':''}>研究生及以上</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">婚姻状况</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="marriage">
                                <option value="1" ${emp.marriage=='1'?'selected':''}>未婚</option>
                                <option value="2" ${emp.marriage=='2'?'selected':''}>离异</option>
                                <option value="3" ${emp.marriage=='3'?'selected':''}>丧偶</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">工作单位</label>

                        <div class="col-sm-4">
                            <input type="text" id="company" class="form-control" value="${emp.company}"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-2 control-label">所在地-省</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="provinceid" onchange="selectCitys()">
                                <option value="">--选择省份--</option>
                                <c:forEach items="${listProvinces}" var="e" varStatus="st">
                                    <option value="${e.provinceid}"  ${emp.provinceid==e.provinceid?'selected':''}>${e.pname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所在地-城市</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="cityid">
                                <option value="${emp.cityid}" >${emp.cityName}</option>
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveEmp()">确定
                            </button>
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

    function saveRole(mm_emp_id) {
        var empid = $("#empid").val();
        var sign = $("#sign").val();
        var age = $("#age").val();
        var heightl = $("#heightl").val();
        var education = $("#education").val();
        var marriage = $("#marriage").val();
        var company = $("#company").val();

        if (nickname.replace(/\s/g, '') == '') {
            alert("姓名不能为空");
            return;
        }

        if (sign.replace(/\s/g, '') == '') {
            alert("签名不能为空");
            return;
        }
        if (age.replace(/\s/g, '') == '') {
            alert("年龄不能为空");
            return;
        }
        if (heightl.replace(/\s/g, '') == '') {
            alert("身高不能为空");
            return;
        }
        if (education.replace(/\s/g, '') == '') {
            alert("请选择受教育程度");
            return;
        }
        if (marriage.replace(/\s/g, '') == '') {
            alert("请选择婚姻状况");
            return;
        }
        if (company.replace(/\s/g, '') == '') {
            alert("工作单位不能为空");
            return;
        }

        var imagePath = $("img[name='imagePath']").attr("src");

        if (imagePath == "" || imagePath == null) {
            imagePath = $("#mm_emp_cover").val();
            return;
        }

        var imagePath1 = $("img[name='imagePath1']").attr("src");

        if (imagePath1 == "" || imagePath1 == null) {
            imagePath1 = $("#ad_pic").val();
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/emp/updateEmp.do",
            data: {
                "empid": empid,
                "sign": sign,
                "age": age,
                "cover": imagePath,
                "cardpic": imagePath1,
                "heightl": heightl,
                "education": education,
                "marriage": marriage,
                "company": company
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("修改成功");
                    history.go(-1);
                } else {
                    var _case = {1: "修改失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;


</script>


<script type="text/javascript">
    function uploadImage(_fileUpload, _imageDiv) {
        $.ajaxFileUpload(
                {
                    url: "/uploadUnCompressImage.do?_t=" + new Date().getTime(),            //需要链接到服务器地址
                    secureuri: false,//是否启用安全提交，默认为false
                    fileElementId: _fileUpload,                        //文件选择框的id属性
                    dataType: 'json',                                     //服务器返回的格式，可以是json, xml
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                        if (data.success) {
                            document.getElementById(_imageDiv).src = data.data;
                        } else {
                            if (data.code == 1) {
                                alert("上传图片失败");
                            } else if (data.code == 2) {
                                alert("上传图片格式只能为：jpg、png、gif、bmp、jpeg");
                            } else if (data.code == 3) {
                                alert("请选择上传图片");
                            } else {
                                alert("上传失败");
                            }
                        }
                    }
                }
        );
    }

    function deleteImage(e, node) {
        if (e.button == 2) {
            if (confirm("确定移除该图片吗？")) {
                $(node).remove();
            }
        }
    }
    ;


    function selectCitys() {
        var province = $("#provinceid").val();
        $.ajax({
            cache: true,
            type: "POST",
            url: "/getAllCitys.do",
            data: {
                "provinceid": province
            },
            async: false,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    var citys = data.data;
                    var ret = "<option value=''>" + '请选择城市' + "</option>";
                    for (var i = citys.length - 1; i >= 0; i--) {
                        if (citys[i].areaid == province) {
                            ret += "<option value='" + citys[i].cityid + "'>" + citys[i].cityName + "</option>";
                        }
                    }
                    $("#cityid").html(ret);
                } else {
                    var _case = {1: "获取数据失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;


</script>


