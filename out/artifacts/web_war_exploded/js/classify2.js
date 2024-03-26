$(function() {
    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        };
    }

    var accordion = new Accordion($('#accordion'), false);
});




(function(d, c, a) {
    function b(f, e) {
        this.el = f;
        this.options = { pageNo: e.initPageNo || 1, totalPages: e.totalPages || 1, totalCount: e.totalCount || "", slideSpeed: e.slideSpeed || 0, jump: e.jump || false, callback: e.callback || function() {} };
        this.init()
    }
    b.prototype = {
        constructor: b,
        init: function() {
            this.createDom();
            this.bindEvents()
        },
        createDom: function() {
            var k = this,
                m = "",
                e = "",
                j = "",
                g = 60,
                h = k.options.totalPages,
                l = 0;
            h > 5 ? l = 5 * g : l = h * g;
            for (var f = 1; f <= k.options.totalPages; f++) { f != 1 ? m += "<li>" + f + "</li>" : m += '<li class="sel-page">' + f + "</li>" }
            k.options.jump ? e = '<input type="text" placeholder="1" class="jump-text" id="jumpText"><button type="button" class="jump-button" id="jumpBtn">跳转</button>' : e = "";
            j = '<button type="button" id="firstPage" class="turnPage first-page">首页</button>' + '<button class="turnPage" id="prePage">上一页</button>' + '<div class="pageWrap" style="width:' + l + 'px">' + '<ul id="pageSelect" style="transition:all ' + k.options.slideSpeed + 'ms">' + m + "</ul></div>" + '<button class="turnPage" id="nextPage">下一页</button>' + '<button type="button" id="lastPage" class="last-page">尾页</button>' + e + '<p class="total-pages">共&nbsp;' + k.options.totalPages + "&nbsp;页</p>" + '<p class="total-count">' + k.options.totalCount + "</p>";
            k.el.html(j)
        },
        bindEvents: function() {
            var k = this,
                f = d("#pageSelect"),
                r = f.children(),
                n = r[0].offsetWidth,
                l = k.options.totalPages,
                g = k.options.pageNo,
                e = 0,
                o = d("#prePage"),
                m = d("#nextPage"),
                i = d("#firstPage"),
                j = d("#lastPage"),
                q = d("#jumpBtn"),
                h = d("#jumpText");
            o.on("click", function() {
                g--;
                if (g < 1) { g = 1 }
                p(g)
            });
            m.on("click", function() {
                g++;
                if (g > r.length) { g = r.length }
                p(g)
            });
            i.on("click", function() {
                g = 1;
                p(g)
            });
            j.on("click", function() {
                g = l;
                p(g)
            });
            q.on("click", function() {
                var s = parseInt(h.val().replace(/\D/g, ""));
                if (s && s >= 1 && s <= l) {
                    g = s;
                    p(g);
                    h.val(s)
                }
            });
            r.on("click", function() {
                g = d(this).index() + 1;
                p(g)
            });

            function p(s) {
                r.removeClass("sel-page").eq(s - 1).addClass("sel-page");
                if (l <= 5) { k.options.callback(s); return false }
                if (s >= 3 && s <= l - 2) { e = (s - 3) * n }
                if (s == 2 || s == 1) { e = 0 }
                if (s > l - 2) { e = (l - 5) * n }
                f.css("transform", "translateX(" + (-e) + "px)");
                s == 1 ? i.attr("disabled", true) : i.attr("disabled", false);
                s == 1 ? o.attr("disabled", true) : o.attr("disabled", false);
                s == l ? j.attr("disabled", true) : j.attr("disabled", false);
                s == l ? m.attr("disabled", true) : m.attr("disabled", false);
                k.options.callback(s)
            }
            p(k.options.pageNo)
        }
    };
    d.fn.paging = function(e) { return new b(d(this), e) }
})(jQuery, window, document);
//分类

$(function (){
    var forData={"Theme":"novel","Genre":"science","param":""}
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get("Theme")!=null)
    {
        forData["Theme"] =urlParams.get("Theme")
        forData["Genre"] =urlParams.get("Genre")
    }
    if(urlParams.get("param")!=null)
    {
        forData["param"] =urlParams.get("param")
    }

    $.ajax({
        // URL
        url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
        // 参数
        data: forData,
        // 请求类型
        type: 'POST',
        // 期望的返回数据类型，请根据实际情况修改
        dataType: 'json',
        success: function (data) {

            let elements1 = document.querySelectorAll('.f1');
            let elements2 = document.querySelectorAll('.f2');
            let elements4 = document.querySelectorAll('.p1');
            var i=0
            elements1.forEach((element, index) => {
                if (index < data.rows.length) {
                    element.src = ".." + data.rows[index].picture;
                    console.log(data.rows[index].picture);
                }
            });

            elements2.forEach((element, index) => {
                if (index < data.rows.length) {
                    console.log(index);
                    element.innerText = data.rows[index].title;
                }
            });

            elements4.forEach((element, index) => {
                if (index < data.rows.length) {
                    element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                }
            });
            //window.history.back();
            //  window.location.href = "/web_war_exploded/html/main.html"
        },
        timeout:2000,
        error:function (){
            alert("网络延迟")

        }
    });
    $("#accordion").click(function (e){

        console.log(forData["Genre"])
        // console.log(document.getElementById("f1").src="../img/novel/adventure/2.jpg")
        // console.log(2)

        if ($(e.target).html()=="爱情小说")
        {
            forData["Theme"] ="novel"
            forData["Genre"]= "romance"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }

        if ($(e.target).html()=="冒险小说")
        {
            forData["Theme"] ="novel"
            forData["Genre"]= "adventure"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="科幻小说")
        {
            forData["Theme"] ="novel"
            forData["Genre"]= "science"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="推理小说")
        {
            forData["Theme"] ="novel"
            forData["Genre"]= "reasoning"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="历史小说")
        {
            console.log("ok")
            forData["Theme"] ="novel"
            forData["Genre"]= "historical"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="文学经典")
        {
            forData["Theme"] ="novel"
            forData["Genre"]= "literar"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="自助成长")
        {
            forData["Theme"] ="popularScienceBooks"
            forData["Genre"]= "self-helpGrowth"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="历史传记")
        {
            forData["Theme"] ="popularScienceBooks"
            forData["Genre"]= "historicalBiography"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="文学评价")
        {
            forData["Theme"] ="popularScienceBooks"
            forData["Genre"]= "literaryCriticism"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="心理学")
        {
            forData["Theme"] ="popularScienceBooks"
            forData["Genre"]= "psychology"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="政治经济")
        {
            forData["Theme"] ="popularScienceBooks"
            forData["Genre"]= "polittica"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
        if ($(e.target).html()=="童话故事")
        {
            forData["Theme"] ="children"
            forData["Genre"]= "fairy"
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/ebook/Get',
                // 参数
                data: forData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {

                    let elements1 = document.querySelectorAll('.f1');
                    let elements2 = document.querySelectorAll('.f2');
                    let elements4 = document.querySelectorAll('.p1');
                    var i=0
                    elements1.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.src = ".." + data.rows[index].picture;
                            console.log(data.rows[index].picture);
                        }
                    });

                    elements2.forEach((element, index) => {
                        if (index < data.rows.length) {
                            console.log(index);
                            element.innerText = data.rows[index].title;
                        }
                    });

                    elements4.forEach((element, index) => {
                        if (index < data.rows.length) {
                            element.href = "contain.html?BookID=" + data.rows[index].EBookID;
                        }
                    });
                    //window.history.back();
                    //  window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
    })

})