package io.polarpoint.store.service;

import io.polarpoint.store.domain.Rulez;
import io.polarpoint.store.repository.RulezRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Rulez}.
 */
@Service
@Transactional
public class RulezService {

    private final Logger log = LoggerFactory.getLogger(RulezService.class);

    private final RulezRepository rulezRepository;

    public RulezService(RulezRepository rulezRepository) {
        this.rulezRepository = rulezRepository;
    }

    /**
     * Save a rulez.
     *
     * @param rulez the entity to save.
     * @return the persisted entity.
     */
    public Rulez save(Rulez rulez) {
        log.debug("Request to save Rulez : {}", rulez);
        return rulezRepository.save(rulez);
    }

    /**
     * Get all the rulezs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Rulez> findAll() {
        log.debug("Request to get all Rulezs");
        return rulezRepository.findAll();
    }


    /**
     * Get one rulez by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Rulez> findOne(Long id) {
        log.debug("Request to get Rulez : {}", id);
        return rulezRepository.findById(id);
    }

    /**
     * Delete the rulez by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rulez : {}", id);
        rulezRepository.deleteById(id);
    }
}
