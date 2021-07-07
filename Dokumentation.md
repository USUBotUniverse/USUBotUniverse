# USUBotUniverse Kommunikationsprotokoll

[TOC]

## Anbindung

Zur Anbindung von verschiedenenen Front-end's stehen im KBot sogenannte "Connector's" zur Verfügung. Über diese können verschiedenen Kommunikationsprotokolle zur Anbindung verschiedenener Anwendung generiert werden. Jeder Konnektor wird über einen speziellen Rest-Service mittels eindeutiger URL angesprochen. Die URL ist jeweils in der KBot-Konfiguration des jeweiligen Connector's zu finden.

Typischerweise ist solch eine URL wie folgt aufgebaut:

```bash
http(s)://<server>[:<port>]/kbot-api/interact/<kbot-id>/<con-type>:<con-name>
```

| Name       | Bedeutung                                   |
| ---------- | ------------------------------------------- |
| *server*   | IP oder Domain des Servers                  |
| *port*     | Port des Servers                            |
| *kbot-id*  | eindeutige ID des KBot's                    |
| *con-type* | Typ des Connector's                         |
| *con-name* | Name/eindeutiger Bezeichner des Connector's |

Ein Beispiel:

​	http://localhost:9190/kbot-api/interact/kcpreview/default:Default



Aktuell stehen zwei Konnektoren zur Verfügung.

1. Default - der Standard-Connector
2. Facebook - der Facebook Messenger-Connector



## Der "Default" - Connector

Der Default Connector ist der der Standard-Connector des KBot. Sowohl KBot Widget als auch KFirst verwenden diesen zur Kommunikation. Er sollte auch immer dann verwendet werden, wenn eine Drittanwendung mit KBot kommunizieren möchte. Sollten bestimmte Dinge nicht mit ihm abgebildet werden, so sollten ein neuer Connector-Typ entwickelt werden. Der Default-Connector kommunziert mittels http(s). Anfragen werden immer als HTTP-Post Commands gesendet.

### Die Basis JSON-Struktur

KBot verwendet zum Austausch von Daten eine JSON-Datenstruktur. Es gibt eine Basisstruktur für Anfragen "Requests" und eine Basisstruktur für Antworten "Responses". 

#### Request

Das Anfrage-Objekt eines Requests, besteht aus zwei wesentlichen Elementen.

1. "session" - Element
2. "request" - Element

Das "session" Element beinhaltet wesentliche Parameter zur eigentlichen Session-Verwaltung einer KBot-Kommunikation. Das "request" Element enthält die eigentliche Anfrage an den KBot.

##### Aufbau des "session"-Elementes:

```json
"session": 
{  
      "application":
      {  
        "applicationId":"<Name der aufrufenden Anwendung>"
      },
      "user":
      {  
        "userId":"<ID des verwendeten Users>"
      },
      "attributes":{ },
      "sessionId":"<ID der Session>",
      "new":<true oder false>
}
```

###### Bedeutung der "session" Attribute:

| Attribute   | Bedeutung                                                    | Typ     | Optional |
| ----------- | ------------------------------------------------------------ | ------- | -------- |
| application | Ein Object, welches Informationen über die aufrufende Application beinhaltet. | Object  | nein     |
| user        | Ein Object, welches Informationen über den User, welcher eine Anfrage stellt, beinhaltet. Hat bisher noch keine bedeutung und es sollte "Kbot-Default-User" als Wert verwendet werden. | Object  | ja       |
| attributes  | Ein Object, welches eine Key/Value Liste representiert, mit der frei definierbare Werte mit der Anfrage übersendet werden können. | Object  | ja       |
| sessionId   | Die Id der Kommunikations-Session. Diese sollte bei jedem Request mitgegeben werden. Wird keine "Session-ID" mitgegeben, so wird eine neue Kommunikations-Session erstellt und deren ID wird mit dem Respons geliefert. Eine Aufrufende Anwendung kann auch ein eigene ID hier angeben. Sollte unter dieser ID eine Session existieren, so verwendet der KBot diese Session. Wenn nicht, legt KBat intern eine neue Session an und verwendet die gegebene Session als ID. | String  | nein     |
| new         | Gibt an, ob eine neue Session unter dieser ID angelegt werden soll. Dies ist dann relevant, falls unter einer angegebenen Session-ID bereits eine Session auf dem Server existiert. Diese Session wird dann verworfen und eine neue Session wird erstellt. | Boolean | ja       |

###### Das "application" Arttribut

