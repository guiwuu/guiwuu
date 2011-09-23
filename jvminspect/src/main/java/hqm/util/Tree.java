package hqm.util;

import java.util.List;

/**
 * 
 * @author Houqianming
 *
 * @param <E>
 * @param <ID>
 */
public interface Tree<E,ID>
{
    String PATH_SEP_DOT = ".";

    List<Tree<E, ID>> getChildren();
    List<Tree<E, ID>> getTrees();
    List<E> getLeaves();
    boolean isLeaf();
    
    Tree<E,ID> getParent();
    void setParent(Tree<E, ID> parent);    
    E getEntity();
    void setEntity(E entity);
    ID getId();

    Tree<E,ID> mkdirs(ID[] pathes);
    boolean addChild(ID[] parentPathes, Tree<E, ID> childTree, boolean mkdir);
    void addChild(Tree<E, ID> childTree);
    boolean removeChild(Tree<E, ID> childTree);
    void addEntity(E leafEntity, ID id);
    Tree<E,ID> removeEntity(E leafEntity);
    E removeEntity(ID id, ID id2);
    
    //List<String> getPath();
    //void setPath(List<String> path);
    Tree<E,ID> locate(ID[] pathes);
}
