package org.troy.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.platform.entity.PlatformMember;

public interface PlatformMemberDao extends JpaRepository<PlatformMember, Long> {

    PlatformMember findById(Long id);

    List<PlatformMember> findAll();

}