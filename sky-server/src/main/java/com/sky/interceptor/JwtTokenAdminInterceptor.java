package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt token interceptor
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * validate jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //check if we handled controller method or other resources
        if (!(handler instanceof HandlerMethod)) {
            //if we didn't get dynamic method, let go
            return true;
        }

        //1. get token from request header
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //2. validate token
        try {
            log.info("jwt validate:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            // set localThread variable;
            BaseContext.setCurrentId(empId);
            log.info("employee id：{}", empId);
            //3. valid, let go
            return true;
        } catch (Exception ex) {
            //4. invalid, return 401 code
            response.setStatus(401);
            return false;
        }
    }
}
