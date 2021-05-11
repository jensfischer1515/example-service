package com.example.gradle

import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.security.SecureRandom
import java.util.concurrent.TimeUnit

@CompileStatic
class PipelinePlugin implements Plugin<Project> {

    SecureRandom random = new SecureRandom()

    @Override
    void apply(Project project) {

        project.tasks.register('sleep') {
            it.group = "pipeline"
            it.doLast {
                sleep(100, 1500)
            }
        }

        project.tasks.register('deployCI') {
            it.group = "pipeline"
            it.doLast {
                sleep(1000, 5000)
            }
        }

        project.tasks.register('deployStaging') {
            it.group = "pipeline"
            it.doLast {
                sleep(1000, 5000)
            }
        }

        project.tasks.register('deployProduction') {
            it.group = "pipeline"
            it.doLast {
                sleep(1000, 5000)
            }
        }
    }

    int random(int min, int max) {
        return min + random.nextInt((max - min) + 1);
    }

    void sleep(int min, int max) {
        TimeUnit.MILLISECONDS.sleep(random(min, max))
    }
}
