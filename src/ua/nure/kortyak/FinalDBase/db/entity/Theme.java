package ua.nure.kortyak.FinalDBase.db.entity;

import java.util.Objects;

/**
 * Theme entity.
 *
 * @author E.Kortyak
 */
public class Theme extends Entity {

    private static final long serialVersionUID = 6709646200484901033L;

    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return "Theme [name=" + name + "]"; }

    public static Theme createTheme(String name){
        Theme theme = new Theme();
        theme.setName(name);
        return theme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return Objects.equals(name, theme.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
