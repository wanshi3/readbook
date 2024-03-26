/* =============== 定义全局变量 =============== */
console.log("建议把全局变量都定义在最上方，并写好注释");

var urlType;    // 用于判断用户点击的是添加还是编辑：add-添加; update-编辑;

//页面加载时显示所有记录
$(function (){
    $('#tbl_student').bootstrapTable({
        columns: [
            {checkbox: true},
            {field:'sequence',title:'学号',align:'center',valign:'middle',sortable:true,width:'120'},
            {field:'name',title:'姓名',align:'center',valign:'middle',sortable:true,width:'100'},
            {field:'gender',title:'性别',align:'center',valign:'middle',width:'80'},
            {field:'card',title:'身份证号',align:'center',valign:'middle',width:'160'},
            {field:'birthday',title:'出生日期',align:'center',valign:'middle',width:'100'},
            {field:'nation',title:'民族',align:'center',valign:'middle',width:'100'},
            {field:'nativePlace',title:'籍贯',align:'center',valign:'middle',width:'160'},
            {field:'political',title:'政治面貌',align:'center',valign:'middle',width:'160'},
            {field:'operate', title: '操作', align: 'center', valign: 'middle', width: '200',formatter: operateFormatter,events:operateEvents}
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
        url: "/test/demo/controller/student/get",    //要请求数据的文件路径
        sortName: 'id', //默认排序列
        queryParamsType: 'limit',   //查询参数组织方式
        sidePagination: 'server',   //指定服务器端分页 client
        pagination: true,   //是否分页，即分页按钮
        pageSize: 10,   //单页记录数    limit
        pageList: [10, 20, 50, 100],   //分页步进值
        paginationHAlign: 'right',  //分页条水平对齐
        paginationVAlign: 'bottom', //分页条垂直对齐
        singleSelect: true, //只能选中一行
        clickToSelect: true,    //点击选中行
        toolbar: '#tb_student',    //自定义工具栏对齐方式
        locale: 'zh-CN'   //支持中文

    });
});

//条件查找按钮
$(function(){
    $("#btn_student_search").on("click",function(){
        let param = $("#txt_student_name").val();
        let url = "../demo/controller/student/get?param=";
        url += param;
        $('#tbl_student').bootstrapTable("refresh", {"url": url});
    });
});

//增加记录
$(function(){
    $("#btn_student_insert").on("click",function(){
        $("#studentEditLabel").html("新增用户");
        urlType = "add";
        $("#fm_studentEdit")[0].reset();
        $("#studentEdit").modal("show");
    });
    //向后台发送ajax请求实现添加
    $("#btn_studentEdit_save").on("click",function () {
        var studentSequence = $("#studentEdit_sequence").val();
        studentSequence = studentSequence.replace(/^\s*|\s*$/g, "");//去除字符串中的空格
        if (studentSequence == null || studentSequence == "" || studentSequence.length == 0) {
               $.confirm({
                title: '警告',
                content: "学号不能为空！",
                draggable: true,
                type: 'red'
            });
            return;
        }
        var studentName = $("#studentEdit_name").val();
        studentName = studentName.replace(/^\s*|\s*$/g, "");
        if (studentName == null || studentName == "" || studentName.length == 0) {
            $.confirm({
                title: '警告',
                content: "姓名不能为空！",
                draggable: true,
                type: 'red'
            });
            return;
        }
        var url = ""
        if (urlType == "add") {
            url = "/test/demo/controller/student/insert";
        } else if(urlType == "update"){
            url = "/test/demo/controller/student/update?id=";
            url += $("#studentEdit_id").val()
        }
        var param = $("#fm_studentEdit").serialize()
        console.log("序列化结果"+param);
        $.ajax({
            url:url,
            data:param,
            dataType:"json",
            success:function (data) {
                $.confirm({
                    title: '信息显示',
                    content: data.msg,
                    draggable: true,
                    type: 'green'
                });
                $("#studentEdit").modal("hide");
                var param = $("#btn_student_search").val();
                var url = "/test/demo/controller/student/get?param=" + param;
                $('#tbl_student').bootstrapTable("refresh", {"url": url})
            }
        })
    });

});

//弹出模态框的初始化
$(function(){
    /**
     * 初始化性别下拉框
     */
    $.get(
        '../public/json/gender.json',
        function (data) {
            var opt = "";
            for (var i in data) {
                opt += '<option value="' + data[i].id + '">' + data[i].text + '</option>';
            }
            $("#studentEdit_gender").append(opt);
        }
    );

    /**
     * 初始化民族下拉框
     */
    $.get(
        '../public/json/nation.json',
        function (data) {
            var opt = "";
            for (var i in data) {
                opt += '<option value="' + data[i].id + '">' + data[i].text + '</option>';
            }
            $("#studentEdit_nation").append(opt);
        }
    );

    /**
     * 初始化政治面貌下拉框
     */
    $.get(
        '../public/json/political.json',
        function (data) {
            var opt = "";
            for (var i in data) {
                opt += '<option value="' + data[i].id + '">' + data[i].text + '</option>';
            }
            $("#studentEdit_political").append(opt);
        });
});

//删除,修改
/* =============== 表格操作列相关代码 =============== */

/**
 * 自定义函数  渲染operate列,
 * value： 当前单元格中的值,
 * row：行的数据 ,返回json格式
 * index：行的（索引）
 * field:行的字段名
 * */

function operateFormatter(value, row, index,field) {
    console.log(value,index,row.name,field);
    return [
        '<a title="编辑" class="editRow btn btn-sm btn-warning" style="margin-right:4px;"><span class="icon-edit">编辑</span></a>',
        '<a title="删除" class="deleteRow btn btn-sm btn-danger"><span class="icon-delete">删除</span></a>'
    ].join('')
}

/**
 * 表格最后一列  注册事件  绑定
 * e: 触发的事件
 * value： 当前单元格中的值,
 * row：行的数据 ,返回json格式
 * index：行的（索引）
 */

window.operateEvents = {

    // 编辑
    "click .editRow": function (e, value, row, index) {
        console.log(e.type+value+row.name+index);
        editRow(row);
    },
    // 删除
    "click .deleteRow": function (e, value, row, index) {
        deleteRow(row);
    }
}


/**
 * 编辑
 */
function editRow(row) {
    $("#studentEditLabel").html("编辑用户");
    urlType = "update";
    $("#studentEdit").modal("show");
    //赋值
    $("#studentEdit_id").val(row.id);
    $("#studentEdit_sequence").val(row.sequence);
    $("#studentEdit_name").val(row.name);
    $("#studentEdit_gender").val(row.gender);
    $("#studentEdit_card").val(row.card);
    $("#studentEdit_birthday").val(row.birthday);
    $("#studentEdit_nation").val(row.nation);
    $("#studentEdit_nativePlace").val(row.nativePlace);
    $("#studentEdit_political").val(row.political);
    $("#studentEdit_department").html("");
}

/**
 * 删除
 */
function deleteRow(row) {
    $.confirm({
        title: '警告!',
        content: '确定要删除【' + row.name + '】吗？',
        buttons: {
            formSubmit: {
                text: '确定',
                btnClass: 'btn-red',
                action: function () {
                    $.post(
                        "/test/demo/controller/student/del",
                        {"id": row.id},
                        function (data) {
                            $.confirm({
                                title: '信息显示',
                                content: "删除成功",
                                draggable: true,
                                type: 'green'
                            });
                            var param = $("#btn_student_search").val()
                            var url = "/test/demo/controller/student/get?param=" + param
                            $('#tbl_student').bootstrapTable("refresh", {"url": url})
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


