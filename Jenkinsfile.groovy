node {
    def mvnHome

    stage('Preparation') {
        git 'https://github.com/Alfresco/jk-jbehave-demo.git'
        mvnHome = tool 'M3'
    }

    stage('Build') {
        configFileProvider(
                [configFile(fileId: 'my-maven-settings', variable: 'MAVEN_SETTINGS')]) {
            sh "'${mvnHome}/bin/mvn' -s $MAVEN_SETTINGS clean install"
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