
// 判断是否登录：未登录，则跳转到首页
// if (getCookie("studentName") == null) {
//     //window.location.href = "index.jsp";
// }



$(function($) {
    $("#h1").click(function (){
        $(".p1").show()
        $(".p2").hide()
        $(".p3").hide()
        $(".p4").hide()
        $(".p5").hide()
    })
    $("#h2").click(function (){
        $(".p1").hide()
        $(".p2").show()
        $(".p3").hide()
        $(".p4").hide()
        $(".p5").hide()
    })
    $("#h3").click(function (){
        $(".p1").hide()
        $(".p2").hide()
        $(".p3").show()
        $(".p4").hide()
        $(".p5").hide()
    })
    $("#h4").click(function (){
        $("#p1").hide()
        $("#p2").hide()
        $("#p3").hide()
        $("#p4").show()
        $(".p5").hide()
    })

});
$(function (){
    $("#back").click(function (){
        Cookies.remove('userID')
        Cookies.remove('username')
        Cookies.remove('name')
        window.location.href = "/web_war_exploded/html/main.html"
    })
})
// var load = new Loading();
//
// $(document).ready(function () {
//
//     // 点击按钮触发loading动画
//     $(".nav-treeview .nav-link").click(function () {
//         load.init();
//         load.start();
//     })
//
//     // 首页
//     $("#a_homePage").click(function () {
//         $("#span_title").html("教学管理 - <small>首页</small>");
//         $("#div_content").load("admin/homePage/homePage.inc");
//     });
//
//     // 课程管理
//     $("#a_basic_course").click(function () {
//         $("#span_title").html("基础信息 - <small>课程管理</small>");
//         $("#div_content").load("admin/basic/course/course.inc");
//         $(".nav-link").css("color", "#c2c7d0");
//         $("#a_basic_course").css("color", "#f0f000");
//     });
//
//     // 教师管理
//     $("#a_basic_teacher").click(function () {
//         $("#span_title").html("基础信息 - <small>教师管理</small>");
//         $("#div_content").load("admin/basic/teacher/teacher.inc");
//         $(".nav-link").css("color", "#c2c7d0");
//         $("#a_basic_teacher").css("color", "#f0f000");
//     });
//
//     // 学生管理
//     $("#a_basic_student").click(function () {
//         $("#span_title").html("基础信息 - <small>学生管理</small>");
//         $("#div_content").load("admin/basic/student/student.inc");
//         $(".nav-link").css("color", "#c2c7d0");
//         $("#a_basic_student").css("color", "#f0f000");
//     });
//
//     // 成绩录入
//     $("#a_score_manage").click(function () {
//         $("#span_title").html("成绩管理 - <small>成绩录入</small>");
//         $("#div_content").load("admin/score/manage/manage.inc");
//         $(".nav-link").css("color", "#c2c7d0");
//         $("#a_score_manage").css("color", "#f0f000");
//     });
//
//     // 成绩统计
//     $("#a_score_count").click(function () {
//         $("#span_title").html("成绩管理 - <small>成绩统计</small>");
//         $("#div_content").load("admin/score/count/count.inc");
//         $(".nav-link").css("color", "#c2c7d0");
//         $("#a_score_count").css("color", "#f0f000");
//     });
//
//
// });