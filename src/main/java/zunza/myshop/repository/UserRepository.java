package zunza.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zunza.myshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
