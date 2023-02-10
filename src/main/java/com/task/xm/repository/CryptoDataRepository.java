package com.task.xm.repository;


import com.task.xm.model.CryptoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;


@Repository
public interface CryptoDataRepository extends JpaRepository<CryptoData, BigInteger> {
    @Query("SELECT DISTINCT a.name FROM CryptoData a")
    List<String> findDistinctName();
    List<CryptoData> findByNameIgnoreCase(String name);

    CryptoData findTopByNameOrderByPriceDesc(String name);
    CryptoData findTopByNameOrderByPriceAsc(String name);

    CryptoData findTopByNameOrderByDateDesc(String name);
    CryptoData findTopByNameOrderByDateAsc(String name);


}
