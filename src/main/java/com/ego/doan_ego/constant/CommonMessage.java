package com.ego.doan_ego.constant;

public enum CommonMessage {
    SUCCESS(0, "{} thành công"),
    FAILED(-1, "{} không thành công"),
    USERNAME_INVALID(100000, "Tài khoản hoặc mật khẩu không đúng"),
    USER_NOT_FOUND(100001, "Người dùng không tồn tại"),
    USER_DONT_HAVE_PERMISSION(100002, "Tài khoản của bạn không đủ quyền hạn"),
    OBJECT_EXIST(100003, "{} đã tồn tại. Vui lòng thử lại"),
    USER_EXISTED(100004, "Người dùng đã tồn tại"),
    GROUP_DOES_NOT_EXIST(100005, "Group không tồn tại");


    public int code;
    public String message;

    CommonMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    CommonMessage(int code) {
        this.code = code;
    }

}
