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
@JsonPropertyOrder({ "userId" })
public class User
{

	@JsonProperty("userId")
	private String userId;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("userId")
	public String getUserId()
	{
		return userId;
	}

	@JsonProperty("userId")
	public void setUserId(final String userId)
	{
		this.userId = userId;
	}

	public User withUserId(final String userId)
	{
		this.userId = userId;
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

	public User withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}
}
