node {
    def mvnHome

    stage('Preparation') {
        git 'https://github.com/Alfresco/jk-jbehave-demo.git'
        mvnHome = tool 'M3'
    }

    stage('Build') {
        configFileProvider(
                [configFile(fileId: 'f6a54b8b-e0ee-4efb-9873-72a5fed6c6b0', variable: 'MAVEN_SETTINGS')]) {
            sh "'${mvnHome}/bin/mvn' -s ${MAVEN_SETTINGS} clean compile"
        }

    }

    stage('configureTest'){
        dir('scripts') {
            sh "echo ++++++++++++ executing script."
            sh "./test-script.sh"
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