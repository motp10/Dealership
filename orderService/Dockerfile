FROM ubuntu:jammy

RUN apt-get update && apt-get install -y libz-dev && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY orderService/build/native/nativeCompile/orderService /app/app-native

RUN chmod +x /app/app-native

EXPOSE 8080
ENTRYPOINT ["/app/app-native"]