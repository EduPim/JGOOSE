package br.unioeste.jgoose.controller;

import br.unioeste.jgoose.e4j.swing.EditorJFrame;
import br.unioeste.jgoose.model.IStarActorElement;
import br.unioeste.jgoose.model.IStarElement;
import br.unioeste.jgoose.model.IStarLink;
import br.unioeste.jgoose.model.TokensOpenOME;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

/**
 *
 * @author Leonardo Merlin - leonardo.merlin at unioeste.br
 */
@SuppressWarnings(value = "serial")
public class ImportIStarGraph extends AbstractAction {

    private static Logger LOG = Logger.getLogger("console");
    //
    private JFrame e4jinstance;
    private mxGraphComponent component;
    private mxGraph graph;
    private mxIGraphModel model;
    //
    private Map<mxCell, Element> vertex = new HashMap<>();
    private Map<mxCell, Element> edges = new HashMap<>();
    private Map<mxCell, Object> mapped = new HashMap<>();
    //
    private TokensOpenOME jgoose = Controller.getOme();
    //
    private Map<mxCell, mxCell> deleteds = new HashMap<>();
    //

    public ImportIStarGraph(EditorJFrame e4jInstace) {
        this.e4jinstance = e4jInstace;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.debug("Generate Use Case init.");

        // clear all previous action performed
        vertex.clear();
        edges.clear();

        jgoose = new TokensOpenOME();
        Controller.setOme(jgoose);

        Object source = e.getSource();

        if (source instanceof mxGraphComponent) {
            this.component = (mxGraphComponent) source;
            this.graph = component.getGraph();
            this.model = graph.getModel();

            Object[] cells = this.selectAll();

            // filter cells and put them in the map structure (elements and links)
            // 1) compute all vertex
            for (Object c : cells) {
                mxCell cell = (mxCell) c;

                if (!cell.isVertex()) {
                    continue;
                } else {
                    this.convertVertex(cell);
                }
            }

            //2) compute edges
            for (Object c : cells) {
                mxCell cell = (mxCell) c;

                if (!cell.isEdge()) {
                    // compute children
                    //actor, agent, role, position,
                    int children = cell.getChildCount();
                    if (children > 0) {
                        for (int i = children - 1; i >= 0; i--) {
                            mxCell child = (mxCell) cell.getChildAt(i);
                            String t = child.getAttribute("type");
                            if (t == null) {
                                LOG.debug("children removed.");
                                deleteds.put(cell, child);
                                cell.remove(child);
                            } else {
                                if (child.isEdge()) {
                                    Object result = convertEdge(child);
                                    if (result != null) {
                                        Object parent = mapped.get(cell);
                                        if (parent instanceof IStarActorElement) {
                                            IStarActorElement parentActor = (IStarActorElement) parent;
                                            parentActor.getChildrens().add((String) result);
                                        } else {
                                            LOG.error("parent instance not found.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    continue;
                } else {
                    this.convertEdge(cell);
                }
            }

            LOG.debug("total elements interpreted: " + vertex.size());
            LOG.debug("total links interpreted: " + edges.size());

            // close editor and call view to select the main actor (system).
            WindowEvent wev = new WindowEvent(this.e4jinstance, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);

            // Abre a janela para selecionar o Ator Sistema
            Controller.showActorSystemSelectionView();
        }

        LOG.debug("Generate Use Case finished.");

        //adicionar os elementos deletados
        for(mxCell c : deleteds.keySet()){
            model.add(c, deleteds.get(c), 0);
        }
        
    }

    /**
     * Root? selection.
     *
     * @return user selection or forced 'selectAll'.
     */
    private Object[] selectAll() {
        // first, get user selected cells
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();
        graph.clearSelection();
//        Object[] cells = graph.getSelectionCells();
//
//        // if none is selected, force 'selectAll()'
//        if (cells.length == 0) {
//            LOG.debug("No one cell is selected. Selecting All...");
//            //get all cells in the graph
//            //TODO: change this! its dont select all.
//            // Only the first level is selected
//            graph.selectAll();
//            cells = graph.getSelectionCells();
//            graph.clearSelection();
//        }

        LOG.debug("Total root cells founded: " + cells.length);
        return cells;
    }

    /**
     *
     * @param cell
     * @return element mapped or null with any error.
     */
    private Object convertVertex(mxCell cell) {
        Object result = null;

        Element element = null;
        Object v = cell.getValue();
        if (v instanceof Element) {
            element = (Element) v;
        } else {
            LOG.debug("Value of cell is not a Element type.");
            return null;
        }

        String tagName = element.getTagName();
        String type = element.getAttribute("type");
        if (tagName == null || type == null) {
            LOG.debug("tagname and type is null.");
            return null;
        }

        // aliases to jgoose structure
        // elements (vertex)
        ArrayList<IStarActorElement> actors = jgoose.getActors();
        ArrayList<IStarActorElement> agents = jgoose.getAgents();
        ArrayList<IStarActorElement> roles = jgoose.getRoles();
        ArrayList<IStarActorElement> positions = jgoose.getPositions();
        //
        ArrayList<IStarElement> goals = jgoose.getGoals();
        ArrayList<IStarElement> softgoals = jgoose.getSoftgoals();
        ArrayList<IStarElement> resources = jgoose.getResourcess();
        ArrayList<IStarElement> tasks = jgoose.getTasks();

        switch (tagName) {
            case "actor":
                //is a vertex
                vertex.put(cell, element);

                switch (type) {
                    case "actor":
                    case "agent":
                    case "role":
                    case "position":
                        IStarActorElement actor = new IStarActorElement();
                        actor.setName(element.getAttribute("label").replaceAll("\n", " "));
                        actor.setCod(cell.getId());
                        actors.add(actor);
                        mapped.put(cell, actor);
                        result = actor.getCod();
                        break;
                    default:
                        LOG.debug("case for "
                                + "'" + type
                                + "' actor type is not implemented yet.");
                        break;
                }

                //actor, agent, role, position,
                int children = cell.getChildCount();
                if (children > 0) {
                    for (int i = children - 1; i >= 0; i--) {
                        mxCell child = (mxCell) cell.getChildAt(i);
                        String t = child.getAttribute("type");
                        if (t == null) {
                            LOG.debug("(null) (null) children removed..");
                            deleteds.put(cell, child);
                            cell.remove(child);
                        } else {
                            if (child.isVertex()) {
                                Object resultt = convertVertex(child);
                                if (resultt != null) {
                                    Object parent = mapped.get(cell);
                                    if (parent instanceof IStarActorElement) {
                                        IStarActorElement parentActor = (IStarActorElement) parent;
                                        parentActor.getChildrens().add((String) resultt);
                                    } else {
                                        LOG.error("parent instance not found.");
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "ielement":
                //is a vertex
                vertex.put(cell, element);
                //goal, resource, task, softgoal
                IStarElement istarelement = new IStarElement();
                istarelement.setCod(cell.getId());
                istarelement.setName("" + element.getAttribute("label").replaceAll("\n", " "));
//                istarelement.set
                mapped.put(cell, istarelement);
                result = istarelement.getCod();
                switch (type) {
                    case "goal":
                        goals.add(istarelement);
                        break;
                    case "resource":
                        resources.add(istarelement);
                        break;
                    case "task":
                        tasks.add(istarelement);
                        break;
                    case "softgoal":
                        softgoals.add(istarelement);
                        break;
                    default:
                        LOG.debug("case for "
                                + "'" + type
                                + "' ielement type is not implemented yet.");
                        break;
                }
                break;
            case "actorLink":
                LOG.warn("is a vertex or a edge-actorLink?!");
                LOG.debug("is vertex?" + cell.isVertex());
                LOG.debug("is edge?" + cell.isEdge());
                LOG.debug("value:" + element.getAttribute("type"));
                break;
            case "ielementLink":
                LOG.warn("is a vertex or a edge-ielementLink?!");
                LOG.debug("is vertex?" + cell.isVertex());
                LOG.debug("is edge?" + cell.isEdge());
                LOG.debug("value:" + element.getAttribute("type"));
                break;
            case "dependency":
                LOG.warn("is a vertex or a edge-dependencyLink?!");
                LOG.debug("is vertex?" + cell.isVertex());
                LOG.debug("is edge?" + cell.isEdge());
                LOG.debug("value:" + element.getAttribute("type"));
                break;
            default:
                LOG.debug("case for "
                        + "'" + tagName
                        + "' tagname is not implemented yet.");
                break;
        }
        return result;
    }

    private Object convertEdge(mxCell cell) {
        Object result = null;
        Element element = null;
        Object v = cell.getValue();
        if (v instanceof Element) {
            element = (Element) v;
        } else {
            LOG.debug("Value of cell is not a Element type.");
            return null;
        }

        String tagName = element.getTagName();
        String type = element.getAttribute("type");

        if (tagName == null || type == null) {
            LOG.debug("tagname and type is null.");
            return null;
        }

        // aliases to jgoose structure
        // elements (edge)
        ArrayList<IStarLink> contributions = jgoose.getContributions();
        ArrayList<IStarLink> decompositions = jgoose.getDecompositions();
        ArrayList<IStarLink> meansEnds = jgoose.getMeansEnds();
        //
        ArrayList<IStarLink> dependencies = jgoose.getDependenciess();
        ArrayList<IStarLink> ins = jgoose.getInss();
        ArrayList<IStarLink> isa = jgoose.getIsas();
        ArrayList<IStarLink> isPartOf = jgoose.getIsPartOfs();
        ArrayList<IStarLink> covers = jgoose.getCoverss();
        ArrayList<IStarLink> occupies = jgoose.getOccupiess();
        ArrayList<IStarLink> plays = jgoose.getPlayss();
        //

        switch (tagName) {
            case "actor":
                LOG.warn("is a edge or a vertex-actor?!");
                break;
            case "ielement":
                LOG.warn("is a edge or a vertex-ielement?!");
                break;
            case "actorLink":
                //is a edge
                edges.put(cell, element);
                LOG.debug("actorLink");
                this.debugEdge(cell);

                // 1) get elements of this edge
                mxCell source = (mxCell) cell.getSource();
                mxCell target = (mxCell) cell.getTarget();

                // 2) get mapped elements to create a link between then
                Object mappedSource = mapped.get(source);
                if (mappedSource == null) {
                    LOG.error("mapped source dont founded.");
                }
                Object mappedTarget = mapped.get(target);
                if (mappedTarget == null) {
                    LOG.error("mapped target dont founded.");
                }

                if (mappedSource != null && mappedTarget != null) {

                    IStarLink link = this.createLink(cell);
                    mapped.put(cell, link);
                    result = link.getCod();
                    switch (type) {
                        case "is_a":
                            isa.add(link);
                            break;
                        case "is_part_of":
                            isPartOf.add(link);
                            break;
                        case "instance_of":
                            ins.add(link);
                            break;
                        case "plays":
                            plays.add(link);
                            break;
                        case "occupies":
                            occupies.add(link);
                            break;
                        case "covers":
                            covers.add(link);
                            break;
                        default:
                            LOG.debug("case for "
                                    + "'" + type
                                    + "' iactorLink type is not implemented yet.");
                            break;
                    }
                }
                break;
            case "ielementLink":
                //is a edge
                edges.put(cell, element);

                LOG.debug("ielementLink");
                this.debugEdge(cell);

                IStarLink link = this.createLink(cell);
                mapped.put(cell, link);
                result = link.getCod();
                switch (type) {
                    case "decomposition":
                        decompositions.add(link);
                        break;
                    case "meansend":
                        meansEnds.add(link);
                        break;
                    case "contribution":
                        contributions.add(link);
                        //contribution > break, hurt ...
                        break;
                    default:
                        LOG.debug("case for "
                                + "'" + type
                                + "' ielementLink type is not implemented yet.");
                        break;
                }
                break;
            case "dependency":
                //is a edge
                edges.put(cell, element);

                //dependency dont have type
                // all dependency elements must have been mapped.
                LOG.debug("dependency");

                IStarLink dependency = this.createLink(cell);
                dependencies.add(dependency);
                mapped.put(cell, dependency);
                result = dependency.getCod();

//                this.debugEdge(cell);
                break;
            default:
                LOG.debug("case for "
                        + "'" + tagName
                        + "' tagname is not implemented yet.");
                break;
        }
        return result;
    }

    private void debugGraphInfo(mxCell cell, mxIGraphModel model) {

        String id = cell.getId();
        LOG.debug("id: " + id);

        int children = model.getChildCount(cell);
        LOG.debug("children: " + children);
        int edges = model.getEdgeCount(cell);
        LOG.debug("edges: " + edges);

        mxGeometry geometry = model.getGeometry(cell);
        LOG.debug("position: " + geometry.getCenterX() + ", " + geometry.getCenterY());

        String tag = ((Element) cell.getValue()).getTagName();
        LOG.debug("tag: " + tag);

        String type = cell.getAttribute("type");
        LOG.debug("type: " + type);

        String label = cell.getAttribute("label").replaceAll("\n", " ");
        LOG.debug("label: " + label);

        LOG.debug("element: " + ((Element) cell.getValue()).toString());
    }

    private void debugStateInfo(mxCell cell, mxIGraphModel model) {
        boolean isCollapsed = model.isCollapsed(cell);
        boolean isConnectable = model.isConnectable(cell);
        boolean isEdge = model.isEdge(cell);
        boolean isVertex = model.isVertex(cell);
        boolean isVisible = model.isVisible(cell);

        LOG.debug("Cell: ["
                + " " + (isVertex ? "" : "!") + "isVertex"
                + " " + (isEdge ? "" : "!") + "isEdge"
                + " " + (isCollapsed ? "" : "!") + "isCollapsed"
                + " " + (isConnectable ? "" : "!") + "isConnectable"
                + " " + (isVisible ? "" : "!") + "isVisible"
                + "]");
    }

    private void debugEdge(mxCell cell) {
        if (!cell.isEdge()) {
            LOG.error("this cell is not a edge! How?");
        } else {
            mxICell source = cell.getSource();
            LOG.debug("source: " + ((Element) source.getValue()).getAttribute("type"));

            mxICell target = cell.getTarget();
            LOG.debug("target: " + ((Element) target.getValue()).getAttribute("type"));
        }
    }

    private IStarLink createLink(mxCell cell) {
        // 1) get elements of this edge
        mxCell source = (mxCell) cell.getSource();
        mxCell target = (mxCell) cell.getTarget();

        // 2) get mapped elements to create a link between then
        Object mappedSource = mapped.get(source);
        if (mappedSource == null) {
            LOG.error("mapped source dont founded.");
        }
        Object mappedTarget = mapped.get(target);
        if (mappedTarget == null) {
            LOG.error("mapped target dont founded.");
        }

        if (mappedSource != null && mappedTarget != null) {
            IStarLink link = new IStarLink();
            link.setCod(cell.getId());
            link.setName(cell.getAttribute("label").replaceAll("\n", " "));
            link.setFrom(source.getId());
            link.setTo(target.getId());

            // update link in 'from' and 'to'
            if (mappedSource instanceof IStarActorElement) {
                IStarActorElement actor = (IStarActorElement) mappedSource;
//                actor.setLinks(0, link.getCod());
                actor.setLink(link.getCod());
            } else if (mappedSource instanceof IStarElement) {
                IStarElement ielement = (IStarElement) mappedSource;
//                ielement.setLinks(0, link.getCod());
                ielement.setLink(link.getCod());
            } else {
                LOG.debug("source not correctly mapped? " + mappedSource);
            }

            if (mappedTarget instanceof IStarActorElement) {
                IStarActorElement actor = (IStarActorElement) mappedTarget;
//                actor.setLinks(0, link.getCod());
                actor.setLink(link.getCod());
            } else if (mappedTarget instanceof IStarElement) {
                IStarElement ielement = (IStarElement) mappedTarget;
//                ielement.setLinks(0, link.getCod());
                ielement.setLink(link.getCod());
            } else {
                LOG.debug("target not correctly mapped? " + mappedTarget);
            }

            return link;
        }

        return null;
    }
}
