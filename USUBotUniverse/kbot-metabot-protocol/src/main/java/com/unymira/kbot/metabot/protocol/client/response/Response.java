package com.unymira.kbot.metabot.protocol.client.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "shouldEndSession", "type", "payload" })
public class Response
{

	@JsonProperty("shouldEndSession")
	private Boolean shouldEndSession;
	@JsonProperty("type")
	private String type;
	@JsonProperty("payload")
	@Valid
	private List<Payload> payload = null;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("shouldEndSession")
	public Boolean getShouldEndSession()
	{
		return shouldEndSession;
	}

	@JsonProperty("shouldEndSession")
	public void setShouldEndSession(final Boolean shouldEndSession)
	{
		this.shouldEndSession = shouldEndSession;
	}

	public Response withShouldEndSession(final Boolean shouldEndSession)
	{
		this.shouldEndSession = shouldEndSession;
		return this;
	}

	@JsonProperty("type")
	public String getType()
	{
		return type;
	}

	@JsonProperty("type")
	public void setType(final String type)
	{
		this.type = type;
	}

	public Response withType(final String type)
	{
		this.type = type;
		return this;
	}

	@JsonProperty("payload")
	public List<Payload> getPayload()
	{
		return payload;
	}

	@JsonProperty("payload")
	public void setPayload(final List<Payload> payload)
	{
		this.payload = payload;
	}

	public Response withPayload(final List<Payload> payload)
	{
		this.payload = payload;
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

	public Response withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}

}
