package dgsw.hackathon.DaeItSo.domain.post.repository;

import dgsw.hackathon.DaeItSo.domain.post.domain.entity.Post;
import dgsw.hackathon.DaeItSo.domain.post.domain.enums.Category;
import dgsw.hackathon.DaeItSo.domain.user.domain.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user, Sort sort);
    List<Post> findAllByCategory(Category category, Sort sort);
    List<Post> findByContentContaining(String keyword, Sort sort);
}
