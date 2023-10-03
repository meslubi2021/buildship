/*******************************************************************************
 * Copyright (c) 2023 Gradle Inc. and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.buildship.core.internal.gradle;

import java.util.ArrayList;
import java.util.Collections;
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
            JsonElement where = problemJson.get("where");
            if (where != null) {
                Set<ProblemLocation> result = new LinkedHashSet<>();
                if (where.isJsonObject()) {
                    maybeAddLocation(where.getAsJsonObject(), result);
                } else if (where.isJsonArray()) {
                    JsonArray locations = where.getAsJsonArray();
                    for (JsonElement l : locations) {
                        if (l.isJsonObject()) {
                            maybeAddLocation(l.getAsJsonObject(), result);
                        }
                    }
                }
                return result;
            } else {
                return Collections.emptySet();
            }
        }

        private static void maybeAddLocation(JsonObject location, Set<ProblemLocation> result) {
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
    }

    static class ProblemDocumentationParser implements ProblemPropertyParser<String> {

        @Override
        public String parse(JsonObject problemJson) {
            // TODO this is incorrect as we don't have information about the current Gradle version.
            // The url creation should happen within either the Gradle daemon or the TAPI internals.
            if (problemJson.has("documentationLink")) {
                JsonObject docLinkObject = problemJson.get("documentationLink").getAsJsonObject();
                if (docLinkObject.has("page")) {
                    String page = docLinkObject.get("page").getAsString();
                    if (docLinkObject.has("section")) {
                        String section = docLinkObject.get("section").getAsString();
                        return String.format("%s/userguide/%s.html#%s", "https://docs.gradle.org/current", page, section);
                    } else {
                        return String.format("%s/userguide/%s.html", "https://docs.gradle.org/current", page);
                    }
                } else {
                    return null;
                }
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
            if (problemJson.has("problemType")) {
                return new ProblemCategory(problemJson.get("problemType").getAsString());
            } else {
                return new ProblemCategory(problemJson.get("problemCategory").getAsString());
            }

        }
    }
}
