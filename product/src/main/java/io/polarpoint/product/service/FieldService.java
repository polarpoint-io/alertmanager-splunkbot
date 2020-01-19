package io.polarpoint.product.service;

import io.polarpoint.product.domain.Field;
import io.polarpoint.product.repository.FieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Field}.
 */
@Service
@Transactional
public class FieldService {

    private final Logger log = LoggerFactory.getLogger(FieldService.class);

    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**
     * Save a field.
     *
     * @param field the entity to save.
     * @return the persisted entity.
     */
    public Field save(Field field) {
        log.debug("Request to save Field : {}", field);
        return fieldRepository.save(field);
    }

    /**
     * Get all the fields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Field> findAll(Pageable pageable) {
        log.debug("Request to get all Fields");
        return fieldRepository.findAll(pageable);
    }


    /**
     * Get one field by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Field> findOne(Long id) {
        log.debug("Request to get Field : {}", id);
        return fieldRepository.findById(id);
    }

    /**
     * Delete the field by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Field : {}", id);
        fieldRepository.deleteById(id);
    }
}
