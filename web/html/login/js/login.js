

$(function (){
    $("#login").click(function (){
        var formData = $("#myForm").serialize();
        $.ajax({
            // URL
            url: 'http://localhost:8080/web_war_exploded/com/demo/controller/index/Login',
            // 参数
            data: formData,
            // 请求类型
            type: 'POST',
            // 期望的返回数据类型，请根据实际情况修改
            dataType: 'json',
            success: function (data) {

                if(data.success)
                {
                    if (data.rows.length==1)
                        {
                            alert(data.msg)
                            Cookies.set('userID', data.rows[0].userID, { expires: 7 })
                            Cookies.set('username', data.rows[0].username, { expires: 7 })
                            Cookies.set('name', data.rows[0].name, { expires: 7 })
                            if (data.rows[0].username=="admin")
                            {
                                window.location.href = "/web_war_exploded/html/main.html"
                            }
                            else
                            {
                                window.history.back();
                            }

                        }
                    else {
                        alert(data.msg)
                    }
                }

                else {
                    $.showMessage("网络异常")
                }
                //window.history.back();
                //  window.location.href = "/web_war_exploded/html/main.html"
            },
            timeout:2000,
            error:function (){
                alert("网络延迟")

            }
        });

    })
})
// Cookies.set('name', 'value', { expires: 7 })
// console.log(Cookies.get('name')) // => 'value'