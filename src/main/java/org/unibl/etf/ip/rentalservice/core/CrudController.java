package org.unibl.etf.ip.rentalservice.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;

import java.io.Serializable;
import java.util.List;

public abstract class CrudController<ID extends Serializable, REQ, RESP> {

    private final Class<RESP> respClass;
    private final CrudService<ID> crudService;

    // Constructor for initializing the controller with service and response class
    protected CrudController(Class<RESP> respClass, CrudService<ID> crudService) {
        this.respClass = respClass;
        this.crudService = crudService;
    }

    // Get all entities (responds with a list of response DTOs)
    @GetMapping
    public List<RESP> findAll() {
        return crudService.findAll(respClass);
    }

    // Get an entity by ID
    @GetMapping("/{id}")
    public RESP findById(@PathVariable ID id) throws NotFoundException {
        return crudService.findById(id, respClass);
    }

    // Delete an entity by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable ID id) throws NotFoundException {
        crudService.delete(id);
    }

    // Insert a new entity
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RESP insert(@RequestBody REQ object) throws NotFoundException {
        return crudService.insert(object, respClass);
    }

    // Update an existing entity
    @PutMapping("/{id}")
    public RESP update(@PathVariable ID id, @RequestBody REQ object) throws NotFoundException {
        return crudService.update(id, object, respClass);
    }
}