| Attribut      | Bedeutung                                                    | Typ    | Optional |
| ------------- | ------------------------------------------------------------ | ------ | -------- |
| applicationId | Dieses Attribut beinhaltet den Namen der aufrufenden Anwendung. z.B. 'Alexa'. | String | nein     |

###### Das "user" Attribut

| Attribute | Bedeutung                                                    | Typ    | Optional |
| --------- | ------------------------------------------------------------ | ------ | -------- |
| userId    | Die id des Users, welcher die Anfrage sendet. Hat im Moment noch keine Bedeutung. | String | nein     |

###### Das "attributes" Attribut

An diesem Object können beliebige Key/Value Paare definiert werden.



##### Aufbau des "request"-Elementes:

```json
"request":
{  
      "type":"<Typ des Requests>",
      "requestId":"<ID des Requests>",
      "locale":"<verwendete Sprache>",
      "timestamp":"<Timestamp im UTC Format>",
      "payload":
      {  
          "type":"<Typ/Inhalt des Payload>"
      }
}
```

###### Bedeutung der "request" - Attribute

| Attribut  | Bedeutung                                                    | Typ    | Optional |
| --------- | ------------------------------------------------------------ | ------ | -------- |
| type      | Gibt den Typ der Anfrage an. Mögliche Werte sind im Moment 'command' und 'message'. | String | nein     |
| requestId | Die ID des Requests. Diese wird von der anfragenden Anwendung gesetzt. Sie hat keine Auswirkung auf die Kommunikation ansich. Im Response wird diese ID wieder zurück geliefert. | String | ja       |
| locale    | Gibt die verwendete (Text) Sprache für die Anfrage an.       | String | nein     |
| timestamp | Ein Zeitstempel, wann der Request angelegt wurde. Dieser wird von der aufrufenden Anwendung vergeben und derzeit keine weitere Bedeutung. Der Zeitstempel sollte im UTC Zeitformat angegeben werden. | String | ja       |
| payload   | Hier befinden sich die eigentlichen Kommunikationsdaten. Der Aufbau dieses Objektes hängt vom angegebenen Request-Type an. | Object | nein     |

###### 'payload' vom Typ 'command'

Mithilfe des 'payload' vom Typ 'command', wird angegeben, welche 'native' KBot Aktion ausgeführt werden soll. 

```json
"payload":{  
    "type":"<action>"
}
```
| Attribut | Bedeutung                                                    | Typ    | Optional |
| -------- | ------------------------------------------------------------ | ------ | -------- |
| type     | Gibt das auszuführende Kommand an.<br />Mögliche Commands sind:<br />'startSession', resetSession', 'endSession' | String | ja       |

Mögliche Aktionen:

| Aktion       | Bedeutung                                                    | Parameter |
| ------------ | ------------------------------------------------------------ | --------- |
| startSession | Startet eine neue Session. Sollte eine Session über die 'sessionId' angegeben worden sein, so wird diese beendet, falls vorhanden. | keine     |
| resetSession | Beendet die aktuelle Session und startet sie neu. Die Session selber wird anhand der 'sessionId' im Session-Object definiert. | keine     |
| endSession   | Beendet die aktuelle Session. Die Session selber wird anhand der 'sessionId' im Session-Object definiert. |           |

###### 'payload' vom Typ 'message'

```json
"payload":{
    "text":"test"
}
```

| Attribut | Bedeutung                                                    | Typ    | Optional |
| -------- | ------------------------------------------------------------ | ------ | -------- |
| text     | Die eigentliche textuelle Eingabe, die an den KBot gesendet werden soll. | String | ja       |

##### Beispiel für eine Request-Anfrage:

```json
{  
  "session":{  
      "application":{  
        "applicationId":"Knowledge Bot Widget"
      },
      "user":{  
        "userId":"Kbot-Default-User"
      },
      "attributes":{ },
      "sessionId":"405eed2f:166f7fa721a:-7ffc",
      "new":true
  },
  "request":{  
      "type":"command",
      "requestId":"1",
      "locale":"de",
      "timestamp":"2018-11-09T16:06:52.331Z",
      "payload":{  
        "type":"startSession"
     }
  }
}
```



#### Response

Das Antwort-Objekt eines Response, besteht aus zwei wesentlichen Elementen.

1. "sessionAttributes" - Element
2. "response" - Element

Das "sessionAttributes" Element beinhaltet wesentliche Parameter zur eigentlichen Session-Verwaltung einer KBot-Kommunikation. Das "response"-Element beinhalt alle Informationen zu einer Anfrage.

