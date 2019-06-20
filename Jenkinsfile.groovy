node {
    def mvnHome

    stage('Preparation') {
        git 'https://github.com/Alfresco/jk-jbehave-demo.git'
        mvnHome = tool 'M3',
        mavenSettingsConfig: 'my-maven-settings'
    }

    stage('Build') {
        withMaven(
                // Maven settings.xml file defined with the Jenkins Config File Provider Plugin
                // Maven settings and global settings can also be defined in Jenkins Global Tools Configuration
                mavenSettingsConfig: 'my-maven-settings',
                mavenLocalRepo: '.repository') {

            // Run the maven build
            sh "'${mvnHome}/bin/mvn' clean install"

        }

    }

    stage('Results') {

        junit '**/target/surefire-reports/*.xml'

        // publish html
        publishHTML (target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: 'target/site/serenity',
                reportFiles: 'index.html',
                reportName: "Serenity Report"
        ])
    }
}