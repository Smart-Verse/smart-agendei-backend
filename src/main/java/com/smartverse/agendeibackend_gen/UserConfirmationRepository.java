package com.smartverse.agendeibackend_gen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserConfirmationRepository extends JpaRepository<UserConfirmationEntity, UUID> {
    Page<UserConfirmationEntity> findAll(Specification<UserConfirmationEntity> spec, Pageable pageable);
}
