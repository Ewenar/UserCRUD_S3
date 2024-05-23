FROM openjdk:17-alpine

COPY build/libs/BucketUpload-0.0.1-SNAPSHOT.jar /BucketUpload.jar

ENV AWS_ACCESS_KEY_ID="AKIA4TS3WBWNCHCMPCRN"
ENV AWS_SECRET_ACCESS_KEY="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"
ENV AWS_REGION="us-east-1"

CMD ["java","-jar","BucketUpload.jar"]