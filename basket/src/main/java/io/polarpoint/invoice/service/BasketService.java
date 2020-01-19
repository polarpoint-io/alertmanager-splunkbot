package io.polarpoint.invoice.service;

import io.polarpoint.invoice.domain.Basket;
import io.polarpoint.invoice.repository.BasketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Basket}.
 */
@Service
@Transactional
public class BasketService {

    private final Logger log = LoggerFactory.getLogger(BasketService.class);

    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    /**
     * Save a basket.
     *
     * @param basket the entity to save.
     * @return the persisted entity.
     */
    public Basket save(Basket basket) {
        log.debug("Request to save Basket : {}", basket);
        return basketRepository.save(basket);
    }

    /**
     * Get all the baskets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Basket> findAll(Pageable pageable) {
        log.debug("Request to get all Baskets");
        return basketRepository.findAll(pageable);
    }


    /**
     * Get one basket by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Basket> findOne(Long id) {
        log.debug("Request to get Basket : {}", id);
        return basketRepository.findById(id);
    }

    /**
     * Delete the basket by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Basket : {}", id);
        basketRepository.deleteById(id);
    }
}
