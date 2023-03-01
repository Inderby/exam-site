package com.sp.fc.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(Authority.class)
public class Authority implements GrantedAuthority {
    public static final String ROLE_TEACHER = "ROLE_TEACHER";

    public static final String ROLE_STUDENT = "ROLE_STUDENT";

    @Id
    private long userId;

    @Id
    private String authority;
}
