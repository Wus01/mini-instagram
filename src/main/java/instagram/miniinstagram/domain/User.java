package instagram.miniinstagram.domain;

import lombok.Data;

@Data       //getter + setter 자동으로 만들어줌
public class User {

    private Long id;
    private String nic_name;        //닉네임
    private String password;        //비밀번호
    private String email;           //이메일
    private String name;            //이름
    private String profile_img;     //이미지
    private String webSite;         //웹사이트
    private String introduction;    //소개 글

    //컴퓨터 관련 중요한 이야기 !!
    //디펜던시 인젝션(di), inversion, 솔리드 원칙(코드 가독성을 위한)


}
