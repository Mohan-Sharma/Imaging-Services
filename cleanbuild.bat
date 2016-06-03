rmdir /s /q %JBOSS_HOME%\server\all\deploy\ospedale.war
rmdir /s /q %JBOSS_HOME%\server\all\log
rmdir /s /q %JBOSS_HOME%\server\all\tmp
rmdir /s /q %JBOSS_HOME%\server\all\work

call mvn clean
call build