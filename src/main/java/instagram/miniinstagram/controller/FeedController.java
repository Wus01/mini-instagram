package instagram.miniinstagram.controller;

import instagram.miniinstagram.domain.Feed;
import instagram.miniinstagram.domain.ImageFile;
import instagram.miniinstagram.domain.User;
import instagram.miniinstagram.service.FeedService;
import instagram.miniinstagram.utils.FileStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FeedController {

    private final FileStore fileStore;
    private final FeedService feedService;

    @GetMapping("/upload/feed")
    public String uploadFeed(HttpServletRequest request, Model model, FeedForm feedForm) throws IOException {
        HttpSession session = request.getSession(false);
        User user1 = (User) session.getAttribute("member");
        model.addAttribute("user",user1);


        log.info("user = {}", user1);

        return "upload/feed";

    }


    @PostMapping("/upload/feed/{memberId}")
    public String uploadFeedPost(@PathVariable("memberId") Long memberId, FeedForm feedForm ) throws IOException {

            Feed feed = new Feed();
            feed.setMemberId(memberId);
            feed.setContent(feedForm.getContent());

            List<ImageFile> imageFiles = fileStore.storeFiles(feedForm.getImageFiles());
            feedService.joinImage(imageFiles, feedService.join(feed));
            //content 먼저 저장하고, 반환받은 feedid값을 가지고 image 저장.


            log.info("member id = {}", feed.getMemberId());
            log.info("content = {}", feedForm.getContent());




        return "profile";
    }

}
