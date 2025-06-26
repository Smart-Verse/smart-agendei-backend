package com.smartverse.agendeibackend_gen;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

public interface HandlerBase<T,ID> {

    @PostMapping
    @Transactional
    public T save(@RequestBody T obj);

    @PutMapping("{id}")
    @Transactional
    public T update(@RequestBody T obj,@PathVariable ID id);

    @DeleteMapping("{id}")
    @Transactional
    public void delete(@PathVariable(value = "id", required = true) ID id);

    @GetMapping("{id}")
    @Transactional
    public T get(@PathVariable(value = "id", required = true) ID id);

    @GetMapping
    @Transactional
    public ResponseData getAll(@RequestParam Map<String, String> params);
}
