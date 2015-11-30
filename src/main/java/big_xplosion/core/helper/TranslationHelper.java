package big_xplosion.core.helper;

import net.minecraft.util.StatCollector;

public class TranslationHelper {

	private TranslationHelper() {

	}

	public static String translate(String text) {
		return translate(text, null);
	}

	public static String translate(String text, Object... objects) {
		return StatCollector.translateToLocal(String.format(text, objects));
	}
}
