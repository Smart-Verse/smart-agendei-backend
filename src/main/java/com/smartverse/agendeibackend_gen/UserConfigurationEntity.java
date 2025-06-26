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
@Table(name="user_configuration")
public class UserConfigurationEntity {

    
    /**Campo identificação**/
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, name = "id")
    private UUID id;
    
    /**Nome do usuário**/
    @Column(nullable = false, name = "name")
    private String name;
    
    /**Foto Usuário**/
    @Column(name = "user_photo")
    private String userPhoto;
    
    /**Tema**/
    @Column(name = "theme")
    private Theme theme;
    
    /**Linguagem sistema**/
    @Column(nullable = false, name = "lang")
    private Language lang;
    
    /**Email**/
    @Column(nullable = false, name = "email")
    private String email;
    
    /**ID do usuário**/
    @Column(nullable = false, name = "hash")
    private UUID hash;
    
}
