package me.mafrans.emotestf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils extends org.apache.commons.lang.StringUtils {
    private static final Random rand = new Random();

    public static String generateRandoms(final String in) {
        final String pattern = "\\{([^{}])+}";
        final StringBuilder outBuilder = new StringBuilder();
        final String toSplit = in.replaceAll(pattern, "%%spl");
        final String[] split = toSplit.split("%%spl");
        final List<String> matches = new ArrayList<>();
        final Matcher m = Pattern.compile(pattern).matcher(in);

        while (m.find()) {
            matches.add(m.group());
        }

        for (int i = 0; i < split.length - (split.length > matches.size() ? 1 : 0); ++i) {
            outBuilder.append(split[i]);
            final String randoms = matches.get(i).substring(1, matches.get(i).length() - 1);
            final String[] splitRandoms = randoms.split("\\|");
            outBuilder.append(splitRandoms[rand.nextInt(splitRandoms.length - 1)]);
        }

        if (split.length > matches.size()) {
            outBuilder.append(split[split.length - 1]);
        }

        return outBuilder.toString();
    }
}
