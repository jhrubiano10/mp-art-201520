package co.edu.uniandes.csw.artmarketplace.persistence;

import co.edu.uniandes.csw.artmarketplace.entities.ArtworkEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.ejb.Stateless;

/**
 * @generated
 */
@Stateless
public class ArtworkPersistence extends CrudPersistence<ArtworkEntity> {

    /**
     * @generated
     */
    public ArtworkPersistence() {
        this.entityClass = ArtworkEntity.class;
    }
    /**
     * Search artist with cheapest artwork
     * @param artworkName
     * @return 
     */
    public List<ArtworkEntity> searchArtistWithCheapestArtwork(String artworkName) {
        List<ArtworkEntity> result = new ArrayList<ArtworkEntity>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("artworkName", artworkName);
            List<ArtworkEntity> list = new ArrayList<ArtworkEntity>();
            list = executeListNamedQuery("ArtworkEntity.searchArtistWithCheapestArtwork", params);
            result = list.subList(0, 1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    
    /**
     * Search cheapest artwork of an artist
     * @param artistName
     * @return 
     */
    public List<ArtworkEntity> searchCheapestArtworkOfAnArtist(String artistName) {
        List<ArtworkEntity> result = new ArrayList<ArtworkEntity>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("artistName", artistName);
            List<ArtworkEntity> list = new ArrayList<ArtworkEntity>();
            list = executeListNamedQuery("ArtworkEntity.searchCheapestArtworkOfAnArtist", params);
            result = list.subList(0, 1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    
   /**
     * Search cheapest artwork of an artist
     * @param name
     * @return 
     */
    public List<ArtworkEntity> searchArtworksOfAnArtist(String name) {
        List<ArtworkEntity> result = new ArrayList<ArtworkEntity>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("name", name);
            result = executeListNamedQuery("ArtworkEntity.searchArtworksOfAnArtist", params);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    
    /**
     * Search atrworks between prices
     * @param artworkMinPrice
     * @param artworkMaxPrice
     * @return 
     */
    public List<ArtworkEntity> searchArtworksBetweenPrices(int artworkMinPrice, int artworkMaxPrice) {
        List<ArtworkEntity> result = new ArrayList<ArtworkEntity>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("artworkMinPrice", artworkMinPrice);
            params.put("artworkMaxPrice", artworkMaxPrice);
            result = executeListNamedQuery("ArtworkEntity.searchArtworksBetweenPrices", params);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
