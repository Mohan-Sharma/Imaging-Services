set MAVEN_OPTS=-XX:+CMSClassUnloadingEnabled -XX:PermSize=256M -XX:MaxPermSize=512M -Xmx1024M -Xdebug -Xrunjdwp:transport=dt_socket,address=8010,server=y,suspend=n
SET MAVEN_TERMINATE_CMD = off
call mvn install -Dmaven.test.skip=true
cd ospedale-webapp
call mvn -Djetty.port=7979 jetty:run
cd..
