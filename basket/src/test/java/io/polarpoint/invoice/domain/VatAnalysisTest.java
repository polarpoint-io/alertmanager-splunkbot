package io.polarpoint.invoice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.polarpoint.invoice.web.rest.TestUtil;

public class VatAnalysisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VatAnalysis.class);
        VatAnalysis vatAnalysis1 = new VatAnalysis();
        vatAnalysis1.setId(1L);
        VatAnalysis vatAnalysis2 = new VatAnalysis();
        vatAnalysis2.setId(vatAnalysis1.getId());
        assertThat(vatAnalysis1).isEqualTo(vatAnalysis2);
        vatAnalysis2.setId(2L);
        assertThat(vatAnalysis1).isNotEqualTo(vatAnalysis2);
        vatAnalysis1.setId(null);
        assertThat(vatAnalysis1).isNotEqualTo(vatAnalysis2);
    }
}
