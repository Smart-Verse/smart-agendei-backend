package com.smartverse.agendeibackend_gen;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import com.potatotech.authorization.tenant.TenantContext;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_confirmation")
public class UserConfirmationEntity {

    
    /**Identificação**/
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, name = "id")
    private UUID id;
    
    /**Id Usuario**/
    @Column(nullable = false, name = "user_id")
    private UUID userId;
    
    /**Hash de confirmação**/
    @Column(nullable = false, name = "hash")
    private String hash;
    
}
