package io.polarpoint.product.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.polarpoint.product.web.rest.TestUtil;

public class VatRateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VatRate.class);
        VatRate vatRate1 = new VatRate();
        vatRate1.setId(1L);
        VatRate vatRate2 = new VatRate();
        vatRate2.setId(vatRate1.getId());
        assertThat(vatRate1).isEqualTo(vatRate2);
        vatRate2.setId(2L);
        assertThat(vatRate1).isNotEqualTo(vatRate2);
        vatRate1.setId(null);
        assertThat(vatRate1).isNotEqualTo(vatRate2);
    }
}
