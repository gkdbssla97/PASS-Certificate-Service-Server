package com.example.miniProj2.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="authorization", length= 20)
    private String authorization; // AccessToken
    @Column(name ="encryptKey", length= 50)
    private String encryptKey; // encryptKey
}
