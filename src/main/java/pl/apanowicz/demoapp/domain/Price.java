package pl.apanowicz.demoapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class Price {

    @Min(0)
    @NotNull(message = "Amount cannot be null")
    private double amount;

    @NotNull(message = "Currency cannot be null")
    private Currency currency;

    @JsonCreator
    public Price(int amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount(){
        return amount;
    }

    public Currency getCurrency(){
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price that = (Price) o;
        return Objects.equals(currency, that.currency) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
