/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.odbogm.proxy;

import com.orientechnologies.orient.core.record.ODirection;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;
import java.util.Map;
import net.odbogm.Transaction;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
public interface ILazyMapCalls extends ILazyCalls {
    public void init(Transaction t, OVertex relatedTo, IObjectProxy parent, String field, Class<?> keyClass, Class<?> valueClass, ODirection d);
    public Map<Object,ObjectCollectionState> collectionState();
    
    
    public Map<Object, ObjectCollectionState> getEntitiesState();
    public Map<Object, ObjectCollectionState> getKeyState();
    public Map<Object, OEdge> getKeyToEdge();
}
