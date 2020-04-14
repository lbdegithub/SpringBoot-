/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.loader;

import org.springframework.boot.loader.archive.Archive;

/**
 * {@link Launcher} for JAR based archives. This launcher assumes that dependency jars are
 * included inside a {@code /BOOT-INF/lib} directory and that application classes are
 * included inside a {@code /BOOT-INF/classes} directory.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 * @since 1.0.0
 */
public class JarLauncher extends ExecutableArchiveLauncher {

	static final String BOOT_INF_CLASSES = "BOOT-INF/classes/";

	static final String BOOT_INF_LIB = "BOOT-INF/lib/";

	public JarLauncher() {
	}

	protected JarLauncher(Archive archive) {
		super(archive);
	}

	@Override
	protected boolean isNestedArchive(Archive.Entry entry) {
		if (entry.isDirectory()) {
			return entry.getName().equals(BOOT_INF_CLASSES);
		}
		return entry.getName().startsWith(BOOT_INF_LIB);
	}

	/**
	 * LB-TODO  spring boot 的真实引导入口 <br/>
	 * 由	<pre>{@code
	 * 	<plugin>
	 * 		<groupId>org.springframework.boot</groupId>
	 * 		<artifactId>spring-boot-maven-plugin</artifactId>
	 * 	</plugin>} </pre>
	 * 这个插件决定的。
	 * 最终的打包内容的文件MANIFEST.MF
	 * <pre>{@code Manifest-Version: 1.0
	 * Created-By: Maven Archiver 3.4.0
	 * Build-Jdk-Spec: 12
	 * Implementation-Title: demo
	 * Implementation-Version: 0.0.1-SNAPSHOT
	 * Main-Class: org.springframework.boot.loader.JarLauncher
	 * Start-Class: com.example.demo.DemoApplication
	 * Spring-Boot-Version: 2.2.5.RELEASE
	 * Spring-Boot-Classes: BOOT-INF/classes/
	 * Spring-Boot-Lib: BOOT-INF/lib/}</pre>
	 *
	 * 基于JAR档案的启动器。此启动程序假定依赖项jar包含在/ BOOT-INF / lib目录中，并且应用程序类包含在/ BOOT-INF / classes目录中。
	 * <br/>
	 *  https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/appendix-executable-jar-format.html#executable-jar
	 */
	public static void main(String[] args) throws Exception {
		new JarLauncher().launch(args);
	}

}
