package com.smartverse.agendeibackend_gen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.UUID;




@Component
public class UserConfigurationDTOConverter {

    

    public UserConfigurationEntity toEntity(UserConfigurationDTO dto, String displayFields){
        displayFields = displayFields == null || displayFields.trim().isEmpty() ? "*" : displayFields;


        if(dto != null) {
            var entity = new UserConfigurationEntity();
            
           entity.setId(dto.id);
           entity.setName(dto.name);
           entity.setUserPhoto(dto.userPhoto);
           entity.setTheme(dto.theme);
           entity.setLang(dto.lang);
           entity.setEmail(dto.email);
           entity.setHash(dto.hash);
           return entity;
        }

        return null;
    }

    public UserConfigurationDTO toDTO(UserConfigurationEntity entity, String displayFields){
        displayFields = displayFields == null || displayFields.trim().isEmpty() ? "*" : displayFields;

        if(entity != null){
            var dto = new UserConfigurationDTO();
            
           dto.id = displayFields.contains("*") || displayFields.contains("id") ? entity.getId() : null;
           dto.name = displayFields.contains("*") || displayFields.contains("name") ? entity.getName() : null;
           dto.userPhoto = displayFields.contains("*") || displayFields.contains("userPhoto") ? entity.getUserPhoto() : null;
           dto.theme = displayFields.contains("*") || displayFields.contains("theme") ? entity.getTheme() : null;
           dto.lang = displayFields.contains("*") || displayFields.contains("lang") ? entity.getLang() : null;
           dto.email = displayFields.contains("*") || displayFields.contains("email") ? entity.getEmail() : null;
           dto.hash = displayFields.contains("*") || displayFields.contains("hash") ? entity.getHash() : null;
           return dto;
        }
        return null;
    }

    public List<UserConfigurationEntity> toEntity(List<UserConfigurationDTO> obj, String displayFields){
        if(obj != null){
            var list = new ArrayList<UserConfigurationEntity>();
            obj.forEach(dto -> {
                var entity = toEntity(dto, displayFields);
                list.add(entity);
            });
            return list;
        }
        return null;
    }

    public List<UserConfigurationDTO> toDTO(List<UserConfigurationEntity> obj, String displayFields){
        if(obj != null) {
            var list = new ArrayList<UserConfigurationDTO>();
            obj.forEach(entity -> {
                var dto = toDTO(entity, displayFields);
                list.add(dto);
            });
            return list;
        }
        return null;

    }
}
