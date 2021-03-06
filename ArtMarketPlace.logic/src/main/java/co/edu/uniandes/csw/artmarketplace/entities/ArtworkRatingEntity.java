package co.edu.uniandes.csw.artmarketplace.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * La clase ArtworkRatingEntity esta disennada para persistir las votaciones realizadas por los compradores
 * frente a las obras de arte de un artista.
 * 
 * @author lf.mendivelso10
 * @version 1.0.0
 */
@Entity
public class ArtworkRatingEntity implements Serializable{
    
    /**
     * Es el identificador unico de la votacion hecha por una comprador frente a una Obra de arte.
     */
    @Id
    @GeneratedValue(generator = "ArtworkRating")
    private Long id;
    
    /** 
     * Es el comprador quien realiza la votacion.
     */
    private ClientEntity client;
    
    @ManyToOne
    private ArtworkEntity artwork;

    /**
     * Es una metodo de acceso que retorna el identificador unico de la votacion hecha por un comprador frente a una obra de arte.
     * @return el identificador de la votacion.
     */
    public Long getId() {
        return id;
    }

    /**
     * Es un metodo de acceso que modifica el identificador unico de la votacion hecha por un comprador frente a una obra de arte.
     * @param id es el nuevo identificador de la votacion.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Es un metodo de acceso que retorna la informacion del cliente quien hizo la valoracion de la obra de arte.
     * @return el cliente que hizo la votacion.
     */
    public ClientEntity getClient() {
        return client;
    }

    /**
     * Es un metodo de acceso que modifica la informacion del cliente quien hizo la valoracion de la obra de arte.
     * @param client es el cliente que hizo la valoracion.
     */
    public void setClient(ClientEntity client) {
        this.client = client;
    }
    
    /**
     * Es un metodo de acceso que retorno la obra de arte a la cual se le hizo la calificacion
     * @return es la obra de arte calificada
     */
    public ArtworkEntity getArtwork() {
        return artwork;
    }

    /**
     * Es un metodo de accesso que modifica la informacion de la calificacion realizada por un cliente
     * frente a un obra de arte.
     * @param artwork es la informacion de la ogra de arte en cuestion.
     */
    public void setArtwork(ArtworkEntity artwork) {
        this.artwork = artwork;
    }
        
}
