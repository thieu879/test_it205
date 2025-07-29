package ra.test_exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.test_exam.model.dto.request.CustomerLogin;
import ra.test_exam.model.dto.request.CustomerPrincipal;
import ra.test_exam.model.dto.response.APIResponse;
import ra.test_exam.model.dto.response.JWTResponse;
import ra.test_exam.model.entity.Customer;
import ra.test_exam.service.CustomerService;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<Customer>> registerUser(@RequestBody CustomerPrincipal customerPrincipal){
        return new ResponseEntity<>(new APIResponse<>(true,"Register customer successfully!",customerService.registerCustomer(customerPrincipal), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<JWTResponse>> login(@RequestBody CustomerLogin userLogin){
        return new ResponseEntity<>(new APIResponse<>(true,"Login successfully!",customerService.login(userLogin), HttpStatus.OK),HttpStatus.OK);
    }

}
