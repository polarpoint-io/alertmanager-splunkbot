package io.polarpoint.product.web.rest;

import io.polarpoint.product.domain.Branch;
import io.polarpoint.product.service.BranchService;
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
 * REST controller for managing {@link io.polarpoint.product.domain.Branch}.
 */
@RestController
@RequestMapping("/api")
public class BranchResource {

    private final Logger log = LoggerFactory.getLogger(BranchResource.class);

    private static final String ENTITY_NAME = "productBranch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BranchService branchService;

    public BranchResource(BranchService branchService) {
        this.branchService = branchService;
    }

    /**
     * {@code POST  /branches} : Create a new branch.
     *
     * @param branch the branch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new branch, or with status {@code 400 (Bad Request)} if the branch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/branches")
    public ResponseEntity<Branch> createBranch(@Valid @RequestBody Branch branch) throws URISyntaxException {
        log.debug("REST request to save Branch : {}", branch);
        if (branch.getId() != null) {
            throw new BadRequestAlertException("A new branch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Branch result = branchService.save(branch);
        return ResponseEntity.created(new URI("/api/branches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /branches} : Updates an existing branch.
     *
     * @param branch the branch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated branch,
     * or with status {@code 400 (Bad Request)} if the branch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the branch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/branches")
    public ResponseEntity<Branch> updateBranch(@Valid @RequestBody Branch branch) throws URISyntaxException {
        log.debug("REST request to update Branch : {}", branch);
        if (branch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Branch result = branchService.save(branch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, branch.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /branches} : get all the branches.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of branches in body.
     */
    @GetMapping("/branches")
    public List<Branch> getAllBranches() {
        log.debug("REST request to get all Branches");
        return branchService.findAll();
    }

    /**
     * {@code GET  /branches/:id} : get the "id" branch.
     *
     * @param id the id of the branch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the branch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/branches/{id}")
    public ResponseEntity<Branch> getBranch(@PathVariable Long id) {
        log.debug("REST request to get Branch : {}", id);
        Optional<Branch> branch = branchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(branch);
    }

    /**
     * {@code DELETE  /branches/:id} : delete the "id" branch.
     *
     * @param id the id of the branch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/branches/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        log.debug("REST request to delete Branch : {}", id);
        branchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
