def buildJar() {
    echo "building the application..."
    sh 'mvn clean install -Dmaven.test.skip=true'
}

def runUnitTests() {
    echo "running the unit tests..."
    //sh 'mvn test'
 
    // docker.image('mysql:latest').withRun('-e "MYSQL_ROOT_PASSWORD=hanah" -e "MYSQL_DATABASE=school_library" -p 3310:3306 --name sql-sidecarr') { c ->
    //     /* Wait until mysql service is up */
    //     sh 'sleep 30'
    //     /* Run some tests which require MySQL */
    //     sh 'mvn test'
    // }
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t hanaghz/demo-app:${IMAGE_NAME} .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push hanaghz/demo-app:${IMAGE_NAME}'
    }
}

def sonarTest() {
    echo "Running sonarQube checks..."
    sh 'mvn clean verify sonar:sonar  -Dmaven.test.skip=true  -Dsonar.projectKey=project-devops   -Dsonar.host.url=http://172.23.0.6:9000   -Dsonar.login=sqp_114f7278795d345ede80d4d31a39c9bd382db6dd'
}


def deployApp() {
    echo 'deploying the application...'
}

def pushToNexus() {
    echo "pushing the jar file to Nexus maven-snapshots repo..."
    // sh 'mvn dependency:resolve'
    //sh 'mvn clean deploy -Dmaven.test.skip=true'
    nexusArtifactUploader artifacts: [[artifactId: 'project-devops', classifier: '', file: 'uber.jar', type: 'jar']], credentialsId: '', groupId: 'com.example', nexusUrl: 'localhost:8083', nexusVersion: 'nexus3', protocol: 'http', repository: '', version: '0.0.1-SNAPSHOT'
}


return this