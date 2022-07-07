package com.redhatschool.customermanagement;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.redhatschool.customermanagement.controller.CustomerController;

@WebMvcTest({ CustomerController.class })
@ExtendWith(MockitoExtension.class)
class CustomerControllerTests {

}
