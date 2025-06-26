package com.smartverse.agendeibackend_gen;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("userConfiguration")
public abstract class UserConfigurationHandler implements HandlerBase<UserConfigurationDTO, UUID> {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public UserConfigurationRepository repository;

    @Autowired
    public UserConfigurationDTOConverter dtoConverter;

    @Autowired
    public SpecificationFilter<UserConfigurationEntity> especificationFilter;

    @Override
    public UserConfigurationDTO save(UserConfigurationDTO obj) {
        var entity = dtoConverter.toEntity(obj, null);
        entityManager.persist(entity);
        return dtoConverter.toDTO(entity, null);
    }

    @Override
    public UserConfigurationDTO update(UserConfigurationDTO obj, UUID id) {
        var entity = dtoConverter.toEntity(obj, null);
        entity.setId(id);
        entityManager.merge(entity);
        return dtoConverter.toDTO(entity, null);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public UserConfigurationDTO get(UUID id) {
        return dtoConverter.toDTO(repository.getReferenceById(id), null);
    }

    @Override
    public ResponseData getAll(@RequestParam Map<String, String> params) {

        var input = new RequestData();
        input.size = params.containsKey("size") ? Integer.parseInt(params.get("size")) : null;
        input.offset = params.containsKey("offset") ? Integer.parseInt(params.get("offset")) : null;
        input.filter = params.get("filter");
        input.order = params.get("order");
        input.displayFields = params.get("displayFields");

         if(input.offset <= 0){
             input.offset = 1;
         }
         input.offset--;
         Pageable pageable = PageRequest.of(input.offset , input.size);
         var spec = especificationFilter.customFilter(input.filter);
         var ret = repository.findAll(spec, pageable);

         var output = new ResponseData();
         output.total = Integer.parseInt(String.valueOf(ret.getTotalElements()));
         output.contents = dtoConverter.toDTO(ret.getContent(), input.displayFields);
         output.size = ret.getSize();
         output.offset = input.offset;
         return output;
    }
}
