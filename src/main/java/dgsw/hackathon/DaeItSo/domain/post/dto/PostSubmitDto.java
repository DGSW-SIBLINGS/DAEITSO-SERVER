package dgsw.hackathon.DaeItSo.domain.post.dto;

import dgsw.hackathon.DaeItSo.domain.file.dto.ImgUrlResponseDto;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Category;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.FreeShare;
import dgsw.hackathon.DaeItSo.domain.post.domain.entity.Post;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Place;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostSubmitDto {

//    private List<ImgUrlResponseDto> imgUrls;
    private String imgUrls;
    private Category category;
    private String title;
    private String content;
    private Long price;
    private FreeShare freeShare;
    private Place place;

    public Post toEntity(PostSubmitDto postSubmitDto, User user) {
        return Post.builder()
                .user(user)
                .place(postSubmitDto.getPlace())
                .title(postSubmitDto.getTitle())
                .category(postSubmitDto.getCategory())
                .freeShare(postSubmitDto.getFreeShare())
                .price(postSubmitDto.getPrice())
                .content(postSubmitDto.getContent())
                .imgUrl(postSubmitDto.getImgUrls()).build();
//                .imgUrl(String.join("///", postSubmitDto.getImgUrls().stream().map(
//                        imgUrlResponseDto -> imgUrlResponseDto.getImgUrl()).collect(Collectors.toList()))).build();
    }
}
