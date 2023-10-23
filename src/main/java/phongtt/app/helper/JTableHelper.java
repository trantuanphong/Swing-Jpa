/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package phongtt.app.helper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Phong
 */
public class JTableHelper {

    public static TableModel createTableFromGetters(List<?> data) {
        DefaultTableModel model = new DefaultTableModel();
        if (!data.isEmpty()) {
            Class<?> clazz = data.get(0).getClass();
            List<Method> methods = getDeclaredGetters(clazz);
            
            // Create column
            methods.forEach(m -> model.addColumn(
                    m.getName().substring(3)));
            
            // Create row
            data.forEach(d -> {
                Object[] row = methods.stream().map(m -> {
                    try {
                        return m.invoke(d);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return "";
                    }
                }).toArray();
                model.addRow(row);
            });
        }
        
        return model;
    }

    private static List<Method> getDeclaredGetters(Class clazz) {
        List<Method> getters = new ArrayList();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().startsWith("get")
                    && m.getModifiers() == Modifier.PUBLIC) {
                getters.add(m);
            }
        }
        return getters;
    }
}
