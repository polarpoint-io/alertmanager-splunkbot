package io.polarpoint.product.web.rest;

import io.polarpoint.product.ProductApp;
import io.polarpoint.product.domain.Page;
import io.polarpoint.product.domain.Product;
import io.polarpoint.product.domain.Field;
import io.polarpoint.product.repository.PageRepository;
import io.polarpoint.product.service.PageService;
import io.polarpoint.product.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.polarpoint.product.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PageResource} REST controller.
 */
@SpringBootTest(classes = ProductApp.class)
public class PageResourceIT {

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_PREDICATES = "AAAAAAAAAA";
    private static final String UPDATED_PREDICATES = "BBBBBBBBBB";

    private static final String DEFAULT_PROTECTION_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_PROTECTION_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_QUOTE = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageService pageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPageMockMvc;

    private Page page;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PageResource pageResource = new PageResource(pageService);
        this.restPageMockMvc = MockMvcBuilders.standaloneSetup(pageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Page createEntity(EntityManager em) {
        Page page = new Page()
            .number(DEFAULT_NUMBER)
            .predicates(DEFAULT_PREDICATES)
            .protectionLevel(DEFAULT_PROTECTION_LEVEL)
            .quote(DEFAULT_QUOTE)
            .title(DEFAULT_TITLE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        page.setPage(product);
        // Add required entity
        Field field;
        if (TestUtil.findAll(em, Field.class).isEmpty()) {
            field = FieldResourceIT.createEntity(em);
            em.persist(field);
            em.flush();
        } else {
            field = TestUtil.findAll(em, Field.class).get(0);
        }
        page.setOrder(field);
        return page;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Page createUpdatedEntity(EntityManager em) {
        Page page = new Page()
            .number(UPDATED_NUMBER)
            .predicates(UPDATED_PREDICATES)
            .protectionLevel(UPDATED_PROTECTION_LEVEL)
            .quote(UPDATED_QUOTE)
            .title(UPDATED_TITLE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        page.setPage(product);
        // Add required entity
        Field field;
        if (TestUtil.findAll(em, Field.class).isEmpty()) {
            field = FieldResourceIT.createUpdatedEntity(em);
            em.persist(field);
            em.flush();
        } else {
            field = TestUtil.findAll(em, Field.class).get(0);
        }
        page.setOrder(field);
        return page;
    }

    @BeforeEach
    public void initTest() {
        page = createEntity(em);
    }

    @Test
    @Transactional
    public void createPage() throws Exception {
        int databaseSizeBeforeCreate = pageRepository.findAll().size();

        // Create the Page
        restPageMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(page)))
            .andExpect(status().isCreated());

        // Validate the Page in the database
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeCreate + 1);
        Page testPage = pageList.get(pageList.size() - 1);
        assertThat(testPage.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPage.getPredicates()).isEqualTo(DEFAULT_PREDICATES);
        assertThat(testPage.getProtectionLevel()).isEqualTo(DEFAULT_PROTECTION_LEVEL);
        assertThat(testPage.getQuote()).isEqualTo(DEFAULT_QUOTE);
        assertThat(testPage.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createPageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pageRepository.findAll().size();

        // Create the Page with an existing ID
        page.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(page)))
            .andExpect(status().isBadRequest());

        // Validate the Page in the database
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPages() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

        // Get all the pageList
        restPageMockMvc.perform(get("/api/pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(page.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].predicates").value(hasItem(DEFAULT_PREDICATES)))
            .andExpect(jsonPath("$.[*].protectionLevel").value(hasItem(DEFAULT_PROTECTION_LEVEL)))
            .andExpect(jsonPath("$.[*].quote").value(hasItem(DEFAULT_QUOTE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getPage() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

        // Get the page
        restPageMockMvc.perform(get("/api/pages/{id}", page.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(page.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.predicates").value(DEFAULT_PREDICATES))
            .andExpect(jsonPath("$.protectionLevel").value(DEFAULT_PROTECTION_LEVEL))
            .andExpect(jsonPath("$.quote").value(DEFAULT_QUOTE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    public void getNonExistingPage() throws Exception {
        // Get the page
        restPageMockMvc.perform(get("/api/pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePage() throws Exception {
        // Initialize the database
        pageService.save(page);

        int databaseSizeBeforeUpdate = pageRepository.findAll().size();

        // Update the page
        Page updatedPage = pageRepository.findById(page.getId()).get();
        // Disconnect from session so that the updates on updatedPage are not directly saved in db
        em.detach(updatedPage);
        updatedPage
            .number(UPDATED_NUMBER)
            .predicates(UPDATED_PREDICATES)
            .protectionLevel(UPDATED_PROTECTION_LEVEL)
            .quote(UPDATED_QUOTE)
            .title(UPDATED_TITLE);

        restPageMockMvc.perform(put("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPage)))
            .andExpect(status().isOk());

        // Validate the Page in the database
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeUpdate);
        Page testPage = pageList.get(pageList.size() - 1);
        assertThat(testPage.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPage.getPredicates()).isEqualTo(UPDATED_PREDICATES);
        assertThat(testPage.getProtectionLevel()).isEqualTo(UPDATED_PROTECTION_LEVEL);
        assertThat(testPage.getQuote()).isEqualTo(UPDATED_QUOTE);
        assertThat(testPage.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingPage() throws Exception {
        int databaseSizeBeforeUpdate = pageRepository.findAll().size();

        // Create the Page

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageMockMvc.perform(put("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(page)))
            .andExpect(status().isBadRequest());

        // Validate the Page in the database
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePage() throws Exception {
        // Initialize the database
        pageService.save(page);

        int databaseSizeBeforeDelete = pageRepository.findAll().size();

        // Delete the page
        restPageMockMvc.perform(delete("/api/pages/{id}", page.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
