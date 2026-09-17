
package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// FIX #5: DO NOT use @Data on User - it includes password in toString/equals/hashCode
@ToString(exclude = "password")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // hashed, never exposed in toString

    private String email;
    private String role = "ROLE_USER";
}
