package io.polarpoint.store.web.rest;

import io.polarpoint.store.domain.SubscriptionGroup;
import io.polarpoint.store.repository.SubscriptionGroupRepository;
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
 * REST controller for managing {@link io.polarpoint.store.domain.SubscriptionGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubscriptionGroupResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionGroupResource.class);

    private static final String ENTITY_NAME = "subscriptionGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscriptionGroupRepository subscriptionGroupRepository;

    public SubscriptionGroupResource(SubscriptionGroupRepository subscriptionGroupRepository) {
        this.subscriptionGroupRepository = subscriptionGroupRepository;
    }

    /**
     * {@code POST  /subscription-groups} : Create a new subscriptionGroup.
     *
     * @param subscriptionGroup the subscriptionGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscriptionGroup, or with status {@code 400 (Bad Request)} if the subscriptionGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subscription-groups")
    public ResponseEntity<SubscriptionGroup> createSubscriptionGroup(@Valid @RequestBody SubscriptionGroup subscriptionGroup) throws URISyntaxException {
        log.debug("REST request to save SubscriptionGroup : {}", subscriptionGroup);
        if (subscriptionGroup.getId() != null) {
            throw new BadRequestAlertException("A new subscriptionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubscriptionGroup result = subscriptionGroupRepository.save(subscriptionGroup);
        return ResponseEntity.created(new URI("/api/subscription-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subscription-groups} : Updates an existing subscriptionGroup.
     *
     * @param subscriptionGroup the subscriptionGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionGroup,
     * or with status {@code 400 (Bad Request)} if the subscriptionGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subscription-groups")
    public ResponseEntity<SubscriptionGroup> updateSubscriptionGroup(@Valid @RequestBody SubscriptionGroup subscriptionGroup) throws URISyntaxException {
        log.debug("REST request to update SubscriptionGroup : {}", subscriptionGroup);
        if (subscriptionGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubscriptionGroup result = subscriptionGroupRepository.save(subscriptionGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriptionGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subscription-groups} : get all the subscriptionGroups.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscriptionGroups in body.
     */
    @GetMapping("/subscription-groups")
    public List<SubscriptionGroup> getAllSubscriptionGroups() {
        log.debug("REST request to get all SubscriptionGroups");
        return subscriptionGroupRepository.findAll();
    }

    /**
     * {@code GET  /subscription-groups/:id} : get the "id" subscriptionGroup.
     *
     * @param id the id of the subscriptionGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscriptionGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subscription-groups/{id}")
    public ResponseEntity<SubscriptionGroup> getSubscriptionGroup(@PathVariable Long id) {
        log.debug("REST request to get SubscriptionGroup : {}", id);
        Optional<SubscriptionGroup> subscriptionGroup = subscriptionGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subscriptionGroup);
    }

    /**
     * {@code DELETE  /subscription-groups/:id} : delete the "id" subscriptionGroup.
     *
     * @param id the id of the subscriptionGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subscription-groups/{id}")
    public ResponseEntity<Void> deleteSubscriptionGroup(@PathVariable Long id) {
        log.debug("REST request to delete SubscriptionGroup : {}", id);
        subscriptionGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
