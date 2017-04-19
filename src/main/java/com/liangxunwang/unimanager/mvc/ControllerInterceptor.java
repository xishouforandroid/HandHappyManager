package com.liangxunwang.unimanager.mvc;

import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 */
public class ControllerInterceptor extends ControllerConstants implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        Object account = request.getSession().getAttribute(ACCOUNT_KEY);

        if(uri.contains(".json")){
                return true;
        }

        if(uri.matches("(^/$)|(^/index\\.do$)|(^/adminLogin\\.do$)|(^/logout\\.do$)" +
                        "|(^/uploadImage\\.do$)" +
                        "|(^/uploadFileXls\\.do$)" +
                        "|(^/appLogin\\.do$)" +
                        "|(^/appReg\\.do$)" +
                        "|(^/appUpdateCover\\.do$)" +
                        "|(^/token\\.do$)" +
                        "|(^/appProvinces\\.do$)" +
                        "|(^/appCitys\\.do$)" +
                        "|(^/appLikes\\.do$)" +
                        "|(^/appUpdateProfile\\.do$)" +
                        "|(^/appLikesBylikeIds\\.do$)" +
                        "|(^/getAllCitys\\.do$)" +
                        "|(^/appAboutUs\\.do$)" +
                        "|(^/getVersionCode\\.do$)" +
                        "|(^/appSaveSuggest\\.do$)" +
                        "|(^/appSaveReport\\.do$)" +
                        "|(^/appUpdateMoible\\.do$)" +
                        "|(^/appUpdatePwrById\\.do$)" +
                        "|(^/appUpdatePwrByMobile\\.do$)" +
                        "|(^/appUpdateCard\\.do$)" +

                        "|(^/payWxHyNotifyAction\\.do$)" +
                        "|(^/payWxCxNotifyAction\\.do$)" +
                        "|(^/orderSave\\.do$)" +
                        "|(^/orderSaveWx\\.do$)" +
                        "|(^/orderUpdate\\.do$)" +
                        "|(^/payZfbHyNotifyAction\\.do$)" +
                        "|(^/payZfbCxNotifyAction\\.do$)" +

                        "|(^/appSaveOrUpdatePhotos\\.do$)" +
                        "|(^/appPhotos\\.do$)" +

                        "|(^/appTuijianPeoples\\.do$)" +

                        "|(^/uploadUnCompressImage\\.do$)"

        ) || account != null) {
            return true;
        }

        if("true".equals(request.getParameter("j"))) {
            PrintWriter out = response.getWriter();
            out.print(toJSONString(TIMEOUT));
            out.close();
        } else {
            request.getRequestDispatcher("/WEB-INF/session.jsp").forward(request, response);
            return false;
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
