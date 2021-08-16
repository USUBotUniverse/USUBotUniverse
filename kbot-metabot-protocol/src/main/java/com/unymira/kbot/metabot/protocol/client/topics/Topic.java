package com.unymira.kbot.metabot.protocol.client.topics;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "confidence", "name" })
public class Topic
{
	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("confidence")
	private Double confidence;

	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("id")
	public String getId()
	{
		return id;
	}

	@JsonProperty("id")
	public void setId(final String p_id)
	{
		this.id = p_id;
	}

	@JsonProperty("confidence")
	public Double getConfidence()
	{
		return confidence;
	}

	@JsonProperty("confidence")
	public void setConfidence(final Double p_confidence)
	{
		this.confidence = p_confidence;
	}

	public Topic withConfidence(final Double p_confidence)
	{
		this.confidence = p_confidence;
		return this;
	}

	@JsonProperty("name")
	public String getName()
	{
		return name;
	}

	@JsonProperty("name")
	public void setName(final String p_name)
	{
		this.name = p_name;
	}

	public Topic withName(final String p_name)
	{
		this.name = p_name;
		return this;
	}
}
