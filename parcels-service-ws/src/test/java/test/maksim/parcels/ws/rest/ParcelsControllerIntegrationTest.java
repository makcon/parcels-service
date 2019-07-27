package test.maksim.parcels.ws.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import test.maksim.parcels.constants.Endpoints;
import test.maksim.parcels.dto.Shipment;
import test.maksim.parcels.dto.Tracking;
import test.maksim.parcels.ws.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ParcelsControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void register_shouldReturnStatusOk() throws Exception {
        var shipment = new Shipment("ref", List.of());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(Endpoints.REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shipment));

        mvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void push_shouldReturnStatusOk() throws Exception {
        var tracking = Tracking.builder().reference("ref").build();
        MockHttpServletRequestBuilder requestBuilder = put(Endpoints.PUSH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tracking));

        mvc.perform(requestBuilder).andExpect(status().isOk());
    }
}