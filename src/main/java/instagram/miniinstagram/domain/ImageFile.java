package instagram.miniinstagram.domain;

import lombok.Data;

@Data
public class ImageFile {
    private Long id;                //이미지 파일 id
    private Long feedId;            //피드 id
    private String originalName;    //원래 파일명
    private String storeFileName;   //만들어 낸 파일명
    private String insertDate;      //저장된 날짜 ?

    public ImageFile(String originalName, String storeFileName)
    {
        this.originalName = originalName;
        this.storeFileName = storeFileName;
    }
}
