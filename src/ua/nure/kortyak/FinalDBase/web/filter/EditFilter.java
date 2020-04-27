package ua.nure.kortyak.FinalDBase.web.filter;

import org.apache.log4j.Logger;
import ua.nure.kortyak.FinalDBase.Path;
import ua.nure.kortyak.FinalDBase.db.entity.Role;
import ua.nure.kortyak.FinalDBase.db.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security filter. Disabled by default. Check
 * whether somebody has permissions to use uri.
 *
 * @author E.Kortyk
 */
public class EditFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOG.debug("Filter initialization starts");
        // no op
        LOG.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);

        User user = (User) session.getAttribute("user");
        LOG.trace("Get a session attribute: user --> " + user);

        if (user != null && Role.getRole(user) == Role.ADMIN) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            LOG.trace("User is logged --> " + user);
            servletRequest.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                    .forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        LOG.debug("Filter destruction starts");
        // no op
        LOG.debug("Filter destruction finished");
    }
}
