# Avulias Telegram-botti

*Briefly in English:* This is a simple Telegram bot.

## Botin ajaminen lokaalisti

1. Luo itsellesi Telegram-botti [BotFatherin avulla](https://telegram.me/botfather).
2. Luo tiedosto `.lein-env` ja laita siihen BotFatherilta saamasi token:
       
        {:telegram-token "tähän se token"}

3. Käynistä REPL:

        boot repl

4. Aja:

        (require '[avulias-botti.core :as bot])
        (def bot (bot/start))
        ;; ...nyt se toimii...
        (bot/stop bot)

## Tuotanto

`master`-branch menee automaattisesti tuotantoon. Ole siis varovainen.

## Lisenssi

Copyright © 2016 Miikka Koskinen.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
