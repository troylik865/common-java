package org.troy.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.platform.entity.PlatformItem;

public interface PlatformItemDao extends JpaRepository<PlatformItem, Long> {

    PlatformItem findById(Long id);

    List<PlatformItem> findAll();

}