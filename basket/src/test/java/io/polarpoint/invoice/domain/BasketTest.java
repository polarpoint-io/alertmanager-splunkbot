package io.polarpoint.invoice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.polarpoint.invoice.web.rest.TestUtil;

public class BasketTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Basket.class);
        Basket basket1 = new Basket();
        basket1.setId(1L);
        Basket basket2 = new Basket();
        basket2.setId(basket1.getId());
        assertThat(basket1).isEqualTo(basket2);
        basket2.setId(2L);
        assertThat(basket1).isNotEqualTo(basket2);
        basket1.setId(null);
        assertThat(basket1).isNotEqualTo(basket2);
    }
}
