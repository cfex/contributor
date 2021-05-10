package com.contributor.dao;

import com.contributor.model.Authority;
import com.contributor.model.enumeration.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityDao extends JpaRepository<Authority, Long> {
    Authority findByAuthority(Authorities authority);
}
