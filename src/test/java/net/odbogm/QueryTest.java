package net.odbogm;

import com.tinkerpop.blueprints.Vertex;
import java.util.List;
import net.odbogm.utils.ODBOrientDynaElementIterable;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import test.Foo;
import test.SimpleVertex;
import test.SimpleVertexEx;

/**
 *
 * @author jbertinetti
 */
public class QueryTest {

    private SessionManager sm;


    @Before
    public void setUp() {
        sm = new SessionManager("remote:localhost/Test", "admin", "admin");
        sm.begin();
    }


    @After
    public void tearDown() {
        sm.shutdown();
    }


    /**
     * Verificar que un query simple basado en una clase devueve el listado
     * correcto de objetos.
     */
    @Test
    public void testSimpleQuery() {
        System.out.println("\n\n\n");
        System.out.println("***************************************************************");
        System.out.println("Query basado en la clase: verificar que devuelve la clase y los");
        System.out.println("subtipos de la misma");
        System.out.println("***************************************************************");
        
        SimpleVertexEx sve = new SimpleVertexEx();
        sve.initEnum();
        sve.initInner();
        sve.initArrayList();
        sve.initHashMap();

        System.out.println("guardado del objeto limpio.");
        SimpleVertexEx stored = sm.store(sve);
        sm.commit();

        System.out.println("consultando por SimpleVertex....");
        List list = sm.query(SimpleVertex.class);
        int isv = 0;
        int isve = 0;
        for (Object object : list) {
            if (object instanceof SimpleVertexEx) {
                isve++;
            } else if (object instanceof SimpleVertex) {
                isv++;
            } else {
                System.out.println("ERROR:  " + object.getClass() + 
                        " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        assertTrue(isv > 0);
        assertTrue(isve > 0);

        System.out.println("***************************************************************");
        System.out.println("Fin SimpleQuery");
        System.out.println("***************************************************************");
    }

    
    @Test
    public void directIterable() throws Exception {
        //creo un vértice asociado a otros 2
        SimpleVertex sv1 = new SimpleVertex();
        SimpleVertex sv2 = new SimpleVertex();
        Foo foo = new Foo();
        foo.add(sv1);
        foo.add(sv2);
        foo = sm.store(foo);
        sm.commit();
        String rid = sm.getRID(foo);
        
        ODBOrientDynaElementIterable<Vertex> list = sm.query(
                "select expand(out('Foo_lsve')) from (select from " + rid + ")");
        if (!list.iterator().hasNext()) {
            fail("Empty list!");
        } else {
            Vertex v = list.iterator().next();
            SimpleVertex sv = sm.get(SimpleVertex.class, v.getId().toString());
            assertNotNull(sv);
        }
    }
    
    
    @Test
    public void listQuery() throws Exception {
        Foo foo = new Foo("test query");
        foo.add(new SimpleVertex("related vertex"));
        sm.store(foo);
        sm.commit();
        sm.getTransaction().clearCache();
        
        List<Foo> res = sm.query(Foo.class, "where text = 'test query'");
        assertFalse(res.isEmpty());
        for (Foo f : res) {
            assertEquals("test query", f.getText());
            SimpleVertex sv = f.getLsve().iterator().next();
            assertEquals("related vertex", sv.getS());
        }
    }
    
}
