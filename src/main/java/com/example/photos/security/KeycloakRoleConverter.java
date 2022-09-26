package com.example.photos.security;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<?> roles =  Optional.ofNullable(jwt.getClaimAsMap("realm_access"))
                .map(x -> x.get("roles"))
                .filter(x -> x instanceof Collection)
                .map(Collection.class::cast)
                .orElseGet(ArrayList::new);

        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    }

}