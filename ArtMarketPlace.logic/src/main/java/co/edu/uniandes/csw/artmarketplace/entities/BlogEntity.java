/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artmarketplace.entities;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jh.rubiano10
 */
/**
 * Query que trae las entradas de un artisita dado el id del mismo.
 *
 * @author jh.rubiano10
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Blog.getEntryArtist", query = "select u from BlogEntity u WHERE u.clientId = :idArtist"),
    @NamedQuery(name = "Blog.searchBlog", query = "select u from BlogEntity u WHERE u.clientId = :idArtist AND LOWER(u.title) like :search")  
})
public class BlogEntity implements Serializable {

    /**
     * Id de la entrada
     */
    @Id
    @GeneratedValue(generator = "Blog")
    private Long id;

    /**
     * Texto de la entrada, en este caso de tipo TEXT para permitir más de 255
     * carácteres
     */
    private String entry;

    private String title;

    @Temporal(TemporalType.DATE)
    private Calendar dateEntry;

    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(Calendar dateEntry) {
        this.dateEntry = dateEntry;
    }

    

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
