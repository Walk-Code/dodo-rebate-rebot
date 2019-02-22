<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>微信机器人操作页面</title>
    <style>
        .content-warp {
            text-align: center;
            color: red;
            font-size: 16px;
            font-family: "黑体";
            border: 1px solid #ccc;
            margin: 0px;
            padding: 0px;
        }

        input {
            width: 300px;
            height: 25px;
            font-size: 14px;
        }

        fieldset {
            text-align: center;
            width: 50%;
            margin-left: 24%;
        }

        fieldset > p {
            width: 502px;
            text-align: left;
        }

        select {
            width: 200px;
        }
    </style>
</head>
<body>
<div class="content-warp">
    <p><h4>请用微信扫描下方登录二维码,如不能正常登录,请重新退出登录.<a href="/logout">刷新二维码</a></h4></p>
    <p><img id="wxLoginQrImage" width="200" src='/getWxLoginQrImageUrl'/></p>
    <p>
    <h3><span id="loginStatus"></span></h3></p>
    <p>
        <fieldset id="testWarp" style="display: none;">
            <legend>测试区域</legend>
    <p>
        <span>搜索关键词:</span>
        <input type="text" id="searchText" placeholder="请输入你要发送的群组或者好友昵称关键词">
    </p>
    <p>
    <p>
        <span>发送目标ID:</span>
        <input type="text" id="sentUserId" placeholder="请输入你要发送目标ID"></p>
    <p>
        <span>发送类型:&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <select id="sendUserType">
            <option value="1">好友</option>
            <option value="2" selected>群组</option>
        </select>
    </p>
    <p>
        <span id="searchResult" style="word-break: keep-all;"></span>
    </p>
    <a href="javascript:void(0);" id="searchBt">搜索目标用户</a>
    <a href="javascript:void(0);" id="sentBt">测试发送数据</a>
    </fieldset>
    </p>
</div>
</body>
</html>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
    $(document).ready(function () {

        checkLoginStatus();
        //循环执行，每隔1秒钟执行一次 1000
        var checkLoginNum = 0;
        var checkLoginStatusTimer = window.setInterval(checkLoginStatus, 5 * 1000);

        function checkLoginStatus() {
            checkLoginNum++;
            $.ajax({
                type: 'POST',
                dataType: "json",
                url: "/checkLoginStatus",
                data: $.param({}),
                success: function (data) {
                    $("#loginStatus").html(data.message + " ---检查次数:" + checkLoginNum);
                    if (data.status == 200) {
                        $("#testWarp").show();
                    } else {
                        $("#testWarp").hide();
                    }
                }
            });
        }

        //测试搜索用户
        $("#searchBt").click(function () {
            let data = {};
            data.sentUserType = $("#sendUserType").val();
            data.searchText = $("#searchText").val();
            console.log(data);
            if (data.searchText == null || data.searchText == "") {
                alert("请先输入搜索内容.");
                return;
            }
            $.ajax({
                type: 'POST',
                dataType: "json",
                url: "/searchUser",
                data: data,
                success: function (data) {
                    $("#searchResult").show();
                    if (data.data == null || data.data == "") {
                        $("#searchResult").html("搜索内容:暂时找不到对应的数据.");
                        return;
                    }
                    $("#searchResult").html("搜索内容:<br/> 昵称: " + data.data.NickName + "<br/> 备注: " + data.data.RemarkName + "<br/> 目标ID: " + data.data.UserName);
                }
            });
        });

        //测试发送信息
        $("#sentBt").click(function () {
            let data = {};
            data.sentUserId = $("#sentUserId").val();
            console.log(data);
            if (data.sentUserId == null || data.sentUserId == "" || data.sentUserId == undefined) {
                alert("请先输入发送目标ID.");
                return;
            }
            $.ajax({
                type: 'POST',
                dataType: "json",
                url: "/test/sfsfsdfsdfs",
                data: data,
                success: function (data) {
                    if (data.status == 200) {
                        alert("发送成功.");
                    } else {
                        alert(data.message);
                    }
                }
            });
        });

    });
</script>