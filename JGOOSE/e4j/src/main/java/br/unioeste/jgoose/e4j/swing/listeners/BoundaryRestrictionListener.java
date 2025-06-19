/*    */ package br.unioeste.jgoose.e4j.swing.listeners;
/*    */ 
/*    */ import com.mxgraph.model.mxCell;
/*    */ import com.mxgraph.model.mxICell;
/*    */ import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
/*    */ import com.mxgraph.util.mxEventSource.mxIEventListener;
/*    */ import com.mxgraph.view.mxGraph;
import com.sun.org.apache.xerces.internal.impl.xs.opti.ElementImpl;
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoundaryRestrictionListener
/*    */   implements mxEventSource.mxIEventListener
/*    */ {
/*    */@Override
   public void invoke(Object o, mxEventObject eo)
/*    */   {
/* 23 */     Object[] cells = (Object[])eo.getProperty("cells");
/* 24 */     mxCell cell = (mxCell)cells[0];
/* 25 */     mxGraph graph = (mxGraph)o;
/* 26 */     if (
               //((cell.getValue() instanceof ElementImpl)) &&
/* 27 */       (cell.getParent().getId().compareTo(((mxCell)graph.getDefaultParent()).getId()) != 0) && 
/* 28 */       //role|position|agent|actor       
                (("actor".equals(cell.getAttribute("type")))||
                ("position".equals(cell.getAttribute("type")))||
                ("agent".equals(cell.getAttribute("type")))||
                ("role".equals(cell.getAttribute("type"))))
                ) {
/* 29 */       
                graph.removeCells(cells);
/* 30 */       JOptionPane.showMessageDialog(null, "An actor can not be added within the limits of another actor.");
/*    */     }
/*    */   }
/*    */ }