##### Aufbau des "sessionAttributes"-Elementes:

```json
"sessionAttributes" : {
  "sessionId" : "<session-id>"
}
```

| Attribute | Bedeutung                                                    | Typ                                                 | Optional |
| --------- | ------------------------------------------------------------ | -------- | -------- |
| sessionId    | Die id der Session, zu der die Anfrage gehört. | String | nein     |

##### Aufbau des "response"-Elementes:

| Attribute        | Bedeutung                                                    | Typ                  | Optional |
| ---------------- | ------------------------------------------------------------ | -------------------- | -------- |
| shouldEndSession | gibt an, ob die Session auf dem Server beendet wurde. die aufrufende Anwendung sollte ebenfalls ihre Session oder ähnliches beenden. | Boolean              | ja       |
| type             | Gibt den Typ der 'response' an. Im Momment existiert nur der Typ "reply". | String               | nein     |
| payload          | Beinhaltet die Kommunikationsdaten, Antworten auf ein Command oder Anfrage.  Ein payload kann ein einzelnes Object oder ein array von Objects sein. Stand heute wird ein Array mit einem Object geliefert. Die aufrufende Anwendung sollte trotzdem prüfen, ob es ein Array oder ein einzelnes Object ist. | Object oder Object[] | nein     |

###### 'payload' vom Typ 'reply'

```json
"payload":[ 
    {
      "language" : "<sprache>",
      "conversation" : {
        "bubbles":[
          {
            "content" : "<text>",
            "delay" : <delay in ms>,
            "metadata" : { },
            "silentDelay" : <true oder false>
          }
        ]
      },
      "interaction" : {
        "multiSelectAllowed" : <true oder false>,
        "inputType" : "<input type>",
        "inputOptions" : [ {
          "label" : "<Label>",
          "value" : "<command>",
          "styleInfo" : "<css style>",
          "metadata" : { }
        } ],
        "inputFieldActive" : <true oder false>,
        "inputHint" : "<hint>"
      }
    } 
]
```

| Attribute    | Bedeutung                                                    | Typ    | Optional |
| ------------ | ------------------------------------------------------------ | ------ | -------- |
| language     | Gibt an, in welcher Sprache die Antwort verfasst ist.        | String | nein     |
| topic        | Gibt das Topic/Thema der Antwort an.                         | String | ja       |
| conversation | Enthält die Textausgabe des Bots in form von einzelnen bubbles. | Object | nein     |
| interaction  | Beinhaltet die möglichen Eingabeoptionen für den Anwender.   | Object | nein     |

###### Das 'conversation' Attribut

```json
"conversation" : {
        "bubbles":[
          {
            "content" : "<text>",
            "speech" : "<ssml>",
            "delay" : <delay in ms>,
            "metadata" : { },
            "silentDelay" : <true oder false>
          }
        ]
      }
```

| Attribute | Bedeutung                                             | Typ   | Optional |
| --------- | ----------------------------------------------------- | ----- | -------- |
| bubbles   | Beinhaltet ein oder mehrere Textausgaben für den Bot. | Array | nein     |

###### Das 'bubbles' Attribute

```json
"bubbles":[
    {
        "content" : "<text>",
        "speech" : "<ssml>",
        "delay" : <delay in ms>,
        "metadata" : { },
    	"silentDelay" : <true oder false>
    }
]
```

| Attribute   | Bedeutung                                                    | Typ     | Optional |
| ----------- | ------------------------------------------------------------ | ------- | -------- |
| content     | Der auszugebenende Text innerhalb einer Sprechblase.         | String  | ja       |
| speech      | Der zu sprechende Text einer Sprechblase in SSML Syntax.     | String  | ja       |
| delay       | Gibt die Wartezeit in Millisekunden an, bevor die Sprechblase im Frontend angezeigt werden soll. Wenn ein Wert <= 0 angegeben wird, so wird die Sprechblase sofort angezeigt. Defaultwert ist 0. | Integer | ja       |
| silentDelay | Wenn hier der Wert 'false' angegeben wird, so wird für die Wartezeit 'delay' eine Animation (Denkpause) angezeigt. Für den Wert 'true' wird nichts angezeigt. Der Defaultwert ist 'false'. | Boolean | ja       |
| metadata    | Ein Key/Value Speicher für zusätzliche Informationen.        | Object  | ja       |

###### Das 'interaction' Attribut

