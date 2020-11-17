package com.softwaremagico.tm.advisor.core;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.advisor.R;
import com.softwaremagico.tm.advisor.log.AdvisorLog;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.ThreatLevel;
import com.softwaremagico.tm.character.creation.CharacterProgressionStatus;

public class ThreatLevelHandler {
    private final CharacterPlayer characterPlayer;
    private final int threatLevel;

    public ThreatLevelHandler(CharacterPlayer characterPlayer) {
        int threatLevel1;
        this.characterPlayer = characterPlayer;
        try {
            threatLevel1 = ThreatLevel.getThreatLevel(characterPlayer);
        } catch (InvalidXmlElementException e) {
            threatLevel1 = 0;
            AdvisorLog.errorMessage(ThreatLevelHandler.class.getName(), e);
        }
        threatLevel = threatLevel1;
    }

    public String getThreatLevel() {
        if (threatLevel < 20) {
            return "☮☮☮☮";
        } else if (threatLevel < 30) {
            return "☮☮☮";
        } else if (threatLevel < 50) {
            return "☮☮";
        } else if (threatLevel < 75) {
            return "☮";
        } else if (threatLevel < 100) {
            return "☠";
        } else if (threatLevel < 125) {
            return "☠☠";
        } else if (threatLevel < 150) {
            return "☠☠☠";
        } else if (threatLevel < 200) {
            return "☠☠☠☠";
        } else if (threatLevel < 250) {
            return "☠☠☠☠☠";
        }
        return "☠☠☠☠☠☠";
    }

    public String getColor(Context context) {
        if (threatLevel < 75) {
            return String.format("#%06x", ContextCompat.getColor(context, R.color.threatPeace) & 0xffffff);
        } else if (threatLevel < 100) {
            return String.format("#%06x", ContextCompat.getColor(context, R.color.threatLow) & 0xffffff);
        } else if (threatLevel < 150) {
            return String.format("#%06x", ContextCompat.getColor(context, R.color.threatMedium) & 0xffffff);
        } else if (threatLevel < 200) {
            return String.format("#%06x", ContextCompat.getColor(context, R.color.threatHigh) & 0xffffff);
        }
        return String.format("#%06x", ContextCompat.getColor(context, R.color.threatExtreme) & 0xffffff);
    }
}
