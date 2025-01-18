package my.exratesservice.repository;


import my.exratesservice.model.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Long> {
    RateEntity findByCurrency(String currency);
}
