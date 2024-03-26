$(function (){
    $("#signup").click(function (){
        console.log($("#textInput3").val())
        if($("#textInput3").val()  == "")
        {
            alert("用户名没填")
            return
        }
        if ($("#textInput1").val()==$("#textInput2").val()&&  $("#textInput1").val() != "")
        {
            var formData = $("#myForm").serialize();
            $.ajax({
                // URL
                url: 'http://localhost:8080/web_war_exploded/com/demo/controller/index/Signup',
                // 参数
                data: formData,
                // 请求类型
                type: 'POST',
                // 期望的返回数据类型，请根据实际情况修改
                dataType: 'json',
                success: function (data) {
                    console.log(data.rows[0].username)
                    console.log(data.rows[0].userID)
                    if (data.total==1)
                    {
                        var str=""
                        str+="用户名："+data.rows[0].name+"\n"+
                            "账户："+data.rows[0].username+"\n"+
                            "密码；"+data.rows[0].password;
                        alert(str)
                        window.location.href = "/web_war_exploded/html/login/login.html"
                    }
                    else
                        alert("异常")
                    //window.history.back();
                    // window.location.href = "/web_war_exploded/html/main.html"
                },
                timeout:2000,
                error:function (){
                    alert("网络延迟")

                }
            });
        }
      else {
          alert("两次密码不一致")
        }

    })
})