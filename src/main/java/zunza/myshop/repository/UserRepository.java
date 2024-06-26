package zunza.myshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByNickname(String nickname);

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
}
