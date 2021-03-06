package hudson.plugins.locale;

import hudson.Plugin;
import jenkins.model.Jenkins;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Locale;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class LocaleFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // nop
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            PluginImpl plugin = (PluginImpl) Jenkins.getInstance().getPlugin("locale");
            if (plugin.isIgnoreAcceptLanguage())
                request = new HttpServletRequestWrapper((HttpServletRequest)request) {
                    @Override
                    public Locale getLocale() {
                        // Force locale to configured default, ignore request' Accept-Language header
                        return Locale.getDefault();
                    }
                };
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        // nop
    }
}
