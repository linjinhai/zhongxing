package com.sendyago.monitoring.dwrPush;

import com.sendyago.util.common.DwrScriptSessionManagerUtil;
import org.directwebremoting.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Created by Rick on 6/23/16.
 */
@Controller
@RequestMapping("/pushData")
@Scope("prototype")
public class PushDwrData {

    public void onPageLoad(String userId) {

        ScriptSession scriptSession = WebContextFactory.get()
                .getScriptSession();

        scriptSession.setAttribute("userId", userId);

        DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();

        try {

            dwrScriptSessionManagerUtil.init();

        } catch (ServletException e) {

            e.printStackTrace();

        }
    }

    /**
     * 消息推送
     */
    @RequestMapping(value = "sendMessage")
    public void sendMessageAuto(HttpServletRequest req, HttpServletResponse rep) {

        String userid = req.getParameter("userid");
        String id = req.getParameter("sensorId");
        String data = req.getParameter("sensorData");

        final String userId = userid;

        final String sensorId = id;

        final String sensorData = data;

        Browser.withAllSessionsFiltered(new ScriptSessionFilter() {

            public boolean match(ScriptSession session) {
//                if (session.getAttribute("userId") == null) {
//                    return false;
//                } else {
//                    return (session.getAttribute("userId")).equals(userId);
//                }
                return true;

            }

        }, new Runnable() {

            private ScriptBuffer script = new ScriptBuffer();

            public void run() {

                script.appendCall("callBack", sensorId, sensorData);
                Collection<ScriptSession> sessions = Browser.getTargetSessions();

                for (ScriptSession scriptSession : sessions) {

                    scriptSession.addScript(script);

                }

            }

        });

    }
}
