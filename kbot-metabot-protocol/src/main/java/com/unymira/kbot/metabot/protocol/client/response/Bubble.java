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
@JsonPropertyOrder({ "content", "speech", "delay", "metadata", "silentDelay" })
public class Bubble
{

	@JsonProperty("content")
	private String content;
	@JsonProperty("delay")
	private Integer delay;
	@JsonProperty("metadata")
	@Valid
	private Metadata metadata;
	@JsonProperty("silentDelay")
	private Boolean silentDelay;
	@JsonProperty("speech")
	private String speech;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("content")
	public String getContent()
	{
		return content;
	}

	@JsonProperty("content")
	public void setContent(final String content)
	{
		this.content = content;
	}

	public Bubble withContent(final String content)
	{
		this.content = content;
		return this;
	}

	@JsonProperty("speech")
	public String getSpeech()
	{
		return speech;
	}

	@JsonProperty("speech")
	public void setSpeech(final String speech)
	{
		this.speech = speech;
	}

	public Bubble withSpeech(final String speech)
	{
		this.speech = speech;
		return this;
	}

	@JsonProperty("delay")
	public Integer getDelay()
	{
		return delay;
	}

	@JsonProperty("delay")
	public void setDelay(final Integer delay)
	{
		this.delay = delay;
	}

	public Bubble withDelay(final Integer delay)
	{
		this.delay = delay;
		return this;
	}

	@JsonProperty("metadata")
	public Metadata getMetadata()
	{
		return metadata;
	}

	@JsonProperty("metadata")
	public void setMetadata(final Metadata metadata)
	{
		this.metadata = metadata;
	}

	public Bubble withMetadata(final Metadata metadata)
	{
		this.metadata = metadata;
		return this;
	}

	@JsonProperty("silentDelay")
	public Boolean getSilentDelay()
	{
		return silentDelay;
	}

	@JsonProperty("silentDelay")
	public void setSilentDelay(final Boolean silentDelay)
	{
		this.silentDelay = silentDelay;
	}

	public Bubble withSilentDelay(final Boolean silentDelay)
	{
		this.silentDelay = silentDelay;
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

	public Bubble withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}

}
