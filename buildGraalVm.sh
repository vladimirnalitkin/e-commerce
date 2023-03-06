docker build -t spring-boot-build-graalvm -f Dockerfile_build_graalVm .
docker run --name spring-boot-build-graalvm -d --network=host spring-boot-build-graalvm
#./mvnw native:compile -Dmaven.test.skip=true -Pnative
# export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-java17-22.3.0/Contents/Home
docker cp spring-boot-build-graalvm:/work/target/e-commerce-0.0.1-SNAPSHOT ./target/
