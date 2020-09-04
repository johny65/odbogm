package net.odbogm.proxy;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import java.util.Map;
import net.odbogm.Transaction;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
public interface ILazyMapCalls extends ILazyCalls {
    
    public void init(Transaction t, OrientVertex relatedTo, IObjectProxy parent, String field, Class<?> keyClass, Class<?> valueClass, Direction d);
    public Map<Object,ObjectCollectionState> collectionState();
    public Map<Object, ObjectCollectionState> getEntitiesState();
    public Map<Object, ObjectCollectionState> getKeyState();
    public Map<Object, OrientEdge> getKeyToEdge();
    public void updateKey(Object originalKey, OrientEdge edge);
    
}
