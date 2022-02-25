/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.repository;

import co.id.mii.serverside.model.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAI
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{
    
    Region findByName(String name);
    List<Region> findByNameContains(String name);
        
    // Select name From region Where region.name = (Parameter Name)
}
