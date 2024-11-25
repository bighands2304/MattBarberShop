# UserController

{% hint style="warning" %}
**Attenzione:** Tutti i metodi di questo controller possono essere effettuati solamente autenticandosi con un utente che abbia come ruolo user o admin.
{% endhint %}

## PostMapping

{% swagger method="post" path="/user/nuovoOrdine" baseUrl="https://mattbarbershop.herokuapp.com" summary="nuovoOrdine" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idIndirizzoDestinatario" required="true" %}
Id indirizzo destinatario
{% endswagger-parameter %}

{% swagger-parameter in="body" name="prodotti" required="true" %}
Lista prodotti
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Ordine creato correttamente" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come user o admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/user/nuovoIndirizzo" baseUrl="https://mattbarbershop.herokuapp.com" summary="nuovoIndirizzo" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idUtente" required="true" %}
Id utente a cui appartiene l'indirizzo
{% endswagger-parameter %}

{% swagger-parameter in="body" name="città" required="true" %}
Città indirizzo
{% endswagger-parameter %}

{% swagger-parameter in="body" name="cap" required="true" %}
Cap indirizzo
{% endswagger-parameter %}

{% swagger-parameter in="body" name="via" required="true" %}
Via indirizzo
{% endswagger-parameter %}

{% swagger-parameter in="body" name="numeroCivico" required="true" %}
Numero civico indirizzo
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Indirizzo creato correttamente" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore nella creazione con relativi messaggi d'errore" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come user o admin" %}

{% endswagger-response %}
{% endswagger %}

## GetMapping

{% swagger method="get" path="/user/getOrdiniDestinatario" baseUrl="https://mattbarbershop.herokuapp.com" summary="getOrdiniDestinatario" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idUtente" required="true" %}
Id utente
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Ritorna tutti gli ordini relativi ad un utente" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come user o admin" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="get" path="/user/getIndirizziUtente" baseUrl="https://mattbarbershop.herokuapp.com" summary="getIndirizziUtente" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="idUtente" required="true" %}
Id utente
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Ritorna tutti gli indirizzi relativi ad un utente" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come user o admin" %}

{% endswagger-response %}
{% endswagger %}

## DeleteMapping

{% swagger method="delete" path="/user/eliminaUtente/{idUtente}" baseUrl="https://mattbarbershop.herokuapp.com" summary="eliminaUtente" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="path" name="idUtente" required="true" %}
Id utente
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="Utente eliminato con successo" %}
```javascript
{
    // Response
}
```
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="Errore con relativi messaggi" %}

{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Necessaria l'autenticazione come user o admin" %}

{% endswagger-response %}
{% endswagger %}
