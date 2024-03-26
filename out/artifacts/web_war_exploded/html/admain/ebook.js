


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
    $('#tb_ebook').bootstrapTable({
        columns: [
            {checkbox: true},
            {field:'title',title:'书名',align:'center',valign:'middle',sortable:true,width:'120'},
            {field:'author',title:'价格',align:'center',valign:'middle',sortable:true,width:'100'},
            {field:'views',title:'浏览次数',align:'center',valign:'middle',width:'80'},
            {field:'theme',title:'主题',align:'center',valign:'middle',width:'160'},
            {field:'genre',title:'类别',align:'center',valign:'middle',width:'100'},
            {field:'eoperate2', title: '操作2', align: 'center', valign: 'middle', width: '200',formatter:eoperateFormatter2, events: eoperateEvents},
            {field:'eoperate', title: '操作', align: 'center', valign: 'middle', width: '200', formatter: eoperateFormatter, events: eoperateEvents}
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
        url: "http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get",    //要请求数据的文件路径
        sortName: 'EBookID', //默认排序列
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
    $("#btn_search2").click(function () {
        var url = "http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get?param=";
        var param = $("#Bookname").val();
        url += param;
        $('#tb_ebook').bootstrapTable("refresh", {"url": url})
    })

    //添加
    $("#btn_insert2").click(function () {

        urlType = "add";


    })


});


/* =============== 表格操作列相关代码 =============== */

/**
 * 渲染operate列
 */
function eoperateFormatter2(value,row,index){
    // alert("ooo")
    var str="";
    str +='<img '+'src= "../../'+row.picture+'"class="img-fluid shadow p-3 mb-5 bg-body rounded"/>';
    return str;

}
function eoperateFormatter(value, row, index) {

    return [
        '<a title="编辑" class="ebookeditRow btn btn-sm btn-warning"  data-bs-toggle="modal" data-bs-target="#exampleModal2" style="margin-right:4px;"><span class="icon-edit"></span></a>',
        '<a title="删除" class="ebookdeleteRow btn btn-sm btn-danger"><span class="icon-delete"></span></a>'
    ].join('')
}



/**
 * 事件绑定
 */
window.eoperateEvents = {
    // 编辑
    "click .ebookeditRow": function (e, value, row, index) {
        ebookeditRow(row);
    },
    // 删除
    "click .ebookdeleteRow": function (e, value, row, index) {
        ebookdeleteRow(row);
    }
}
/*
 * 编辑
 */
function ebookeditRow(row) {
    urlType = "update";
    $("#exampleModal2").modal("show");
    //赋值
    $("#ebookID").val(row.EBookID);
    $("#eTitle").val(row.title);
    $("#eAuthor").val(row.author);
    $("#eViews").val(row.views);
    $("#eTheme").val(row.theme);
    $("#eGenre").val(row.genre);
    $("#ePicture").val(row.picture);

}

/**
 * 删除
 */
function ebookdeleteRow(row) {
    $.confirm({
        title: '警告!',
        content: '确定要删除【' + row.title + '】吗？',
        buttons: {
            formSubmit: {
                text: '确定',
                btnClass: 'btn-red',
                action: function () {
                    $.post(
                        "http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Delete",
                        {"EBookID": row.EBookID},
                        function (data) {
                            var json = JSON.parse(data)
                            if (json.success) {
                                $.showMessage(json.msg)
                            }
                            var param = $("#searchName").val()
                            var url = "http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get?param=" + param
                            $('#tb_ebook').bootstrapTable("refresh", {"url": url})
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
    $("#btn_ebook_save").click(function () {

        var studentSequence = $("#eTitle").val();
        studentSequence = studentSequence.replace(/^\s*|\s*$/g, "");
        if (studentSequence == null || studentSequence == "" || studentSequence.length == 0) {
            $.showMessage("电子书名不能为空！");
            return;
        }

        var studentName = $("#eAuthor").val();
        studentName = studentName.replace(/^\s*|\s*$/g, "");
        if (studentName == null || studentName == "" || studentName.length == 0) {
            $.showMessage("作者不能为空！");
            return;
        }

        var url = ""
        if (urlType == "add") {
            url = "http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Insert";
        } else {
            url = "http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Update?EbookID=";
            url += $("#ebookID").val()
        }
        var param = $("#ebookfrom").serialize()
        $.post(url, param, function (data) {
            var json = JSON.parse(data)
            if (json.success) {
                $.showMessage(json.msg);
                $("#exampleModal2").modal("hide");
                var param = $("#searchName").val();
                var url = "http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get?param=" + param;
                $('#tb_ebook').bootstrapTable("refresh", {"url": url})
            }
        })
    })

});








