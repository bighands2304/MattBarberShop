# AdminController

{% hint style="warning" %}
**Attenzione:** Tutti i metodi di questo controller possono essere effettuati solamente autenticandosi con un utente che abbia come ruolo admin.
{% endhint %}

## PostMapping

{% swagger method="post" path="/admin/nuovoAdmin" baseUrl="https://mattbarbershop.herokuapp.com" summary="nuovoAdmin" %}
{% swagger-description %}
API richiamata per effettuare la creazione di un nuovo utente admin
{% endswagger-description %}

{% swagger-parameter in="body" name="mail" required="true" %}
Mail admin
{% endswagger-parameter %}

{% swagger-parameter in="body" name="password" required="true" %}
Password admin
{% endswagger-parameter %}

{% swagger-parameter in="body" name="nome" %}
Nome admin
{% endswagger-parameter %}

{% swagger-parameter in="body" name="cognome" %}
Cognome admin
{% endswagger-parameter %}

{% swagger-parameter in="body" name="cellulare" %}
Cellulare admin
{% endswagger-parameter %}

{% swagger-parameter in="body" name="foto" %}
Path della foto admin
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Admin creato correttamente" %}
```json
{
    "id": 2,
    "mail": "mariorossi@mail.com",
    "role": "ROLE_ADMIN",
    "nome": "Mario",
    "cognome": "Rossi",
    "cellulare": "1234567890",
    "foto": "C:\\Users\\utente\\Pictures\\pic.jpg"
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/admin/nuovoBarbiere" baseUrl="https://mattbarbershop.herokuapp.com" summary="nuovoBarbiere" %}
{% swagger-description %}
API richiamata per effettuare la creazione di un nuovo utente barbiere
{% endswagger-description %}

{% swagger-parameter in="body" name="mail" required="true" %}
Mail barbiere
{% endswagger-parameter %}

{% swagger-parameter in="body" name="password" required="true" %}
Password barbiere
{% endswagger-parameter %}

{% swagger-parameter in="body" name="nome" required="true" %}
Nome barbiere
{% endswagger-parameter %}

{% swagger-parameter in="body" name="cognome" %}
Cognome barbiere
{% endswagger-parameter %}

{% swagger-parameter in="body" name="cellulare" %}
Cellulare barbiere
{% endswagger-parameter %}

{% swagger-parameter in="body" name="foto" %}
Path della foto barbiere
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Barbiere creato correttamente" %}
```json
{
    "id": 3,
    "mail": "mariorossi@mail.com",
    "role": "ROLE_BARBIERE",
    "nome": "Mario",
    "cognome": "Rossi",
    "cellulare": "1234567890",
    "foto": "C:\\Users\\utente\\Pictures\\pic.jpg"
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/admin/nuovoTrattamento" baseUrl="https://mattbarbershop.herokuapp.com" summary="nuovoTrattamento" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="nome" required="true" %}
Nome trattamento
{% endswagger-parameter %}

{% swagger-parameter in="body" name="prezzo" required="true" %}
Prezzo trattamento
{% endswagger-parameter %}

{% swagger-parameter in="body" name="durata" %}
Durata trattamento espressa in minuti
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Trattamento creato correttamente" %}
```json
{
    "id": 1,
    "nome": "Shampoo",
    "prezzo": 5.0,
    "durata": 5
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/admin/nuovoProdotto" baseUrl="https://mattbarbershop.herokuapp.com" summary="nuovoProdotto" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="nome" required="true" %}
Nome prodotto
{% endswagger-parameter %}

{% swagger-parameter in="body" name="prezzo" required="true" %}
Prezzo prodotto
{% endswagger-parameter %}

{% swagger-parameter in="body" name="descrizione" %}
Descrizione prodotto
{% endswagger-parameter %}

{% swagger-parameter in="body" name="peso" %}
Peso prodotto espresso in kg
{% endswagger-parameter %}

{% swagger-parameter in="body" name="foto" %}
Path della foto prodotto
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Prodotto creato correttamente" %}
```json
{
    "id": 1,
    "nome": "Tinta",
    "descrizione": "Tinta capelli rossa",
    "prezzo": 25.0,
    "peso": 2.0,
    "foto": "C:\\Users\\utente\\Pictures\\pic.jpg"
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

## GetMapping

{% swagger method="get" path="/admin/getAdmin" baseUrl="https://mattbarbershop.herokuapp.com" summary="getAdmin" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-response status="200: OK" description="Ritorna tutti gli admin creati" %}
```json
[
    {
        "id": 1,
        "mail": "admin",
        "role": "ROLE_ADMIN",
        "nome": null,
        "cognome": null,
        "cellulare": null,
        "foto": null
    },
    {
        "id": 2,
        "mail": "mariorossi@mail.com",
        "role": "ROLE_ADMIN",
        "nome": "Mario",
        "cognome": "Rossi",
        "cellulare": "1234567890",
        "foto": "C:\\Users\\utente\\Pictures\\pic.jpg"
    }
]
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/admin/getOrdini" baseUrl="https://mattbarbershop.herokuapp.com" summary="getOrdini" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-response status="200: OK" description="Ritorna tutti gli ordini creati" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

## PatchMapping

{% swagger method="patch" path="/admin/modificaTrattamento" baseUrl="https://mattbarbershop.herokuapp.com" summary="modificaTrattamento" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="IdTrattamento" required="true" %}
Id trattamento
{% endswagger-parameter %}

{% swagger-parameter in="body" name="nome" %}
Nome trattamento
{% endswagger-parameter %}

{% swagger-parameter in="body" name="prezzo" %}
Prezzo trattamento
{% endswagger-parameter %}

{% swagger-parameter in="body" name="durata" %}
Durata trattamento espressa in minuti
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Trattamento modificato con successo" %}
```json
{
    "id": 1,
    "nome": "Shampoo",
    "prezzo": 5.0,
    "durata": 5
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="patch" path="/admin/modificaProdotto" baseUrl="https://mattbarbershop.herokuapp.com" summary="modificaProdotto" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idProdotto" required="true" %}
Id prodotto
{% endswagger-parameter %}

{% swagger-parameter in="body" name="nome" %}
Nome prodotto
{% endswagger-parameter %}

{% swagger-parameter in="body" name="prezzo" %}
Prezzo prodotto
{% endswagger-parameter %}

{% swagger-parameter in="body" name="descrizione" %}
Descrizione prodotto
{% endswagger-parameter %}

{% swagger-parameter in="body" name="peso" %}
Peso prodotto espresso in kg
{% endswagger-parameter %}

{% swagger-parameter in="body" name="foto" %}
Path della foto prodotto
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Prodotto modificato con successo" %}
```json
{
    "id": 1,
    "nome": "Tinta",
    "descrizione": "Tinta capelli rossa",
    "prezzo": 25.0,
    "peso": 2.0,
    "foto": "C:\\Users\\utente\\Pictures\\pic.jpg"
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

## DeleteMapping

{% swagger method="delete" path="/admin/eliminaAdmin/{idAdmin}" baseUrl="https://mattbarbershop.herokuapp.com" summary="eliminaAdmin" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="path" name="idAdmin" required="true" %}
Id admin
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Admin eliminato con successo" %}
```json
[
    {
        "id": 1,
        "mail": "admin",
        "role": "ROLE_ADMIN",
        "nome": null,
        "cognome": null,
        "cellulare": null,
        "foto": null
    }
]
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="delete" path="/admin/eliminaBarbiere/{idBarbiere}" baseUrl="https://mattbarbershop.herokuapp.com" summary="eliminaBarbiere" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="path" name="idBarbiere" required="true" %}
Id barbiere
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Barbiere eliminato con successo" %}
```json
[]
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="delete" path="/admin/eliminaTrattamento/{idTrattamento}" baseUrl="https://mattbarbershop.herokuapp.com" summary="eliminaTrattamento" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="path" name="idTrattamento" required="true" %}
Id trattamento
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Trattamento eliminato con successo" %}
```json
[]
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="delete" path="/admin/eliminaProdotto/{idProdotto}" baseUrl="https://mattbarbershop.herokuapp.com" summary="eliminaProdotto" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="path" name="idProdotto" required="true" %}
Id prodotto
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Prodotto eliminato con successo" %}
```json
[]
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come admin" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}
{% endswagger %}
