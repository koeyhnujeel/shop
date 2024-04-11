package zunza.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zunza.myshop.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
