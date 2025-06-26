package com.smartverse.agendeibackend_gen;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConfigurationDTO {
    
    UUID id; 
    String name; 
    String userPhoto; 
    Theme theme; 
    Language lang; 
    String email; 
    UUID hash; 
}
