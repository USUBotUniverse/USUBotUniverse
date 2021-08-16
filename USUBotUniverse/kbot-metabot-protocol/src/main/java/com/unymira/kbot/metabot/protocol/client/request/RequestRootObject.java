package com.unymira.kbot.metabot.protocol.client.request;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/*
"session" : {
    "application" : {
      "applicationId" : KBotConfig.applicationId
    },
    "user" : {
      "userId" : KBotConfig.userId
    },
    "attributes" : { },
    "new" : true
  },
  "request" : {
    "type" : "command",
    "requestId" : "" + (_requestCounter++),
    "locale" : KBotConfig.locale,
    "timestamp" : new Date().toISOString(),
    "payload" : { "type" : "startSession" }
}
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "session", "request" })
public class RequestRootObject
{
	@JsonProperty("session")
	@Valid
	private Session session;
	@JsonProperty("request")
	@Valid
	private Request request;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("session")
	public Session getSession()
	{
		return session;
	}

	@JsonProperty("session")
	public void setSession(final Session session)
	{
		this.session = session;
	}

	public RequestRootObject withSession(final Session session)
	{
		this.session = session;
		return this;
	}

	@JsonProperty("request")
	public Request getRequest()
	{
		return request;
	}

	@JsonProperty("request")
	public void setRequest(final Request request)
	{
		this.request = request;
	}

	public RequestRootObject withRequest(final Request request)
	{
		this.request = request;
		return this;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
	}

	public RequestRootObject withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}
}
