/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.peasant.util.web;

import java.io.IOException;
import javax.persistence.FieldResult;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 对所有请求及响应字符编码设置为统一编码，默认为UTF-8，可以web.xml中通过设置此filter的“encoding”参数改变字符编码集
 * 
 * @author 谢金光
 */
public class CharacterEncodingFilter implements Filter {

    public final static String CHARACTER_ENCODING_PARAM = "encoding";
    String encoding = "UTF-8";
    FilterConfig fc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
        String ec = fc.getInitParameter(CHARACTER_ENCODING_PARAM);
        if (ec != null && !(ec.isEmpty())) {
            this.encoding = ec;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(this.encoding);
        response.setCharacterEncoding(this.encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.encoding = null;
        this.fc = null;
    }

}
