 package com.example.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(unique = true,nullable = true)
    private String email;

  
    @Column(unique = true,nullable = true)
    private String username;

    
    @Column(unique = true,nullable = true)
    private String phone;

    @Column(nullable = false)
    private String password;
    
    
    private boolean emailVerified=false;
    private boolean phonelVerified=false;
    
    
    private String status="PENDING";

}
