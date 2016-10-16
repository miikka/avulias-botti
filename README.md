# Avulias Telegram-botti [![CircleCI](https://circleci.com/gh/miikka/avulias-botti.svg?style=shield)][circleci]

[circleci]: https://circleci.com/gh/miikka/avulias-botti

*Briefly in English:* This is a simple Telegram bot.

## Botin ajaminen lokaalisti

1. Luo itsellesi Telegram-botti [BotFatherin avulla](https://telegram.me/botfather).
2. Luo tiedosto `.lein-env` ja laita siihen BotFatherilta saamasi token:
       
        {:telegram-token "tähän se token"}

3. Käynistä REPL:

        boot repl

4. Aja:

        (go)
        ;; ...nyt se toimii...
        (stop)

## Tuotanto

`master`-branch menee automaattisesti tuotantoon Herokuun, jos
[CircleCI][circleci]-testit menevät läpi. Ole siis varovainen.

## Lisenssi

Copyright © 2016 Miikka Koskinen.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
