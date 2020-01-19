package io.polarpoint.product.web.rest;

import io.polarpoint.product.ProductApp;
import io.polarpoint.product.domain.SubscriptionGroup;
import io.polarpoint.product.domain.Category;
import io.polarpoint.product.domain.Branch;
import io.polarpoint.product.repository.SubscriptionGroupRepository;
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
 * Integration tests for the {@link SubscriptionGroupResource} REST controller.
 */
@SpringBootTest(classes = ProductApp.class)
public class SubscriptionGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubscriptionGroupRepository subscriptionGroupRepository;

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

    private MockMvc restSubscriptionGroupMockMvc;

    private SubscriptionGroup subscriptionGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubscriptionGroupResource subscriptionGroupResource = new SubscriptionGroupResource(subscriptionGroupRepository);
        this.restSubscriptionGroupMockMvc = MockMvcBuilders.standaloneSetup(subscriptionGroupResource)
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
    public static SubscriptionGroup createEntity(EntityManager em) {
        SubscriptionGroup subscriptionGroup = new SubscriptionGroup()
            .name(DEFAULT_NAME);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        subscriptionGroup.getSubscriptionGroups().add(category);
        // Add required entity
        Branch branch;
        if (TestUtil.findAll(em, Branch.class).isEmpty()) {
            branch = BranchResourceIT.createEntity(em);
            em.persist(branch);
            em.flush();
        } else {
            branch = TestUtil.findAll(em, Branch.class).get(0);
        }
        subscriptionGroup.getSubscriptionGroups().add(branch);
        return subscriptionGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionGroup createUpdatedEntity(EntityManager em) {
        SubscriptionGroup subscriptionGroup = new SubscriptionGroup()
            .name(UPDATED_NAME);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        subscriptionGroup.getSubscriptionGroups().add(category);
        // Add required entity
        Branch branch;
        if (TestUtil.findAll(em, Branch.class).isEmpty()) {
            branch = BranchResourceIT.createUpdatedEntity(em);
            em.persist(branch);
            em.flush();
        } else {
            branch = TestUtil.findAll(em, Branch.class).get(0);
        }
        subscriptionGroup.getSubscriptionGroups().add(branch);
        return subscriptionGroup;
    }

    @BeforeEach
    public void initTest() {
        subscriptionGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscriptionGroup() throws Exception {
        int databaseSizeBeforeCreate = subscriptionGroupRepository.findAll().size();

        // Create the SubscriptionGroup
        restSubscriptionGroupMockMvc.perform(post("/api/subscription-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionGroup)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionGroup in the database
        List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupRepository.findAll();
        assertThat(subscriptionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionGroup testSubscriptionGroup = subscriptionGroupList.get(subscriptionGroupList.size() - 1);
        assertThat(testSubscriptionGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubscriptionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionGroupRepository.findAll().size();

        // Create the SubscriptionGroup with an existing ID
        subscriptionGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionGroupMockMvc.perform(post("/api/subscription-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionGroup)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionGroup in the database
        List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupRepository.findAll();
        assertThat(subscriptionGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionGroupRepository.findAll().size();
        // set the field null
        subscriptionGroup.setName(null);

        // Create the SubscriptionGroup, which fails.

        restSubscriptionGroupMockMvc.perform(post("/api/subscription-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionGroup)))
            .andExpect(status().isBadRequest());

        List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupRepository.findAll();
        assertThat(subscriptionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubscriptionGroups() throws Exception {
        // Initialize the database
        subscriptionGroupRepository.saveAndFlush(subscriptionGroup);

        // Get all the subscriptionGroupList
        restSubscriptionGroupMockMvc.perform(get("/api/subscription-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSubscriptionGroup() throws Exception {
        // Initialize the database
        subscriptionGroupRepository.saveAndFlush(subscriptionGroup);

        // Get the subscriptionGroup
        restSubscriptionGroupMockMvc.perform(get("/api/subscription-groups/{id}", subscriptionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriptionGroup() throws Exception {
        // Get the subscriptionGroup
        restSubscriptionGroupMockMvc.perform(get("/api/subscription-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptionGroup() throws Exception {
        // Initialize the database
        subscriptionGroupRepository.saveAndFlush(subscriptionGroup);

        int databaseSizeBeforeUpdate = subscriptionGroupRepository.findAll().size();

        // Update the subscriptionGroup
        SubscriptionGroup updatedSubscriptionGroup = subscriptionGroupRepository.findById(subscriptionGroup.getId()).get();
        // Disconnect from session so that the updates on updatedSubscriptionGroup are not directly saved in db
        em.detach(updatedSubscriptionGroup);
        updatedSubscriptionGroup
            .name(UPDATED_NAME);

        restSubscriptionGroupMockMvc.perform(put("/api/subscription-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubscriptionGroup)))
            .andExpect(status().isOk());

        // Validate the SubscriptionGroup in the database
        List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupRepository.findAll();
        assertThat(subscriptionGroupList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionGroup testSubscriptionGroup = subscriptionGroupList.get(subscriptionGroupList.size() - 1);
        assertThat(testSubscriptionGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscriptionGroup() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionGroupRepository.findAll().size();

        // Create the SubscriptionGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionGroupMockMvc.perform(put("/api/subscription-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionGroup)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionGroup in the database
        List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupRepository.findAll();
        assertThat(subscriptionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubscriptionGroup() throws Exception {
        // Initialize the database
        subscriptionGroupRepository.saveAndFlush(subscriptionGroup);

        int databaseSizeBeforeDelete = subscriptionGroupRepository.findAll().size();

        // Delete the subscriptionGroup
        restSubscriptionGroupMockMvc.perform(delete("/api/subscription-groups/{id}", subscriptionGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupRepository.findAll();
        assertThat(subscriptionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
