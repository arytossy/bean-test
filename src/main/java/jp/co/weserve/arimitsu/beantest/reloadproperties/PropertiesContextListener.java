package jp.co.weserve.arimitsu.beantest.reloadproperties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class PropertiesContextListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        ServletRequest request = event.getServletRequest();
        if (request instanceof HttpServletRequest) {
            int contextId = PropertiesContextHolder.getCurrentId();
            request.setAttribute(PropertiesScope.ATTRIBUTE_NAME, contextId);
            PropertiesContextHolder.startRequest(contextId);
        } else {
            throw new IllegalArgumentException("HttpServletRequest is expected, but actual is: " + request);
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        ServletRequest request = event.getServletRequest();
        if (request instanceof HttpServletRequest) {
            Object contextId = request.getAttribute(PropertiesScope.ATTRIBUTE_NAME);
            if (contextId instanceof Integer) {
                PropertiesContextHolder.endRequest((int)contextId);
            }
        }
    }
}