```json
"interaction" : {
    "multiSelectAllowed" : <true oder false>,
    "inputType" : "<input type>",
    "inputOptions" :[ 
      {
    	"label" : "<Label>",
    	"value" : "<command>",
    	"styleInfo" : "<css style>",
    	"metadata" : { }
	   }
     ],
	"inputFieldActive" : <true oder false>,
	"inputHint" : "<hint>"
}
```

| Attribute          | Bedeutung                                                    | Typ     | Optional |
| ------------------ | ------------------------------------------------------------ | ------- | -------- |
| multiSelectAllowed | Abhängig vom Eingabetyp, gibt dieser Wert an, ob eine Mehrfachauswahl möglich ist. Wird im Zusammenhang mit 'select'-Eingaben verwendet. | Boolean | ja       |
| inputType          | Gibt die Art der Eingabemöglichkeit durch den Anwender an.   | String  | nein     |
| allowAutoComplete  | Gibt an, ob an der Oberfläche die AutoComplete -Funktion aktiviert werden soll. Hierbei handelt es sich um ein KBot-Feature. | Boolean | ja       |
| inputOptions       | Wird ebenfalls im Zusammenhang mit 'select'-Eingaben verwendet. Hier werden die einzelnen möglichen Optionen gelistet. | Array   | ja       |
| inputFieldActive   | Gibt an, ob textuelle Eingaben möglich sind.                 | Boolean | nein     |
| inputFieldVisible  | gibt an, ob das Eingabefeld angezeigt werden soll oder nicht. | Boolean | ja       |
| inputHint          | Gibt einen Hinweis/Hilfe-Text an, welcher im Eingabefeld angezeigt wird und dem Anwender mitteilt, welche aktion er ausführen kann. | String  | ja       |
| inputFieldPattern  | Legt das Eingabeformat für das Eingabefeld fest.             | String  | ja       |
| showResultCount    | Gibt an, wieviele Element angezeigt werden sollen, bevor ein Paging aktiviert wird. Dies ist nur relevant in Verbindung mit der inputOptions Eigenschaft. | Integer | ja       |

###### Das 'inputOptions' Attribut

```json
"inputOptions" :[ 
      {
    	"label" : "<Label>",
    	"value" : "<command>",
    	"styleInfo" : "<css style>",
    	"metadata" : { }
	   }
     ]
```

| Attribute | Bedeutung                                                    | Typ     | Optional |
| --------- | ------------------------------------------------------------ | ------- | -------- |
| label     | Der anzuzeigende Text.                                       | Boolean | nein     |
| inputType | Der Wert, welcher bei Selektierung als Request an den KBot gesendet wird. Der 'request' sollte vom Typ 'message' sein. | String  | nein     |
| styleInfo | Mögliche Zusatzinfo, welche verwendet werden kann um die die Elemente per CSS zu stylen. | String  | ja       |
| metadata  | Key/Value Store. Wird im Moment nicht verwendet.             | Object  | ja       |



##### Beispiel für eine Response-Antwort:

```json
{
  "sessionAttributes" : {
    "sessionId" : "633f9264:16708c1a9b8:-7ffd"
  },
  "response" : {
    "shouldEndSession" : false,
    "type" : "reply",
    "payload" : [ {
      "language" : "de",
      "conversation" : {
        "bubbles" : [ {
          "content" : "tester",
          "delay" : 0,
          "metadata" : { },
          "silentDelay" : false
        } ]
      },
      "interaction" : {
        "multiSelectAllowed" : false,
        "inputType" : "vertical_select",
        "inputOptions" : [ {
          "label" : "Guided Tour durch die angebotenen Tips und Tricks",
          "value" : "#cmd://odin?i=int487",
          "styleInfo" : "dialog",
          "metadata" : { }
        } ],
        "inputFieldActive" : true,
        "inputHint" : "Hier eingeben oder Option wählen... "
      }
    } ]
  }
}
```



### Beispiel Requests für die Kommunikation

#### Eröffnung der Kommunikation

Die Eröffnung einer Kommunikation kann entweder über das 'startSession' - Command oder direkt über das Senden einer Anfrage durchgeführt werden.

<u>JSON Request Structure</u>

```json
{
    "session":{
        "application":{
            "applicationId":"Knowledge Bot Widget"
        },
        "user":{
            "userId":"Kbot-Default-User"
        },
        "attributes":{},
        "new":true
    },
    "request":{
        "type":"command",
        "requestId":"0",
        "locale":"de",
        "timestamp":"2018-11-12T21:40:36.873Z",
        "payload":{
            "type":"startSession"
        }
    }
}
```

