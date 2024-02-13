package instagram.miniinstagram.domain;

import lombok.Data;

@Data
public class Feed {
    private Long id;        //게시글 id
    //private Long imgId;         //이미지 id
    private String content;     //게시글 내용
    private Long memberId;    //회원 id

}
