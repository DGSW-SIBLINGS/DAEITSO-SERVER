package dgsw.hackathon.DaeItSo.domain.post.domain.entity;

import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Category;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.FreeShare;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Place;
import dgsw.hackathon.DaeItSo.domain.post.dto.PostUpdateDto;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import dgsw.hackathon.DaeItSo.global.error.CustomError;
import dgsw.hackathon.DaeItSo.global.error.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @NotNull
    private Long price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @CreatedDate
    private LocalDateTime createPostDateTime;

    @LastModifiedDate
    private LocalDateTime updatePostDateTime;

    @Column(columnDefinition = "LONGTEXT")
    private String imgUrl;

    @NotNull
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FreeShare freeShare;

    @NotNull
    private Long likeCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Place place;

    @Builder
    public Post(User user, Category category, String content, String imgUrl, Long price, FreeShare freeShare, Place place, String title) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        if(imgUrl.isEmpty()) this.imgUrl = null;
        else this.imgUrl = imgUrl;
        checkPrice(freeShare, price);
        this.likeCount = 0L;
        this.place = place;
    }

    private void checkPrice(FreeShare freeShare, Long price) {
        if(freeShare.equals(FreeShare.FREE)) this.price = 0L;
        else {
            if(price > 5000L) {
                throw CustomError.of(ErrorCode.REFUSE_PRICE);
            }
            else this.price = price;
        }
        this.freeShare = freeShare;
    }

    public void updateInfo(PostUpdateDto postUpdateDto) {
        this.category = postUpdateDto.getCategory();
        this.price = postUpdateDto.getPrice();
        this.content = postUpdateDto.getContent();
        if(postUpdateDto.getImgUrls().isEmpty()) this.imgUrl = null;
        else this.imgUrl = String.join("///", postUpdateDto.getImgUrls().stream().map(
                imgUrlResponseDto -> imgUrlResponseDto.getImgUrl()).collect(Collectors.toList()));
        checkPrice(freeShare, price);;
        this.place = postUpdateDto.getPlace();
        this.title = postUpdateDto.getTitle();
    }
}
