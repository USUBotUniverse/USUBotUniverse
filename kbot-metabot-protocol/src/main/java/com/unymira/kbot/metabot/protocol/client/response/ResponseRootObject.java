package com.unymira.kbot.metabot.protocol.client.response;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "sessionAttributes", "response" })
public class ResponseRootObject
{

	@JsonProperty("sessionAttributes")
	@Valid
	private SessionAttributes sessionAttributes;
	@JsonProperty("response")
	@Valid
	private Response response;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("sessionAttributes")
	public SessionAttributes getSessionAttributes()
	{
		return sessionAttributes;
	}

	@JsonProperty("sessionAttributes")
	public void setSessionAttributes(final SessionAttributes sessionAttributes)
	{
		this.sessionAttributes = sessionAttributes;
	}

	public ResponseRootObject withSessionAttributes(final SessionAttributes sessionAttributes)
	{
		this.sessionAttributes = sessionAttributes;
		return this;
	}

	@JsonProperty("response")
	public Response getResponse()
	{
		return response;
	}

	@JsonProperty("response")
	public void setResponse(final Response response)
	{
		this.response = response;
	}

	public ResponseRootObject withResponse(final Response response)
	{
		this.response = response;
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

	public ResponseRootObject withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}

}