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
    function preview_img() {
        var input_file = $("#file_input");
        var reader = new FileReader();
        reader.onloadend = function () {
            $("#preview_image").attr("src", reader.result);
        };
        reader.readAsDataURL(input_file[0].files[0]);
    }

    function on_submit() {
        upload_img(function (path) {
            // 成功回调
            $("#file_input").value(path);
            $("#submit_btn").submit();
        });
    }

    function upload_img(callback) {
        var input_file = $("#file_input");
        var filename = input_file.val();
        var file_blob = input_file[0].files[0];
        console.log('准备上传文件 ' + filename);

        var forData = new FormData();
        forData.set("file", file_blob);
        forData.set("file_name", filename);

        // 发起请求 上传到服务端
        $.ajax({
            type: "POST",
            url: "/file/uploadImage",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            data: forData,
            success: function (data) {
                callback(data.data.relativeUrl);
            },
            error: function () {
                document.alert("上传失败")
            }
        });
    }
</script>

<#--边栏sidebar-->
<#include "../common/nav2.ftl">

<div id="page-content-wrapper" class="toggled">
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <form role="form" method="post" action="/admin/fall_image/save">
                    <div class="form-group">
                        <label>width</label>
                        <input name="width" type="text" class="form-control"
                               value="${(fallImage.width)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>height</label>
                        <input name="height" type="number" class="form-control"
                               value="${(fallImage.height)!''}"/>
                    </div>

                    <#-- 文件上传 -->
                    <div class="form-group">
                        <label for="exampleInputFile">文件上传</label>
                        <input type="file" id="file_input" name="src" accept="image/*"
                               onchange="preview_img()"/>
                    </div>
                    <br/>
                    <img id="preview_image" src="/image/place_holder.svg" height="200"
                         alt="上传图片预览">

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
                    <br/><br/>
                    <button id="submit_btn" type="submit" class="btn btn-default"
                            onclick="on_submit()">提交添加
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>