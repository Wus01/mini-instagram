package instagram.miniinstagram.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserForm {
    private Long id;
    private String email;
    private String nic_name;
    private String name;
    private String password;
    /*
    private String webSite;
    private String profile_img;
    private String introduction;
     */
}
