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
package io.github.jeddict.ai.scanner;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;

/**
 *
 * @author Gaurav Gupta
 */
public class ProjectClassListener {

    private final Project project;

    private final Map<FileObject, ClassData> classDatas;
    private final Set<DataObject> pendingDO = new HashSet<>();

    public ProjectClassListener(Project project, Map<FileObject, ClassData> classDatas) {
        this.project = project;
        this.classDatas = classDatas;
    }

    public Set<DataObject> getPendingDataObject() {
        return pendingDO;
    }

    public void register() {
        // Get source groups from the project (Java source folders)
        Sources sources = ProjectUtils.getSources(project);
        SourceGroup[] sourceGroups = sources.getSourceGroups(Sources.TYPE_GENERIC);

        FileObject javaFolder = null;
        for (SourceGroup group : sourceGroups) {
            // Get the root folder of the source group
            FileObject rootFolder = group.getRootFolder();

            // Check if the root folder has a 'src/main/java' folder
            FileObject srcFolder = rootFolder.getFileObject("src");
            if (srcFolder != null) {
                FileObject mainFolder = srcFolder.getFileObject("main");
                if (mainFolder != null) {
                    javaFolder = mainFolder.getFileObject("java");
                }
            }
        }

        FileObject javaFolder2 = javaFolder;
        // Listen for changes in the DataObject Registry
        DataObject.Registry registry = DataObject.getRegistry();
        registry.addChangeListener((e) -> {
            DataObject[] modifiedObjects = registry.getModified();
            for (DataObject dataObj : modifiedObjects) {
                if (FileUtil.isParentOf(javaFolder2, dataObj.getPrimaryFile())) {
                    pendingDO.add(dataObj);
                    classDatas.remove(dataObj.getPrimaryFile());
                } else {
                    System.out.println("");
                }
            }
        });
    }

}
