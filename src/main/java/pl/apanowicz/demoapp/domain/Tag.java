package pl.apanowicz.demoapp.domain;

import java.util.Objects;

public class Tag {

    private final String name;

    public Tag(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag that = (Tag) o;
        return Objects.equals(name, that.name);
    }
}
