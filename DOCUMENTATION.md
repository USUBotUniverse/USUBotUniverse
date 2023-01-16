# USUBotUniverse communication protocol

[TOC]

## Connection

For the connection of different front-end's so called "Connector's" are available in the USUBotUniverse. Over these different communication protocols for the binding of different application can be generated. Each connector is addressed via a special rest service using a unique URL. The URL can be found in the bot configuration of the respective connector.

Typically, such a URL is structured as follows:

```bash
http(s)://<server>[:<port>]/kbot-api/interact/<kbot-id>/<con-type>:<con-name>
```

| Name       | Description                                   |
| ---------- | ------------------------------------------- |
| *server*   | IP or Domain of the servers                  |
| *port*     | Port of the servers                            |
| *kbot-id*  | unique ID of the bot                   |
| *con-type* | Type of the Connector                         |
| *con-name* | Name/unique identifier of the connector |

An example:

​	http://localhost:9190/kbot-api/interact/kcpreview/default:Default



Currently, two connectors are available.

1. Default - the default connector
2. Facebook - the Facebook Messenger connector



## The "Default" - Connector

The default connector is the standard connector with which the USU's own bot (KBot) is connected. Both KBot Widget and KFirst use it for communication. It should also be used whenever a third party application wants to communicate with a bot. If certain things are not mapped with it, a new connector type should be developed. The default connector communicates using http(s). Requests are always sent as HTTP post commands.

### The basic JSON structure

The communication protocol of the USUBotUniverse uses a JSON data structure to exchange data. There is a base structure for requests and a base structure for responses. 

#### Request

The request object of a request, consists of two essential elements.

1. "session" - Element
2. "request" - Element

The "session" element contains essential parameters for the actual session management of a bot communication. The "request" element contains the actual request to the connected bot.

##### Structure of the "session" element:

```json
"session": 
{  
      "application":
      {  
        "applicationId":"<Name of the calling application>"
      },
      "user":
      {  
        "userId":"<ID of the user used>"
      },
      "attributes":{ },
      "sessionId":"<ID of the session>",
      "new": true "or" false
}
```

###### Description of the "session" attributes:

| Attributes   | Description                                                   | Type     | Optional |
| ----------- | ------------------------------------------------------------ | ------- | -------- |
| application | An object that contains information about the calling application. | Object  | no     |
| user        | An object containing information about the user making a request. Has no meaning yet and "Kbot-Default-User" should be used as value. | Object  | yes       |
| attributes  | An object that represents a key/value list with which freely definable values can be sent with the request. | Object  | yes       |
| sessionId   | The id of the communication session. This should be supplied with every request. If no session ID is specified, a new communication session is created and its ID is supplied with the response. A calling application can also specify its own ID here. If a session exists under this ID, the bot uses this session. If not, a new session is created internally and the given session is used as ID. | String  | no     |
| new         | Specifies whether a new session should be created under this ID. This is relevant if a session already exists on the server under a specified session ID. This session is then discarded and a new session is created. | Boolean | yes       |

###### The "application" attribute

| Attribute      | Description                                                  | Type    | Optional |
| ------------- | ------------------------------------------------------------ | ------ | -------- |
| applicationId | This attribute contains the name of the calling application. e.g. 'Alexa'. | String | no     |

###### The "user" attribute

| Attribute | Description                                                    | Type    | Optional |
| --------- | ------------------------------------------------------------ | ------ | -------- |
| userId    | The id of the user who sends the request. Has no meaning at the moment. | String | no     |

###### The "attributes" attribute

Any key/value pairs can be defined at this object.



##### Structure of the "request" element:

```json
"request":
{  
      "type":"<Request type>",
      "requestId":"<ID of the request>",
      "locale":"<language used>",
      "timestamp":"<Timestamp in UTC format>",
      "payload":
      {  
          "type":"<Payload type/content>"
      }
}
```

###### Description of the "request" attributes

| Attribute  | Description                                                    | Type    | Optional |
| --------- | ------------------------------------------------------------ | ------ | -------- |
| type      | Specifies the type of the request. Possible values at the moment are 'command' and 'message'. | String | no     |
| requestId | The ID of the request. This is set by the requesting application. It has no effect on the communication itself. This ID is returned in the response. | String | yes       |
| locale    | Specifies the (text) language used for the request.       | String | no     |
| timestamp | A timestamp of when the request was created. This is assigned by the calling application and currently has no further meaning. The timestamp should be specified in UTC time format. | String | yes       |
| payload   | The actual communication data is located here. The structure of this object depends on the specified request type. | Object | no     |

