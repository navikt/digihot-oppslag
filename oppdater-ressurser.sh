#!/bin/bash

#
# Bydeler
#
# Mer informasjon om [SSB-kilden](https://data.norge.no/datasets/44f30e8d-b653-4463-9e78-73aa7fbcfdf0).
curl https://data.ssb.no/api/klass/v1/versions/1168.json | jq -r '.classificationItems[] | .code + ";" + .name' >app/src/main/resources/geografi/bydeler.csv

#
# Kommuner
#
# Kilde: https://ws.geonorge.no/kommuneinfo/v1/#/default/get_fylkerkommuner
curl 'https://ws.geonorge.no/kommuneinfo/v1/fylkerkommuner?sorterkommuner=kommunenummer&sorterfylker=fylkesnummer' | jq -r '.[].kommuner[] | .fylkesnummer + ";" + .fylkesnavn + ";" + .kommunenummer + ";" + .kommunenavnNorsk' >app/src/main/resources/geografi/kommuner.csv

#
# Poststeder
#
# Dette er en ANSI-encoded fil med tabs og Windows style newlines. Vi ønsker UTF-8-encoding og Unix newlines.
# Sjekk diff, og verifiser at det ikke er noe fundamentalt galt med fila eller formatet.
curl https://www.bring.no/postnummerregister-ansi.txt | iconv -f iso-8859-1 -t utf-8 | tr -d \\r >app/src/main/resources/geografi/poststeder.tsv
