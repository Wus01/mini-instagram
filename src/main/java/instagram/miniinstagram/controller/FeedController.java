package instagram.miniinstagram.controller;

import instagram.miniinstagram.domain.Feed;
import instagram.miniinstagram.domain.ImageFile;
import instagram.miniinstagram.domain.User;
import instagram.miniinstagram.service.FeedService;
import instagram.miniinstagram.utils.FileStore;
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
    public String uploadFeedGET(Model model, @ModelAttribute("user") User user) throws IOException {

        model.addAttribute("user", user);
        //feedService.join(feed);

        //log.info("feedForm = {}", feedForm);
        log.info("upload/feed user = {}",user);

        return "upload/feed";
    }
    @PostMapping("/upload/feed")
    public String uploadFeed(FeedForm feedForm, @ModelAttribute("user") User user) throws IOException {
/*
        Feed feed = new Feed();

        //List<ImageFile> imageFiles = fileStore.storeFiles(feedForm.getImageFiles());
        //feed.setContent(feedForm.getContent());
        //feed.setMemberId(user.getId());

        //feedService.join(feed);



        //log.info("feedForm = {}", feedForm);
        log.info("member id = {}", feed.getMemberId());
        log.info("content = {}", feedForm.getContent());
*/
        log.info("user = {}",user);
        return "upload/feed";
    }

}
