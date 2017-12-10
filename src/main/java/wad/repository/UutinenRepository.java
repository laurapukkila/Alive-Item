package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wad.domain.Uutinen;

@Component
public interface UutinenRepository extends JpaRepository<Uutinen, Long> {
}
