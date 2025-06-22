package com.tcs.training.controller;

import com.tcs.training.bean.Customer;
import com.tcs.training.service.ServiceClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceClass serviceClass;

    @Test
    void testGetCustomerByAccNumber() throws Exception {
        Customer customer = new Customer("","rahul","delhi",8999.0);

        when(serviceClass.getCustomerByAccNumber(customer.getAccountNumber())).thenReturn(Optional.of(customer));

        String accNo = customer.getAccountNumber();
        mockMvc.perform(get("/xyz/bank/customers/"+customer.getAccountNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("rahul"))
                .andExpect(jsonPath("$.branch").value("delhi"));

    }
}
