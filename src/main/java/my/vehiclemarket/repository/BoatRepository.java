package my.vehiclemarket.repository;

import my.vehiclemarket.model.entity.BoatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BoatRepository extends JpaRepository<BoatEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE BoatEntity b SET b.daysActive = b.daysActive + 1")
    void incrementDaysActiveForAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM BoatEntity b WHERE b.daysActive > 60")
    void deleteVehiclesWithDaysActiveGreaterThan(int i);

    List<BoatEntity> getBoatsByOwnerId(Long ownerId);
}