###### 'payload' of type 'command'

Using the 'payload' of type 'command', it is specified which 'native' bot action should be executed. 

```json
"payload":{  
    "type":"<action>"
}
```
| Attribute | Description                                                    | Type    | Optional |
| -------- | ------------------------------------------------------------ | ------ | -------- |
| type     | Specifies the command to execute.<br />Possible commands are:<br />'startSession', resetSession', 'endSession'. | String | yes       |

Possible actions:

| Action       | Description                                                    | Parameter |
| ------------ | ------------------------------------------------------------ | --------- |
| startSession | Starts a new session. If a session has been specified via the 'sessionId', it will be terminated, if present. | none     |
| resetSession | Ends the current session and restarts it. The session itself is defined by the 'sessionId' in the session object. | none     |
| endSession   | Ends the current session. The session itself is defined by the 'sessionId' in the session object. | none       |
| available    | Checks whether the bot is available for a certain protocol session. Return 'true' or 'false'.  | none       |
| availableForQuestion    | Checks whether the bot is available for answering certain types of questions. Return 'true' or 'false'. | none 
| responsible | Checks whether the bot finds matching intents to the user's questions. Return 'true' or 'false' and the bot's topics | "text" (user's input)


###### 'payload' of type 'message'

```json
"payload":{
    "text":"test"
}
```

| Attribute | Description                                                   | Type    | Optional |
| -------- | ------------------------------------------------------------ | ------ | -------- |
| text     | The actual textual input to be sent to the bot. | String | yes       |

###### 'payload' of type 'available'

```json
"payload":{
    "type":"available"
}
```

###### 'payload' of type 'availableForQuestion'

```json
"payload":{
    "type":"availableForQuestion"
}
```

###### 'payload' of type 'responsible'

```json
"payload":{
    "type":"responsible",
    "text": "test"
}
```

| Attribute | Description                                                   | Type    | Optional |
| -------- | ------------------------------------------------------------ | ------ | -------- |
| test     | User input | String | no       |



##### Example of a request query:

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

The response object of a response, consists of two main elements.

1. "sessionAttributes" element
2. "response" element

The "sessionAttributes" element contains essential parameters for the actual session management of a bot communication. The "response" element contains all information about a request.

##### Structure of the "sessionAttributes" element:

```json
"sessionAttributes" : {
  "sessionId" : "<session-id>"
}
```

| Attribute | Description                                                    | Type      | Optional |
| --------- | ------------------------------------------------------------ | -------- | -------- |
| sessionId    | The id of the session to which the request belongs. | String | no     |

##### Structure of the "response" element:

| Attribute        | Description                                                    | Type                  | Optional |
| ---------------- | ------------------------------------------------------------ | -------------------- | -------- |
| shouldEndSession | indicates whether the session on the server has ended. The calling application should also terminate its session or similar. | Boolean              | yes       |
| type             | Specifies the type of the 'response'. At the moment only the type "reply" exists. | String               | no     |
| payload          | Contains the communication data, responses to a command or request.  A payload can be a single object or an array of objects. As of today, an array is delivered with an object. The calling application should still check whether it is an array or a single object. | Object or Object[] | no     |

###### 'payload' of type 'reply'

```json
"payload":[ 
    {
      "language" : "<language>",
      "conversation" : {
        "bubbles":[
          {
            "content" : "<text>",
            "delay" : "delay in ms (int)",
            "metadata" : { },
            "silentDelay" : true "or" false
          }
        ]
      },
      "interaction" : {
        "multiSelectAllowed" : true "or" false,
        "inputType" : "<input type>",
        "inputOptions" : [ {
          "label" : "<label>",
          "value" : "<command>",
          "styleInfo" : "<css style>",
          "metadata" : { }
        } ],
        "inputFieldActive" : true "or" false,
        "inputHint" : "<hint>"
      }
    } 
]
```

| Attribute    | Description                                                    | Type    | Optional |
| ------------ | ------------------------------------------------------------ | ------ | -------- |
| language     | Indicates in which language the answer is written.        | String | no     |
| topic        | Indicates the topic/topic of the answer.                        | String | yes       |
| conversation | Contains the text output of the bot in the form of individual bubbles. | Object | no     |
| interaction  | Contains the possible input options for the user.   | Object | no     |

###### The 'conversation' attribute

```json
"conversation" : {
        "bubbles":[
          {
            "content" : "<text>",
            "speech" : "<ssml>",
            "delay" : "delay in ms (int)",
            "metadata" : { },
            "silentDelay" : true "or" false
          }
        ]
      }
```

| Attribute | Description                                             | Type   | Optional |
| --------- | ----------------------------------------------------- | ----- | -------- |
| bubbles   | Includes one or more text outputs for the bot. | Array | no     |

###### The 'bubbles' attribute

```json
"bubbles":[
    {
        "content" : "<text>",
        "speech" : "<ssml>",
        "delay" : "delay in ms (int)",
        "metadata" : { },
    	"silentDelay" : true "or" false
    }
]
```

| Attribute   | Description                                                    | Type     | Optional |
| ----------- | ------------------------------------------------------------ | ------- | -------- |
| content     | The text to be output inside a speech bubble.         | String  | yes       |
| speech      | The text of a speech bubble to be spoken in SSML syntax.     | String  | yes       |
| delay       | Specifies the waiting time in milliseconds before the balloon should be displayed in the frontend. If a value <= 0 is specified, the balloon is displayed immediately. Default value is 0. | Integer | yes       |
| silentDelay | If the value 'false' is specified here, an animation (pause for thought) is displayed for the waiting time 'delay'. For the value 'true' nothing is displayed. The default value is 'false'. | Boolean | yes       |
| metadata    | A Key/Value memory for additional information.        | Object  | yes       |

###### The 'interaction' attribute

```json
"interaction" : {
    "multiSelectAllowed" : true "or" false,
    "inputType" : "<input type>",
    "inputOptions" :[ 
      {
    	"label" : "<label>",
    	"value" : "<command>",
    	"styleInfo" : "<css style>",
    	"metadata" : { }
	   }
     ],
	"inputFieldActive" : true "or" false,
	"inputHint" : "<hint>"
}
```

| Attribute          | Description                                                    | Type     | Optional |
| ------------------ | ------------------------------------------------------------ | ------- | -------- |
| multiSelectAllowed | Depending on the input type, this value indicates whether multiple selection is possible. Used in connection with 'select' inputs. | Boolean | yes       |
| inputType          | Specifies the type of input option by the user.   | String  | no     |
| allowAutoComplete  | Specifies whether the AutoComplete function should be activated on the interface. This is a KBot feature. | Boolean | yes       |
| inputOptions       | Also used in connection with 'select' inputs. The individual possible options are listed here. | Array   | yes       |
| inputFieldActive   | Indicates whether textual input is possible.                 | Boolean | no     |
| inputFieldVisible  | Specifies whether the input field should be displayed or not. | Boolean | yes       |
| inputHint          | Specifies a hint/help text which is displayed in the input field and tells the user what action he can perform. | String  | yes       |
| inputFieldPattern  | Sets the input format for the input field.            | String  | yes       |
| showResultCount    | Specifies how many element should be displayed before a paging is activated. This is only relevant in conjunction with the inputOptions property. | Integer | yes       |

###### The 'inputOptions' attribute

```json
"inputOptions" :[ 
      {
    	"label" : "<label>",
    	"value" : "<command>",
    	"styleInfo" : "<css style>",
    	"metadata" : { }
	   }
     ]
```

| Attribute | Description                                                    | Type     | Optional |
| --------- | ------------------------------------------------------------ | ------- | -------- |
| label     | The text to be displayed.                                       | Boolean | no     |
| inputType | The value that will be sent as a request to the bot when selected. The 'request' should be of type 'message'. | String  | no     |
| styleInfo | Possible additional info, which can be used to style the elements via CSS. | String  | yes       |
| metadata  | Key/Value Store. Not used at the moment.          | Object  | yes       |



##### Example of a response answer:

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
          "label" : "Guided tour of the tips and tricks offered",
          "value" : "#cmd://odin?i=int487",
          "styleInfo" : "dialog",
          "metadata" : { }
        } ],
        "inputFieldActive" : true,
        "inputHint" : "Enter here or select option... "
      }
    } ]
  }
}
```



### Example requests for communication 

#### Communication opening

Opening a communication can be done either by using the 'startSession' command or directly by sending a request.

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
          "label" : "Guided tour of the tips and tricks offered",
          "value" : "#cmd://odin?i=int487",
          "styleInfo" : "dialog",
          "metadata" : { }
        } ],
        "inputFieldActive" : true,
        "inputHint" : "Enter here or select option... "
      }
    } ]
  }
}
```
<u>Example as cUrl:</u>

