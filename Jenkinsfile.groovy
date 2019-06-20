node {
    def mvnHome

    stage('Preparation') {
        git 'https://github.com/Alfresco/jk-jbehave-demo.git'
        mvnHome = tool 'M3'
    }

    stage('Build') {
        configFileProvider(
                [configFile(fileId: '78d61d7c-0a3a-4da5-974f-44fc98b78556', variable: 'MAVEN_SETTINGS')]) {
            sh 'mvn -s $MAVEN_SETTINGS clean install'
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