package instagram.miniinstagram.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.sqm.mutation.internal.Handler;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
    //수문장 역할!
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);                //세션 가져오기
        if(session == null || session.getAttribute("member") == null)   //세션이 없거나
                                                                                // 세션 값이 존재하지 않으면 -> sessionID: { "member": null }
        {
            response.sendRedirect("/login");        //login.html로 돌아감
            return false;
        }
        return true;


    }

}
