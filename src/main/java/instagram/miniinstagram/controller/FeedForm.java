package instagram.miniinstagram.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FeedForm {

    private String content;
    private List<MultipartFile> imageFiles;
}
