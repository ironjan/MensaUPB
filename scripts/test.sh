#!/bin/bash
# Fix a bug, see https://stackoverflow.com/a/44304075
LC_NUMERIC="en_US.UTF-8" ./gradlew --parallel test

