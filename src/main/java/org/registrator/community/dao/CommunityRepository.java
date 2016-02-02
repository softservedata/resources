package org.registrator.community.dao;
import org.registrator.community.entity.TerritorialCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends JpaRepository<TerritorialCommunity, Integer>{

    @Query("select tc FROM TerritorialCommunity tc where tc.name = :name")
    TerritorialCommunity findByName(@Param("name") String name);
}
