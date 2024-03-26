$(function (){
    // Cookies.remove('name') // fail!
    // Cookies.set("username","123456")
    // Cookies.set("name","admin")
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
            $("#login").text(Cookies.get("name"))
        }


    }

})