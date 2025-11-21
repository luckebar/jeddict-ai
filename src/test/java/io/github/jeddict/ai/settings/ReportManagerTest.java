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

import com.github.stefanbirkner.systemlambda.SystemLambda;
import static io.github.jeddict.ai.settings.PreferencesManagerTest.LINUX;
import static io.github.jeddict.ai.settings.PreferencesManagerTest.USER;
import static io.github.jeddict.ai.settings.ReportManager.JEDDICT_STATS;
import io.github.jeddict.ai.test.TestBase;
import io.github.jeddict.ai.util.FileUtil;
import java.lang.reflect.Field;
import java.nio.file.Path;
import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class ReportManagerTest extends TestBase {

    @BeforeEach
    @Override
    public void beforeEach() throws Exception {
        super.beforeEach();
        // Reset the singleton instance before each test
        Field instance = ReportManager.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void constructor_without_given_path_linux() throws Exception {
        final Path USERHOME = HOME.resolve(USER);
        SystemLambda.restoreSystemProperties(() -> {
            System.setProperty("os.name", LINUX);
            System.setProperty("user.name", USER);
            System.setProperty("user.home", USERHOME.toString());

            Path expectedPath = FileUtil.getConfigPath().resolve(JEDDICT_STATS);

            ReportManager manager = ReportManager.getInstance();
            Field prefsField = ReportManager.class.getDeclaredField("stats");
            prefsField.setAccessible(true);
            FilePreferences filePreferences = (FilePreferences) prefsField.get(manager);

            then(filePreferences.preferencesPath).isEqualTo(expectedPath);
        });
    }

    @Test
    public void constructor_without_given_path_macos() throws Exception {
        final Path USERHOME = HOME.resolve(USER);
        SystemLambda.restoreSystemProperties(() -> {
            System.setProperty("os.name", LINUX);
            System.setProperty("user.name", USER);
            System.setProperty("user.home", USERHOME.toString());

            Path expectedPath = FileUtil.getConfigPath().resolve(JEDDICT_STATS);

            ReportManager manager = ReportManager.getInstance();
            Field prefsField = ReportManager.class.getDeclaredField("stats");
            prefsField.setAccessible(true);
            FilePreferences filePreferences = (FilePreferences) prefsField.get(manager);

            then(filePreferences.preferencesPath).isEqualTo(expectedPath);
        });
    }

    @Test
    public void constructor_without_given_path_windows() throws Exception {
        final Path USERHOME = HOME.resolve(USER);
        SystemLambda.restoreSystemProperties(() -> {
            System.setProperty("os.name", LINUX);
            System.setProperty("user.name", USER);
            System.setProperty("user.home", USERHOME.toString());

            Path expectedPath = FileUtil.getConfigPath().resolve(JEDDICT_STATS);

            ReportManager manager = ReportManager.getInstance();
            Field prefsField = ReportManager.class.getDeclaredField("stats");
            prefsField.setAccessible(true);
            FilePreferences filePreferences = (FilePreferences) prefsField.get(manager);

            then(filePreferences.preferencesPath).isEqualTo(expectedPath);
        });
    }
}