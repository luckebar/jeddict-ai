/**
 * Copyright 2025 the original author or authors from the Jeddict project (https://jeddict.github.io/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.jeddict.ai.settings;

import io.github.jeddict.ai.util.FileUtil;
import org.json.JSONObject;

/**
 *
 * @author Gaurav Gupta
 */
public class ReportManager {

    public static final String JEDDICT_STATS = "jeddict-stats.json";
    public static final String DAILY_INPUT_TOKEN_STATS_KEY = "dailyInputTokenStats";
    public static final String DAILY_OUTPUT_TOKEN_STATS_KEY = "dailyOutputTokenStats";

    private final FilePreferences stats;
    private static ReportManager instance;
    private JSONObject dailyInputTokenStats;
    private JSONObject dailyOutputTokenStats;

    private ReportManager() {
        stats = new FilePreferences(FileUtil.getConfigPath().resolve(JEDDICT_STATS));
    }

    public static ReportManager getInstance() {
        if (instance == null) {
            synchronized (ReportManager.class) {
                if (instance == null) {
                    instance = new ReportManager();
                }
            }
        }
        return instance;
    }

    public JSONObject getDailyInputTokenStats() {
        if (dailyInputTokenStats == null) {
            dailyInputTokenStats = stats.getChild(DAILY_INPUT_TOKEN_STATS_KEY);
        }
        return dailyInputTokenStats;
    }

    public void setDailyInputTokenStats(JSONObject usage) {
        this.dailyInputTokenStats = usage;
        stats.setChild(DAILY_INPUT_TOKEN_STATS_KEY, usage);
    }

    public JSONObject getDailyOutputTokenStats() {
        if (dailyOutputTokenStats == null) {
            dailyOutputTokenStats = stats.getChild(DAILY_OUTPUT_TOKEN_STATS_KEY);
        }
        return dailyOutputTokenStats;
    }

    public void setDailyOutputTokenStats(JSONObject usage) {
        stats.setChild(DAILY_OUTPUT_TOKEN_STATS_KEY, usage);
    }
}
