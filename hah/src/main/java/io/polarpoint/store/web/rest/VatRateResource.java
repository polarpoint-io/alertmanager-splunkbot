package io.polarpoint.store.web.rest;

import io.polarpoint.store.domain.VatRate;
import io.polarpoint.store.repository.VatRateRepository;
import io.polarpoint.store.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.polarpoint.store.domain.VatRate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VatRateResource {

    private final Logger log = LoggerFactory.getLogger(VatRateResource.class);

    private static final String ENTITY_NAME = "vatRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VatRateRepository vatRateRepository;

    public VatRateResource(VatRateRepository vatRateRepository) {
        this.vatRateRepository = vatRateRepository;
    }

    /**
     * {@code POST  /vat-rates} : Create a new vatRate.
     *
     * @param vatRate the vatRate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vatRate, or with status {@code 400 (Bad Request)} if the vatRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vat-rates")
    public ResponseEntity<VatRate> createVatRate(@Valid @RequestBody VatRate vatRate) throws URISyntaxException {
        log.debug("REST request to save VatRate : {}", vatRate);
        if (vatRate.getId() != null) {
            throw new BadRequestAlertException("A new vatRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VatRate result = vatRateRepository.save(vatRate);
        return ResponseEntity.created(new URI("/api/vat-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vat-rates} : Updates an existing vatRate.
     *
     * @param vatRate the vatRate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vatRate,
     * or with status {@code 400 (Bad Request)} if the vatRate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vatRate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vat-rates")
    public ResponseEntity<VatRate> updateVatRate(@Valid @RequestBody VatRate vatRate) throws URISyntaxException {
        log.debug("REST request to update VatRate : {}", vatRate);
        if (vatRate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VatRate result = vatRateRepository.save(vatRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vatRate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vat-rates} : get all the vatRates.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vatRates in body.
     */
    @GetMapping("/vat-rates")
    public List<VatRate> getAllVatRates() {
        log.debug("REST request to get all VatRates");
        return vatRateRepository.findAll();
    }

    /**
     * {@code GET  /vat-rates/:id} : get the "id" vatRate.
     *
     * @param id the id of the vatRate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vatRate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vat-rates/{id}")
    public ResponseEntity<VatRate> getVatRate(@PathVariable Long id) {
        log.debug("REST request to get VatRate : {}", id);
        Optional<VatRate> vatRate = vatRateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vatRate);
    }

    /**
     * {@code DELETE  /vat-rates/:id} : delete the "id" vatRate.
     *
     * @param id the id of the vatRate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vat-rates/{id}")
    public ResponseEntity<Void> deleteVatRate(@PathVariable Long id) {
        log.debug("REST request to delete VatRate : {}", id);
        vatRateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
