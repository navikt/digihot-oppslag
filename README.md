# README


# Henvendelser

Spørsmål knyttet til koden eller prosjektet kan stilles som issues her på GitHub.

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #digihot-dev.

### Oppdatere postnummertabell

```bash
cd oppslag/src/main/resources/geografi
curl https://www.bring.no/postnummerregister-ansi.txt | iconv -f iso-8859-1 -t utf-8 | tr -d \\r > postal_codes_no.tsv.new

```

Dette er en ANSI-encoded fil med tabs og Windows style newlines. Vi ønsker UTF-8-encoding og Unix newlines.
Sjekk diff, og verifiser at det ikke er noe fundamentalt galt med fila eller formatet.

```bash
diff -u postal_codes_no.tsv postal_codes_no.tsv.new
mv postal_codes_no.tsv.new postal_codes_no.tsv
git add .
...
```

### Oppdatere kommunetabell

// TODO
