package com.example.miniProj2.domain;

import com.example.miniProj2.util.Constants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    @Id @GeneratedValue
    private Long id;

    @Column(name ="authorization", length= 20)
    private String authorization; // AccessToken
    @Column(name ="encryptKey", length= 50)
    private String encryptKey; // encryptKey

}
