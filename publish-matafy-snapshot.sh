#!/bin/sh

models=(
blzo-ex-authj
blzo-ex-basic
blzo-ex-google-auth
blzo-ex-ip2region
blzo-ex-mqtt
blzo-ex-mybatis
blzo-ex-mybatis-plus
blzo-ex-redission
blzo-ex-risk
blzo-ex-starter
blzo-ex-usignin
blzo-ex-utils
blzo-ex-version
)

echo "publish to MatafySnapshotRepository"

for model in ${models[@]}
  do
    echo $model
    gradle $model:publishMavenJavaPublicationToMatafySnapshotRepository
  done

echo "done."
