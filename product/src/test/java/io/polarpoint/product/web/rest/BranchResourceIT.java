package io.polarpoint.product.web.rest;

import io.polarpoint.product.ProductApp;
import io.polarpoint.product.domain.Branch;
import io.polarpoint.product.repository.BranchRepository;
import io.polarpoint.product.service.BranchService;
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
 * Integration tests for the {@link BranchResource} REST controller.
 */
@SpringBootTest(classes = ProductApp.class)
public class BranchResourceIT {

    private static final String DEFAULT_BRANCH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_FAD = "AAAAAAAAAA";
    private static final String UPDATED_FAD = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchService branchService;

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

    private MockMvc restBranchMockMvc;

    private Branch branch;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BranchResource branchResource = new BranchResource(branchService);
        this.restBranchMockMvc = MockMvcBuilders.standaloneSetup(branchResource)
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
    public static Branch createEntity(EntityManager em) {
        Branch branch = new Branch()
            .branchAddress(DEFAULT_BRANCH_ADDRESS)
            .fad(DEFAULT_FAD)
            .name(DEFAULT_NAME);
        return branch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createUpdatedEntity(EntityManager em) {
        Branch branch = new Branch()
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .fad(UPDATED_FAD)
            .name(UPDATED_NAME);
        return branch;
    }

    @BeforeEach
    public void initTest() {
        branch = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranch() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();

        // Create the Branch
        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isCreated());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate + 1);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchAddress()).isEqualTo(DEFAULT_BRANCH_ADDRESS);
        assertThat(testBranch.getFad()).isEqualTo(DEFAULT_FAD);
        assertThat(testBranch.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBranchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();

        // Create the Branch with an existing ID
        branch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBranchAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setBranchAddress(null);

        // Create the Branch, which fails.

        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFadIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setFad(null);

        // Create the Branch, which fails.

        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setName(null);

        // Create the Branch, which fails.

        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBranches() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList
        restBranchMockMvc.perform(get("/api/branches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branch.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchAddress").value(hasItem(DEFAULT_BRANCH_ADDRESS)))
            .andExpect(jsonPath("$.[*].fad").value(hasItem(DEFAULT_FAD)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get the branch
        restBranchMockMvc.perform(get("/api/branches/{id}", branch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(branch.getId().intValue()))
            .andExpect(jsonPath("$.branchAddress").value(DEFAULT_BRANCH_ADDRESS))
            .andExpect(jsonPath("$.fad").value(DEFAULT_FAD))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingBranch() throws Exception {
        // Get the branch
        restBranchMockMvc.perform(get("/api/branches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranch() throws Exception {
        // Initialize the database
        branchService.save(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch
        Branch updatedBranch = branchRepository.findById(branch.getId()).get();
        // Disconnect from session so that the updates on updatedBranch are not directly saved in db
        em.detach(updatedBranch);
        updatedBranch
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .fad(UPDATED_FAD)
            .name(UPDATED_NAME);

        restBranchMockMvc.perform(put("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBranch)))
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchAddress()).isEqualTo(UPDATED_BRANCH_ADDRESS);
        assertThat(testBranch.getFad()).isEqualTo(UPDATED_FAD);
        assertThat(testBranch.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Create the Branch

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchMockMvc.perform(put("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBranch() throws Exception {
        // Initialize the database
        branchService.save(branch);

        int databaseSizeBeforeDelete = branchRepository.findAll().size();

        // Delete the branch
        restBranchMockMvc.perform(delete("/api/branches/{id}", branch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
