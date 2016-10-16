#!/bin/bash
# This script checks that the AOT build results in a JAR file that actually can
# be executed.

set -euo pipefail
boot build
java -jar target/avulias-botti-standalone.jar --check
