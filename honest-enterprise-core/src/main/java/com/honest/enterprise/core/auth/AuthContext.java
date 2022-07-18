package com.honest.enterprise.core.auth;



import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * A context holder class for holding the current userId and authz info
 * @author fanjie
 */
public class AuthContext {

    private static String getRequestHeader(String headerName) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String value = request.getHeader(headerName);
            return value;
        }
        return null;
    }

    public static Integer getUserId() {
        String userId = getRequestHeader(AuthConstant.CURRENT_USER_HEADER_ID);
        if (Objects.nonNull(userId)) {
            return Integer.parseInt(userId);
        }
        return 0;
    }

    public static String getUserName() {
        String userName = getRequestHeader(AuthConstant.CURRENT_USER_HEADER_HETOKEN);
        return userName;
    }


}
