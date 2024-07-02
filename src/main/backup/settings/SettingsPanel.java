package com.nickcoblentz.montoya.settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.httprpc.sierra.UIBuilder.*;

public class SettingsPanel {
    SettingsStore Store;
    JScrollPane Pane;


    SettingsPanel(SettingsStore store) {
        Store=store;
        init();
    }


    private Cell createLabel(String name)
    {
        return UIBuilder.cell(new JLabel(name)).with(field -> field.setAlignmentX(1.0f));
    }

    private Cell createTextField(ValueSetting setting)
    {
        return UIBuilder.cell(new JTextField(setting.getValue().toString(), 20))
                .with(field -> field.setAlignmentX(0.0f))
                .with(field -> field.getDocument().addDocumentListener(new DocumentListener() {
                       @Override
                       public void insertUpdate(DocumentEvent e) {
                           handleTextChange();
                       }

                       @Override
                       public void removeUpdate(DocumentEvent e) {
                           handleTextChange();
                       }

                       @Override
                       public void changedUpdate(DocumentEvent e) {

                       }

                    private void handleTextChange() {
                        setting.setValue(field.getText());
                        Store.persistSetting(setting);

                    }
                   }));


    }

    private Cell createSingleSelect(CollectionSetting setting)
    {
        return UIBuilder.cell(new JComboBox<>(getItems(setting.getOptions())))
                .with(field -> field.setSelectedItem(setting.getValue().toArray()[0]))
                .with(field -> field.setAlignmentX(0.0f))
                .with(field -> field.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String selectedText = (String) field.getSelectedItem();
                        setting.setValue(List.of(selectedText));
                        Store.persistCollectionSetting(setting);
                    }
                }));
    }

    private Cell createMultiSelect(CollectionSetting setting)
    {

        JList<String> jList = new JList<>(getItems(setting.getOptions()));
        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return UIBuilder.cell(jList)
                .with(field -> field.setAlignmentX(0.0f))
                .with(field -> field.setSelectedIndices(getSelectedIndices(setting)))
                .with(field -> field.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting())
                        {

                            setting.setValue(field.getSelectedValuesList());
                            Store.persistCollectionSetting(setting);
                        }
                    }
                }));
    }

    private int[] getSelectedIndices(CollectionSetting setting)
    {

        String[] options = getItems(setting.getOptions());
        String[] values = getItems(setting.getValue());
        List<String> valuesList = List.of(values);

        int[] intersectionIndices = new int[values.length];
        int index=0;
        for (int i = 0; i < options.length; i++) {
            if (valuesList.contains(options[i])) {
                intersectionIndices[index]=i;
                index++;
            }
        }
        return intersectionIndices;

    }

    private String[] getItems(Collection listofItems)
    {

        String[] items = new String[listofItems.size()];
        var preItems = listofItems.toArray();
        for(int j=0;j<listofItems.size();j++)
        {
            items[j]=preItems[j].toString();
        }
        return items;
    }

    private void init() {


/*
                cell(new JSeparator()),
*/


        Cell[] cellsArray = new Cell[Store.getAllSettings().size()];

        for(int i=0;i<cellsArray.length;i++)
        {
            ISetting s = Store.allSettings.get(i);
            Cell labelCell = createLabel(s.getDisplayName());
            Cell valueCell;
            if(s instanceof CollectionSetting cOfS)
            {
                if(cOfS.isMultiSelect())
                {
                    valueCell = createMultiSelect(cOfS);
                }
                else
                {
                    valueCell = createSingleSelect(cOfS);
                }
            }
            else
            {
                valueCell = createTextField((ValueSetting)s);
            }


            cellsArray[i] = UIBuilder.row(true,labelCell,valueCell);
        }

        var scrollPane = new JScrollPane(UIBuilder.column(4, true,cellsArray).with(viewportView -> viewportView.setBorder(new EmptyBorder(8, 8, 8, 8))).getComponent());
        scrollPane.setBorder(null);
        Pane=scrollPane;
    }

    public JScrollPane getPane() {
        return Pane;
    }
}
