package io.polarpoint.invoice.service;

import io.polarpoint.invoice.domain.BasketEntry;
import io.polarpoint.invoice.repository.BasketEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BasketEntry}.
 */
@Service
@Transactional
public class BasketEntryService {

    private final Logger log = LoggerFactory.getLogger(BasketEntryService.class);

    private final BasketEntryRepository basketEntryRepository;

    public BasketEntryService(BasketEntryRepository basketEntryRepository) {
        this.basketEntryRepository = basketEntryRepository;
    }

    /**
     * Save a basketEntry.
     *
     * @param basketEntry the entity to save.
     * @return the persisted entity.
     */
    public BasketEntry save(BasketEntry basketEntry) {
        log.debug("Request to save BasketEntry : {}", basketEntry);
        return basketEntryRepository.save(basketEntry);
    }

    /**
     * Get all the basketEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BasketEntry> findAll(Pageable pageable) {
        log.debug("Request to get all BasketEntries");
        return basketEntryRepository.findAll(pageable);
    }


    /**
     * Get one basketEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BasketEntry> findOne(Long id) {
        log.debug("Request to get BasketEntry : {}", id);
        return basketEntryRepository.findById(id);
    }

    /**
     * Delete the basketEntry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BasketEntry : {}", id);
        basketEntryRepository.deleteById(id);
    }
}
