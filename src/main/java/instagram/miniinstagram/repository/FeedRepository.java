package instagram.miniinstagram.repository;

import instagram.miniinstagram.domain.Feed;

import java.util.Optional;

public interface FeedRepository {
    Feed saveContent(Feed feed);
    Optional<Feed> findById(Long id);


}
