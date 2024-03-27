package zunza.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zunza.myshop.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
