package cinemahouse.project.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static String getIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");// lấy ra địa chỉ ip
        if (xForwardedForHeader == null) {
            var remoteAddr = request.getRemoteAddr();
            if (remoteAddr == null) {
                remoteAddr = "127.0.0.1"; // TODO: the IP of this BE app
            }
            return remoteAddr;
        }
        return xForwardedForHeader.split(",")[0].trim();
    }
}