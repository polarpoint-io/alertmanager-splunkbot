package io.polarpoint.product.web.rest;

import io.polarpoint.product.domain.Rulez;
import io.polarpoint.product.service.RulezService;
import io.polarpoint.product.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.polarpoint.product.domain.Rulez}.
 */
@RestController
@RequestMapping("/api")
public class RulezResource {

    private final Logger log = LoggerFactory.getLogger(RulezResource.class);

    private static final String ENTITY_NAME = "productRulez";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RulezService rulezService;

    public RulezResource(RulezService rulezService) {
        this.rulezService = rulezService;
    }

    /**
     * {@code POST  /rulezs} : Create a new rulez.
     *
     * @param rulez the rulez to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rulez, or with status {@code 400 (Bad Request)} if the rulez has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rulezs")
    public ResponseEntity<Rulez> createRulez(@Valid @RequestBody Rulez rulez) throws URISyntaxException {
        log.debug("REST request to save Rulez : {}", rulez);
        if (rulez.getId() != null) {
            throw new BadRequestAlertException("A new rulez cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rulez result = rulezService.save(rulez);
        return ResponseEntity.created(new URI("/api/rulezs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rulezs} : Updates an existing rulez.
     *
     * @param rulez the rulez to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rulez,
     * or with status {@code 400 (Bad Request)} if the rulez is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rulez couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rulezs")
    public ResponseEntity<Rulez> updateRulez(@Valid @RequestBody Rulez rulez) throws URISyntaxException {
        log.debug("REST request to update Rulez : {}", rulez);
        if (rulez.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rulez result = rulezService.save(rulez);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rulez.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rulezs} : get all the rulezs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rulezs in body.
     */
    @GetMapping("/rulezs")
    public List<Rulez> getAllRulezs() {
        log.debug("REST request to get all Rulezs");
        return rulezService.findAll();
    }

    /**
     * {@code GET  /rulezs/:id} : get the "id" rulez.
     *
     * @param id the id of the rulez to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rulez, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rulezs/{id}")
    public ResponseEntity<Rulez> getRulez(@PathVariable Long id) {
        log.debug("REST request to get Rulez : {}", id);
        Optional<Rulez> rulez = rulezService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rulez);
    }

    /**
     * {@code DELETE  /rulezs/:id} : delete the "id" rulez.
     *
     * @param id the id of the rulez to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rulezs/{id}")
    public ResponseEntity<Void> deleteRulez(@PathVariable Long id) {
        log.debug("REST request to delete Rulez : {}", id);
        rulezService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
