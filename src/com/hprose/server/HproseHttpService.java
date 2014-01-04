/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.net/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * HproseHttpService.java                                 *
 *                                                        *
 * hprose http service class for Java.                    *
 *                                                        *
 * LastModified: May 12, 2011                             *
 * Author: Ma Bingyao <andot@hprfc.com>                   *
 *                                                        *
\**********************************************************/
package com.hprose.server;

import com.hprose.common.HproseMethods;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Type;

public class HproseHttpService extends HproseService {
    private boolean crossDomainEnabled = false;
    private boolean p3pEnabled = false;
    private boolean getEnabled = true;
    private static ThreadLocal<HttpContext> context = new ThreadLocal<HttpContext>();
    private static ThreadLocal<OutputStream> output = new ThreadLocal<OutputStream>();
    private static ThreadLocal<InputStream> input = new ThreadLocal<InputStream>();

    public static HttpContext getCurrentContext() {
        return context.get();
    }

    @Override
    public HproseMethods getGlobalMethods() {
        if (globalMethods == null) {
            globalMethods = new HproseHttpMethods();
        }
        return globalMethods;
    }

    @Override
    public void setGlobalMethods(HproseMethods methods) {
        if (methods instanceof HproseHttpMethods) {
            this.globalMethods = methods;
        }
        else {
            throw new ClassCastException("methods must be a HproseHttpMethods instance");
        }
    }
    
    public boolean isCrossDomainEnabled() {
        return crossDomainEnabled;
    }

    public void setCrossDomainEnabled(boolean enabled) {
        crossDomainEnabled = enabled;
    }    

    public boolean isP3pEnabled() {
        return p3pEnabled;
    }

    public void setP3pEnabled(boolean enabled) {
        p3pEnabled = enabled;
    }

    public boolean isGetEnabled() {
        return getEnabled;
    }

    public void setGetEnabled(boolean enabled) {
        getEnabled = enabled;
    }

    protected OutputStream getOutputStream() throws IOException {
        OutputStream ostream = output.get();
        if (ostream == null) {
            ostream = new BufferedOutputStream(getCurrentContext().getResponse().getOutputStream());
            output.set(ostream);
        }
        return ostream;
    }

    protected InputStream getInputStream() throws IOException {
        InputStream istream = input.get();
        if (istream == null) {
            istream = new BufferedInputStream(getCurrentContext().getRequest().getInputStream());
            input.set(istream);
        }
        return istream;
    }

    @Override
    protected Object[] fixArguments(Type[] argumentTypes, Object[] arguments, int count) {
        if (argumentTypes.length != count) {
            Object[] args = new Object[argumentTypes.length];
            System.arraycopy(arguments, 0, args, 0, count);
            Class<?> argType = (Class<?>) argumentTypes[count];
            HttpContext httpContext = getCurrentContext();
            if (argType.equals(HttpContext.class)) {
                args[count] = httpContext;
            }
            else if (argType.equals(HttpServletRequest.class)) {
                args[count] = httpContext.getRequest();
            }
            else if (argType.equals(HttpServletResponse.class)) {
                args[count] = httpContext.getResponse();
            }
            else if (argType.equals(HttpSession.class)) {
                args[count] = httpContext.getSession();
            }
            else if (argType.equals(ServletContext.class)) {
                args[count] = httpContext.getApplication();
            }
            else if (argType.equals(ServletConfig.class)) {
                args[count] = httpContext.getConfig();
            }
            return args;
        }
        return arguments;
    }

    protected void sendHeader() throws IOException {
        super.sendHeader();
        HttpServletRequest request = getCurrentContext().getRequest();
        HttpServletResponse response = getCurrentContext().getResponse();
        response.setContentType("text/plain");
        if (p3pEnabled) {
            response.setHeader("P3P", "CP=\"CAO DSP COR CUR ADM DEV TAI PSA PSD " +
                                      "IVAi IVDi CONi TELo OTPi OUR DELi SAMi " +
                                      "OTRi UNRi PUBi IND PHY ONL UNI PUR FIN " +
                                      "COM NAV INT DEM CNT STA POL HEA PRE GOV\"");
        }
        if (crossDomainEnabled) {
            String origin = request.getHeader("Origin");
            if (origin != null && !origin.equals("null")) {
                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Credentials", "true");  
            }
            else {
                response.setHeader("Access-Control-Allow-Origin", "*");
            }
        }
    }

    public void handle(HttpContext httpContext) throws IOException {
        handle(httpContext, null);
    }
    public void handle(HttpContext httpContext, HproseHttpMethods methods) throws IOException {
        try {
            context.set(httpContext);
            String method = httpContext.getRequest().getMethod();
            if (method.equals("GET") && getEnabled) {
                doFunctionList(methods);
            }
            else if (method.equals("POST")) {
                super.handle(methods);
            }
            else {
                httpContext.getResponse().sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        }
        finally {
            output.remove();
            input.remove();
            context.remove();
        }
    }
}