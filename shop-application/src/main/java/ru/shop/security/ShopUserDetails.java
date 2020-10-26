package ru.shop.security;

import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.shop.entities.User;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Data
public class ShopUserDetails implements UserDetails {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@NonNull
	private User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(Collections.singletonList(this.user.getRole()));
	}
	
	@Override
	public String getPassword() {
		return this.user.getPassword();
	}
	
	/**
	 * @return {@link User#getNickName()} if not null. Otherwise {@link User#getEmail()} will be returned.
	 */
	@Override
	public String getUsername() {
		return this.user.getNickName() != null ? this.user.getNickName() : this.user.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return this.user.isEnabled();
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return this.user.isLocked();
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return this.user.isLocked();
	}
	
	@Override
	public boolean isEnabled() {
		return this.user.isEnabled();
	}
}
