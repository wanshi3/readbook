


/* =============== 定义全局变量 =============== */
console.log("建议把全局变量都定义在最上方，并写好注释");

var urlType;    // 用于判断用户点击的是添加还是编辑：add-添加; update-编辑;


/* =============== 页面加载相关代码 =============== */

$(document).ready(function () {

    // // 判断是否登录：未登录，则跳转到首页
    // if (getCookie("studentName") == null) {
    //     //window.location.href = "index.jsp";
    // }

    // 初始化数据表格
    $('#tb_user').bootstrapTable({
        columns: [
            {checkbox: true},
            {field:'name',title:'用户名',align:'center',valign:'middle',sortable:true,width:'120'},
            {field:'username',title:'账户',align:'center',valign:'middle',sortable:true,width:'100'},
            {field:'password',title:'密码',align:'center',valign:'middle',width:'80'},
            {field:'uoperate', title: '操作', align: 'center', valign: 'middle', width: '200', formatter: uoperateFormatter, events: uoperateEvents}
        ],
        queryParams: function (params) {
            /**
             * 如果需要修改默认参数，请在这里完成
             return {
            "page": params.offset,
            "size": params.limit,
            "sort": params.sort,
            "asc": params.order,
        }
             */
            return params
        },
        classes: 'table table-bordered table-striped table-condensed table-hover',
        method: 'get',  //bootstrap 默认的内置请求方式
        url: "http://localhost:8080/web_war_exploded/com/demo/controller/user/Get",    //要请求数据的文件路径
        sortName: 'UserID', //默认排序列
        queryParamsType: 'limit',   //查询参数组织方式
        sidePagination: 'server',   //指定服务器端分页
        pagination: true,   //是否分页，即分页按钮
        pageSize: 5,   //单页记录数
        pageList: [5, 10, 15, 20],   //分页步进值
        paginationHAlign: 'right',  //分页条水平对齐
        paginationVAlign: 'bottom', //分页条垂直对齐
        singleSelect: true, //只能选中一行
        clickToSelect: true,    //点击选中行
        toolbar: '#tb_student',    //自定义工具栏对齐方式
        locale: 'zh-CN',    //支持中文
        onLoadSuccess: function (data) {
            // $.showMessage('数据加载成功！');
            // operateFormatter()
            setTimeout(function () {
                // load.stop();
            }, 1000)
        },
        onLoadError: function (status, res) {
            $.showMessage('数据加载失败，请重试！');
            setTimeout(function () {
                // load.stop();
            }, 1000)
        }
    });

});



/* =============== 表格工具栏相关代码 =============== */

$(document).ready(function () {

    //查询
    $("#btn_search3").click(function () {
        var url = "http://localhost:8080/web_war_exploded/com/demo/controller/user/Get?param=";
        var param = $("#usernametext").val();
        url += param;
        console.log(url)
        $('#tb_user').bootstrapTable("refresh", {"url": url})
    })

    //添加
    $("#btn_insert3").click(function () {

        urlType = "add";


    })


});


/* =============== 表格操作列相关代码 =============== */

/**
 * 渲染operate列
 */
function uoperateFormatter(value, row, index) {

    return [
        '<a title="编辑" class="usereditRow btn btn-sm btn-warning"  data-bs-toggle="modal" data-bs-target="#exampleModal3" style="margin-right:4px;"><span class="icon-edit"></span></a>',
        '<a title="删除" class="userdeleteRow btn btn-sm btn-danger"><span class="icon-delete"></span></a>'
    ].join('')
}



/**
 * 事件绑定
 */
window.uoperateEvents = {
    // 编辑
    "click .usereditRow": function (e, value, row, index) {
        usereditRow(row);
    },
    // 删除
    "click .userdeleteRow": function (e, value, row, index) {

        userdeleteRow(row);

    }
}
/*
 * 编辑
 */
function usereditRow(row) {
    urlType = "update";
    $("#exampleModal3").modal("show");
    //赋值
    $("#UserID").val(row.userID);
    $("#Name").val(row.name);
    $("#Username").val(row.username);
    $("#Password").val(row.password);

}

/**
 * 删除
 */
function userdeleteRow(row) {
    $.confirm({
        title: '警告!',
        content: '确定要删除【' + row.name + '】吗？',
        buttons: {
            formSubmit: {
                text: '确定',
                btnClass: 'btn-red',
                action: function () {
                    $.post(
                        "http://localhost:8080/web_war_exploded/com/demo/controller/user/Delete",
                        {"UserID": row.userID},
                        function (data) {
                            var json = JSON.parse(data)
                            if (json.success) {
                                $.showMessage(json.msg)
                            }
                            var param = $("#usernametext").val()
                            var url = "http://localhost:8080/web_war_exploded/com/demo/controller/user/Get?param=" + param
                            $('#tb_user').bootstrapTable("refresh", {"url": url})
                        })
                }
            },
            cancel: {
                text: '取消',
                btnClass: 'btn-gray',
                action: function () {
                    // $.showMessage("取消")
                }
            }
        }
    });
}


/* =============== studentEdit弹出层相关代码 =============== */

$(document).ready(function () {

    //保存
    $("#btn_user_save").click(function () {

        var studentSequence = $("#Name").val();
        studentSequence = studentSequence.replace(/^\s*|\s*$/g, "");
        if (studentSequence == null || studentSequence == "" || studentSequence.length == 0) {
            $.showMessage("用户名不能为空！");
            return;
        }

        var studentName = $("#Password").val();
        studentName = studentName.replace(/^\s*|\s*$/g, "");
        if (studentName == null || studentName == "" || studentName.length == 0) {
            $.showMessage("密码不能为空！");
            return;
        }
        var url = ""
        if (urlType == "add") {
            url = "http://localhost:8080/web_war_exploded/com/demo/controller/user/Insert";
        } else {
            url = "http://localhost:8080/web_war_exploded/com/demo/controller/user/Update?UserID=";
            url += $("#UserID").val()
        }
        var param = $("#userfrom").serialize()
        $.post(url, param, function (data) {
            var json = JSON.parse(data)
            if (json.success) {
                $.showMessage(json.msg);
                $("#exampleModal3").modal("hide");
                console.log(json)
                var str=""
                str+="用户名："+json.rows[0].name+"\n"+
                    "账户："+json.rows[0].username+"\n"+
                    "密码；"+json.rows[0].password;
                alert(str)

                var param = $("#usernametext").val();
                var url = "http://localhost:8080/web_war_exploded/com/demo/controller/user/Get?param=" + param;
                $('#tb_user').bootstrapTable("refresh", {"url": url})
            }
        })
    })

});








