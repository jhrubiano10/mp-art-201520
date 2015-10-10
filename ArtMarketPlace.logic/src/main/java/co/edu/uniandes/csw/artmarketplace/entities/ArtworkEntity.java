package co.edu.uniandes.csw.artmarketplace.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * @generated
 */
@Entity
@NamedQueries(
{
        @NamedQuery(name = "ArtworkEntity.searchArtworksByStyle", 
                    query = "SELECT a FROM ArtworkEntity a WHERE UPPER(a.artworkStyle) like UPPER(:artworkStyle)"),
    
        @NamedQuery(name = "ArtworkEntity.searchArtworksBetweenPrices", 
                    query = "SELECT a FROM ArtworkEntity a WHERE a.price BETWEEN :artworkMinPrice AND :artworkMaxPrice"),
    
        @NamedQuery(name = "ArtworkEntity.searchArtistWithCheapestArtwork", 
                    query = "SELECT a FROM ArtworkEntity a WHERE UPPER(a.name) like UPPER(:artworkName) ORDER BY a.price"),
    
        @NamedQuery(name = "ArtworkEntity.searchArtworksOfAnArtist", 
                    query = "SELECT a FROM ArtworkEntity a WHERE UPPER(a.artist.name) like UPPER(:name) ORDER BY a.price"),
    
        @NamedQuery(name = "ArtworkEntity.searchCheapestArtworkOfAnArtist", 
                    query = "SELECT a FROM ArtworkEntity a WHERE UPPER(a.artist.name) like UPPER(:artistName) ORDER BY a.price")
}
        
)
public class ArtworkEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "Artwork")
    private Long id;

    private String name;

    private String picture;

    private Integer price;
    /**
     * Descuento de una obra
     */
    private Float discount;
    
    /**
     * Artwork style
     */
    private String artworkStyle;
 
    /**
     * Comentarios de la obra de arte.
     */
    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RemarkEntity> remarks;

    @ManyToOne
    private ArtistEntity artist;
    /**
     * @generated
     */
    public Long getId(){
        return id;
    }

    /**
     * @generated
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * @generated
     */
    public String getName(){
        return name;
    }

    /**
     * @generated
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @generated
     */
    public String getPicture(){
        return picture;
    }

    /**
     * @generated
     */
    public void setPicture(String picture){
        this.picture = picture;
    }

    /**
     * @generated
     */
    public Integer getPrice(){
        return price;
    }

    /**
     * @generated
     */
    public void setPrice(Integer price){
        this.price = price;
    }

    /**
     * @generated
     */
    public ArtistEntity getArtist() {
        return artist;
    }

    /**
     * @generated
     */
    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }
    /**
     * Metodo para obtener el descuento de una obra de arte
     */
    public Float getDiscount()
    {
        return discount;
    }
    /**
     * Metodo para actualizar el descuento de una obra de arte
     * 
     */
    public void setDiscount(Float discount)
    {
        this.discount = discount;
    }
    
    /**
     * Retorna los comentarios de una obra de arte.
     * @return remarks
     */
    public List<RemarkEntity> getRemarks()
    {
       return remarks;
    }

    /**
     * Establece los comentarios de una obra de arte.
     * @param remarks
     */
    public void setRemarks(List<RemarkEntity> remarks)
    {
       this.remarks = remarks;
    }

    public String getArtworkStyle() {
        return artworkStyle;
    }

    public void setArtworkStyle(String artworkStyle) {
        this.artworkStyle = artworkStyle;
    }
    
    
    
}