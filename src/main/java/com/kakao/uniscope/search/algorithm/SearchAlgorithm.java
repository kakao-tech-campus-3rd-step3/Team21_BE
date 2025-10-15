package com.kakao.uniscope.search.algorithm;

import org.springframework.stereotype.Component;

@Component
public class SearchAlgorithm {

    public boolean matchesSequentialChars(String text, String query) {
        if (text == null || query == null || query.isEmpty()) {
            return false;
        }

        if (text.contains(query)) {
            return true;
        }

        int textIndex = 0;
        for (char c : query.toCharArray()) {
            textIndex = text.indexOf(c, textIndex);
            if (textIndex == -1) {
                return false;
            }
            textIndex++;
        }
        return true;
    }

    public int compareRelevance(String text1, String text2, String query) {
        return Integer.compare(
                getRelevanceScore(text2, query),
                getRelevanceScore(text1, query)
        );
    }

    public int getRelevanceScore(String text, String query) {
        if (text == null || query == null) {
            return 0;
        }

        if (text.equals(query)) {
            return 1000;
        }
        if (text.startsWith(query)) {
            return 500;
        }
        if (text.contains(query)) {
            return 100;
        }
        return 10;
    }
}
