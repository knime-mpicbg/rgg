/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.ui.inputselection;

import java.util.ArrayList;

/**
 *
 * @author ahmet
 */
public class InputList {

    private ArrayList<InputInfo> list;
    public static final InputInfo NONE_OPTION = new InputInfo(InputSelectorTable.NONE_OPTION, InputInfo.OptionType.MANY_TO_ONE);

    public InputList() {
        list = new ArrayList<InputInfo>();
        this.put(NONE_OPTION);
    }

    public void put(InputInfo inputInfo) {
        if (inputInfo == null) {
            throw new NullPointerException("input info is null");
        }
        if (list.contains(inputInfo)) {
            throw new IllegalArgumentException("inputInfo object with same id is allready in the list");
        }

        if (inputInfo.getOptionType() == InputInfo.OptionType.ONE_TO_ONE && inputInfo.isAssignedToColumns()) {
            for (InputInfo ii : list) {
                for (int col : ii.getColumns()) {
                    if (col == inputInfo.getFirstColumn()) {
                        throw new IllegalArgumentException("Default Column is not acceptable, it is allready assigned to another input option");
                    }
                }
            }
        }

        list.add(inputInfo);
    }

    public InputInfo[] getInputs() {
        return list.toArray(new InputInfo[list.size()]);
    }
}
