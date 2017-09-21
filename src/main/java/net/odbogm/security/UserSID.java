/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.odbogm.security;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserSID extends SID implements ISecurityCredentials {
    private final static Logger LOGGER = Logger.getLogger(UserSID.class .getName());
    static {
        LOGGER.setLevel(Level.INFO);
    }
    private String email;
    
    private List<GroupSID> groups;
            
    public UserSID() {
        super();
    }
    
    public UserSID(String name, String uuid) {
        super(name, uuid);
    }
    
    public void addGroup(GroupSID gsid) {
        if (this.groups==null) {
            this.groups = new ArrayList<>();
        }
        if (!this.groups.contains(gsid))
            this.groups.add(gsid);
        gsid.add(this);
    }
    
    public void removeGroup(GroupSID gsid) {
        if (this.groups!=null) {
            this.groups.remove(gsid);
            gsid.remove(this);
        }
    }
    
    /**
     * Retorna una lista con todos los UUID de los grupos a los que pertenece el usuario.
     * @return {@literal List<String::UUID>} lista de grupos a los que pertene el usuario.
     */
    @Override
    public List<String> showSecurityCredentials() {
        // recuperar todos los grupos a los que pertenece el UserSID actual.
        // FIXME: la lista retornada debería ser inmutable.
        return this.groups.stream().map(gid -> gid.getUUID()).collect(Collectors.toList());
    }
    
    /**
     * Retorna una lista con todos los grupos a los que pertenece el usuario.
     * @return lista de GroupSID
     */
    public List<GroupSID> getGroups() {
        // FIXME: existe algún riesgo en esto?
        return this.groups.stream().map(gid -> gid).collect(Collectors.toList());
    }

}
