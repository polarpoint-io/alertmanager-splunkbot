package io.polarpoint.product.web.rest;

import io.polarpoint.product.ProductApp;
import io.polarpoint.product.domain.Token;
import io.polarpoint.product.domain.Product;
import io.polarpoint.product.repository.TokenRepository;
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
 * Integration tests for the {@link TokenResource} REST controller.
 */
@SpringBootTest(classes = ProductApp.class)
public class TokenResourceIT {

    private static final String DEFAULT_CLIENT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final Long DEFAULT_IIN = 1L;
    private static final Long UPDATED_IIN = 2L;

    private static final Long DEFAULT_ITEM_ID = 1L;
    private static final Long UPDATED_ITEM_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SVC_START = 1L;
    private static final Long UPDATED_SVC_START = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private TokenRepository tokenRepository;

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

    private MockMvc restTokenMockMvc;

    private Token token;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TokenResource tokenResource = new TokenResource(tokenRepository);
        this.restTokenMockMvc = MockMvcBuilders.standaloneSetup(tokenResource)
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
    public static Token createEntity(EntityManager em) {
        Token token = new Token()
            .clientAccountName(DEFAULT_CLIENT_ACCOUNT_NAME)
            .clientId(DEFAULT_CLIENT_ID)
            .iin(DEFAULT_IIN)
            .itemId(DEFAULT_ITEM_ID)
            .name(DEFAULT_NAME)
            .svcStart(DEFAULT_SVC_START)
            .type(DEFAULT_TYPE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        token.setToken(product);
        return token;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Token createUpdatedEntity(EntityManager em) {
        Token token = new Token()
            .clientAccountName(UPDATED_CLIENT_ACCOUNT_NAME)
            .clientId(UPDATED_CLIENT_ID)
            .iin(UPDATED_IIN)
            .itemId(UPDATED_ITEM_ID)
            .name(UPDATED_NAME)
            .svcStart(UPDATED_SVC_START)
            .type(UPDATED_TYPE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        token.setToken(product);
        return token;
    }

    @BeforeEach
    public void initTest() {
        token = createEntity(em);
    }

    @Test
    @Transactional
    public void createToken() throws Exception {
        int databaseSizeBeforeCreate = tokenRepository.findAll().size();

        // Create the Token
        restTokenMockMvc.perform(post("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(token)))
            .andExpect(status().isCreated());

        // Validate the Token in the database
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeCreate + 1);
        Token testToken = tokenList.get(tokenList.size() - 1);
        assertThat(testToken.getClientAccountName()).isEqualTo(DEFAULT_CLIENT_ACCOUNT_NAME);
        assertThat(testToken.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testToken.getIin()).isEqualTo(DEFAULT_IIN);
        assertThat(testToken.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testToken.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testToken.getSvcStart()).isEqualTo(DEFAULT_SVC_START);
        assertThat(testToken.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tokenRepository.findAll().size();

        // Create the Token with an existing ID
        token.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTokenMockMvc.perform(post("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(token)))
            .andExpect(status().isBadRequest());

        // Validate the Token in the database
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClientAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tokenRepository.findAll().size();
        // set the field null
        token.setClientAccountName(null);

        // Create the Token, which fails.

        restTokenMockMvc.perform(post("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(token)))
            .andExpect(status().isBadRequest());

        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTokens() throws Exception {
        // Initialize the database
        tokenRepository.saveAndFlush(token);

        // Get all the tokenList
        restTokenMockMvc.perform(get("/api/tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(token.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientAccountName").value(hasItem(DEFAULT_CLIENT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].iin").value(hasItem(DEFAULT_IIN.intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].svcStart").value(hasItem(DEFAULT_SVC_START.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getToken() throws Exception {
        // Initialize the database
        tokenRepository.saveAndFlush(token);

        // Get the token
        restTokenMockMvc.perform(get("/api/tokens/{id}", token.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(token.getId().intValue()))
            .andExpect(jsonPath("$.clientAccountName").value(DEFAULT_CLIENT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.iin").value(DEFAULT_IIN.intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.svcStart").value(DEFAULT_SVC_START.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingToken() throws Exception {
        // Get the token
        restTokenMockMvc.perform(get("/api/tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateToken() throws Exception {
        // Initialize the database
        tokenRepository.saveAndFlush(token);

        int databaseSizeBeforeUpdate = tokenRepository.findAll().size();

        // Update the token
        Token updatedToken = tokenRepository.findById(token.getId()).get();
        // Disconnect from session so that the updates on updatedToken are not directly saved in db
        em.detach(updatedToken);
        updatedToken
            .clientAccountName(UPDATED_CLIENT_ACCOUNT_NAME)
            .clientId(UPDATED_CLIENT_ID)
            .iin(UPDATED_IIN)
            .itemId(UPDATED_ITEM_ID)
            .name(UPDATED_NAME)
            .svcStart(UPDATED_SVC_START)
            .type(UPDATED_TYPE);

        restTokenMockMvc.perform(put("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedToken)))
            .andExpect(status().isOk());

        // Validate the Token in the database
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeUpdate);
        Token testToken = tokenList.get(tokenList.size() - 1);
        assertThat(testToken.getClientAccountName()).isEqualTo(UPDATED_CLIENT_ACCOUNT_NAME);
        assertThat(testToken.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testToken.getIin()).isEqualTo(UPDATED_IIN);
        assertThat(testToken.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testToken.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testToken.getSvcStart()).isEqualTo(UPDATED_SVC_START);
        assertThat(testToken.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingToken() throws Exception {
        int databaseSizeBeforeUpdate = tokenRepository.findAll().size();

        // Create the Token

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenMockMvc.perform(put("/api/tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(token)))
            .andExpect(status().isBadRequest());

        // Validate the Token in the database
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteToken() throws Exception {
        // Initialize the database
        tokenRepository.saveAndFlush(token);

        int databaseSizeBeforeDelete = tokenRepository.findAll().size();

        // Delete the token
        restTokenMockMvc.perform(delete("/api/tokens/{id}", token.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Token> tokenList = tokenRepository.findAll();
        assertThat(tokenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
