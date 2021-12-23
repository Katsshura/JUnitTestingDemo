#!/bin/sh

exec java ${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=${SPRING_PROFILE} api.jar
