/*
 *
 *   Copyright (c) 2016-2017 Red Hat, Inc.
 *
 *   Red Hat licenses this file to you under the Apache License, version
 *   2.0 (the "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *   implied.  See the License for the specific language governing
 *   permissions and limitations under the License.
 */

package io.fabric8.vertx.maven.plugin.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

/**
 * A bunch of file utility that will be used by the plugin to perform common file operations
 *
 * @author kameshs
 */
public class FileUtils {

    /**
     * Utility method to perform move of {@link File} source under target directory, the default backup file name
     * will be  source base name with &quot;.original.jar&quot; appended to it
     *
     * @param source    - the {@link File} that needs to be backed up
     * @param backupDir - the {@link File} the destination directory where the file will be backed up
     * @return the {@link Path} of the backup file
     * @throws IOException - the exception that might occur while backing up
     */
    public static Path backup(File source, File backupDir) throws IOException {
        Path sourceFilePath = Paths.get(source.toURI());
        String targetName = org.codehaus.plexus.util.FileUtils.basename(source.toString()) + "original.jar";

        Path backupFilePath = Paths.get(backupDir.toURI().resolve(targetName));

        if (backupDir.isDirectory()) {
            Files.move(sourceFilePath, Paths.get(backupDir.toString(), targetName),
                StandardCopyOption.ATOMIC_MOVE);
        }

        return backupFilePath;
    }

    /**
     * Utility method to perform copy of {@link File} source under target directory, the default backup file name
     * will be  source base name with &quot;.original.jar&quot; appended to it
     *
     * @param source  - the {@link File} that needs to be backed up
     * @param destDir - the {@link File} the destination directory where the file will be copied to
     * @return the {@link Path} of the backup file
     * @throws IOException - the exception that might occur while backing up
     */
    public static Path copy(File source, File destDir) throws IOException {

        Path sourceFilePath = Paths.get(source.toURI());
        String targetName = org.codehaus.plexus.util.FileUtils.basename(source.toString()) + "original.jar";

        Path backupFilePath = Paths.get(destDir.toURI().resolve(targetName));

        if (destDir.exists() && destDir.isDirectory()) {

            Files.copy(sourceFilePath, Paths.get(destDir.toString(), targetName)
                , StandardCopyOption.REPLACE_EXISTING);
        }

        return backupFilePath;
    }

    /**
     * A small utility method to read lines from the {@link InputStream}
     *
     * @param input - the input stream from which the lines needs to be read
     * @return the {@link String} with lines of the file concatenated
     * @throws IOException - any exception while reading the file
     */
    public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
