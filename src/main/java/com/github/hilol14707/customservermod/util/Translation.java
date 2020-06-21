package com.github.hilol14707.customservermod.util;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class Translation {
    // returns the translation text as a String (client doesn't require lang file)
    public static String translatedString(String translationKey) {
        return new TextComponentTranslation(translationKey).getUnformattedText();
    }

    // returns the translation text as a TranslationComponent (client doesn't require lang file)
    public static TextComponentString getTextComponent(String translationKey) {
        return new TextComponentString(translatedString(translationKey));
    }

    // returns the translation text as a TranslationComponent with color (client doesn't require lang file)
    public static TextComponentString getTextComponent(String translationKey, TextFormatting color) {
        return (TextComponentString) new TextComponentString(translatedString(translationKey)).setStyle(new Style().setColor(color));
    }
}