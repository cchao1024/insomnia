pipeline {
  agent any
  stages {
    stage('检出') {
      steps {
        sh 'ci-init'
        checkout([$class: 'GitSCM', branches: [[name: env.GIT_BUILD_REF]], 
                                                            userRemoteConfigs: [[url: env.GIT_REPO_URL, credentialsId: env.CREDENTIALS_ID]]])
      }
    }
    stage('构建') {
      steps {
        sh 'java -version'
        echo '构建中...'
        sh 'mvn clean'
        sh 'mvn package -Dmaven.test.skip'
        echo '构建完成.'
        script {
          def exists = fileExists 'README.md'
          if (!exists) {
            writeFile(file: 'README.md', text: 'Helloworld')
          }
        }

        archiveArtifacts(artifacts: 'README.md', fingerprint: true)
      }
    }
    stage('测试') {
      steps {
        echo '暂不测试...'
      }
    }
    stage('部署') {
      steps {
        sh 'apt install -y sshpass'
        echo '上传jar包中...'
        sh 'sshpass -p asd123vps~ scp target/${env.artifact_name} root@47.240.35.14:/root/'
        echo '运行中...'
        sh 'sshpass -p asd123vps~ ssh root@47.240.35.14 java -jar /root/${env.artifact_name}'
        echo '部署运行完成'
      }
    }
  }
}