package com.unymira.kbot.metabot.protocol.client.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "availableForQuestion" })
public class AvailableForQuestion
{
	@JsonProperty("availableForQuestion")
	private Boolean m_available;

	public AvailableForQuestion()
	{

	}

	@JsonGetter("availableForQuestion")
	public Boolean getAvailable()
	{
		return m_available == null ? Boolean.FALSE : m_available;
	}

	@JsonSetter("availableForQuestion")
	public void setAvailable(final Boolean available)
	{
		this.m_available = available;
	}

	@Override
	public String toString()
	{
		return "Available for question [m_availableForQuestion=" + m_available + "]";
	}

}
