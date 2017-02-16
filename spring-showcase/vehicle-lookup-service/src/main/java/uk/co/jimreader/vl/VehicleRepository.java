package uk.co.jimreader.vl;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

	Vehicle findByVin(@Param("vin") String vin);

}
