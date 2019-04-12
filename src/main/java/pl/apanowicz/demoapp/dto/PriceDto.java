package pl.apanowicz.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.apanowicz.demoapp.domain.Currency;
import pl.apanowicz.demoapp.domain.Price;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class PriceDto {

    @Min(0)
    @NotNull(message = "Amount cannot be null")
    private double amount;

    @NotNull(message = "Currency cannot be null")
    private Currency currency;

    @JsonCreator
    public PriceDto(@JsonProperty("amount") double amount,
                    @JsonProperty("currency") Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public PriceDto(Price price) {
        this.amount = price.getAmount();
        this.currency = price.getCurrency();
    }

    public double getAmount(){
        return this.amount;
    }

    public Currency getCurrency(){
        return this.currency;
    }

    @Override
    public String toString() {
        return "PriceDto{" +
                "amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceDto that = (PriceDto) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency);
    }
}
