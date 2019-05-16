<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" enctype="multipart/form-data" action="/admin/fall_image/save">
                        <div class="form-group">
                            <label>width</label>
                            <input name="width" type="text" class="form-control" value="${(fallImage.width)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>height</label>
                            <input name="height" type="number" class="form-control" value="${(fallImage.height)!''}"/>
                        </div>

                        <#-- 文件上传 -->
                        <div class="form-group">
                            <label for="exampleInputFile">文件上传</label>
                            <input type="file" id="exampleInputFile" name="file" onchange="previewFile()"/>
                        </div>
                        <br/>
                        <img src="/image/place_holder.svg" height="200" alt="上传图片预览">

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
                        <button type="submit" class="btn btn-default">提交添加</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>