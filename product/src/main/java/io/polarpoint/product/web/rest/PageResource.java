package io.polarpoint.product.web.rest;

import io.polarpoint.product.domain.Page;
import io.polarpoint.product.service.PageService;
import io.polarpoint.product.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link io.polarpoint.product.domain.Page}.
 */
@RestController
@RequestMapping("/api")
public class PageResource {

    private final Logger log = LoggerFactory.getLogger(PageResource.class);

    private static final String ENTITY_NAME = "productPage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageService pageService;

    public PageResource(PageService pageService) {
        this.pageService = pageService;
    }

    /**
     * {@code POST  /pages} : Create a new page.
     *
     * @param page the page to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new page, or with status {@code 400 (Bad Request)} if the page has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pages")
    public ResponseEntity<Page> createPage(@Valid @RequestBody Page page) throws URISyntaxException {
        log.debug("REST request to save Page : {}", page);
        if (page.getId() != null) {
            throw new BadRequestAlertException("A new page cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Page result = pageService.save(page);
        return ResponseEntity.created(new URI("/api/pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pages} : Updates an existing page.
     *
     * @param page the page to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated page,
     * or with status {@code 400 (Bad Request)} if the page is not valid,
     * or with status {@code 500 (Internal Server Error)} if the page couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pages")
    public ResponseEntity<Page> updatePage(@Valid @RequestBody Page page) throws URISyntaxException {
        log.debug("REST request to update Page : {}", page);
        if (page.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Page result = pageService.save(page);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, page.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pages} : get all the pages.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pages in body.
     */
    @GetMapping("/pages")
    public ResponseEntity<List<Page>> getAllPages(Pageable pageable) {
        log.debug("REST request to get a page of Pages");
        Page<Page> page = pageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pages/:id} : get the "id" page.
     *
     * @param id the id of the page to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the page, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pages/{id}")
    public ResponseEntity<Page> getPage(@PathVariable Long id) {
        log.debug("REST request to get Page : {}", id);
        Optional<Page> page = pageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(page);
    }

    /**
     * {@code DELETE  /pages/:id} : delete the "id" page.
     *
     * @param id the id of the page to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pages/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable Long id) {
        log.debug("REST request to delete Page : {}", id);
        pageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
