package my.vehiclemarket.repository;

import my.vehiclemarket.model.entity.MotorcycleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<MotorcycleEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE MotorcycleEntity m SET m.daysActive = m.daysActive + 1")
    void incrementDaysActiveForAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM MotorcycleEntity m WHERE m.daysActive > 60")
    void deleteVehiclesWithDaysActiveGreaterThan(int i);

    List<MotorcycleEntity> getMotorcyclesByOwnerId(Long ownerId);
}
