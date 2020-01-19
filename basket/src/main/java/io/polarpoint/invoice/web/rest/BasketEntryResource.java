package io.polarpoint.invoice.web.rest;

import io.polarpoint.invoice.domain.BasketEntry;
import io.polarpoint.invoice.service.BasketEntryService;
import io.polarpoint.invoice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.polarpoint.invoice.domain.BasketEntry}.
 */
@RestController
@RequestMapping("/api")
public class BasketEntryResource {

    private final Logger log = LoggerFactory.getLogger(BasketEntryResource.class);

    private static final String ENTITY_NAME = "basketBasketEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BasketEntryService basketEntryService;

    public BasketEntryResource(BasketEntryService basketEntryService) {
        this.basketEntryService = basketEntryService;
    }

    /**
     * {@code POST  /basket-entries} : Create a new basketEntry.
     *
     * @param basketEntry the basketEntry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new basketEntry, or with status {@code 400 (Bad Request)} if the basketEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/basket-entries")
    public ResponseEntity<BasketEntry> createBasketEntry(@Valid @RequestBody BasketEntry basketEntry) throws URISyntaxException {
        log.debug("REST request to save BasketEntry : {}", basketEntry);
        if (basketEntry.getId() != null) {
            throw new BadRequestAlertException("A new basketEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BasketEntry result = basketEntryService.save(basketEntry);
        return ResponseEntity.created(new URI("/api/basket-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /basket-entries} : Updates an existing basketEntry.
     *
     * @param basketEntry the basketEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated basketEntry,
     * or with status {@code 400 (Bad Request)} if the basketEntry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the basketEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/basket-entries")
    public ResponseEntity<BasketEntry> updateBasketEntry(@Valid @RequestBody BasketEntry basketEntry) throws URISyntaxException {
        log.debug("REST request to update BasketEntry : {}", basketEntry);
        if (basketEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BasketEntry result = basketEntryService.save(basketEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, basketEntry.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /basket-entries} : get all the basketEntries.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of basketEntries in body.
     */
    @GetMapping("/basket-entries")
    public ResponseEntity<List<BasketEntry>> getAllBasketEntries(Pageable pageable) {
        log.debug("REST request to get a page of BasketEntries");
        Page<BasketEntry> page = basketEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /basket-entries/:id} : get the "id" basketEntry.
     *
     * @param id the id of the basketEntry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the basketEntry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/basket-entries/{id}")
    public ResponseEntity<BasketEntry> getBasketEntry(@PathVariable Long id) {
        log.debug("REST request to get BasketEntry : {}", id);
        Optional<BasketEntry> basketEntry = basketEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(basketEntry);
    }

    /**
     * {@code DELETE  /basket-entries/:id} : delete the "id" basketEntry.
     *
     * @param id the id of the basketEntry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/basket-entries/{id}")
    public ResponseEntity<Void> deleteBasketEntry(@PathVariable Long id) {
        log.debug("REST request to delete BasketEntry : {}", id);
        basketEntryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
