package instagram.miniinstagram.controller;

import instagram.miniinstagram.domain.User;
import instagram.miniinstagram.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(){

        return "login";

        //숙제
        // 1. 로그인 페이지, 회원가입 페이지
        // 2. 회원 가입 데이터 삽입
        // 3. 로그인 시, 성공-> index로 리다이렉트 되게!
        // 4. 백준 별찍기 - 2,3번 풀기
        // 5. 포큐 가입하기(02/01 까지)

    }

    @PostMapping("/login")
    public String loginPost(UserForm form, HttpServletRequest request ){
                                            //request 객체
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());

        User currentUser = userService.login(user).get();
        HttpSession session = request.getSession();         //세션 id 생성
        session.setAttribute("member", currentUser);    //sessionID : {"memeber": currentUser}

        return "redirect:/";

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);    //세션을 만들지 말고 가져올 것!!

        //세션 삭제
        if(session != null)
        {
            session.invalidate();
        }

        return "redirect:/login";
    }

    //야호!

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(UserForm form){
        User user = new User();
        user.setEmail(form.getEmail());
        user.setNic_name(form.getNic_name());
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        /*
        user.setProfile_img(form.getProfile_img());
        user.setWebSite(form.getWebSite());
        user.setIntroduction(form.getIntroduction());

         */

        userService.join(user);

        return "redirect:/";
    }

    /*
    @GetMapping("/upload/feed")
    public String feed(){
        return "upload/feed";
    }
*/

    @GetMapping("/profile/{nic_name}")
    public String profile(@PathVariable("nic_name") String nic_name, Model model, User user){

        model.addAttribute("nic_name",nic_name);      //"nic_name" : nic_name
        log.info("nic_name = {}", nic_name);            //nic_name 출력`



        return "profile";
    }

    @PostMapping("/profile/{nic_name}")
    public String profilePost(@PathVariable("nic_name") String nic_name, @ModelAttribute("user") User user, Model model){
        //@ModelAttribute = index에서 생성한 객체에 대한 값을 모델로 다시 받음

        model.addAttribute("user", user);       //index에서 넘어온 id, nic_name, email만 들어 있음

        log.info("<profile.html> user = {}", user);

        return "profile";
    }

}
