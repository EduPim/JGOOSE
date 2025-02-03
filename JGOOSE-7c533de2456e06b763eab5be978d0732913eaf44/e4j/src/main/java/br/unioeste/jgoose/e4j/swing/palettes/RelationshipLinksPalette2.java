/*    */ package br.unioeste.jgoose.e4j.swing.palettes;
/*    */ 
/*    */ import br.unioeste.jgoose.e4j.shape.DecompositionMarker;
/*    */ import br.unioeste.jgoose.e4j.shape.DependencyMarker;
/*    */ import br.unioeste.jgoose.util.IStarUtils;
/*    */ import br.unioeste.jgoose.util.IStarUtils2;
/*    */ import com.mxgraph.shape.mxMarkerRegistry;
/*    */ import com.mxgraph.util.mxResources;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JTabbedPane;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class RelationshipLinksPalette2
/*    */   extends AbstractPalette
/*    */ {
/* 24 */   private static final Logger CONSOLE = Logger.getLogger("console");
/*    */   
/*    */   public RelationshipLinksPalette2(JTabbedPane libraryPane) {

/* 27 */     super(mxResources.get("Elements", "Relationship Links"), libraryPane);

/* 28 */     mxMarkerRegistry.registerMarker("dependency", new DependencyMarker());
/* 29 */     Element element = IStarUtils2.createDepndency();
/* 30 */     addEdgeTemplate(element.getAttribute("label"), new ImageIcon("resources/shapes/elements/dependency.png"), "straight;endArrow=dependency;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle", 80, 80, element);
/*    */     
/* 33 */     element = IStarUtils2.createMeansEnd();
/* 34 */     addEdgeTemplate(element.getAttribute("label"), new ImageIcon("resources/shapes/elements/meansend.png"), "straight;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle", 80, 80, element);
/*    */     
/* 36 */     element = IStarUtils2.createDecomposition();
/* 37 */     mxMarkerRegistry.registerMarker("decomposition", new DecompositionMarker());
/* 38 */     addEdgeTemplate(element.getAttribute("label"), new ImageIcon("resources/shapes/elements/decomposition.png"), "straight;endSize=10;endArrow=decomposition;noLabel=1;", 80, 80, element);

/* 33 */    ///////////////////testes

             element = IStarUtils2.createNeeded_By();
/* 34 */     addEdgeTemplate(element.getAttribute("label"), new ImageIcon("resources/shapes/elements/neededby.png"), "straight;noLabel=1;endArrow=oval;endSize=10;shape=curvedEdge;edgeStyle=curvedEdgeStyle", 80, 80, element);

             element = IStarUtils2.createQualification();
/* 34 */     addEdgeTemplate(element.getAttribute("label"), new ImageIcon("resources/shapes/elements/qualification.png"), "dashed=1;dashPattern=5 5;endArrow=none;noLabel=1;shape=curvedEdge;edgeStyle=curvedEdgeStyle", 80, 80, element);


/*    */   }
/*    */ }