```bash
curl "http://localhost:9190/kbot-api/interact/kcpreview/widget:Default" -H "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" -H "Accept: */*" -H "Accept-Language: de,en-US;q=0.7,en;q=0.3" -H "Content-Type: application/json; charset=UTF-8" -H "Connection: keep-alive" --data "{\"session\":{\"application\":{\"applicationId\":\"Knowledge Bot Widget\"},\"user\":{\"userId\":\"Kbot-Default-User\"},\"attributes\":{},\"new\":true},\"request\":{\"type\":\"command\",\"requestId\":\"1\",\"locale\":\"de\",\"timestamp\":\"2018-11-09T16:06:52.331Z\",\"payload\":{\"type\":\"startSession\"}}}"
```



#### Asking a question

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
            "text":"What is alexa brain"
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
          "content" : "Alexa Brain is a great piece of software.",
          "delay" : 0,
          "silentDelay" : false
        }, {
          "content" : "Was this the right answer?",
          "delay" : 1000,
          "silentDelay" : true
        } ]
      },
      "interaction" : {
        "multiSelectAllowed" : false,
        "inputType" : "vertical_select",
        "inputOptions" : [ {
          "label" : "Yes",
          "value" : "#cmd://yes"
        }, {
          "label" : "No",
          "value" : "#cmd://no"
        } ],
        "inputHint" : "Select option...",
        "inputFieldActive" : false
      }
    } ]
  }
}
```

<u>Example as cUrl:</u>

```bash
curl "http://knowledgefirst-poc.usu.de/kbot-api/interact/kcpreview/widget:Default" -H "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" -H "Accept: */*" -H "Accept-Language: de,en-US;q=0.7,en;q=0.3" --compressed -H "Content-Type: application/json; charset=UTF-8" -H "Connection: keep-alive" --data "{\"session\":{\"application\":{\"applicationId\":\"Knowledge Bot Widget\"},\"user\":{\"userId\":\"Kbot-Default-User\"},\"attributes\":{},\"sessionId\":\"633f9264:16708c1a9b8:-7ffb\",\"new\":false},\"request\":{\"type\":\"message\",\"requestId\":\"2\",\"locale\":\"de\",\"timestamp\":\"2018-11-12T21:48:04.102Z\",\"payload\":{\"text\":\"What is alexa brain\"}}}"
```



#### Sending an option

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
          "content" : "Great!",
          "delay" : 0,
          "silentDelay" : false
        }, {
          "content" : "If you have any further questions, feel free to ask ...",
          "delay" : 500,
          "silentDelay" : false
        } ]
      },
      "interaction" : {
        "inputType" : "text",
        "inputHint" : "Enter here...",
        "inputFieldActive" : true
      }
    } ]
  }
}
```



