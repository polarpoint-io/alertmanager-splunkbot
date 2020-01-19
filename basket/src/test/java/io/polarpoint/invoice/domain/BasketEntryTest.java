package io.polarpoint.invoice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.polarpoint.invoice.web.rest.TestUtil;

public class BasketEntryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BasketEntry.class);
        BasketEntry basketEntry1 = new BasketEntry();
        basketEntry1.setId(1L);
        BasketEntry basketEntry2 = new BasketEntry();
        basketEntry2.setId(basketEntry1.getId());
        assertThat(basketEntry1).isEqualTo(basketEntry2);
        basketEntry2.setId(2L);
        assertThat(basketEntry1).isNotEqualTo(basketEntry2);
        basketEntry1.setId(null);
        assertThat(basketEntry1).isNotEqualTo(basketEntry2);
    }
}
