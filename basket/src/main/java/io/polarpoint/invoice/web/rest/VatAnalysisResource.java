package io.polarpoint.invoice.web.rest;

import io.polarpoint.invoice.domain.VatAnalysis;
import io.polarpoint.invoice.repository.VatAnalysisRepository;
import io.polarpoint.invoice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.polarpoint.invoice.domain.VatAnalysis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VatAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(VatAnalysisResource.class);

    private static final String ENTITY_NAME = "basketVatAnalysis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VatAnalysisRepository vatAnalysisRepository;

    public VatAnalysisResource(VatAnalysisRepository vatAnalysisRepository) {
        this.vatAnalysisRepository = vatAnalysisRepository;
    }

    /**
     * {@code POST  /vat-analyses} : Create a new vatAnalysis.
     *
     * @param vatAnalysis the vatAnalysis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vatAnalysis, or with status {@code 400 (Bad Request)} if the vatAnalysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vat-analyses")
    public ResponseEntity<VatAnalysis> createVatAnalysis(@RequestBody VatAnalysis vatAnalysis) throws URISyntaxException {
        log.debug("REST request to save VatAnalysis : {}", vatAnalysis);
        if (vatAnalysis.getId() != null) {
            throw new BadRequestAlertException("A new vatAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VatAnalysis result = vatAnalysisRepository.save(vatAnalysis);
        return ResponseEntity.created(new URI("/api/vat-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vat-analyses} : Updates an existing vatAnalysis.
     *
     * @param vatAnalysis the vatAnalysis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vatAnalysis,
     * or with status {@code 400 (Bad Request)} if the vatAnalysis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vatAnalysis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vat-analyses")
    public ResponseEntity<VatAnalysis> updateVatAnalysis(@RequestBody VatAnalysis vatAnalysis) throws URISyntaxException {
        log.debug("REST request to update VatAnalysis : {}", vatAnalysis);
        if (vatAnalysis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VatAnalysis result = vatAnalysisRepository.save(vatAnalysis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vatAnalysis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vat-analyses} : get all the vatAnalyses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vatAnalyses in body.
     */
    @GetMapping("/vat-analyses")
    public List<VatAnalysis> getAllVatAnalyses() {
        log.debug("REST request to get all VatAnalyses");
        return vatAnalysisRepository.findAll();
    }

    /**
     * {@code GET  /vat-analyses/:id} : get the "id" vatAnalysis.
     *
     * @param id the id of the vatAnalysis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vatAnalysis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vat-analyses/{id}")
    public ResponseEntity<VatAnalysis> getVatAnalysis(@PathVariable Long id) {
        log.debug("REST request to get VatAnalysis : {}", id);
        Optional<VatAnalysis> vatAnalysis = vatAnalysisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vatAnalysis);
    }

    /**
     * {@code DELETE  /vat-analyses/:id} : delete the "id" vatAnalysis.
     *
     * @param id the id of the vatAnalysis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vat-analyses/{id}")
    public ResponseEntity<Void> deleteVatAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete VatAnalysis : {}", id);
        vatAnalysisRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
