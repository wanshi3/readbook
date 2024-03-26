/**
 * 评分插件
 * @author LQresier
 */
(function ($) {
    var LQScore = function (ele, options) {
        this.$element = ele;
        this.mouseoverColor; //鼠标经过的颜色,自动生成相对于选中颜色的浅色
        this.isScoreFinish = false; //评分是否完成
        this.defaults = {
            $tipEle: null,//提示元素
            fontSize: "20px", //大小
            isReScore: false, //是否允许重新评分
            tips: null, //提示
            zeroTip: "", //无分数提示
            score: null, //分数
            callBack: null, //评分后处理
            content: "★", //内容
            defultColor: "rgb(220,220,220)", //默认颜色(未选中的颜色)
            selectColor: ["rgb(198, 209, 222)", "#8eb9f5", "#ffac38", "#ff8547", "#f54545"] //选中后的颜色
        };
        this.options = $.extend({}, this.defaults, options);
    }
    LQScore.prototype = {
        // 初始化评分插件
        init: function () {
            var obj = this;
            var $tipEle = obj.options.$tipEle;
            obj.createMouseoverColor(obj.options.selectColor);
            obj.$element.addClass("lq-score");
            obj.$element.append($("<ul></ul>"));
            var $ulEle = obj.$element.children("ul").eq(0);
            for (var i = 0; i < 5; i++) {
                $ulEle.append($("<li style='font-size:" + obj.options.fontSize + ";'>" + obj.options.content + "</li>"));
            }
            var $liEle = $ulEle.children("li");
            $liEle.css("color", obj.options.defultColor);
            if ($tipEle) {
                $tipEle.html(obj.options.zeroTip).css("color", obj.options.defultColor);
            }
            if (obj.options.score != null) {
                if (obj.options.score < 0 || obj.options.score > 5) {
                    throw new Error("score 超出范围");
                } else {
                    obj.isScoreFinish=true;
                    this.setScore();
                }
            }
            $liEle.on("mouseover", function () {
                if (!obj.options.isReScore && obj.isScoreFinish) {
                    return;
                }
                var len = $(this).prevAll("li").add(this).length;
                $(this).prevAll("li").add(this).css("color", (obj.mouseoverColor instanceof Array) ? obj.mouseoverColor[len - 1] : obj.mouseoverColor);
                $(this).nextAll("li").css("color", obj.options.defultColor);
                if ($tipEle) {
                    if (obj.options.tips === "default") {
                        $tipEle.html(len + "分").css("color", (obj.mouseoverColor instanceof Array) ? obj.mouseoverColor[len - 1] : obj.mouseoverColor);
                    } else {
                        var tip = typeof obj.options.tips == 'string' ? obj.options.tips : obj.options.tips[len - 1];
                        tip = tip.replace(/{{[\s]*lq-score[\s]*}}/g, len);
                        $tipEle.html(tip).css("color", (obj.mouseoverColor instanceof Array) ? obj.mouseoverColor[len - 1] : obj.mouseoverColor);
                    }
                }
            });
            $liEle.on("mouseout", function () {
                if (!obj.options.isReScore && obj.isScoreFinish) {
                    return;
                }
                $(this).prevAll("li").add(this).css("color", obj.options.defultColor);
                if ($tipEle) {
                    $tipEle.html(obj.options.zeroTip).css("color", obj.options.defultColor);
                }
                if (obj.options.score != null) {
                    obj.setScore();
                }
            });
            $liEle.on("click", function () {
                if (!obj.options.isReScore && obj.isScoreFinish) {
                    return;
                }
                var len = $(this).prevAll("li").add(this).length;
                $(this).prevAll("li").add(this).css("color", (obj.options.selectColor instanceof Array) ? obj.options.selectColor[len - 1] : obj.options.selectColor);
                if ($tipEle) {
                    if (obj.options.tips === "default") {
                        $tipEle.html(len + "分").css("color", (obj.mouseoverColor instanceof Array) ? obj.mouseoverColor[len - 1] : obj.mouseoverColor);
                    } else {
                        var tip = typeof obj.options.tips == 'string' ? obj.options.tips : obj.options.tips[len - 1];
                        tip = tip.replace(/{{[\s]*lq-score[\s]*}}/g, len);
                        $tipEle.html(tip).css("color", (obj.options.selectColor instanceof Array) ? obj.options.selectColor[len - 1] : obj.options.selectColor);
                    }
                }
                obj.options.score = len;
                $(this).nextAll("li").css("color", obj.options.defultColor);
                obj.isScoreFinish = true;
                if (!obj.options.isReScore) {
                    $liEle.css("cursor", "default");
                }
                if (obj.options.callBack) {
                    obj.options.callBack(obj.options.score, obj.$element);
                }
            });
            return this.$element;
        },
        createMouseoverColor: function (selectColor) {
            if (typeof selectColor == 'string' && selectColor.constructor == String) {
                this.mouseoverColor = this.changeColor(selectColor);
            } else if (typeof selectColor == 'object' && selectColor.constructor == Array) {
                this.mouseoverColor = [];
                for (var i=0;i<selectColor.length;i++) {
                    this.mouseoverColor.push(this.changeColor(selectColor[i]));
                }
            }
        },
        changeColor: function (colorStr) {
            if (/^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$/g.test(colorStr)) {
                //十六进制
                var lowColor = colorStr.toLowerCase();
                if (lowColor.length === 4) {
                    var tempColr = "#";
                    for (var i = 1; i < 4; i++) {
                        tempColor += lowColor.slice(i, i + 1).concat(lowColor.slice(i, i + 1));
                    }
                    lowColor = tempColor;
                }
                var colorChange = [];
                var rgb = ['r', 'g', 'b'];
                for (var i = 1, k = 0; i < 7; i += 2, k++) {
                    var c = parseInt("0x" + lowColor.slice(i, i + 2));
                    c = this.shallowColor(c, rgb[k]);
                    colorChange.push(c);
                }
                return "rgba(" + colorChange.join(",") + ",1)";
            } else if (/^[rR][gG][Bb][Aa][\(]([\s]*(2[0-4][0-9]|25[0-5]|[01]?[0-9][0-9]?),){2}[\s]*(2[0-4][0-9]|25[0-5]|[01]?[0-9][0-9]?),?[\s]*(0\.\d{1,2}|1|0)?[\)]{1}$/g.test(colorStr)) {
                //rgba
                var colors = colorStr.match(/[0-9]+/g);
                var rgb = ['r', 'g', 'b'];
                for (var i = 0; i < 3; i++) {
                    colors[i] = this.shallowColor(parseInt(colors[i]), rgb[i]);
                }
                return "rgba(" + colors.join(",") + ")";
            } else if (/^[rR][gG][Bb][\(]([\s]*(2[0-4][0-9]|25[0-5]|[01]?[0-9][0-9]?),){2}[\s]*(2[0-4][0-9]|25[0-5]|[01]?[0-9][0-9]?)[\)]{1}$/g.test(colorStr)) {
                //rgb
                var colors = colorStr.match(/[0-9]+/g);
                var rgb = ['r', 'g', 'b'];
                for (var i = 0; i < 3; i++) {
                    colors[i] = this.shallowColor(parseInt(colors[i]), rgb[i]);
                }
                return "rgba(" + colors.join(",") + ",1)";
            }
        },
        //单色变浅
        shallowColor: function (colorNum, flag) {
            switch (flag) {
                case "r":
                    return colorNum + 15;
                case "g":
                    return colorNum + 20;
                case "b":
                    return colorNum + 25;
                default:
                    return colorNum;
            }
        },
        setScore: function () {
            var $liEle = this.$element.children("ul").children("li");
            if (!this.options.isReScore) {
                $liEle.css("cursor", "default");
            }
            var showLen;
            if (this.options.score < 1 && this.options.score > 0) {
                showLen = 1;
            } else {
                showLen = Math.round(this.options.score);
            }
            if (this.options.score == 0) {
                if (this.options.$tipEle != null) {
                    this.options.$tipEle.html(this.options.zeroTip).css("color", this.options.defultColor);
                }
                return;
            }
            $liEle.slice(0, showLen).css("color", this.options.selectColor[showLen - 1]);
            if (this.options.$tipEle != null) {
                if (this.options.tips === "default") {
                    this.options.$tipEle.html(this.options.score + "分").css("color", this.options.selectColor[showLen - 1]);
                } else {
                    var tip = typeof this.options.tips == 'string' ? this.options.tips : this.options.tips[showLen - 1];
                    tip = tip.replace(/{{[\s]*lq-score[\s]*}}/g, this.options.score);
                    this.options.$tipEle.html(tip).css("color", this.options.selectColor[showLen - 1]);
                }
            }
            if (!this.options.isReScore && this.options.callBack != null) {
                this.options.callBack(this.options.score, this.$element);
            }
        }
    }
    $.fn.lqScore = function (options) {
        var lqScore = new LQScore(this, options);
        return lqScore.init();
    }
})(jQuery);



