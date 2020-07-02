package com.pec.personalexpensescontrol.infra.security;

import com.pec.personalexpensescontrol.repository.UserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserManagementRepository userManagementRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userManagementRepository.findByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        return new org.springframework.security.core.userdetails.User(user.get().getUserName(), user.get().getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Optional<User> user) {
        var auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRole()));
        return auth;
    }

    public void save(User user) {
        userManagementRepository.save(user);
    }

    public boolean exists(String email) {
        return userManagementRepository.findByEmail(email.trim()).isPresent();
    }
}