<u>JSON Response Structure:</u>

```JSON
{
  "sessionAttributes" : {
    "sessionId" : "633f9264:16708c1a9b8:-7ffb"
  },
  "response" : {
    "shouldEndSession" : false,
    "type" : "reply",
    "payload" : [ {
      "language" : "de",
      "conversation" : {
        "bubbles" : [ {
          "content" : "tester",
          "delay" : 0,
          "metadata" : { },
          "silentDelay" : false
        } ]
      },
      "interaction" : {
        "multiSelectAllowed" : false,
        "inputType" : "vertical_select",
        "inputOptions" : [ {
          "label" : "Guided Tour durch die angebotenen Tips und Tricks",
          "value" : "#cmd://odin?i=int487",
          "styleInfo" : "dialog",
          "metadata" : { }
        } ],
        "inputFieldActive" : true,
        "inputHint" : "Hier eingeben oder Option wählen... "
      }
    } ]
  }
}
```
<u>Beispiel als cUrl:</u>

```bash
curl "http://localhost:9190/kbot-api/interact/kcpreview/widget:Default" -H "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" -H "Accept: */*" -H "Accept-Language: de,en-US;q=0.7,en;q=0.3" -H "Content-Type: application/json; charset=UTF-8" -H "Connection: keep-alive" --data "{\"session\":{\"application\":{\"applicationId\":\"Knowledge Bot Widget\"},\"user\":{\"userId\":\"Kbot-Default-User\"},\"attributes\":{},\"new\":true},\"request\":{\"type\":\"command\",\"requestId\":\"1\",\"locale\":\"de\",\"timestamp\":\"2018-11-09T16:06:52.331Z\",\"payload\":{\"type\":\"startSession\"}}}"
```



#### Stellen einer Frage

<u>JSON Request Structure</u>

```json
{
    "session":{
    	"application":{
        	"applicationId":"Knowledge Bot Widget"
    	},
    	"user":{
        	"userId":"Kbot-Default-User"
    	},
    	"attributes":{},
    	"sessionId":"633f9264:16708c1a9b8:-7ffb",
    	"new":false
	},
    "request":{
        "type":"message",
        "requestId":"1",
        "locale":"de",
        "timestamp":"2018-11-12T21:48:04.102Z",
        "payload":{
            "text":"Was ist alexa brain"
        }
    }
}
```

<u>JSON Response Structure</u>

```json
{
  "sessionAttributes" : {
    "sessionId" : "633f9264:16708c1a9b8:-7ffb"
  },
  "response" : {
    "shouldEndSession" : false,
    "type" : "reply",
    "payload" : [ {
      "language" : "de",
      "conversation" : {
        "bubbles" : [ {
          "content" : "Alexa Brain ist eine tolles Stück Software.",
          "delay" : 0,
          "silentDelay" : false
        }, {
          "content" : "War dies die richtige Antwort?",
          "delay" : 1000,
          "silentDelay" : true
        } ]
      },
      "interaction" : {
        "multiSelectAllowed" : false,
        "inputType" : "vertical_select",
        "inputOptions" : [ {
          "label" : "Ja",
          "value" : "#cmd://yes"
        }, {
          "label" : "Nein",
          "value" : "#cmd://no"
        } ],
        "inputHint" : "Option wählen...",
        "inputFieldActive" : false
      }
    } ]
  }
}
```

<u>Beispiel als cUrl:</u>

```bash
curl "http://knowledgefirst-poc.usu.de/kbot-api/interact/kcpreview/widget:Default" -H "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" -H "Accept: */*" -H "Accept-Language: de,en-US;q=0.7,en;q=0.3" --compressed -H "Content-Type: application/json; charset=UTF-8" -H "Connection: keep-alive" --data "{\"session\":{\"application\":{\"applicationId\":\"Knowledge Bot Widget\"},\"user\":{\"userId\":\"Kbot-Default-User\"},\"attributes\":{},\"sessionId\":\"633f9264:16708c1a9b8:-7ffb\",\"new\":false},\"request\":{\"type\":\"message\",\"requestId\":\"2\",\"locale\":\"de\",\"timestamp\":\"2018-11-12T21:48:04.102Z\",\"payload\":{\"text\":\"Was ist alexa brain\"}}}"
```



#### Senden einer Option

<u>JSON Request Structure</u>

