package dgsw.hackathon.DaeItSo.domain.post.dto;

import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Category;
import dgsw.hackathon.DaeItSo.domain.post.domain.entity.Post;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Place;
import dgsw.hackathon.DaeItSo.domain.user.domain.StdInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.FreeShare;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostResponseDto {

    private Category category;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private FreeShare freeShare;
    private Place place;
    private String title;
    private String content;
    private List<String> imgUrls;
    private Long postId;
    private String userName;
    private String profileUrl;
    private StdInfo stdInfo;
    private Long like;
    private Long author;

    public PostResponseDto(Post post) {
        this.category = post.getCategory();
        this.createDateTime = post.getCreatePostDateTime();
        this.updateDateTime = post.getUpdatePostDateTime();
        this.freeShare = post.getFreeShare();
        this.content = post.getContent();
        if(post.getImgUrl() != null)
        this.imgUrls = List.of(post.getImgUrl().split("///"));
        this.postId = post.getId();
        this.userName = post.getUser().getName();
        this.profileUrl = post.getUser().getProfileImage();
        this.stdInfo = post.getUser().getStdInfo();
        this.title = post.getTitle();
        this.like = post.getLikeCount();
        this.author = post.getUser().getId();
        this.place = post.getPlace();
    }
}
