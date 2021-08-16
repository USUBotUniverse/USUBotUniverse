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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type" })
public class Payload
{
	@JsonProperty("type")
	private String type;
	@JsonProperty("text")
	private String text;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

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

	public Payload withType(final String type)
	{
		this.type = type;
		return this;
	}

	@JsonProperty("text")
	public String getText()
	{
		return text;
	}

	@JsonProperty("text")
	public void setText(final String text)
	{
		this.text = text;
	}

	public Payload withText(final String text)
	{
		this.text = text;
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

	public Payload withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}
}
