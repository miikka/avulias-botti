#!/bin/bash

set -euo pipefail
export TELEGRAM_TOKEN=$(heroku config:get TELEGRAM_TOKEN)
export WEBHOOK_ID=$(heroku config:get WEBHOOK_ID)
boot setup-webhook
