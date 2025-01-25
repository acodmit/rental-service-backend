package org.unibl.etf.ip.rentalservice.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CrudJpaService<E extends BaseEntity<ID>, ID extends Serializable> implements CrudService<ID> {

    private final JpaRepository<E, ID> repository;
    private final Class<E> entityClass;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public CrudJpaService(JpaRepository<E, ID> repository, Class<E> entityClass, ModelMapper modelMapper) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.modelMapper = modelMapper;
    }

    @Override
    public <T> List<T> findAll(Class<T> resultDtoClass) {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, resultDtoClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> T findById(ID id, Class<T> resultDtoClass) {
        E entity = findEntityById(id);
        return modelMapper.map(entity, resultDtoClass);
    }

    @Override
    public <T, U> T insert(U object, Class<T> resultDtoClass) {
        E entity = modelMapper.map(object, entityClass);
        entity.setId(null); // Ensure it's a new entity
        entity = repository.saveAndFlush(entity);
        entityManager.refresh(entity);
        return modelMapper.map(entity, resultDtoClass);
    }

    @Override
    public <T, U> T update(ID id, U object, Class<T> resultDtoClass) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found");
        }
        E entity = modelMapper.map(object, entityClass);
        entity.setId(id);
        entity = repository.saveAndFlush(entity);
        entityManager.refresh(entity);
        return modelMapper.map(entity, resultDtoClass);
    }

    @Override
    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found");
        }
        repository.deleteById(id);
    }

    private E findEntityById(ID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }
}
