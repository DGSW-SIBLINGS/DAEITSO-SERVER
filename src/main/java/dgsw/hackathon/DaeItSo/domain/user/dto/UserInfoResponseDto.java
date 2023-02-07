package dgsw.hackathon.DaeItSo.domain.user.dto;

import dgsw.hackathon.DaeItSo.domain.user.domain.Roles;
import dgsw.hackathon.DaeItSo.domain.user.domain.StdInfo;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserInfoResponseDto {

    private Long userId;
    private String name;
    private String email;
    private String profileImage;
    private StdInfo stdInfo;
    private Roles roles;

    public UserInfoResponseDto(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.stdInfo = user.getStdInfo();
        this.roles = user.getRoles();
    }
}
