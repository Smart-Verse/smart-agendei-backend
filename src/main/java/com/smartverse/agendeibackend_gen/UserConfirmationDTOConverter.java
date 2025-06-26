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
public class UserConfirmationDTOConverter {

    

    public UserConfirmationEntity toEntity(UserConfirmationDTO dto, String displayFields){
        displayFields = displayFields == null || displayFields.trim().isEmpty() ? "*" : displayFields;


        if(dto != null) {
            var entity = new UserConfirmationEntity();
            
           entity.setId(dto.id);
           entity.setUserId(dto.userId);
           entity.setHash(dto.hash);
           return entity;
        }

        return null;
    }

    public UserConfirmationDTO toDTO(UserConfirmationEntity entity, String displayFields){
        displayFields = displayFields == null || displayFields.trim().isEmpty() ? "*" : displayFields;

        if(entity != null){
            var dto = new UserConfirmationDTO();
            
           dto.id = displayFields.contains("*") || displayFields.contains("id") ? entity.getId() : null;
           dto.userId = displayFields.contains("*") || displayFields.contains("userId") ? entity.getUserId() : null;
           dto.hash = displayFields.contains("*") || displayFields.contains("hash") ? entity.getHash() : null;
           return dto;
        }
        return null;
    }

    public List<UserConfirmationEntity> toEntity(List<UserConfirmationDTO> obj, String displayFields){
        if(obj != null){
            var list = new ArrayList<UserConfirmationEntity>();
            obj.forEach(dto -> {
                var entity = toEntity(dto, displayFields);
                list.add(entity);
            });
            return list;
        }
        return null;
    }

    public List<UserConfirmationDTO> toDTO(List<UserConfirmationEntity> obj, String displayFields){
        if(obj != null) {
            var list = new ArrayList<UserConfirmationDTO>();
            obj.forEach(entity -> {
                var dto = toDTO(entity, displayFields);
                list.add(dto);
            });
            return list;
        }
        return null;

    }
}
