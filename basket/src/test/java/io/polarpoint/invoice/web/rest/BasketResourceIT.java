package io.polarpoint.invoice.web.rest;

import io.polarpoint.invoice.BasketApp;
import io.polarpoint.invoice.domain.Basket;
import io.polarpoint.invoice.repository.BasketRepository;
import io.polarpoint.invoice.service.BasketService;
import io.polarpoint.invoice.web.rest.errors.ExceptionTranslator;

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

import static io.polarpoint.invoice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BasketResource} REST controller.
 */
@SpringBootTest(classes = BasketApp.class)
public class BasketResourceIT {

    private static final String DEFAULT_BASKET_ID = "AAAAAAAAAA";
    private static final String UPDATED_BASKET_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    private static final String DEFAULT_TXNS = "AAAAAAAAAA";
    private static final String UPDATED_TXNS = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_ANALYSIS = "AAAAAAAAAA";
    private static final String UPDATED_VAT_ANALYSIS = "BBBBBBBBBB";

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketService basketService;

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

    private MockMvc restBasketMockMvc;

    private Basket basket;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BasketResource basketResource = new BasketResource(basketService);
        this.restBasketMockMvc = MockMvcBuilders.standaloneSetup(basketResource)
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
    public static Basket createEntity(EntityManager em) {
        Basket basket = new Basket()
            .basketId(DEFAULT_BASKET_ID)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .txns(DEFAULT_TXNS)
            .vatAnalysis(DEFAULT_VAT_ANALYSIS);
        return basket;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Basket createUpdatedEntity(EntityManager em) {
        Basket basket = new Basket()
            .basketId(UPDATED_BASKET_ID)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .txns(UPDATED_TXNS)
            .vatAnalysis(UPDATED_VAT_ANALYSIS);
        return basket;
    }

    @BeforeEach
    public void initTest() {
        basket = createEntity(em);
    }

    @Test
    @Transactional
    public void createBasket() throws Exception {
        int databaseSizeBeforeCreate = basketRepository.findAll().size();

        // Create the Basket
        restBasketMockMvc.perform(post("/api/baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basket)))
            .andExpect(status().isCreated());

        // Validate the Basket in the database
        List<Basket> basketList = basketRepository.findAll();
        assertThat(basketList).hasSize(databaseSizeBeforeCreate + 1);
        Basket testBasket = basketList.get(basketList.size() - 1);
        assertThat(testBasket.getBasketId()).isEqualTo(DEFAULT_BASKET_ID);
        assertThat(testBasket.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testBasket.getTxns()).isEqualTo(DEFAULT_TXNS);
        assertThat(testBasket.getVatAnalysis()).isEqualTo(DEFAULT_VAT_ANALYSIS);
    }

    @Test
    @Transactional
    public void createBasketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = basketRepository.findAll().size();

        // Create the Basket with an existing ID
        basket.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBasketMockMvc.perform(post("/api/baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basket)))
            .andExpect(status().isBadRequest());

        // Validate the Basket in the database
        List<Basket> basketList = basketRepository.findAll();
        assertThat(basketList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBasketIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = basketRepository.findAll().size();
        // set the field null
        basket.setBasketId(null);

        // Create the Basket, which fails.

        restBasketMockMvc.perform(post("/api/baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basket)))
            .andExpect(status().isBadRequest());

        List<Basket> basketList = basketRepository.findAll();
        assertThat(basketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = basketRepository.findAll().size();
        // set the field null
        basket.setTotalPrice(null);

        // Create the Basket, which fails.

        restBasketMockMvc.perform(post("/api/baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basket)))
            .andExpect(status().isBadRequest());

        List<Basket> basketList = basketRepository.findAll();
        assertThat(basketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBaskets() throws Exception {
        // Initialize the database
        basketRepository.saveAndFlush(basket);

        // Get all the basketList
        restBasketMockMvc.perform(get("/api/baskets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basket.getId().intValue())))
            .andExpect(jsonPath("$.[*].basketId").value(hasItem(DEFAULT_BASKET_ID)))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].txns").value(hasItem(DEFAULT_TXNS)))
            .andExpect(jsonPath("$.[*].vatAnalysis").value(hasItem(DEFAULT_VAT_ANALYSIS)));
    }
    
    @Test
    @Transactional
    public void getBasket() throws Exception {
        // Initialize the database
        basketRepository.saveAndFlush(basket);

        // Get the basket
        restBasketMockMvc.perform(get("/api/baskets/{id}", basket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(basket.getId().intValue()))
            .andExpect(jsonPath("$.basketId").value(DEFAULT_BASKET_ID))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.txns").value(DEFAULT_TXNS))
            .andExpect(jsonPath("$.vatAnalysis").value(DEFAULT_VAT_ANALYSIS));
    }

    @Test
    @Transactional
    public void getNonExistingBasket() throws Exception {
        // Get the basket
        restBasketMockMvc.perform(get("/api/baskets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBasket() throws Exception {
        // Initialize the database
        basketService.save(basket);

        int databaseSizeBeforeUpdate = basketRepository.findAll().size();

        // Update the basket
        Basket updatedBasket = basketRepository.findById(basket.getId()).get();
        // Disconnect from session so that the updates on updatedBasket are not directly saved in db
        em.detach(updatedBasket);
        updatedBasket
            .basketId(UPDATED_BASKET_ID)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .txns(UPDATED_TXNS)
            .vatAnalysis(UPDATED_VAT_ANALYSIS);

        restBasketMockMvc.perform(put("/api/baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBasket)))
            .andExpect(status().isOk());

        // Validate the Basket in the database
        List<Basket> basketList = basketRepository.findAll();
        assertThat(basketList).hasSize(databaseSizeBeforeUpdate);
        Basket testBasket = basketList.get(basketList.size() - 1);
        assertThat(testBasket.getBasketId()).isEqualTo(UPDATED_BASKET_ID);
        assertThat(testBasket.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testBasket.getTxns()).isEqualTo(UPDATED_TXNS);
        assertThat(testBasket.getVatAnalysis()).isEqualTo(UPDATED_VAT_ANALYSIS);
    }

    @Test
    @Transactional
    public void updateNonExistingBasket() throws Exception {
        int databaseSizeBeforeUpdate = basketRepository.findAll().size();

        // Create the Basket

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBasketMockMvc.perform(put("/api/baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basket)))
            .andExpect(status().isBadRequest());

        // Validate the Basket in the database
        List<Basket> basketList = basketRepository.findAll();
        assertThat(basketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBasket() throws Exception {
        // Initialize the database
        basketService.save(basket);

        int databaseSizeBeforeDelete = basketRepository.findAll().size();

        // Delete the basket
        restBasketMockMvc.perform(delete("/api/baskets/{id}", basket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Basket> basketList = basketRepository.findAll();
        assertThat(basketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
