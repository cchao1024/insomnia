<head>
    <meta charset="utf-8">
    <title>insomnia后端管理系统</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/nav.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">

    <script type="text/javascript">
        function del_confirm(url) {
            if (window.confirm('确定删除？')) {
                window.location.href = url
            }
        }


        // 图片预览
        function previewFile() {
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
        }
    </script>
</head>