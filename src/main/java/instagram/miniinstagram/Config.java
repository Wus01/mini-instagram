package instagram.miniinstagram;

import instagram.miniinstagram.domain.User;
import instagram.miniinstagram.repository.FeedRepository;
import instagram.miniinstagram.repository.JdbcFeedRepository;
import instagram.miniinstagram.repository.JdbcUserRepository;
import instagram.miniinstagram.repository.UserRepository;
import instagram.miniinstagram.service.FeedService;
import instagram.miniinstagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Config {
    private final DataSource dataSource;

    @Autowired
    public Config(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Bean
    public UserService userService(){
        return new UserService(userRepository());

    }

    @Bean
    public UserRepository userRepository(){
        return new JdbcUserRepository(dataSource);
    }


    @Bean
    public FeedService feedService(){
        return new FeedService(feedRepository());
    }
    @Bean
    public FeedRepository feedRepository(){ return new JdbcFeedRepository(dataSource);}
}
