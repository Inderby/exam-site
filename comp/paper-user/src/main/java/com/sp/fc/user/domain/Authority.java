package com.sp.fc.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

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
