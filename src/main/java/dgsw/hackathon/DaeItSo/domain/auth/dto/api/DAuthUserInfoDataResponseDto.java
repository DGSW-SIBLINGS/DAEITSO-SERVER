package dgsw.hackathon.DaeItSo.domain.auth.dto.api;

import dgsw.hackathon.DaeItSo.domain.user.domain.Roles;
import dgsw.hackathon.DaeItSo.domain.user.domain.StdInfo;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DAuthUserInfoDataResponseDto {

    private int grade;
    private int room;
    private int number;
    private String name;
    private String profileImage;
    private String email;

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .profileImage(profileImage)
                .stdInfo(new StdInfo(grade, room, number))
                .roles(Roles.USER)
                .build();
    }

    public void setProfileImgNull() {
        this.profileImage = null;
    }
}
