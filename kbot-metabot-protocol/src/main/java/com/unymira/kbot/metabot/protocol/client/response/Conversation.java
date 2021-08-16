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
@JsonPropertyOrder({ "bubbles" })
public class Conversation
{

	@JsonProperty("bubbles")
	@Valid
	private List<Bubble> bubbles = null;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("bubbles")
	public List<Bubble> getBubbles()
	{
		return bubbles;
	}

	@JsonProperty("bubbles")
	public void setBubbles(final List<Bubble> bubbles)
	{
		this.bubbles = bubbles;
	}

	public Conversation withBubbles(final List<Bubble> bubbles)
	{
		this.bubbles = bubbles;
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

	public Conversation withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}

}
