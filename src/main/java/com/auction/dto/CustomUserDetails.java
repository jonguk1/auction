package com.auction.dto;

import com.auction.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Spring Security 커스텀 사용자 정보 DTO
 */
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String username;
    private String password;
    private User.UserRole userRole;
    private boolean enabled;

    public CustomUserDetails() {
    }

    public static CustomUserDetails from(User user) {
        CustomUserDetails details = new CustomUserDetails();
        details.id = user.getId();
        details.email = user.getEmail();
        details.username = user.getUsername();
        details.password = user.getPassword();
        details.userRole = user.getUserRole();
        details.enabled = user.getUserStatus() == User.UserStatus.ACTIVE;
        return details;
    }

    // UserDetails 인터페이스 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayUsername() {
        return username;
    }

    public User.UserRole getUserRole() {
        return userRole;
    }

    // Setter 메서드들 (필요한 경우)
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(User.UserRole userRole) {
        this.userRole = userRole;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
