#!/bin/bash

curl https://www.bring.no/postnummerregister-ansi.txt | iconv -f iso-8859-1 -t utf-8 | tr -d \\r > src/main/resources/geografi/postal_codes_no.tsv

