package com.sendyago.util.common;

import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.impl.DefaultScriptSessionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 * 消息推送中，首先加载用户登陆唯一标识
 *
 * @author Faraway 20130315
 */
public class DwrScriptSessionManagerUtil extends DefaultScriptSessionManager {

    public DwrScriptSessionManagerUtil() {
        addScriptSessionListener(new ScriptSessionListener() {

            public void sessionCreated(ScriptSessionEvent ev) {

                HttpSession session = WebContextFactory.get().getSession();

                String userId = ((String) session.getAttribute("login_id"));


                ev.getSession().setAttribute("userId", userId);

            }

            public void sessionDestroyed(ScriptSessionEvent ev) {


            }

        });
    }

    /**
     * 初始化用户唯一标识
     */
    public void init() throws ServletException {

        Container container = ServerContextFactory.get().getContainer();

        ScriptSessionManager manager = container
                .getBean(ScriptSessionManager.class);

        ScriptSessionListener listener = new ScriptSessionListener() {

            public void sessionCreated(ScriptSessionEvent ev) {

                HttpSession session = WebContextFactory.get().getSession();

                String userId = ((String) session.getAttribute("login_id"));


                ev.getSession().setAttribute("userId", userId);

            }

            public void sessionDestroyed(ScriptSessionEvent ev) {


            }

        };

        manager.addScriptSessionListener(listener);

    }



}
