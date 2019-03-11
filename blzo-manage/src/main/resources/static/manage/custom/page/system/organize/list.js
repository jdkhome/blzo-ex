"use strict";

(function ($, request) {

    function OrganizeList() {
        this.dom = {};
    }

    OrganizeList.prototype = {
        init: function () {
            var _this = this;

            // === 响应事件 === //
            // 同步响应=> 成功后自动刷新页面
            $(document).on('api-with-sync', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: data.msg
                    }).show();
                } else {
                    new NotificationFx({
                        message: data.msg,
                        onClose: function () {
                            location.reload();
                        }
                    }).show();
                }
            });

            // 非同步响应=> 如果失败则刷新页面
            $(document).on('api-without-sync', function (msg, data) {
                if (data.code != 200) {
                    new NotificationFx({
                        message: "失去同步:" + data.msg,
                        onClose: function () {
                            location.reload();
                        }
                    }).show();
                }
            });



        }
    };

    window.controller.OrganizeList = OrganizeList;

})(jQuery, controller.Request);