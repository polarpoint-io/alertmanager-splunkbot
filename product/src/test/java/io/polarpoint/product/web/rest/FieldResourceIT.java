package io.polarpoint.product.web.rest;

import io.polarpoint.product.ProductApp;
import io.polarpoint.product.domain.Field;
import io.polarpoint.product.repository.FieldRepository;
import io.polarpoint.product.service.FieldService;
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
 * Integration tests for the {@link FieldResource} REST controller.
 */
@SpringBootTest(classes = ProductApp.class)
public class FieldResourceIT {

    private static final String DEFAULT_DEFAULTZ = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULTZ = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EDITABLE = false;
    private static final Boolean UPDATED_EDITABLE = true;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final String DEFAULT_MAXS = "AAAAAAAAAA";
    private static final String UPDATED_MAXS = "BBBBBBBBBB";

    private static final String DEFAULT_MINS = "AAAAAAAAAA";
    private static final String UPDATED_MINS = "BBBBBBBBBB";

    private static final Long DEFAULT_MULTIPLE = 1L;
    private static final Long UPDATED_MULTIPLE = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATTERNZ = "AAAAAAAAAA";
    private static final String UPDATED_PATTERNZ = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldService fieldService;

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

    private MockMvc restFieldMockMvc;

    private Field field;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FieldResource fieldResource = new FieldResource(fieldService);
        this.restFieldMockMvc = MockMvcBuilders.standaloneSetup(fieldResource)
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
    public static Field createEntity(EntityManager em) {
        Field field = new Field()
            .defaultz(DEFAULT_DEFAULTZ)
            .editable(DEFAULT_EDITABLE)
            .label(DEFAULT_LABEL)
            .mandatory(DEFAULT_MANDATORY)
            .maxs(DEFAULT_MAXS)
            .mins(DEFAULT_MINS)
            .multiple(DEFAULT_MULTIPLE)
            .name(DEFAULT_NAME)
            .patternz(DEFAULT_PATTERNZ)
            .type(DEFAULT_TYPE);
        return field;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Field createUpdatedEntity(EntityManager em) {
        Field field = new Field()
            .defaultz(UPDATED_DEFAULTZ)
            .editable(UPDATED_EDITABLE)
            .label(UPDATED_LABEL)
            .mandatory(UPDATED_MANDATORY)
            .maxs(UPDATED_MAXS)
            .mins(UPDATED_MINS)
            .multiple(UPDATED_MULTIPLE)
            .name(UPDATED_NAME)
            .patternz(UPDATED_PATTERNZ)
            .type(UPDATED_TYPE);
        return field;
    }

    @BeforeEach
    public void initTest() {
        field = createEntity(em);
    }

    @Test
    @Transactional
    public void createField() throws Exception {
        int databaseSizeBeforeCreate = fieldRepository.findAll().size();

        // Create the Field
        restFieldMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(field)))
            .andExpect(status().isCreated());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeCreate + 1);
        Field testField = fieldList.get(fieldList.size() - 1);
        assertThat(testField.getDefaultz()).isEqualTo(DEFAULT_DEFAULTZ);
        assertThat(testField.isEditable()).isEqualTo(DEFAULT_EDITABLE);
        assertThat(testField.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testField.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testField.getMaxs()).isEqualTo(DEFAULT_MAXS);
        assertThat(testField.getMins()).isEqualTo(DEFAULT_MINS);
        assertThat(testField.getMultiple()).isEqualTo(DEFAULT_MULTIPLE);
        assertThat(testField.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testField.getPatternz()).isEqualTo(DEFAULT_PATTERNZ);
        assertThat(testField.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldRepository.findAll().size();

        // Create the Field with an existing ID
        field.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(field)))
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFields() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList
        restFieldMockMvc.perform(get("/api/fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(field.getId().intValue())))
            .andExpect(jsonPath("$.[*].defaultz").value(hasItem(DEFAULT_DEFAULTZ)))
            .andExpect(jsonPath("$.[*].editable").value(hasItem(DEFAULT_EDITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].maxs").value(hasItem(DEFAULT_MAXS)))
            .andExpect(jsonPath("$.[*].mins").value(hasItem(DEFAULT_MINS)))
            .andExpect(jsonPath("$.[*].multiple").value(hasItem(DEFAULT_MULTIPLE.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].patternz").value(hasItem(DEFAULT_PATTERNZ)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getField() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get the field
        restFieldMockMvc.perform(get("/api/fields/{id}", field.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(field.getId().intValue()))
            .andExpect(jsonPath("$.defaultz").value(DEFAULT_DEFAULTZ))
            .andExpect(jsonPath("$.editable").value(DEFAULT_EDITABLE.booleanValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.maxs").value(DEFAULT_MAXS))
            .andExpect(jsonPath("$.mins").value(DEFAULT_MINS))
            .andExpect(jsonPath("$.multiple").value(DEFAULT_MULTIPLE.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.patternz").value(DEFAULT_PATTERNZ))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingField() throws Exception {
        // Get the field
        restFieldMockMvc.perform(get("/api/fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateField() throws Exception {
        // Initialize the database
        fieldService.save(field);

        int databaseSizeBeforeUpdate = fieldRepository.findAll().size();

        // Update the field
        Field updatedField = fieldRepository.findById(field.getId()).get();
        // Disconnect from session so that the updates on updatedField are not directly saved in db
        em.detach(updatedField);
        updatedField
            .defaultz(UPDATED_DEFAULTZ)
            .editable(UPDATED_EDITABLE)
            .label(UPDATED_LABEL)
            .mandatory(UPDATED_MANDATORY)
            .maxs(UPDATED_MAXS)
            .mins(UPDATED_MINS)
            .multiple(UPDATED_MULTIPLE)
            .name(UPDATED_NAME)
            .patternz(UPDATED_PATTERNZ)
            .type(UPDATED_TYPE);

        restFieldMockMvc.perform(put("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedField)))
            .andExpect(status().isOk());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeUpdate);
        Field testField = fieldList.get(fieldList.size() - 1);
        assertThat(testField.getDefaultz()).isEqualTo(UPDATED_DEFAULTZ);
        assertThat(testField.isEditable()).isEqualTo(UPDATED_EDITABLE);
        assertThat(testField.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testField.isMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testField.getMaxs()).isEqualTo(UPDATED_MAXS);
        assertThat(testField.getMins()).isEqualTo(UPDATED_MINS);
        assertThat(testField.getMultiple()).isEqualTo(UPDATED_MULTIPLE);
        assertThat(testField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testField.getPatternz()).isEqualTo(UPDATED_PATTERNZ);
        assertThat(testField.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingField() throws Exception {
        int databaseSizeBeforeUpdate = fieldRepository.findAll().size();

        // Create the Field

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldMockMvc.perform(put("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(field)))
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteField() throws Exception {
        // Initialize the database
        fieldService.save(field);

        int databaseSizeBeforeDelete = fieldRepository.findAll().size();

        // Delete the field
        restFieldMockMvc.perform(delete("/api/fields/{id}", field.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
