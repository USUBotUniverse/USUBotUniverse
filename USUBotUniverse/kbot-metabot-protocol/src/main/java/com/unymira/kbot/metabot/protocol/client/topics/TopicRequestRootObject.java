package com.unymira.kbot.metabot.protocol.client.topics;

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
"sessionId" : "",
"input" : ""
}
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "sessionId", "input" })
public class TopicRequestRootObject
{
	@JsonProperty("sessionId")
	@Valid
	private String sessionId;
	@JsonProperty("input")
	@Valid
	private String input;
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("sessionId")
	public String getSessionId()
	{
		return sessionId;
	}

	@JsonProperty("sessionId")
	public void setSession(final String p_sessionId)
	{
		this.sessionId = p_sessionId;
	}

	public TopicRequestRootObject withSessionId(final String p_sessionId)
	{
		this.sessionId = p_sessionId;
		return this;
	}

	@JsonProperty("input")
	public String getInput()
	{
		return input;
	}

	@JsonProperty("input")
	public void setInput(final String p_input)
	{
		this.input = p_input;
	}

	public TopicRequestRootObject withInput(final String p_input)
	{
		this.input = p_input;
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

	public TopicRequestRootObject withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}
}
