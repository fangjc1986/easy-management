package com.fangjc1986.support.helper;

import com.fangjc1986.ezmpro.sys.service.impl.RequestLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "callbackFilter")
public class ResponseFilter implements Filter {

    @Autowired
    private RequestLogServiceImpl requestLogService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        MyResponseWrapper responseWrapper = new MyResponseWrapper((HttpServletResponse) response);
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        filterChain.doFilter(request, responseWrapper);
        byte[] content = responseWrapper.getContent();
        String str = new String(content, "UTF-8");
        requestLogService.updateByRequest(str, 1);
        ServletOutputStream out = response.getOutputStream();
        out.write(content);
    }


    private class MyResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayOutputStream buffer;

        private ServletOutputStream out;

        public MyResponseWrapper(HttpServletResponse httpServletResponse) {
            super(httpServletResponse);
            buffer = new ByteArrayOutputStream();
            out = new WrapperOutputStream(buffer);
        }

        @Override
        public ServletOutputStream getOutputStream()
            throws IOException {
            return out;
        }

        @Override
        public void flushBuffer()
            throws IOException {
            if (out != null) {
                out.flush();
            }
        }

        public byte[] getContent()
            throws IOException {
            flushBuffer();
            return buffer.toByteArray();
        }

        class WrapperOutputStream extends ServletOutputStream {
            private ByteArrayOutputStream bos;

            public WrapperOutputStream(ByteArrayOutputStream bos) {
                this.bos = bos;
            }

            @Override
            public void write(int b)
                throws IOException {
                bos.write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener arg0) {
            }
        }
    }

    @Override
    public void init(FilterConfig arg0)
        throws ServletException {
    }

    @Override
    public void destroy() {
    }

}