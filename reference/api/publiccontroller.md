# PublicController

{% hint style="warning" %}
**Attenzione:** Tutti i metodi di questo controller possono essere effettuati senza l'obbligo di autenticazione.
{% endhint %}

## PostMapping

{% swagger method="post" path="/public/nuovoUtente" baseUrl="https://mattbarbershop.herokuapp.com" summary="nuovoUtente" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="mail" required="true" %}
Mail utente
{% endswagger-parameter %}

{% swagger-parameter in="body" name="password" required="true" %}
Password utente
{% endswagger-parameter %}

{% swagger-parameter in="body" name="nome" %}
Nome utente
{% endswagger-parameter %}

{% swagger-parameter in="body" name="cognome" %}
Cognome utente
{% endswagger-parameter %}

{% swagger-parameter in="body" name="cellulare" %}
Cellulare utente
{% endswagger-parameter %}

{% swagger-parameter in="body" name="foto" %}
Path della foto utente
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Utente creato correttamente" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/public/nuovaPrenotazione" baseUrl="https://mattbarbershop.herokuapp.com" summary="nuovaPrenotazione" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="nome" required="true" %}
Nome utente
{% endswagger-parameter %}

{% swagger-parameter in="body" name="mail" required="true" %}
Mail utente
{% endswagger-parameter %}

{% swagger-parameter in="body" name="idBarbiere" required="true" %}
Id Barbiere prenotazione
{% endswagger-parameter %}

{% swagger-parameter in="body" name="startTime" required="true" %}
Data prenotazione con formato "dd/MM/yyyy HH:mm"
{% endswagger-parameter %}

{% swagger-parameter in="body" name="trattamenti" %}
Lista trattamenti
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Prenotazione creata con successo" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/public/recensionePrenotazione" baseUrl="https://mattbarbershop.herokuapp.com" summary="recensionePrenotazione" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" required="true" name="idPrenotazione" %}
Id prenotazione
{% endswagger-parameter %}

{% swagger-parameter in="body" name="valutazione" %}
Valutazione da 0.0 a 5.0
{% endswagger-parameter %}

{% swagger-parameter in="body" name="Descrizione" %}
Descrizione recensione
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Recensione creata con successo" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}
{% endswagger %}

## GetMapping

{% swagger method="get" path="/public/getBarbieri" baseUrl="https://mattbarbershop.herokuapp.com" summary="getBarbieri" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-response status="200: OK" description="Ritorna tutti i barbieri creati" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/public/getBarbiere" baseUrl="https://mattbarbershop.herokuapp.com" summary="getBarbiere" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idBarbiere" required="true" %}
Id barbiere
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Ritorna il barbiere legato all'id" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/getPrenotazioni" baseUrl="https://mattbarbershop.herokuapp.com" summary="getPrenotazioni" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-response status="200: OK" description="Ritorna tutte le prenotazioni create" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/public/getPrenotazioniBarbiere" baseUrl="https://mattbarbershop.herokuapp.com" summary="getPrenotazioniBarbiere" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idBarbiere" required="true" %}
Id barbiere
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Ritorna tutte le prenotazioni relative ad un barbiere" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/public/getPrenotazioniBarbierePerGiorno" baseUrl="https://mattbarbershop.herokuapp.com" summary="/getPrenotazioniBarbierePerGiorno" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idBarbiere" required="true" %}
Id barbiere
{% endswagger-parameter %}

{% swagger-parameter in="body" name="giorno" required="true" %}
Data con formato "dd/MM/yyyy"
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Ritorna tutte le prenotazioni relative ad un barbiere ed un giorno" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/getPrenotazioniLibereBarbierePerGiorno" baseUrl="https://mattbarbershop.herokuapp.com" summary="getPrenotazioniLibereBarbierePerGiorno" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idBarbiere" required="true" %}
Id barbiere
{% endswagger-parameter %}

{% swagger-parameter in="body" name="giorno" %}
Data con formato "dd/MM/yyyy"
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Ritorna tutti i turni liberi relativi ad un barbiere ed un giorno" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/public/getTrattamenti" baseUrl="https://mattbarbershop.herokuapp.com" summary="getTrattamenti" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-response status="200: OK" description="Ritorna tutti i trattamenti creati" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/public/getRecensioni" baseUrl="https://mattbarbershop.herokuapp.com" summary="getRecensioni" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-response status="200: OK" description="Ritorna tutte le recensioni create" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/public/getLastThreeRecensioni" baseUrl="https://mattbarbershop.herokuapp.com" summary="getLastThreeRecensioni" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-response status="200: OK" description="Ritorna le ultime tre recensioni create" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/public/getRecensioniBarbiere" baseUrl="https://mattbarbershop.herokuapp.com" summary="getRecensioniBarbiere" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idBarbiere" required="true" %}
Id barbiere
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Ritorna tutte le recensioni relative ad un barbiere" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/public/getProdotti" baseUrl="https://mattbarbershop.herokuapp.com" summary="getProdotti" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-response status="200: OK" description="Ritorna tutti i prodotti creati" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}
{% endswagger %}

## DeleteMapping

{% swagger method="delete" path="/public/eliminaPrenotazione/{idPrenotazione}" baseUrl="https://mattbarbershop.herokuapp.com" summary="eliminaPrenotazione" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="path" name="idPrenotazione" %}
Id prenotazione
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Prenotazione eliminata con successo" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}
{% endswagger %}
