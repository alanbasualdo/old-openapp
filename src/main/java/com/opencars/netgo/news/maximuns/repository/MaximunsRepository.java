package com.opencars.netgo.news.maximuns.repository;

import com.opencars.netgo.news.maximuns.entity.Maximuns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaximunsRepository extends JpaRepository<Maximuns, Integer> {

    List<Maximuns> findByOutstanding(int outstanding);

}
