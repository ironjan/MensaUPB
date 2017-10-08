#!/bin/bash
set -e errexit
set -o pipefail
./gradlew --daemon clean
./scripts/lint.sh
./scripts/_connected_test.sh*
# Fix a bug, see https://stackoverflow.com/a/44304075
LC_NUMERIC="en_US.UTF-8" ./gradlew --daemon --parallel clean assembleRelease
caja app/build/outputs/apk/
