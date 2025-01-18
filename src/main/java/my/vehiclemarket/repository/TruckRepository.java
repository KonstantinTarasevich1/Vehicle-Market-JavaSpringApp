package my.vehiclemarket.repository;

import my.vehiclemarket.model.entity.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TruckRepository extends JpaRepository<TruckEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE TruckEntity t SET t.daysActive = t.daysActive + 1")
    void incrementDaysActiveForAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM TruckEntity t WHERE t.daysActive > 60")
    void deleteVehiclesWithDaysActiveGreaterThan(int i);


    List<TruckEntity> getTrucksByOwnerId(Long ownerId);
}
