/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artmarketplace.services;

import co.edu.uniandes.csw.artmarketplace.api.IAdminLogic;
import co.edu.uniandes.csw.artmarketplace.api.IArtistLogic;
import co.edu.uniandes.csw.artmarketplace.api.IClientLogic;
import co.edu.uniandes.csw.artmarketplace.dtos.AdminDTO;
import co.edu.uniandes.csw.artmarketplace.dtos.ArtistDTO;
import co.edu.uniandes.csw.artmarketplace.dtos.ClientDTO;
import co.edu.uniandes.csw.artmarketplace.dtos.UserDTO;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.group.GroupList;
import com.stormpath.sdk.resource.ResourceException;
import com.stormpath.shiro.realm.ApplicationRealm;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Jhonatan
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

    @Inject
    private IClientLogic clientLogic;

    @Inject
    private IArtistLogic artistLogic;

    @Inject
    private IAdminLogic adminLogic;

    @Path("/login")
    @POST
    public Response login(UserDTO user) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword(), user.isRememberMe());
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            ClientDTO client = clientLogic.getClientByUserId(currentUser.getPrincipal().toString());
            if (client != null) {
                currentUser.getSession().setAttribute("Client", client);
                return Response.ok(client).build();
            }
            ArtistDTO provider = artistLogic.getArtistByUserId(currentUser.getPrincipal().toString());
            if (provider != null) {
                currentUser.getSession().setAttribute("Artist", provider);
                return Response.ok(provider).build();
            }
            AdminDTO providerAdmin = adminLogic.getAdminByUserId(currentUser.getPrincipal().toString());
            if (providerAdmin != null) {
                currentUser.getSession().setAttribute("Admin", providerAdmin);
                return Response.ok(providerAdmin).build();
            }
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(" User is not registered")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (AuthenticationException e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @Path("/logout")
    @GET
    public Response logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
            return Response.ok().build();
        } catch (Exception e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/currentUser")
    @GET
    public Response getCurrentUser() {
        UserDTO user = new UserDTO();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            Map<String, String> userAttributes = (Map<String, String>) currentUser.getPrincipals().oneByType(java.util.Map.class);
            user.setName(userAttributes.get("givenName") + " " + userAttributes.get("surname"));
            user.setEmail(userAttributes.get("email"));
            user.setUserName(userAttributes.get("username"));
            AdminDTO admin = adminLogic.getAdminByUserId(currentUser.getPrincipal().toString());
            ArtistDTO artist = artistLogic.getArtistByUserId(currentUser.getPrincipal().toString());
            if (admin != null) {
                user.setRole("Admin");
            } else if (artist != null) {
                user.setRole("Artist");
            } else {
                user.setRole("Client");
            }
            return Response.ok(user).build();
        } catch (AuthenticationException e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @Path("/register")
    @POST
    public Response setUser(UserDTO user) {
        try {
            Account account = createUser(user);
            switch (user.getRole()) {
                case "user":
                    ClientDTO client = new ClientDTO();
                    client.setName(user.getUserName());
                    client.setUserId(account.getHref());
                    clientLogic.createClient(client);
                    break;
                case "artists":
                    ArtistDTO artist = new ArtistDTO();
                    artist.setName(user.getUserName());
                    artist.setUserId(account.getHref());
                    artistLogic.createArtist(artist);
                    break;
                default:
                    break;
            }
            return Response.ok().build();
        } catch (ResourceException e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, e);
            return Response.status(e.getStatus())
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    private Account createUser(UserDTO user) {
        ApplicationRealm realm = (ApplicationRealm) ((RealmSecurityManager) SecurityUtils.getSecurityManager()).getRealms().iterator().next();
        Client client = realm.getClient();
        Application application = client.getResource(realm.getApplicationRestUrl(), Application.class);
        Account acct = client.instantiate(Account.class);
        acct.setUsername(user.getUserName());
        acct.setPassword(user.getPassword());
        acct.setEmail(user.getEmail());
        acct.setGivenName(user.getName());
        acct.setSurname(user.getLastName());
        acct.setStatus(AccountStatus.ENABLED);
        GroupList groups = application.getGroups();
        for (Group grp : groups) {
            if (grp.getName().equals(user.getRole())) {
                acct = application.createAccount(acct);
                acct.addGroup(grp);
                break;
            }
        }
        return acct;
    }
}