package by.yurhilevich.WebApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name = "fio",nullable = false)
    private String fio;

    @Column(name = "position",nullable = false)
    private String position;

    @ManyToOne
    @JoinColumn(name = "fkcombineid", nullable = false)
    private Combine combine;

    @OneToMany(mappedBy = "employee")
    private Set<Price> prices = new HashSet<>();
}
