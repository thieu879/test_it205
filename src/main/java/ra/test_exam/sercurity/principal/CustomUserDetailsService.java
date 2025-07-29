package ra.test_exam.sercurity.principal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.test_exam.model.entity.Customer;
import ra.test_exam.repository.CustomerRepository;

import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = userRepository.findCustomerByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Khong ton tai username"));
        return CustomUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .status(user.getStatus())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }
}
