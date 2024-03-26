$(function (){
    // Cookies.remove('name') // fail!
    // Cookies.remove("username")
    // Cookies.remove("name")
    if (typeof Cookies.get("name") == 'undefined') {
        $.showMessage("请登入")
    }
    else
    {
        if (Cookies.get("username")=="admin")
        {
            window.location.href = "/web_war_exploded/html/admain/admain.html"
        }
        else {
            $("#me").text(Cookies.get("name"))

        }

    }
    $("#res").click(function (){
        Cookies.remove("userID")
        Cookies.remove("username")
        Cookies.remove("name")
        $("#me").text("请登入")

    })

})