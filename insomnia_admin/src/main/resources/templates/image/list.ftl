<html>
<#include "../common/header.ftl">

<body>

<#--边栏sidebar-->
<#include "../common/nav2.ftl">

<#--主要内容content-->
<div id="page-content-wrapper" class="toggled">
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="content_title">
                图片列表
            </div>

            <div class="col-md-12 column">
                <table class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>商品id</th>
                        <th>图片</th>
                        <th>width</th>
                        <th>height</th>
                        <th>点赞数</th>
                        <th>创建时间</th>
                        <th>修改时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#list data as fallImage>
                        <tr>
                            <td>${fallImage.id}</td>
                            <td><img height="200" width="200" src="${fallImage.src}" alt="${fallImage.src}"></td>
                            <td>${fallImage.width}</td>
                            <td>${fallImage.height}</td>
                            <td>${fallImage.like_count}</td>
                            <td>${fallImage.createTime}</td>
                            <td>${fallImage.updateTime}</td>
                            <td><a href="javascript:" onclick="del_confirm('xx/xxx'+${fallImage.id})">删除</a></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>

            <#--分页-->
            <div class="col-md-12 column">
                <ul class="pagination pull-right">
                    <#if curPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/admin/fall_image/list?page=${curPage - 1}&size=${pageSize}">上一页</a></li>
                    </#if>

                    <#list 1..totalPage as index>
                        <#if curPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/admin/fall_image/list?page=${index}&size=${pageSize}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if curPage gte totalPage>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/admin/fall_image/list?page=${curPage + 1}&size=${pageSize}">下一页</a></li>
                    </#if>
                </ul>
            </div>
            <br style="margin-bottom: 100px"/>
        </div>
    </div>
</div>
</body>
</html>