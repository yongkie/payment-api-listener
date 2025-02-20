#!/bin/bash

echo "CONTAINER_ENV : ${CONTAINER_ENV}"

# define default attribute..
APPLICATION_NAME=payment-api-listener
APPLICATION_JAR="${APPLICATION_NAME}.jar"

echo "run java spring boot ${APPLICATION_NAME} with profile : ${CONTAINER_ENV}"

if [[ "${CONTAINER_ENV,,}" == "LOCAL" ]]; then
    # default environment variable
    CONTAINER_ENV="local"

    # Argument options to running apps
    JAVA_OPTS="-Dspring.profiles.active=${CONTAINER_ENV}"

    echo "masuk ke if ${CONTAINER_ENV}"
elif [[ "${CONTAINER_ENV,,}" == "qa" ]]; then
    # default environment variable
    CONTAINER_ENV="qa"

    # Argument options to running apps
    JAVA_OPTS="-Dspring.profiles.active=${CONTAINER_ENV} -Dspring.cloud.config.uri=${SPRING_CLOUD_CONFIG_URI}"

    echo "masuk ke if ${CONTAINER_ENV}"
elif [[ "${CONTAINER_ENV,,}" == "dev" ]]; then
    # default environment variable
    CONTAINER_ENV="dev"

    # Argument options to running apps
    JAVA_OPTS="-Dspring.profiles.active=${CONTAINER_ENV} -Dspring.cloud.config.uri=${SPRING_CLOUD_CONFIG_URI}"

    echo "masuk ke if ${CONTAINER_ENV}"
elif [[ "${CONTAINER_ENV,,}" == "sandbox" ]]; then
    # default environment variable
    CONTAINER_ENV="sandbox"

    # Argument options to running apps
    JAVA_OPTS="-Dspring.profiles.active=${CONTAINER_ENV} -Dspring.cloud.config.uri=${SPRING_CLOUD_CONFIG_URI}"

    echo "masuk ke if ${CONTAINER_ENV}"
elif [[ "${CONTAINER_ENV,,}" == "sit" ]]; then
    # default environment variable
    CONTAINER_ENV="sit"

    # Argument options to running apps
    JAVA_OPTS="-Dspring.profiles.active=${CONTAINER_ENV} -Dspring.cloud.config.uri=${SPRING_CLOUD_CONFIG_URI}"

    echo "masuk ke if ${CONTAINER_ENV}"
elif [[ "${CONTAINER_ENV,,}" == "prod" ]]; then
    # default environment variable
    CONTAINER_ENV="prod"

    # Argument options to running apps
    JAVA_OPTS="-Dspring.profiles.active=${CONTAINER_ENV} -Dspring.cloud.config.uri=${SPRING_CLOUD_CONFIG_URI}"

    echo "masuk ke if ${CONTAINER_ENV}"
elif [[ "${CONTAINER_ENV,,}" == "uat" ]]; then
    # default environment variable
    CONTAINER_ENV="uat"

    # Argument options to running apps
    JAVA_OPTS="-Dspring.profiles.active=${CONTAINER_ENV} -Dspring.cloud.config.uri=${SPRING_CLOUD_CONFIG_URI}"

    echo "masuk ke if ${CONTAINER_ENV}"
fi

# Setup new relic
if [[ "${ENABLE_NEWRELIC}" == "true" ]]; then
         echo "Enable newrelic agent"
         AGENT_NEWRELIC="-javaagent:/apps/fc/newrelic.jar"
fi
# prepared arguments allocation memory..


if [ -z "${XMSLimit}" -a "${XMSLimit}" == "" ]; then
    echo "set XMS limit by default ...."
    XMSLimit="128m"
fi

if [ -z "${XMXLimit}" -a "${XMXLimit}" == "" ]; then
    echo "set XMX limit by default ...."
    XMXLimit="256m"
fi

# Argument options to running apps
JAVA_HEAP_SIZE="-Xms${XMSLimit} -Xmx${XMXLimit}"

# run apps.
eval "java ${AGENT_NEWRELIC} -jar ${JAVA_HEAP_SIZE} ${JAVA_OPTS} /apps/payment-api-listener/app/${APPLICATION_JAR} "

echo "set environment : DONE!"

