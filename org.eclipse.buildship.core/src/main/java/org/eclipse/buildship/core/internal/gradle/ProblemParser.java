package org.eclipse.buildship.core.internal.gradle;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProblemParser {

    public static Problem parse(String json) {
        JsonObject problemJsonElement = JsonParser.parseString(json).getAsJsonObject();
        String label = new ProblemLabelParser().parse(problemJsonElement);
        ProblemSeverity severity = new ProblemSeverityParser().parse(problemJsonElement);
        Set<ProblemLocation> locations = new ProblemLocationsParser().parse(problemJsonElement);
        String documentationLink = new ProblemDocumentationParser().parse(problemJsonElement);
        List<String> solutions = new ProblemSolutionsParser().parse(problemJsonElement);
        ProblemCategory category = new ProblemCategoryParser().parse(problemJsonElement);
        return new Problem(label, severity, locations, documentationLink, solutions, category);
    }

    static interface ProblemPropertyParser <T> {
        T parse(JsonObject problemJson);
    }

    static class ProblemLabelParser implements ProblemPropertyParser<String> {

        @Override
        public String parse(JsonObject problemJson) {
            return problemJson.get("label").getAsString();
        }
    }

    static class ProblemSeverityParser implements ProblemPropertyParser<ProblemSeverity> {

        @Override
        public ProblemSeverity parse(JsonObject problemJson) {
            String severityString = problemJson.get("severity").getAsString();
            try {
                return ProblemSeverity.valueOf(severityString);
            } catch (Exception e) {
                return ProblemSeverity.WARNING;
            }
        }
    }

    static class ProblemLocationsParser implements ProblemPropertyParser<Set<ProblemLocation>> {

        @Override
        public Set<ProblemLocation> parse(JsonObject problemJson) {
            JsonArray locations = problemJson.get("where").getAsJsonArray();
            Set<ProblemLocation> result = new LinkedHashSet<>(locations.size());
            for (JsonElement l : locations) {
                JsonObject location = l.getAsJsonObject();
                if (location.has("path")) {
                    String path = location.get("path").getAsString();
                    Integer line = location.has("line") ? location.get("line").getAsInt() : null;
                    Integer column = location.has("column") ? location.get("column").getAsInt() : null;
                    Integer length = location.has("length") ? location.get("length").getAsInt() : null;
                    result.add(new FileLocation(path, line, column, length));
                } else if (location.has("identityPath")) {
                    JsonObject pathObject = location.get("identityPath").getAsJsonObject();
                    String fullPath = pathObject.get("fullPath").getAsString();
                    result.add(new TaskLocation(fullPath));
                }
            }
            return result;
        }
    }

    static class ProblemDocumentationParser implements ProblemPropertyParser<String> {

        @Override
        public String parse(JsonObject problemJson) {
            if (problemJson.has("documentationLink")) {
                JsonObject docLinkObject = problemJson.get("documentationLink").getAsJsonObject();
                String page = docLinkObject.get("page").getAsString();
                String section = docLinkObject.get("section").getAsString();
                // TODO this is incorrect as we don't have information about the current Gradle version. The url creation should happen within either the Gradle daemon or the TAPI internals.
                return String.format("%s/userguide/%s.html#%s", "https://docs.gradle.org/current", page, section);
            }
            else {
                return null;
            }
        }
    }

    static class ProblemSolutionsParser implements ProblemPropertyParser<List<String>> {

        @Override
        public List<String> parse(JsonObject problemJson) {
            JsonArray solutions = problemJson.get("solutions").getAsJsonArray();
            List<String> result = new ArrayList<>(solutions.size());
            for (JsonElement s : solutions) {
                result.add(s.getAsString());
            }
            return result;
        }
    }

    static class ProblemCategoryParser implements ProblemPropertyParser<ProblemCategory> {

        @Override
        public ProblemCategory parse(JsonObject problemJson) {
            // TODO This will change once https://github.com/gradle/gradle/pull/26510 is merged
            return new ProblemCategory(problemJson.get("problemType").getAsString());
        }
    }
}
