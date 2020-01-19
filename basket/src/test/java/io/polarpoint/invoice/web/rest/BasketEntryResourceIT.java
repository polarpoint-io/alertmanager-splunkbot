package io.polarpoint.invoice.web.rest;

import io.polarpoint.invoice.BasketApp;
import io.polarpoint.invoice.domain.BasketEntry;
import io.polarpoint.invoice.domain.Basket;
import io.polarpoint.invoice.repository.BasketEntryRepository;
import io.polarpoint.invoice.service.BasketEntryService;
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
 * Integration tests for the {@link BasketEntryResource} REST controller.
 */
@SpringBootTest(classes = BasketApp.class)
public class BasketEntryResourceIT {

    private static final String DEFAULT_BASKET_ID = "AAAAAAAAAA";
    private static final String UPDATED_BASKET_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CAN_EDIT = false;
    private static final Boolean UPDATED_CAN_EDIT = true;

    private static final Long DEFAULT_PRODICT_ID = 1L;
    private static final Long UPDATED_PRODICT_ID = 2L;

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Boolean DEFAULT_REFUNDABLE = false;
    private static final Boolean UPDATED_REFUNDABLE = true;

    private static final Boolean DEFAULT_REMOVEABLE = false;
    private static final Boolean UPDATED_REMOVEABLE = true;

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);

    private static final String DEFAULT_VAT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VAT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_ELEMENT = "AAAAAAAAAA";
    private static final String UPDATED_VAT_ELEMENT = "BBBBBBBBBB";

    @Autowired
    private BasketEntryRepository basketEntryRepository;

    @Autowired
    private BasketEntryService basketEntryService;

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

    private MockMvc restBasketEntryMockMvc;

    private BasketEntry basketEntry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BasketEntryResource basketEntryResource = new BasketEntryResource(basketEntryService);
        this.restBasketEntryMockMvc = MockMvcBuilders.standaloneSetup(basketEntryResource)
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
    public static BasketEntry createEntity(EntityManager em) {
        BasketEntry basketEntry = new BasketEntry()
            .basketId(DEFAULT_BASKET_ID)
            .canEdit(DEFAULT_CAN_EDIT)
            .prodictId(DEFAULT_PRODICT_ID)
            .quantity(DEFAULT_QUANTITY)
            .refundable(DEFAULT_REFUNDABLE)
            .removeable(DEFAULT_REMOVEABLE)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .vatCode(DEFAULT_VAT_CODE)
            .vatElement(DEFAULT_VAT_ELEMENT);
        // Add required entity
        Basket basket;
        if (TestUtil.findAll(em, Basket.class).isEmpty()) {
            basket = BasketResourceIT.createEntity(em);
            em.persist(basket);
            em.flush();
        } else {
            basket = TestUtil.findAll(em, Basket.class).get(0);
        }
        basketEntry.setBasket(basket);
        return basketEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BasketEntry createUpdatedEntity(EntityManager em) {
        BasketEntry basketEntry = new BasketEntry()
            .basketId(UPDATED_BASKET_ID)
            .canEdit(UPDATED_CAN_EDIT)
            .prodictId(UPDATED_PRODICT_ID)
            .quantity(UPDATED_QUANTITY)
            .refundable(UPDATED_REFUNDABLE)
            .removeable(UPDATED_REMOVEABLE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .unitPrice(UPDATED_UNIT_PRICE)
            .vatCode(UPDATED_VAT_CODE)
            .vatElement(UPDATED_VAT_ELEMENT);
        // Add required entity
        Basket basket;
        if (TestUtil.findAll(em, Basket.class).isEmpty()) {
            basket = BasketResourceIT.createUpdatedEntity(em);
            em.persist(basket);
            em.flush();
        } else {
            basket = TestUtil.findAll(em, Basket.class).get(0);
        }
        basketEntry.setBasket(basket);
        return basketEntry;
    }

    @BeforeEach
    public void initTest() {
        basketEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createBasketEntry() throws Exception {
        int databaseSizeBeforeCreate = basketEntryRepository.findAll().size();

        // Create the BasketEntry
        restBasketEntryMockMvc.perform(post("/api/basket-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basketEntry)))
            .andExpect(status().isCreated());

        // Validate the BasketEntry in the database
        List<BasketEntry> basketEntryList = basketEntryRepository.findAll();
        assertThat(basketEntryList).hasSize(databaseSizeBeforeCreate + 1);
        BasketEntry testBasketEntry = basketEntryList.get(basketEntryList.size() - 1);
        assertThat(testBasketEntry.getBasketId()).isEqualTo(DEFAULT_BASKET_ID);
        assertThat(testBasketEntry.isCanEdit()).isEqualTo(DEFAULT_CAN_EDIT);
        assertThat(testBasketEntry.getProdictId()).isEqualTo(DEFAULT_PRODICT_ID);
        assertThat(testBasketEntry.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBasketEntry.isRefundable()).isEqualTo(DEFAULT_REFUNDABLE);
        assertThat(testBasketEntry.isRemoveable()).isEqualTo(DEFAULT_REMOVEABLE);
        assertThat(testBasketEntry.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testBasketEntry.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testBasketEntry.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testBasketEntry.getVatCode()).isEqualTo(DEFAULT_VAT_CODE);
        assertThat(testBasketEntry.getVatElement()).isEqualTo(DEFAULT_VAT_ELEMENT);
    }

    @Test
    @Transactional
    public void createBasketEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = basketEntryRepository.findAll().size();

        // Create the BasketEntry with an existing ID
        basketEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBasketEntryMockMvc.perform(post("/api/basket-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basketEntry)))
            .andExpect(status().isBadRequest());

        // Validate the BasketEntry in the database
        List<BasketEntry> basketEntryList = basketEntryRepository.findAll();
        assertThat(basketEntryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = basketEntryRepository.findAll().size();
        // set the field null
        basketEntry.setTotalPrice(null);

        // Create the BasketEntry, which fails.

        restBasketEntryMockMvc.perform(post("/api/basket-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basketEntry)))
            .andExpect(status().isBadRequest());

        List<BasketEntry> basketEntryList = basketEntryRepository.findAll();
        assertThat(basketEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBasketEntries() throws Exception {
        // Initialize the database
        basketEntryRepository.saveAndFlush(basketEntry);

        // Get all the basketEntryList
        restBasketEntryMockMvc.perform(get("/api/basket-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basketEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].basketId").value(hasItem(DEFAULT_BASKET_ID)))
            .andExpect(jsonPath("$.[*].canEdit").value(hasItem(DEFAULT_CAN_EDIT.booleanValue())))
            .andExpect(jsonPath("$.[*].prodictId").value(hasItem(DEFAULT_PRODICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].refundable").value(hasItem(DEFAULT_REFUNDABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].removeable").value(hasItem(DEFAULT_REMOVEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].vatCode").value(hasItem(DEFAULT_VAT_CODE)))
            .andExpect(jsonPath("$.[*].vatElement").value(hasItem(DEFAULT_VAT_ELEMENT)));
    }
    
    @Test
    @Transactional
    public void getBasketEntry() throws Exception {
        // Initialize the database
        basketEntryRepository.saveAndFlush(basketEntry);

        // Get the basketEntry
        restBasketEntryMockMvc.perform(get("/api/basket-entries/{id}", basketEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(basketEntry.getId().intValue()))
            .andExpect(jsonPath("$.basketId").value(DEFAULT_BASKET_ID))
            .andExpect(jsonPath("$.canEdit").value(DEFAULT_CAN_EDIT.booleanValue()))
            .andExpect(jsonPath("$.prodictId").value(DEFAULT_PRODICT_ID.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.refundable").value(DEFAULT_REFUNDABLE.booleanValue()))
            .andExpect(jsonPath("$.removeable").value(DEFAULT_REMOVEABLE.booleanValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.vatCode").value(DEFAULT_VAT_CODE))
            .andExpect(jsonPath("$.vatElement").value(DEFAULT_VAT_ELEMENT));
    }

    @Test
    @Transactional
    public void getNonExistingBasketEntry() throws Exception {
        // Get the basketEntry
        restBasketEntryMockMvc.perform(get("/api/basket-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBasketEntry() throws Exception {
        // Initialize the database
        basketEntryService.save(basketEntry);

        int databaseSizeBeforeUpdate = basketEntryRepository.findAll().size();

        // Update the basketEntry
        BasketEntry updatedBasketEntry = basketEntryRepository.findById(basketEntry.getId()).get();
        // Disconnect from session so that the updates on updatedBasketEntry are not directly saved in db
        em.detach(updatedBasketEntry);
        updatedBasketEntry
            .basketId(UPDATED_BASKET_ID)
            .canEdit(UPDATED_CAN_EDIT)
            .prodictId(UPDATED_PRODICT_ID)
            .quantity(UPDATED_QUANTITY)
            .refundable(UPDATED_REFUNDABLE)
            .removeable(UPDATED_REMOVEABLE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .unitPrice(UPDATED_UNIT_PRICE)
            .vatCode(UPDATED_VAT_CODE)
            .vatElement(UPDATED_VAT_ELEMENT);

        restBasketEntryMockMvc.perform(put("/api/basket-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBasketEntry)))
            .andExpect(status().isOk());

        // Validate the BasketEntry in the database
        List<BasketEntry> basketEntryList = basketEntryRepository.findAll();
        assertThat(basketEntryList).hasSize(databaseSizeBeforeUpdate);
        BasketEntry testBasketEntry = basketEntryList.get(basketEntryList.size() - 1);
        assertThat(testBasketEntry.getBasketId()).isEqualTo(UPDATED_BASKET_ID);
        assertThat(testBasketEntry.isCanEdit()).isEqualTo(UPDATED_CAN_EDIT);
        assertThat(testBasketEntry.getProdictId()).isEqualTo(UPDATED_PRODICT_ID);
        assertThat(testBasketEntry.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBasketEntry.isRefundable()).isEqualTo(UPDATED_REFUNDABLE);
        assertThat(testBasketEntry.isRemoveable()).isEqualTo(UPDATED_REMOVEABLE);
        assertThat(testBasketEntry.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testBasketEntry.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBasketEntry.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testBasketEntry.getVatCode()).isEqualTo(UPDATED_VAT_CODE);
        assertThat(testBasketEntry.getVatElement()).isEqualTo(UPDATED_VAT_ELEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingBasketEntry() throws Exception {
        int databaseSizeBeforeUpdate = basketEntryRepository.findAll().size();

        // Create the BasketEntry

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBasketEntryMockMvc.perform(put("/api/basket-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basketEntry)))
            .andExpect(status().isBadRequest());

        // Validate the BasketEntry in the database
        List<BasketEntry> basketEntryList = basketEntryRepository.findAll();
        assertThat(basketEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBasketEntry() throws Exception {
        // Initialize the database
        basketEntryService.save(basketEntry);

        int databaseSizeBeforeDelete = basketEntryRepository.findAll().size();

        // Delete the basketEntry
        restBasketEntryMockMvc.perform(delete("/api/basket-entries/{id}", basketEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BasketEntry> basketEntryList = basketEntryRepository.findAll();
        assertThat(basketEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
