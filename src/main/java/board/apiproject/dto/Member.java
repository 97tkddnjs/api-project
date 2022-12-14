package board.apiproject.dto;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Member {

    //@NotEmpty <- 곧 해결 예정
    private int num; //

    private String id; // pk 사용자 id

    private String pass; // 사용자 비번

    private String name; //사용자 이름

    private int age;

    private String email;

    private String phone;

    private String grant;

}
