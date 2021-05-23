package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "airlines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airline implements CrudEntity<Airline> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    public Airline(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Airline(String code) {
        this.code = code;
    }


    @Override
    public void throwErrorIfCreationIsNotOk() throws DomainException {
        if (name == null) throw new DomainException("Name cannot be null");
        if (code == null) throw new DomainException("Code cannot be null");
    }

    @Override
    public void throwErrorIfUpdatingIsNotOk() throws DomainException {
        if (name == null && code == null)
            throw new DomainException("No attributes were modified");
    }

    @Override
    public void updateFromEntity(Airline newData) {
        if (newData.getName() != null) name = newData.getName();
        if (newData.getCode() != null) code = newData.getCode();

    }

    @Override
    public String getSystemIdInStringFormat() {
        return getCode();
    }
}