```json
{
    "session":{
        "application":{
            "applicationId":"Knowledge Bot Widget"
        },
        "user":{
            "userId":"Kbot-Default-User"
        },
        "attributes":{},
        "sessionId":"633f9264:16708c1a9b8:-7ffb",
        "new":false
    },
    "request":{
        "type":"message",
        "requestId":"3",
        "locale":"de",
        "timestamp":"2018-11-12T21:59:02.750Z",
        "payload":{
            "text":"#cmd://yes"
        }
    }
}
```

<u>JSON Response Structure</u>

```json
{
  "sessionAttributes" : {
    "sessionId" : "633f9264:16708c1a9b8:-7ffb"
  },
  "response" : {
    "shouldEndSession" : false,
    "type" : "reply",
    "payload" : [ {
      "language" : "de",
      "conversation" : {
        "bubbles" : [ {
          "content" : "Super!",
          "delay" : 0,
          "silentDelay" : false
        }, {
          "content" : "Solltest Du noch weitere Fragen haben, schieß los ...",
          "delay" : 500,
          "silentDelay" : false
        } ]
      },
      "interaction" : {
        "inputType" : "text",
        "inputHint" : "Hier eingeben...",
        "inputFieldActive" : true
      }
    } ]
  }
}
```



<u>Beispiel als cUrl</u>

```bash
curl "http://knowledgefirst-poc.usu.de/kbot-api/interact/kcpreview/widget:Default" -H "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" -H "Accept: */*" -H "Accept-Language: de,en-US;q=0.7,en;q=0.3" --compressed -H "Referer: http://knowledgefirst-poc.usu.de/kbot-market/bots/preview/kcpreview" -H "Content-Type: application/json; charset=UTF-8" -H "x-kbot-token: /*\"kcpreview\"*/" -H "lang: en" -H "x-referer: http://knowledgefirst-poc.usu.de/kbot-market/bots/preview/kcpreview" -H "Connection: keep-alive" -H "Cookie: _ga=GA1.2.756883637.1525766520" --data "{\"session\":{\"application\":{\"applicationId\":\"Knowledge Bot Widget\"},\"user\":{\"userId\":\"Kbot-Default-User\"},\"attributes\":{},\"sessionId\":\"633f9264:16708c1a9b8:-7ffb\",\"new\":false},\"request\":{\"type\":\"message\",\"requestId\":\"4\",\"locale\":\"de\",\"timestamp\":\"2018-11-12T21:59:02.750Z\",\"payload\":{\"text\":\"#cmd://yes\"}}}"
```

#### Beenden der Kommunikation

<u>JSON Request Structure</u>

```json
{
    "session":{
        "application":{
            "applicationId":"Knowledge Bot Widget"
        },
        "user":{
            "userId":"Kbot-Default-User"
        },
        "attributes":{},
        "new":false,
        "sessionId": "633f9264:16708c1a9b8:-7ffb"
    },
    "request":{
        "type":"command",
        "requestId":"3",
        "locale":"de",
        "timestamp":"2018-11-12T21:59:30.750Z",
        "payload":{
            "type":"endSession"
        }
    }
}
```

<u>JSON Response Structure</u>

```json
{
  "sessionAttributes" : {
    "sessionId" : "633f9264:16708c1a9b8:-7ffb"
  },
  "response" : {
    "shouldEndSession" : true,
    "type" : "reply",
    "payload" : [ {
      "language" : "de",
      "conversation" : {
        "bubbles" : [ {
          "content" : "Bis zum nächsten mal. Tschüß!",
          "delay" : 0,
          "metadata" : { },
          "silentDelay" : false
        } ]
      }
    } ]
  }
}
```

<u>Beispiel als cUrl:</u>

```bash
curl "http://localhost:9190/kbot-api/interact/kcpreview/widget:Default" -H "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" -H "Accept: */*" -H "Accept-Language: de,en-US;q=0.7,en;q=0.3" --compressed -H "Content-Type: application/json; charset=UTF-8" -H "Connection: keep-alive" --data "{\"session\":{\"application\":{\"applicationId\":\"Knowledge Bot Widget\"},\"user\":{\"userId\":\"Kbot-Default-User\"},\"attributes\":{},\"new\":false,\"sessionId\": \"633f9264:16708c1a9b8:-7ffb\"},\"request\":{\"type\":\"command\",\"requestId\":\"1\",\"locale\":\"de\",\"timestamp\":\"2018-11-09T16:06:53.001Z\",\"payload\":{\"type\":\"endSession\"}}}"
```

