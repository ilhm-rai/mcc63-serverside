/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.repository;

import co.id.mii.serverside.model.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/**
 *
 * @author MSI-JO
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{
    
    Region findByName(String name);
    List<Region> findByNameContains(String name);
        
    // Select name From region Where region.name = (Parameter Name)
    
    // JPQL Default (Berdasarkan Model/Object)
    @Query("SELECT r FROM Region r WHERE r.id = ?1 AND r.name= ?2" )
    Region findByIdAndName(Long id, String name);
    
    // JPQL Native (Berdasarkan Database)
    @Query(value = "SELECT tc.name FROM tb_region tr "
            + "JOIN tb_country tc ON "
            + "tr.id = tc.region_id WHERE tr.region_name = ?1", nativeQuery = true)
    List<String> filterCountryByRegionName(String name);
}
