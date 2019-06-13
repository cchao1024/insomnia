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
                音乐列表
            </div>

            <a class="add_item" href="/admin/fall_music/add">新增项目</a>

            <div class="col-md-12 column">
                <table class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>音乐名</th>
                        <th>歌手</th>
                        <th>封面图</th>
                        <th>播放</th>
                        <th>点赞数</th>
                        <th>播放数</th>
                        <th>创建时间</th>
                        <th>修改时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#list data as fallMusic>
                        <tr>
                            <td>${fallMusic.id}</td>
                            <td>${fallMusic.name}</td>
                            <td>${fallMusic.singer}</td>
                            <td><img width="60" src="${fallMusic.cover_img}"/></td>
                            <td>
                                <audio src="${fallMusic.src}" loop="loop" controls="controls">播放
                                </audio>
                            </td>
                            <td>${fallMusic.like_count}</td>
                            <td>${fallMusic.play_count}</td>
                            <td>${fallMusic.createTime}</td>
                            <td>${fallMusic.updateTime}</td>
                            <td><a href="javascript:"
                                   onclick="del_confirm('/admin/fall_music/delete?id=${fallMusic.id}')">删除</a>
                            </td>
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
                        <li><a href="/admin/fall_music/list?page=${curPage - 1}&size=${pageSize}">上一页</a>
                        </li>
                    </#if>

                    <#list 1..totalPage as index>
                        <#if curPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li>
                                <a href="/admin/fall_music/list?page=${index}&size=${pageSize}">${index}</a>
                            </li>
                        </#if>
                    </#list>

                    <#if curPage gte totalPage>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/admin/fall_music/list?page=${curPage + 1}&size=${pageSize}">下一页</a>
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