package ua.nure.kortyak.FinalDBase.db.entity;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field.
 *
 * @author E.Kortyak
 *
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 2150916225796845332L;

    private Long id;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}

