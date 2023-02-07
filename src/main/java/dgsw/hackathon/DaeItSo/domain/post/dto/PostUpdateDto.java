package dgsw.hackathon.DaeItSo.domain.post.dto;

import dgsw.hackathon.DaeItSo.domain.file.dto.ImgUrlResponseDto;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Category;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.FreeShare;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Place;
import lombok.Getter;

import java.util.List;

@Getter
public class PostUpdateDto {
    private Long postId;
    private List<ImgUrlResponseDto> imgUrls;
    private Category category;
    private Long price;
    private String title;
    private String content;
    private FreeShare freeShare;
    private Place place;
}