// 评论
(function($){
    function crateCommentInfo(obj){
        /*
         * <div class="comment-info">
            <header><img src="./images/img.jpg"></header>
            <div class="comment-right">
                <h3>匿名</h3>
                <div class="comment-content-header"><span><i class="glyphicon glyphicon-time"></i> 2017-10-17 11:42:53</span><span><i class="glyphicon glyphicon-map-marker"></i>深圳</span></div>
                <p class="content">mongodb 副本集配置副本集概念：就我的理解就是和主从复制 差不多，就是在主从复制的基础上多加了一个选举的机制。
                复制集 特点：数据一致性 主是唯一的，没有Mysql 那样的双主结构大多数原则，集群存活节点小于二分之一是集群不可写，
                只可读从库无法写入数据自动容灾通过下面的一个图来简单的了解下
                 配置过程：一、安装mongodb安装过程略，不懂得可以看前面的教程二、创建存储目录与配置文件创...</p>
                <div class="comment-content-footer">
                    <div class="row">
                        <div class="col-md-10">
                            <span><i class="glyphicon glyphicon-pushpin"></i> 来自:win10 </span><span><i class="glyphicon glyphicon-globe"></i> chrome 55.0.2883.87</span>
                        </div>
                        <div class="col-md-2"><span class="reply-btn">回复</span></div>
                    </div>
                </div>
                <div class="reply-list">
                    <div class="reply">
                        <div><a href="javascript:void(0)">匿名</a>:<a href="javascript:void(0)">@匿名</a><span>这写的是什么鬼东西。。。。</span></div>
                        <p><span>2017-10-17 11:42:53</span> <span class="reply-list-btn">回复</span></p>
                    </div>
                </div>
            </div>
        </div>
         * */

        if(typeof(obj.time) == "undefined" || obj.time == ""){
            obj.time = getNowDateFormat();
        }

        var el = "<div class='comment-info'><header><img src='"+obj.img+"'></header><div class='comment-right'><h3>"+obj.replyName+"</h3>"
            +"<div class='comment-content-header'><span><i class='glyphicon glyphicon-time'></i>"+obj.time+"</span>";

        if(typeof(obj.address) != "undefined" && obj.browse != ""){
            el =el+"<span><i class='glyphicon glyphicon-map-marker'></i>"+obj.address+"</span>";
        }
        el = el+"</div><p class='content'>"+obj.content+"</p><div class='comment-content-footer'><div class='row'><div class='col-md-10'>";

        if(typeof(obj.osname) != "undefined" && obj.osname != ""){
            el =el+"<span><i class='glyphicon glyphicon-pushpin'></i> 来自:"+obj.osname+"</span>";
        }

        if(typeof(obj.browse) != "undefined" && obj.browse != ""){
            el = el + "<span><i class='glyphicon glyphicon-globe'></i> "+obj.browse+"</span>";
        }

        el = el + "</div><div class='col-md-2'><span class='reply-btn'>回复</span></div></div></div><div class='reply-list'>";
        if(obj.replyBody != "" && obj.replyBody.length > 0){
            var arr = obj.replyBody;
            for(var j=0;j<arr.length;j++){
                var replyObj = arr[j];
                el = el+createReplyComment(replyObj);
            }
        }
        el = el+"</div></div></div>";
        return el;
    }

    //返回每个回复体内容
    function createReplyComment(reply){
        var replyEl = "<div class='reply'><div><a href='javascript:void(0)' class='replyname'>"+reply.replyName+"</a>:<a href='javascript:void(0)'>@"+reply.beReplyName+"</a><span>"+reply.content+"</span></div>"
            + "<p><span>"+reply.time+"</span> <span class='reply-list-btn'>回复</span></p></div>";
        return replyEl;
    }
    function getNowDateFormat(){
        var nowDate = new Date();
        var year = nowDate.getFullYear();
        var month = filterNum(nowDate.getMonth()+1);
        var day = filterNum(nowDate.getDate());
        var hours = filterNum(nowDate.getHours());
        var min = filterNum(nowDate.getMinutes());
        var seconds = filterNum(nowDate.getSeconds());
        return year+"-"+month+"-"+day+" "+hours+":"+min+":"+seconds;
    }
    function filterNum(num){
        if(num < 10){
            return "0"+num;
        }else{
            return num;
        }
    }
    function replyClick(el){
        el.parent().parent().append("<div class='replybox'><textarea cols='80' rows='50' placeholder='来说几句吧......' class='mytextarea' ></textarea><span class='send'>发送</span></div>")
            .find(".send").click(function(){
            var content = $(this).prev().val();
            if(content != ""){
                var parentEl = $(this).parent().parent().parent().parent();
                var obj = new Object();
                obj.replyName="匿名";
                if(el.parent().parent().hasClass("reply")){
                    console.log("1111");
                    obj.beReplyName = el.parent().parent().find("a:first").text();
                }else{
                    console.log("2222");
                    obj.beReplyName=parentEl.find("h3").text();
                }
                obj.content=content;
                obj.time = getNowDateFormat();
                var replyString = createReplyComment(obj);
                $(".replybox").remove();
                parentEl.find(".reply-list").append(replyString).find(".reply-list-btn:last").click(function(){alert("不能回复自己");});
            }else{
                alert("空内容");
            }
        });
    }


    $.fn.addCommentList=function(options){
        var defaults = {
            data:[],
            add:""
        }
        var option = $.extend(defaults, options);
        //加载数据
        if(option.data.length > 0){
            var dataList = option.data;
            var totalString = "";
            for(var i=0;i<dataList.length;i++){
                var obj = dataList[i];
                var objString = crateCommentInfo(obj);
                totalString = totalString+objString;
            }
            $(this).append(totalString).find(".reply-btn").click(function(){
                if($(this).parent().parent().find(".replybox").length > 0){
                    $(".replybox").remove();
                }else{
                    $(".replybox").remove();
                    replyClick($(this));
                }
            });
            $(".reply-list-btn").click(function(){
                if($(this).parent().parent().find(".replybox").length > 0){
                    $(".replybox").remove();
                }else{
                    $(".replybox").remove();
                    replyClick($(this));
                }
            })
        }

        //添加新数据
        if(option.add != ""){
            obj = option.add;
            var str = crateCommentInfo(obj);
            $(this).prepend(str).find(".reply-btn").click(function(){
                replyClick($(this));
            });
        }
    }


})(jQuery);