package instagram.miniinstagram.utils;

import instagram.miniinstagram.domain.ImageFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileStore {

    //이미지 저장
    @Value("${file.dir}")       //properties에 저장한 경로
    private String fileDir;

    //파일의 절대 경로
    public String getFullPath(String filename)
    {
        return fileDir + filename;
    }

    //저장한 경로에 파일 저장
    public ImageFile storeFile(MultipartFile multipartFile) throws IOException
    {
        if(multipartFile.isEmpty())
        {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new ImageFile(originalFileName, storeFileName);      //파일의 원래 이름과 생성된 파일 이름
    }

    public List<ImageFile> storeFiles(List<MultipartFile> multipartFile) throws IOException{
        List<ImageFile> imageFiles = new ArrayList<>();

        for(int i=0; i< multipartFile.size(); i++){
            imageFiles.add(storeFile(multipartFile.get(i)));
        }


        return imageFiles;

    }

    //파일 이름 변경
    private String createStoreFileName(String originalFileName)
    {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();         //id 생성
        return uuid + "." + ext;
    }

    //확장자 반환
    private String extractExt(String originalFileName)
    {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

}
