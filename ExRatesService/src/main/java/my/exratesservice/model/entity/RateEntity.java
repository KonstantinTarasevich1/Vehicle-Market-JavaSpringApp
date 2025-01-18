package my.exratesservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private Double rate;


    public RateEntity() {

    }

    public RateEntity(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public RateEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public RateEntity setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public RateEntity setRate(Double rate) {
        this.rate = rate;
        return this;
    }
}
