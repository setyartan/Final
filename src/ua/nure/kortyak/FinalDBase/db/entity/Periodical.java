package ua.nure.kortyak.FinalDBase.db.entity;

import java.util.Objects;

/**
 * Periodical entity.
 *
 * @author E.Kortyak
 */
public class Periodical extends Entity {

    private static final long serialVersionUID = -6894599680985663309L;

    private String name;

    private long price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Periodical [name=" + name
                + ", price=" + price + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodical that = (Periodical) o;
        return price == that.price &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public static Periodical createPeriodical
            (String name, long price) {
        Periodical periodical = new Periodical();
        periodical.setName(name);
        periodical.setPrice(price);
        return periodical;
    }
}
