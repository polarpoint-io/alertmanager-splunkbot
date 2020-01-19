package io.polarpoint.store.web.rest;

import io.polarpoint.store.HahApp;
import io.polarpoint.store.domain.VatRate;
import io.polarpoint.store.domain.Product;
import io.polarpoint.store.repository.VatRateRepository;
import io.polarpoint.store.web.rest.errors.ExceptionTranslator;

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

import static io.polarpoint.store.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VatRateResource} REST controller.
 */
@SpringBootTest(classes = HahApp.class)
public class VatRateResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_RATE = 1L;
    private static final Long UPDATED_RATE = 2L;

    @Autowired
    private VatRateRepository vatRateRepository;

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

    private MockMvc restVatRateMockMvc;

    private VatRate vatRate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VatRateResource vatRateResource = new VatRateResource(vatRateRepository);
        this.restVatRateMockMvc = MockMvcBuilders.standaloneSetup(vatRateResource)
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
    public static VatRate createEntity(EntityManager em) {
        VatRate vatRate = new VatRate()
            .code(DEFAULT_CODE)
            .rate(DEFAULT_RATE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        vatRate.setVatRate(product);
        return vatRate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VatRate createUpdatedEntity(EntityManager em) {
        VatRate vatRate = new VatRate()
            .code(UPDATED_CODE)
            .rate(UPDATED_RATE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        vatRate.setVatRate(product);
        return vatRate;
    }

    @BeforeEach
    public void initTest() {
        vatRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createVatRate() throws Exception {
        int databaseSizeBeforeCreate = vatRateRepository.findAll().size();

        // Create the VatRate
        restVatRateMockMvc.perform(post("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatRate)))
            .andExpect(status().isCreated());

        // Validate the VatRate in the database
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeCreate + 1);
        VatRate testVatRate = vatRateList.get(vatRateList.size() - 1);
        assertThat(testVatRate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVatRate.getRate()).isEqualTo(DEFAULT_RATE);
    }

    @Test
    @Transactional
    public void createVatRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vatRateRepository.findAll().size();

        // Create the VatRate with an existing ID
        vatRate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVatRateMockMvc.perform(post("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatRate)))
            .andExpect(status().isBadRequest());

        // Validate the VatRate in the database
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vatRateRepository.findAll().size();
        // set the field null
        vatRate.setCode(null);

        // Create the VatRate, which fails.

        restVatRateMockMvc.perform(post("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatRate)))
            .andExpect(status().isBadRequest());

        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVatRates() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);

        // Get all the vatRateList
        restVatRateMockMvc.perform(get("/api/vat-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vatRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())));
    }
    
    @Test
    @Transactional
    public void getVatRate() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);

        // Get the vatRate
        restVatRateMockMvc.perform(get("/api/vat-rates/{id}", vatRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vatRate.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVatRate() throws Exception {
        // Get the vatRate
        restVatRateMockMvc.perform(get("/api/vat-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVatRate() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);

        int databaseSizeBeforeUpdate = vatRateRepository.findAll().size();

        // Update the vatRate
        VatRate updatedVatRate = vatRateRepository.findById(vatRate.getId()).get();
        // Disconnect from session so that the updates on updatedVatRate are not directly saved in db
        em.detach(updatedVatRate);
        updatedVatRate
            .code(UPDATED_CODE)
            .rate(UPDATED_RATE);

        restVatRateMockMvc.perform(put("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVatRate)))
            .andExpect(status().isOk());

        // Validate the VatRate in the database
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeUpdate);
        VatRate testVatRate = vatRateList.get(vatRateList.size() - 1);
        assertThat(testVatRate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVatRate.getRate()).isEqualTo(UPDATED_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVatRate() throws Exception {
        int databaseSizeBeforeUpdate = vatRateRepository.findAll().size();

        // Create the VatRate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVatRateMockMvc.perform(put("/api/vat-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatRate)))
            .andExpect(status().isBadRequest());

        // Validate the VatRate in the database
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVatRate() throws Exception {
        // Initialize the database
        vatRateRepository.saveAndFlush(vatRate);

        int databaseSizeBeforeDelete = vatRateRepository.findAll().size();

        // Delete the vatRate
        restVatRateMockMvc.perform(delete("/api/vat-rates/{id}", vatRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VatRate> vatRateList = vatRateRepository.findAll();
        assertThat(vatRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
