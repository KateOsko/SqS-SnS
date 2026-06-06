#!/bin/bash

set -a
source .env
set +a

./gradlew test --rerun-tasks "$@"
