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
                用户列表
            </div>

            <div class="col-md-12 column">
                <table class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>昵称</th>
                        <th>邮箱</th>
                        <th>头像</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>获得的点赞数</th>
                        <th>操作</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#list data as item>
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.nickName}</td>
                            <td>${item.email}</td>
                            <td><img height="60" width="60" src="${item.avatar}"
                                     alt="${item.avatar}"></td>
                            <td>${item.gender}</td>
                            <td>${item.age}</td>
                            <td>${item.getLike}</td>
                            <td>${item.createTime}</td>
                            <td>${item.updateTime}</td>
                            <td><a href="javascript:"
                                   onclick="del_confirm('xx/xxx'+${item.id})">删除</a></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>

            <#--分页-->
            <div class="col-md-12 column">
                <ul class="pagination pull-right">
                    <#if listBean.curPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li>
                            <a href="/admin/user/list?page=${listBean.curPage - 1}&size=${pageSize}">上一页</a>
                        </li>
                    </#if>

                    <#list 1..listBean.totalPage as index>
                        <#if listBean.curPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li>
                                <a href="/admin/user/list?page=${index}&size=${pageSize}">${index}</a>
                            </li>
                        </#if>
                    </#list>

                    <#if listBean.curPage gte listBean.totalPage>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li>
                            <a href="/admin/user/list?page=${listBean.curPage + 1}&size=${pageSize}">下一页</a>
                        </li>
                    </#if>
                </ul>
            </div>
            <br style="margin-bottom: 100px"/>
        </div>
    </div>
</div>
</body>
</html>