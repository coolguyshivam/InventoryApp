@echo off
set DIR=%~dp0
set CLASSPATH=%DIR%gradle-wrapper.jar
java -classpath %CLASSPATH% org.gradle.wrapper.GradleWrapperMain %*