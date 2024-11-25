# AuthenticationController

{% hint style="info" %}
**Nota:** La login restituisce una stringa JWT che serve per autenticarsi nelle chiamate presenti negli altri controller.
{% endhint %}

{% swagger baseUrl="https://mattbarbershop.herokuapp.com" method="post" path="/login" summary="login" %}
{% swagger-description %}
API richiamata per effettuare la login
{% endswagger-description %}

{% swagger-parameter in="body" name="username" required="true" type="string" %}
Username dell'utente
{% endswagger-parameter %}

{% swagger-parameter in="body" name="password" required="true" type="string" %}
Password dell'utente
{% endswagger-parameter %}

{% swagger-response status="200" description="Login effettuato" %}
```json
[
    {
        "jwt":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2MDgzODQ4OCwiZXhwIjoxNjYwODc0NDg4fQ.h_NjyyfH8cRRQlXhHhqg6sAScvjDVY4usqAD8hwLOpg"
    },
    {
        "id":1,
        "username":"admin",
        "enable":true,
        "credentialExpired":true,
        "accountNonExpired":true,
        "accountNonLocked":true,
        "authorities":[
            {"role":"ROLE_ADMIN"}
        ]
    }
]
```
{% endswagger-response %}

{% swagger-response status="403: Forbidden" description="Credenziali errate" %}
```json
{
    "timestamp": "2022-08-18T16:03:45.877+00:00",
    "status": 403,
    "error": "Forbidden",
    "path": "/login"
}
```
{% endswagger-response %}
{% endswagger %}
