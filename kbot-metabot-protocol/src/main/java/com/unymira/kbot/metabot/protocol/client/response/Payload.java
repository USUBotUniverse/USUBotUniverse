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
@JsonPropertyOrder({ "language", "conversation", "interaction", "metadata", "responsible", "available", "availableForQuestion"})
public class Payload
{
	@JsonProperty("language")
	private String language;

	@JsonProperty("topic")
	private String topic;

	@JsonProperty("conversation")
	@Valid
	private Conversation conversation;

	@JsonProperty("interaction")
	@Valid
	private Interaction interaction;

	@JsonProperty("responsible")
	@Valid
	@JsonIgnore
	private Responsible responsible;

	@JsonProperty("available")
	@Valid
	@JsonIgnore
	private Available available;

	@JsonProperty("availableForQuestion")
	@Valid
	@JsonIgnore
	private AvailableForQuestion availableForQuestion;

	@JsonProperty("metadata")
	private Map<String, Object> metadata = new HashMap<>();
	@JsonIgnore
	@Valid
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonProperty("language")
	public String getLanguage()
	{
		return language;
	}

	@JsonProperty("language")
	public void setLanguage(final String language)
	{
		this.language = language;
	}

	public Payload withLanguage(final String language)
	{
		this.language = language;
		return this;
	}

	@JsonProperty("topic")
	public String getTopic()
	{
		return topic;
	}

	@JsonProperty("topic")
	public void setTopic(final String topic)
	{
		this.topic = topic;
	}

	public Payload withTopic(final String topic)
	{
		this.topic = topic;
		return this;
	}

	@JsonProperty("conversation")
	public Conversation getConversation()
	{
		return conversation;
	}

	@JsonProperty("conversation")
	public void setConversation(final Conversation conversation)
	{
		this.conversation = conversation;
	}

	public Payload withConversation(final Conversation conversation)
	{
		this.conversation = conversation;
		return this;
	}

	@JsonProperty("interaction")
	public Interaction getInteraction()
	{
		return interaction;
	}

	@JsonProperty("interaction")
	public void setInteraction(final Interaction interaction)
	{
		this.interaction = interaction;
	}

	public Payload withInteraction(final Interaction interaction)
	{
		this.interaction = interaction;
		return this;
	}

	@JsonProperty("responsible")
	public Responsible getResponsible()
	{
		return responsible;
	}

	@JsonProperty("responsible")
	public void setResponsible(final Responsible responsible)
	{
		this.responsible = responsible;
	}

	public Payload withResponsible(final Responsible responsible)
	{
		this.responsible = responsible;
		return this;
	}

	@JsonProperty("available")
	public Available getAvailable()
	{
		return available;
	}

	@JsonProperty("available")
	public void setAvailable(final Available available)
	{
		this.available = available;
	}

	public Payload withAvaiable(final Available available)
	{
		this.available = available;
		return this;
	}

	@JsonProperty("availableForQuestion")
	public AvailableForQuestion getAvailableForQuestion()
	{
		return availableForQuestion;
	}

	@JsonProperty("availableForQuestion")
	public void setAvailableForQuestion(final AvailableForQuestion availableForQuestion)
	{
		this.availableForQuestion = availableForQuestion;
	}

	public Payload withAvailableForQuestion(final AvailableForQuestion availableForQuestion)
	{
		this.availableForQuestion = availableForQuestion;
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

	@JsonProperty("metadata")
	public void setMetadata(final Map<String, Object> metadata)
	{
		this.metadata = metadata;
	}

	@JsonProperty("metadata")
	public Map<String, Object> getMetadata()
	{
		return metadata;
	}

}