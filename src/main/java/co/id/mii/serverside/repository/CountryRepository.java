/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.repository;

import co.id.mii.serverside.model.Country;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAI
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    
    Country findByCode(String code);
    Country findByName(String name);
    
    @Query("SELECT c FROM Country c WHERE c.name LIKE ?1")
    List<Country> findCountriesByName(String name);
    
    @Query(value = "SELECT c.* FROM tb_country c "
            + "INNER JOIN tb_region r ON c.region_id = r.id "
            + "WHERE r.region_name = ?1", nativeQuery = true)
    List<Country> getCountriesByRegionName(String regionName);
}
