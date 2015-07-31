package org.troy.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.platform.entity.PlatformMemberItemRelation;

public interface PlatformMemberItemRelationDao extends
                                              JpaRepository<PlatformMemberItemRelation, Long> {

    PlatformMemberItemRelation findById(Long id);

    List<PlatformMemberItemRelation> findAll();

}