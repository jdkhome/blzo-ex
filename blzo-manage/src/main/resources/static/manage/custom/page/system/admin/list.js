"use strict";

(function ($, request) {

    function AdminList() {
        this.dom = {};
    }

    AdminList.prototype = {
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


            $('.admin-edit').click(function (event) {

                $(this).parents('tr').find('.admin-nickName-input:first').removeClass('hidden');
                $(this).parents('tr').find('.admin-username-input:first').removeClass('hidden');
                $(this).parents('tr').find('.admin-phone-input:first').removeClass('hidden');
                $(this).parents('tr').find('.admin-status-select:first').removeClass('hidden');
                $(this).parents('tr').find('.admin-remark-input:first').removeClass('hidden');
                $(this).parents('tr').find('.admin-save:first').removeClass('hidden');

                $(this).parents('tr').find('.admin-nickName-span:first').addClass('hidden');
                $(this).parents('tr').find('.admin-username-span:first').addClass('hidden');
                $(this).parents('tr').find('.admin-phone-span:first').addClass('hidden');
                $(this).parents('tr').find('.admin-status-span:first').addClass('hidden');
                $(this).parents('tr').find('.admin-remark-span:first').addClass('hidden');
                $(this).parents('tr').find('.admin-edit:first').addClass('hidden');

            });

            $('.admin-save').click(function (event) {
                var obj = {
                    "adminId": $(this).data('id'),
                    "username": $(this).parents('tr').find('.admin-username-input:first').val(),
                    "nickName": $(this).parents('tr').find('.admin-nickName-input:first').val(),
                    "phone": $(this).parents('tr').find('.admin-phone-input:first').val(),
                    "status": $(this).parents('tr').find('.admin-status-select:first').val(),
                    "remark": $(this).parents('tr').find('.admin-remark-input:first').val()
                };

                request.apiSystemAdminEdit($(document), obj, 'adminEdit');
            });


            $('#admin-add').click(function (event) {
                var obj = {
                    "username": $('#add-username').val(),
                    "password": $('#add-password').val(),
                    "nickName": $('#add-nickName').val(),
                    "phone": $('#add-phone').val(),
                    "email": $('#add-email').val(),
                    "remark": $('#add-remark').val(),
                    "organizeId": $('#add-organizeId').val()
                };
                request.apiSystemAdminAdd($(document), obj, 'api-with-sync');
            });

            $(document).on('adminEdit', function (msg, data) {
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


            $('.delete').click(function (event) {
                var adminId = $(this).data('id');
                console.log(adminId);
                request.apiSystemAdminDel($(document), {
                    adminId: adminId
                }, 'adminList');
            });
            $(document).on('adminList', function (msg, data) {
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
        }
    };

    window.controller.AdminList = AdminList;

})(jQuery, controller.Request);