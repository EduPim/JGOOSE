/*    */ package br.unioeste.jgoose.e4j.swing.palettes;
/*    */ 
/*    */ import br.unioeste.jgoose.util.IStarUtils2;
/*    */ import com.mxgraph.util.mxResources;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JTabbedPane;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActorAssociationsPaletteIStar2
/*    */   extends AbstractPalette
/*    */ {
/* 21 */   private static final Logger CONSOLE = Logger.getLogger("console");
/*    */   /*    */   
/*    */   public ActorAssociationsPaletteIStar2(JTabbedPane libraryPane) {
/* 24 */     super(mxResources.get("Elements", "Actor Associations"), libraryPane);
/*    */     
/*    */ 
/* 27 */     Element element = IStarUtils2.createIS_A();
/* 28 */     addEdgeTemplate(element.getAttribute("label"), new ImageIcon("resources/shapes/elements/connector.png"), "straight;shape=curvedEdge;edgeStyle=curvedEdgeStyle", 80, 80, element);
/* 27 */             element = IStarUtils2.createPARTICIPATES_IN();
/* 28 */     addEdgeTemplate(element.getAttribute("label"), new ImageIcon("resources/shapes/elements/connector.png"), "straight;shape=curvedEdge;edgeStyle=curvedEdgeStyle", 80, 80, element);
/*    */     
/*    */   }
/*    */ }

