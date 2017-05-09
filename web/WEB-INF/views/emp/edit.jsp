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
                    <input type="hidden" value="${cover}" id="cover">
                    <input type="hidden" value="${cardpic}" id="cardpic">
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
                        <label class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="sex">
                                <option value="0" ${emp.sex=='0'?'selected':''}>女</option>
                                <option value="1" ${emp.sex=='1'?'selected':''}>男</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">学历</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="education">
                                <option value="2" ${emp.education=='2'?'selected':''}>专科以下</option>
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
                        <label class="col-sm-2 control-label">兴趣爱好</label>
                        <div class="col-sm-4">
                            <input type="text" id="listsName" class="form-control" value="${listsName}" readonly="true"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">单身状态</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="state" readonly="readonly">
                                <option value="1" ${emp.state=='1'?'selected':''}>单身</option>
                                <option value="2" ${emp.state=='2'?'selected':''}>交往中</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">身份认证</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="rzstate1">
                                <option value="0" ${emp.rzstate1=='0'?'selected':''}>未认证</option>
                                <option value="1" ${emp.rzstate1=='1'?'selected':''}>已认证</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">会员认证</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="rzstate2">
                                <option value="0" ${emp.rzstate2=='0'?'selected':''}>未认证</option>
                                <option value="1" ${emp.rzstate2=='1'?'selected':''}>已认证</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">诚信认证</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="rzstate3">
                                <option value="0" ${emp.rzstate3=='0'?'selected':''}>未认证</option>
                                <option value="1" ${emp.rzstate3=='1'?'selected':''}>已认证</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否使用</label>
                        <div class="col-sm-4">
                            <select class="form-control" id="is_use">
                                <option value="0" ${emp.is_use=='0'?'selected':''}>禁用</option>
                                <option value="1" ${emp.is_use=='1'?'selected':''}>使用</option>
                                <option value="2" ${emp.is_use=='2'?'selected':''}>尚未维护资料</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">注册日期</label>
                        <div class="col-sm-4">
                            <input type="text" id="dateline" class="form-control" value="${um:format(emp.dateline, 'yyyy-MM-dd HH:mm:ss')}" readonly="true"
                                   data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
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

    function saveEmp(mm_emp_id) {
        var empid = $("#empid").val();
        var nickname = $("#nickname").val();
        var sign = $("#sign").val();
        var age = $("#age").val();
        var heightl = $("#heightl").val();
        var education = $("#education").val();
        var marriage = $("#marriage").val();
        var company = $("#company").val();
        var sex = $("#sex").val();
        var rzstate1 = $("#rzstate1").val();
        var rzstate2 = $("#rzstate2").val();
        var rzstate3 = $("#rzstate3").val();

        var provinceid = $("#provinceid").val();
        var cityid = $("#cityid").val();
        var is_use = $("#is_use").val();

        if (nickname.replace(/\s/g, '') == '') {
            alert("姓名不能为空");
            return;
        }

        if (sign.replace(/\s/g, '') == '') {
            alert("签名不能为空");
            return;
        }
//        if (age.replace(/\s/g, '') == '') {
//            alert("年龄不能为空");
//            return;
//        }
//        if (heightl.replace(/\s/g, '') == '') {
//            alert("身高不能为空");
//            return;
//        }
        var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
        if (age.replace(/\s/g, '') == '') {
            alert("年龄不能为空");
            return;
        } else {
            if (!reg.test(age)) {
                alert("年龄必须是正整数！");
                return;
            }
        }
        if (heightl.replace(/\s/g, '') == '') {
            alert("身高不能为空");
            return;
        } else {
            if (!reg.test(heightl)) {
                alert("身高必须是正整数！");
                return;
            }
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

        if (provinceid.replace(/\s/g, '') == '') {
            alert("请选择省！");
            return;
        }
        if (cityid.replace(/\s/g, '') == '') {
            alert("请选择县");
            return;
        }
        if (is_use.replace(/\s/g, '') == '') {
            alert("请选择是否禁用该用户");
            return;
        }

        var imagePath = $("img[name='imagePath']").attr("src");

        if (imagePath == "" || imagePath == null) {
            //空的  没有头像文件
            alert("请上传头像文件！");
            return;
        }
        if (imagePath.substring(0,4)=="http")
        {
            //说明没有新文件
            imagePath = $("#cover").val();
        }

        var imagePath1 = $("img[name='imagePath1']").attr("src");

        if (imagePath1 == "" || imagePath1 == null) {
            alert("请上传身份文件！");
            return;
        }

        if (imagePath1.substring(0,4)=="http")
        {
            //说明没有新文件
            imagePath1 = $("#cardpic").val();
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/emp/edit.do",
            data: {
                "empid": empid,
                "nickname": nickname,
                "sign": sign,
                "age": age,
                "sex": sex,
                "heightl": heightl,
                "cover": imagePath,
                "cardpic": imagePath1,
                "education": education,
                "marriage": marriage,
                "company": company,
                "provinceid": provinceid,
                "cityid": cityid,
                "rzstate1": rzstate1,
                "rzstate2": rzstate2,
                "rzstate3": rzstate3,
                "is_use": is_use
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("修改成功");
                    history.go(-1);
                } else {
                    alert(data.message)
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


