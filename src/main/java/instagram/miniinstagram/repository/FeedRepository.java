package instagram.miniinstagram.repository;

import instagram.miniinstagram.domain.Feed;
import instagram.miniinstagram.domain.ImageFile;

import java.util.List;
import java.util.Optional;

public interface FeedRepository {
    Feed saveContent(Feed feed);
    List<ImageFile> saveImage(ImageFile imageFile);
    Optional<Feed> findById(Long id);




}
