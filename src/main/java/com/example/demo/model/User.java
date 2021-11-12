package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name="seq-gen", sequenceName="MY_SEQ_GEN", initialValue=205, allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq-gen")
    private Integer id;

    @Column(name = "first_name", length = 24, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 24, nullable = false)
    private String lastName;

    @Column(name = "email", length = 36, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;
}
