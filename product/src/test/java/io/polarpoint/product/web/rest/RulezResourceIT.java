package io.polarpoint.product.web.rest;

import io.polarpoint.product.ProductApp;
import io.polarpoint.product.domain.Rulez;
import io.polarpoint.product.domain.Product;
import io.polarpoint.product.repository.RulezRepository;
import io.polarpoint.product.service.RulezService;
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
 * Integration tests for the {@link RulezResource} REST controller.
 */
@SpringBootTest(classes = ProductApp.class)
public class RulezResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RulezRepository rulezRepository;

    @Autowired
    private RulezService rulezService;

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

    private MockMvc restRulezMockMvc;

    private Rulez rulez;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RulezResource rulezResource = new RulezResource(rulezService);
        this.restRulezMockMvc = MockMvcBuilders.standaloneSetup(rulezResource)
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
    public static Rulez createEntity(EntityManager em) {
        Rulez rulez = new Rulez()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        rulez.setRulez(product);
        return rulez;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rulez createUpdatedEntity(EntityManager em) {
        Rulez rulez = new Rulez()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        rulez.setRulez(product);
        return rulez;
    }

    @BeforeEach
    public void initTest() {
        rulez = createEntity(em);
    }

    @Test
    @Transactional
    public void createRulez() throws Exception {
        int databaseSizeBeforeCreate = rulezRepository.findAll().size();

        // Create the Rulez
        restRulezMockMvc.perform(post("/api/rulezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rulez)))
            .andExpect(status().isCreated());

        // Validate the Rulez in the database
        List<Rulez> rulezList = rulezRepository.findAll();
        assertThat(rulezList).hasSize(databaseSizeBeforeCreate + 1);
        Rulez testRulez = rulezList.get(rulezList.size() - 1);
        assertThat(testRulez.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRulez.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRulezWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rulezRepository.findAll().size();

        // Create the Rulez with an existing ID
        rulez.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRulezMockMvc.perform(post("/api/rulezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rulez)))
            .andExpect(status().isBadRequest());

        // Validate the Rulez in the database
        List<Rulez> rulezList = rulezRepository.findAll();
        assertThat(rulezList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rulezRepository.findAll().size();
        // set the field null
        rulez.setName(null);

        // Create the Rulez, which fails.

        restRulezMockMvc.perform(post("/api/rulezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rulez)))
            .andExpect(status().isBadRequest());

        List<Rulez> rulezList = rulezRepository.findAll();
        assertThat(rulezList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRulezs() throws Exception {
        // Initialize the database
        rulezRepository.saveAndFlush(rulez);

        // Get all the rulezList
        restRulezMockMvc.perform(get("/api/rulezs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rulez.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getRulez() throws Exception {
        // Initialize the database
        rulezRepository.saveAndFlush(rulez);

        // Get the rulez
        restRulezMockMvc.perform(get("/api/rulezs/{id}", rulez.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rulez.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingRulez() throws Exception {
        // Get the rulez
        restRulezMockMvc.perform(get("/api/rulezs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRulez() throws Exception {
        // Initialize the database
        rulezService.save(rulez);

        int databaseSizeBeforeUpdate = rulezRepository.findAll().size();

        // Update the rulez
        Rulez updatedRulez = rulezRepository.findById(rulez.getId()).get();
        // Disconnect from session so that the updates on updatedRulez are not directly saved in db
        em.detach(updatedRulez);
        updatedRulez
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restRulezMockMvc.perform(put("/api/rulezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRulez)))
            .andExpect(status().isOk());

        // Validate the Rulez in the database
        List<Rulez> rulezList = rulezRepository.findAll();
        assertThat(rulezList).hasSize(databaseSizeBeforeUpdate);
        Rulez testRulez = rulezList.get(rulezList.size() - 1);
        assertThat(testRulez.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRulez.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRulez() throws Exception {
        int databaseSizeBeforeUpdate = rulezRepository.findAll().size();

        // Create the Rulez

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRulezMockMvc.perform(put("/api/rulezs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rulez)))
            .andExpect(status().isBadRequest());

        // Validate the Rulez in the database
        List<Rulez> rulezList = rulezRepository.findAll();
        assertThat(rulezList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRulez() throws Exception {
        // Initialize the database
        rulezService.save(rulez);

        int databaseSizeBeforeDelete = rulezRepository.findAll().size();

        // Delete the rulez
        restRulezMockMvc.perform(delete("/api/rulezs/{id}", rulez.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rulez> rulezList = rulezRepository.findAll();
        assertThat(rulezList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
