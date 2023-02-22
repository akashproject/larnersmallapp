package com.icajobguarantee.ica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class youTubeVideoIdGet {
    public static String getYoutubeVideoIdFromIframe(String iframetext) {
        String pattern = "src=\"(?:https?:\\/\\/)?.*\\/(.*?)(?:\\?.*)?\"";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(iframetext);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String getYoutubeVideoIdFromURL(String yturl) {
        String pattern = "^https?://.*(?:youtu.be/|v/|u/\\\\w/|embed/|watch?v=)([^#&?]*).*$";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(yturl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
