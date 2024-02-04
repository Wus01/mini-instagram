package instagram.miniinstagram.service;

import instagram.miniinstagram.domain.Feed;
import instagram.miniinstagram.domain.User;
import instagram.miniinstagram.repository.FeedRepository;

public class FeedService {

    private final FeedRepository feedRepository;

    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public Long join(Feed feed){
        feedRepository.saveContent(feed);
        return feed.getMemberId();
    }
}
