package io.polarpoint.store.web.rest;

import io.polarpoint.store.HahApp;
import io.polarpoint.store.domain.VatAnalysis;
import io.polarpoint.store.repository.VatAnalysisRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static io.polarpoint.store.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VatAnalysisResource} REST controller.
 */
@SpringBootTest(classes = HahApp.class)
public class VatAnalysisResourceIT {

    private static final String DEFAULT_VAT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VAT_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VAT_ELEMENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VAT_ELEMENT = new BigDecimal(2);

    @Autowired
    private VatAnalysisRepository vatAnalysisRepository;

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

    private MockMvc restVatAnalysisMockMvc;

    private VatAnalysis vatAnalysis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VatAnalysisResource vatAnalysisResource = new VatAnalysisResource(vatAnalysisRepository);
        this.restVatAnalysisMockMvc = MockMvcBuilders.standaloneSetup(vatAnalysisResource)
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
    public static VatAnalysis createEntity(EntityManager em) {
        VatAnalysis vatAnalysis = new VatAnalysis()
            .vatCode(DEFAULT_VAT_CODE)
            .vatElement(DEFAULT_VAT_ELEMENT);
        return vatAnalysis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VatAnalysis createUpdatedEntity(EntityManager em) {
        VatAnalysis vatAnalysis = new VatAnalysis()
            .vatCode(UPDATED_VAT_CODE)
            .vatElement(UPDATED_VAT_ELEMENT);
        return vatAnalysis;
    }

    @BeforeEach
    public void initTest() {
        vatAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createVatAnalysis() throws Exception {
        int databaseSizeBeforeCreate = vatAnalysisRepository.findAll().size();

        // Create the VatAnalysis
        restVatAnalysisMockMvc.perform(post("/api/vat-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatAnalysis)))
            .andExpect(status().isCreated());

        // Validate the VatAnalysis in the database
        List<VatAnalysis> vatAnalysisList = vatAnalysisRepository.findAll();
        assertThat(vatAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        VatAnalysis testVatAnalysis = vatAnalysisList.get(vatAnalysisList.size() - 1);
        assertThat(testVatAnalysis.getVatCode()).isEqualTo(DEFAULT_VAT_CODE);
        assertThat(testVatAnalysis.getVatElement()).isEqualTo(DEFAULT_VAT_ELEMENT);
    }

    @Test
    @Transactional
    public void createVatAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vatAnalysisRepository.findAll().size();

        // Create the VatAnalysis with an existing ID
        vatAnalysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVatAnalysisMockMvc.perform(post("/api/vat-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the VatAnalysis in the database
        List<VatAnalysis> vatAnalysisList = vatAnalysisRepository.findAll();
        assertThat(vatAnalysisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVatAnalyses() throws Exception {
        // Initialize the database
        vatAnalysisRepository.saveAndFlush(vatAnalysis);

        // Get all the vatAnalysisList
        restVatAnalysisMockMvc.perform(get("/api/vat-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vatAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].vatCode").value(hasItem(DEFAULT_VAT_CODE)))
            .andExpect(jsonPath("$.[*].vatElement").value(hasItem(DEFAULT_VAT_ELEMENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getVatAnalysis() throws Exception {
        // Initialize the database
        vatAnalysisRepository.saveAndFlush(vatAnalysis);

        // Get the vatAnalysis
        restVatAnalysisMockMvc.perform(get("/api/vat-analyses/{id}", vatAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vatAnalysis.getId().intValue()))
            .andExpect(jsonPath("$.vatCode").value(DEFAULT_VAT_CODE))
            .andExpect(jsonPath("$.vatElement").value(DEFAULT_VAT_ELEMENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVatAnalysis() throws Exception {
        // Get the vatAnalysis
        restVatAnalysisMockMvc.perform(get("/api/vat-analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVatAnalysis() throws Exception {
        // Initialize the database
        vatAnalysisRepository.saveAndFlush(vatAnalysis);

        int databaseSizeBeforeUpdate = vatAnalysisRepository.findAll().size();

        // Update the vatAnalysis
        VatAnalysis updatedVatAnalysis = vatAnalysisRepository.findById(vatAnalysis.getId()).get();
        // Disconnect from session so that the updates on updatedVatAnalysis are not directly saved in db
        em.detach(updatedVatAnalysis);
        updatedVatAnalysis
            .vatCode(UPDATED_VAT_CODE)
            .vatElement(UPDATED_VAT_ELEMENT);

        restVatAnalysisMockMvc.perform(put("/api/vat-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVatAnalysis)))
            .andExpect(status().isOk());

        // Validate the VatAnalysis in the database
        List<VatAnalysis> vatAnalysisList = vatAnalysisRepository.findAll();
        assertThat(vatAnalysisList).hasSize(databaseSizeBeforeUpdate);
        VatAnalysis testVatAnalysis = vatAnalysisList.get(vatAnalysisList.size() - 1);
        assertThat(testVatAnalysis.getVatCode()).isEqualTo(UPDATED_VAT_CODE);
        assertThat(testVatAnalysis.getVatElement()).isEqualTo(UPDATED_VAT_ELEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingVatAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = vatAnalysisRepository.findAll().size();

        // Create the VatAnalysis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVatAnalysisMockMvc.perform(put("/api/vat-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vatAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the VatAnalysis in the database
        List<VatAnalysis> vatAnalysisList = vatAnalysisRepository.findAll();
        assertThat(vatAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVatAnalysis() throws Exception {
        // Initialize the database
        vatAnalysisRepository.saveAndFlush(vatAnalysis);

        int databaseSizeBeforeDelete = vatAnalysisRepository.findAll().size();

        // Delete the vatAnalysis
        restVatAnalysisMockMvc.perform(delete("/api/vat-analyses/{id}", vatAnalysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VatAnalysis> vatAnalysisList = vatAnalysisRepository.findAll();
        assertThat(vatAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
