<html>
<#include "../common/header.ftl">

<body>

<script>
    /*
        var preview = document.querySelector('img');
        var file = document.querySelector('input[type=file]').files[0];
        var reader = new FileReader();

        reader.onloadend = function () {
            preview.src = reader.result;
        }

        if (file) {
            reader.readAsDataURL(file);
        } else {
            preview.src = "";
        }
    */

    // 预览图片
    function on_preview() {
        var input_file = $("#file_input");
        var reader = new FileReader();
        var image_file = input_file[0].files[0];
        reader.readAsDataURL(image_file);
        reader.onloadend = function () {
            var preview_img = $("#preview_image");
            preview_img.attr("src", reader.result);
        };

        // 填入宽高
        var _URL = window.URL || window.webkitURL;
        var file, img;
        if ((file = image_file)) {
            img = new Image();
            img.onload = function () {
                $("#img_h").attr("value", this.height);
                $("#img_w").attr("value", this.width);
            };
            img.src = _URL.createObjectURL(file);
        }
    }

    // 上传图片
    var upload_suc = false;
    function on_submit() {
        var input_file = $("#file_input");
        var filename = input_file.val();
        var file_blob = input_file[0].files[0];
        console.log('准备上传文件 ' + filename);

        var forData = new FormData();
        forData.set("file", file_blob);
        forData.set("file_name", filename);
        forData.set("type", "fall_image");

        // 发起请求 上传到服务端
        $.ajax({
            type: "POST",
            url: "/file/upload",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            data: forData,
            dataType: "json",
            complete: function (data) {
                /**{
                 "code":0,
                 "msg":"请求成功",
                 "data":{
                   "relativeUrl":"/image/2019_05_20/35693.png",
                   "absoluteUrl":"https:/insomnia-1254010092.cos.ap-hongkong.myqcloud.com/image/2019_05_20/35693.png",
                   "fileName":"35693.png"
                  }}*/

                var object = eval("(" + data.responseText + ")");
                console.log("返回json data：" + object['data']['relativeUrl']);
                upload_suc = true;
                // 成功后，提交表单
                $("#resouce").attr("value", object['data']['relativeUrl']);
                $("#submit_btn").click();
            },
            error: function () {
                document.alert("上传失败")
            }
        });
        return upload_suc;
    }
</script>

<#--边栏sidebar-->
<#include "../common/nav2.ftl">

<div id="page-content-wrapper" class="toggled">
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-12 column" style="margin-top: 30px; margin-left: 10px">

                <#-- 文件上传 -->
                <label for="exampleInputFile">文件上传</label>

                <div class="form-group">
                    <input type="file" id="file_input" accept="image/*"
                           onchange="on_preview(this)"/>
                </div>

                <img id="preview_image" src="/image/place_holder.svg" height="200"
                     alt="上传图片预览">

                <form role="form" method="post" action="/admin/fall_image/save"
                      onsubmit="return on_submit()" style="margin-top: 50px">
                    <div class="form-group">
                        <label>width</label>
                        <input id="img_w" name="width" type="number" class="form-control"
                               value="${(fallImage.width)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>height</label>
                        <input id="img_h" name="height" type="number" class="form-control"
                               value="${(fallImage.height)!''}"/>
                    </div>

                    <input id="resouce" name="src" hidden/>

                    <#--<div class="form-group">
                        <label>类目</label>
                        <select name="categoryType" class="form-control">
                            <#list categoryList as category>
                                <option value="${(category.categoryType)}"
                                        <#if (fallImage.categoryType)?? && fallImage.categoryType==category.categoryType>
                                selected
                                        </#if>>
                                    ${category.categoryName}
                                </option>
                            </#list>

                        </select>
                    </div>-->

                    <button id="submit_btn" type="submit" class="btn btn-default">提交添加</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>