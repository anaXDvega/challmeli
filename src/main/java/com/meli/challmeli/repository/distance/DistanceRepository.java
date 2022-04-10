package com.meli.challmeli.repository.distance;
import com.meli.challmeli.model.Distance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Map;

public interface DistanceRepository extends CrudRepository<Distance, Integer> {
    @Query(nativeQuery = true, value = "SELECT AVG(distance) AS average, MIN(distance) AS min, MAX(distance) AS max, sum(invocations) AS cantInvocations FROM Distance")
    Map<String, ?> averageDistanceToBuenosAires();
}
