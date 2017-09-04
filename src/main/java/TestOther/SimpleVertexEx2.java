/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TestOther;

import Test.*;
import net.odbogm.annotations.Ignore;
import net.odbogm.annotations.Link;
import net.odbogm.annotations.LinkList;
import net.odbogm.annotations.RemoveOrphan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import net.odbogm.annotations.FieldAttributes;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
public class SimpleVertexEx2 extends SimpleVertex {
    @Ignore
    private final static Logger LOGGER = Logger.getLogger(SimpleVertexEx2.class .getName());

    @FieldAttributes(
    mandatory = FieldAttributes.Bool.TRUE
    )
    private String svex;

    private SimpleVertexEx2 looptest;
    
    public EnumTest enumTest;
    
    @RemoveOrphan
    public SimpleVertex svinner; 
    
    public ArrayList<SimpleVertex> alSV;
    
    public ArrayList<SimpleVertexEx2> alSVE;
    
    public HashMap<String,SimpleVertex> hmSV;
    
    public SimpleVertexEx2(String svex, String s, int i, float f, boolean b, Integer oI, Float oF, Boolean oB) {
        super(s, i, f, b, oI, oF, oB);
        this.svex = svex;
        this.enumTest = EnumTest.UNO;
    }

    public SimpleVertexEx2() {
        super();
        this.svex = "default";
    }
        
    public void initEnum() {
        this.enumTest = EnumTest.UNO;
    }

    public void initArrayList(){
        this.alSV = new ArrayList<SimpleVertex>();
        this.alSV.add(new SimpleVertex());
        this.alSV.add(new SimpleVertex());
        this.alSV.add(new SimpleVertex());
    }
    
    public void initHashMap() {
        this.hmSV = new HashMap<String, SimpleVertex>();
        SimpleVertex sv = new SimpleVertex();
        this.hmSV.put("key1", sv);
        this.hmSV.put("key2", sv);
        this.hmSV.put("key3", new SimpleVertex());
    }
    
    
    public void initInner() {
        this.svinner = new SimpleVertex();
        this.svinner.setS("sv inner");
    }
    
    public void testSVEXMethod() {
        System.out.println("in SVEx");
    }

    public String getSvex() {
        return svex;
    }

    public EnumTest getEnumTest() {
        return enumTest;
    }

    public SimpleVertex getSvinner() {
        return svinner;
    }

    public ArrayList<SimpleVertex> getAlSV() {
        return alSV;
    }

    public ArrayList<SimpleVertexEx2> getAlSVE() {
        return alSVE;
    }

    public void setAlSVE(ArrayList<SimpleVertexEx2> alSVE) {
        this.alSVE = alSVE;
    }
    
    public HashMap<String, SimpleVertex> getHmSV() {
        return hmSV;
    }

    public SimpleVertexEx2 getLooptest() {
        return looptest;
    }

    public void setLooptest(SimpleVertexEx2 looptest) {
        this.looptest = looptest;
    }

        
}