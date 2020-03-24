package com.softwaremagico.tm.advisor.ui.character.characteristics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.softwaremagico.tm.advisor.CharacterManager;
import com.softwaremagico.tm.advisor.R;
import com.softwaremagico.tm.advisor.ui.components.CustomFragment;
import com.softwaremagico.tm.advisor.ui.components.TranslatedNumberPicker;
import com.softwaremagico.tm.advisor.ui.translation.ThinkMachineTranslator;
import com.softwaremagico.tm.character.characteristics.CharacteristicDefinition;
import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.characteristics.CharacteristicType;
import com.softwaremagico.tm.character.characteristics.CharacteristicsDefinitionFactory;
import com.softwaremagico.tm.character.creation.FreeStyleCharacterCreation;
import com.softwaremagico.tm.file.modules.ModuleManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CharacteristicsFragment extends CustomFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Map<CharacteristicName, TranslatedNumberPicker> translatedNumberPickers = new HashMap<>();

    public static CharacteristicsFragment newInstance(int index) {
        CharacteristicsFragment fragment = new CharacteristicsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.character_characteristics_fragment, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.characteristics_container);

        for (final CharacteristicType type : CharacteristicType.values()) {
            if (type == CharacteristicType.OTHERS) {
                continue;
            }

            addSection(ThinkMachineTranslator.getTranslatedText(type.name().toLowerCase() + "Characteristics"), linearLayout);
            for (CharacteristicDefinition characteristicDefinition : CharacteristicsDefinitionFactory.getInstance().getAll(type, Locale.getDefault().getLanguage(),
                    ModuleManager.DEFAULT_MODULE)) {
                createCharacteristicsEditText(root, linearLayout, characteristicDefinition);
            }
        }

        updateCharacteristicsLimits();

        return root;
    }


    public void updateCharacteristicsLimits() {
        if (CharacterManager.getSelectedCharacter().getRace() != null) {
            for (Map.Entry<CharacteristicName, TranslatedNumberPicker> characteristicComponent : translatedNumberPickers.entrySet()) {
                characteristicComponent.getValue().setLimits(CharacterManager.getSelectedCharacter().getStartingValue(characteristicComponent.getKey()),
                        FreeStyleCharacterCreation.getMaxInitialCharacteristicsValues(characteristicComponent.getKey(),
                                CharacterManager.getSelectedCharacter().getInfo().getAge(), CharacterManager.getSelectedCharacter().getRace()));
            }
        }
    }

    public void refreshCharacteristicValues() {
        if (CharacterManager.getSelectedCharacter().getRace() != null) {
            for (Map.Entry<CharacteristicName, TranslatedNumberPicker> characteristicComponent : translatedNumberPickers.entrySet()) {
                characteristicComponent.getValue().setValue(CharacterManager.getSelectedCharacter().getValue(characteristicComponent.getKey()));
            }
        }
    }

    private void createCharacteristicsEditText(View root, LinearLayout linearLayout, CharacteristicDefinition characteristicDefinition) {
        TranslatedNumberPicker characteristicsNumberPicker = new TranslatedNumberPicker(getContext(), null);
        translatedNumberPickers.put(characteristicDefinition.getCharacteristicName(), characteristicsNumberPicker);
        characteristicsNumberPicker.setLabel(ThinkMachineTranslator.getTranslatedText(characteristicDefinition.getId()));
        characteristicsNumberPicker.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //characteristicsNumberPicker.setPadding(20, 20, 20, 20);

        // Add EditText to LinearLayout
        if (linearLayout != null) {
            linearLayout.addView(characteristicsNumberPicker);
        }

        if (CharacterManager.getSelectedCharacter().getValue(characteristicDefinition.getCharacteristicName()) != null) {
            characteristicsNumberPicker.setValue(CharacterManager.getSelectedCharacter().getValue(characteristicDefinition.getCharacteristicName()));
        }
        characteristicsNumberPicker.addValueChangeListener((picker, oldVal, newVal) -> CharacterManager.getSelectedCharacter().getCharacteristic(characteristicDefinition.getCharacteristicName()).setValue(newVal));

    }

}
