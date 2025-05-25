package controller;

import models.User;

public class SessionManager {
    private static User currentUser;

    // Gọi khi đăng nhập thành công
    public static void login(User user) {
        currentUser = user;
    }

    // Gọi khi đăng xuất
    public static void logout() {
        currentUser = null;
    }

    // Trả về user hiện tại nếu đã đăng nhập
    public static User getCurrentUser() {
        return currentUser;
    }

    // Kiểm tra có đang đăng nhập hay không
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // Cập nhật thông tin user đang đăng nhập (sau khi thay đổi profile, mật khẩu, v.v.)
    public static void updateCurrentUser(User updatedUser) {
        if (isLoggedIn() && currentUser.getId() == updatedUser.getId()) {
            currentUser = updatedUser;
        }
    }
}
