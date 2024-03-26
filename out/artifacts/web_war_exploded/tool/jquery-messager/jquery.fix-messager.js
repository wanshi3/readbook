    ;(function($) {
    $.extend({
        "showMessage":function(message){

            var $divToRemove = $('.jquery-fix-messager');
            if($divToRemove.length > 0)
                $divToRemove.remove();

            var messagerDom = '<div class="jquery-fix-messager"><div>';
            $(document.body).append(messagerDom);
            var $messageDiv = $('.jquery-fix-messager');
            $messageDiv.hide(0);
            //message = '<span class="am-text-danger">&#xf00d;</span> ' + message;
            $messageDiv.html(message);
            $messageDiv.fadeIn(500).delay(1000);

            $messageDiv.fadeOut(500);
        },

        "showSuccess":function(message) {
            var $divToRemove = $('.jquery-fix-messager');
            if($divToRemove.length > 0)
                $divToRemove.remove();

            var messagerDom = '<div class="jquery-fix-messager"><div>';
            $(document.body).append(messagerDom);
            var $messageDiv = $('.jquery-fix-messager');
            $messageDiv.hide(0);
            //message = '<span class="jquery-fix-messager-success">&#xf058;</span> ' + message;
            $messageDiv.html(message);
            $messageDiv.fadeIn(500).delay(1000);

            $messageDiv.fadeOut(500);
        },
        
        "showWarning": function (message) {
            var $divToRemove = $('.jquery-fix-messager');
            if($divToRemove.length > 0)
                $divToRemove.remove();

            var messagerDom = '<div class="jquery-fix-messager"><div>';
            $(document.body).append(messagerDom);
            var $messageDiv = $('.jquery-fix-messager');
            $messageDiv.hide(0);
            //message = '<span class="jquery-fix-messager-warning">&#xf071;</span> ' + message;
            $messageDiv.html(message);
            $messageDiv.fadeIn(500).delay(1000);

            $messageDiv.fadeOut(500);
        },

        "showError" : function(message) {
            var $divToRemove = $('.jquery-fix-messager');
            if($divToRemove.length > 0)
                $divToRemove.remove();

            var messagerDom = '<div class="jquery-fix-messager"><div>';
            $(document.body).append(messagerDom);
            var $messageDiv = $('.jquery-fix-messager');
            $messageDiv.hide(0);
            //message = '<span class="am-text-danger">&#xf00d;</span> ' + message;
            $messageDiv.html(message);
            $messageDiv.fadeIn(500).delay(1000);

            $messageDiv.fadeOut(500);
        }
    });
})(jQuery);