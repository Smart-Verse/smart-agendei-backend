package com.smartverse.agendeibackend.repository;

import com.smartverse.agendeibackend_gen.AppointmentEntity;
import com.smartverse.agendeibackend_gen.AppointmentRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Primary
public interface AppointmentCustomRepository extends AppointmentRepository {
    List<AppointmentEntity> findByStartDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query(nativeQuery = true,value = "SELECT COUNT(1) FROM appointment " +
            "WHERE status = '3' " +
            "AND startDate >= :startDate")
    long countByStatusAndLast7Days(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT a.client, COUNT(a) AS total " +
            "FROM AppointmentEntity a " +
            "GROUP BY a.client " +
            "ORDER BY total DESC " +
            "LIMIT 5")
    List<Object[]> findTop5ClientsByAppointmentCount();

    @Query(nativeQuery = true, value = "SELECT count(1) FROM appointment WHERE status = '0'")
    long countAppointmentsPending();

}
