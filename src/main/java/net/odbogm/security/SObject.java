/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.odbogm.security;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.odbogm.LogginProperties;
import net.odbogm.annotations.Entity;
import net.odbogm.annotations.Ignore;

@Entity
public abstract class SObject {

    private final static Logger LOGGER = Logger.getLogger(SObject.class.getName());
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(LogginProperties.SObject);
        }
    }
    
    
    private ISID __owner;
    // Access Control List
    private Map<String, Integer> __acl = new HashMap<>();
    ;
    
    /**
     * Estados internos del objeto:
     * 0: sin acceso
     * 1: Read
     * 2: write
     * 4: delete
     * 8: list
     */ 
    @Ignore
    private int __state = 0;
    private SObject __inherit = null;

    public SObject() {
    }

    public SObject(UserSID owner) {
        this.__owner = owner;
    }
    
    public SObject(GroupSID owner) {
        this.__owner = owner;
    }

    final void setState(int s) {
        this.__state = s;
    }

    final int getState() {
        return this.__state;
    }

    /**
     * Add or update de AccessRight for the specified SID.
     *
     * @param sid the Security ID
     * @param ar the AccessRight to set
     * @return this SObject reference
     */
    public final SObject setAcl(GroupSID sid, AccessRight ar) {
        __acl.put(sid.getUUID(), ar.getRights());
        return this;
    }
    
    public final SObject setAcl(UserSID sid, AccessRight ar) {
        __acl.put(sid.getUUID(), ar.getRights());
        return this;
    }

    public final void removeAcl(GroupSID sid) {
        if (__acl != null) {
            __acl.remove(sid.getUUID());
        }
    }
    
    public final void removeAcl(UserSID sid) {
        if (__acl != null) {
            __acl.remove(sid.getUUID());
        }
    }

    public final SObject setOwner(UserSID o) {
        this.__owner = o;
        return this;
    }

    public final ISID getOwner() {
        return this.__owner;
    }
    
    /**
     * Validate all groups aganis the acls and return the final state of the object
     *
     * @param sc SecurityCredential
     * @return the security state computed.
     */
    public final int validate(ISecurityCredentials sc) {
        LOGGER.log(Level.FINER, "validando los permisos de acceso...");
        int partialState = 0;
        Integer gal = 0;
        HashMap<String, Integer> acls = this.getAcls();
        LOGGER.log(Level.FINER, "Lista de acls: "+acls.size()+" : "+acls);
        if (acls.size() != 0) {
            for (String securityCredential : sc.showSecurityCredentials()) {
                gal = acls.get(securityCredential);
                LOGGER.log(Level.FINER, "SecurityCredential access: "+securityCredential+" "+gal);
                if (gal != null) {
                    if (gal == AccessRight.NOACCESS) {
                        partialState = 0;
                        break;
                    }
                    partialState |= gal;
                }
            }
        } else {
            // si no hay ACLs definidos, se conceden todos los permisos por defectos.
            partialState = Integer.MAX_VALUE;
        }
        this.__state = partialState;

        return this.__state;
    }

    /**
     * Devuelve el estado de seguridad actual del objeto.
     *
     * @return devuelve el SecurityState actual del objeto
     */
    public final int getSecurityState() {
        return this.__state;
    }

    /**
     * Retorna una copia de los ACLs establecidos para el objecto
     *
     * @return {@literal Map<String,Integer>} de los acls
     */
    public final HashMap<String, Integer> getAcls() {
        HashMap<String, Integer> acls = new HashMap<>();

        if (this.__inherit != null) {
            acls.putAll(this.__inherit.getAcls());
        }
        acls.putAll(this.__acl);

        return acls;
    }

    /**
     * Establece el objecto desde el que se heredan los permisos.
     *
     * @param so objecto desde el que se heredan los permisos.
     */
    public final void setInheritFrom(SObject so) {
        this.__inherit = so;
    }
}
