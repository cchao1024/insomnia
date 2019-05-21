<html>
<#include "../common/header.ftl">

<body>

<script>
    // 预览音频
    function on_music_change() {
        var input_file = $("#file_input");
        var reader = new FileReader();
        reader.onloadend = function () {
            var preview_music = $("#preview_music");
            preview_music.attr("src", reader.result);
        };
        reader.readAsDataURL(input_file[0].files[0]);
    }

    var upload_suc = false;

    function on_submit() {
        var input_file = $("#file_input");
        var filename = input_file.val();
        var file_blob = input_file[0].files[0];
        console.log('准备上传文件 ' + filename);

        var forData = new FormData();
        forData.set("file", file_blob);
        forData.set("file_name", filename);
        forData.set("type", "fall_music");

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
                // 成功回调
                $("#resouce").attr("value", object['data']['relativeUrl']);
                $("#submit_btn").click();
            },
            error: function () {
                alert("上传失败")
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
                    <input type="file" id="file_input" accept="audio/mpeg"
                           onchange="on_music_change()"/>
                </div>

                <#--播放音乐预览-->
                <audio loop="loop" src="" id="preview_music" controls="controls">播放</audio>

                <form role="form" method="post" action="/admin/fall_music/save"
                      onsubmit="return on_submit()" style="margin-top: 50px">
                    <div class="form-group">
                        <label>时长，单位s</label>
                        <input name="during" type="number" class="form-control"
                               value="${(fallMusic.during)!''}"/>
                    </div>

                    <input id="resouce" name="src" hidden/>

                    <button id="submit_btn" type="submit" class="btn btn-default">提交添加</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>