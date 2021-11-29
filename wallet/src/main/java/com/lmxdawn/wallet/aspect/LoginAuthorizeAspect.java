package com.lmxdawn.wallet.aspect;

import com.lmxdawn.dubboapi.service.user.UserDubboService;
import com.lmxdawn.wallet.annotation.LoginAuthAnnotation;
import com.lmxdawn.wallet.enums.ResultEnum;
import com.lmxdawn.wallet.exception.JsonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 登录验证 AOP
 */
@Aspect
@Component
@Slf4j
public class LoginAuthorizeAspect {

    @DubboReference
    private UserDubboService userDubboService;

    @Pointcut("@annotation(com.lmxdawn.wallet.annotation.LoginAuthAnnotation)")
    public void loginVerify() {
    }

    /**
     * 登录验证
     *
     * @param joinPoint
     */
    @Before("loginVerify()")
    public void doLoginAuthVerify(JoinPoint joinPoint) {
        // 判断是否进行需要登录
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //从切面中获取当前方法
        Method method = signature.getMethod();
        //得到了方,提取出他的注解
        LoginAuthAnnotation action = method.getAnnotation(LoginAuthAnnotation.class);
        boolean login = action.isLogin();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            if (login) {
                throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
            }
        }
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("x-token");
        if (token == null) {
            if (login) {
                throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
            }
        }

        Long memberId = userDubboService.Login(token);
        if (memberId == null || memberId <= 0) {
            if (login) {
                throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
            }
        }

        // 设置
        request.setAttribute("memberId", memberId == null ? 0 : memberId);
    }

}
