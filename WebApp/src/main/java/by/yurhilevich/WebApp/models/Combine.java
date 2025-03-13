package by.yurhilevich.WebApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "combine")
public class Combine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long combineId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone",nullable = false)
    private String phone;

    @ManyToOne()
    @JoinColumn(name = "FKRegionID", nullable = true)
    private Region region;

    @OneToMany(mappedBy = "combine")
    private Set<Employee> employees = new HashSet<>();
}