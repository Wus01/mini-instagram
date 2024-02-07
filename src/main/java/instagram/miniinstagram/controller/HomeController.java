package instagram.miniinstagram.controller;

import instagram.miniinstagram.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){

        //세션 값 꺼내오기
        HttpSession session = request.getSession(false);
        session.getAttribute("member");     //member에는 회원T의 id, nic_name, password, email이 담김
        User user = (User)session.getAttribute("member");       //getAttribute의 반환값은 객체이기 때문에
                                                                        //강제 형변환(User)를 해줌

        model.addAttribute("user",user);

        log.info("user = {}", user);

        return "index";
    }

}
