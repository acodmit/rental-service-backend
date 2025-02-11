package org.unibl.etf.ip.rentalservice.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;

import java.io.Serializable;
import java.util.List;

public abstract class CrudController<ID extends Serializable, REQ, RESP> {

    private final Class<RESP> responseType;
    private final CrudService<ID> crudService;

    protected CrudController(Class<RESP> responseType, CrudService<ID> crudService) {
        this.responseType = responseType;
        this.crudService = crudService;
    }

    @GetMapping
    public ResponseEntity<List<RESP>> findAll() {
        List<RESP> responseList = crudService.findAll(responseType);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RESP> findById(@PathVariable ID id) throws NotFoundException {
        RESP response = crudService.findById(id, responseType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) throws NotFoundException {
        crudService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody REQ request) throws NotFoundException {
        RESP response = crudService.insert(request, responseType);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RESP> update(@PathVariable ID id, @RequestBody REQ request) throws NotFoundException {
        RESP response = crudService.update(id, request, responseType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
