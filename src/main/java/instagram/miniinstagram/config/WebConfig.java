package instagram.miniinstagram.config;

import instagram.miniinstagram.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

                                                        //order(1) == 인터셉터 중 1번째
        registry.addInterceptor(new LoginCheckInterceptor()).order(1)
                .addPathPatterns("/**").excludePathPatterns("/login", "/logout", "/css/*", "/.ico", "/error", "/register");
                //add~~ = 전체 경로에 대해서 인터셉터를 작동시켜라
                //exclude(~~) = ~~들을 제외해라
    }
}
