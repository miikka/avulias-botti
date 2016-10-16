#!/bin/bash

set -euo pipefail

curl -L https://github.com/boot-clj/boot-bin/releases/download/latest/boot.sh -o ~/bin/boot
chmod +x ~/bin/boot

# Download the dependencies if needed
boot show -d
