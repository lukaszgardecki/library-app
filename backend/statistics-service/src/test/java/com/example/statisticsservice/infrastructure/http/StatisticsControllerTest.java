package com.example.statisticsservice.infrastructure.http;

import com.example.statisticsservice.core.StatisticsFacade;
import com.example.statisticsservice.infrastructure.http.dto.BorrowerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatisticsController.class)
//@SpringBootTest
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StatisticsFacade statisticsFacade;

    @Test
    void shouldReturnBorrowersList() throws Exception {
        // given
        given(statisticsFacade.getTop10Borrowers()).willReturn(Stream.of(
                new BorrowerDto(1L, 123L, "Jan", "Kowalski", 3),
                new BorrowerDto(2L, 234L, "Anna", "Nowak", 2)
        ).map(BorrowerMapper::toModel).toList());

        // when & then
        mockMvc.perform(get("/statistics/borrowers/top10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Jan"))
                .andExpect(jsonPath("$[1].lastName").value("Nowak"));
    }
}
