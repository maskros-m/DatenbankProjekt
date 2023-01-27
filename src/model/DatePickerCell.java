/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

/**
 *
 * @author mido
 */
public class DatePickerCell extends TableCell<Transaction, LocalDate> {
    private DatePicker datePicker;

    public DatePickerCell() {
    }
    
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }
    
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getDate().toString());
        setGraphic(null);
    }
    
    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker(getDate());
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.setOnAction((e) -> {
                System.out.println("Committed: " + datePicker.getValue().toString());
                // commitEdit(LocalDate.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                commitEdit(LocalDate.from(datePicker.getValue()));
            });
    }

    private LocalDate getDate() {
        return getItem() == null ? LocalDate.now() : getItem();
    }
    
    
}
