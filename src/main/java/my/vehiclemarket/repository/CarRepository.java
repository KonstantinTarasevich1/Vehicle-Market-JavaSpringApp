package my.vehiclemarket.repository;

import my.vehiclemarket.model.entity.BoatEntity;
import my.vehiclemarket.model.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE CarEntity c SET c.daysActive = c.daysActive + 1")
    void incrementDaysActiveForAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM CarEntity c WHERE c.daysActive > 60")
    void deleteVehiclesWithDaysActiveGreaterThan(int i);

    List<CarEntity> getCarsByOwnerId(Long ownerId);
}