<u>Example as cUrl</u>

```bash
curl "http://knowledgefirst-poc.usu.de/kbot-api/interact/kcpreview/widget:Default" -H "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" -H "Accept: */*" -H "Accept-Language: de,en-US;q=0.7,en;q=0.3" --compressed -H "Referer: http://knowledgefirst-poc.usu.de/kbot-market/bots/preview/kcpreview" -H "Content-Type: application/json; charset=UTF-8" -H "x-kbot-token: /*\"kcpreview\"*/" -H "lang: en" -H "x-referer: http://knowledgefirst-poc.usu.de/kbot-market/bots/preview/kcpreview" -H "Connection: keep-alive" -H "Cookie: _ga=GA1.2.756883637.1525766520" --data "{\"session\":{\"application\":{\"applicationId\":\"Knowledge Bot Widget\"},\"user\":{\"userId\":\"Kbot-Default-User\"},\"attributes\":{},\"sessionId\":\"633f9264:16708c1a9b8:-7ffb\",\"new\":false},\"request\":{\"type\":\"message\",\"requestId\":\"4\",\"locale\":\"de\",\"timestamp\":\"2018-11-12T21:59:02.750Z\",\"payload\":{\"text\":\"#cmd://yes\"}}}"
```

#### Terminate communication

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
          "content" : "Until the next time. Bye!",
          "delay" : 0,
          "metadata" : { },
          "silentDelay" : false
        } ]
      }
    } ]
  }
}
```

<u>Example as cUrl:</u>

```bash
curl "http://localhost:9190/kbot-api/interact/kcpreview/widget:Default" -H "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0" -H "Accept: */*" -H "Accept-Language: de,en-US;q=0.7,en;q=0.3" --compressed -H "Content-Type: application/json; charset=UTF-8" -H "Connection: keep-alive" --data "{\"session\":{\"application\":{\"applicationId\":\"Knowledge Bot Widget\"},\"user\":{\"userId\":\"Kbot-Default-User\"},\"attributes\":{},\"new\":false,\"sessionId\": \"633f9264:16708c1a9b8:-7ffb\"},\"request\":{\"type\":\"command\",\"requestId\":\"1\",\"locale\":\"de\",\"timestamp\":\"2018-11-09T16:06:53.001Z\",\"payload\":{\"type\":\"endSession\"}}}"
```

#### Asking if a bot is responsible for answering a question

<u>JSON Request Structure</u>

```json
{
  "session": {
    "application": {
      "applicationId": "KBot"
    },
    "user": {
      "userId": "127921f7-c89d-4d15-a5b8-5307836e6af6"
    },
    "asTeamMember": true,
    "smalltalk": false,
    "new": false
  },
  "request": {
    "type": "message",
    "requestId": "1",
    "locale": "en",
    "timestamp": "2023-01-16T09:52:37.386+00:00",
    "payload": {
      "type": "responsible",
      "text": "test"
    },
  }
}
```

<u>JSON Response Structure</u>

```json
{
  "sessionAttributes": {
    "sessionId": "633f9264:16708c1a9b8:-7ffb"
  },
  "response": {
    "shouldEndSession": false,
    "type": "reply",
    "payload": [
      {
        "responsible": {
          "responsible": true,
          "topics": [
            {
              "name": "weather",
              "confidence": "1.0"
            }
          ]
        }
      }
    ]
  }
}
```

| Attribute | Description                                                   | Type    | 
| -------- | ------------------------------------------------------------ | ------ | 
| responsible     | Indicates whether the bot finds any answers to user's input | Boolean | 
| topics     | Array of topics the bot can offer | Array | 
| name     | Name of the topic | String | 
| confidence     | Extend to which the answer matches user's input | Double | 

#### Asking if a bot is available at all at session start

<u>JSON Request Structure</u>

```json
{
  "session": {
    "application": {
      "applicationId": "KBot"
    },
    "user": {
      "userId": "127921f7-c89d-4d15-a5b8-5307836e6af6"
    },
    "asTeamMember": true,
    "smalltalk": false,
    "new": false
  },
  "request": {
    "type": "message",
    "requestId": "1",
    "locale": "en",
    "timestamp": "2023-01-16T09:52:37.386+00:00",
    "payload": {
      "type": "avaiable",
    },
  }
}
```

<u>JSON Response Structure</u>

```json
{
  "sessionAttributes": {
    "sessionId": "633f9264:16708c1a9b8:-7ffb"
  },
  "response": {
    "shouldEndSession": false,
    "type": "reply",
    "payload": [
      {
        "available": {
          "available": false
        }
      }
    ]
  }
}
```

#### Asking if a bot is available for answering a certain domain of questions


<u>JSON Request Structure</u>

```json
{
  "session": {
    "application": {
      "applicationId": "KBot"
    },
    "user": {
      "userId": "127921f7-c89d-4d15-a5b8-5307836e6af6"
    },
    "asTeamMember": true,
    "smalltalk": false,
    "new": false
  },
  "request": {
    "type": "message",
    "requestId": "1",
    "locale": "en",
    "timestamp": "2023-01-16T09:52:37.386+00:00",
    "payload": {
      "type": "availableForQuestion",
      "domain": "Berlin"
    },
  }
}
```

<u>JSON Response Structure</u>

```json
{
  "sessionAttributes": {
    "sessionId": "633f9264:16708c1a9b8:-7ffb"
  },
  "response": {
    "shouldEndSession": false,
    "type": "reply",
    "payload": [
      {
        "available": {
          "available": true
        }
      }
    ]
  }
}
```
