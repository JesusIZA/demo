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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "profile")
@DynamicUpdate
public class Profile {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name="seq-gen1", sequenceName="MY_SEQ_GEN1", initialValue=205, allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq-gen1")
    private Integer id;

    @OneToOne(mappedBy = "profile")
    private User user;
}
