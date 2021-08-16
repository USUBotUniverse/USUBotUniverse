package com.unymira.kbot.metabot.protocol.client.topics;

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
@JsonPropertyOrder({ "sessionId", "topics" })
public class TopicResponseRootObject
{

	@JsonProperty("sessionId")
	@Valid
	private String sessionId;
	@JsonProperty("topics")
	@Valid
	private List<Topic> topics;

	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("sessionId")
	public String getSessionId()
	{
		return sessionId;
	}

	@JsonProperty("sessionId")
	public void setSessionId(final String p_sessionId)
	{
		this.sessionId = p_sessionId;
	}

	public TopicResponseRootObject withSessionId(final String p_sessionId)
	{
		this.sessionId = p_sessionId;
		return this;
	}

	@JsonProperty("topics")
	public List<Topic> getTopics()
	{
		return topics;
	}

	@JsonProperty("topics")
	public void setResponse(final List<Topic> p_topics)
	{
		this.topics = p_topics;
	}

	public TopicResponseRootObject withResponse(final List<Topic> p_topics)
	{
		this.topics = p_topics;
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

	public TopicResponseRootObject withAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
		return this;
	}

}