package ra.test_exam.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.test_exam.model.dto.request.CustomerLogin;
import ra.test_exam.model.dto.request.CustomerPrincipal;
import ra.test_exam.model.dto.response.JWTResponse;
import ra.test_exam.model.entity.Customer;
import ra.test_exam.repository.CustomerRepository;
import ra.test_exam.sercurity.jwt.JWTProvider;
import ra.test_exam.sercurity.principal.CustomUserDetails;
import ra.test_exam.service.CustomerService;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Customer registerCustomer(CustomerPrincipal customerPrincipal) {
        Customer customer = Customer.builder()
                .username(customerPrincipal.getUsername())
                .password(passwordEncoder.encode(customerPrincipal.getPassword()))
                .fullName(customerPrincipal.getFullName())
                .email(customerPrincipal.getEmail())
                .phone(customerPrincipal.getPhone())
                .status(true)
                .isLogin(false)
                .build();
        return customerRepository.save(customer);
    }

    @Override
    public JWTResponse login(CustomerLogin userLogin) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(),userLogin.getPassword()));
        }catch(AuthenticationException e){
            log.error("Sai username hoac password!");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtProvider.generateToken(userDetails.getUsername());

        return JWTResponse.builder()
                .username(userDetails.getUsername())
                .fullName(userDetails.getFullName())
                .enabled(userDetails.isEnabled())
                .email(userDetails.getEmail())
                .phone(userDetails.getPhone())
                .authorities(userDetails.getAuthorities())
                .token(token)
                .build();
    }

    @Override
    public JWTResponse logout() {
        return null;
    }


}
