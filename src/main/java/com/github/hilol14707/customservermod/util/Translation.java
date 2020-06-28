package com.github.hilol14707.customservermod.util;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class Translation {
    /**
     * (client doesn't require lang file)
     * @param translationKey
     * @return translation text as a String
     */
    public static String translatedString(String translationKey) {
        return new TextComponentTranslation(translationKey).getUnformattedText();
    }

    /**
     * (client doesn't require lang file)
     * @param translationKey
     * @return translation text as a TranslationComponent
     */
    public static TextComponentString getTextComponent(String translationKey) {
        return new TextComponentString(translatedString(translationKey));
    }

    /**
     * (client doesn't require lang file)
     * @param translationKey
     * @param color
     * @return translation text as a TranslationComponent with color
     */
    public static TextComponentString getTextComponent(String translationKey, TextFormatting color) {
        return (TextComponentString) new TextComponentString(translatedString(translationKey)).setStyle(new Style().setColor(color));
    }
}