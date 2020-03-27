/*
 *  Copyright (C) 2020 Softwaremagico
 *
 *  This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero  <softwaremagico@gmail.com> Valencia (Spain).
 *
 *  This program is free software; you can redistribute it and/or modify it under  the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this Program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 */

package com.softwaremagico.tm.advisor.ui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.softwaremagico.tm.Element;
import com.softwaremagico.tm.advisor.R;

import java.util.List;

public class ElementAdapter<T extends Element<?>> extends ArrayAdapter<T> {
    private List<T> elements;

    public ElementAdapter(@NonNull Context context, @NonNull List<T> objects) {
        super(context, android.R.layout.simple_spinner_dropdown_item, objects);
        this.elements = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.element_list, parent, false);
        }

        Element element = elements.get(position);

        if (element != null) {
            TextView name = listItem.findViewById(R.id.selected_item);
            name.setText(element.getName());
        }

        return listItem;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.element_list, parent, false);
        }

        Element element = elements.get(position);

        if (element != null) {
            TextView elementName = listItem.findViewById(R.id.selected_item);
            elementName.setText(element.getName());
        }

        return listItem;
    }

    public int indexOf(T element){
        return elements.indexOf(element);
    }


}
