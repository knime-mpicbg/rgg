package at.ac.arcs.rgg.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class AbstractBoxElement extends RElement {

    protected ArrayList<RElement> childElements = new ArrayList<RElement>();


    public void addChild(RElement elem) {
        if (!isChildAddable()) {
            throw new UnsupportedOperationException("This element doesn't accept any child elements.");
        } else {
            childElements.add(elem);
            return;
        }
    }


    public RElement getChild(int i) {
        return childElements.get(i);
    }


    public List<RElement> getChilds() {
        return childElements;
    }


    @Override
    public void persistState(Map<String, Object> persistMap) {
        for (RElement childElement : childElements) {
            childElement.persistState(persistMap);
        }
    }


    @Override
    public void restoreState(Map<String, Object> persistMap) {
        for (RElement childElement : childElements) {
            childElement.restoreState(persistMap);
        }
    }

}
